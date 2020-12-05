package com.josephs_projects.apricotLibrary.gui;

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

	public GUIWrapper(Tuple position, Apricot apricot, World world, ColorScheme scheme) {
		super(position, apricot, world);
		world.add(this);
		this.scheme = scheme;
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

	public void setRenderOrder(int order) {
		this.renderOrder = order + 1;
		for(GUIObject obj : objects) {
			obj.setRenderOrder(order);
		}
	}

	@Override
	public void render(Graphics2D g) {
		if(!shown) {
			return;
		}
		g.setColor(scheme.borderColor);
		g.drawRect((int)position.x, (int)position.y, width(), height());
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
	public int height() {
		if(!shown) {
			return 0;
		}
		int height = padding;
		int rowHeight = 0; // Used for determining the width of the column
		for (GUIObject o : objects) {
			// Check for vertical overflow
			if(!o.shown)
				continue;
			if (position.y + height + o.height() > apricot.height()) {
				height = padding;
			}
			height += o.height();
			rowHeight = Math.max(rowHeight, o.height());
		}
		return height;
	}

	@Override
	public int width() {
		int width = 0;
		for (GUIObject o : objects) {
			width = Math.max(width, o.width());
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
			if(!o.shown)
				continue;
			o.position = new Tuple(position.x + width, position.y + height);
			if (position.y + height + o.height() > apricot.height()) {
				height = padding;
				width += colWidth;
				colWidth = 0;
			}
			o.updatePosition(new Tuple(position.x + width, position.y + height));
			height += o.height();
			colWidth = Math.max(colWidth, o.width());
		}
	}
}