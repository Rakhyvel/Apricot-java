package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Map;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

public class Terrain implements Element {
	int width = 1025;
	int height = 1025;
	Map map = new Map(width, height, 2);
	int[] mapImage;
	int[] tile = Main.spritesheet.getSubset(2, 0, 64);
	int[] water = Main.spritesheet.getSubset(0, 0, 64);
	int[][] tiles = new int[9 * 77][64 * 64];
	Tuple offset = new Tuple(0, 0);

	int xOffset = 0;
	int yOffset = 0;

	public Terrain() {
		mapImage = map.getImage();
		for (int i = 0; i < map.biomes.length; i++) {
			tiles[i] = Render.getScreenBlend(map.biomes[i], tile);
		}
	}

	@Override
	public void tick() {
	}

	public void render(Render r) {
		// Could be optimized to only iterate over tiles known to be visible, but
		// probably wouldn't help much
		int size = Main.zoom;
		r.drawRect(0, 0, 13 * 64, 7 * 64, 254 << 24);
		for (int x = (int) (Main.player.getPosition().getX() - 7); x < Main.player.getPosition().getX() + 8; x++) {
			for (int y = (int) (Main.player.getPosition().getY() - 3); y < Main.player.getPosition().getY() + 5; y++) {
				int x1 = x * size - Main.player.getX() - 32;
				int y1 = y * size - Main.player.getY() - 32;
				if (x1 < -size)
					continue;
				if (y1 < -size)
					continue;
				if (x1 - size > Registrar.canvas.getWidth())
					continue;
				if (y1 - size > Registrar.canvas.getHeight())
					continue;
				if(map.getPlot(x, y) > 0.5) {
					r.drawImage(x1, y1, size, tiles[Math.max(0, map.getColorIndex(x + y * width))], 1, 0);
				} else {
					r.drawImage(x1, y1, size, Main.spritesheet.getSubset(0, (int)((Registrar.ticks % 60) * (1/15.0)), 64), 1, 0);
				}
			}
		}
	}
	
	public void r0nder(Render r) {
		// Could be optimized to only iterate over tiles known to be visible, but
		// probably wouldn't help much
		int size = Main.zoom;
		r.drawRect(0, 0, 13 * 64, 7 * 64, 254 << 24);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int x1 = x * size - Main.player.getX() - 32;
				int y1 = y * size - Main.player.getY() - 32;
				if (x1 < -size)
					continue;
				if (y1 < -size)
					continue;
				if (x1 - size > Registrar.canvas.getWidth())
					continue;
				if (y1 - size > Registrar.canvas.getHeight())
					continue;
				r.drawRect(x1, y1, size, size, mapImage[x + y * width]);
			}
		}
	}

	@Override
	public void input() {
	}

	public Tuple getPosition() {
		// Should never be called
		return new Tuple(2000, -2000);
	}

	@Override
	public Element setPosition(Tuple position) {
		return this;
	}

	public void remove() {
		// Can't do that :)
	}

	public float getPlot(Tuple point) {
		return map.getPlot(point);
	}

	/**
	 * 
	 * @param point
	 * @return An double corresponding to the precipitation at that point ranging
	 *         from 0-99, 0 being moist, 99 being dry.
	 */
	public double getPrecipitation(Tuple point) {
		return map.getPrecipitation(point);
	}

	public int getTemp(Tuple point) {
		return (int) map.getTemp(point);
	}

	public Element clone() {
		return null;
	}
}
