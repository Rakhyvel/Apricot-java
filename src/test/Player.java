package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.josephs_projects.Element;
import com.josephs_projects.Registrar;
import com.josephs_projects.Tuple;

public class Player implements Element{
	Tuple position = new Tuple(400, 250);
	Tuple velocity = new Tuple(0,0);
	Map map;
	
	public Player(Map map) {
		this.map = map;
	}

	@Override
	public void tick() {
		position = position.addTuple(velocity);
		if (Registrar.keyboard.keyDown(KeyEvent.VK_W)) {
			velocity.setY(-5);
		} else if (Registrar.keyboard.keyDown(KeyEvent.VK_S)) {
			velocity.setY(5);
		} else {
			velocity.setY(0);
		}
		
		if (Registrar.keyboard.keyDown(KeyEvent.VK_A)) {
			velocity.setX(-5);
		} else if (Registrar.keyboard.keyDown(KeyEvent.VK_D)) {
			velocity.setX(5);
		} else {
			velocity.setX(0);
		}
		
		System.out.println(map.isBlue(position));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect((int)position.getX() - 25, (int)position.getY() - 25, 50, 50);
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
		
	}
	
}
