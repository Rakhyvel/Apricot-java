package test.holdables.tools;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class Stone extends ToolObject implements Element, Holdable, Interactable {
	Tuple position;
	boolean held = false;

	public Stone() {
		this.position = getRandomTuple();
		Main.holdables.add(this);
		haftedImg = Image.loadImage("/res/tools/stone.png");
		// DON'T ADD STONES TO INTERACTABLES!
		// Stones are TOOLS! They are added in ToolObject
	}

	@Override
	public void tick() {
		if (durability <= -1) {
			held = false;
			position = getRandomTuple();
		}
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
		r.drawImage(x, y, 64, haftedImg, 1, 0);
	}

	@Override
	public void input() {
	}

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
		// TODO: Add stone skipping lol
	}

	@Override
	public boolean isConsumed() {
		return false;
	}

	@Override
	public boolean interact(Holdable hand) {
		if (hand instanceof Stone) {
			Tool tool = Main.flintknappingWindow.getTool();
			switch (tool) {
			case SHOVEL_HEAD:
				Shovel shovel = new Shovel();
				shovel.setPosition(position);
				Main.r.addElement(shovel);
				break;
			case AXE_HEAD:
				Axe axe = new Axe();
				axe.setPosition(position);
				Main.r.addElement(axe);
				break;
			case KNIFE_BLADE:
				Knife knife = new Knife();
				knife.setPosition(position);
				Main.r.addElement(knife);
				break;
			default:
				break;
			}
			position = getRandomTuple();
			((Stone) hand).perhapsBreak();
			return true;
		}
		return false;
	}

	static Tuple getRandomTuple() {
		int x, y;
		Tuple randPoint = new Tuple(513, 205);
		do {
			x = Registrar.rand.nextInt(Main.size);
			y = Registrar.rand.nextInt(Main.size);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) <= 0.5 || Main.findClosestDistance(randPoint) < 1
				|| Main.player.getPosition().getDist(randPoint) < 7);
		return randPoint;
	}

}
