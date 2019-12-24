package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.holdables.food.VegetableObject;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class VegetablePlant extends Plant implements Element, Interactable {
	public Vegetable type;
	double amount = 0;

	public VegetablePlant(Vegetable type) {
		super(getRandomTuple());
		growthStage = GrowthStage.BABY;
		waterHardiness = type.waterHardiness;
		preferedTemp = type.preferedTemp;
		Main.interactables.add(this);
		this.type = type;
		waterTimer = 5200;
		this.growSpeed = 588000;
	}

	@Override
	public void tick() {
		grow();

		decayHunger();
		drinkWater();

		if (checkBadTemp())
			remove();

		if (dieIfRootRot())
			remove();

		dieIfDehydrated();

		if (!Main.r.registryContains((Element) this))
			remove();

		if (growthStage != GrowthStage.ADULT) {
			amount += (4 / 3.0) * Main.terrain.getNutrient(type.nutrient, position) / 1764000.0;
			Main.terrain.depleteNutrient(type.nutrient, position);
		}
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
		return new VegetablePlant(type);
	}

	@Override
	public boolean interact(Holdable hand) {
		if (hand == null) {
			if (growthStage == GrowthStage.ADULT) {
				VegetableObject vegetable = new VegetableObject(new Tuple(), type, amount);
				Main.r.addElement(vegetable);
				Main.player.setHand(vegetable);
			}
			remove();
			return true;
		}
		return false;
	}

	static Tuple getRandomTuple() {
		Tuple randPoint;
		do {
			int x = Registrar.rand.nextInt(Main.size);
			int y = Registrar.rand.nextInt(Main.size);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) <= 0.5 || Main.findClosestDistance(randPoint) < 1);
		return randPoint;
	}
}
