package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {

	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	private Card suggestedRoom;
	private Card suggestedPerson;
	private Card suggestedWeapon;
	
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
	
	public Card getSuggestedRoom() {
		return suggestedRoom;
	}
	
	public Card getSuggestedPerson() {
		return suggestedPerson;
	}
	
	public Card getSuggestedWeapon() {
		return suggestedWeapon;
	}
	
	public void updateSeen (Card seen)
	{
		seenCards.add(seen);
		
		return;
	}

	public BoardCell pickLocation(Set<BoardCell> targets)
	{
		Random rand = new Random();
		BoardCell [] targetArray = new BoardCell[targets.size()];
		
		for (BoardCell b : targets)
		{
			if (b.isRoom())
			{
				if (((RoomCell)b).getInitial() != lastRoomVisited)
				{
					lastRoomVisited = ((RoomCell)b).getInitial();
					return b;
				}
			}
		}
		
		targets.toArray(targetArray);
		
		return targetArray[rand.nextInt(targetArray.length)];
	}
	
	public void createSuggestion(ArrayList<Card> allCards)
	{	
		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> people = new ArrayList<Card>();
		Random rand = new Random();
		
		for (Card c : allCards)
		{
			if (c.getCardType() == CardType.ROOM)
			{
				if (((RoomCell)this.currentLocation).getRoomName().equals(c.getName()))
				{
					this.suggestedRoom = c;
				}
			}
			else if (c.getCardType() == CardType.PERSON)
			{
				if (!(seenCards.contains(c)))
				{
					people.add(c);
				}
			}
			else 
			{
				if (!(seenCards.contains(c)))
				{
					weapons.add(c);
				}
			}
		}

		this.suggestedPerson = people.get(rand.nextInt(people.size()));
		this.suggestedWeapon = weapons.get(rand.nextInt(weapons.size()));
		
		return;
	}
	
	//This is for testing purposes only and should not be called anywhere in the game play	
	public void setLastRoomVisited(char lastRoomVisited)
	{
		this.lastRoomVisited = lastRoomVisited;
	}
}
