package test.beings.plants;

import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.beings.Being;

public abstract class Plant extends Being{
	
	public Plant(Tuple position) {
		super(position);
	}
	
	public abstract void tick();
	public abstract void render(Render r);
	public abstract void input();
	public abstract void remove();
	
	public void grow() {
		// Reject if adult, already grown
		if (growthStage == Being.GrowthStage.ADULT)
			return;
		
		// Reject if not the right time
		if((birthTick - Registrar.ticks) % 2000 != 0) 
			return;
		
		// Making sure plant has enough water
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
}
