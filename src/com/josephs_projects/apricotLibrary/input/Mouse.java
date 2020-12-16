package com.josephs_projects.apricotLibrary.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;

/**
 * This class handles key mouse and mousewheel input events.
 * 
 * @author Joseph Shimel
 *
 */
public class Mouse extends MouseAdapter {
	public boolean leftDown = false;
	public boolean rightDown = false;
	public int mouseWheelPosition;
	private final Apricot apricot;
	public Tuple position = new Tuple();
	public Tuple absolutePos = new Tuple();

	public Mouse(Apricot apricot) {
		this.apricot = apricot;
		apricot.canvas.addMouseListener(this);
		apricot.canvas.addMouseMotionListener(this);
		apricot.canvas.addMouseWheelListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftDown = true;
			if (apricot != null)
				apricot.input(InputEvent.MOUSE_LEFT_DOWN);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightDown = true;
			if (apricot != null)
				apricot.input(InputEvent.MOUSE_RIGHT_DOWN);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftDown = false;
			if (apricot != null)
				apricot.input(InputEvent.MOUSE_LEFT_RELEASED);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			rightDown = false;
			if (apricot != null)
				apricot.input(InputEvent.MOUSE_RIGHT_RELEASED);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (apricot != null) {
			position.x = getX();
			position.y = getY();
			getAbsPos();
			apricot.input(InputEvent.MOUSE_MOVED);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (apricot != null) {
			position.x = getX();
			position.y = getY();
			getAbsPos();
			apricot.input(InputEvent.MOUSE_DRAGGED);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelPosition = e.getWheelRotation();
		if (apricot != null)
			apricot.input(InputEvent.MOUSEWHEEL_MOVED);
	}

	/**
	 * @return The x coordinate of the position of the mouse relative to the
	 *         top-left corner of the screen.
	 */
	double getX() {
		Point mousePos = apricot.canvas.getMousePosition();
		if (mousePos != null) {
			return mousePos.x;
		}
		return -1;
	}

	/**
	 * @return The y coordinate of the position of the mouse relative to the
	 *         top-left corner of the screen
	 */
	double getY() {
		Point mousePos = apricot.canvas.getMousePosition();
		if (mousePos != null)
			return mousePos.y;
		return -1;
	}

	private void getAbsPos() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		absolutePos.x = p.getX();
		absolutePos.y = p.getY();
	}

	/**
	 * @return Position of the mouse using AWT's Point class
	 */
	public Point getAWTPoint() {
		return MouseInfo.getPointerInfo().getLocation();
	}

	/**
	 * @return Position of the mouse using Tuple object
	 */
	public Tuple getTuple() {
		return new Tuple(getX(), getY());
	}

	/**
	 * @return The x coordinate of the position of the mouse relative to the
	 *         top-left corner of the screen.
	 */
	public double getRawX() {
		return MouseInfo.getPointerInfo().getLocation().getX();
	}

	/**
	 * @return The y coordinate of the position of the mouse relative to the
	 *         top-left corner of the screen
	 */
	public double getRawY() {
		return MouseInfo.getPointerInfo().getLocation().getY();
	}
}
