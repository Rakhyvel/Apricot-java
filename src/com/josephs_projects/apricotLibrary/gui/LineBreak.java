package com.josephs_projects.apricotLibrary.gui;

import java.awt.Graphics2D;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class LineBreak extends GUIObject {

	public LineBreak(Apricot apricot, World world) {
		super(new Tuple(), apricot, world);
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
		return 0;
	}

	@Override
	public void render(Graphics2D arg0) {
	}

	@Override
	public int height() {
		if(!shown)
			return 0;
		return (int)(apricot.height() - position.y) + 1;
	}

	@Override
	public int width() {
		return 0;
	}

}
