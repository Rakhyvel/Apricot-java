package test.holdables.food;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.Player;
import test.beings.Being;
import test.beings.plants.Grain;
import test.beings.plants.GrainPlant;
import test.holdables.tools.Knife;
import test.interfaces.Holdable;
import test.interfaces.Interactable;
import test.interfaces.Plantable;

public class GrainObject implements Element, Holdable, Interactable, Plantable {
	Tuple position;
	boolean held = false;
	int[] image = Main.spritesheet.getSubset(1, 0, 64);
	boolean onBush = true;
	double decay = 1;
	Grain type;
	// Can thresh by pouring into bowl, then another bowl, or using knife.
	// Can plant either threshed or not
	// Can feed to livestock either threshed or not
	// Can only eat if threshed.
	boolean threshed = false;

	public GrainObject(Tuple position, Grain type) {
		this.position = position;
		Main.holdables.add(this);
		Main.interactables.add(this);
		this.type = type;
	}

	@Override
	public void tick() {
		decay *= 0.99999;

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
			y = 7 * 64 - 106;
		}
		r.drawImage(x, y, 64, image, 1, 0);
	}

	@Override
	public void input() {
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
		if(threshed) {
			Main.player.eat(decay * 504000 * 0.333, Player.Hunger.GRAIN);
		} else {
			System.out.println("Yuck! That hasn't been threshed! Feed that to the pigs!");
		}
	}

	@Override
	public boolean isConsumed() {
		return true;
	}

	public void remove() {
		Main.holdables.remove(this);
		Main.r.removeElement(this);
		if (held) {
			Main.player.setHand(null);
		}
		Main.interactables.remove(this);
	}

	public Element clone() {
		return new GrainObject(new Tuple(position), type);
	}

	@Override
	public boolean interact(Holdable hand) {
		if (hand instanceof Knife) {
			threshed = true;
			return true;
		}
		return false;
	}

	@Override
	public void sprout() {
		remove();
		GrainPlant plant = (GrainPlant) new GrainPlant(type).setPosition(position);
		plant = (GrainPlant) plant.setGrowthStage(Being.GrowthStage.BABY);
		Main.r.addElement(plant);
	}

}