package com.josephs_projects.apricotLibrary;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

import com.josephs_projects.apricotLibrary.graphics.ColorOld;
import com.josephs_projects.apricotLibrary.graphics.Image;
import com.josephs_projects.apricotLibrary.input.InputEvent;
import com.josephs_projects.apricotLibrary.input.Keyboard;
import com.josephs_projects.apricotLibrary.input.Mouse;

import sun.java2d.SunGraphicsEnvironment;

/**
 * This class can be used to organize your Java project. It provides a window, a
 * GameLoop, input listening, and an interface for World collections.
 * 
 * @author Joseph Shimel
 *
 */
public class Apricot extends Thread {
	/* Reference to world that engine is working with */
	private World world;

	// Graphics fields
	/* JFrame used by application */
	public final JFrame frame;
	/* Canvas used by application */
	public final Canvas canvas;

	// Input fields
	/* Mouse input handler */
	public final Mouse mouse;
	/* Keyboard input handler */
	public final Keyboard keyboard;

	// Gameloop fields
	/* Timestep between ticks */
	private double dt = 1000 / 60.0;
	/* Number of ticks that have passed */
	public int ticks = 0;
	/* The current Frames Per Second of the application */
	public int fps;

	// misc public-static access fields
	/* Random number generator */
	public static Random rand = new Random();
	/* NoiseMap generator */
	public static final NoiseMap noiseMap = new NoiseMap();
	/* Image manipulation handler */
	public static final Image image = new Image();
	/* DEPRECATED: Use java.awt.Color instead. Still has some useful methods */
	public static final ColorOld color = new ColorOld();
	
	/* Whether or not the application is maximized */
	public static boolean maximized;
	
	/* The current cursor of the application */
	public Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	
	/**
	 * Creates a new Apricot object with a given title, width, and height.
	 */
	public Apricot(String title, int width, int height) {
		frame = new JFrame(title);
		canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(canvas);
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		frame.pack();		
		frame.setVisible(true);
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		frame.setMaximizedBounds(e.getMaximumWindowBounds());
		frame.setLocationRelativeTo(null);

		mouse = new Mouse(this);
		keyboard = new Keyboard(this);
	}
	
	/**
	 * Creates an Apricot object with a given title, width, and height, and the ability to be undecorated.
	 */
	public Apricot(String title, int width, int height, boolean undecorated) {
		frame = new JFrame(title);
		canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setUndecorated(undecorated);
		frame.add(canvas);
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setVisible(true);
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		frame.setMaximizedBounds(e.getMaximumWindowBounds());
		frame.setLocationRelativeTo(null);

		mouse = new Mouse(this);
		keyboard = new Keyboard(this);
	}
	
	

	/**
	 * Starts the gameloop. Note that this method will not run if the engine
	 * already has a loop running.
	 */
	@Override
	public void run() {
		// Game loop begins here
		double previous = System.currentTimeMillis();
		double lag = 0;
		int frames = 0;
		double elapsedFPS = 0;
		while (frame.isDisplayable()) {
			double current = System.currentTimeMillis();
			double elapsed = current - previous;

			previous = current;

			lag += elapsed;
			elapsedFPS += elapsed;

			while (lag >= dt) {
				if (world != null)
					world.tick();
				lag -= dt;
				ticks++;
				if(cursor.getType() != frame.getCursor().getType()) {
					frame.setCursor(cursor);
				}
			}
			frames++;
			render();
			if(elapsedFPS > 1000) {
				fps = frames;
				frames = 0;
				elapsedFPS = 0;
			}
		}
	}

	/**
	 * Draws to the screen according to what Renderables are in the current World
	 */
	public void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(5);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		if (world != null)
			world.render(g);

		g.dispose();
		bs.show();
	}

	/**
	 * Checks for any input of the user, informs InputListeners added to current World
	 */
	public void input(InputEvent e) {
		if (world != null)
			world.input(e);
	}
	
	/**
	 * Changes the current world
	 */
	public void setWorld(World world) {
		this.world = world;
		input(InputEvent.WORLD_CHANGE);
	}
	
	/**
	 * Returns the current world
	 */
	public World getWorld(World world) {
		return world;
	}
	
	/**
	 * Minimizes the JFrame
	 */
	public void minimize() {
		frame.setState(JFrame.ICONIFIED);
	}

	/**
	 * Recovers JFrame from being minimized
	 */
	public void normalize() {
		frame.setState(JFrame.NORMAL);
		frame.requestFocusInWindow();
	}

	/**
	 * If JFrame is maximum size, sets to default size as dicted by settings. Else,
	 * sets to maximum size.
	 */
	public void maximize() {
		if (maximized) {
			frame.setSize(1106, 640);
			frame.setLocationRelativeTo(null);
		} else {
			Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(frame.getGraphicsConfiguration().getDevice());
			frame.setMaximizedBounds(usableBounds);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		maximized = !maximized;
	}
	
	/**
	 * Returns the width of content inside the JFrame
	 */
	public int width() {
		return frame.getContentPane().getWidth();
	}
	
	/**
	 * Returns the height of the content inside the JFrame. Does not include Windows or MacOS Menu bar
	 */
	public int height() {
		return frame.getContentPane().getHeight();
	}
	
	/**
	 * Changes the timestep in milliseconds
	 */
	public void setDeltaT(double dt) {
		this.dt = dt;
	}
}
