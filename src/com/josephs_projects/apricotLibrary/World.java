package com.josephs_projects.apricotLibrary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

import com.josephs_projects.apricotLibrary.graphics.Render;
import com.josephs_projects.apricotLibrary.graphics3d.Camera;
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
	
	public int background = 0;
	
	public Camera camera = null;

	private final Comparator<Renderable> comparator = new YPositionComparator();
	
	public World() {}
	public World(int background) {
		this.background = background;
	}

	void tick() {
		for (int i = 0; i < tickables.size(); i++) {
			tickables.get(i).tick();
		}
	}

	void render(Render r) {
		r.fillRect(0, r.topEdge, r.width, r.bottomEdge, background);
		try {
			renderables.sort(comparator);
			for (int i = 0; i < renderables.size(); i++) {
				renderables.get(i).render(r);
			}
		} catch(ConcurrentModificationException c) {
			System.out.println("Warning: World.render(): Concurrent modification during sort method.");
		}
		if(camera != null) {
			camera.render(r);
		}
	}

	void input(InputEvent e) {
		for (int i = 0; i < inputListeners.size(); i++) {
			inputListeners.get(i).input(e);
		}
	}
	
	public void add(Object o) {
		if(o == null)
			throw new NullPointerException();
		
		if(o instanceof Tickable) {
			tickables.add((Tickable)o);
		}
		if(o instanceof Renderable) {
			renderables.add((Renderable) o);
		}
		if(o instanceof InputListener) {
			inputListeners.add((InputListener) o);
		}
	}
	
	public void remove(Object o) {
		if(o == null)
			throw new NullPointerException();
		
		if(o instanceof Tickable) {
			tickables.remove((Tickable)o);
		}
		if(o instanceof Renderable) {
			renderables.remove((Renderable) o);
		}
		if(o instanceof InputListener) {
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
}
