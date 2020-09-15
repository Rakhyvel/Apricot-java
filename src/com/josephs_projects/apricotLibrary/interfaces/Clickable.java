package com.josephs_projects.apricotLibrary.interfaces;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class Clickable implements InputListener {
	public Tuple position;
	public int width, height;
	private Apricot apricot;
	public boolean shown = true;
	
	public boolean isHovered = false;
	public boolean triggerEvent = false;
	protected boolean witnessed = false;
	
	public Clickable(Tuple position, int width, int height, Apricot apricot) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.apricot = apricot;
	}
	
	@Override
	public void input(InputEvent e) {
		if(!shown) {
			return;
		}
		if(e == InputEvent.MOUSE_MOVED || e == InputEvent.MOUSE_LEFT_DOWN) {
			checkIsHovered();
		}
		if(e == InputEvent.MOUSE_LEFT_DOWN) {
			triggerEvent = isHovered;
		}
		if(e == InputEvent.MOUSE_LEFT_RELEASED) {
			witnessed = false;
			triggerEvent = false;
		}
	}
	
	public boolean checkIsHovered() {
		int diffX = (int)(position.x - apricot.mouse.position.x + width);
		int diffY = (int)(position.y - apricot.mouse.position.y + height);
		isHovered = (diffX > 0 && diffX < width) && (diffY > 0 && diffY < height);
		return isHovered;
	}

	@Override
	public void remove() {
	}
	
	public boolean witness() {
		if(witnessed)
			return false;
		
		witnessed = triggerEvent;
		return triggerEvent;
	}

}
