package com.josephs_projects.apricotLibrary.threed;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import com.josephs_projects.apricotLibrary.interfaces.Renderable;

public class RayCaster implements Renderable {
	public int width, height;
	BufferedImage image;
	int[] pixels;
	int pixelDepth = 4;
	Ray[][] rays;
	Vector sun = new Vector(0, -0.707, -0.707);

	public ArrayList<Mesh> meshes = new ArrayList<>();

	public RayCaster(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		rays = new Ray[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				rays[x][y] = new Ray(new Vector(0, 0, -4),
						new Vector(2.0 * x / width - 1, 2.0 * y / height - 1, 1));
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[y * width + x] = 0;
			}
		}
		g.fillRect(0, 0, width, height);
		for (int x = 0; x < width; x += pixelDepth) {
			for (int y = 0; y < height; y += pixelDepth) {
				for (Mesh mesh : meshes) {
					for (Triangle tri : mesh.triangles) {
						Triangle projTri = tri.mult(mesh.matRotX).mult(mesh.matRotX).mult(mesh.matRotX);
						if(projTri.normal.dot(rays[x][y].vector) > 0)
							continue;
						if (projTri.intersects(rays[x][y])) {
							double brightness = Math.max(0, Math.min(1, sun.dot(projTri.normal)));
							plot(x, y, 255 << 24 | ((int) (127 * brightness + 127)));
						}
					}
				}
			}
		}
		g.drawImage(image, 0, 0, null);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getRenderOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void plot(int x, int y, int color) {
		x = (int) (x / pixelDepth) * pixelDepth;
		y = (int) (y / pixelDepth) * pixelDepth;
		for (int i = 0; i < pixelDepth; i++) {
			for (int j = 0; j < pixelDepth; j++) {
				pixels[(y + j) * width + (x + i)] = color;
			}
		}
	}
}
