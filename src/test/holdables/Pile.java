package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.holdables.tools.Shovel;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class Pile implements Element, Interactable {
	public static enum Material{
		DIRT, LOG;
	}
	
	Tuple position;
	int amount = 0;
	Material material;

	public Pile(Tuple position, int amount, Material material) {
		this.position = position;
		this.amount = amount;
		this.material = material;
		Main.interactables.add(this);
	}

	@Override
	public void tick() {}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * 64 - Main.player.getX() + 32;
		int y = (int) position.getY() * 64 - Main.player.getY() + 32;
		r.drawRect(x - 20, y, 40, amount, 255 << 24 | 60 << 16 | 90 << 8 | 10);
	}

	@Override
	public void input() {}
	
	public void increase() {
		amount++;
	}
	
	public void decrease() {
		amount--;
		if(amount == 0)
			remove();
	}

	@Override
	public Tuple getPosition() {
		return position;
	}
	
	@Override
	public Element setPosition(Tuple position) {
		this.position = new Tuple(position);
		return this;
	}

	public void remove() {
		Main.r.removeElement(this);
		Main.interactables.remove(this);
	}
	
	public Element clone() {
		return new Pile(new Tuple(position), amount, material);
	}

	@Override
	public boolean interact(Holdable hand) {
		// TIP: MAKE ABSOLUTE SURE you return true or false. Don't leave empty threads
		if(hand instanceof Shovel && material == Material.DIRT) {
			if(((Shovel)hand).fullOfDirt) {
				increase();
				((Shovel)hand).fullOfDirt = false;
			} else {
				decrease();
				((Shovel)hand).fullOfDirt = true;
			}
			return true;
		} else if (hand instanceof Log && material == Material.LOG) {
			increase();
			hand.remove();
			return true;
		}
		if(hand != null)
			return false;
		
		if(material == Material.LOG) {
			decrease();
			Log log = new Log(new Tuple(-100, 10000));
			Main.r.addElement(log);
			Main.player.setHand(log);
			return true;
		}
		
		return false;
	}

}
