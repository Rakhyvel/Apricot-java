package test;

import java.awt.event.KeyEvent;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.animals.Being;
import test.objects.Holdable;

public class Player extends Being {
	boolean pickupDown = false;
	boolean usedDown = false;
	
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

	public Player() {
	}

	@Override
	public void tick() {
		move();
		keyMove();
		decayHunger();
		awakeTicks++;
		
		if(Registrar.keyboard.keyDown(KeyEvent.VK_SPACE)) {
			pickupDown = true;
		} else if(pickupDown) {
			pickupDown = false;
			pickup();
		}
		
		if(Registrar.mouse.getMouseLeftDown()) {
			usedDown = true;
		} else if (usedDown) {
			usedDown = false;
			if(hand != null)
				hand.use();
		}
	}

	@Override
	public void render(Render r) {
		r.drawRect(6 * 64 - 3, 7 * 32 - 32, 64, 64, 255 << 24 | 255 << 16);
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub

	}
	
	void keyMove() {
		if (Registrar.keyboard.keyDown(KeyEvent.VK_W) && position.getY() == target.getY()) {
			target.setY(target.getY() - 1);
		} 
		if (Registrar.keyboard.keyDown(KeyEvent.VK_S) && position.getY() == target.getY()) {
			target.setY(target.getY() + 1);
		}

		if (Registrar.keyboard.keyDown(KeyEvent.VK_A) && position.getX() == target.getX()) {
			target.setX(target.getX() - 1);
		} 
		if (Registrar.keyboard.keyDown(KeyEvent.VK_D) && position.getX() == target.getX()) {
			target.setX(target.getX() + 1);
		}
	}
	
	public Tuple getPosition() {
		return position;
	}
	
	public int getX() {
		return (int) (position.getX() * 64);
	}
	
	public int getY() {
		return (int) (position.getY() * 64);
	}
	
	void pickup() {
		Holdable closest = null;
		double closestDist = 1;
		for (int i = 0; i < Main.holdables.size(); i++) {
			double tempDist = Main.holdables.get(i).getPosition().getDist(position.addTuple(new Tuple(6,3)));
			if (tempDist < closestDist) {
				closestDist = tempDist;
				closest = Main.holdables.get(i);
			}
		}
		if (hand != null && closest != null)
			return;
		
		if (hand != null) {
			drop();
			return;
		}
		
		if(closest == null)
			return;
		
		hand = closest;
		closest.pickup();
	}
	
	void drop() {
		if(hand != null) {
			hand.drop();
		}
		hand = null;
	}
	
	public void eat(double amount, Hunger hunger) {
		System.out.println("Nom nom " + hungers[hunger.ordinal()]);
		hungers[hunger.ordinal()] = Math.min(1, hungers[hunger.ordinal()] * amount);
	}
}
