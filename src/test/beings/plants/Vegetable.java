package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Vegetable {
	// Dry-hot
	BARLEY("barley", 3, 80), TOMATOES("cactus", 10000, 120),

	// Dry-cool
	RYE("blueberry", 3, 40), POTATOES("cherry", 3, 60),

	// Wet-hot
	CORN("orange", 2, 80), PEPPER("melon", 1.5, 80),

	// Wet-cool
	WHEAT("apple", 2, 60), CARROTS("strawberry", 1.5, 30);

	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;

	Vegetable(String imagePath, double waterHardiness, int preferedTemp) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/" + imagePath + "/adult.png");
	}
}
