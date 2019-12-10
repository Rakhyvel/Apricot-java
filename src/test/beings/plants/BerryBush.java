package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.beings.Being;
import test.holdables.Berry;

/**
 * Berry Bushes grow berries. They are plants that grow from berries.
 * 
 * @author Joseph Shimel
 *
 */
public class BerryBush extends Plant {
	int[] image = Main.spritesheet.getSubset(0, 0, 64);

	public BerryBush(Tuple position) {
		super(position);
		growthStage = Being.GrowthStage.ADULT;
	}

	@Override
	public void tick() {
		if ((birthTick - Registrar.ticks) % 2000 == 0) {
			produceBerry();
		}
		grow();
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * 64 - Main.player.getX() + 32;
		int y = (int) position.getY() * 64 - Main.player.getY() + 32;
		r.drawImage(x, y, 64, image, 1, 0);
	}

	@Override
	public void input() {}

	/**
	 * This function checks if there is a holdable ontop of the bush, and if not, it
	 * produces a berry
	 */
	void produceBerry() {
		Element nearestHoldable = Main.findNearestElement(position);
		if (nearestHoldable != null && nearestHoldable.getPosition().getDist(position) < 1)
			return;

		// There is no holdable ontop of bush
		Berry newBerry = new Berry(new Tuple(position));
		Main.r.addElement(newBerry);
		Main.holdables.add(newBerry);
	}
	
	public void remove() {
		Main.r.removeElement(this);
	}
}
