package experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class IntBoard {
	private ArrayList<Integer> grid;
	private Map<Integer, LinkedList<Integer>> adjList;
	private TreeSet<Integer> targets;
	private int columns;
	private int rows;

	public IntBoard(int rows, int columns) {
		super();
		this.columns = columns;
		this.rows = rows;
		grid = new ArrayList<Integer>();
		for(int i = 0; i < rows*columns; ++i){
			grid.add(i);
		}
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		targets = new TreeSet<Integer>();
		calcAdjacencies();
	}

	public void calcAdjacencies() {
		for(Integer i: grid){
			adjList.put(i, new LinkedList<Integer>());
			// Check if i is on left side of the board
			if(i%columns != 0){
				adjList.get(i).add(i-1);
			}		
			// Check if i is on the right side of the board
			if((i+1)%columns != 0){
				adjList.get(i).add(i+1);
			}
			// Check if i is on top of board
			if(i >= columns){
				adjList.get(i).add(i-columns);
			}
			// Check if i is on bottom of board
			if(i+columns < grid.size()){
				adjList.get(i).add(i+columns);
			}
		}
	}

	public void calcTargets(int start, int steps) {	
		targets.clear();
		LinkedList<Integer> temp = new LinkedList<Integer>();
		// make a box
		for (int i = steps; i > 0; i -= 2) {
			int leftCol = start%columns - i;
			int rightCol = start%columns + i;
			int upRow = start/rows - i;
			int downRow = start/rows + i;
			for (int j = 0; j < i; ++j) {
				// top-left quarter of box
				if (leftCol + j >= 0 && start/rows - j >= 0 && start/rows < rows) 
					temp.add(calcIndex(start/rows - j, leftCol + j));
				// top-right quarter of box
				if (upRow + j >= 0 && start%columns + j >= 0 && start%columns + j < columns)
					temp.add(calcIndex(upRow + j, start%columns + j));
				// bottom-right quarter of box
				if (rightCol - j < columns && start/rows + j < rows && start/rows + j >= 0) 
					temp.add(calcIndex(start/rows + j, rightCol - j));
				// bottom-left quarter of box
				if (downRow - j < rows && start%columns - j < columns && start%columns - j >= 0)
					temp.add(calcIndex(downRow - j, start%columns - j));
			}
		}
		Set<Integer> bfs = new TreeSet<Integer>();
		bfs.add(start);
		LinkedList<Integer> next = new LinkedList<Integer>();
		next = getAdjList(start);
		LinkedList<Integer> current;
		for(int i = 0; i < steps; ++i){
			current = new LinkedList<Integer>(next);
			next.clear();
			for(int j = 0; j < current.size(); ++j){
				if(!bfs.contains(current.get(j))){
					bfs.add(current.get(j));
					next.addAll(getAdjList(current.get(j)));
				}
			}
		}
		for(int i = 0; i < temp.size(); ++i){
			if(bfs.contains(temp.get(i))){
				targets.add(temp.get(i));
			}
		}
	}

	public LinkedList<Integer> getAdjList(int i) {
		return adjList.get(i);
	}

	public TreeSet<Integer> getTargets() {
		return targets;
	}

	public int calcIndex(int row, int col) {
		return row*columns+col;
	}
}
