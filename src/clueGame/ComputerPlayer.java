package clueGame;

import java.util.ArrayList;

public class ComputerPlayer extends Player {

	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	
	public ComputerPlayer(String name, WalkwayCell startingLocation) {
		// TODO Auto-generated constructor stub
		super(name, startingLocation);
		lastRoomVisited = '?';
		seenCards = new ArrayList<Card>();
	}

	public void updateSeen (Card seen)
	{
		seenCards.add(seen);
		
		return;
	}
	
	public BoardCell pickLocation(ArrayList<BoardCell> targets)
	{
		return targets.get(0);
	}
}
