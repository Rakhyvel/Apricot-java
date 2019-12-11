package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Fruit {
	// Dry-hot
	PEACH_TREE("peach", 3, 80), 
	CACTUS("cactus", 10000, 110),

	// Dry-cool
	BLUEBERRY_BUSH("blueberry", 3, 40),
	CHERRY_TREE("cherry", 3, 60),

	// Wet-hot
	ORANGE_TREE("orange", 2, 80), 
	MELON_VINE("melon", 1.5, 80),

	// Wet-cool
	APPLE_TREE("apple", 2, 60),
	STRAWBERRY_BUSH("strawberry", 1.5, 60);

	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;
	public int[] pregnantImage;

	Fruit(String imagePath, double waterHardiness, int preferedTemp) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/" + imagePath + "/adult.png");
		pregnantImage = Image.loadImage("/res/plants/" + imagePath + "/pregnant.png");
	}
}
