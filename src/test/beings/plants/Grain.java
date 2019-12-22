package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Grain {
	BARLEY("barley", 5, 80),
	OAT("oat", 25, 70), // Couldn't find much info on water needs
	RICE("rice", 12, 85),
	RYE("rye", 5, 45),
	CORN("corn", 40, 85), 
	WHEAT("wheat", 15, 72);
	
	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;

	Grain(String imagePath, double waterHardiness, int preferedTemp) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/grain/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/grain/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/grain/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/grain/" + imagePath + "/adult.png");
	}
}