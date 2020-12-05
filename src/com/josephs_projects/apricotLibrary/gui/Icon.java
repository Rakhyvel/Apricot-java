package com.josephs_projects.apricotLibrary.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class Icon extends GUIObject {
	public BufferedImage image;

	public Icon(BufferedImage image, Apricot apricot, World world) {
		super(new Tuple(), apricot, world);
		this.image = image;
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
		if (image == null)
			return;
		g.drawImage(image, (int) position.x, (int) position.y, null);
	}

	@Override
	public int height() {
		if(image == null)
			return 0;
		return image.getHeight();
	}

	@Override
	public int width() {
		if(image == null)
			return 0;
		return image.getWidth();
	}

}
