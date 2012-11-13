/* Project: Clue Game
 * Date:  Oct 23, 2012
 * Authors: Troy A. Sornson
 * 					Kyle Geyser
 * Contact: tsornson@mines.edu
 * 					kgeyser@mines.edu
 *
 * The config files that Rader has made for us have been 
 * renamed to ClueLegend.txt and ClueLayout.csv (the CR-
 *  is the only part that has been removed)
 */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.Card.CardType;

public class Board extends JPanel {

	// Variables
	private ArrayList<ComputerPlayer> computerPlayers;
	private Player currentPlayer;
	//In case we ever want more than one human player
	private ArrayList<HumanPlayer> humanPlayer;
	private ArrayList<Card> cards;
	private Solution solution;
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;
	private Map<Integer, LinkedList<Integer>> adjList;
	private Set<BoardCell> targets;
	private boolean[] seen; 
	// Change names to load the respective files
	private final String legend = "src/legend.csv";
	private final String layout = "src/clue_board.csv";
	private final String WEAPON_FILE = "src/Weapons.csv";
	private final String PLAYER_FILE = "src/Player.csv";
	static final int SIDE = 40;

	// Constructor
	public Board() {
		computerPlayers = new ArrayList<ComputerPlayer>();
		currentPlayer = new HumanPlayer(); // new ComputerPlayer();
		humanPlayer = new ArrayList<HumanPlayer>();
		cards = new ArrayList<Card>();
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character,String>();
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		loadConfigFiles();
		loadPlayers();
		loadWeapons();
		initRoomCards();
		calcAdjacencies();
		deal();
		seen = new boolean[cells.size()];
	}

	/* 
	 * Function loads values from files stored in legend and layout
	 * into cells and rooms variables.
	 */
	public void loadConfigFiles() {
		Scanner scn;
		String line;

		//This will be incremented to 0 for the first row later on		
		numRows = -1;
		try{
			//Read in the legend and load up the rooms maps
			scn = new Scanner(new FileReader(legend));
			while(scn.hasNext()){
				line = scn.nextLine();
				if(!line.contains(",")){
					throw new BadConfigFormatException("One of your lines doesn't contain 2 entries.");
				}
				String[] split = line.split(",");

				if (split.length > 2) {
					throw new BadConfigFormatException("Legend: A line has more than 2 entries");
				}
				rooms.put(split[0].charAt(0),split[1].trim());
			}
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(legend + " could not be found!");
			return;
		}
		catch (BadConfigFormatException ex)
		{
			System.out.println(ex.toString());
			return;
		}

		try {
			//Read the layout file and load up the cells ArrayList
			scn = new Scanner(new FileReader(layout));
			while(scn.hasNext()){
				numColumns = -1;
				numRows += 1;
				line = scn.nextLine();
				String[] split = line.split(",");
				for(String i : split){
					if (i.length() > 2) {
						throw new BadConfigFormatException("Unknown room identifier: " + i);
					} 
					numColumns += 1;
					if(rooms.get(i.charAt(0)).equals("Walkway")){
						cells.add(new WalkwayCell(numRows,numColumns));
					} else {
						cells.add(new RoomCell(i,rooms.get(i.charAt(0)),numRows,numColumns));
					}
				}
			}
			numRows += 1;
			numColumns += 1;
			//There are special conditions that would pass this test, but they'd be very specific
			if(cells.size() != numRows*numColumns){
				throw new BadConfigFormatException("Config is not rectangular!");
			}
		} 
		catch(FileNotFoundException ex){
			System.out.println(layout + " could not be found!");
			return;
		}
		catch (BadConfigFormatException ex)
		{
			System.out.println(ex.toString());
			return;
		}
	}

	public void loadPlayers()
	{
		try {
			Scanner scan = new Scanner (new FileReader(PLAYER_FILE));
			String inputLine;

			while (scan.hasNext())
			{
				inputLine = scan.nextLine();

				String [] personConfig = inputLine.split(",");

				try {
					//Make sure the correct amount of information is given for each player.
					//This includes name, color, and starting row and column.
					if (personConfig.length == 4)
					{
						String name = personConfig[0];
						Color color = convertColor(personConfig[1]);
						int startRow = Integer.parseInt(personConfig[2]);
						int startCol = Integer.parseInt(personConfig[3]);

						if (color == null)
						{
							throw new BadConfigFormatException("ERROR: One or more players in " + PLAYER_FILE + " had an invalid color!");
						}

						if (this.getBoardCellAt(this.calcIndex(startRow, startCol)).isWalkway())
						{
							cards.add(new Card(name, CardType.PERSON));

							//Only one human player.
							if (humanPlayer.isEmpty())
							{
								humanPlayer.add(new HumanPlayer(name, color, (WalkwayCell)this.getBoardCellAt(this.calcIndex(startRow, startCol))));
							}
							else
							{
								computerPlayers.add(new ComputerPlayer(name, color, (WalkwayCell)this.getBoardCellAt(this.calcIndex(startRow, startCol))));
							}
						}
						else
						{
							throw new BadConfigFormatException("ERROR: The starting location for all players must be a walkway cell!");
						}
					}
					else
					{
						throw new BadConfigFormatException("ERROR: One or more rows in " + PLAYER_FILE + " had the incorrect number of columns!");
					}
				}
				catch (NumberFormatException ex)
				{
					System.out.println("ERROR: Non-numeric row or column detected in " + PLAYER_FILE);
				}
				catch (BadConfigFormatException ex)
				{
					System.out.println(ex.toString());
				}
			}
		}
		catch (FileNotFoundException ex) {
			System.out.println(PLAYER_FILE + " could not be found!");
		}
	}

	//Load and store all of the weapons as long as they are more than one character long.
	public void loadWeapons()
	{
		try {
			Scanner scan = new Scanner(new FileReader(WEAPON_FILE));
			String inputLine;

			while (scan.hasNext())
			{
				inputLine = scan.nextLine();

				if (inputLine.length() > 1)
				{
					cards.add(new Card(inputLine, CardType.WEAPON));
				}
				else
				{
					throw new BadConfigFormatException("ERROR: Invalid weapon in " + WEAPON_FILE);
				}
			}
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(WEAPON_FILE + " could not be found!");
		}
		catch (BadConfigFormatException ex)
		{
			System.out.println(ex.toString());
		}
	}

	//Initialize each of the rooms as a card.
	public void initRoomCards()
	{
		Collection<String> localRooms = rooms.values();

		for (String s : localRooms)
		{
			if (!(s.equals("Kafadar")) && !(s.equals("Walkway")))
			{
				cards.add(new Card(s, CardType.ROOM));
			}
		}
	}

	//Calculate the index of a given row and column.
	public int calcIndex(int row, int col) {
		return row*numColumns+col;
	}

	// ---Get Board Cell Row---
	public int getRow(int indx, int BoardCols) {
		return (int) (indx / BoardCols);
	}

	// ---Get Board Cell Column---
	public int getColumn(int indx, int BoardCols) {
		return indx % BoardCols;
	}
	/*
	 * Name is self explanatory
	 */
	public ComputerPlayer getComputerPlayer(int index)
	{
		return computerPlayers.get(index);
	}

	public ArrayList<ComputerPlayer> getComputerPlayers()
	{
		return computerPlayers;
	}

	public HumanPlayer getHumanPlayer()
	{
		return humanPlayer.get(0);
	}

	public ArrayList<Card> getCards()
	{
		return cards;
	}

	//Returns null if index is not a room.
	public RoomCell getRoomCellAt(int row, int col){
		if(cells.get(calcIndex(row,col)).isRoom()){
			RoomCell temp = (RoomCell) cells.get(calcIndex(row,col));
			return temp;
		} 
		return null;
	}

	public ArrayList<BoardCell> getCells(){
		return cells;
	}

	public BoardCell getBoardCellAt(int i){
		return cells.get(i);
	}

	public Map<Character,String> getRooms(){
		return rooms;
	}

	public int getNumRows(){
		return numRows;
	}

	public int getNumColumns(){
		return numColumns;
	}

	public LinkedList<Integer> getAdjList(int i) {
		return adjList.get(i);
	}

	/*
	 * The recursive calcTargets function
	 */
	public void calcTargetsRecurse(int start, int steps, int startSteps) {
		if (steps == 0 || (getBoardCellAt(start).isRoom() && steps != startSteps)) {
			targets.add(getBoardCellAt(start));
			return;
		}

		seen[start] = true;

		for (int i : getAdjList(start)) {
			if (!seen[i]) {
				calcTargetsRecurse(i, steps-1, startSteps);
				seen[i] = false;
			}
		}
	}

	/* 
	 * The calling calcTargets function, uses a help function calcTargetsRecurse
	 */
	public void calcTargets(int start, int steps) {
		targets.clear();	
		for (int i = 0; i < seen.length; ++i) {
			seen[i] = false;
		}	
		calcTargetsRecurse(start, steps, steps);
	}

	/*
	 * This was a helper function, prints out the row and column of a given index.
	 * Exactly the opposite of calcIndex(int i)
	 */
	public void inverseCalcIndex(Integer i){
		System.out.println(i/numColumns + ", " + i%numColumns);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	/*
	 * Lots of if statements to determine what's adjacent to the respective spot
	 */
	public void calcAdjacencies() {
		for(int i = 0; i < cells.size(); ++i){
			adjList.put(i, new LinkedList<Integer>());
			//If i is a doorway, then there's only one adjacent; switch case
			if(cells.get(i).isDoorway()){
				RoomCell temp = (RoomCell) cells.get(i);
				switch(temp.getDoorDirection()){
				case RIGHT:
					adjList.get(i).add(i+1);
					break;
				case LEFT:
					adjList.get(i).add(i-1);
					break;
				case UP:
					adjList.get(i).add(i-numColumns);
					break;
				case DOWN:
					adjList.get(i).add(i+numColumns);
					break;
				default:
					break;
				}
			} else if(cells.get(i).isWalkway()){				
				// Check if i is on left side of the board
				if(i%numColumns != 0){
					if(cells.get(i-1).isWalkway())
						adjList.get(i).add(i-1);
					else {
						RoomCell temp = (RoomCell) cells.get(i-1);
						if(temp.getDoorDirection() == RoomCell.DoorDirection.RIGHT)
							adjList.get(i).add(i-1);
					}
				}		
				// Check if i is on the right side of the board
				if((i+1)%numColumns != 0){
					if(cells.get(i+1).isWalkway())
						adjList.get(i).add(i+1);
					else {
						RoomCell temp = (RoomCell) cells.get(i+1);
						if(temp.getDoorDirection() == RoomCell.DoorDirection.LEFT)
							adjList.get(i).add(i+1);
					}
				}
				// Check if i is on top of board
				if(i >= numColumns){
					if(cells.get(i-numColumns).isWalkway())
						adjList.get(i).add(i-numColumns);
					else {
						RoomCell temp = (RoomCell) cells.get(i-numColumns);
						if(temp.getDoorDirection() == RoomCell.DoorDirection.DOWN)
							adjList.get(i).add(i-numColumns);
					}
				}
				// Check if i is on bottom of board
				if(i+numColumns < cells.size()){
					if(cells.get(i+numColumns).isWalkway())
						adjList.get(i).add(i+numColumns);
					else {
						RoomCell temp = (RoomCell) cells.get(i+numColumns);
						if(temp.getDoorDirection() == RoomCell.DoorDirection.UP)
							adjList.get(i).add(i+numColumns);
					}
				}
			}
		}
	}

	public void deal()
	{
		Random rand = new Random();
		ArrayList<Card> Rooms = new ArrayList<Card>();
		ArrayList<Card> People = new ArrayList<Card>();
		ArrayList<Card> Weapons = new ArrayList<Card>();
		ArrayList<Card> remainingCards = new ArrayList<Card>();
		Card room;
		Card person;
		Card weapon;
		int randIndex;
		int playerIndex = -1;

		//Place each card in its respective category of person, room, or weapon.
		for (Card c : cards)
		{
			if (c.getCardType() == CardType.ROOM)
			{
				Rooms.add(c);
			}
			else if (c.getCardType() == CardType.PERSON)
			{
				People.add(c);
			}
			else
			{
				Weapons.add(c);
			}
		}

		/*Randomly select one card from the rooms, one from players, and one from weapons and store
		as the solution*/
		randIndex = rand.nextInt(Rooms.size());
		room = Rooms.get(randIndex);
		Rooms.remove(randIndex);

		randIndex = rand.nextInt(People.size());
		person = People.get(randIndex);
		People.remove(randIndex);

		randIndex = rand.nextInt(Weapons.size());
		weapon = Weapons.get(randIndex);
		Weapons.remove(randIndex);

		solution = new Solution(person, weapon, room);

		remainingCards.addAll(Rooms);
		remainingCards.addAll(Weapons);
		remainingCards.addAll(People);

		while (!remainingCards.isEmpty())
		{
			randIndex = rand.nextInt(remainingCards.size());

			if (playerIndex == -1)
			{
				humanPlayer.get(0).addCard(remainingCards.get(randIndex));	
				playerIndex = 0;
			}
			else 
			{
				computerPlayers.get(playerIndex).addCard(remainingCards.get(randIndex));
				playerIndex = ++playerIndex % computerPlayers.size();

				if (playerIndex == 0)
				{
					playerIndex = -1;
				}
			}

			remainingCards.remove(randIndex);
		}

		return;
	}

	//Returns true if all three cards in the accusation match the solution, false otherwise.
	public boolean checkAccusation(Card person, Card weapon, Card room)
	{
		Solution tempSolution = new Solution(person, weapon, room);

		if (tempSolution.getPerson().equals(solution.getPerson()) && 
				tempSolution.getWeapon().equals(solution.getWeapon()) &&
				tempSolution.getRoom().equals(solution.getRoom()))
		{
			return true;
		}
		return false;
	}

	//Randomly show a card that one of the computer players has to disprove a suggestion.
	public Card handleSuggestion(Card person, Card room, Card weapon)
	{
		ArrayList<Card> disproveCards = new ArrayList<Card>();
		Random rand = new Random();

		for (Player p : computerPlayers)
		{
			disproveCards.addAll(p.disproveSuggestion(person, room, weapon));
		}

		for (Player p : humanPlayer)
		{
			disproveCards.addAll(p.disproveSuggestion(person, room, weapon));
		}

		//Make sure none of currentPlayer's cards are shown to disprove the suggestion.
		disproveCards.removeAll(currentPlayer.getCards());

		if (disproveCards.size() > 0)
		{
			return disproveCards.get(rand.nextInt(disproveCards.size()));
		}
		return null;
	}

	public void selectAnswer()
	{
		return;
	}

	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}

	//This is for testing purposes only and should not be called anywhere in the game play	
	public void setSolution (Solution solution)
	{
		this.solution = solution;

		return;
	}
	//This is for testing purposes only and should not be called anywhere in the game play	
	public void setComputerPlayers (ArrayList<ComputerPlayer> computerPlayers)
	{
		this.computerPlayers = computerPlayers;

		return;
	}
	//This is for testing purposes only and should not be called anywhere in the game play	
	public void setHumanPlayer (HumanPlayer humanPlayer)
	{
		this.humanPlayer.clear();
		this.humanPlayer.add(humanPlayer);

		return;
	}

	//This is for testing purposes only and should not be called anywhere in the game play
	public void setCurrentPlayer (Player player)
	{
		this.currentPlayer = player;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);	
		
		for (int i=0; i<getNumRows(); i++)
		{
			for (int j=0; j<getNumColumns(); j++)
			{
				getBoardCellAt(calcIndex(i, j)).draw(g, this);
			}
		}

		for (int i=0; i<getNumRows(); i++)
		{
			for (int j=0; j<getNumColumns(); j++)
			{
				if (getBoardCellAt(calcIndex(i, j)).isRoom()) {
					((RoomCell)getBoardCellAt(calcIndex(i, j))).drawNames(g);
				}
			}
		}
	}

}



