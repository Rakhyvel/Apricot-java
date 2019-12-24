package test.interactables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.holdables.tools.Stone;

public class Firepit implements Element {
	Tuple position;
	int amount = 1800;

	public Firepit(Tuple position) {
		this.position = position;
		Main.lightSources.add(this);
	}

	@Override
	public void tick() {
		if (amount > 0)
			amount--;
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * 64 - Main.player.getX() + 32;
		int y = (int) position.getY() * 64 - Main.player.getY() + 32;
		r.drawRect(x, y, 64, 64, 255 << 24 | 255 << 16);
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {
		Main.r.removeElement(this);
		Main.lightSources.remove(this);
	}

	public int getRenderOrder() {
		return (int) position.getY() - 1;
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

	public Element clone() {
		return new Stone();
	}

}
