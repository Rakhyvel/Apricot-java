package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Vegetable {		
	PEPPER("pepper", 35, 75), CABBAGE("cabbage", 25, 55), CARROT("carrot", 35, 65), GREENBEAN("greenbean", 20, 75)
	, ONION("onion", 30, 75), RUTABAGA("rutabaga", 45, 55), POTATO("potato", 40, 45), SOYBEAN("soybean", 5, 85)
	, SQUASH("squash", 35, 95), TOMATO("tomato", 40, 75);

	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;

	Vegetable(String imagePath, double waterHardiness, int preferedTemp) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/vegetables/" + imagePath + "/adult.png");
	}
}
