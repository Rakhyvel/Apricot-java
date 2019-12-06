package com.josephs_projects;

import com.josephs_projects.io.Keyboard;
import com.josephs_projects.io.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Registrar {
    public ArrayList<Element> registry = new ArrayList<>();
    private static JFrame frame;
    private static Canvas canvas;
    public static Mouse mouse;
    public static Keyboard keyboard;
    private boolean running = false;
    private int dt = 1000;

    public Registrar(String title, int width, int height){
        frame = new JFrame(title);
        canvas = new Canvas();
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        mouse = new Mouse(canvas, this);
        keyboard = new Keyboard(canvas, this);

        running = true;
    }

    public void addElement(Element element){
        registry.add(element);
    }

    public void removeElement(Element element){
        registry.remove(element);
    }

    public void run() {
        long previous = System.currentTimeMillis();
        int lag = 0;
        while(running){
            long current = System.currentTimeMillis();
            long elapsed = current - previous;
            lag += elapsed;
            while (lag >= dt){
                tick();
                lag -= dt;
            }
            render();
        }
    }

    private void tick() {
        for (Element e : registry){
            e.tick();
        }
    }

    private void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null){
            canvas.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        for(Element e : registry){
            e.render(g);
        }

        g.dispose();
        bs.show();
    }

    public void input(){
        for(Element e : registry){
            e.input();
        }
    }

    public void stop(){
        running = false;
        System.exit(0);
    }
}
