package com.josephs_projects.library;

import com.josephs_projects.library.graphics.Render;

public interface Element {
	void tick();

	void render(Render r);

	void input();

	void remove();
	
	int getRenderOrder();

	Tuple getPosition();

	Element setPosition(Tuple position);
	
	Element clone();
}
