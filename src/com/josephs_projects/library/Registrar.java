package com.josephs_projects.library;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import com.josephs_projects.library.graphics.Render;
import com.josephs_projects.library.input.Keyboard;
import com.josephs_projects.library.input.Mouse;

public class Registrar {
    public ArrayList<Element> registry = new ArrayList<>();
    public static Random rand = new Random();
    private static JFrame frame;
    private static Canvas canvas;
    public static Mouse mouse;
    public static Keyboard keyboard;
    private static Render render;
    
    private boolean running = false;
    private double dt = 1000 / 60.0;
    public static int ticks = 0;

    public Registrar(String title, int width, int height){
        frame = new JFrame(title);
        canvas = new Canvas();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        canvas.setSize(width,height);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        mouse = new Mouse(canvas, this);
        keyboard = new Keyboard(canvas);
        
        render = new Render(width, height);

        running = true;
    }

    public void addElement(Element element){
        registry.add(element);
    }

    public void removeElement(Element element){
        registry.remove(element);
    }

    public void run() {
        long current = System.currentTimeMillis();
        while(running){
            while ((System.currentTimeMillis() - current) > dt){
                tick();
                current = System.currentTimeMillis();
                ticks++;
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
            e.render(render);
        }
        
        render.render(g);

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
