package com.josephs_projects.apricotLibrary.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class Label extends GUIObject {
	public String text = "";
	ColorScheme scheme;
	public Alignment align;
	private int width = 0;
	private int height = 0;
	public int fontSize = 18;
	boolean active = true;
	public boolean dropShadow = false;
	BufferedImage coin;
	BufferedImage ore;
	
	public enum Alignment {
		CENTER, LEFT, RIGHT;
	}

	public Label(String text, ColorScheme scheme, Apricot apricot, World world) {
		super(new Tuple(), apricot, world);
		this.text = text;
		this.scheme = scheme;
		world.add(this);
	}

	@Override
	public void input(InputEvent e) {
	}

	@Override
	public void remove() {
		world.remove(this);
	}

	@Override
	public int getRenderOrder() {
		return renderOrder;
	}

	@Override
	public void render(Graphics2D g) {
		if (!shown)
			return;

		g.setFont(new Font("Arial", Font.PLAIN, fontSize));
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		width = g.getFontMetrics(g.getFont()).stringWidth(text);
		height = g.getFontMetrics(g.getFont()).getHeight();
		int textWidth = align == Alignment.CENTER ? width : 0;
		if(align == Alignment.RIGHT) {
			textWidth = width * 2;
		}
		int textHeight = align == Alignment.CENTER ? height : height;

		if (dropShadow) {
			g.setColor(new Color(30, 30, 30, 255));
			g.drawString(text, (int) position.x - textWidth / 2, (int) position.y + textHeight / 2 + 1);
		}
		if (active) {
			g.setColor(scheme.textColor);
		} else {
			g.setColor(scheme.disabledTextColor);
		}
		g.drawString(text, (int) position.x - textWidth / 2, (int) position.y + textHeight / 2);
	}

	@Override
	public int height() {
		if (!shown)
			return 0;
		return height + margin;
	}

	@Override
	public int width() {
		if (!shown)
			return 0;
		return width + margin;
	}

}
