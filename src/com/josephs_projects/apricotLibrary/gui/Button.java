package com.josephs_projects.apricotLibrary.gui;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Graphics2D;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.gui.Updatable;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class Button extends GUIObject {
	int width;
	int height;
	ColorScheme scheme;
	public Label label;
	public boolean isHovered;
	boolean isClicked;
	public boolean active = true;
	public int borderSize = 0;
	public Updatable listener;

	public Button(String text, int width, int height, ColorScheme scheme, Apricot apricot, World world,
			Updatable listener) {
		super(new Tuple(), apricot, world);
		this.width = width;
		this.height = height;
		this.scheme = scheme;
		world.add(this);
		this.listener = listener;
		label = new Label(text, scheme, apricot, world);
		label.align = Label.Alignment.CENTER;
		label.fontSize = 17;
	}

	public Button(String text, int width, int height, int textSize, int margin, int padding, Label.Alignment align,
			ColorScheme scheme, Apricot apricot, World world,
			Updatable listener) {
		this(text, width, height, scheme, apricot, world, listener);
		label = new Label(text, scheme, apricot, world);
		label.align = align;
		label.fontSize = textSize;
		this.margin = margin;
		this.padding = padding;
	}

	@Override
	public void render(Graphics2D g) {
		if (!shown)
			return;
		label.active = this.active;
		if (!active)
			isHovered = false;

		g.setColor(scheme.backgroundColor);
		g.fillRect((int) position.x, (int) position.y, width, height);
		if (isHovered) {
			g.setColor(scheme.highlightColor);
			g.fillRect((int) position.x, (int) position.y, width, height);
			apricot.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		}

		if (borderSize != 0) {
			g.setColor(scheme.borderColor);
			g.setStroke(new BasicStroke(borderSize));
			g.drawRect((int) position.x, (int) position.y, width, height);
		}
	}

	/**
	 * When the button is pressed, it will send the updatable the text that is
	 * contained within the label.
	 */
	@Override
	public void input(InputEvent e) {
		if (!shown || !active) {
			return;
		}
		if (e == InputEvent.MOUSE_MOVED) {
			int diffX = (int) (apricot.mouse.position.x - position.x);
			int diffY = (int) (apricot.mouse.position.y - position.y);
			isHovered = (diffX > 0 && diffX < width) && (diffY > 0 && diffY < height);
		}
		if (e == InputEvent.WORLD_CHANGE) {
			isHovered = false;
		}
		if (e == InputEvent.MOUSE_LEFT_DOWN) {
			isClicked = isHovered;
		}
		if (e == InputEvent.MOUSE_LEFT_RELEASED) {
			// Call caller and let them know button is clicked.
			if (isClicked) {
				listener.update(label.text);
			}
			isClicked = false;
		}

	}

	@Override
	public void remove() {
		world.remove(this);
		label.remove();
	}

	public void setRenderOrder(int order) {
		this.renderOrder = order;
		label.renderOrder = order;
	}

	@Override
	public int getRenderOrder() {
		return renderOrder;
	}

	@Override
	public void setShown(boolean shown) {
		this.shown = shown;
		label.setShown(shown);
	}

	public void updatePosition(Tuple position) {
		this.position = position;
		if (label.align == Label.Alignment.CENTER) {
			label.updatePosition(position.add(new Tuple(width / 2, height / 2 - 4)));
		} else if (label.align == Label.Alignment.LEFT) {
			label.updatePosition(position.add(new Tuple(7, height / 2 - 4)));
		} else if (label.align == Label.Alignment.RIGHT) {
			label.updatePosition(position.add(new Tuple(width - label.width(), height / 2 - 4)));
		}
	}

	public int height() {
		if (!shown)
			return 0;
		return height + 2 * margin;
	}

	public int width() {
		if (!shown)
			return 0;
		return width + 2 * margin;
	}
}
