package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

import test.Main;

public enum Fruit {
	// Trees
	BANANA_TREE("banana", 40, 95, 0), 
	CHERRY_TREE("cherry", 20, 40, 1),
	APPLE_TREE("apple", 25, 45, 2),
	LEMON_TREE("lemon", 10, 85, 3),
	OLIVE_TREE("olive", 25, 75, 4),
	ORANGE_TREE("orange", 40, 95, 5), 
	PEACH_TREE("peach", 5, 75, 6), 
	PLUM_TREE("plum", 30, 45, 7), 
	CACTUS("cactus", 10, 95, 8),

	// Bushes
	BLACKBERRY_BUSH("blackberry", 17, 60, 9),
	BLUEBERRY_BUSH("blueberry", 17, 60, 10),
	BUNCHBERRY_BUSH("bunchberry", 17, 50, 11),
	CLOUDBERRY_BUSH("cloudberry", 25, 50, 12),
	CRANBERRY_BUSH("cranberry", 45, 55, 13),
	ELDERBERRY_BUSH("elderberry", 17, 60, 14),
	GOOSEBERRY_BUSH("gooseberry", 17, 60, 15),
	RASPBERRY_BUSH("raspberry", 17, 60, 16),
	SNOWBERRY_BUSH("snowberry", 17, 40, 17),
	STRAWBERRY_BUSH("strawberry", 40, 85, 18),
	WINTERGREENBERRY_BUSH("wintergreenberry", 40, 45, 19);

	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;
	public int[] pregnantImage;
	public int[] holdableImage;

	Fruit(String imagePath, double waterHardiness, int preferedTemp, int foodNumber) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/adult.png");
		pregnantImage = Image.loadImage("/res/plants/fruit/" + imagePath + "/pregnant.png");
		holdableImage = Main.food.getSubset((foodNumber) % 16, (foodNumber) / 16, 64);
	}
}
