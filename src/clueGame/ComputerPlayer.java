package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {

	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	
	public ComputerPlayer() {
		super();
		lastRoomVisited = '?';
		seenCards = new ArrayList<Card>();
	}
	
	public ComputerPlayer(String name, Color color, BoardCell startingLocation) {
		// TODO Auto-generated constructor stub
		super(name, color, startingLocation);
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

	public BoardCell pickLocation(Set<BoardCell> targets)
	{
		return new WalkwayCell(0,0);
	}
	
	public void createSuggestion()
	{
		return;
	}
}
