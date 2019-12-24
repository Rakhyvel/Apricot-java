package test.beings;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;

import test.Main;

public abstract class Being {
	protected Tuple position = new Tuple();
	protected Tuple target = new Tuple();

	protected double speed = 1 / 14.0;
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
		FRUIT(0), GRAIN(1), VEGETABLE(2), MEAT(3), DAIRY(4);
		int ordinal;

		Hunger(int ordinal) {
			this.ordinal = ordinal;
		}
	}

	public static enum GrowthStage {
		BABY, CHILD, SUBADULT, ADULT, PREGNANT;
	}

	public double[] nutrition = { 0.3, // Fruit
			1, // Grain
			1, // Vegetable
			1, // Meat
			1, // Dairy
			1, // Water
	};
	public int hungerTimer = 504000;
	public double waterTimer = 72000;
	public int awakeTicks = 96000;
	public double temperatureTimer = 0;

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
		if(position.getDist(target) < 0.1) {
			position.setX(target.getX());
			position.setY(target.getY());
		}
	}

	protected void decayHunger() {
		hungerTimer--;
		waterTimer--;
		for (int i = 0; i < nutrition.length - 1; i++) {
			nutrition[i] *= 0.999999999;
		}
	}

	public void eat(double amount, Hunger hunger) {
		// dairy/meat (0.5) > grain (0.333) > vegetable (0.25) > fruit (0.2)
		hungerTimer = (int) Math.min(504000, hungerTimer + amount);

		// 1.27 was chosen because it can fill nutrition bar in five meals from 30%,
		// which felt right
		nutrition[hunger.ordinal()] = (nutrition[hunger.ordinal()]) * 1.27;
		nutrition[hunger.ordinal()] = Math.min(1, nutrition[hunger.ordinal()]);
	}
	
	public void drink(double amount) {
		waterTimer = (int) Math.min(72000, waterTimer + amount);
	}

	public Being setGrowthStage(Being.GrowthStage growthStage) {
		this.growthStage = growthStage;
		return this;
	}

	protected void setWaterHardiness(double waterHardiness) {
		this.waterHardiness = waterHardiness / (waterHardiness + 1);
	}

	public void dieIfStarving() {
		if(hungerTimer <= 0) 
			remove();
	}

	public void dieIfDehydrated() {
		if(waterTimer <= 0)
			remove();
	}
	
	public void temperatureTimer() {
		temperatureTimer += (Main.terrain.getTemp(position) - preferedTemp) / 60.0;
	}
}
