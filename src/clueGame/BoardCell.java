package clueGame;

import java.awt.Graphics;

abstract public class BoardCell {
	public int row;
	public int col;
	protected char cellType;
	public static final int SIDE = 33;
	
	public BoardCell() {}

	public boolean isWalkway() {
		return false;
	}

	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway() {
		return false;
	}

	abstract public void draw(Graphics g, Board b);

	abstract public void drawTargets(Graphics g);
}

