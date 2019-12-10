package test;

import java.util.ArrayList;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.SpriteSheet;

import test.beings.plants.BerryBush;
import test.holdables.Berry;
import test.holdables.Holdable;
import test.holdables.Shovel;

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
 */

public class Main {
	public static SpriteSheet spritesheet = new SpriteSheet("/res/spritesheet.png", 256);
	public static Player player;	
	public static ArrayList<Holdable> holdables = new ArrayList<>();
	public static Registrar r;
	public static Terrain terrain;
	
	public Main() {
		r = new Registrar("Test RPG Game", 13 * 64, 7 * 64);
		terrain = new Terrain();
		player = new Player();
		r.addElement(terrain);
		for (int i = 0; i < 1; i++) {
			Tuple position = new Tuple(514, 205);
			r.addElement(new BerryBush(position));
			r.addElement(new Berry(new Tuple(position)));
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
			if(point == r.getElement(i).getPosition())
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
			if(point == holdables.get(i).getPosition())
				continue;
			if (tempDist < closestDistance) {
				closestDistance = tempDist;
				closestHoldable = holdables.get(i);
			}
		}
		return closestHoldable;
	}
}
