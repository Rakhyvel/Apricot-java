package test.beings.plants;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;

import test.Main;
import test.beings.Being;

public abstract class Plant extends Being{
	
	public Plant(Tuple position) {
		super(position);
	}
	
	public void grow() {
		// Reject if adult, already grown
		if (growthStage == Being.GrowthStage.ADULT)
			return;
		
		// Reject if not the right time
		if((Registrar.ticks - birthTick) % 200 != 199)
			return;
		
		switch (growthStage) {
		case SUBADULT:
			growthStage = Being.GrowthStage.ADULT;
			break;
		case CHILD:
			growthStage = Being.GrowthStage.SUBADULT;
			break;
		case BABY:
			growthStage = Being.GrowthStage.CHILD;
			break;
		default:
			break;
		}
	}
	
	public boolean checkUnderWater() {
		return Main.terrain.getPlot(position) <= 0.5;
	}
	
	public boolean checkBadTemp() {
		return Math.abs(Main.terrain.getTemp(position) - preferedTemp) > 20;
	}
	
	public void drinkWater() {
		// If theres more rain than the minimum, water increases
		// if rain < waterHardiness
		drink(invertedRain() / waterHardiness);
	}
	
	double invertedRain() {
		return 99 - Main.terrain.getPrecipitation(position);
	}
	
	public void dieIfBadTemp() {
		if(checkBadTemp()) {
			remove();
		}
	}
	
	public boolean dieIfRootRot() {
		return invertedRain() > (waterHardiness * 8);
	}
}
