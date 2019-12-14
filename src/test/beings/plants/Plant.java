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
		
//		Making sure plant has enough water
		if (hungers[Hunger.WATER.ordinal()] < 0.5)
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
		eat((100 - Main.terrain.getPrecipitation(position))/100.0, Hunger.WATER);
	}
	
	public void dieIfBadTemp() {
		if(checkBadTemp()) {
			remove();
		}
	}
	
	public void dieIfRootRot() {
		if((99 - Main.terrain.getPrecipitation(position)) / 35.0 + 1.5 < waterHardiness) {
			remove();
		}
	}
}
