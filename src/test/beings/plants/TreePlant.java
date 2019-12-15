package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.beings.Being;
import test.holdables.Log;
import test.holdables.Stick;
import test.holdables.TreeFoliage;
import test.holdables.tools.Axe;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class TreePlant extends Plant implements Element, Interactable {
	Tree type;
	int hits = 0;

	public TreePlant(Tree type) {
		super(getRandomTuple());
		growthStage = Being.GrowthStage.ADULT;
		setWaterHardiness(type.waterHardiness);
		preferedTemp = type.preferedTemp;
		this.type = type;
		Main.interactables.add(this);
		int numberOfSticks = (int) Math.max(0, Registrar.rand.nextInt(10) - 8);
		for (int i = 0; i < numberOfSticks; i++) {
			Tuple tempPoint = getNearTuple();
			Main.r.addElement(new Stick(tempPoint));
		}
	}
	
	public TreePlant(Tuple position, Tree type) {
		super(position);
		growthStage = Being.GrowthStage.ADULT;
		setWaterHardiness(type.waterHardiness);
		preferedTemp = type.preferedTemp;
		this.type = type;
		Main.interactables.add(this);
		int numberOfSticks = (int) Math.max(0, Registrar.rand.nextInt(10) - 8);
		for (int i = 0; i < numberOfSticks; i++) {
			Tuple tempPoint = getNearTuple();
			Main.r.addElement(new Stick(tempPoint));
		}
	}

	@Override
	public void tick() {		
		grow();
		decayHunger();
		drinkWater();
		dieIfDehydrated();
		dieIfBadTemp();
		dieIfRootRot();

		if(Registrar.ticks - birthTick > 10000 && Registrar.rand.nextInt(480000) == 0) {
			chop();
		}
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * Main.zoom - Main.player.getX() + 32;
		int y = (int) position.getY() * Main.zoom - Main.player.getY() - 128 + 32;
		switch (growthStage) {
		case BABY:
			r.drawImage(x, y, 192, type.babyImage, 1, 0);
			break;
		case CHILD:
			r.drawImage(x, y, 192, type.childImage, 1, 0);
			break;
		case SUBADULT:
			r.drawImage(x, y, 192, type.subAdultImage, 1, 0);
			break;
		case ADULT:
			r.drawImage(x, y, 192, type.adultImage, 1, 0);
			break;
		default:
			break;
		}
	}

	@Override
	public void input() {
	}

	public void remove() {
		Main.r.removeElement(this);
		Main.interactables.remove(this);
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
		return new TreePlant(type);
	}

	static Tuple getRandomTuple() {
		Tuple randPoint;
		do {
			int x = Registrar.rand.nextInt(1025);
			int y = Registrar.rand.nextInt(1025);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) < 0.5 || Main.findClosestDistance(randPoint) < 2);
		return randPoint;
	}

	@Override
	public boolean interact(Holdable hand) {
		if (hand instanceof Axe) {
			// Axe must have handle to work
			if (!((Axe) hand).hafted)
				return false;

			((Axe) hand).perhapsBreak();
			hits++;
			if (hits > 4) {
				chop();
			}
		} else if (hand == null) {
			Stick stick = new Stick(new Tuple(-100, 3000));
			Main.r.addElement(stick);
			Main.player.setHand(stick);
		}
		return false;
	}
	
	void chop() {
		int randDirection = Registrar.rand.nextInt(4);
		Tuple additivePoint = null;
		Tuple endPoint = null;
		switch (randDirection) {
		case 0:
			additivePoint = new Tuple(0, 1);
			break;
		case 1:
			additivePoint = new Tuple(1, 0);
			break;
		case 2:
			additivePoint = new Tuple(0, -1);
			break;
		case 3:
			additivePoint = new Tuple(-1, 0);
			break;
		}

		int randLength = 3;
		for (int i = 0; i < randLength; i++) {
			endPoint = additivePoint.scalar(i);
			addLog(position.addTuple(endPoint));
		}
		Main.r.addElement(new Log(position));

		endPoint = position.addTuple(endPoint);
		addFoliage(endPoint.addTuple(new Tuple(-1, -1)));
		addFoliage(endPoint.addTuple(new Tuple(-1, 0)));
		addFoliage(endPoint.addTuple(new Tuple(-1, 1)));
		addFoliage(endPoint.addTuple(new Tuple(0, -1)));
		addFoliage(endPoint.addTuple(new Tuple(0, 0)));
		addFoliage(endPoint.addTuple(new Tuple(0, 1)));
		addFoliage(endPoint.addTuple(new Tuple(1, -1)));
		addFoliage(endPoint.addTuple(new Tuple(1, 0)));
		addFoliage(endPoint.addTuple(new Tuple(1, 1)));
		remove();
	}
	
	Tuple getNearTuple() {
		int x;
		int y;
		Tuple randPoint;
		int tries = 0;
		do {
			x = (int) (position.getX() + Registrar.rand.nextInt(4) - 2);
			y = (int) (position.getY() + Registrar.rand.nextInt(4) - 2);
			x = (x == position.getX()) ? x + 1 : x;
			y = (y == position.getY()) ? y + 1 : y;
			randPoint = new Tuple(x, y);
			tries++;
		} while (tries < 1 && (Main.terrain.getPlot(randPoint) < 0.5 || Main.findClosestDistance(randPoint) == 0));
		return randPoint;
	}

	void addLog(Tuple point) {
		Element nearestElement = Main.findNearestElement(point);
		if (nearestElement.getPosition().getDist(point) > 0) {
			Main.r.addElement(new Log(point));
		}
	}

	void addFoliage(Tuple point) {
		Element nearestElement = Main.findNearestElement(point);

		if (nearestElement.getPosition().getDist(point) == 0) {
			return;
		}

		int randFoliage = Registrar.rand.nextInt(4);
		if (randFoliage == 0) {
//			Main.r.addElement(new Stick(point));
		} else if (randFoliage == 1){
			Main.r.addElement(new TreeFoliage(point, type));
		}

	}
}
