package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

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
		g.setColor(Color.GRAY);
		g.fillRect(this.col*SIDE, this.row*SIDE, SIDE, SIDE);
		g.setColor(Color.BLACK);
		//Add doorways
		if(this.isDoorway()) {
			if(this.doorDirection.equals(doorDirection.DOWN)) {
				g.setColor(Color.WHITE);
				g.fillRect(this.col*SIDE, this.row*SIDE+SIDE-5, SIDE, 5);
			} else if(this.doorDirection.equals(doorDirection.UP)) {
				g.setColor(Color.WHITE);
				g.fillRect(this.col*SIDE, this.row*SIDE+1, SIDE, 5);
			} else if(this.doorDirection.equals(doorDirection.LEFT)) {
				g.setColor(Color.WHITE);
				g.fillRect(this.col*SIDE+1, this.row*SIDE, 5, SIDE);
			} else {
				g.setColor(Color.WHITE);
				g.fillRect(this.col*SIDE+SIDE-6, this.row*SIDE, 5, SIDE);
			}
		}
		//Add the names of each room

	}

	Set<String> drawed = new HashSet<String>();
	String temp = "";

	public void drawNames(Graphics g) {

		temp = this.getRoomName();

		if (!drawed.contains(temp)){
			g.setColor(Color.BLUE);
			g.drawString(temp, this.col*SIDE+50, this.row*SIDE+100);
			drawed.add(temp);
		}
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
