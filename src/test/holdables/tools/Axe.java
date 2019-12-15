package test.holdables.tools;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.interfaces.Holdable;

public class Axe extends ToolObject implements Holdable, Element {
	Tuple position;
	boolean held = false;

	public Axe() {
		Main.holdables.add(this);
		position = new Tuple(512, 205);
	}

	@Override
	public void tick() {
		if (durability <= -1)
			remove();
	}

	@Override
	public void render(Render r) {
		int x = 0;
		int y = 0;
		if (!held) {
			x = (int) position.getX() * 64 - Main.player.getX() + 32;
			y = (int) position.getY() * 64 - Main.player.getY() + 32;
		} else {
			x = 50;
			y = Registrar.canvas.getHeight() - 106;
		}
		if (hafted)
			r.drawRect(x, y, 3, 64, 255 << 24 | 150 << 16 | 75 << 8 | 0);

		r.drawRect(x - 10, y + 10, 20, 20, 255 << 24 | 128 << 16 | 128 << 8 | 128);
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {
		Main.holdables.remove(this);
		Main.r.removeElement(this);
		if (held) {
			Main.player.setHand(null);
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

	public Element clone() {
		return new Shovel();
	}

	@Override
	public boolean pickup() {
		held = true;
		position.setX(-100);
		position.setY(3000);
		return true;
	}

	@Override
	public void drop() {
		held = false;
		position.setX(Main.player.getPosition().getX());
		position.setY(Main.player.getPosition().getY());
	}

	@Override
	public void use() {
	}

	@Override
	public boolean isConsumed() {
		return false;
	}
}