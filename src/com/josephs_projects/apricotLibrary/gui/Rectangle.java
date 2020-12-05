package com.josephs_projects.apricotLibrary.gui;

import java.awt.Graphics2D;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class Rectangle extends GUIObject {
	int width, height;
	ColorScheme scheme;
	public Rectangle(int width, int height, Apricot apricot, World world, ColorScheme scheme) {
		super(new Tuple(), apricot, world);
		world.add(this);
		this.scheme = scheme;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Graphics2D g) {
		if(!shown) {
			return;
		}
		g.setColor(scheme.borderColor);
		g.fillRect((int)position.x, (int)position.y, width, height);
	}

	@Override
	public void input(InputEvent arg0) {

	}

	@Override
	public void remove() {
		
	}

	@Override
	public int getRenderOrder() {
		return 3;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public int width() {
		return width;
	}

}
