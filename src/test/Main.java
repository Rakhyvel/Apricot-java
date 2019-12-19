package test;

import java.util.ArrayList;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.SpriteSheet;

import test.beings.plants.Fruit;
import test.beings.plants.FruitPlant;
import test.beings.plants.Grain;
import test.beings.plants.GrainPlant;
import test.beings.plants.Tree;
import test.beings.plants.TreePlant;
import test.beings.plants.Vegetable;
import test.beings.plants.VegetablePlant;
import test.flintknapping.FlintknappingWindow;
import test.holdables.tools.Shovel;
import test.holdables.tools.Stone;
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
 * X Add vegetables
 * X Add trees
 * 
 * X Add sorted rendering
 * 
 * - Add all vegetables
 * 
 * - Add interactions
 * 	    X Add log to pile type
 * 
 * X Add images for:
 *     X Foliage
 *     X Logs
 *     X Holes
 *     X Dirt piles
 *     X Log piles
 *     
 * X Foliage should grow more trees
 * 
 * X Split vegetables and grains
 *      X Add grain meal and seeds
 * - Add unadded vegetables
 * - Add unadded fruits
 * - Add unadded grains
 * - Add unadded trees perhaps
 * 
 * - Add images for all added food
 * 
 * - Add water drinking for players
 * - Add sicknesses
 * - Add warmth requirement
 * - Add rest requirement
 * 
 * - Update images for all plants, make them unique
 * - Add brush
 * 
 */

public class Main {
	public static SpriteSheet spritesheet = new SpriteSheet("/res/spritesheet.png", 256);
	public static Player player;
	public static ArrayList<Holdable> holdables = new ArrayList<>();
	public static ArrayList<Interactable> interactables = new ArrayList<>();
	public static Registrar r;
	public static Terrain terrain;
	public static int zoom = 64;
	public static FlintknappingWindow flintknappingWindow;

	public Main() {
		flintknappingWindow = new FlintknappingWindow();
		r = new Registrar("Test RPG Game", 13 * 64, 7 * 64);
		Registrar.rand.setSeed(100);
		System.out.println("Generating terrain");
		terrain = new Terrain();
		player = new Player();
		r.addElement(terrain);
		r.addElement(player);
		r.addElement(new GUI());
		Shovel shovel = (Shovel) new Shovel().setPosition(player.getPosition());
		shovel.hafted = true;
		r.addElement(shovel);
		
		System.out.println("Generating fruit");
		addElement(new FruitPlant(Fruit.BLUEBERRY_BUSH), 500);
		addElement(new FruitPlant(Fruit.CACTUS), 500);
		addElement(new FruitPlant(Fruit.STRAWBERRY_BUSH), 500);
		addElement(new FruitPlant(Fruit.APPLE_TREE), 500);
		addElement(new FruitPlant(Fruit.CHERRY_TREE), 500);
		addElement(new FruitPlant(Fruit.MELON_VINE), 500);
		addElement(new FruitPlant(Fruit.ORANGE_TREE), 500);
		addElement(new FruitPlant(Fruit.PEACH_TREE), 500);

		System.out.println("Generating vegetables");
		addElement(new VegetablePlant(Vegetable.CARROT), 500);
		addElement(new VegetablePlant(Vegetable.PEPPER), 500);
		addElement(new VegetablePlant(Vegetable.POTATO), 500);
		addElement(new VegetablePlant(Vegetable.TOMATO), 500);

		System.out.println("Generating grains");
		addElement(new GrainPlant(Grain.BARLEY), 500);
		addElement(new GrainPlant(Grain.CORN), 500);
		addElement(new GrainPlant(Grain.RYE), 500);
		addElement(new GrainPlant(Grain.WHEAT), 500);
		addElement(new GrainPlant(Grain.OAT), 500);
		addElement(new GrainPlant(Grain.RICE), 500);

		System.out.println("Generating trees");
		addElement(new TreePlant(Tree.SAVANNAH), 1000);
		addElement(new TreePlant(Tree.MESQUITE), 1000);
		addElement(new TreePlant(Tree.SPRUCE), 1000);
		addElement(new TreePlant(Tree.PINE), 1000);
		addElement(new TreePlant(Tree.WILLOW), 1000);
		addElement(new TreePlant(Tree.RUBBER), 1000);
		addElement(new TreePlant(Tree.OAK), 1000);
		addElement(new TreePlant(Tree.MAPLE), 1000);

		System.out.println("Generating stones");
		addElement(new Stone(), 1000);
		
		System.out.println(r.registrySize());
		
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

	public static double findClosestDistance(Tuple point) {
		Element closestElement = r.getElement(0);
		double closestDistance = point.getDist(closestElement.getPosition());
		for (int i = 0; i < r.registrySize(); i++) {
			Tuple tempPoint = r.getElement(i).getPosition();
			if (tempPoint == null)
				continue;

			double tempDist = tempPoint.getDist(point);
			// This method will try not to return itself
			if (point == r.getElement(i).getPosition())
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closestElement = r.getElement(i);
			}
		}
		return closestDistance;
	}

	public static Holdable findNearestHoldable(Tuple point) {
		Holdable closestHoldable = holdables.get(0);
		double closestDistance = 10000000;
		for (int i = 0; i < holdables.size(); i++) {
			Tuple tempPoint = holdables.get(i).getPosition();
			if (tempPoint == null) {
				continue;
			}
			double tempDist = tempPoint.getDist(point);
			if (point == holdables.get(i).getPosition())
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closestHoldable = holdables.get(i);
			}
		}
		return closestHoldable;
	}

	void addElement(Element e, int quantity) {
		for (int i = 0; i < quantity; i++) {
			r.addElement(e.clone());
		}
	}
}
