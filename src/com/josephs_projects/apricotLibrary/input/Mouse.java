package com.josephs_projects.apricotLibrary.input;

import java.awt.*;
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
    private boolean mouseLeftDown = false;
    private boolean mouseRightDown = false;
    public int mouseWheelPosition;
    private final Canvas canvas;
    private final Apricot registrar;

    public Mouse(Canvas canvas, Apricot registrar) {
        this.canvas = canvas;
        this.registrar = registrar;
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftDown = true;
            registrar.input(InputEvent.MOUSE_LEFT_DOWN);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            mouseRightDown = true;
            registrar.input(InputEvent.MOUSE_RIGHT_DOWN);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftDown = false;
            registrar.input(InputEvent.MOUSE_LEFT_RELEASED);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            mouseRightDown = false;
            registrar.input(InputEvent.MOUSE_RIGHT_RELEASED);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e){
        registrar.input(InputEvent.MOUSE_MOVED);
    }

    @Override
    public void mouseDragged(MouseEvent e){
        registrar.input(InputEvent.MOUSE_DRAGGED);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelPosition = e.getWheelRotation();
        registrar.input(InputEvent.MOUSEWHEEL_MOVED);
    }

    /**
     * @return The x coordinate of the position of the mouse relative to the
     *         top-left corner of the screen.
     */
    public int getX() {
        Point mousePosition = canvas.getMousePosition();
        return mousePosition != null ? mousePosition.x : -1;
    }

    /**
     * @return The y coordinate of the position of the mouse relative to the
     *         top-left corner of the screen
     */
    public int getY() {
        Point mousePosition = canvas.getMousePosition();
        return mousePosition != null ? mousePosition.y : -1;
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
    public Tuple getTuple(){
        return new Tuple(getX(), getY());
    }

    /**
     * @return The x coordinate of the position of the mouse relative to the
     *         top-left corner of the screen.
     */
    public int getRawX() {
        return (int) MouseInfo.getPointerInfo().getLocation().getX();
    }

    /**
     * @return The y coordinate of the position of the mouse relative to the
     *         top-left corner of the screen
     */
    public int getRawY() {
        return (int) (int) MouseInfo.getPointerInfo().getLocation().getY();
    }

    /**
     * @return Whether the left mouse button (MouseEvent.BUTTON1) is pressed
     */
    public boolean isLeftDown() {
        return mouseLeftDown;
    }

    /**
     * @return Whether the right mouse button (MouseEvent.BUTTON3) is pressed
     */
    public boolean isRightDown() {
        return mouseRightDown;
    }

    public void resetMouseDownValues() {
        mouseRightDown = false;
        mouseLeftDown = false;
    }

    public int getWheelPosition(){
        return mouseWheelPosition;
    }
}
