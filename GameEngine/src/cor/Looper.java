package cor;

import java.awt.Canvas;
import java.net.URL;
import java.util.ArrayList;

import fio.ImageLoader;
import fio.Music;
import gfx.StateManager;
import gfx.Window;
import gpy.Cycler;
import inp.Button;
import inp.KeyWatcher;
import inp.Menu;
import lvl.Dungeon;
import lvl.LevelGenerator;
import stt.GAME_STATE;


/**
 * 
 * @author Ander
 * This class contains the game loop, It's primary function
 * is to instantiate all the ap data and then loop over the data until
 * the program is killed
 */

public class Looper implements Runnable{
	
	/**
	 * All the big game components
	 */

	public static GAME_STATE state;
	private Cycler cycler;
	private StateManager sm;
	private ImageLoader il;
	public static Menu mw;
	private ArrayList<Button> buttons;
	private boolean running;
	private double pauseTime;
	private Canvas canvas;
	private Window window;
	public static int WIDTH,HEIGHT;
	private KeyWatcher kw;
	public URL megamanURL; 
	public static Music megamanMusic;

	/*
	 * Preps the thread
	 */
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	/*
	 * Instantiates all the important variables
	 */

	private void init() {
		this.running = true;
		this.pauseTime = 1000.0/60;
		this.canvas = new Canvas();
		this.WIDTH = 640;
		this.HEIGHT = 640;
		this.state = GAME_STATE.START;
		buttons = new ArrayList<Button>();
		cycler = new Cycler();
		window = new Window(canvas,"ENGINE",WIDTH,HEIGHT);
		il = new ImageLoader();
		il.init();
		kw = new KeyWatcher();
		mw = new Menu(buttons,cycler);
		canvas.requestFocus();
		canvas.addKeyListener(kw);
		canvas.addMouseListener(mw);
		sm = new StateManager(canvas,cycler,buttons);
		
		megamanURL = getClass().getResource("tune.wav");
		//megamanMusic = new Music();
		//megamanMusic.setURL(megamanURL);
		//megamanMusic.loop();
 	}
	
	/*
	 * This is the game loop. The actual state management occurs in the
	 * updater and drawer objects
	 */
	
	public void run() {
		this.init();
		while(running) {
			this.sm.paint();
			this.pause();
		}

	}
	
	/*
	 * Gotta get that sweet sweet framerate man
	 */

	public void pause() {
		try {
			Thread.sleep((long)pauseTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public static void resetMenu() {
		mw.reset();
	}




}
