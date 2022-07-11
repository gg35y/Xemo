package com.java.game;

import java.awt.image.BufferedImage;

public class Textures {
	private SpriteSheet ss;
	private BufferedImage tex = null; 

	public BufferedImage[] block = new BufferedImage[3];
	public BufferedImage[] spikes = new BufferedImage[3];	
	public BufferedImage[] item = new BufferedImage[4];
	public BufferedImage[] player = new BufferedImage[6];
	public BufferedImage[] monsters = new BufferedImage[2];

	public Textures() {
		Loader loader = new Loader();
		tex = loader.load("res/res.png");
		
		ss = new SpriteSheet(tex);
		getTextures();
	}

	private void getTextures() {
		block[0] = ss.get(1, 3, 16, 16);
		block[1] = ss.get(2, 3, 16, 16);
		
		spikes[0] = ss.get(1, 4, 16, 16);
		spikes[1] = ss.get(2, 4, 16, 16);
		spikes[2] = ss.get(3, 4, 16, 16);
				
		item[0] = ss.get(4, 1, 16, 16);
		item[1] = ss.get(5, 1, 16, 16);
		item[2] = ss.get(6, 1, 16, 16);
		item[3] = ss.get(2, 5, 16, 16);
		
		monsters[0] = ss.get(1, 5, 16, 16);
		
		player[0] = ss.get(1, 1, 16, 16); // right 1
		player[1] = ss.get(2, 1, 16, 16); // right 1.2
		player[2] = ss.get(3, 1, 16, 16); // right 2.2
		player[3] = ss.get(1, 2, 16, 16); // left 1
		player[4] = ss.get(2, 2, 16, 16); // left 1.1
		player[5] = ss.get(3, 2, 16, 16); // left 1.2
	} 
} 