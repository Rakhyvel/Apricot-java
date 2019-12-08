package test.animals;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;

public class Pig extends Being{
	
	public Pig() {
		position = new Tuple(Registrar.rand.nextInt(5), Registrar.rand.nextInt(5));
		speed = 1/16.0;
	}

	@Override
	public void tick() {
		if(position.equals(target) && Registrar.ticks % 15 == 0) {
			int x = Registrar.rand.nextInt(6)-2;
			int y = Registrar.rand.nextInt(6)-2;
			target = target.addTuple(new Tuple(x,y));
		}
		move();
	}

	@Override
	public void render(Render r) {
		r.drawRect((int) (position.getX() * 64) - (int) (Main.player.getPosition().getX() * 64),
					(int) (position.getY() * 64) - (int) (Main.player.getPosition().getY() * 64), 64, 64, 255 << 24 | 255 << 16 | 128 << 8 | 255);
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tuple getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
