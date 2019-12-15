package test.holdables.tools;

public enum Tool {
	SHOVEL_HEAD("Shovel head"), AXE_HEAD("Axe head"), KNIFE_BLADE("Knife blade"), GARBAGE("Garbage trash");
	
	String title;
	Tool(String title) {
		this.title = title;
	}
	
	public String toString() {
		return title;
	}
}
