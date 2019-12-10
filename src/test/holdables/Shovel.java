package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;

public class Shovel implements Holdable, Element {
	Tuple position;
	boolean held = false;
	boolean fullOfDirt = false;

	public Shovel() {
		Main.holdables.add(this);
		position = new Tuple(512, 205);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Render r) {
		if (!held) {
			int x = (int) position.getX() * 64 - Main.player.getX() + 32;
			int y = (int) position.getY() * 64 - Main.player.getY();
			r.drawRect(x, y, 3, 64, 255 << 24 | 90 << 16 | 90 << 8 | 90);
			if (fullOfDirt) {
				r.drawRect(x - 10, y, 20, 20, 255 << 24 | 90 << 16 | 60 << 8 | 10);
			} else {
				r.drawRect(x - 10, y, 20, 20, 255 << 24 | 128 << 16 | 128 << 8 | 128);
			}
		}
	}

	@Override
	public void input() {
	}

	@Override
	public boolean pickup() {
		held = true;
		position.setX(-100);
		position.setY(-100);
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
		Element closest = findElement();

		if (fullOfDirt) {
			if (closest == null) {
				// Shovel is full and there is nothing
				Pile pile = new Pile(new Tuple(Main.player.getPosition()), 1, Pile.PileMaterial.DIRT);
				Main.r.addElement(pile);
				fullOfDirt = false;
			} else if (closest instanceof Hole) {
				// Shovel is full and there is a hole to fill
				closest.remove();
				fullOfDirt = false;
			} else if (closest instanceof Pile) {
				// Shovel is full and there is a pile to add to
				Pile pile = (Pile) closest;
				pile.increase();
				fullOfDirt = false;
			}
			return;
		}
		if (closest == null) {
			// Shovel is empty and there was nothing obstructing
			Hole hole = new Hole(new Tuple(Main.player.getPosition()));
			Main.r.addElement(hole);
			fullOfDirt = true;
		} else if (closest instanceof Pile) {
			// Shovel is empty and there is a pile of dirt
			((Pile) closest).decrease();
			fullOfDirt = true;
		}

	}

	@Override
	public boolean isConsumed() {
		return false;
	}

	@Override
	public Tuple getPosition() {
		return position;
	}

	@Override
	public void remove() {
		Main.holdables.remove(this);
		Main.r.removeElement(this);
	}

	Element findElement() {
		Element closest = null;
		double closestDistance = 1;
		for (int i = 0; i < Main.r.registrySize(); i++) {
			if (!(Main.r.getElement(i) instanceof Hole) && !(Main.r.getElement(i) instanceof Pile))
				continue;
			double tempDist = Main.r.getElement(i).getPosition().getDist(Main.player.getPosition());
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closest = Main.r.getElement(i);
			}
		}
		return closest;
	}

}
