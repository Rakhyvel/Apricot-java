package com.josephs_projects.apricotLibrary.graphics;

/**
 * This class is similar to the SpriteSheet class, except it has code for kerning.
 * 
 * @author Joseph Shimel
 *
 */
public class Font {
	
	SpriteSheet charset;
	private int size;
	// This array is a pretty good estimate of non-monospace, 16 bit font kerning.
	private static int[] kern16 = {
			0,0,4,4,4,1,7,16,7,0,0,6,5,0,6,5,
			7,6,5,4,5,7,7,4,5,4,6,6,0,0,0,0,
			8,3,5,9,7,12,9,2,4,4,5,8,2,4,2,6,
			7,5,7,7,8,7,7,7,7,7,2,2,8,8,8,7,
			13,11,9,9,9,9,8,10,9,3,7,9,7,11,9,10,
			9,10,9,9,9,9,10,13,9,10,7,4,5,4,5,9,
			3,7,7,7,7,7,6,7,7,3,4,7,3,11,7,7,
			8,7,5,7,5,7,7,11,7,7,8,5,3,5,8,4};

	public Font(SpriteSheet charset, int size) {
		this.charset = charset;
		this.setSize(size);
	}

	/**
	 * @param letter  The ascii code of the letter to be returned
	 * @return  The image of the letter specified
	 */
	public int[] getLetter(int letter) {
		return charset.getSubset(letter % 16, letter / 16, getSize());
	}

	/**
	 * @param index  The ascii code of the letter to be returned
	 * @return  The width of the letter
	 */
	public int getKern(int index) {
		if (getSize() == 32)
			return kern16[index]*2+4;
		if (index > kern16.length) {
			return 10;
		}
		return kern16[index]+1;
	}
	
	/**
	 * @param label  The string to be measured
	 * @return  How long the string would be if drawn
	 */
	public int getStringWidth(String label) {
		int length = 0;
		for(int i = 0; i < label.length(); i++) {
			length+=getKern(label.charAt(i));
		}
		return length;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
