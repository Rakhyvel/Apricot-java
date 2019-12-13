package test.interfaces;

import com.josephs_projects.library.Tuple;

public abstract interface Holdable{
	boolean pickup();
	void drop();
	void use();
	boolean isConsumed();
	Tuple getPosition();
	void remove();
}
