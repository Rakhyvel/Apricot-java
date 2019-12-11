package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Tree {
	SAVANNAH("", 2, 72), MESQUITE("", 2, 72), 
	SPRUCE("", 2, 72), PINE("", 2, 72), 
	WILLOW("", 2, 72), RUBBER("", 2, 72),
	OAK("", 2, 72), MAPLE("", 2, 72);
	public double waterHardiness;
	public int preferedTemp;
	public int[] babyImage;
	public int[] childImage;
	public int[] subAdultImage;
	public int[] adultImage;
	public int[] pregnantImage;

	Tree(String imagePath, double waterHardiness, int preferedTemp) {
		this.waterHardiness = waterHardiness;
		this.preferedTemp = preferedTemp;
		babyImage = Image.loadImage("/res/plants/" + imagePath + "/baby.png");
		childImage = Image.loadImage("/res/plants/" + imagePath + "/child.png");
		subAdultImage = Image.loadImage("/res/plants/" + imagePath + "/subadult.png");
		adultImage = Image.loadImage("/res/plants/" + imagePath + "/adult.png");
		pregnantImage = Image.loadImage("/res/plants/" + imagePath + "/pregnant.png");
	}
}
