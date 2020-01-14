package com.josephs_projects.apricotLibrary.graphics3d;

import java.util.ArrayList;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.graphics.Render;

public class Camera {
	double fov = 1.3;
	double a = 500 / 800.0;
	double f = 1.0 / Math.tan(fov / 2.0);
	double near = .1;
	double far = 1000;
	public Tuple3D position = new Tuple3D(0, 0, 0);
	public Tuple3D target = new Tuple3D(0, 0, 5);
	public Tuple3D up = new Tuple3D(0, 1, 0);
	Tuple3D light = new Tuple3D(0, 0, -1).normalize();
	public ArrayList<Mesh> meshes = new ArrayList<>();
	double[] zBuffer = new double[800 * 500];

	public void render(Render r) {
		for (int i = 0; i < zBuffer.length; i++) {
			zBuffer[i] = Double.POSITIVE_INFINITY;
		}
		for (int i = 0; i < meshes.size(); i++) {
			ArrayList<Triangle> clipped = new ArrayList<Triangle>();
			for (int i2 = 0; i2 < meshes.get(i).tris.size(); i2++) {
				Tuple3D[] vertices = copyVertices(meshes.get(i).tris.get(i2).vertices);
				vertices = multPointAtMatrix(vertices, meshes.get(i).position, meshes.get(i).target, meshes.get(i).up);

				Tuple3D edge1 = vertices[0].sub(vertices[1]);
				Tuple3D edge2 = vertices[1].sub(vertices[2]);
				Tuple3D normal = edge2.cross(edge1).normalize();
				if (normal.dot(position.sub(vertices[0])) > 0) {
					continue;
				}
				meshes.get(i).tris.get(i2).shade = Apricot.color.scalar(meshes.get(i).tris.get(i2).color,(normal.dot(light) + 1) * 0.5);
				vertices = multLookAtMatrix(vertices);

				Triangle out1 = new Triangle(null);
				Triangle out2 = new Triangle(null);
				Triangle triangle = new Triangle(vertices);
				triangle.color = meshes.get(i).tris.get(i2).color;
				triangle.shade = meshes.get(i).tris.get(i2).shade;
				int numberOfTris = clipTriangleAgainstPlane(new Tuple3D(0, 0, near),
						new Tuple3D(0, 0, 1), triangle, out1, out2);
				if (numberOfTris > 0 && out1.vertices != null) {
					clipped.add(out1);
				}
				if (numberOfTris > 1 && out2.vertices != null) {
					clipped.add(out2);
				}
			}
			for (Triangle tri : clipped) {
				drawMeshes(r, tri.vertices, tri.shade);
			}
			clipped.clear();
		}
	}

	void drawMeshes(Render r, Tuple3D[] vertices, int color) {
		// Project 3D -> 2D
		vertices = multPerspectiveMatrix(vertices);
		zBuffer = r.fillTriangle(vertices[0].flat(), vertices[1].flat(), vertices[2].flat(), color, zBuffer,
				vertices[0].distSquared(position));
	}

	ArrayList<Triangle> insertZ(ArrayList<Triangle> list, Triangle tri) {
		if (list.size() == 0) {
			list.add(tri);
			return list;
		}

		for (int i = 0; i < list.size(); i++) {
			if (tri.vertices[0].distSquared(position) >= list.get(i).vertices[0].distSquared(position)) {
				list.add(i, tri);
				break;
			}
		}

		return list;
	}

	Tuple3D intersectPlane(Tuple3D point, Tuple3D normal, Tuple3D lineStart, Tuple3D lineEnd) {
		Tuple3D diff = lineStart.sub(point);
        double prod1 = diff.dot(normal);
        double prod2 = lineEnd.sub(lineStart).normalize().dot(normal);
        double prod3 = prod1 / prod2;
        return lineStart.sub(lineEnd.sub(lineStart).normalize().scalar(prod3));
	}

	int clipTriangleAgainstPlane(Tuple3D point, Tuple3D normal, Triangle in, Triangle out1, Triangle out2) {
		normal = normal.normalize();

		Tuple3D[] insidePoints = new Tuple3D[3];
		Tuple3D[] outsidePoints = new Tuple3D[3];
		int numberOfInside = 0;
		int numberOfOutside = 0;

		double d0 = in.vertices[0].distFromPlane(point, normal);
		double d1 = in.vertices[1].distFromPlane(point, normal);
		double d2 = in.vertices[2].distFromPlane(point, normal);

		if (d0 >= 0) {
			insidePoints[numberOfInside++] = in.vertices[0];
		} else {
			outsidePoints[numberOfOutside++] = in.vertices[0];
		}
		if (d1 >= 0) {
			insidePoints[numberOfInside++] = in.vertices[1];
		} else {
			outsidePoints[numberOfOutside++] = in.vertices[1];
		}
		if (d2 >= 0) {
			insidePoints[numberOfInside++] = in.vertices[2];
		} else {
			outsidePoints[numberOfOutside++] = in.vertices[2];
		}
		
		if (numberOfInside == 0) {
			return 0;
		}
		
		if (numberOfInside == 3) {
			out1.shade = in.shade;
			out1.color = in.color;
			out1.vertices = in.vertices;
			return 1;
		}
		if (numberOfInside == 1 && numberOfOutside == 2) {
			out1.shade = in.shade;
			out1.color = in.color;
			Tuple3D[] vertices = new Tuple3D[3];
			vertices[0] = new Tuple3D(insidePoints[0]); // Know this one is inside
			vertices[1] = new Tuple3D(intersectPlane(point, normal, insidePoints[0], outsidePoints[0]));
			vertices[2] = new Tuple3D(intersectPlane(point, normal, insidePoints[0], outsidePoints[1]));
			out1.vertices = vertices;
			return 1;
		}
		if (numberOfInside == 2 && numberOfOutside == 1) {
			out1.shade = in.shade;
			out1.color = in.color;
			out2.shade = in.shade;
			out2.color = in.color;
			Tuple3D[] vertices1 = new Tuple3D[3];
			vertices1[0] = new Tuple3D(insidePoints[0]); // Know this one is inside
			vertices1[1] = new Tuple3D(insidePoints[1]);
			vertices1[2] = new Tuple3D(intersectPlane(point, normal, insidePoints[0], outsidePoints[0]));
			out1.vertices = vertices1;

			Tuple3D[] vertices2 = new Tuple3D[3];
			vertices2[0] = new Tuple3D(insidePoints[1]); // Know this one is inside
			vertices2[1] = new Tuple3D(intersectPlane(point, normal, insidePoints[0], outsidePoints[0]));
			vertices2[2] = new Tuple3D(intersectPlane(point, normal, insidePoints[1], outsidePoints[0]));
			out2.vertices = vertices2;
			return 2;
		}

		return 0;
	}

	Tuple3D[] copyVertices(Tuple3D[] old) {
		Tuple3D[] vertices = new Tuple3D[old.length];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Tuple3D(old[i]);
		}
		return vertices;
	}

	Tuple3D[] multPerspectiveMatrix(Tuple3D[] vertices) {
		double newX;
		double newY;
		double newZ;
		for (int i = 0; i < vertices.length; i++) {
			newX = (int) (400 * a * f * vertices[i].x / vertices[i].z + 400);
			newY = (int) (250 * f * vertices[i].y / vertices[i].z + 250);
			newZ = (far / (far - near)) * vertices[i].z - (far * near) / (far - near);
			vertices[i].x = newX;
			vertices[i].y = newY;
			vertices[i].z = newZ;
		}
		return vertices;
	}

	Tuple3D[] multPointAtMatrix(Tuple3D[] vertices, Tuple3D origin, Tuple3D target, Tuple3D up) {
		Tuple3D newForward = target.sub(origin).normalize();
		Tuple3D a = newForward.scalar(up.dot(newForward));
		Tuple3D newUp = up.sub(a).normalize();
		Tuple3D newRight = newUp.cross(newForward);
		double newX;
		double newY;
		double newZ;
		for (int i = 0; i < vertices.length; i++) {
			newX = newRight.x * vertices[i].x + newUp.x * vertices[i].y + newForward.x * vertices[i].z + origin.x;
			newY = newRight.y * vertices[i].x + newUp.y * vertices[i].y + newForward.y * vertices[i].z + origin.y;
			newZ = newRight.z * vertices[i].x + newUp.z * vertices[i].y + newForward.z * vertices[i].z + origin.z;
			vertices[i].x = newX;
			vertices[i].y = newY;
			vertices[i].z = newZ;
		}
		return vertices;
	}

	Tuple3D[] multLookAtMatrix(Tuple3D[] vertices) {
		Tuple3D newForward = target.sub(position).normalize();
		Tuple3D a = newForward.scalar(up.dot(newForward));
		Tuple3D newUp = up.sub(a).normalize();
		Tuple3D newRight = newUp.cross(newForward);
		double newX;
		double newY;
		double newZ;
		for (int i = 0; i < vertices.length; i++) {
			newX = newRight.x * vertices[i].x + newRight.y * vertices[i].y + newRight.z * vertices[i].z
					+ -position.dot(newRight);
			newY = newUp.x * vertices[i].x + newUp.y * vertices[i].y + newUp.z * vertices[i].z + -position.dot(newUp);
			newZ = newForward.x * vertices[i].x + newForward.y * vertices[i].y + newForward.z * vertices[i].z
					+ -position.dot(newForward);
			vertices[i].x = -newX;
			vertices[i].y = newY;
			vertices[i].z = newZ;
		}
		return vertices;
	}
}
