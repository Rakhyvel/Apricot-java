package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

public class GUI implements Element{

	@Override
	public void tick() {}

	@Override
	public void render(Render r) {
		r.drawRect(-1, 7 * 64 - 107, 100, 100, 180 << 24 | 86 << 16 | 86 << 8 | 86);
		r.drawRect(20, 20, 300, 20, 255 << 24);
		r.drawRect(20, 20, (int)(300 * Main.player.hungerTimer / 504000), 20, 255 << 24 | 255 << 8);
	}

	@Override
	public void input() {}

	@Override
	public void remove() {
		// Cannot do
	}

	@Override
	public Tuple getPosition() {
		return new Tuple(0, 2000);
	}

	@Override
	public Element setPosition(Tuple position) {
		return null;
	}

	@Override
	public Element clone() {
		return null;
	}
	
}
