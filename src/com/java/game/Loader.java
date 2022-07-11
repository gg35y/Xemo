package com.java.game;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Loader {
	private BufferedImage image;
	
	public BufferedImage load(String path) {
		try {
			image = ImageIO.read(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
} 