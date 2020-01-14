package com.josephs_projects.apricotLibrary;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

import com.josephs_projects.apricotLibrary.graphics.Color;
import com.josephs_projects.apricotLibrary.graphics.Font;
import com.josephs_projects.apricotLibrary.graphics.Image;
import com.josephs_projects.apricotLibrary.graphics.Render;
import com.josephs_projects.apricotLibrary.graphics.SpriteSheet;
import com.josephs_projects.apricotLibrary.input.InputEvent;
import com.josephs_projects.apricotLibrary.input.Keyboard;
import com.josephs_projects.apricotLibrary.input.Mouse;

/**
 * This class can be used to organize your Java project. It provides a window, a
 * GameLoop, input listening, and an interface for World collections.
 * 
 * @author Joseph Shimel
 *
 */
public class Apricot extends Thread {
	// Reference to world that engine is working with
	private World world;

	// Graphics fields
	public final JFrame frame;
	public final Canvas canvas;
	public final Render render;

	// Input fields
	public final Mouse mouse;
	public final Keyboard keyboard;

	// Gameloop fields
	private double dt = 1000 / 60.0;
	public int ticks = 0;
	public int fps;

	// misc public-static access fields
	public static Random rand = new Random();
	public static final NoiseMap noiseMap = new NoiseMap();
	public static final Image image = new Image();
	public static final Color color = new Color();
	public static final Font defaultFont = new Font(
			new SpriteSheet("/com/josephs_projects/apricotLibrary/graphics/font16.png", 256), 16);

	public Apricot(String title, int width, int height) {
		frame = new JFrame(title);
		canvas = new Canvas();
		render = new Render(width);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(canvas);
		canvas.setSize(width, height);
		frame.pack();
		render.calcDimensions(frame);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		mouse = new Mouse(this);
		keyboard = new Keyboard(this);
	}

	/**
	 * Starts the gameloop. Note that this method will not run if the Registrar
	 * already has a loop running.
	 */
	@Override
	public void run() {
		// Game loop begins here
		double previous = System.currentTimeMillis();
		double lag = 0;
		while (frame.isDisplayable()) {
			double current = System.currentTimeMillis();
			double elapsed = current - previous;

			previous = current;

			lag += elapsed;

			while (lag >= dt) {
				if (world != null)
					world.tick();
				lag -= dt;
				ticks++;
			}
			render();
		}
	}

	public void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		
		if (world != null)
			world.render(render);

		render.render(g, frame);

		g.dispose();
		bs.show();
	}

	public void input(InputEvent e) {
		if (world != null)
			world.input(e);
	}
	
	public void setWorld(World world) {
		this.world = world;
		input(InputEvent.WORLD_CHANGE);
	}
	
	public World getWorld(World world) {
		return world;
	}
}
