package com.java.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Thread thread;
	
	private Handler handler;
	private Camera camera;
	private Loader loader;
	private static Textures tex;
	private Menu menu;
	
	public static int WIDTH, HEIGHT;
	
	private BufferedImage level1;
	private Image dead = new ImageIcon("res/over.png").getImage();
	private Image win = new ImageIcon("res/win.png").getImage();
	
	public static int LEVEL = 1;
	
	public static enum STATUS {
		MENU,
		START,
		OVER,
		WIN;
	}
	
	public static STATUS status = STATUS.MENU;
	
	public Game() {}
	
	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		camera = new Camera(0, 0);
		handler = new Handler(camera);
		loader = new Loader();
		tex = new Textures();
		
		level1 = loader.load("res/level1.png");
		handler.renderLevel(level1);
		
		menu = new Menu();
		
		this.addKeyListener(new Controller(handler));
	}
	
	public synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {}
	
	public void run() {
		init();
		createBufferStrategy(3);
		requestFocus();
		int fps = 60;
		double interval = 1000000000 / fps; 
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime, timer = 0;
		int frames = 0;
		while(running) { // thread != null
			currentTime  = System.nanoTime();
			delta += (currentTime - lastTime) / interval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			if(delta >= 1) {
				updates();
				render();			
				delta--;
				frames++;	
			}
			
			if(timer >= 1000000000) {
				System.out.println(frames + " fps");
				frames = 0;
				timer = 0;
			}
		}	
	}	

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(status == STATUS.MENU)
			menu.render(g);
		
		if(status == STATUS.OVER) {
			g.drawImage(dead, 0, 0, getWidth(), getHeight(), null);
			g.setColor(Color.white);
			g.drawString("Всего золота: " + Player.gold, 20, 150);
		}
		
		if(status == STATUS.WIN)
			g.drawImage(win, 0, 0, getWidth(), getHeight(), null);
		
		if(status == STATUS.START) {
			g2.translate(camera.getX(), camera.getY());
			handler.render(g);
			g2.translate(-camera.getX(), -camera.getY());
			
			g.setColor(Color.red);
			g.drawString("HP: " + Player.hp, 5, 20);
			g.setColor(Color.red);
			g.drawString("Золото: " + Player.gold, 5, 30);	
		}		
		
		g.dispose();
		bs.show();
	}

	private void updates() {
		handler.tick();
		for(int i = 0; i < handler.go.size(); i++) {
			if(handler.go.get(i).getId() == ID.Player)
				camera.tick(handler.go.get(i));
		}	
	}
		
	public static Textures getTextures() {
		return tex;
	}
	
	public static void main(String[] args) {
		new Window(400, 300, 2, "Xemo", new Game());
	}
}