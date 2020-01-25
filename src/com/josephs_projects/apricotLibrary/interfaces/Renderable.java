package com.josephs_projects.apricotLibrary.interfaces;

import java.awt.Graphics;

/**
 * Renderables are called to draw to the screen using the Render class. They
 * often have a position, and an order.
 * 
 * @author Joseph Shimel
 *
 */
public interface Renderable {
	void render(Graphics g);

	void remove();

	int getRenderOrder();
}
