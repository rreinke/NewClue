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

	public char getLastRoomVisited()
	{
		return lastRoomVisited;
	}
	
	public ArrayList<Card> getSeenCards()
	{
		return seenCards;
	}
	
	public void updateSeen (Card seen)
	{
		seenCards.add(seen);
		
		return;
	}

	public BoardCell pickLocation(ArrayList<BoardCell> targets)
	{
		return new WalkwayCell(0,0);
	}
	
	public void createSuggestion()
	{
		return;
	}
}
