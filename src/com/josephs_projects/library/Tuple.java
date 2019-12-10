package com.josephs_projects.library;

public class Tuple {
    private double x;
    private double y;
    public Tuple(){
        this.x = 0;
        this.y = 0;
    }

    public Tuple(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Tuple(Tuple t){
        this.x = t.x;
        this.y = t.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Tuple addTuple(Tuple t){
        return new Tuple(t.x + this.x, t.y + this.y);
    }

    public Tuple subTuple(Tuple t){
        return new Tuple(this.x - t.x, this.y - t.y);
    }

    public Double getDist(Tuple t){
        return Math.sqrt((this.x - t.x) * (this.x - t.x) + (this.y - t.y) * (this.y - t.y));
    }
    
    public Double magnitude() {
    	return Math.sqrt((x * x) + (y * y));
    }

    public Tuple scalar(double scalar){
        return new Tuple(this.x * scalar, this.y * scalar);
    }
    
    @Override
    public String toString() {
    	return "(" + x + ", " + y +")";
    }
    
    @Override
    public boolean equals(Object o) {
    	if(o == null)
    		return false;
    	
    	if (o instanceof Tuple) {
    		Tuple t = (Tuple)o;
    		return (x == t.x) && (y == t.y);
    	}
    	
    	return false;
    }

	public Tuple normalize() {
		return new Tuple(x/magnitude(), y/magnitude());
	}
}
