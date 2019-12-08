package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Map;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

public class Terrain implements Element {
	Map map = new Map(513, 513, 2);
	int[] mapImage;
	Tuple offset = new Tuple(0,0);
	
	int xOffset = 0;
	int yOffset = 0;

	public Terrain() {
		mapImage = map.getImage();
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Render r) {
		int size = 6;
		r.drawRect(0, 0, 13 * 64, 7 * 64, 255 << 24);
		for(int i = 0; i < mapImage.length; i++) {
			r.drawRect((i%513) * size - Main.player.getX(), (i/513) * size - Main.player.getY(), size, size, mapImage[i]);
		}
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub

	}
	
	public Tuple getPosition() {
		// Should never be called
		return null;
	}

}
