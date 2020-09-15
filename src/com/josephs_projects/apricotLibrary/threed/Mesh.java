package com.josephs_projects.apricotLibrary.threed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Mesh {
	public ArrayList<Triangle> triangles = new ArrayList<>();
	public Vector position = new Vector(4, 0, 6, 0);
	public Matrix matTrans = new Matrix(new Vector(1, 0, 0, 0),
			new Vector(0, 1, 0, 0),
			new Vector(0, 0, 1, 0),
			new Vector(0, 0, 0, 1));
	public Matrix matRotX = new Matrix(new Vector(1, 0, 0, 0),
			new Vector(0, 1, 0, 0),
			new Vector(0, 0, 1, 0),
			new Vector(0, 0, 0, 1));
	public Matrix matRotY = new Matrix(new Vector(1, 0, 0, 0),
			new Vector(0, 1, 0, 0),
			new Vector(0, 0, 1, 0),
			new Vector(0, 0, 0, 1));
	public Matrix matRotZ = new Matrix(new Vector(1, 0, 0, 0),
			new Vector(0, 1, 0, 0),
			new Vector(0, 0, 1, 0),
			new Vector(0, 0, 0, 1));

	public Mesh() {
	};

	public Mesh(String filename) throws IOException {
		File objFile = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(objFile));
		String line;
		ArrayList<Vector> vertices = new ArrayList<>();

		while ((line = br.readLine()) != null) {
			if(line.length() < 1)
				continue;
			if (line.charAt(0) == 'v' && line.charAt(1) == ' ') {
				;
				String[] lineSplit = line.split(" ");
				vertices.add(new Vector(Double.parseDouble(lineSplit[1]),
						Double.parseDouble(lineSplit[2]),
						Double.parseDouble(lineSplit[3]), 1));
			} else if (line.charAt(0) == 'f' && line.charAt(1) == ' ') {
				String[] lineSplit = line.split(" ");
				String[] v0 = lineSplit[1].split("/");
				String[] v1 = lineSplit[2].split("/");
				String[] v2 = lineSplit[3].split("/");

				triangles.add(new Triangle(vertices.get(Integer.parseInt(v0[0]) - 1),
						vertices.get(Integer.parseInt(v1[0]) - 1),
						vertices.get(Integer.parseInt(v2[0]) - 1)));
			}
		}
		br.close();
	}

	public void setPosition(double x, double y, double z) {
		matTrans.elements[0][3] = x;
		matTrans.elements[1][3] = y;
		matTrans.elements[2][3] = z;
	}
	
	public void setRotateX(double angle) {
		matRotX.elements[1][1] = Math.cos(angle);
		matRotX.elements[1][2] = -Math.sin(angle);
		matRotX.elements[2][1] = Math.sin(angle);
		matRotX.elements[2][2] = Math.cos(angle);
	}
	
	public void setRotateY(double angle) {
		matRotY.elements[0][0] = Math.cos(angle);
		matRotY.elements[0][2] = Math.sin(angle);
		matRotY.elements[2][0] = -Math.sin(angle);
		matRotY.elements[2][2] = Math.cos(angle);
	}
	
	public void setRotateZ(double angle) {
		matRotZ.elements[0][0] = Math.cos(angle);
		matRotZ.elements[0][1] = -Math.sin(angle);
		matRotZ.elements[1][0] = Math.sin(angle);
		matRotZ.elements[1][1] = Math.cos(angle);
	}
}
