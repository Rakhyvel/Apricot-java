package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

public class Time implements Element {
	public int year = 1000;
	public int month = 5;
	public int day = 0;
	public int hour = 6;

	@Override
	public void tick() {
		if (Registrar.ticks % 6000 == 0)
			hour++;
		
		if (hour == 24) {
			hour = 0;
			day++;
		}
		
		if (day == 7) {
			day = 0;
			month++;
		}
		if(month == 12) {
			month = 0;
			year++;
		}
	}

	@Override
	public void render(Render r) {
	}

	@Override
	public void input() {
	}

	@Override
	public void remove() {
	}

	@Override
	public Tuple getPosition() {
		return new Tuple(0,0);
	}

	@Override
	public Element setPosition(Tuple position) {
		return null;
	}

	@Override
	public Element clone() {
		return null;
	}
	
	public String getMonthName() {
		switch(month) {
		case 0:
			return "January";
		case 1:
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "June";
		case 6:
			return "July";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "October";
		case 10:
			return "November";
		case 11:
			return "December";
		default:
			return "";
		}
	}
	
	public String getDayName() {
		switch(day) {
		case 0:
			return "Monday";
		case 1:
			return "Tuesday";
		case 2:
			return "Wednesday";
		case 3:
			return "Thursday";
		case 4:
			return "Friday";
		case 5:
			return "Saturday";
		case 6:
			return "Sunday";
		default:
			return "";
		}
	}
}
