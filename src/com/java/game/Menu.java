package com.java.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Menu {
	private int w = 400, h = 300;
	private Image menu = new ImageIcon("res/title.png").getImage(); 
	
	public void render(Graphics g) {
		g.drawImage(menu, 0, 0, w * 2, h * 2, null);
			
		g.setColor(Color.white);
		g.drawString("����������: ", 20, 150);
		g.drawString("A - �����, D - ������, Space - ������, Esc - �����, P - �����", 20, 170);
		g.drawString("���� ������� gg35y [c] 2022", 20, 190);		
	}
}