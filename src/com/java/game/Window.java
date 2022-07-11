package com.java.game;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	public Window(int w, int h, int s, String title, Game game) {
		game.setPreferredSize(new Dimension(w * s, h * s));
		game.setMaximumSize(new Dimension(w * s, h * s));
		game.setMinimumSize(new Dimension(w * s, h * s));

		JFrame jFrame = new JFrame(title);
		jFrame.add(game);
		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);
		jFrame.setResizable(false);
		jFrame.setVisible(true);
		
		game.start();
	}  
}