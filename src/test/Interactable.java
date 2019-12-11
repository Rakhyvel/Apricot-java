package test;

import com.josephs_projects.library.Tuple;

import test.holdables.Holdable;

/**
 * Interactable is meant to be interacted by the player in game without having
 * to hold it. If a player holds an object to interact with it, that object
 * should interface Holdable
 * 
 * @author Joseph Shimel
 *
 */
public interface Interactable {
	void interact(Holdable hand);

	Tuple getPostition();
}
