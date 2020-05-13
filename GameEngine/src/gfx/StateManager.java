package gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import cor.Looper;
import fio.ImageLoader;
import gpy.Cycler;
import inp.Button;
import stt.BUTTON_ID;
import stt.GAME_STATE;

public class StateManager {
	
	private Canvas canvas;
	private Cycler cycler;
	private ArrayList<Button> buttons;
	
	public StateManager(Canvas canvas, Cycler cycler, ArrayList<Button> buttons) {
		this.canvas = canvas;
		this.cycler = cycler;
		this.buttons = buttons;
	}

	public void paint() {
		if(canvas.getBufferStrategy()==null) {
			canvas.createBufferStrategy(3);
		}
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics g = bufferStrategy.getDrawGraphics();
		this.stateManage(g);
		bufferStrategy.show();
		g.dispose();		
	}
	
	private void stateManage(Graphics g) {
		switch(Looper.state) {
		case OVERWORLD:
			this.overworldUpdate(g);
			break;
		}
	}
	
	private void overworldUpdate(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0,Looper.WIDTH, Looper.HEIGHT);
		if(Looper.state == GAME_STATE.OVERWORLD) {
			cycler.update();
			cycler.draw(g);
		}
		
	}
}
