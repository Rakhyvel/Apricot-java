package com.josephs_projects.apricotLibrary.gui;

import java.awt.Graphics2D;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class ProgressBar extends GUIObject {
	int width;
	int height;
	public double progress = 1.0;
	ColorScheme scheme;

	public ProgressBar(int width, int height, ColorScheme scheme, Apricot apricot, World world) {
		super(new Tuple(), apricot, world);
		this.width = width;
		this.height = height;
		this.scheme = scheme;
		world.add(this);
	}

	@Override
	public void input(InputEvent arg0) {

	}

	@Override
	public void remove() {
		world.remove(this);
	}

	@Override
	public int getRenderOrder() {
		return renderOrder;
	}

	@Override
	public void render(Graphics2D g) {
		if(!shown)
			return;
		
		g.setColor(scheme.borderColor);
		g.fillRect((int) position.x, (int) position.y, width, height);
		g.setColor(scheme.highlightColor);
		g.fillRect((int) position.x + 2, (int) position.y + 2, (int)(width * progress) - 4, height - 4);
	}

	@Override
	public int height() {
		if(!shown)
			return 0;
		return height + 2 * margin;
	}

	@Override
	public int width() {
		if(!shown)
			return 0;
		return width + 2 * margin;
	}

}
