package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Tree {
	SAVANNAH("palm", 3, 110), MESQUITE("mesquite", 3, 90), 
	SPRUCE("spruce", 3, 30), PINE("pine", 3, 50), 
	WILLOW("willow", 1.5, 90), RUBBER("rubber", 1.5, 110),
	OAK("oak", 1.5, 50), MAPLE("maple", 1.5, 30);
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
