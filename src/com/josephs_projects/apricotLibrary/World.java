package com.josephs_projects.apricotLibrary;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

import com.josephs_projects.apricotLibrary.input.InputEvent;
import com.josephs_projects.apricotLibrary.interfaces.InputListener;
import com.josephs_projects.apricotLibrary.interfaces.Renderable;
import com.josephs_projects.apricotLibrary.interfaces.Tickable;

/**
 * World classes hold tickable, renderable, and inputListener objects. They
 * should be used to separate fundamentally different sections in an
 * application. For example, one could have a main screen World, and a separate
 * true game World.
 * 
 * @author Joseph Shimel
 *
 */
public class World {
	public final ArrayList<Tickable> tickables = new ArrayList<>();
	public final ArrayList<Renderable> renderables = new ArrayList<>();
	public final ArrayList<InputListener> inputListeners = new ArrayList<>();

	private final Comparator<Renderable> comparator = new YPositionComparator();

	void tick() {
		for (int i = 0; i < tickables.size(); i++) {
			tickables.get(i).tick();
		}
	}

	void render(Graphics2D g) {
		try {
			renderables.sort(comparator);
			for (int i = 0; i < renderables.size(); i++) {
				renderables.get(i).render(g);
			}
		} catch (ConcurrentModificationException c) {
			System.out.println("Warning: World.render(): Concurrent modification during sort method.");
		}
	}

	void input(InputEvent e) {
		for (int i = 0; i < inputListeners.size(); i++) {
			inputListeners.get(i).input(e);
		}
	}

	public void add(Object o) {
		if (o == null)
			throw new NullPointerException();

		if (o instanceof Tickable) {
			tickables.add((Tickable) o);
		}
		if (o instanceof Renderable) {
			renderables.add((Renderable) o);
		}
		if (o instanceof InputListener) {
			inputListeners.add((InputListener) o);
		}
	}

	public void remove(Object o) {
		if (o == null)
			throw new NullPointerException();

		if (o instanceof Tickable) {
			tickables.remove((Tickable) o);
		}
		if (o instanceof Renderable) {
			renderables.remove((Renderable) o);
		}
		if (o instanceof InputListener) {
			inputListeners.remove((InputListener) o);
		}
	}

	private class YPositionComparator implements Comparator<Renderable> {
		@Override
		public int compare(Renderable arg0, Renderable arg1) {
			if (arg0 == null)
				return 0;
			if (arg1 == null)
				return 0;

			return arg0.getRenderOrder() - arg1.getRenderOrder();
		}
	}
	
	public boolean renderablesContains(Object o) {
		return renderables.contains(o);
	}
}
