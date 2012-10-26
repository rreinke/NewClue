package clueGame;

import java.awt.Color;
import java.util.ArrayList;

import clueGame.Card.CardType;

public abstract class Player {

	private String name;
	private ArrayList<Card> cards;
	private Color color;
	private BoardCell currentLocation;
	
	public Player() {
		// TODO Auto-generated constructor stub
		this.name = "???";
		cards = new ArrayList<Card>();
		color = Color.BLACK;
		this.currentLocation = new WalkwayCell(0,0);
	}
	
	public Player(String name, Color color, BoardCell startingLocation) {
		this.name = name;
		cards = new ArrayList<Card>();
		this.color = color;
		this.currentLocation = startingLocation;
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
	
	public BoardCell getCurrentLocation()
	{
		return currentLocation;
	}
	
	public void setCurrentLocation(BoardCell currentLocation)
	{
		this.currentLocation = currentLocation;
	}
	
	public void addCard(Card card)
	{
		cards.add(card);
	}
	
	public Card disproveSuggestion(Card person, Card room, Card weapon)
	{
		return new Card("???", CardType.PERSON);
	}
}
