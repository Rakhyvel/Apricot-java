package com.josephs_projects.library.interfaces;

/**
 * Tickables are called whenever a specified amount of time has passed (Usually
 * 1/60th of a second). Useful for physics simulations or animations.
 * 
 * @author Joseph Shimel
 *
 */
public interface Tickable {
	void tick();

	void remove();

	Tickable clone();
}
