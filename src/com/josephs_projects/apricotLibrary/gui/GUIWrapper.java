package com.josephs_projects.apricotLibrary.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class GUIWrapper extends GUIObject {
	public List<GUIObject> objects = new ArrayList<>();
	ColorScheme scheme;

	public GUIWrapper(Tuple position, int renderOrder, ColorScheme scheme, Apricot apricot, World world) {
		super(position, apricot, world);
		world.add(this);
		this.scheme = scheme;
		setRenderOrder(renderOrder);
	}

	public void addGUIObject(GUIObject object) {
		objects.add(object);
		object.wrapper = this;
		updatePosition(position);
	}

	public void removeGUIObject(GUIObject object) {
		objects.remove(object);
		world.remove(object);
	}

	public void setShown(boolean shown) {
		this.shown = shown;
		for (GUIObject o : objects) {
			o.setShown(shown);
		}
	}

	@Override
	public void render(Graphics2D g) {
		if (!shown) {
			return;
		}
		if (border > 0) {
			g.setColor(scheme.borderColor);
			g.setStroke(new BasicStroke(border));
			g.drawRect((int) position.x, (int) position.y, width(), height());
		}
	}

	@Override
	public void input(InputEvent e) {

	}

	@Override
	public void remove() {
		for (GUIObject o : objects) {
			o.remove();
		}
		world.remove(this);
	}

	@Override
	public int getRenderOrder() {
		return renderOrder;
	}

	@Override
	public void setRenderOrder(int order) {
		this.renderOrder = order;
		for (GUIObject o : objects) {
			o.setRenderOrder(order);
		}
	}

	@Override
	public int height() {
		if (!shown) {
			return 0;
		}
		int height = 0;
		for (GUIObject o : objects) {
			height = (int) Math.max(o.position.y - position.y + o.height(), height);
		}
		return height + padding;
	}

	@Override
	public int width() {
		int width = 0;
		for (GUIObject o : objects) {
			width = (int) Math.max(o.position.x - position.x + o.width(), width);
		}
		return width + padding;
	}

	public void updatePosition(Tuple position) {
		this.position = position;
		int height = padding;
		int width = padding; // Used for overall width of Wrapper
		int colWidth = 0; // Used for determining the width of the column
		for (GUIObject o : objects) {
			// Check for vertical overflow
			if (!o.shown)
				continue;
			o.updatePosition(new Tuple(position.x + width + o.margin, position.y + height + o.margin));
			if (position.y + height + o.height() + o.margin*2 + padding*2 > apricot.height()) {
				height = padding;
				width += colWidth;
				colWidth = 0;
				o.updatePosition(new Tuple(position.x + width + o.margin, position.y + height + o.margin));
			}
			height += o.height() + o.margin;
			colWidth = Math.max(colWidth, o.width() + o.margin * 2);
		}
	}
	
	public void update() {
		updatePosition(position);
	}
}