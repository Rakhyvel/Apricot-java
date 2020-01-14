package com.josephs_projects.apricotLibrary.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import com.josephs_projects.apricotLibrary.Apricot;

/**
 * This class handles keyboard input
 * 
 * @author Joseph Shimel
 *
 */
public class Keyboard extends KeyAdapter {
	private final Set<Integer> keysDown = new HashSet<>();
	private final Apricot apricot;
	public char lastKey;

	public Keyboard(Apricot apricot) {
		apricot.canvas.addKeyListener(this);
		this.apricot = apricot;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysDown.add(Integer.valueOf((int) e.getKeyCode()));
		if (e.getKeyChar() < 255 && e.getKeyChar() > 0) {
			lastKey = e.getKeyChar();
		} else {
			lastKey = 0;
		}
		apricot.input(InputEvent.KEY_PRESSED);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysDown.remove(Integer.valueOf((int) e.getKeyCode()));
		apricot.input(InputEvent.KEY_RELEASED);
	}

	/**
	 * Checks to see if a key is being pressed by the user.
	 * 
	 * @param key The KeyEvent keycode of the key to check for
	 * @return Whether the key is currently being pressed
	 */
	public boolean keyDown(int key) {
		return keysDown.contains(key);
	}
}