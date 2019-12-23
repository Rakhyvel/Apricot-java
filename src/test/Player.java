package test;

import java.awt.event.KeyEvent;
import java.util.HashSet;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.beings.Being;
import test.beings.plants.TreePlant;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class Player extends Being implements Element {
	boolean pickupDown = false;
	boolean usedDown = false;
	Holdable hand = null;
	int[][] images = new int[4][];
	
	int drinkTimer = 0;

	enum Facing {
		SOUTH, EAST, WEST, NORTH;
	}

	Facing facing = Facing.SOUTH;

	public Player() {
		super(getRandomTuple());
		target = new Tuple(position);
		setWaterHardiness(15634);
		for (int i = 0; i < 4; i++) {
			images[i] = Main.playerSprites.getSubset(i, 0, 64);
		}
		waterTimer = 36000;
		hungerTimer = 252000;
	}

	@Override
	public void tick() {
		move();
		decayHunger();
		awakeTicks--;

		if (Registrar.keyboard.keyDown(KeyEvent.VK_SPACE)) {
			pickupDown = true;
		} else if (pickupDown) {
			pickupDown = false;
			pickup();
			int numberOfTheseFruit = 0;
			for (int i = 0; i < Main.interactables.size(); i++) {
				if (Main.interactables.get(i) instanceof TreePlant) {
					numberOfTheseFruit++;
				}
			}
			System.out.println(numberOfTheseFruit);
		}

		if (Registrar.keyboard.keyDown(KeyEvent.VK_ENTER)) {
			usedDown = true;
			
			if (Main.terrain.getPlot(getLookAt()) <= 0.5) {
				drinkTimer++;
				if(drinkTimer == 60) {
					drink(600);
					System.out.println(waterTimer);
					drinkTimer = 0;
				}
			}			
		} else if (usedDown) {
			usedDown = false;
			if (interact())
				return;

			if (hand != null) {
				hand.use();
			}

			drinkTimer = 0;
		}

		keyMove();
	}

	@Override
	public void render(Render r) {
		r.drawImage(6 * 64 + 32, 7 * 32 - 32, 64, images[facing.ordinal()], 1, 0);
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {
		// Sorry can't do that :)
		// TODO: Make it so you can
	}

	@Override
	public Tuple getPosition() {
		return position;
	}

	public Tuple getLookAt() {
		switch (facing) {
		case NORTH:
			return position.addTuple(new Tuple(0, -1));
		case EAST:
			return position.addTuple(new Tuple(1, 0));
		case SOUTH:
			return position.addTuple(new Tuple(0, 1));
		case WEST:
			return position.addTuple(new Tuple(-1, 0));
		default:
			return null;
		}
	}

	@Override
	public Element setPosition(Tuple position) {
		this.position = new Tuple(position);
		return this;
	}

	void keyMove() {
		// I don't use look-at because its slow and allows diagonal no-clip

		if (Registrar.keyboard.keyDown(KeyEvent.VK_W) && position.getY() - target.getY() < 0.1) {
			facing = Facing.NORTH;
			Tuple newTarget = target.addTuple(new Tuple(0, -1));
			if (!Main.interactableExists(newTarget) && Main.terrain.getPlot(newTarget) >= 0.5) {
				target.setY(target.getY() - 1);
				setSpeed();
			}
		}

		if (Registrar.keyboard.keyDown(KeyEvent.VK_S) && Math.abs(position.getY() - target.getY()) < 0.1) {
			facing = Facing.SOUTH;
			Tuple newTarget = target.addTuple(new Tuple(0, 1));
			if (!Main.interactableExists(newTarget) && Main.terrain.getPlot(newTarget) >= 0.5) {
				target.setY(target.getY() + 1);
				setSpeed();
			}
		}

		if (Registrar.keyboard.keyDown(KeyEvent.VK_A) && position.getX() - target.getX() < 0.1) {
			facing = Facing.WEST;
			Tuple newTarget = target.addTuple(new Tuple(-1, 0));
			if (!Main.interactableExists(newTarget) && Main.terrain.getPlot(newTarget) >= 0.5) {
				target.setX(target.getX() - 1);
				setSpeed();
			}
		}
		if (Registrar.keyboard.keyDown(KeyEvent.VK_D) && Math.abs(position.getX() - target.getX()) < 0.1) {
			facing = Facing.EAST;
			Tuple newTarget = target.addTuple(new Tuple(1, 0));
			if (!Main.interactableExists(newTarget) && Main.terrain.getPlot(newTarget) >= 0.5) {
				target.setX(target.getX() + 1);
				setSpeed();
			}
		}
	}

	void setSpeed() {
		if (Registrar.keyboard.keyDown(KeyEvent.VK_CONTROL)) {
			speed = 1 / 12.0;
		} else {
			speed = 1 / 16.0;
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

		// There is nothing on the ground (to pickup)
		if (closest == null)
			return;

		// Hand is full, there is nothing on the ground
		if (hand != null) {
			drop();
			return;
		}

		// There is a nearby object, but it's too far away
		if (closest.getPosition().getCabDist(position) >= 1)
			return;

		// Hand is not full, there is something on the ground
		if (closest.pickup())
			hand = closest;

	}

	void drop() {
		if (hand != null) {
			hand.drop();
		}
		hand = null;
	}

	boolean interact() {
		if (Main.interactables.size() == 0)
			return false;

		HashSet<Interactable> interactables = new HashSet<>();
		for (int i = 0; i < Main.interactables.size(); i++) {
			Tuple tempPoint = Main.interactables.get(i).getPosition();
			if (tempPoint == null)
				continue;
			double tempDist = tempPoint.getCabDist(getLookAt());
			if (tempDist < 1) {
				interactables.add(Main.interactables.get(i));
			}
		}

		boolean interacted = false;
		for (Interactable i : interactables) {
			interacted |= i.interact(hand);
		}
		return interacted;
	}

	static Tuple getRandomTuple() {
		Tuple randPoint = new Tuple(513, 205);
		do {
			int x = Registrar.rand.nextInt(Main.size);
			int y = Main.size / 3;// Registrar.rand.nextInt(Main.size);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) < 0.5);
		return randPoint;
	}

	public void setHand(Holdable h) {
		System.out.println(h);
		if (h != null) {
			h.pickup();
		}
		hand = h;
	}

	public Element clone() {
		return null;
	}
}
