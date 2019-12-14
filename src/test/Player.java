package test;

import java.awt.event.KeyEvent;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.beings.Being;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class Player extends Being implements Element {
	boolean pickupDown = false;
	boolean usedDown = false;
	Holdable hand = null;

	public Player() {
		super(getRandomTuple());
		target = new Tuple(position);
	}

	@Override
	public void tick() {
		move();
		decayHunger();
		awakeTicks++;

		if (Registrar.keyboard.keyDown(KeyEvent.VK_SPACE)) {
			pickupDown = true;
		} else if (pickupDown) {
			pickupDown = false;
			pickup();
		}

		if (Registrar.mouse.getMouseLeftDown()) {
			usedDown = true;
		} else if (usedDown) {
			usedDown = false;
			// Holdable's use() method is used for things like food and shovels, where there
			// is no interactable to be used.
			// Most holdables probably won't use it.
			if (hand != null) {
				hand.use();
				if (hand.isConsumed()) {
					hand = null;
				}
			}
			
			interact();
		}

		keyMove();
	}

	@Override
	public void render(Render r) {
		r.drawRect(6 * 64 - 3, 7 * 32 - 64, 64, 64, 255 << 24 | 255 << 16);
	}

	@Override
	public void input() {}

	@Override
	public void remove() {
		// Sorry can't do that :)
		// TODO: Make it so you can
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

	public int getX() {
		return (int) ((position.getX() - 6) * 64);
	}

	public int getY() {
		return (int) ((position.getY() - 3) * 64);
	}

	void pickup() {
		Holdable closest = Main.findNearestHoldable(position);

		// Hand is not full, there is nothing on the ground (to pickup)
		if (closest == null)
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

		System.out.println(hand);
	}

	void drop() {
		if (hand != null) {
			hand.drop();
		}
		hand = null;
	}

	public void eat(double amount, Hunger hunger) {
		amount += 1;
		System.out.println("Nom nom " + hungers[hunger.ordinal()] + " hunger after " + amount);
		hungers[hunger.ordinal()] = Math.min(1, hungers[hunger.ordinal()] * amount);
	}

	void interact() {
		if(Main.interactables.size() == 0)
			return;
		
		Interactable interactable = Main.interactables.get(0);
		double closestDistance = 1000000000;
		for (int i = 0; i < Main.interactables.size(); i++) {
			Tuple tempPoint = Main.interactables.get(i).getPosition();
			if(tempPoint == null)
				continue;
			double tempDist = tempPoint.getDist(position);
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				interactable = Main.interactables.get(i);
			}
		}
		if (closestDistance >= 1)
			return;

		interactable.interact(hand);
	}

	static Tuple getRandomTuple() {
		Tuple randPoint = new Tuple(513, 205);
		do {
			int x = Registrar.rand.nextInt(685) + 170;
			int y = 205;//Registrar.rand.nextInt(685) + 170;
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) < 0.5);
		return randPoint;
	}
	
	public void setHand(Holdable h) {
		if(hand != null)
			return;
		
		h.pickup();
		hand = h;
	}
	
	public Element clone() {
		return null;
	}
}
