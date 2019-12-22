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
 * X Add unadded vegetables
 * X Add unadded fruits
 * X Add unadded grains
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
 * - Buy Thomas a new laptop when this game is big
 * 
 */

public class Main {
	public static SpriteSheet spritesheet = new SpriteSheet("/res/spritesheet.png", 256);
	public static SpriteSheet food = new SpriteSheet("/res/food.png", 1024);
	public static SpriteSheet playerSprites = new SpriteSheet("/res/player.png", 256);
	public static Player player;
	public static ArrayList<Holdable> holdables = new ArrayList<>();
	public static ArrayList<Interactable> interactables = new ArrayList<>();
	public static Registrar r;
	public static Terrain terrain;
	public static int zoom = 64;
	// Must be 256 or bigger (1 << 7)
	public static int size = (1 << 10) + 1;
	public static FlintknappingWindow flintknappingWindow;

	public Main() {
		flintknappingWindow = new FlintknappingWindow();
		r = new Registrar("Space to pickup, click to interact, THOMAS", 13 * 64, 7 * 64);
		System.out.println("Generating terrain");
		terrain = new Terrain();
		player = new Player();
		r.addElement(terrain);
		r.addElement(player);
		r.addElement(new GUI());

		System.out.println("Generating trees");
		int numberOfTrees = (int)(size);
		addElement(new TreePlant(Tree.SAVANNAH), numberOfTrees);
		addElement(new TreePlant(Tree.MESQUITE), numberOfTrees);
		addElement(new TreePlant(Tree.SPRUCE), numberOfTrees);
		addElement(new TreePlant(Tree.PINE), numberOfTrees);
		addElement(new TreePlant(Tree.WILLOW), numberOfTrees);
		addElement(new TreePlant(Tree.RUBBER), numberOfTrees);
		addElement(new TreePlant(Tree.OAK), numberOfTrees);
		addElement(new TreePlant(Tree.MAPLE), numberOfTrees);
		
		System.out.println("Generating fruit");
		int numberOfFruit = size / 20;
		addElement(new FruitPlant(Fruit.CACTUS), numberOfFruit);
		addElement(new FruitPlant(Fruit.APPLE_TREE), numberOfFruit);
		addElement(new FruitPlant(Fruit.BANANA_TREE), numberOfFruit);
		addElement(new FruitPlant(Fruit.CHERRY_TREE), numberOfFruit);
		addElement(new FruitPlant(Fruit.LEMON_TREE), numberOfFruit);
		addElement(new FruitPlant(Fruit.OLIVE_TREE), numberOfFruit);
		addElement(new FruitPlant(Fruit.ORANGE_TREE), numberOfFruit);
		addElement(new FruitPlant(Fruit.PEACH_TREE), numberOfFruit);
		addElement(new FruitPlant(Fruit.PLUM_TREE), numberOfFruit);

		addElement(new FruitPlant(Fruit.BLACKBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.BLUEBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.BUNCHBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.CLOUDBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.CRANBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.ELDERBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.GOOSEBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.RASPBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.SNOWBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.STRAWBERRY_BUSH), numberOfFruit);
		addElement(new FruitPlant(Fruit.WINTERGREENBERRY_BUSH), numberOfFruit);

		System.out.println("Generating vegetables");
		int numberOfVegetables = size / 10;
		addElement(new VegetablePlant(Vegetable.CARROT), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.PEPPER), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.POTATO), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.TOMATO), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.CABBAGE), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.GREENBEAN), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.ONION), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.RUTABAGA), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.SOYBEAN), numberOfVegetables);
		addElement(new VegetablePlant(Vegetable.SQUASH), numberOfVegetables);

		System.out.println("Generating grains");
		int numberOfGrain = size / 5;
		addElement(new GrainPlant(Grain.BARLEY), numberOfGrain);
		addElement(new GrainPlant(Grain.CORN), numberOfGrain);
		addElement(new GrainPlant(Grain.RYE), numberOfGrain);
		addElement(new GrainPlant(Grain.WHEAT), numberOfGrain);
		addElement(new GrainPlant(Grain.OAT), numberOfGrain);
		addElement(new GrainPlant(Grain.RICE), numberOfGrain);

		System.out.println("Generating stones");
		addElement(new Stone(), size);
		
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
	
	public static boolean interactableExists(Tuple point) {
		for (int i = 0; i < interactables.size(); i++) {
			// Only used for walking, should be able to walk over holdables
			if (interactables.get(i) instanceof Holdable)
				continue;
			
			if(interactables.get(i).getPosition().equals(point)) {
				return true;
			}
		}
		return false;
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
