package test;

import java.awt.event.KeyEvent;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.beings.Being;
import test.holdables.Holdable;

public class Player extends Being {
	boolean pickupDown = false;
	boolean usedDown = false;
	Holdable hand = null;

	public Player() {
		super(new Tuple(0, 0));
		target = new Tuple(position);
	}

	@Override
	public void tick() {
		move();
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
			if(hand != null) {
				hand.use();
				if (hand.isConsumed()) {
					hand = null;
				}
			}
		}
		
		keyMove();
	}

	@Override
	public void render(Render r) {
		r.drawRect(6 * 64 - 3, 7 * 32 - 32, 64, 64, 255 << 24 | 255 << 16);
	}

	@Override
	public void input() {
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
	
	
	public void remove() {
		// Sorry can't do that :)
	}
	
	public Tuple getPosition() {
		return position;
	}
	
	public int getX() {
		return (int) ((position.getX() - 6) * 64);
	}
	
	public int getY() {
		return (int) ((position.getY() - 3) * 64);
	}
	
	void pickup() {
		Holdable closest = Main.findNearestHoldable(position);
		
		// Hand is not full, there is nothing on the ground (to pickup)
		if(closest == null)
			return;
		
		// Hand is full, there is nothing on the ground
		if (hand != null) {
			drop();
			return;
		}

		// There is a nearby object, but it's too far away
		if (closest.getPosition().getDist(position) >= 1)
			return;

		// Hand is not full, there is something on the ground
		if (closest.pickup())
			hand = closest;
	}
	
	void drop() {
		if(hand != null) {
			hand.drop();
		}
		hand = null;
	}
	
	public void eat(double amount, Hunger hunger) {
		System.out.println("Nom nom " + hungers[hunger.ordinal()] + " hunger after " + amount);
		hungers[hunger.ordinal()] = Math.min(1, hungers[hunger.ordinal()] * amount);
	}
}
