package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.holdables.tools.Stone;
import test.holdables.tools.ToolObject;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class Stick implements Element, Holdable, Interactable{
	Tuple position;
	boolean held = false;
	int[] image;

	public Stick(Tuple position) {
		this.position = position;
		Main.holdables.add(this);
		Main.interactables.add(this);
		image = Image.loadImage("/res/tools/stick.png");
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
	public void input() {}

	@Override
	public void remove() {
		Main.r.removeElement(this);
		Main.holdables.remove(this);
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

	@Override
	public boolean interact(Holdable hand) {
		if(hand instanceof ToolObject) {
			if(((ToolObject) hand).hafted)
				return false;
			
			((ToolObject) hand).hafted = true;
			position = Stick.getRandomTuple();
			return true;
		}
		return false;
	}

	static Tuple getRandomTuple() {
		Tuple randPoint = new Tuple(513, 205);
		do {
			int x = Registrar.rand.nextInt(1025);
			int y = Registrar.rand.nextInt(1025);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) < 0.5 || Main.findClosestDistance(randPoint) < 1
				|| Main.player.getPosition().getDist(randPoint) < 7);
		return randPoint;
	}
}
