package com.josephs_projects.apricotLibrary.graphics3d;

import com.josephs_projects.apricotLibrary.Apricot;

public class Triangle {
	public Tuple3D[] vertices = new Tuple3D[3];
	public int color = Apricot.color.ahsv(255, 208, 0.1, 0.9);
	public int shade;
	public Tuple3D normal = new Tuple3D();
	
	public Triangle(Tuple3D[] vertices) {
		this.vertices = vertices;
	}
}
