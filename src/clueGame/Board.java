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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private class Solution {
		public String person;
		public String weapon;
		public String room;
		
		public Solution(String person, String weapon, String room) {
			this.person = person;
			this.weapon = weapon;
			this.room = room;
		}
		
		public boolean verifyGuess(String guessPerson, String guessWeapon, String guessRoom)
		{
			return (this.person.equals(guessPerson) && this.weapon.equals(guessWeapon) && this.room.equals(guessRoom));
		}
	}
	
	// Variables
	private ArrayList<ComputerPlayer> computerPlayers;
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
	private final String legend = "~/../legend.csv";
	private final String layout = "~/../clue_board.csv";
	
	// Constructor
	public Board() {
		computerPlayers = new ArrayList<ComputerPlayer>();
		humanPlayer = new ArrayList<HumanPlayer>();
		cards = new ArrayList<Card>();
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character,String>();
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		loadConfigFiles();
		calcAdjacencies();
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
						cells.add(new RoomCell(i,numRows,numColumns));
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

	public int calcIndex(int row, int col) {
		return row*numColumns+col;
	}

	/*
	 * Name is self explanatory
	 */
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
	
	public BoardCell getCellAt(int i){
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
}

