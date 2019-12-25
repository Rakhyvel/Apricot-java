package com.josephs_projects.apricotLibrary;

import java.util.ArrayList;
import java.util.Comparator;

import com.josephs_projects.apricotLibrary.graphics.Render;
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
	private final ArrayList<Tickable> tickables = new ArrayList<>();
	private final ArrayList<Renderable> renderables = new ArrayList<>();
	private final ArrayList<InputListener> inputListeners = new ArrayList<>();

	private final Comparator<Renderable> comparator = new YPositionComparator();

	void tick() {
		for (int i = 0; i < tickables.size(); i++) {
			tickables.get(i).tick();
		}
	}

	void render(Render r) {
		renderables.sort(comparator);
		for (int i = 0; i < renderables.size(); i++) {
			renderables.get(i).render(r);
		}
	}

	void input() {
		for (int i = 0; i < inputListeners.size(); i++) {
			inputListeners.get(i).input();
		}
	}

	public void addTickable(Tickable t) {
		tickables.add(t);
	}

	public void addRenderable(Renderable r) {
		renderables.add(r);
	}

	public void addInputListener(InputListener i) {
		inputListeners.add(i);
	}

	public void removeTickable(Tickable t) {
		tickables.remove(t);
	}

	public void removeRenderable(Renderable r) {
		renderables.remove(r);
	}

	public void removeInputListener(InputListener i) {
		inputListeners.remove(i);
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
	
	public ArrayList<Tickable> getTickables(){
		return tickables;
	}
	
	public ArrayList<Renderable> getRenderables(){
		return renderables;
	}
	
	public ArrayList<InputListener> getInputListeners(){
		return inputListeners;
	}
}
