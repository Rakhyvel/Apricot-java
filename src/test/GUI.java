package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

public class GUI implements Element{

	@Override
	public void tick() {}

	@Override
	public void render(Render r) {
		// Hand square \/
		r.drawRect(-1, 7 * 64 - 107, 100, 100, 180 << 24 | 86 << 16 | 86 << 8 | 86);
		
		String feeling = "Content";
		String severity = "";
		double lowest = 0.5;
		if(Main.player.awakeTicks / 144000.0 < lowest) {
			lowest = Main.player.awakeTicks / 144000.0;
			feeling = "Tired";
		}
		if(Main.player.hungerTimer / 504000.0 < lowest) {
			lowest = Main.player.hungerTimer / 504000.0;
			feeling = "Hungry";
		}
		if(Main.player.waterTimer / 72000.0 < lowest) {
			lowest = Main.player.waterTimer / 72000.0;
			feeling = "Thirsty";
		}
		if((18000.0 - Math.abs(Main.player.temperatureTimer)) / 18000.0 < lowest) {
			lowest = (18000.0 - Math.abs(Main.player.temperatureTimer)) / 18000.0;
			if(Main.player.temperatureTimer < 0) {
				feeling = "Cold";
			} else {
				feeling = "Hot";
			}
		}
		
		if(lowest > 0.4 && lowest < 0.5) {
			severity = "A little ";
		} else if(lowest < 0.25) {
			severity = "Very ";
		}

		r.drawRect(670, 7 * 64 - 80, 150, 70, 180 << 24 | 86 << 16 | 86 << 8 | 86);
		r.drawString("Feeling", 752, 390, Main.font32, 255 << 24, true);
		r.drawString(severity + feeling, 752, 420, Main.font, 255 << 24 | 255 << 16 | 255 << 8 | 255, true);
		
		// Time
		r.drawRect(580, 0, 250, 50, 180 << 24 | 86 << 16 | 86 << 8 | 86);
		String date = Main.time.getDayName()+", "+ Main.time.getMonthName() + ", "+String.valueOf(Main.time.year);
		String temperature = String.valueOf(Main.terrain.getTemp(Main.player.getPosition())) + "°F";
		r.drawString(date, 700, 15, Main.font, 255 << 24 | 255 << 16 | 255 << 8 | 255, true);
		r.drawString(temperature, 700, 30, Main.font, 255 << 24 | 255 << 16 | 255 << 8 | 255, true);
	}
	
	void drawMeter(Render r, int y, int value, int max, int color) {
		r.drawRect(20, y, 300, 20, 255 << 24);
		r.drawRect(20, y, (int)(300 * value / max), 20, color);
	}

	@Override
	public void input() {}

	@Override
	public void remove() {
		// Cannot do
	}
	
	public int getRenderOrder() {
		return 2000;
	}

	@Override
	public Tuple getPosition() {
		return null;
	}

	@Override
	public Element setPosition(Tuple position) {
		return null;
	}

	@Override
	public Element clone() {
		return null;
	}
	
}
