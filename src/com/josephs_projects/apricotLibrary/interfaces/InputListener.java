package com.josephs_projects.apricotLibrary.interfaces;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.input.InputEvent;

/**
 * InputListeners are called whenever there is an input given from either the
 * mouse or keyboard.
 * 
 * @author Joseph Shimel
 *
 */
public interface InputListener {
	void input(InputEvent e, Apricot apricot);

	void remove();
}
