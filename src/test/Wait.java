package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;

public class Wait implements Element {
	int[] image = Image.loadImage("/res/wait.png");

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Render r) {
		r.drawImage(0, 0, 800, image, 1, 0);
	}

	@Override
	public void input() {
		
	}

	@Override
	public void remove() {
		
	}

	@Override
	public Tuple getPosition() {
		return new Tuple(0, -3000);
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
