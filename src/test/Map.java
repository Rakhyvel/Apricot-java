package test;

import java.awt.Color;
import java.awt.Graphics;

import com.josephs_projects.*;

public class Map implements Element{
	
	Color[] tiles = new Color[8 * 5];
	
	public Map() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = new Color(Registrar.rand.nextInt(255), Registrar.rand.nextInt(255), Registrar.rand.nextInt(255));
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		for(int i = 0; i < tiles.length; i++) {
			g.setColor(tiles[i]);
			g.fillRect((i%8) * 100, (i/8) * 100, 100, 100);
		}
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isBlue(Tuple center) {
		center = center.scalar(1/100.0);
		Color color = tiles[(int)center.getX() + (int)center.getY() * 8];
		return color.getBlue() > color.getGreen() && color.getBlue() > color.getRed();
	}

}
