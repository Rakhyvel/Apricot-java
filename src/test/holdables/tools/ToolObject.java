package test.holdables.tools;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;

import test.Main;
import test.holdables.Stick;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public abstract class ToolObject implements Interactable{
	protected int durability = 3;
	public boolean hafted = false;
	int[] haftedImg;
	int[] headImg;
	
	public ToolObject() {
		Main.interactables.add(this);
	}
	
	public void perhapsBreak() {
		if(Registrar.rand.nextInt(6) == 0) {
			durability--;
		}
	}

	@Override
	public boolean interact(Holdable hand) {
		if(hand instanceof Stick) {
			if(hafted)
				return false;
			hafted = true;
			hand.drop();
			((Stick) hand).setPosition(Stone.getRandomTuple());
			Main.player.setHand(null);
			return true;
		}
		return false;
	}

	@Override
	public abstract Tuple getPosition();
}
