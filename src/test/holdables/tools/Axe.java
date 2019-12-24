package test.holdables.tools;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.interfaces.Holdable;

public class Axe extends ToolObject implements Holdable, Element {
	Tuple position;
	boolean held = false;

	public Axe() {
		Main.holdables.add(this);
		position = new Tuple(512, 205);
		haftedImg = Image.loadImage("/res/tools/axe/hafted.png");
		headImg = Image.loadImage("/res/tools/axe/head.png");
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
			y = 7 * 64 - 106 + 48;
		}
		if (hafted) {
			r.drawImage(x, y, 64, haftedImg, 1, 0);
		} else {
			r.drawImage(x, y, 64, headImg, 1, 0);
		}
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
	
	public int getRenderOrder() {
		return (int) position.getY();
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