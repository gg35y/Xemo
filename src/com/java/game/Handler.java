package com.java.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
	public LinkedList<GameObject> go = new LinkedList<GameObject>();
	
	private GameObject object;
	private Camera camera;
	
	private BufferedImage level2, level3, level4;
	
	public Handler(Camera camera) {
		this.camera = camera;
		
		Loader loader = new Loader();
		level2 = loader.load("res/level2.png");
		level3 = loader.load("res/level3.png");		
		level4 = loader.load("res/level4.png");		
	}
	
	public void tick() {
		for(int i = 0; i < go.size(); i++) {
			object = go.get(i);
			object.tick(go);
		}
	}
 
	public void render(Graphics g) {
		for(int i = 0; i < go.size(); i++) {
			object = go.get(i);
			object.render(g);
		}
	}
	
	public void renderLevel(BufferedImage level) {
		int w = level.getWidth();
		int h = level.getHeight();
		
		for(int xx = 0; xx < h; xx++) {
			for(int yy = 0; yy < w; yy++) {
				int pixel = level.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff; 
				int blue = (pixel) & 0xff;

				if(red == 0 && green == 0 && blue == 255) addObject(new Player(xx * 32, yy * 32, this, camera, ID.Player));

				if(red == 255 && green == 255 && blue == 255) addObject(new Block(xx * 32, yy * 32, 1, ID.Block));
				if(red == 158 && green == 158 && blue == 158) addObject(new Block(xx * 32, yy * 32, 0, ID.Block));

				if(red == 255 && green == 0 && blue == 0) addObject(new Spikes(xx * 32, yy * 32, 1, ID.Spikes)); // top
				if(red == 254 && green == 0 && blue == 0) addObject(new Spikes(xx * 32, yy * 32, 2, ID.Spikes)); // right 
				if(red == 253 && green == 0 && blue == 0) addObject(new Spikes(xx * 32, yy * 32, 3, ID.Spikes)); // left
				
				if(red == 233 && green == 255 && blue == 0) addObject(new Gold(xx * 32, yy * 32, ID.Gold));  

				if(red == 255 && green == 0 && blue == 255) addObject(new Flag(xx * 32, yy * 32, ID.Flag));
				if(red == 254 && green == 0 && blue == 254) addObject(new Flag(xx * 32, yy * 32, ID.FlagEnd));

				if(red == 0 && green == 255 && blue == 0) addObject(new Plate(xx * 32, yy * 32, ID.Plate1));
				if(red == 0 && green == 254 && blue == 0) addObject(new Plate(xx * 32, yy * 32, ID.Plate2));
				if(red == 0 && green == 253 && blue == 0) addObject(new Plate(xx * 32, yy * 32, ID.Plate3));
			}		
		}
	}
	
	public void switchLevel() {
		clearLevel();
		camera.setX(0);
		
		switch(Game.LEVEL) {
		case 1:
			renderLevel(level2);
			Game.LEVEL++;
			break;
		case 2:
			renderLevel(level3);
			Game.LEVEL++;
			break;	
		case 3:
			renderLevel(level4);
			Game.LEVEL++;
			break;				
		}
	}
	
	public void clearLevel() {
		go.clear();
	}
	
	public void addObject(GameObject go) {
		this.go.add(go);
	}

	public void removeObject(GameObject go) {
		this.go.remove(go);
	}
}