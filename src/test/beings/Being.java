package test.beings;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;

public abstract class Being {
	protected Tuple position = new Tuple();
	protected Tuple target = new Tuple();

	protected double speed = 1 / 16.0;
	protected int birthTick;
	protected GrowthStage growthStage;
	protected double waterHardiness = 0;
	protected int preferedTemp = 72;

	public Being(Tuple position) {
		this.position = position;
		this.target = new Tuple(target);
		birthTick = Registrar.ticks;
		setWaterHardiness(999);
	}

	public static enum Hunger {
		FRUIT, VEGETABLE, MEAT, DAIRY, WATER;
	}

	public static enum GrowthStage {
		BABY, CHILD, SUBADULT, ADULT, PREGNANT;
	}

	protected double[] hungers = { 1, // Fruit
			1, // Vegetable
			1, // Meat
			1, // Dairy
			1, // Water
	};
	protected int awakeTicks = 0;
	protected int temperature = 72;
	
	public abstract void remove();

	protected void move() {
		if (position.getX() < target.getX())
			position.setX(position.getX() + speed);

		if (position.getX() > target.getX())
			position.setX(position.getX() - speed);

		if (position.getY() < target.getY())
			position.setY(position.getY() + speed);

		if (position.getY() > target.getY())
			position.setY(position.getY() - speed);
	}

	protected void decayHunger() {
		for (int i = 0; i < hungers.length - 1; i++) {
			hungers[i] = hungers[i] * 0.9999;
		}
		hungers[Hunger.WATER.ordinal()] = hungers[Hunger.WATER.ordinal()] * waterHardiness;
	}

	public void eat(double amount, Hunger hunger) {
		hungers[hunger.ordinal()] = Math.min(1, hungers[hunger.ordinal()] * (amount + 1));
	}

	public Being setGrowthStage(Being.GrowthStage growthStage) {
		this.growthStage = growthStage;
		return this;
	}

	protected void setWaterHardiness(double waterHardiness) {
		this.waterHardiness = waterHardiness / (waterHardiness + 1);
	}

	public void dieIfStarving() {
		for (int i = 0; i < hungers.length - 1; i++) {
			if (hungers[i] < 0.1) {
				remove();
				return;
			}
		}
	}

	public void dieIfDehydrated() {
		if (hungers[Hunger.WATER.ordinal()] < 0.1) {
			remove();
		}
	}
}
