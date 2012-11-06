package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

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
		int col = b.getNumColumns();
		int row = b.getNumRows();
		for (int i = 0; i<row; i++) {
			x=0;
			for (int j = 0; j<col; j++) {
				if (bcList.get(i*col+j).isWalkway()) {
					g.setColor(Color.YELLOW);
					g.fillRect(x, y, SIDE, SIDE);
					g.setColor(Color.BLACK);
					g.drawRect(x, y, SIDE, SIDE);
				}
				else {
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