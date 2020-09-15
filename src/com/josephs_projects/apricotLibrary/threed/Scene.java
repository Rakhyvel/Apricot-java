package com.josephs_projects.apricotLibrary.threed;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.josephs_projects.apricotLibrary.interfaces.Renderable;

public class Scene implements Renderable {
	int width, height;
	double a; // Aspect ratio
	double f; // Field of view scaling factor
	double zNear = 0.1, zFar = 100;
	double q;
	public Matrix projMat;

	public Matrix matTrans = new Matrix(new Vector(1, 0, 0, 0),
			new Vector(0, 1, 0, 0),
			new Vector(0, 0, 1, 0),
			new Vector(0, 0, 0, 1));

	public Matrix matLookAt = new Matrix(new Vector(1, 0, 0, 0),
			new Vector(0, 1, 0, 0),
			new Vector(0, 0, 1, 0),
			new Vector(0, 0, 0, 1));

	public Vector position = new Vector(0, 0, 0);
	public Vector lookAt = new Vector(0, 0, 0);
	public Vector up = new Vector(0, 1, 0).normalize();
	public Vector light = new Vector(0, 1, 1).normalize();

	public ArrayList<Mesh> meshes = new ArrayList<>();

	public Scene(int width, int height, double fov) {
		this.width = width;
		this.height = height;
		this.a = (double) height / width;
		this.f = 1 / Math.tan(fov * 0.5 / 180.0 * Math.PI);
		this.q = zFar / (zFar - zNear);
		projMat = new Matrix(new Vector(a * f, 0, 0, 0),
				new Vector(0, f, 0, 0),
				new Vector(0, 0, q, -zNear * q),
				new Vector(0, 0, 1, 0));

	}

	public Vector realToScreen(Vector real) {
		Vector retval = new Vector(real.elements);
		retval = projMat.mult(retval);
		// Normalize
		retval.elements[0] = retval.x() / retval.w();
		retval.elements[1] = retval.y() / retval.w();
		retval.elements[2] = retval.z() / retval.w();
		return retval;
	}

	/**
	 * Take in a triangle defined in real space, transforms it to a new triangle
	 * screen space
	 * 
	 * @param tri
	 * @return
	 */
	public Triangle realToScreen(Triangle tri) {
		Triangle retval = new Triangle(realToScreen(tri.v0),
				realToScreen(tri.v1),
				realToScreen(tri.v2));

		retval.v0.add(1.0, 1.0);
		retval.v1.add(1.0, 1.0);
		retval.v2.add(1.0, 1.0);

		retval.v0.mult(0.5 * width, 0.5 * height);
		retval.v1.mult(0.5 * width, 0.5 * height);
		retval.v2.mult(0.5 * width, 0.5 * height);

		return retval;
	}

	public Triangle translateMatrix(Triangle tri, Vector translate) {
		Matrix matTrans = new Matrix(new Vector(1, 0, 0, 0),
				new Vector(0, 1, 0, 0),
				new Vector(0, 0, 1, 0),
				translate);
		matTrans.T();
		return new Triangle(matTrans.mult(tri.v0), matTrans.mult(tri.v1), matTrans.mult(tri.v2));
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		for (Mesh mesh : meshes) {
			for (Triangle tri : mesh.triangles) {
				// Transform triangles in real space
				Triangle projTri = tri.mult(mesh.matRotX, mesh.matRotY, mesh.matRotZ,
						mesh.matTrans, matTrans, matLookAt);

				// Cull triangles before projection to screen space
				if (projTri.normal.dot(projTri.v0.sub(lookAt)) < 0)
					continue;

				// Draw triangle
				int brightness = (int)(255 * Math.max(0, Math.min(1, projTri.normal.dot(matLookAt.mult(light)) - .75)));
				g.setColor(new Color(brightness, brightness, brightness));
				
				projTri = realToScreen(projTri);
				g.fillPolygon(projTri.getXCoords(), projTri.getYCoords(), 3);
			}
		}
	}

	@Override
	public int getRenderOrder() {
		return 0;
	}

	@Override
	public void remove() {

	}

	public void setPosition(double x, double y, double z) {
		matTrans.elements[0][3] = x;
		matTrans.elements[1][3] = y;
		matTrans.elements[2][3] = z;
		position.elements[0] = x;
		position.elements[1] = y;
		position.elements[2] = z;
	}

	public void setLookAt(double x, double y, double z) {
		Vector forward = position.sub(new Vector(x, y, z)).normalize();
		Vector right = forward.cross(up);
		Vector u = right.cross(forward);

		matLookAt.elements[0] = right.elements;
		matLookAt.elements[1] = u.elements;
		matLookAt.elements[2] = forward.elements;
		matLookAt.elements[0][3] = 0;
		matLookAt.elements[1][3] = 0;
		matLookAt.elements[2][3] = 0;
		matLookAt.elements[3] = new double[] { 0, 0, 0, 1 };

		lookAt.elements[0] = x;
		lookAt.elements[1] = y;
		lookAt.elements[2] = z;
	}
}
