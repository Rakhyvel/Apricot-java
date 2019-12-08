package test.animals;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.objects.Holdable;

public abstract class Being implements Element{
	protected Tuple position = new Tuple();
	protected Tuple target = new Tuple();
	
	boolean pickupDown = false;
	boolean usedDown = false;
	
	double speed = 1/8.0;
	
	public static enum Hunger {
		FRUIT, VEGETABLE, MEAT, DAIRY, WATER;
	}
	
	double[] hungers = {1, // Fruit
						1, // Vegetable
						1, // Meat
						1, // Dairy
						1, // Water
						};
	int awakeTicks = 0;
	int temperature = 72; // TODO: Implement temperature
	Holdable hand = null;

	public abstract void tick();
	public abstract void render(Render r);
	public abstract void input();
	
	public Tuple getPosition() {
		return position;
	}
	
	protected void move() {
		if(position.getX() < target.getX())
			position.setX(position.getX() + speed);

		if(position.getX() > target.getX())
			position.setX(position.getX() - speed);
		
		if(position.getY() < target.getY())
			position.setY(position.getY() + speed);

		if(position.getY() > target.getY())
			position.setY(position.getY() - speed);
	}
	
	protected void decayHunger() {
		for(int i = 0; i < hungers.length; i++) {
			hungers[i] = hungers[i] * 0.999;
		}
	}
	
	public void eat(double amount, Hunger hunger) {
		hungers[hunger.ordinal()] = Math.min(1, hungers[hunger.ordinal()] * amount);
	}
}
