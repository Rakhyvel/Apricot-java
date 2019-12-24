package test.beings.plants;

import com.josephs_projects.library.graphics.Image;

public enum Tree {	
	SAVANNAH("palm", 40, 95), MESQUITE("mesquite", 10, 95), 
	SPRUCE("spruce", 10, 50), PINE("pine", 10, 40), 
	WILLOW("willow", 45, 90), RUBBER("rubber", 22, 100),
	OAK("oak", 15, 55), MAPLE("maple", 12, 44);
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
