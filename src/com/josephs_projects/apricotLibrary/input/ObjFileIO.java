package com.josephs_projects.apricotLibrary.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.josephs_projects.apricotLibrary.graphics3d.Mesh;
import com.josephs_projects.apricotLibrary.graphics3d.Triangle;
import com.josephs_projects.apricotLibrary.graphics3d.Tuple3D;

public class ObjFileIO {
	public static Mesh readObjFile(String path) {
		ArrayList<Tuple3D> vertices = new ArrayList<>();
		ArrayList<Triangle> tris = new ArrayList<>();
		String line = "";
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split(" ");
				if (splitLine[0].equals("v")) {
					vertices.add(new Tuple3D(
							Double.parseDouble(splitLine[1]), 
							Double.parseDouble(splitLine[2]),
							Double.parseDouble(splitLine[3])));
				} else if (splitLine[0].equals("f")) {
					Tuple3D[] triVert = {
							vertices.get(Integer.parseInt(splitLine[1].split("/")[0]) - 1),
							vertices.get(Integer.parseInt(splitLine[2].split("/")[0]) - 1),
							vertices.get(Integer.parseInt(splitLine[3].split("/")[0]) - 1),
					};
					tris.add(new Triangle(triVert));
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading obj file: " + e.getMessage());
		} catch (NumberFormatException n) {
			System.out.println("File is not in a pleasant format\n" + line);
		}
		
		return new Mesh(tris);
	}
}
