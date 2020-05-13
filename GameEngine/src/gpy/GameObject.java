package gpy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import cor.Looper;
import inp.KeyWatcher;
import stt.DIR;
import stt.OBJ_ID;
import stt.OBJ_STATE;


/**
 * 
 * @author Ander
 * @summary
 * The core class of this GameEngine. GameObject contains most of the scripts that can be
 * associated with any object in the game. By selecting behaviors from a long list in this class
 * one can make a unique enemy/bullet/item/player quickly
 */

public abstract class GameObject extends Locatable implements Comparable<OBJ_ID>{

	/**
	 * allows us to sort a collection of GameObjects
	 */

	public static Comparator<GameObject> byId = new Comparator<>() {

		public int compare(GameObject o1, GameObject o2) {
			return o1.getId().compareTo(o2.getId());
		}
	};


	protected ArrayList<OBJ_ID> obstacles, actors;
	protected boolean lcol,rcol,ucol,dcol;
	protected int xv,yv;
	protected Cycler cycler;
	protected OBJ_ID id;
	protected OBJ_STATE stt;
	protected int speed;
	protected int hittimer, shootTimer, drawTimer, lifeTimer, singleBehaviorTimer, damageOutPut, moveTimer;
	protected int hp;
	protected DIR dir;

	public GameObject(int x, int y, int xv, int yv, Cycler cycler, OBJ_ID id) {
		super(x, y);
		this.dir = DIR.DOWN;
		this.xv = xv;
		this.yv = yv;
		this.cycler = cycler;
		this.id = id;
		this.stt = OBJ_STATE.NORM;
		this.obstacles = new ArrayList<>();
		this.actors = new ArrayList<>();
		this.w = Cycler.DEFAULT_WIDTH;
		this.h = Cycler.DEFAULT_HEIGHT;
	}

	public GameObject(int x, int y,Cycler cycler, OBJ_ID id) {
		this(x, y, 0, 0, cycler, id);
	}

	/*
	 * The two main functions of any GO, update is associated with collision and movement, while
	 * draw is used to display the GO to the screen
	 */

	public abstract void update();

	public abstract void draw(Graphics g);

	public abstract void reset();

	public abstract void action(DIR dir, GameObject obj);

	/**
	 *  Simple helper method for displaying a sprite
	 */

	protected void drawImg(Graphics g, BufferedImage img) {
		g.drawImage(img, x, y, w, h, null);
	}

	/**
	 * Checks if, from a collection of ids, this GO is touching any of them on a given side
	 */

	private GameObject touchingInstanceOf(ArrayList<OBJ_ID> ids,DIR side) {
		for(GameObject b: cycler.getObjs()) {
			for(OBJ_ID id: ids) {
				if(b.getId()==id) {
					if(this.touching(b, side)) {
						return b;
					}
				}
			}

		}
		return null;
	}

	/**
	 * manages timer logic
	 */

	protected void timer() {

		if(drawTimer > 0) {
			drawTimer --;
		}else {
			drawTimer = 40;
		}
		if(singleBehaviorTimer > 0) {
			singleBehaviorTimer--;
		}
	}

	public void lifeTimer() {
		if(this.lifeTimer > 0) {
			this.lifeTimer--;
		}else {
			cycler.removeObject(this);
		}
	}

	/**
	 * registers if this GO is touching another GO b on a given side,
	 * It does this by using coordsTouching to check the given side of
	 * this GO and the opposite side of b for a collision
	 * 
	 */

	private boolean touching(GameObject b, DIR side) {
		switch(side) {
		case LEFT:
			if(coordsTouching(b,side) || (b.coordsTouching(this, DIR.RIGHT))) {
				return true;
			}
			break;
		case RIGHT:
			if(coordsTouching(b,side) || (b.coordsTouching(this, DIR.LEFT))) {
				return true;
			}
			break;
		case UP:
			if(coordsTouching(b,side) || (b.coordsTouching(this, DIR.DOWN))) {
				return true;
			}
			break;
		case DOWN:
			if(coordsTouching(b,side) || (b.coordsTouching(this, DIR.UP))) {
				return true;
			}						
			break;
		}
		return false;
	}

	/**
	 * The actual meat of the collision system, checks to see if there is an
	 * actual overlap of two objects
	 */

	private boolean coordsTouching(GameObject b,DIR side) {
		switch(side) {
		case DOWN:
			if((this.getX()+this.getW()/8>b.getX()&&this.getX()+this.getW()/8<b.getX()+b.getW()) ||  
					(this.getX()+this.getW()*7/8>b.getX()&&this.getX()+this.getW()*7/8<b.getX()+b.getW())){
				if(this.getY()+this.getH()>b.getY()&&this.getY()+this.getH()<b.getY()+b.getH()) {
					return true;
				}
			}
			break;
		case UP:
			if((this.getX()+this.getW()/8>b.getX()&&this.getX()+this.getW()/8<b.getX()+b.getW()) ||  
					(this.getX()+this.getW()*7/8>b.getX()&&this.getX()+this.getW()*7/8<b.getX()+b.getW())){
				if(this.getY()>b.getY()&&this.getY()<b.getY()+b.getH()) {
					return true;
				}
			}
			break;
		case LEFT:
			if((this.getY()+this.getH()/8>b.getY() && this.getY()+this.getH()/8<b.getY()+b.getH())
					|| (this.getY()+this.getH()*7/8>b.getY() && this.getY()+this.getH()*7/8<b.getY()+b.getH())) {
				if(this.getX()>b.getX() && this.getX()<b.getX()+b.getW()) {
					return true;
				}
			}
			break;
		case RIGHT:
			if(((this.getY()+this.getH()/8>b.getY()) && (this.getY()+this.getH()/8<b.getY()+b.getH()))
					|| (this.getY()+this.getH()*7/8>b.getY()) && (this.getY()+this.getH()*7/8<b.getY()+b.getH())) {
				if((this.getX()+this.getW()>b.getX()) && (this.getX()+this.getW()<b.getX()+b.getW())) {
					return true;
				}
			}
			break;
		}		
		return false;
	}

	/**
	 * handles behavior of this GO hitting anything that can hurt it
	 */

	protected void actionCollision() {
		if(this.hittimer > 0) {
			this.hittimer--;
		}else {
			this.stt = OBJ_STATE.NORM;
		}
		GameObject collided = this.touchingInstanceOf(actors, DIR.LEFT);
		if(collided != null) {
			collided.action(DIR.LEFT, this);
			return;
		}
		collided = this.touchingInstanceOf(actors, DIR.RIGHT);
		if(collided != null) {
			collided.action(DIR.RIGHT, this);
			return;
		}
		collided = this.touchingInstanceOf(actors, DIR.UP);
		if(collided != null) {
			collided.action(DIR.UP, this);
			return;
		}
		collided = this.touchingInstanceOf(actors, DIR.DOWN);
		if(collided != null) {
			collided.action(DIR.DOWN, this);
			return;
		}
	}

	/**
	 *  checks if this GO is touching anything it cannot go through
	 */

	protected void obstacleCollision() {
		GameObject collided = this.touchingInstanceOf(obstacles, DIR.LEFT);
		if(collided != null) {
			this.lcol = true;
			this.xv = 1;
		}else {
			lcol = false;
		}
		collided = this.touchingInstanceOf(obstacles, DIR.RIGHT);
		if(collided != null) {
			this.rcol = true;
			this.xv = -1;
		}else {
			rcol = false;
		}
		collided = this.touchingInstanceOf(obstacles, DIR.UP);
		if(collided != null) {
			this.ucol = true;
			this.yv = 1;
		}else {
			ucol = false;
		}
		collided = this.touchingInstanceOf(obstacles, DIR.DOWN);
		if(collided != null) {
			this.dcol = true;
			this.yv = -1;			
		}else {
			dcol = false;
		}
	}

	/**
	 * A simple function which determines which direction
	 * a GO should face given the position of a target GO it
	 * is following 
	 */

	protected void trackToDir(GameObject obj) {
		int deltaX = (this.x + (this.w / 2)) - (obj.x + (obj.w /2));
		int deltaY = (this.y + (this.h / 2)) - (obj.y + (obj.h /2));
		if(Math.abs(deltaX) > Math.abs(deltaY)) {
			if(deltaX < 0) {
				this.dir = DIR.RIGHT;
			}else {
				this.dir = DIR.LEFT;
			}
		}else {
			if(deltaY < 0) {
				this.dir = DIR.DOWN;
			}else {
				this.dir = DIR.UP;
			}
		}
	}

	/**
	 * A simple behavior script where this GO follows a given
	 * target GO
	 */

	protected void trackTo(GameObject obj, int interval) {
		if(this.moveTimer > 0) {
			this.moveTimer--;
		}else {
			this.moveTimer = interval;
		}		
		int deltaX = (this.x + (this.w / 2)) - (obj.x + (obj.w /2));
		int deltaY = (this.y + (this.h / 2)) - (obj.y + (obj.h /2));
		if(this.moveTimer >= interval/2) {
			if(deltaX < 0) {
				if(!rcol) {
					this.xv = this.speed;
				}			
			}else {
				if(!lcol) {
					this.xv = -this.speed;
				}
			}
			if(deltaY < 0) {
				if(!dcol) {
					this.yv = this.speed;
				}
			}else {
				if(!ucol) {
					this.yv = -this.speed;
				}
			}
			this.trackToDir(obj);
		}else {
			this.xv = 0;
			this.yv = 0;
		}
	}

	protected void hurt(DIR dir, GameObject obj, int htspd, int httime) {
		if(obj.stt == OBJ_STATE.NORM) {
			obj.hp--;
			obj.stt = OBJ_STATE.HIT;
			switch(dir) {
			case LEFT:
				obj.lcol = true;
				obj.hittimer = httime;
				obj.xv = htspd;
				break;
			case RIGHT:
				obj.rcol = true;
				obj.hittimer = httime;
				obj.xv = -htspd;
				break;
			case UP:
				obj.ucol = true;
				obj.hittimer = httime;
				obj.yv = htspd;
				break;
			case DOWN:
				obj.dcol = true;
				obj.hittimer = httime;
				obj.yv = -htspd;
				break;

			}
		}
	}

	/**
	 * Checks to this is this GO is having any form of
	 * collision with the GO obj
	 */

	public boolean touchingAtAll(GameObject obj) {
		if(this.touching(obj, DIR.LEFT)
				|| this.touching(obj, DIR.RIGHT)
				|| this.touching(obj, DIR.UP)
				|| this.touching(obj, DIR.DOWN)) {
			return true;
		}else {
			return false;
		}

	}


	/**
	 *  Finds a GO of the given id that is touching
	 *  this GO
	 */

	public GameObject getObjectAtTouching(OBJ_ID id) {
		for(GameObject obj : cycler.getObjs()) {
			if(this.touchingAtAll(obj) && obj.getId() == id) {
				return obj;
			}
		}
		return null;
	}

	/**
	 * Gives this GO the capability of movement
	 */

	protected void move() {
		this.x += this.xv;
		this.y += this.yv;
	}


	/**
	 * A behavior typically associated with the player,
	 * this function allows the GO to move with response to key input
	 * WASD
	 */

	protected void wasd() {
		if(KeyWatcher.pressed.contains('a') || KeyWatcher.pressed.contains('A')) {
			this.dir = DIR.LEFT;
			if(!lcol) {
				this.xv = -this.speed;
			}
		}
		else if(KeyWatcher.pressed.contains('d') || KeyWatcher.pressed.contains('D')) {
			this.dir = DIR.RIGHT;
			if(!rcol) {
				this.xv = this.speed;
			}
		}else {
			this.xv = 0;
		}
		if(KeyWatcher.pressed.contains('w') || KeyWatcher.pressed.contains('W')) {
			this.dir = DIR.UP;
			if(!ucol) {
				this.yv = -this.speed;
			}
		}
		else if(KeyWatcher.pressed.contains('s') || KeyWatcher.pressed.contains('S')) {
			this.dir = DIR.DOWN;
			if(!dcol) {
				this.yv = this.speed;
			}
		}else {
			this.yv = 0;
		}
	}

	/**
	 * Also typically associated with the Player,
	 * this allows this GO's movement to navigate a given dungeon
	 */

	protected void roomCol() {
		if(this.x + this.w < 0) {
			cycler.nextRoom(DIR.LEFT);
		}
		if(this.x > Looper.WIDTH) {
			cycler.nextRoom(DIR.RIGHT);
		}
		if(this.y + this.h < 0) {
			cycler.nextRoom(DIR.UP);
		}
		if(this.y > Looper.HEIGHT) {
			cycler.nextRoom(DIR.DOWN);
		}
	}

	/**
	 * Simple combat behavior, shoots a GO of given id 
	 * in the this GO's direction at a given bulletspd
	 */

	private void shoot1LinearObject(int bulletspd,OBJ_ID id) {
		switch(this.dir) {
		case LEFT:
			cycler.addObject(this.x+this.w / 2, this.y+this.w / 2,-bulletspd, 0, id);
			break;
		case RIGHT:
			cycler.addObject(this.x+this.w / 2, this.y+this.w / 2,bulletspd, 0, id);
			break;
		case UP:
			cycler.addObject(this.x+this.w / 2, this.y+this.w / 2, 0, -bulletspd, id);
			break;
		case DOWN:
			cycler.addObject(this.x+this.w / 2, this.y+this.w / 2, 0, bulletspd, id);
			break;
		}


	}

	protected void timerShoot1LinearObject(int bulletspd, int cooldown, OBJ_ID id) {
		if(shootTimer > 0) {
			shootTimer--;
		}
		if(this.shootTimer == 0) {
			this.shoot1LinearObject(bulletspd, id);
			shootTimer = cooldown;
		}			
	}

	/**
	 *  Shoots a GO of given id on an interval cooldown at a given bulletspd if
	 *  a key input matches a given input
	 */

	public void inputShoot1LinearObject(char input, int bulletspd, int cooldown, OBJ_ID id) {
		if(shootTimer > 0) {
			shootTimer--;
		}
		if(KeyWatcher.pressed.contains(input) && this.shootTimer == 0) {
			this.shoot1LinearObject(bulletspd, id);
			this.shootTimer = cooldown;
		}
	}


	private void shootObjectSpread(int numBullets, int bulletspd,OBJ_ID id) {		
		double bulletWedge = 2*Math.PI/numBullets;
		for(int i = 0; i < numBullets; i++) {
			cycler.addObject(this.x+this.w / 2, this.y+this.w / 2, (int)(bulletspd*Math.cos(bulletWedge*i)), (int)(bulletspd*Math.sin(bulletWedge*i)), id);
		}			
	}

	protected void timerShootObjectSpred(int numBullets, int bulletspd, int cooldown,OBJ_ID id) {
		if(shootTimer > 0) {
			shootTimer--;
		}
		if(this.shootTimer == 0) {
			this.shootObjectSpread(numBullets, bulletspd, id);
			shootTimer = cooldown;
		}		
	}


	public void hpManage() {
		if(this.hp <= 0) {
			cycler.removeObject(this);
		}
	}

	public void stationary() {
		this.xv = 0;
		this.yv = 0;
	}

	/*
	 * Getters and setters
	 */

	public boolean isLcol() {
		return lcol;
	}

	public void setLcol(boolean lcol) {
		this.lcol = lcol;
	}

	public boolean isRcol() {
		return rcol;
	}

	public void setRcol(boolean rcol) {
		this.rcol = rcol;
	}

	public boolean isUcol() {
		return ucol;
	}

	public void setUcol(boolean ucol) {
		this.ucol = ucol;
	}

	public boolean isDcol() {
		return dcol;
	}

	public void setDcol(boolean dcol) {
		this.dcol = dcol;
	}

	public int getXv() {
		return xv;
	}

	public void setXv(int xv) {
		this.xv = xv;
	}

	public int getYv() {
		return yv;
	}

	public void setYv(int yv) {
		this.yv = yv;
	}

	public OBJ_ID getId() {
		return id;
	}

	public void setId(OBJ_ID id) {
		this.id = id;
	}

	public OBJ_STATE getStt() {
		return stt;
	}

	public void setStt(OBJ_STATE stt) {
		this.stt = stt;
	}

	public int compareTo(OBJ_ID id) {
		return this.id.compareTo(id);
	}

	public Cycler getCycler() {
		return cycler;
	}

	public void setCycler(Cycler cycler) {
		this.cycler = cycler;
	}

	public int getDamageOutput() {
		return this.damageOutPut;
	}







}
