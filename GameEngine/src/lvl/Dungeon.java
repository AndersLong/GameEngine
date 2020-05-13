package lvl;


public class Dungeon {
	
	public Room start;
	private Room end;
	int numNodes;
	
	public Dungeon(Room start) {
		this.start = start;
	}
	
	public void setEnd(Room end) {
		this.end = end;
	}
	
	public Room getEnd() {
		return this.end;
	}
	
}
