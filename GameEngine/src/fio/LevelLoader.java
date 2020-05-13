package fio;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import gpy.Cycler;
import gpy.GameObject;
import lvl.Room;
import stt.OBJ_ID;

/**
 * 
 * @author Ander
 * This class has the purpose of loading levels into the game
 * with image level templates
 */

public class LevelLoader {


	private Cycler cycler;

	public LevelLoader(Cycler cycler) {
		this.cycler = cycler;
	}	

	/*
	 * Determines which level to load given an enumerated level type
	 * 
	 */


	public void LoadRoom(Room r) {
		System.out.println(r);
		loadAllPixels(r.getImage());		
	}

	private void loadAllPixels(BufferedImage lvl) {
		for(int i = 0; i < lvl.getTileWidth(); i++) {
			for(int j = 0; j < lvl.getTileHeight(); j++) {
				Color c = new Color(lvl.getRGB(i, j));
				this.loadSinglePixel(i*Cycler.BLOCK_SIDE,j*Cycler.BLOCK_SIDE,c);
			}
		}
	}

	private void loadSinglePixel(int x, int y, Color c) {

	}

}
