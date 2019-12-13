package test;

import java.util.ArrayList;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.SpriteSheet;

import test.beings.plants.Fruit;
import test.beings.plants.FruitPlant;
import test.beings.plants.Vegetable;
import test.beings.plants.VegetablePlant;
import test.holdables.Shovel;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

/*
 * TODO:
 * X Implement BerryBush class
 * X Implement Berry class
 * X Implement Shovel class
 * 		X Implement hole class
 * 	    X Implement pile class
 * X Work out how digging should work
 * X Work out how planting should work
 * X Work out how growing should work
 * 
 * X Generalize BerryBush to all plants and seeds
 * X Generalize Berry to all foods and seeds
 * 
 * - Add vegetables
 * - Add trees
 * 
 * - Add sorted rendering
 */

public class Main {
	public static SpriteSheet spritesheet = new SpriteSheet("/res/spritesheet.png", 256);
	public static Player player;
	public static ArrayList<Holdable> holdables = new ArrayList<>();
	public static ArrayList<Interactable> interactables = new ArrayList<>();
	public static Registrar r;
	public static Terrain terrain;
	public static int zoom = 64;

	public Main() {
		r = new Registrar("Test RPG Game", 13 * 64, 7 * 64);
		terrain = new Terrain();
		player = new Player();
		r.addElement(terrain);
		// Blue berries
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.BLUEBERRY_BUSH));
		}
		// Cacti
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.CACTUS));
		}
		// Strawberries
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.STRAWBERRY_BUSH));
		}
		// Apples
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.APPLE_TREE));
		}
		// Cherry
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.CHERRY_TREE));
		}
		// Melon
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.MELON_VINE));
		}
		// Orange
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.ORANGE_TREE));
		}
		// Peach
		for (int i = 0; i < 500; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new FruitPlant(position, Fruit.PEACH_TREE));
		}

		// Barley
		for (int i = 0; i < 400; i++) {
			Tuple position = new Tuple(Registrar.rand.nextInt(1025), Registrar.rand.nextInt(1025));
			r.addElement(new VegetablePlant(position, Vegetable.BARLEY));
		}
		r.addElement(new Shovel());
		r.addElement(player);
		r.run();
	}

	public static void main(String args[]) {
		new Main();
	}

	public static Element findNearestElement(Tuple point) {
		Element closestElement = r.getElement(0);
		double closestDistance = point.getDist(closestElement.getPosition());
		for (int i = 0; i < r.registrySize(); i++) {
			double tempDist = r.getElement(i).getPosition().getDist(point);
			// This method will try not to return itself
			if (point == r.getElement(i).getPosition())
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closestElement = r.getElement(i);
			}
		}
		return closestElement;
	}

	public static Holdable findNearestHoldable(Tuple point) {
		Holdable closestHoldable = holdables.get(0);
		double closestDistance = point.getDist(closestHoldable.getPosition());
		for (int i = 0; i < holdables.size(); i++) {
			double tempDist = holdables.get(i).getPosition().getDist(point);
			if (point == holdables.get(i).getPosition())
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closestHoldable = holdables.get(i);
			}
		}
		return closestHoldable;
	}
}
