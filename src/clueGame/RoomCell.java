package clueGame;

import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection	{
		UP, DOWN, LEFT,	RIGHT, NONE;
	}
	private DoorDirection doorDirection;
	private char roomInitial;
	private String roomName;
	
	public RoomCell(String symbol, String roomName, int row, int col){
		this.row = row;
		this.col = col;
		this.roomInitial = symbol.charAt(0);
		this.cellType = symbol.charAt(0);
		this.roomName = roomName;
		char temp;
		
		if (symbol.length() > 1)
		{
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
				break;
			}
		}
		else
		{
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
	public void draw(Graphics g, Board b){
		
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
	
	public String getRoomName() {
		return roomName;
	}
}
