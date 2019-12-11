package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Vegetable {
	// Dry-hot
	CORN("peach", 3, 80), PUMPKIN("cactus", 10000, 120),

	// Dry-cool
	RYE("blueberry", 3, 40), POTATOES("cherry", 3, 60),

	// Wet-hot
	BARLEY("orange", 2, 80), SQUASH("melon", 1.5, 80),

	// Wet-cool
	WHEAT("apple", 2, 60), TOMATOES("strawberry", 1.5, 60);

	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;
	public int[] pregnantImage;

	Vegetable(String imagePath, double waterHardiness, int preferedTemp) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/" + imagePath + "/adult.png");
		pregnantImage = Image.loadImage("/res/plants/" + imagePath + "/pregnant.png");
	}
}
