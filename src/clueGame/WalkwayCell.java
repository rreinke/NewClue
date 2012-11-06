package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;

public class WalkwayCell extends BoardCell {
	//Make sure these are in pixels!
	private int width = 5;
	private int height = 5;
	
	public WalkwayCell(int row, int col){
		this.cellType = 'W';
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean isWalkway(){
		return true;
	}

	@Override
	public void draw(Graphics g, Board b) {

		g.setColor(Color.YELLOW);
		g.fillRect(50, 50, 10, 10);

	}
}
