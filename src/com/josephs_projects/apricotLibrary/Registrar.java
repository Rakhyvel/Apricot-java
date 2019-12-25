package com.josephs_projects.apricotLibrary;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

import com.josephs_projects.library.graphics.Image;
import com.josephs_projects.library.graphics.Render;
import com.josephs_projects.library.input.Keyboard;
import com.josephs_projects.library.input.Mouse;

/**
 * This class can be used to organize your Java project. It provides a window, a
 * GameLoop, input listening, and an interface for World collections.
 * 
 * @author Joseph Shimel
 *
 */
public class Registrar {
	// Reference to world that registrar is working with
	private World world;

	// Graphics fields
	private final JFrame frame;
	private final Canvas canvas;
	private final Render render;

	// Input fields
	private final Mouse mouse;
	private final Keyboard keyboard;

	// Gameloop fields
	private boolean running = false;
	private double dt = 1000 / 60.0;
	public int ticks = 0;

	// misc public-static access fields
	public static Random rand = new Random();
	public static Map map = new Map();
	public static Image image = new Image();

	public Registrar(String title, int width, int height) {
		frame = new JFrame(title);
		canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		canvas.setSize(width, height);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		mouse = new Mouse(canvas, this);
		keyboard = new Keyboard(canvas, this);

		render = new Render(width, height);
	}

	/**
	 * Starts the gameloop. Note that this method will not run if the Registrar
	 * already has a loop running.
	 */
	public void run() {
		// Only call method if gameloop is not currently running.
		// This is to prevent two loops running simultaneously
		if (running)
			return;
		running = true;

		// Game loop begins here
		double previous = System.currentTimeMillis();
		double lag = 0;
		while (running) {
			double current = System.currentTimeMillis();
			double elapsed = current - previous;

			previous = current;

			lag += elapsed;

			while (lag >= dt) {
				tick();
				lag -= dt;
				ticks++;
			}

			render();
		}
	}

	private void tick() {
		if (world != null)
			world.tick();
	}

	private void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		if (world != null)
			world.render(render);

		render.render(g, frame);

		g.dispose();
		bs.show();
	}

	public void input() {
		if (world != null)
			world.input();
	}

	public void stop() {
		running = false;
		System.exit(0);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}

	public JFrame getJFrame() {
		return frame;
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
