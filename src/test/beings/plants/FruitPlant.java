package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.Player;
import test.beings.Being;
import test.holdables.FruitObject;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

/**
 * Fruit plant grow fruit. They are plants that grow from fruit.
 * 
 * @author Joseph Shimel
 *
 */
public class FruitPlant extends Plant implements Element, Interactable {
	public Fruit type;

	public FruitPlant(Fruit type) {
		super(getRandomTuple());
		growthStage = Being.GrowthStage.ADULT;
		setWaterHardiness(type.waterHardiness);
		preferedTemp = type.preferedTemp;
		this.type = type;
		Main.interactables.add(this);
	}

	@Override
	public void tick() {
		if ((Registrar.ticks - birthTick) % 200 == 199 && growthStage == GrowthStage.ADULT)
			growthStage = GrowthStage.PREGNANT;

		grow();
		decayHunger();
		drinkWater();
		dieIfDehydrated();
		dieIfBadTemp();
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * Main.zoom - Main.player.getX() + Main.zoom / 2;
		int y = (int) position.getY() * Main.zoom - Main.player.getY() + Main.zoom / 2 - 32;
		switch (growthStage) {
		case BABY:
			r.drawImage(x, y, 64, type.babyImage, 1, 0);
			break;
		case CHILD:
			r.drawImage(x, y, 64, type.childImage, 1, 0);
			break;
		case SUBADULT:
			r.drawImage(x, y, 64, type.subAdultImage, 1, 0);
			break;
		case ADULT:
			r.drawImage(x, y, 64, type.adultImage, 1, 0);
			break;
		case PREGNANT:
			r.drawImage(x, y, 64, type.pregnantImage, 1, 0);
			break;
		default:
			break;
		}
	}

	@Override
	public void input() {
	}

	@Override
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
		return new FruitPlant(type);
	}

	@Override
	public boolean interact(Holdable hand) {
		if (hand == null && growthStage == GrowthStage.PREGNANT) {
			FruitObject fruit = new FruitObject(new Tuple(position), type);
			Main.r.addElement(fruit);
			Main.player.setHand(fruit);
			growthStage = GrowthStage.ADULT;
			return true;
		}
		return false;
	}

	/**
	 * This function checks if there is a holdable ontop of the bush, and if not, it
	 * produces a berry
	 */
	void produceBerry() {
		if (growthStage != GrowthStage.ADULT)
			return;

		if (hungers[Hunger.WATER.ordinal()] < 0.5)
			return;

		Element nearestHoldable = Main.findNearestElement(position);
		if (nearestHoldable != null && nearestHoldable.getPosition().getDist(position) < 1
				&& !(nearestHoldable instanceof Player))
			return;

		// There is no holdable ontop of bush
		FruitObject newBerry = new FruitObject(new Tuple(position), type);
		Main.r.addElement(newBerry);
		Main.holdables.add(newBerry);
	}

	static Tuple getRandomTuple() {
		Tuple randPoint;
		do {
			int x = Registrar.rand.nextInt(1025);
			int y = Registrar.rand.nextInt(1025);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) < 0.5 || Main.findClosestDistance(randPoint) < 1);
		return randPoint;
	}
}
