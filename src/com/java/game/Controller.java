package com.java.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.java.game.Game.STATUS;

public class Controller extends KeyAdapter {
	private Handler handler;

	private Sound jump = new Sound("res/jump.wav", 0.8);

	public Controller(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.go.size(); i++) {
			GameObject obj = handler.go.get(i);	
			
			if(obj.getId() == ID.Player) {
				if(key == KeyEvent.VK_D) obj.setVelX(5);
				if(key == KeyEvent.VK_A) obj.setVelX(-5);	
				if(Player.hp >= -1)
					if(key == KeyEvent.VK_P) Game.status = STATUS.START; 
			
				if(key == KeyEvent.VK_SPACE && !obj.isJumping()) {
					obj.setJumping(true);
					obj.setVelY(-10);
					jump.audio();
					jump.setVolume();
				}
			} 
		}
		
		if(key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}	
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.go.size(); i++) {
			GameObject obj = handler.go.get(i);	
			
			if(obj.getId() == ID.Player) {
				if(key == KeyEvent.VK_D) obj.setVelX(0);
				if(key == KeyEvent.VK_A) obj.setVelX(0);		
			}
		}
	} 	
}