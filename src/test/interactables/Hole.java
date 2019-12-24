package test.interactables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.holdables.tools.Shovel;
import test.interfaces.Holdable;
import test.interfaces.Interactable;
import test.interfaces.Plantable;

/**
 * Hole acts as the shepherd for seeds. It watches and listens each time it is
 * filled to see if it has a seed above it. If so, it will grow that seed into a
 * baby plant.
 * 
 * @author Joseph Shimel
 *
 */
public class Hole implements Element, Interactable {
	Tuple position;
	int[] image;

	public Hole(Tuple position) {
		this.position = position;
		Main.interactables.add(this);
		image = Image.loadImage("/res/hole.png");
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * 64 - Main.player.getX() + 32;
		int y = (int) position.getY() * 64 - Main.player.getY() + 32;
		r.drawImage(x, y, 64, image, 1, 0);
	}

	@Override
	public void input() {}

	@Override
	public void remove() {
		Main.r.removeElement(this);
		Main.interactables.remove(this);
	}
	
	public int getRenderOrder() {
		return -1;
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
		return new Hole(new Tuple(position));
	}

	@Override
	public boolean interact(Holdable hand) {
		if (hand instanceof Shovel) {
			// Shovel must be hafted to work
			if(!((Shovel) hand).hafted)
				return false;
			
			if(((Shovel)hand).fullOfDirt) {
				remove();
				((Shovel)hand).fullOfDirt = false;
				sprout();
			}
			return true;
		}
		return false;
	}

	void sprout() {
		Holdable closestElement = findSeed();
		if (closestElement == null)
			return;
		
		((Plantable) closestElement).sprout();
		remove();
	}
	
	Holdable findSeed() {
		Holdable closestElement = null;
		double closestDistance = 1;
		for (int i = 0; i < Main.holdables.size(); i++) {
			double tempDist = Main.holdables.get(i).getPosition().getDist(position);
			if (!(Main.holdables.get(i) instanceof Plantable))
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closestElement = Main.holdables.get(i);
			}
		}
		return closestElement;
	}

}
