package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.RoomCell.DoorDirection;

public class Animation extends JPanel {
	Board b = new Board();
	static final int SIDE = 40;
	int x = 0;
	int y = 0;
	
	
	//@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ArrayList<BoardCell> bcList = b.getCells();
		ArrayList<ComputerPlayer> cPlayers = b.getComputerPlayers();
		HumanPlayer hPlayer = b.getHumanPlayer();
		int col = b.getNumColumns();
		int row = b.getNumRows();
		int counter = 0;
		Map<Character,String> rooms = b.getRooms();
		Map<Character,Boolean> drawName = new HashMap<Character, Boolean>();
		Object [] roomsInitials = rooms.keySet().toArray();
		char initial = '1';
		
		for (int k = 0; k < rooms.size(); k++) {
			drawName.put((char) roomsInitials[k], false);
		}
		
		for (int i = 0; i<row; i++) {
			x=0;
			for (int j = 0; j<col; j++) {
				if (bcList.get(i*col+j).isWalkway()) {
					g.setColor(Color.YELLOW);
					g.fillRect(x, y, SIDE, SIDE);
					g.setColor(Color.BLACK);
					g.drawRect(x, y, SIDE, SIDE);
					if (i == hPlayer.getCurrentLocation().row && j == hPlayer.getCurrentLocation().col) {
						g.setColor(hPlayer.getColor());
						g.fillOval(x, y, SIDE, SIDE);
						g.setColor(Color.BLACK);
						g.drawOval(x, y, SIDE, SIDE);
					}
					
					for (ComputerPlayer cp : cPlayers) {
						if ((i == cp.getCurrentLocation().row) && (j == cp.getCurrentLocation().col)) {
						g.setColor(cp.getColor());
						g.fillOval(x, y, SIDE, SIDE);
						g.setColor(Color.BLACK);
						g.drawOval(x, y, SIDE, SIDE);
						}
					}
					
					
				}
				else {
					for (int cnt = 0; cnt < roomsInitials.length; cnt++) {
						//System.out.println((char)roomsInitials[cnt]);
						
						if ((char)roomsInitials[cnt] != initial) {
							g.setColor(Color.BLUE);
							g.drawString(((RoomCell)bcList.get(i*col+j)).getRoomName(), x, y);
							initial = (((RoomCell)bcList.get(i*col+j)).getRoomName()).charAt(0);
						}
					}
					System.out.println();
						
					g.setColor(Color.GRAY);
					g.fillRect(x, y, SIDE, SIDE);
					if (bcList.get(i*col+j).isDoorway()) {
						g.setColor(Color.RED);
						if (((RoomCell)(bcList.get(i*col+j))).getDoorDirection().equals(DoorDirection.RIGHT)) {
							g.drawLine(x+SIDE-1, y, x+SIDE-1, y+SIDE);
						}
						else if (((RoomCell)(bcList.get(i*col+j))).getDoorDirection().equals(DoorDirection.LEFT)) {
							g.drawLine(x+1, y, x+1, y+SIDE);
						}
						else if (((RoomCell)(bcList.get(i*col+j))).getDoorDirection().equals(DoorDirection.UP)) {
							g.drawLine(x, y+1, x+SIDE, y+1);
						}
						else {
							g.drawLine(x, y+SIDE-1, x+SIDE, y+SIDE-1);
						}
					}
				}
				x+=SIDE;
			}
			y+=SIDE;
		}
		
		
	}
	

}