package clueGame;

import java.awt.Color;
import java.util.ArrayList;

import clueGame.Card.CardType;

public abstract class Player {

	private String name;
	private ArrayList<Card> cards;
	private Color color;
	private WalkwayCell startingLocation;
	
	public Player(String name, WalkwayCell startingLocation) {
		// TODO Auto-generated constructor stub
		this.name = name;
		cards = new ArrayList<Card>();
		color = Color.BLACK;
		this.startingLocation = startingLocation;
	}
	
	public Player(String name, Color color, WalkwayCell startingLocation) {
		this.name = name;
		cards = new ArrayList<Card>();
		this.color = color;
		this.startingLocation = startingLocation;
	}

	public String getName()
	{
		return name;
	}
	
	public ArrayList<Card> getCards()
	{
		return cards;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public WalkwayCell getStartingLocation()
	{
		return startingLocation;
	}
	
	public void addCard(Card card)
	{
		cards.add(card);
	}
	
	public Card disproveSuggestion(String person, String room, String weapon)
	{
		return new Card(person, CardType.PERSON);
	}
}
