package clueGame;

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
	public void draw(){
		
	}
}
