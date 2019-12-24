package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.beings.Being;
import test.beings.plants.Tree;
import test.beings.plants.TreePlant;
import test.holdables.tools.Stone;
import test.interfaces.Holdable;
import test.interfaces.Plantable;

public class TreeFoliage implements Element, Holdable, Plantable {
	Tree type;
	Tuple position;
	boolean held = false;
	int[] image;
	int birthTick;
	double decay = 1;

	public TreeFoliage(Tuple position, Tree type) {
		this.position = position;
		Main.holdables.add(this);
		image = Image.loadImage("/res/plants/foliage.png");
		birthTick = Registrar.ticks;
		this.type = type;
	}

	@Override
	public void tick() {
		if(Registrar.rand.nextInt(72000) == 0 && !held) {
			sprout();
		}
		
		decay *= 0.999999;

		if (decay < 0.01) {
			remove();
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
		r.drawImage(x, y, 64, image, 1, 0);
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {
		Main.r.removeElement(this);
		Main.holdables.remove(this);
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
	public void sprout() {
		remove();
		
		if(nearestTreeDistance() < 2)
			return;
		
		TreePlant plant = (TreePlant) new TreePlant(position, type);
		plant = (TreePlant) plant.setGrowthStage(Being.GrowthStage.BABY);
		Main.r.addElement(plant);		
	}
	
	double nearestTreeDistance() {
		double closestDistance = 3;
		for (int i = 0; i < Main.interactables.size(); i++) {
			double tempDist = Main.interactables.get(i).getPosition().getDist(position);
			if (!(Main.interactables.get(i) instanceof TreePlant))
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
			}
		}
		return closestDistance;
	}
}
