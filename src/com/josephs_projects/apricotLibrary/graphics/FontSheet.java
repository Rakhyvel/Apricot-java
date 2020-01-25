package com.josephs_projects.apricotLibrary.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.josephs_projects.apricotLibrary.Apricot;

/**
 * This class represents a 16x16 ASCII fontsheet. It has methods for extracting
 * each individual character
 * 
 * @author Joseph Shimel
 *
 */
public class FontSheet {
	private BufferedImage fontSheet;
	private final BufferedImage[] chars = new BufferedImage[256];

	public FontSheet(String path, int fontSheetWidth, int fontSheetHeight) throws IOException {
		int charWidth = fontSheetWidth / 16;
		int charHeight = fontSheetHeight / 16;
		fontSheet = Apricot.image.loadImage(path);
		for (int i = 0; i < 256; i++) {
			BufferedImage character = fontSheet.getSubimage((i & 15) * charWidth, (int) (i / 16) * charHeight,
					charWidth, charHeight);
			chars[i] = character;
		}
	}

	/**
	 * @param keyCode The ASCII value of the desired character
	 * @return A BufferedImage of that character. If none can be found, returns
	 *         image for the space character
	 */
	public BufferedImage getCharImage(int keyCode) {
		if (keyCode >= 0 && keyCode < chars.length) {
			return chars[keyCode];
		}
		return chars[0];
	}
}
