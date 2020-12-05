package com.josephs_projects.apricotLibrary.gui;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.interfaces.InputListener;
import com.josephs_projects.apricotLibrary.interfaces.Renderable;

public abstract class GUIObject implements InputListener, Renderable {
	public Tuple position;
	public int padding = 7;
	public int margin = 4;
	public boolean shown = false;
	protected GUIWrapper wrapper;
	public int renderOrder;
	public Apricot apricot;
	public World world;

	public GUIObject(Tuple position, Apricot apricot, World world) {
		this.position = position;
		this.apricot = apricot;
		this.world = world;
	}

	public void updatePosition(Tuple position) {
		this.position = position;
	}

	public void setShown(boolean shown) {
		this.shown = shown;
	}

	public void setRenderOrder(int order) {
		this.renderOrder = order;
	}

	public abstract int height();

	public abstract int width();
}

