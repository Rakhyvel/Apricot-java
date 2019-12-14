package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.beings.Being;

public class TreePlant extends Plant implements Element {
	Tree type;

	public TreePlant(Tree type) {
		super(getRandomTuple());
		growthStage = Being.GrowthStage.ADULT;
		setWaterHardiness(type.waterHardiness);
		preferedTemp = type.preferedTemp;
		this.type = type;
	}

	@Override
	public void tick() {
		grow();
		decayHunger();
		drinkWater();
		dieIfDehydrated();
		dieIfBadTemp();
		dieIfRootRot();
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

		x = (int) position.getX() - Main.player.getX();
		y = (int) position.getY() - Main.player.getY();
		r.drawRect(x, y, 64, 64, 255 << 24 | 255 << 16);
	}

	@Override
	public void input() {
	}

	public void remove() {
		Main.r.removeElement(this);
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
}
