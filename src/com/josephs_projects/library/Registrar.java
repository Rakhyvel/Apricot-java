package com.josephs_projects.library;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JFrame;

import com.josephs_projects.library.graphics.Render;
import com.josephs_projects.library.input.Keyboard;
import com.josephs_projects.library.input.Mouse;

public class Registrar {
    public ArrayList<Element> registry = new ArrayList<>();
    public static Random rand = new Random();
    public static JFrame frame;
    public static Canvas canvas;
    public static Mouse mouse;
    public static Keyboard keyboard;
    private static Render render;
    
    private boolean running = false;
    private double dt = 1000 / 60.0;
    public static int ticks = 0;
    
    Comparator<Element> comparator = new YPositionComparator();

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
        keyboard = new Keyboard(canvas, this);
        
        render = new Render(width, height);

        running = true;
    }

    public void addElement(Element element){
        registry.add(element);
    }

    public void removeElement(Element element){
        registry.remove(element);
    }
    
    public boolean registryContains(Element element) {
		return registry.contains(element);
	}
    
    public Element getElement(int index) {
    	return registry.get(index);
    }
    
    public int registrySize() {
    	return registry.size();
    }

    public void run() {
        double previous = System.currentTimeMillis();
        double lag = 0;
        while(running){
        	double current = System.currentTimeMillis();
        	double elapsed = current - previous;
        	
        	previous = current;

        	lag+= elapsed;
        	
        	while(lag >= dt) {
        		tick();
        		lag -= dt;
        	}
        	
        	render();        	
        }
    }

    private void tick() {
        for (int i = 0; i < registry.size(); i++){
            registry.get(i).tick();
        }
    }

    private void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null){
            canvas.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        registry.sort(comparator);
        for (int i = 0; i < registry.size(); i++){
        	registry.get(i).render(render);
        }
        
        render.render(g);

        g.dispose();
        bs.show();
    }

    public void input(){
    	for (int i = 0; i < registry.size(); i++){
            registry.get(i).input();
        }
    }

    public void stop(){
        running = false;
        System.exit(0);
    }
    
    public class YPositionComparator implements Comparator<Element> {
		@Override
		public int compare(Element arg0, Element arg1) {
			if(arg0 == null)
				return 0;
			if(arg1 == null)
				return 0;
			if(arg0.getPosition() == null)
				return 0;
			if(arg1.getPosition() == null)
				return 0;
						
			return (int)(arg0.getPosition().getY() * 65 - arg1.getPosition().getY() * 65);
		}
    }
}
