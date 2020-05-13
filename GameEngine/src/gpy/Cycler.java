package gpy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

import cor.Looper;
import fio.LevelLoader;
import lvl.Dungeon;
import lvl.LevelGenerator;
import lvl.Room;
import stt.DIR;
import stt.GAME_STATE;
import stt.OBJ_ID;


public class Cycler {

	public static final int DEFAULT_WIDTH = Looper.WIDTH / 12, DEFAULT_HEIGHT = DEFAULT_WIDTH,
			BLOCK_SIDE = Looper.WIDTH / 12;


	private ArrayList<GameObject> objects;
	public LevelLoader levLod;
	private LevelGenerator levGen;
	private Dungeon d;
	private Room currentRoom;

	public Cycler() {
		init();
	}

	public void init() {
		objects = new ArrayList<>();		
	}

	public void update() {
		try {
			for(GameObject object:objects) {
				object.update();
			}
		}catch(ConcurrentModificationException e) {}	
	}

	public void draw(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, Looper.WIDTH, Looper.HEIGHT);
		for(GameObject obj: objects) {
			obj.draw(g);
		}
	}


	public void addObject(int x, int y, int xv, int yv, OBJ_ID id) {
		GameObject obj = null;
		switch(id) {
		
		}
		this.objects.add(0,obj);

	}

	public void addObject(int x, int y, OBJ_ID id) {
		addObject(x, y, 0, 0, id);
	}

	public void addObject(GameObject object) {
		this.objects.add(object);
	}

	public ArrayList<GameObject> getObjs(){
		return objects;
	}

	public void removeObject(OBJ_ID id) {
		ArrayList<GameObject> toBeRemoved = new ArrayList<>();
		for(GameObject object: objects) {
			if(object.getId() == id) {
				toBeRemoved.add(object);
			}
		}
		for(GameObject object: toBeRemoved) {
			objects.remove(object);
		}
	}

	public void removeObject(GameObject object) {
		objects.remove(object);
	}
	

	public GameObject getObject(OBJ_ID id) {
		for(GameObject obj : objects) {
			if(obj.getId() == id) {
				return obj;
			}
		}
		return null;
	}


	public Dungeon getCurrentDungeon() {
		return d;
	}
	
	public Room getCurrentRoom() {
		return this.currentRoom;
	}

	public void nextRoom(DIR dir) {
		Looper.state = GAME_STATE.START;
		this.objects.clear();
		switch(dir) {
		case LEFT:
			this.addObject(Looper.WIDTH - DEFAULT_WIDTH*2, (Looper.HEIGHT - DEFAULT_HEIGHT) / 2, OBJ_ID.PLAYER);
			levLod.LoadRoom(currentRoom.getLeft());
			this.currentRoom = currentRoom.getLeft();
			break;
		case RIGHT:
			this.addObject(DEFAULT_HEIGHT, (Looper.HEIGHT - DEFAULT_HEIGHT) / 2, OBJ_ID.PLAYER);
			levLod.LoadRoom(currentRoom.getRight());
			this.currentRoom = currentRoom.getRight();
			break;
		case UP:
			this.addObject((Looper.WIDTH - DEFAULT_WIDTH) / 2, Looper.HEIGHT - DEFAULT_HEIGHT*2, OBJ_ID.PLAYER);
			levLod.LoadRoom(currentRoom.getUp());
			this.currentRoom = currentRoom.getUp();
			break;
		case DOWN:
			this.addObject((Looper.WIDTH - DEFAULT_WIDTH) / 2, DEFAULT_HEIGHT, OBJ_ID.PLAYER);
			levLod.LoadRoom(currentRoom.getDown());
			this.currentRoom = currentRoom.getDown();
			break;

		}
		Looper.state = GAME_STATE.OVERWORLD;
	}


}
