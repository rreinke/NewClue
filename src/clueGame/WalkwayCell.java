package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;

public class WalkwayCell extends BoardCell {
	
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
		g.fillRect(this.col*SIDE, this.row*SIDE, SIDE, SIDE);
		g.setColor(Color.BLACK);
		g.drawRect(this.col*SIDE, this.row*SIDE, SIDE, SIDE);
		//Add the computer players to the board with correct location and color
		for(int i = 0; i < b.getComputerPlayers().size(); i++) {
			g.setColor(b.getComputerPlayers().get(i).getColor());
			g.fillOval(b.getComputerPlayer(i).getCurrentLocation().col*SIDE, b.getComputerPlayer(i).getCurrentLocation().row*SIDE, SIDE, SIDE);
			g.setColor(Color.BLACK);
			g.drawOval(b.getComputerPlayer(i).getCurrentLocation().col*SIDE, b.getComputerPlayer(i).getCurrentLocation().row*SIDE, SIDE, SIDE);
		}
		//Add the human players to the board with the correct location and color
		g.setColor(b.getHumanPlayer().getColor());
		g.fillOval(b.getHumanPlayer().getCurrentLocation().col*SIDE, b.getHumanPlayer().getCurrentLocation().row*SIDE, SIDE, SIDE);
		g.setColor(Color.BLACK);
		g.drawOval(b.getHumanPlayer().getCurrentLocation().col*SIDE, b.getHumanPlayer().getCurrentLocation().row*SIDE, SIDE, SIDE);
	}
	
	@Override
	public void drawTargets(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(this.col*SIDE, this.row*SIDE, SIDE, SIDE);
	}

}
