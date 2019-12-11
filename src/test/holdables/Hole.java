package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.beings.plants.Plantable;

/**
 * Hole acts as the shepherd for seeds. It watches and listens each time it is
 * filled to see if it has a seed above it. If so, it will grow that seed into a
 * baby plant.
 * 
 * @author Joseph Shimel
 *
 */
public class Hole implements Element {
	Tuple position;

	public Hole(Tuple position) {
		this.position = position;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * 64 - Main.player.getX() + 32;
		int y = (int) position.getY() * 64 - Main.player.getY() + 32;
		r.drawRect(x - 20, y, 40, 20, 255 << 24 | 90 << 16 | 60 << 8 | 10);
	}

	@Override
	public void input() {
	}

	@Override
	public Tuple getPosition() {
		return position;
	}

	@Override
	public void remove() {
		Main.r.removeElement(this);
		Element closestElement = findElement();
		if (closestElement == null)
			return;

		((FruitObject) closestElement).sprout();
	}

	Element findElement() {
		Element closestElement = null;
		double closestDistance = 1;
		for (int i = 0; i < Main.r.registrySize(); i++) {
			double tempDist = Main.r.getElement(i).getPosition().getDist(position);
			if (!(Main.r.getElement(i) instanceof Plantable))
				continue;
			// This method will try not to return itself
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closestElement = Main.r.getElement(i);
			}
		}
		return closestElement;
	}

}
