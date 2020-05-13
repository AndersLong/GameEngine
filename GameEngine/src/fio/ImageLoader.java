package fio;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	
	int imgD = 32;
	int tileD = 16;
	

	public void init() {
		
	}
	
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
		} catch (IOException e) {
			System.exit(1);
			return null;
		}
	}
	
	private BufferedImage getSubImage(BufferedImage img, int x, int y, int w, int h) {
		return img.getSubimage(x, y, w, h);
	}
}
