package test;

import java.awt.Color;
import java.io.IOException;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.gui.Button;
import com.josephs_projects.apricotLibrary.gui.ColorScheme;
import com.josephs_projects.apricotLibrary.gui.GUIWrapper;
import com.josephs_projects.apricotLibrary.interfaces.Tickable;
import com.josephs_projects.apricotLibrary.threed.Mesh;
import com.josephs_projects.apricotLibrary.threed.Scene;

public class Test implements Tickable {
	static Mesh cube;
	static Apricot apricot;
	static World world;
	static Scene scene;
	public static final ColorScheme colorScheme = new ColorScheme(new Color(40, 40, 40, 180), new Color(250, 250, 250),
			new Color(128, 128, 128, 180), new Color(250, 250, 250), new Color(128, 128, 128), new Color(211, 86, 64), new Color(40, 40, 40, 180));

	static GUIWrapper wrapper;
	static Button button;
	static Button button2;
	
	public static void main(String[] args) throws IOException {
		apricot = new Apricot("3D test", 800, 600);
		world = new World();
		
		cube = new Mesh("src/test/cube.obj");
		
		scene = new Scene(800, 600, 50);
		scene.meshes.add(cube);
		
		world.add(scene);
		world.add(new Test());
		apricot.setWorld(world);
		wrapper = new GUIWrapper(new Tuple(0, 0), 1, colorScheme, apricot, world);
		button = new Button("Hello, world!", 150, 50, colorScheme, apricot, world, null);
		button2 = new Button("Hello, world!", 150, 50, colorScheme, apricot, world, null);
		
		wrapper.addGUIObject(button);
		wrapper.addGUIObject(button2);
		
		apricot.start();
	}

	int count = 0;

	@Override
	public void tick() {
		scene.setPosition(Math.cos(count * 0.01) * 10, 0, Math.sin(count * 0.01) * 10);
		scene.setLookAt(0, 0, 0);
		cube.setRotateX(count * 0.05);
//		cube.setRotateY(0.7);
//		cube.setRotateZ(3);
		count++;
		apricot.frame.setTitle("FPS: " + apricot.fps);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
