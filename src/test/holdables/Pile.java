package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;
import com.josephs_projects.library.graphics.SpriteSheet;

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
	SpriteSheet spritesheet;
	int[][] images = new int[4][];

	public Pile(Tuple position, int amount, Material material) {
		this.position = position;
		this.amount = amount;
		this.material = material;
		Main.interactables.add(this);
		if(material == Material.DIRT) {
			spritesheet = new SpriteSheet("/res/dirtPile.png", 64);
		} else if (material == Material.LOG) {
			spritesheet = new SpriteSheet("/res/logPile.png", 64);
		}
		for(int i = 0; i < 4; i++) {
			images[i] = spritesheet.getSubset(0, i, 64);
		}
	}

	@Override
	public void tick() {}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * 64 - Main.player.getX() + 32;
		int y = (int) position.getY() * 64 - Main.player.getY() + 32;
		r.drawImage(x, y, 64, images[((amount - 1)/2)], 1, 0);
	}

	@Override
	public void input() {}
	
	public void increase() {
		if(amount < 7)
			amount++;
	}
	
	public void decrease() {
		amount--;
		if(amount == 0)
			remove();
		if(amount == 1 && material == Material.LOG) {
			Main.r.addElement(new Log(position));
			remove();
		}
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
			// Log hit log pile
			increase();
			hand.remove();
			return true;
		}
		if(hand != null)
			return false;
		
		if(material == Material.LOG) {
			// Hand picks up a log pile
			decrease();
			Log log = new Log(new Tuple(-100, 10000));
			Main.r.addElement(log);
			Main.player.setHand(log);
			return true;
		}
		
		return false;
	}

}
