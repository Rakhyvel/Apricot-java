package test;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.World;

public class Main {
	static Apricot apricot;
	static World world;
	
	public static void main(String args[]) {
		apricot = new Apricot("String", 800, 500);
		world = new World();
		apricot.setWorld(world);
		world.add(new TestObject());
		world.add(new TestObject());
		apricot.run();
	}
}
