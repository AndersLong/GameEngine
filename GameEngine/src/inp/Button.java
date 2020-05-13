package inp;

import java.awt.Graphics;

import fio.ImageLoader;
import gpy.Locatable;
import stt.BUTTON_ID;


/**
 * This contains the data associated with a generic button
 * @author Ander
 * @version 1.0
 */

public class Button extends Locatable{

	
	BUTTON_ID id;
	
	public Button(int x, int y, int w, int h, BUTTON_ID id) {
		super(x, y);
		this.w = w;
		this.h = h;
		this.id = id;
	}
	
	public BUTTON_ID getId() {
		return id;
	}
	
	public void draw(Graphics g) {
		switch(id) {
		case START:
		
			break;
		case HELP:
		
			break;
		case QUIT:
			
			break;
		case BACK:
			
			break;
			
		}
	}


}
