package com.josephs_projects.apricotLibrary.graphics3d;

import java.util.ArrayList;

public class Mesh {
	public ArrayList<Triangle> tris = new ArrayList<>();
	
	public Tuple3D position = new Tuple3D();
	public Tuple3D target = new Tuple3D();
	public Tuple3D up = new Tuple3D(0, 1, 0);

	public Mesh(ArrayList<Triangle> tris) {
		this.tris = tris;
	}
}
