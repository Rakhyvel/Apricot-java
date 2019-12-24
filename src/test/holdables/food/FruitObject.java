package test.holdables.food;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.Player;
import test.beings.Being;
import test.beings.plants.Fruit;
import test.beings.plants.FruitPlant;
import test.interfaces.Holdable;
import test.interfaces.Plantable;

public class FruitObject implements Element, Holdable, Plantable {
	Tuple position;
	boolean held = false;
	int[] image;
	double amount = 100800;
	Fruit type;

	public FruitObject(Tuple position, Fruit type) {
		this.position = position;
		Main.holdables.add(this);
		image = type.holdableImage;
		this.type = type;
	}

	@Override
	public void tick() {
		amount -= 1.4;

		if (amount < 1) {
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
	public Tuple getPosition() {
		return position;
	}

	@Override
	public Element setPosition(Tuple position) {
		this.position = new Tuple(position);
		return this;
	}

	public Element clone() {
		return new FruitObject(new Tuple(position), type);
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
		int eatenAmount = 504000 - Main.player.hungerTimer;
		Main.player.eat(amount, Player.Hunger.FRUIT);
		amount -= eatenAmount;
	}

	@Override
	public boolean isConsumed() {
		return true;
	}

	public void sprout() {
		remove();
		FruitPlant plant = (FruitPlant) new FruitPlant(type).setPosition(position);
		plant = (FruitPlant) plant.setGrowthStage(Being.GrowthStage.BABY);
		Main.r.addElement(plant);
	}

	public void remove() {
		Main.holdables.remove(this);
		Main.r.removeElement(this);
		if (held) {
			Main.player.setHand(null);
		}
	}
	
	public int getRenderOrder() {
		return (int) position.getY() - 1;
	}
}
