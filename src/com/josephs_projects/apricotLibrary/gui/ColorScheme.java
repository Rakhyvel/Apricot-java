package com.josephs_projects.apricotLibrary.gui;

import java.awt.Color;

public class ColorScheme {
	public final Color backgroundColor;
	public final Color borderColor;
	public final Color highlightColor;
	public final Color textColor;
	public final Color disabledTextColor;
	public final Color errorColor;
	public final Color fillColor;
	public Color whatever;

	public ColorScheme(Color backgroundColor, Color borderColor, Color highlightColor, Color textColor,
			Color disabledTextColor, Color errorColor, Color fillColor) {
		this.fillColor = fillColor;
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
		this.highlightColor = highlightColor;
		this.textColor = textColor;
		this.disabledTextColor = disabledTextColor;
		this.errorColor = errorColor;
	}

}
