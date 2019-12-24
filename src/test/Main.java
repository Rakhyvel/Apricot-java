package test;

import java.util.ArrayList;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Font;
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
 * X Add images for all added food
 * 
 * X Add water drinking for players
 * X Add nutrients
 * X Add warmth requirement
 * X Add rest requirement
 *     X Add time
 * 
 * X Change Element in library to ask for render order
 * X Add home marker
 * X Add light
 *      - Add firepit
 *      	- Make firepit emit light
 *      
 * - Add building
 *      - Watch some castle-guy videos to get ideas
 * 
 * - Add sickness
 * - Add bushes and tall grass
 * 
 * - Add pottery
 * - Flush out flintknapping and pottery
 * 
 * - Buy Thomas a new laptop when this game is big
 * 
 * Spitball{
 * - Whittling
 * - Animals
 * - Speech bubbles
 * - Health
 * }
 * 
 */

public class Main {
	public static SpriteSheet spritesheet = new SpriteSheet("/res/spritesheet.png", 256);
	public static SpriteSheet food = new SpriteSheet("/res/food.png", 1024);
	public static SpriteSheet playerSprites = new SpriteSheet("/res/player.png", 256);
	public static Font font = new Font(new SpriteSheet("/res/font16.png", 256), 16);
	public static Font font32 = new Font(new SpriteSheet("/res/font32.png", 512), 32);
	public static Player player;
	public static ArrayList<Holdable> holdables = new ArrayList<>();
	public static ArrayList<Interactable> interactables = new ArrayList<>();
	public static ArrayList<Element> lightSources = new ArrayList<>();
	public static Registrar r;
	public static Terrain terrain;
	public static Time time;
	public static Light light;
	public static int zoom = 64;
	// Must be 256 or bigger (1 << 7)
	public static int size = (1 << 9) + 1;
	public static FlintknappingWindow flintknappingWindow;

	public Main() {
		System.out.println(size);
		flintknappingWindow = new FlintknappingWindow();
		r = new Registrar("Generating world, please wait", 13 * 64, 7 * 64);
		System.out.println("Generating terrain");
		String seed = "Joseph Shimel";
		Registrar.rand.setSeed(seed.hashCode());
		terrain = new Terrain();
		player = new Player();
		time = new Time();
		light = new Light();
		r.addElement(terrain);
		r.addElement(player);
		r.addElement(time);
		r.addElement(light);
		r.addElement(new GUI());
		

		Registrar.frame.setTitle("Generating trees...");
		int numberOfTrees = (int)(size * size) / 1000;
		addElement(new TreePlant(Tree.SAVANNAH), numberOfTrees);
		addElement(new TreePlant(Tree.MESQUITE), numberOfTrees);
		addElement(new TreePlant(Tree.SPRUCE), numberOfTrees);
		addElement(new TreePlant(Tree.PINE), numberOfTrees);
		addElement(new TreePlant(Tree.WILLOW), numberOfTrees);
		addElement(new TreePlant(Tree.RUBBER), numberOfTrees);
		addElement(new TreePlant(Tree.OAK), numberOfTrees);
		addElement(new TreePlant(Tree.MAPLE), numberOfTrees);

		Registrar.frame.setTitle("Generating fruit...");
		int numberOfFruit = (size * size) / 25000;
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

		Registrar.frame.setTitle("Generating vegetables...");
		int numberOfVegetables = (size * size) / 20000;
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

		Registrar.frame.setTitle("Generating grains...");
		int numberOfGrain = (size * size) / 20000;
		addElement(new GrainPlant(Grain.BARLEY), numberOfGrain);
		addElement(new GrainPlant(Grain.CORN), numberOfGrain);
		addElement(new GrainPlant(Grain.RYE), numberOfGrain);
		addElement(new GrainPlant(Grain.WHEAT), numberOfGrain);
		addElement(new GrainPlant(Grain.OAT), numberOfGrain);
		addElement(new GrainPlant(Grain.RICE), numberOfGrain);

		Registrar.frame.setTitle("Generating stones...");
		addElement(new Stone(), size);
		
		System.out.println(r.registrySize());

		Registrar.frame.setTitle("Code name Apricot");
		r.run();
	}

	public static void main(String args[]) {
		new Main();
	}

	public static Element findNearestElement(Tuple point) {
		Element closestElement = r.getElement(0);
		double closestDistance = size;
		for (int i = 0; i < r.registrySize(); i++) {
			Tuple tempPoint = r.getElement(i).getPosition();
			if (tempPoint == null)
				continue;
			
			double tempDist = tempPoint.getCabDist(point);
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
		double closestDistance = size;
		for (int i = 0; i < r.registrySize(); i++) {
			Tuple tempPoint = r.getElement(i).getPosition();
			if (tempPoint == null)
				continue;

			double tempDist = tempPoint.getCabDist(point);
			// This method will try not to return itself
			if (point == r.getElement(i).getPosition())
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
			}
		}
		return closestDistance;
	}
	
	public static double findClosestLight(Tuple point) {
		double closestDistance = size;
		for (int i = 0; i < lightSources.size(); i++) {
			Tuple tempPoint = lightSources.get(i).getPosition();
			if (tempPoint == null)
				continue;

			double tempDist = tempPoint.getDist(point);
			// This method will try not to return itself
			if (point == tempPoint)
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
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
			double tempDist = tempPoint.getCabDist(point);
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
		r.addElement(e);
		for (int i = 0; i < quantity; i++) {
			r.addElement(e.clone());
		}
	}
}
