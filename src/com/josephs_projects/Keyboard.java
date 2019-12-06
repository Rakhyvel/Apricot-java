package com.josephs_projects;

import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Keyboard extends KeyAdapter {
    /* TODO Make it so that if you press a key, then press another key while still holding onto the previous key, it'll
            remember that first key
     */
    private Set<Integer> keysDown = new HashSet<>();
    private boolean controlDown = false;
    private boolean shiftDown = false;

    public Keyboard(Canvas canvas) {
        canvas.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.add(Integer.valueOf((int)e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e){
        keysDown.remove(Integer.valueOf((int)e.getKeyCode()));
    }

    public boolean keyDown(int key){
        return keysDown.contains(key);
    }

    /**
     * @return Whether or not the control key is being pressed
     */
    public boolean isControlDown() {
        return controlDown;
    }

    /**
     * @return Whether or not the shift key is being pressed
     */
    public boolean isShiftDown() {
        return shiftDown;
    }
}