package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class Log implements Element, Holdable, Interactable {
	Tuple position;
	boolean held = false;
	int[] image;
	double decay = 1;

	public Log(Tuple position) {
		this.position = position;
		Main.holdables.add(this);
		Main.interactables.add(this);
		image = Image.loadImage("/res/plants/log.png");
	}

	@Override
	public void tick() {}

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
		r.drawImage(x, y, 64, image, 1, 0);
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {
		Main.r.removeElement(this);
		Main.holdables.remove(this);
		if (held) {
			Main.player.setHand(null);
		}
		Main.interactables.remove(this);
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

	@Override
	public Element clone() {
		return new Log(position);
	}

	@Override
	public boolean pickup() {
		held = true;
		position.setX(-100);
		position.setY(10000);
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

	@Override
	public boolean interact(Holdable hand) {
		if (hand instanceof Log) {
			Pile pile = new Pile(new Tuple(Main.player.getLookAt()), 2, Pile.Material.LOG);
			Main.r.addElement(pile);
			remove();
			hand.remove();
			Main.player.setHand(null);
		}
		return false;
	}
}
