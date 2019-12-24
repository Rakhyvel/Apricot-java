package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.holdables.food.GrainObject;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class GrainPlant extends Plant implements Element, Interactable {
	public Grain type;
	double amount = 0;

	public GrainPlant(Grain type) {
		super(getRandomTuple(type.preferedTemp));
		growthStage = GrowthStage.ADULT;
		waterHardiness = type.waterHardiness;
		preferedTemp = type.preferedTemp;
		Main.interactables.add(this);
		this.type = type;
		waterTimer = .01;
		this.growSpeed = 588000;
	}

	@Override
	public void tick() {
		grow();

		decayHunger();
		drinkWater();

		if (checkBadTemp())
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
	
	public int getRenderOrder() {
		return (int) position.getY();
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
		return new GrainPlant(type);
	}

	@Override
	public boolean interact(Holdable hand) {
		if (hand == null) {
			if (growthStage == GrowthStage.ADULT) {
				GrainObject grain = new GrainObject(new Tuple(), type, amount);
				Main.r.addElement(grain);
				Main.player.setHand(grain);
			}
			remove();
			return true;
		}
		return false;
	}

	static Tuple getRandomTuple(float preferedTemp) {
		Tuple randPoint = new Tuple(513, 205);
		do {
			int x = Registrar.rand.nextInt(Main.size);
			int y = Registrar.rand.nextInt(Main.size);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) < 0.5 || Main.findClosestDistance(randPoint) < 1
				|| Math.abs(Main.terrain.getTemp(randPoint) - preferedTemp) > 40);
		return randPoint;
	}
}
