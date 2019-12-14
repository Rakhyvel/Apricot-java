package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;

/* TODO: Make it so that piles can hold more than one load, like a real pile. 
 * Also there should be piles of all kinds of stuff like logs and sand idk figure it out.
*/
public class Pile implements Element {
	public static enum PileMaterial{
		DIRT;
	}
	
	Tuple position;
	int amount = 0;
	PileMaterial material;

	public Pile(Tuple position, int amount, PileMaterial material) {
		this.position = position;
		this.amount = amount;
		this.material = material;
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
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public Element setPosition(Tuple position) {
		this.position = new Tuple(position);
		return this;
	}

	public void remove() {
		Main.r.removeElement(this);
	}
	
	public Element clone() {
		return new Pile(new Tuple(position), amount, material);
	}

}
