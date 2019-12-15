package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.beings.Being;
import test.holdables.VegetableObject;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class VegetablePlant extends Plant implements Element, Interactable{
	Vegetable type;
	
	public VegetablePlant(Vegetable type) {
		super(getRandomTuple());
		growthStage = Being.GrowthStage.ADULT;
		setWaterHardiness(type.waterHardiness);
		preferedTemp = type.preferedTemp;
		Main.interactables.add(this);
		this.type = type;
	}

	@Override
	public void tick() {		
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
		default:
			break;
		}
	}

	@Override
	public void input() {}

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
		if(hand == null) {
			Main.player.setHand(new VegetableObject(new Tuple(position), type));
			remove();
			return true;
		}
		return false;
	}
	
	static Tuple getRandomTuple() {
		Tuple randPoint = new Tuple(513, 205);
		do {
			int x = Registrar.rand.nextInt(1025);
			int y = Registrar.rand.nextInt(1025);
			randPoint = new Tuple(x, y);
		} while (Main.terrain.getPlot(randPoint) < 0.5 || Main.findClosestDistance(randPoint) < 1);
		return randPoint;
	}
}
