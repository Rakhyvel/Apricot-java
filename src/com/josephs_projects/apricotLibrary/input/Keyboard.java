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
	public boolean shiftDown, ctrlDown;

	public Keyboard(Apricot apricot) {
		apricot.canvas.addKeyListener(this);
		this.apricot = apricot;
		this.apricot.canvas.setFocusTraversalKeysEnabled(false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysDown.add(Integer.valueOf((int) e.getKeyCode()));
		lastKey = e.getKeyChar();
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			lastKey = KeyEvent.VK_LEFT + 255;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			lastKey = KeyEvent.VK_RIGHT + 255;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			lastKey = KeyEvent.VK_UP + 255;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			lastKey = KeyEvent.VK_DOWN + 255;
		}
		if(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			lastKey = KeyEvent.VK_PAGE_DOWN + 255;
		}
		if(e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			lastKey = KeyEvent.VK_PAGE_UP + 255;
		}
		ctrlDown = e.isControlDown();
		shiftDown = e.isShiftDown();
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