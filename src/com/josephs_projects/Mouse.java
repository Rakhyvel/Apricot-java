package com.josephs_projects;

import com.josephs_projects.Registrar;
import com.josephs_projects.Tuple;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse extends MouseAdapter {
    private boolean mouseLeftDown = false;
    private boolean mouseRightDown = false;
    public int mouseWheelPosition;
    private final Canvas canvas;
    private final Registrar registrar;

    public Mouse(Canvas canvas, Registrar registrar) {
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
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            mouseRightDown = true;
        }
        registrar.input();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftDown = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            mouseRightDown = false;
        }
        registrar.input();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        registrar.input();
    }

    @Override
    public void mouseDragged(MouseEvent e){
        registrar.input();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelPosition = e.getWheelRotation();
        registrar.input();
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

    public Point getAWTPoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }

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
    public boolean getMouseLeftDown() {
        return mouseLeftDown;
    }

    /**
     * @return Whether the right mouse button (MouseEvent.BUTTON3) is pressed
     */
    public boolean getMouseRightDown() {
        return mouseRightDown;
    }

    /**
     * Used when iconifying the window, preventing loops
     */
    public void resetMouseDownValues() {
        mouseRightDown = false;
        mouseLeftDown = false;
    }

    public int getMouseWheelPosition(){
        return mouseWheelPosition;
    }
}
