package test.holdables.tools;

import com.josephs_projects.library.Registrar;

public class ToolObject {
	protected int durability = 3;
	
	public void perhapsBreak() {
		if(Registrar.rand.nextInt(10) == 0) {
			durability--;
		}
	}
}
