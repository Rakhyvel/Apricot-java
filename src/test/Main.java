package test;

import com.josephs_projects.Registrar;

public class Main {
	public Main() {
		Registrar r = new Registrar("Test RPG Game", 800, 500);
		Map map = new Map();
		r.addElement(map);
		r.addElement(new Player(map));
		r.run();
	}
	
	public static void main(String args[]) {
		new Main();
	}
}
