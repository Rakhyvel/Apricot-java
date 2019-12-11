package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.Player;
import test.beings.Being;
import test.beings.plants.Fruit;
import test.beings.plants.FruitPlant;
import test.beings.plants.Plantable;

public class FruitObject implements Element, Holdable, Plantable {
	Tuple position;
	boolean held = false;
	int[] image = Main.spritesheet.getSubset(1, 0, 64);
	boolean onBush = true;
	double decay = 1;
	Fruit type;

	public FruitObject(Tuple position, Fruit type) {
		this.position = position;
		Main.holdables.add(this);
		this.type = type;
	}

	@Override
	public void tick() {
		// Assumes all berries are on bush when game is first loaded
		if (!onBush)
			decay *= 0.999;

		if (decay < 0.01) {
			remove();
		}

	}

	@Override
	public void render(Render r) {
		if (!held) {
			int x = (int) position.getX() * 64 - Main.player.getX() + 32;
			int y = (int) position.getY() * 64 - Main.player.getY() + 32;
			r.drawImage(x, y, 64, image, 1, 0);
		}
	}

	@Override
	public void input() {
	}

	@Override
	public Tuple getPosition() {
		return position;
	}

	@Override
	public boolean pickup() {
		held = true;
		onBush = false;
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
		Main.player.eat(decay, Player.Hunger.FRUIT);
	}

	@Override
	public boolean isConsumed() {
		return true;
	}

	public void sprout() {
		remove();
		FruitPlant plant = new FruitPlant(new Tuple(position), type);
		plant = (FruitPlant) plant.setGrowthStage(Being.GrowthStage.BABY);
		Main.r.addElement(plant);
	}

	public void remove() {
		Main.holdables.remove(this);
		Main.r.removeElement(this);
	}
}
