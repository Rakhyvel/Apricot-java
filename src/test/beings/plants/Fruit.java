package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Fruit {
	// Dry-hot
	PEACH_TREE("peach", 5, 75), 
	CACTUS("cactus", 10, 90),

	// Dry-cool
	BLUEBERRY_BUSH("blueberry", 40, 68),
	CHERRY_TREE("cherry", 20, 40),

	// Wet-hot
	ORANGE_TREE("orange", 45, 90), 
	MELON_VINE("melon", 40, 80),

	// Wet-cool
	APPLE_TREE("apple", 25, 45),
	STRAWBERRY_BUSH("strawberry", 40, 80);

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
