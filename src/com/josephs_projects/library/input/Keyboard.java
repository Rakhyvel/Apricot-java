package com.josephs_projects.library.input;

import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import com.josephs_projects.library.Registrar;

public class Keyboard extends KeyAdapter {
    /* TODO Make it so that if you press a key, then press another key while still holding onto the previous key, it'll
            remember that first key
     */
    private Set<Integer> keysDown = new HashSet<>();
    private boolean controlDown = false;
    private boolean shiftDown = false;
    private Registrar registrar;

    public Keyboard(Canvas canvas, Registrar registrar) {
        canvas.addKeyListener(this);
        this.registrar = registrar;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown.add(Integer.valueOf((int)e.getKeyCode()));
        registrar.input();
    }

    @Override
    public void keyReleased(KeyEvent e){
        keysDown.remove(Integer.valueOf((int)e.getKeyCode()));
        registrar.input();
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