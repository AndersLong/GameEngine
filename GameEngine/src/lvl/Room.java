package lvl;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import gpy.Cycler;
import gpy.GameObject;
import stt.OBJ_ID;

public class Room {
	
	boolean hasLeft, hasRight, hasUp, hasDown, empty;
	BufferedImage imge,imgf;
	Room left, right, up, down;
	
	public Room(BufferedImage imge, BufferedImage imgf, boolean hasLeft, boolean hasRight, boolean hasUp, boolean hasDown) {
		this.imge = imge;
		this.imgf = imgf;
		this.hasLeft = hasLeft;
		this.hasRight = hasRight;
		this.hasUp = hasUp;
		this.hasDown = hasDown;
		this.empty = false;
		return;
	}
	
	public Room(Room room) {
		this.imge = room.imge;
		this.imgf = room.imgf;
		this.hasLeft = room.hasLeft;
		this.hasRight = room.hasRight;
		this.hasUp = room.hasUp;
		this.hasDown = room.hasDown;
		return;
	}

	public boolean isHasLeft() {
		return hasLeft;
	}

	public void setHasLeft(boolean hasLeft) {
		this.hasLeft = hasLeft;
	}

	public boolean isHasRight() {
		return hasRight;
	}

	public void setHasRight(boolean hasRight) {
		this.hasRight = hasRight;
	}

	public boolean isHasUp() {
		return hasUp;
	}

	public void setHasUp(boolean hasUp) {
		this.hasUp = hasUp;
	}

	public boolean isHasDown() {
		return hasDown;
	}

	public void setHasDown(boolean hasDown) {
		this.hasDown = hasDown;
	}

	public Room getLeft() {
		return left;
	}

	public void setLeft(Room left) {
		this.left = left;
	}

	public Room getRight() {
		return right;
	}

	public void setRight(Room right) {
		this.right = right;
	}

	public Room getUp() {
		return up;
	}

	public void setUp(Room up) {
		this.up = up;
	}

	public Room getDown() {
		return down;
	}

	public void setDown(Room down) {
		this.down = down;
	}
	
	public boolean allDoorsSatisfied() {
		if(((hasLeft && left != null) || !hasLeft)
			&& ((hasRight && right != null) || !hasRight)
			&& ((hasUp && up != null) || !hasUp)
			&& ((hasDown && down != null) || !hasDown)) {
			return true;
		}else {
			return false;
		}
	}
	
	public BufferedImage getImage() {
		if(empty) {
			return imge;
		}else {
			return imgf;
		}
	}
	
	public Room getPrecedingRoom() {
		if(hasLeft) {
			return this.left;
		}else if(hasRight) {
			return this.right;
		}else if(hasUp) {
			return this.up;
		}else {
			return this.down;
		}
	}
	
	public int numDoors() {
		int numDoors = 0;
		if(hasLeft) {
			numDoors++;
		}
		if(hasRight) {
			numDoors++;
		}
		if(hasUp){
			numDoors++;
		}
		if(hasDown) {
			numDoors++;
		}
		return numDoors;
		
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	public void setDefaultImg(BufferedImage img) {
		this.imgf = img;
	}
	
	

}