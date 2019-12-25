package com.josephs_projects.apricotLibrary.interfaces;

import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.graphics.Render;

/**
 * Renderables are called to draw to the screen using the Render class. They
 * often have a position, and an order.
 * 
 * @author Joseph Shimel
 *
 */
public interface Renderable {
	void render(Render r);

	void remove();

	int getRenderOrder();

	Tuple getPosition();

	void setPosition(Tuple position);
	
	Renderable clone();
}
