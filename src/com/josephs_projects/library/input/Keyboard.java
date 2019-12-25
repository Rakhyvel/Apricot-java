package com.josephs_projects.library.input;

import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import com.josephs_projects.library.Registrar;

/**
 * This class handles keyboard input
 * 
 * @author Joseph Shimel
 *
 */
public class Keyboard extends KeyAdapter {
    private Set<Integer> keysDown = new HashSet<>();
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

    /**
     * Checks to see if a key is being pressed by the user.
     * 
     * @param key The KeyEvent keycode of the key to check for
     * @return Whether the key is currently being pressed
     */
    public boolean keyDown(int key){
        return keysDown.contains(key);
    }
}