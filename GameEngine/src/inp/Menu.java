package inp;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import cor.Looper;
import gpy.Cycler;
import stt.BUTTON_ID;
import stt.GAME_STATE;

public class Menu implements MouseListener{

	ArrayList<Button> buttons;
	Cycler cycler;

	public Menu(ArrayList<Button> buttons, Cycler cycler) {
		this.buttons = buttons;
		this.cycler = cycler;
		reset();
	}
	
	public void reset() {
		buttons.add(new Button(10,Looper.HEIGHT - 128,128,64,BUTTON_ID.START));
		buttons.add(new Button(Looper.WIDTH-128-10,Looper.HEIGHT - 128,128,64,BUTTON_ID.HELP));
		buttons.add(new Button(Looper.WIDTH/2-128/2,Looper.HEIGHT - 128,128,64,BUTTON_ID.QUIT));
	}

	public void mouseClicked(MouseEvent e) {
		try {
			for(Button b: buttons) {
				if(b.pointWithin(e.getX(), e.getY())) {
					switch(b.getId()) {
					case START:
						break;
					case HELP:
						Looper.state = GAME_STATE.HELP;
						break;
					case QUIT:
						System.exit(1);
						break;
					case BACK:
						break;
					}
				}
			}
		}catch(ConcurrentModificationException cme) {}

	}


	public void mousePressed(MouseEvent e) {


	}


	public void mouseReleased(MouseEvent e) {


	}


	public void mouseEntered(MouseEvent e) {


	}


	public void mouseExited(MouseEvent e) {


	}

}
