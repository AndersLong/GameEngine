package lvl;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import fio.ImageLoader;
import gpy.Cycler;
import stt.DIR;

public class LevelGenerator {

	Random r;
	ArrayList<Room> rooms;
	Cycler cycler;

	/*
	 * TODO:
	 * 
	 * 2. create a starting point
	 * 3. 
	 */

	public LevelGenerator(Cycler cycler) {
		this.cycler = cycler;
		this.r = new Random();
		this.rooms = new ArrayList<Room>();
		this.initAllNodes();
	}


	public Dungeon loadDungeon(int size) {
		Room start = new Room(rooms.get(r.nextInt(rooms.size())));
		Room end = null;
		Room prec = null;
		start.setEmpty(true);
		Dungeon dungeon = new Dungeon(start);
		Stack<Room> dungeonRooms = new Stack<>();
		dungeonRooms.push(start);
		while(!dungeonRooms.isEmpty()) {
			if(dungeonRooms.peek().allDoorsSatisfied()) {
				dungeonRooms.pop();
			}else {
				Room curr = dungeonRooms.peek();
				if(size > 0) {
					if(curr.hasLeft && curr.left == null) {
						ArrayList<Room> roomsThatWorkWithLeftDoor = this.getRoomsThatWorkWithDoor(DIR.LEFT);
						Room newRoom = new Room(roomsThatWorkWithLeftDoor.get(r.nextInt(roomsThatWorkWithLeftDoor.size())));
						newRoom.right = curr;
						dungeonRooms.push(newRoom);
						curr.left = newRoom;
						size-=(newRoom.numDoors()-1);
					}
					if(curr.hasRight && curr.right == null) {
						ArrayList<Room> roomsThatWorkWithRightDoor = this.getRoomsThatWorkWithDoor(DIR.RIGHT);
						Room newRoom = new Room(roomsThatWorkWithRightDoor.get(r.nextInt(roomsThatWorkWithRightDoor.size())));
						newRoom.left = curr;
						dungeonRooms.push(newRoom);
						curr.right = newRoom;
						size-=(newRoom.numDoors()-1);
					}
					if(curr.hasUp && curr.up == null) {
						ArrayList<Room> roomsThatWorkWithUpDoor = this.getRoomsThatWorkWithDoor(DIR.UP);
						Room newRoom = new Room(roomsThatWorkWithUpDoor.get(r.nextInt(roomsThatWorkWithUpDoor.size())));
						newRoom.down = curr;
						dungeonRooms.push(newRoom);
						curr.up = newRoom;
						size-=(newRoom.numDoors()-1);
					}
					if(curr.hasDown && curr.down == null) {
						ArrayList<Room> roomsThatWorkWithDownDoor = this.getRoomsThatWorkWithDoor(DIR.DOWN);
						Room newRoom = new Room(roomsThatWorkWithDownDoor.get(r.nextInt(roomsThatWorkWithDownDoor.size())));
						newRoom.up = curr;
						dungeonRooms.push(newRoom);
						curr.down = newRoom;
						size-=(newRoom.numDoors()-1);
					}
				}else {
					if(curr.hasLeft && curr.left == null) {
						ArrayList<Room> roomsThatWorkWithLeftDoor = this.getDeadEnd(DIR.LEFT);
						Room newRoom = end = new Room(roomsThatWorkWithLeftDoor.get(r.nextInt(roomsThatWorkWithLeftDoor.size())));
						newRoom.right = curr;
						dungeonRooms.push(newRoom);
						curr.left = newRoom;
						size-=(newRoom.numDoors()-1);
					}
					if(curr.hasRight && curr.right == null) {
						ArrayList<Room> roomsThatWorkWithRightDoor = this.getDeadEnd(DIR.RIGHT);
						Room newRoom = end = new Room(roomsThatWorkWithRightDoor.get(r.nextInt(roomsThatWorkWithRightDoor.size())));
						newRoom.left = curr;
						dungeonRooms.push(newRoom);
						curr.right = newRoom;
						size-=(newRoom.numDoors()-1);
					}
					if(curr.hasUp && curr.up == null) {
						ArrayList<Room> roomsThatWorkWithUpDoor = this.getDeadEnd(DIR.UP);
						Room newRoom = end = new Room(roomsThatWorkWithUpDoor.get(r.nextInt(roomsThatWorkWithUpDoor.size())));
						newRoom.down = curr;
						dungeonRooms.push(newRoom);
						curr.up = newRoom;
						size-=(newRoom.numDoors()-1);
					}
					if(curr.hasDown && curr.down == null) {
						ArrayList<Room> roomsThatWorkWithDownDoor = this.getDeadEnd(DIR.DOWN);
						Room newRoom = end = new Room(roomsThatWorkWithDownDoor.get(r.nextInt(roomsThatWorkWithDownDoor.size())));
						newRoom.up = curr;
						dungeonRooms.push(newRoom);
						curr.down = newRoom;
						size-=(newRoom.numDoors()-1);
					}
				}

			}
		}
		dungeon.setEnd(end);
		this.setPrec(dungeon);
		return dungeon;
	}

	public void setPrec(Dungeon d) {
		Room end = d.getEnd();
		Room prec = end.getPrecedingRoom();
		System.out.println(end);
		System.out.println(prec);
		if(prec == end.left) {
			if(prec.hasLeft && prec.hasRight && !prec.hasUp && !prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precLeftRightRight.png"));
			}else if(!prec.hasLeft && prec.hasRight && prec.hasUp && !prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precUpRightRight.png"));
			}else if(!prec.hasLeft && prec.hasRight && !prec.hasUp && prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precDownRightRight.png"));
			}else {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/prec4wayRight.png"));
			}

		}else if(prec == end.right) {

			if(prec.hasLeft && prec.hasRight && !prec.hasUp && !prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precLeftRightLeft.png"));
			}else if(prec.hasLeft && !prec.hasRight && prec.hasUp && !prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precUpLeftLeft.png"));
			}else if(prec.hasLeft && !prec.hasRight && !prec.hasUp && prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precDownLeftLeft.png"));
			}else {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/prec4wayLeft.png"));
			}

		}else if(prec == end.up) {
			if(!prec.hasLeft && !prec.hasRight && prec.hasUp && prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precUpDownDown.png"));
			}else if(prec.hasLeft && !prec.hasRight && !prec.hasUp && prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precDownLeftDown.png"));
			}else if(!prec.hasLeft && prec.hasRight && !prec.hasUp && prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precDownRightDown.png"));
			}else {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/prec4wayDown.png"));
			}

		}else if(prec == end.down) {
			if(!prec.hasLeft && !prec.hasRight && prec.hasUp && prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precUpDownUp.png"));
			}else if(prec.hasLeft && !prec.hasRight && prec.hasUp && !prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precUpLeftUp.png"));
			}else if(!prec.hasLeft && prec.hasRight && prec.hasUp && !prec.hasDown) {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/precUpRightUp.png"));
			}else {
				prec.setDefaultImg(ImageLoader.loadImage("/lvl/prec4wayUp.png"));
			}
		}
	}

	public void initAllNodes() {
		Scanner s = new Scanner(LevelGenerator.class.getResourceAsStream("/lvl/lvldat.txt"));
		while(s.hasNextLine()) {
			String[] levdat = s.nextLine().split(",");
			Room room = new Room(ImageLoader.loadImage(String.format("/lvl/%s.png", levdat[0])),
					ImageLoader.loadImage(String.format("/lvl/%s.png", levdat[1])),
					strToBool(levdat[2]),
					strToBool(levdat[3]),
					strToBool(levdat[4]),
					strToBool(levdat[5]));
			rooms.add(room);
		}		
		s.close();
	}

	private boolean strToBool(String num) {
		return numToBool(Integer.parseInt(num));
	}

	private boolean numToBool(int num) {
		if(num == 0) {
			return false;
		}else {
			return true;
		}
	}

	private ArrayList<Room> getDeadEnd(DIR dir){
		ArrayList<Room> searchedRooms = new ArrayList<Room>();
		switch(dir) {
		case LEFT:
			for(Room room: rooms) {
				if(room.hasRight && !room.hasLeft && !room.hasUp && !room.hasDown) {
					searchedRooms.add(room);
				}
			}
			break;
		case RIGHT:
			for(Room room: rooms) {
				if(room.hasLeft && !room.hasRight && !room.hasUp && !room.hasDown) {
					searchedRooms.add(room);
				}
			}
			break;
		case UP:
			for(Room room: rooms) {
				if(room.hasDown && !room.hasUp && !room.hasLeft && !room.hasRight) {
					searchedRooms.add(room);
				}
			}
			break;
		case DOWN:
			for(Room room: rooms) {
				if(room.hasUp && !room.hasDown && !room.hasLeft && !room.hasRight) {
					searchedRooms.add(room);
				}
			}
			break;
		}
		return searchedRooms;
	}

	private ArrayList<Room> getRoomsThatWorkWithDoor(DIR dir) {
		ArrayList<Room> searchedRooms = new ArrayList<Room>();
		switch(dir) {
		case LEFT:
			for(Room room: rooms) {
				if(room.hasRight) {
					searchedRooms.add(room);
				}
			}
			break;
		case RIGHT:
			for(Room room: rooms) {
				if(room.hasLeft) {
					searchedRooms.add(room);
				}
			}
			break;
		case UP:
			for(Room room: rooms) {
				if(room.hasDown) {
					searchedRooms.add(room);
				}
			}
			break;
		case DOWN:
			for(Room room: rooms) {
				if(room.hasUp) {
					searchedRooms.add(room);
				}
			}
			break;
		}
		return searchedRooms;
	}


}
