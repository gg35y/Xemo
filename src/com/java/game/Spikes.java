package com.java.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Spikes extends GameObject {
	private Textures tex = Game.getTextures();
	private int type;
	
	public Spikes(float x, float y, int type, ID id) {
		super(x, y, id);
		this.type = type;
	}

	public void tick(LinkedList<GameObject> object) {}

	public void render(Graphics g) {	
		if(type == 1)
			g.drawImage(tex.spikes[0], (int)x, (int)y, 32, 32, null);
		if(type == 2)
			g.drawImage(tex.spikes[1], (int)x, (int)y, 32, 32, null);
		if(type == 3)
			g.drawImage(tex.spikes[2], (int)x, (int)y, 32, 32, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}	
} 