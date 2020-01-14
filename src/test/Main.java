package test;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.graphics.Render;
import com.josephs_projects.apricotLibrary.input.InputEvent;
import com.josephs_projects.apricotLibrary.interfaces.InputListener;
import com.josephs_projects.apricotLibrary.interfaces.Renderable;


public class Main implements InputListener, Renderable {
	static Apricot apricot;
	static World world;
	
	int[] terrainImg = new int[560 * 560];
	
	public Main() {
		Main.world.add(this);
		float[][] terrain = Apricot.noiseMap.generate(560, 1, 1);
		for(int i = 0; i < 560 * 560; i++) {
			int x = (i % 560);
			int y = (i / 560);
			terrainImg[i] = Apricot.color.grayScale(terrain[x][y]);
		}
	}
	
	public static void main(String[] args) {
		apricot = new Apricot("Test canvas", 800, 800);
		world = new World(Apricot.color.argb(255, 255, 255, 255));
		
		new Main();
		
		apricot.setWorld(world);
		apricot.start();
	}
	
	@Override
	public void render(Render r) {
		r.drawImage(400, 400, 560, terrainImg, 1, 0);
	}
	
	@Override
	public void input(InputEvent e) {
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRenderOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tuple getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Tuple position) {
		// TODO Auto-generated method stub
		
	}

}
