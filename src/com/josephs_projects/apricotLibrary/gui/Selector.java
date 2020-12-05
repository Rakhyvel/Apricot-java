package com.josephs_projects.apricotLibrary.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class Selector<E> extends GUIObject {
	int width;
	int height;
	ColorScheme scheme;
	private E[] options;
	private Label[] labels;
	private int selected = -1;

	public Selector(int width, int height, E[] array, ColorScheme scheme, Apricot apricot, World world) {
		super(new Tuple(), apricot, world);
		this.width = width;
		this.height = height;
		this.scheme = scheme;
		this.options = array;
		this.labels = new Label[options.length];
		for (int i = 0; i < options.length; i++) {
			labels[i] = new Label("", scheme, apricot, world);
			labels[i].fontSize = 17;
			labels[i].renderOrder = renderOrder + 1;
		}
		world.add(this);
	}

	@Override
	public void render(Graphics2D g) {
		if (!shown)
			return;
		g.setStroke(new BasicStroke(2));
		for (int i = 0; i < options.length; i++) {
			g.setColor(scheme.disabledTextColor);
			if (options[i] == null) {
				g.drawRect((int) position.x, (int) position.y + i * height, width, height);
			}
		}
		for (int i = 0; i < options.length; i++) {
			if (options[i] != null) {
				E option = options[i];
				if (i == selected) {
					g.setColor(scheme.highlightColor);
					g.fillRect((int) position.x, (int) position.y + i * height, width, height);
				}
				g.setColor(scheme.borderColor);
				g.drawRect((int) position.x, (int) position.y + i * height, width, height);
				labels[i].text = option.toString();
			}
			labels[i].shown = options[i] != null;
			labels[i].position.x = (int) position.x + 8;
			labels[i].position.y = (int) position.y + i * height + 8;
		}
	}

	@Override
	public void input(InputEvent e) {
		if (!shown)
			return;

		if (e == InputEvent.MOUSE_LEFT_RELEASED) {
			if (apricot.mouse.position.y < position.y || apricot.mouse.position.x < position.x
					|| apricot.mouse.position.x > position.x + width) {
				selected = -1;
				return;
			}
			selected = (int) (apricot.mouse.position.y - position.y) / 25;
		}
	}

	@Override
	public void remove() {

	}

	@Override
	public void setShown(boolean shown) {
		this.shown = shown;
		for (int i = 0; i < labels.length; i++) {
			labels[i].setShown(shown);
		}
	}

	@Override
	public int getRenderOrder() {
		return renderOrder;
	}

	@Override
	public void updatePosition(Tuple position) {
		this.position = position;
	}

	public int height() {
		if (!shown)
			return 0;
		return height;
	}

	public int width() {
		if (!shown)
			return 0;
		return width + 2 * margin + 10;
	}

	public boolean add(E option) {
		for (int i = 0; i < options.length; i++) {
			if (options[i] == null) {
				options[i] = option;
				return true;
			}
		}
		return false;
	}

	public E selected() {
		if (selected < 0 || selected >= options.length)
			return null;
		return options[selected];
	}

	public void clearSelected() {
		options[selected] = null;
		labels[selected].text = "";
	}

}
