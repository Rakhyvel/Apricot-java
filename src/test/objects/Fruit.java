package test.objects;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.Player;

public class Fruit implements Element, Holdable {
	Tuple position;
	boolean held = false;
	int color;

	public Fruit() {
		position = new Tuple(Registrar.rand.nextInt(51), Registrar.rand.nextInt(51));
		Main.holdables.add(this);
		color = 255 << 24 | Registrar.rand.nextInt(255) << 16 | Registrar.rand.nextInt(255) << 8 | Registrar.rand.nextInt(255);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Render r) {
		if (!held) {
			r.drawRect((int) (position.getX() * 64) - (int) (Main.player.getPosition().getX() * 64),
					(int) (position.getY() * 64) - (int) (Main.player.getPosition().getY() * 64), 40, 40,
					color);
		} else {
			r.drawRect(40, 300, 138, 138, color);
		}
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub

	}

	@Override
	public Tuple getPosition() {
		return position;
	}

	@Override
	public void pickup() {
		held = true;
	}

	@Override
	public void drop() {
		held = false;
		position.setX(Main.player.getPosition().getX() + 6);
		position.setY(Main.player.getPosition().getY() + 3);
	}

	@Override
	public void use() {
		Main.player.eat(2, Player.Hunger.FRUIT);
	}

}
