package test;

import java.util.ArrayList;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.graphics.SpriteSheet;

import test.animals.Pig;
import test.objects.Fruit;
import test.objects.Holdable;

public class Main {
	public static SpriteSheet spritesheet = new SpriteSheet("/res/spritesheet.png", 256);
	public static Player player;	
	public static ArrayList<Holdable> holdables = new ArrayList<>();
	
	public Main() {
		Registrar r = new Registrar("Test RPG Game", 13 * 64, 7 * 64);
		Terrain map = new Terrain();
		player = new Player();
		r.addElement(map);
		for (int i = 0; i < 10; i++) {
			r.addElement(new Fruit());
			r.addElement(new Pig());
		}
		r.addElement(player);
		r.run();
	}
	
	public static void main(String args[]) {
		new Main();
	}
}
