package clueGame;

public class RoomCell extends BoardCell {
	public enum DoorDirection	{
		UP, DOWN, LEFT,	RIGHT, NONE;
	}
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(String symbol, int row, int col){
		this.row = row;
		this.col = col;
		this.roomInitial = symbol.charAt(0);
		this.cellType = symbol.charAt(0);
		char temp;
		try{
			temp = symbol.charAt(1);
			//System.out.println(row + " " + col + " " + temp);
			switch(temp){
			case 'U':
				doorDirection = DoorDirection.UP;
				break;
			case 'D':
				doorDirection = DoorDirection.DOWN;
				break;
			case 'L':
				doorDirection = DoorDirection.LEFT;
				break;
			case 'R':
				doorDirection = DoorDirection.RIGHT;
				break;
			default:
				doorDirection = DoorDirection.NONE;
			}
		} catch(StringIndexOutOfBoundsException e){
			doorDirection = DoorDirection.NONE;
		}
	}	

	@Override
	public boolean isRoom(){
		return true;
	}
	
	@Override
	public boolean isDoorway(){
		if(doorDirection.equals(DoorDirection.NONE)) return false;
		return true;
	}

	@Override
	public void draw(){
		
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getRoomInitial() {
		return roomInitial;
	}
	
	public char getInitial(){
		return roomInitial;
	}
}
