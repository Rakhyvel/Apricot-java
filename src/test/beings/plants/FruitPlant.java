package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Interactable;
import test.Main;
import test.Player;
import test.beings.Being;
import test.holdables.FruitObject;
import test.holdables.Holdable;

/**
 * Berry Bushes grow berries. They are plants that grow from berries.
 * 
 * @author Joseph Shimel
 *
 */
public class FruitPlant extends Plant implements Interactable {	
	public Fruit type;

	public FruitPlant(Tuple position, Fruit type) {
		super(position);
		growthStage = Being.GrowthStage.ADULT;
		setWaterHardiness(type.waterHardiness);
		preferedTemp = type.preferedTemp;
		this.type = type;
		Main.interactables.add(this);
	}

	@Override
	public void tick() {
		if (checkUnderWater())
			remove();
		grow();
		decayHunger();
		drinkWater();
		dieIfDehydrated();
		dieIfBadTemp();
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * Main.zoom - Main.player.getX() + Main.zoom / 2;
		int y = (int) position.getY() * Main.zoom - Main.player.getY() + Main.zoom / 2;
		switch(growthStage) {
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
		}
	}

	@Override
	public void input() {}

	/**
	 * This function checks if there is a holdable ontop of the bush, and if not, it
	 * produces a berry
	 */
	void produceBerry() {
		if(growthStage != GrowthStage.ADULT)
			return;

		if (hungers[Hunger.WATER.ordinal()] < 0.5)
			return;

		Element nearestHoldable = Main.findNearestElement(position);
		if (nearestHoldable != null && nearestHoldable.getPosition().getDist(position) < 1 && !(nearestHoldable instanceof Player))
			return;

		// There is no holdable ontop of bush
		FruitObject newBerry = new FruitObject(new Tuple(position), type);
		Main.r.addElement(newBerry);
		Main.holdables.add(newBerry);
	}

	public void remove() {
		Main.r.removeElement(this);
		Main.interactables.remove(this);
	}

	@Override
	public void interact(Holdable hand) {
		if(hand == null) {
			produceBerry();
		}
	}

	@Override
	public Tuple getPostition() {
		return position;
	}
}
