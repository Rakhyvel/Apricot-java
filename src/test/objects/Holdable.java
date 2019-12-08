package test.objects;

import com.josephs_projects.library.Tuple;

public abstract interface Holdable{
	void pickup();
	void drop();
	void use();
	Tuple getPosition();
}
