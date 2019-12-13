package test.holdables;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.Player;
import test.beings.plants.Vegetable;
import test.interfaces.Holdable;

/**
 * Represents the actual object players can eat
 * 
 * @author Joseph Shimel
 *
 */
public class VegetableObject implements Element, Holdable {
	Tuple position;
	boolean held = false;
	int[] image = Main.spritesheet.getSubset(1, 0, 64);
	boolean onBush = true;
	double decay = 1;
	Vegetable type;

	public VegetableObject(Tuple position, Vegetable type) {
		this.position = position;
		Main.holdables.add(this);
		this.type = type;
	}

	@Override
	public void tick() {
		if (decay < 0.01) {
			remove();
		}
	}

	@Override
	public void render(Render r) {
		if (!held) {
			int x = (int) position.getX() * 64 - Main.player.getX() + 32;
			int y = (int) position.getY() * 64 - Main.player.getY() + 32;
			r.drawImage(x, y, 64, image, 1, 0);
		}
	}

	@Override
	public void input() {}

	@Override
	public Tuple getPosition() {
		return position;
	}

	@Override
	public boolean pickup() {
		held = true;
		position.setX(-100);
		position.setY(-100);
		return true;
	}

	@Override
	public void drop() {
		held = false;
		position.setX(Main.player.getPosition().getX());
		position.setY(Main.player.getPosition().getY());
	}

	@Override
	public void use() {
		Main.player.eat(decay, Player.Hunger.VEGETABLE);
	}

	@Override
	public boolean isConsumed() {
		return true;
	}

	public void remove() {
		Main.holdables.remove(this);
		Main.r.removeElement(this);
	}
}
