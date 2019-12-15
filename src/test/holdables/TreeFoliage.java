package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.holdables.tools.Stone;
import test.interfaces.Holdable;

public class TreeFoliage implements Element, Holdable {
	Tuple position;
	boolean held = false;

	public TreeFoliage(Tuple position) {
		this.position = position;
		Main.holdables.add(this);
	}

	@Override
	public void tick() {
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
		r.drawRect(x - 22, y - 22, 44, 44, 255 << 24 | 75 << 16 | 255 << 8 | 0);
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {
		Main.r.removeElement(this);
		Main.holdables.remove(this);
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
