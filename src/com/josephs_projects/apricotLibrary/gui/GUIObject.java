package com.josephs_projects.apricotLibrary.gui;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.interfaces.InputListener;
import com.josephs_projects.apricotLibrary.interfaces.Renderable;

/**
 * Base class for all GUIObjects. Has position, padding, margin, displayability,
 * and can be placed into a wrapper.
 *
 */
public abstract class GUIObject implements InputListener, Renderable {
	public Tuple position;
	public int padding = 7;
	public int margin = 4;
	public boolean shown = false;
	protected GUIWrapper wrapper;
	public int renderOrder;
	public Apricot apricot;
	public World world;

	/**
	 * Creates a new GUIObject at a certain position. GUIObjects need a reference to
	 * the Apricot object, and the World object they're added in.
	 */
	public GUIObject(Tuple position, Apricot apricot, World world) {
		this.position = position;
		this.apricot = apricot;
		this.world = world;
	}

	/**
	 * Changes the position <b>by reference</b>
	 */
	public void updatePosition(Tuple position) {
		this.position = position;
	}

	/**
	 * Changes whether the object is shown. Objects that are not shown 
	 */
	public void setShown(boolean shown) {
		this.shown = shown;
	}

	public void setRenderOrder(int order) {
		this.renderOrder = order;
	}

	public abstract int height();

	public abstract int width();
}
