package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

public class Light implements Element {

	@Override
	public void tick() {
	}

	@Override
	public void render(Render r) {
		int size = Main.zoom;
		for (int x = (int) (Main.player.getPosition().getX() - 7); x < Main.player.getPosition().getX() + 8; x++) {
			for (int y = (int) (Main.player.getPosition().getY() - 3); y < Main.player.getPosition().getY() + 5; y++) {
				int x1 = x * size - Main.player.getX();
				int y1 = y * size - Main.player.getY();
				if (x1 < -size)
					continue;
				if (y1 < -size)
					continue;
				if (x1 - size > Registrar.canvas.getWidth())
					continue;
				if (y1 - size > Registrar.canvas.getHeight())
					continue;

				r.drawRect(x1, y1, size, size, getNightColor(x, y));
			}
		}
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {

	}

	@Override
	public int getRenderOrder() {
		return 1026;
	}

	@Override
	public Tuple getPosition() {
		return null;
	}

	@Override
	public Element setPosition(Tuple position) {
		return null;
	}

	@Override
	public Element clone() {
		return null;
	}

	int getNightColor(int x, int y) {
		double alpha = 0, r = 0, g = 0, b = 0;
		double timeLightLevel = Math.max(0, Math.min(1, Math.abs(Main.time.hour - 12)/1.2 - 5)) * 100;
		double nearestLight = Main.findClosestLight(new Tuple(x, y));
		double lightLevel = Math.min(timeLightLevel, nearestLight * nearestLight * 6.0 + 20);

		alpha = (int) (.02 * lightLevel * lightLevel);
		
		r = -2.76636 * lightLevel + 280.045;
		g = 9.0384 * Math.pow(10, 162) * Math.pow(lightLevel + 1333.26, -51.3684);
		b = -0.00104448 * Math.pow(lightLevel - 55.3502, 3) + 0.922165 * (lightLevel + 60.583);
		
		r = Math.max(0, Math.min(255, r));
		g = Math.max(0, Math.min(255, g));
		b = Math.max(0, Math.min(255, b));		

		return (int) alpha << 24 | (int) r << 16 | (int) g << 8 | (int) b;
	}

}
