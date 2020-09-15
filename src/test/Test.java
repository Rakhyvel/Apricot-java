package test;

import java.io.IOException;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.interfaces.Tickable;
import com.josephs_projects.apricotLibrary.threed.Mesh;
import com.josephs_projects.apricotLibrary.threed.Scene;

public class Test implements Tickable {
	static Mesh cube;
	static Apricot apricot;
	static Scene scene;

	public static void main(String[] args) throws IOException {
		apricot = new Apricot("3D test", 800, 600);
		World world = new World();
		
		cube = new Mesh("src/test/cube.obj");
		
		scene = new Scene(800, 600, 50);
		scene.meshes.add(cube);
		
		world.add(scene);
		world.add(new Test());
		apricot.setWorld(world);
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
