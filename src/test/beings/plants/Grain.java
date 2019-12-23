package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

import test.Main;

public enum Grain {
	BARLEY("barley", 5, 80, 30),
	OAT("oat", 25, 70, 31), // Couldn't find much info on water needs
	RICE("rice", 12, 85, 32),
	RYE("rye", 5, 45, 33),
	CORN("corn", 40, 85, 34), 
	WHEAT("wheat", 15, 72, 35);
	
	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;
	public int[] grassImage;

	Grain(String imagePath, double waterHardiness, int preferedTemp, int foodNumber) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/grain/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/grain/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/grain/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/grain/" + imagePath + "/adult.png");
		grassImage = Main.food.getSubset((foodNumber) % 16, (foodNumber) / 16, 64);
	}
}
