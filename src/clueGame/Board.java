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

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Board {
	// Variables
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private int numRows;
	private int numColumns;
	private Map<Integer, LinkedList<Integer>> adjList;
	private Set<BoardCell> targets;
	private boolean[] seen; 
	// Change names to load the respective files
	private final String legend = "ClueLegend.txt";
	private final String layout = "ClueLayout.csv";
	
	// Constructor
	public Board() {
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
		//This will be incremented to 0 for the first row later on
		numRows = -1;
		try{
			//Read in the legend and load up the rooms maps
			Scanner scn = new Scanner(new FileReader(legend));
			String line;
			while(scn.hasNext()){
				line = scn.nextLine();
				if(!line.contains(",")){
					throw new BadConfigFormatException("One of your lines doesn't contain 2 entries.");
				}
				String[] split = line.split(",");
				try{
					split[2] = "";
					throw new BadConfigFormatException("Legend: A line has more than 2 entries");
				} catch(ArrayIndexOutOfBoundsException e){
					//We wanted it to throw an exception, it means there's only two values that were read in
				}
				rooms.put(split[0].charAt(0),split[1].trim());
			}

			//Read the layout file and load up the cells ArrayList
			scn = new Scanner(new FileReader(layout));
			while(scn.hasNext()){
				numColumns = -1;
				numRows += 1;
				line = scn.nextLine();
				String[] split = line.split(",");
				for(String i : split){
					try{
						i.charAt(2);
						throw new BadConfigFormatException("Unknown room identifier: " + i);
					} catch(StringIndexOutOfBoundsException e){
						//We wanted the exception to be thrown, it means there's a three character string in
						//layout
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
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public int calcIndex(int row, int col) {
		return row*numColumns+col;
	}

	/*
	 * Name is self explanatory
	 */
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
		
		
		
		/*
		 * This was our old calcTargets algorithm that failed under conditions where
		 * back tracking was present. If that condition wasn't there, this would calculate
		 * the targets list in ~O(n^2) time instead of the O(4^n) time that is the recursive
		 * algorithm. 
		 *
		 * The first part of the algorithm creates a checkerboard pattern of all possible
		 * targets with start being the center. The next part of the algorithm was a 
		 * breadth first search (bfs) starting from start and fanning out by steps. We then intersect
		 * the checkerboard with the bfs to create the final targets list, with the exception 
		 * that if the bfs encountered a door, add it to the targets list as well.
		 */
//		LinkedList<Integer> temp = new LinkedList<Integer>();
//		// make a box
//		for (int i = steps; i > 0; i -= 2) {
//			int leftCol = start%numColumns - i;
//			int rightCol = start%numColumns + i;
//			int upRow = start/numColumns - i;
//			int downRow = start/numColumns + i;
//			for (int j = 0; j < i; ++j) {
//				// top-left quarter of box
//				if (leftCol + j >= 0 && start/numColumns - j >= 0 && start/numColumns < numColumns) 
//					temp.add(calcIndex(start/numColumns - j, leftCol + j));
//				// top-right quarter of box
//				if (upRow + j >= 0 && start%numColumns + j >= 0 && start%numColumns + j < numColumns)
//					temp.add(calcIndex(upRow + j, start%numColumns + j));
//				// bottom-right quarter of box
//				if (rightCol - j < numColumns && start/numColumns + j < numRows && start/numColumns + j >= 0) 
//					temp.add(calcIndex(start/numColumns + j, rightCol - j));
//				// bottom-left quarter of box
//				if (downRow - j < numRows && start%numColumns - j < numColumns && start%numColumns - j >= 0)
//					temp.add(calcIndex(downRow - j, start%numColumns - j));
//			}
//		}
//		Set<Integer> bfs = new TreeSet<Integer>();
//		LinkedList<Integer> next = new LinkedList<Integer>(getAdjList(start));
//		LinkedList<Integer> current = new LinkedList<Integer>();
//		LinkedList<Integer> previous = new LinkedList<Integer>();
//		System.out.print("BFS from ");
//		inverseCalcIndex(start);
//		for(int i = 0; i < steps; ++i){
//			if (i > 0) {
//				previous = new LinkedList<Integer>(current);
//			}
//			else {
//				previous = new LinkedList<Integer>();
//				previous.add(start);
//			}
//			current = new LinkedList<Integer>(next);
//			next.clear();
//			for(int j = 0; j < current.size(); ++j){
//				if(!bfs.contains(current.get(j))){
//					bfs.add(current.get(j));
//					for (int k = 0; k < getAdjList(current.get(j)).size(); ++k) {
//						if (!previous.contains(getAdjList(current.get(j)).get(k))) {
//							next.add(getAdjList(current.get(j)).get(k));
//						}
//					}
//				}
//			}
//		}
//		LinkedList<Integer> print = new LinkedList<Integer>();
//		for(Integer i : bfs){
//			if(i == start) continue;
//			if(temp.contains(i) || cells.get(i).isDoorway()){
//				print.add(i);
//				targets.add(cells.get(i));
//			}
//		}
//		System.out.print("starting from: ");
//		inverseCalcIndex(start);
//		for(Integer i : print){
//			inverseCalcIndex(i);
//		}
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

