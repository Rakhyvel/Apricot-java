package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Fruit {
	// Trees
	BANANA_TREE("banana", 45, 85), 
	CHERRY_TREE("cherry", 20, 40),
	APPLE_TREE("apple", 25, 45),
	LEMON_TREE("lemon", 10, 85),
	OLIVE_TREE("olive", 25, 75),
	ORANGE_TREE("orange", 45, 90), 
	PEACH_TREE("peach", 5, 75), 
	PLUM_TREE("plum", 30, 45), 
	CACTUS("cactus", 10, 90),

	// Bushes
	BLACKBERRY_BUSH("blackberry", 17, 60),
	BLUEBERRY_BUSH("blueberry", 17, 60),
	BUNCHBERRY_BUSH("bunchberry", 17, 50),
	CLOUDBERRY_BUSH("cloudberry", 25, 50),
	CRANBERRY_BUSH("cranberry", 45, 55),
	ELDERBERRY_BUSH("elderberry", 17, 60),
	GOOSEBERRY_BUSH("gooseberry", 17, 60),
	RASPBERRY_BUSH("raspberry", 17, 60),
	SNOWBERRY_BUSH("snowberry", 17, 40),
	STRAWBERRY_BUSH("strawberry", 40, 80),
	WINTERGREENBERRY_BUSH("wintergreenberry", 40, 50);

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
		babyImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/adult.png");
		pregnantImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/pregnant.png");
	}
}
