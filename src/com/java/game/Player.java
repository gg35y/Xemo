package com.java.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.java.game.Game.STATUS;

public class Player extends GameObject {
	private float width = 32, height = 32;
	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
	private int f = 1;
	public static int hp = 20, gold = 0;
	
	private Handler handler;
	private Textures tex = Game.getTextures();

	private Animation walkRight, walkLeft;
	private Camera camera;
	
	private Sound pickup = new Sound("res/pickup.wav", 0.8);
	private Sound next = new Sound("res/next.wav", 0.8);

	public Player(float x, float y, Handler handler, Camera camera, ID id) {
		super(x, y, id);
		this.handler = handler;
		this.camera = camera;
		
		walkRight = new Animation(5, tex.player[0], tex.player[1], tex.player[2]);
		walkLeft = new Animation(5, tex.player[3], tex.player[4], tex.player[5]);
	}

	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY; 
		
		if(velX < 0) 
			f = -1;
		if(velX > 0)
			f = 1;
		
		if(falling || jumping) {
			velY += gravity;
			
			if(velY > MAX_SPEED) 
				velY = MAX_SPEED;	
		} 
		
		Collision(object);
		walkRight.run();
		walkLeft.run();
	}

	private void Collision(LinkedList<GameObject> object) {
		for(int i = 0; i < handler.go.size(); i++) {
			GameObject obj = handler.go.get(i);
	
			if(obj.getId() == ID.Block) {
				if(getBoundsTop().intersects(obj.getBounds())) {
					y = obj.getY() + 32;
					velY = 0;
				}
					
				if(getBounds().intersects(obj.getBounds())) {
					y = obj.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				} else
					falling = true;
			
				if(getBoundsRight().intersects(obj.getBounds())) 
					x = obj.getX() - 32;
				
				if(getBoundsLeft().intersects(obj.getBounds())) 
					x = obj.getX() + 32;
			} else if(obj.getId() == ID.Flag)
				if(getBounds().intersects(obj.getBounds())) {
					next.audio();
					next.setVolume();
					handler.switchLevel();
					camera.setX(0);
			} 
			
			if(obj.getId() == ID.FlagEnd)
				if(getBounds().intersects(obj.getBounds())) 
					Game.status = STATUS.WIN;					
			
			if(obj.getId() == ID.Spikes) 
				if(getBounds().intersects(obj.getBounds())) 
					hp-=20;		
			
			if(obj.getId() == ID.Bullet) 
				if(getBounds().intersects(obj.getBounds())) 
					hp-=10;			
			
			if(obj.getId() == ID.Gold) 
				if(getBounds().intersects(obj.getBounds())) {
					pickup.audio();
					pickup.setVolume();
					gold++;
					handler.removeObject(obj);
				}
						
			if(hp == 0)
				Game.status = STATUS.OVER; 
		}
	}

	public void render(Graphics g) {
		for(int i = 0; i < handler.go.size(); i++) {
			GameObject obj = handler.go.get(i);
			
			if(obj.getId() == ID.Plate1) 
				if(getBounds().intersects(obj.getBounds())) {
					g.setColor(Color.red);
					g.drawString("Шипы убивают с одного раза!", (int)x-50, (int)y-10);
				}				
			
			if(obj.getId() == ID.Plate2) 
				if(getBounds().intersects(obj.getBounds())) {
					g.setColor(Color.red);
					g.drawString("Флаг является переходом на следующй уровень!", (int)x-120, (int)y-10);
				}
			
			if(obj.getId() == ID.Plate3) 
				if(getBounds().intersects(obj.getBounds())) {
					g.setColor(Color.red);
					g.drawString("Иногда приходится выбирать!", (int)x-50, (int)y-10);
				}
		}
		
			if(velX != 0) {
				if(f == 1)
					walkRight.drawAnim(g, (int)x, (int)y, 32, 32);
				else 
					walkLeft.drawAnim(g, (int)x, (int)y, 32, 32);
			}				
			else 
				g.drawImage(tex.player[0], (int)x, (int)y, 32, 32, null);
	}

	public Rectangle getBounds() { 
		return new Rectangle((int) ((int)x + (width/2)-((width/2)/2)), (int) ((int)y + (height/2)), (int)width/2, (int)height/2);
	}
	
	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int)x + (width/2)-((width/2)/2)), (int)y + 5, (int)width/2, (int)height/2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int)x + width - 5), (int)y + 5, (int)5, (int)height - 10);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x, (int)y + 5, (int)5, (int)height - 10);
	}
}