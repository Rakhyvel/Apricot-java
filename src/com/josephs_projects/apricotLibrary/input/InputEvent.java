package com.josephs_projects.apricotLibrary.input;

/**
 * InputEvent tells InputListeners which input event has ocurred so they can react accordingly
 * 
 * @author Joseph Shimel
 *
 */
public enum InputEvent {
	// Keyboard events
	KEY_PRESSED, KEY_RELEASED,

	// Mouse events
	MOUSE_LEFT_DOWN, MOUSE_RIGHT_DOWN, MOUSE_LEFT_RELEASED, MOUSE_RIGHT_RELEASED, MOUSE_MOVED, MOUSE_DRAGGED,
	
	// Mousewheel event
	MOUSEWHEEL_MOVED,
	
	// Application events
	WORLD_CHANGE
}
