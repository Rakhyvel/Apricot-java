package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

import test.Main;

public enum Vegetable {
	PEPPER("pepper", 35, 75, 20), CABBAGE("cabbage", 25, 55, 21), CARROT("carrot", 35, 65, 22),
	GREENBEAN("greenbean", 20, 75, 23), ONION("onion", 30, 75, 24), RUTABAGA("rutabaga", 45, 55, 25),
	POTATO("potato", 40, 45, 26), SOYBEAN("soybean", 5, 85, 27), SQUASH("squash", 35, 95, 28),
	TOMATO("tomato", 40, 75, 29);

	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;
	public int[] holdableImage;

	Vegetable(String imagePath, double waterHardiness, int preferedTemp, int foodNumber) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/adult.png");
		holdableImage = Main.food.getSubset((foodNumber) % 16, (foodNumber) / 16, 64);
	}
}
