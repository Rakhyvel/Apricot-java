package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

public class HomeMarker implements Element {
	Tuple homePosition;
	int[] image = Image.loadImage("/res/homeIcon.png");

	@Override
	public void tick() {}

	@Override
	public void render(Render r) {
		if(homePosition == null)
			return;
		
		int x = (int) Math.max(16, Math.min(Registrar.canvas.getWidth() - 32, homePosition.getX() * 64 - Main.player.getX() + 32));
		int y = (int) Math.max(16, Math.min(Registrar.canvas.getHeight() - 32, homePosition.getY() * 64 - Main.player.getY() + 32));
		r.drawImage(x, y, 32, image, 0.5f, 0);
		
	}

	@Override
	public void input() {}

	@Override
	public void remove() {}

	@Override
	public int getRenderOrder() {
		return 3000;
	}

	@Override
	public Tuple getPosition() {
		return homePosition;
	}

	@Override
	public Element setPosition(Tuple position) {
		if(position.equals(homePosition)) {
			this.homePosition = null;
			return this;
		}
		this.homePosition = position;
		return this;
	}

	@Override
	public Element clone() {
		return null;
	}

}
