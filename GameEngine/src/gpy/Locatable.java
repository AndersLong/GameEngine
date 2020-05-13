package gpy;

public abstract class Locatable {

	protected int x,y,w,h;

	public Locatable(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean pointWithin(int x, int y) {
		if(x > this.x && x < this.x + this.w) {
			if(y > this.y && y<this.y + this.h) {
				return true;
			}
		}
		return false;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	
	
}
