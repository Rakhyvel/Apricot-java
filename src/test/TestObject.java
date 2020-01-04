package test;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.graphics.Render;
import com.josephs_projects.apricotLibrary.input.InputEvent;
import com.josephs_projects.apricotLibrary.interfaces.InputListener;
import com.josephs_projects.apricotLibrary.interfaces.Renderable;
import com.josephs_projects.apricotLibrary.interfaces.Tickable;

public class TestObject implements Tickable, Renderable, InputListener {
	int alpha = 0;
	int alpha2 = 0;
	
	public TestObject() {
	}

	@Override
	public void tick() {
		alpha = (int) (Math.sin(Main.apricot.ticks * 0.01) * 128 + 128);
		alpha2 = (int) (Math.cos(Main.apricot.ticks * 0.01) * 128 + 128);
	}

	@Override
	public void remove() {
		
	}

	@Override
	public void render(Render r) {
		r.drawRect(0, 0, 800, 500, Apricot.color.argb(255, 0, 60, 230));
		r.drawRect(100, 100, 100, 100, Apricot.color.argb(alpha, 255, 128, 0));
		r.drawRect(150, 150, 100, 100, Apricot.color.argb(alpha2, 255, 128, 0));
	}

	@Override
	public int getRenderOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Tuple getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Tuple position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void input(InputEvent e, Apricot apricot) {
		// TODO Auto-generated method stub
		
	}

}
