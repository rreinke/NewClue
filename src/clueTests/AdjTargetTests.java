package clueTests;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class AdjTargetTests {
	
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board();
	}

	@Test
	public void adjOnlyWalkwaysTest() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(12, 5));
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(11,5)));
		assertTrue(testList.contains(board.calcIndex(13,5)));
		assertTrue(testList.contains(board.calcIndex(12,4)));
		assertTrue(testList.contains(board.calcIndex(12,6)));
	}
	
	@Test
	public void adjEdgeTest() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 12));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(1,12)));
		assertTrue(testList.contains(board.calcIndex(0,11)));
		
		testList = board.getAdjList(board.calcIndex(10, 16));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(10,15)));
		assertTrue(testList.contains(board.calcIndex(11,16)));
		
		testList = board.getAdjList(board.calcIndex(15, 4));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(14,4)));
		assertTrue(testList.contains(board.calcIndex(15,5)));
		
		testList = board.getAdjList(board.calcIndex(4, 0));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(5,0)));
		assertTrue(testList.contains(board.calcIndex(4,1)));
	}
	
	@Test
	public void adjBesideRoomTest() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(3, 6));
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(3,5)));
		assertTrue(testList.contains(board.calcIndex(4,6)));
		
		testList = board.getAdjList(board.calcIndex(13, 12));
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(13,11)));
		assertTrue(testList.contains(board.calcIndex(12,12)));
		assertTrue(testList.contains(board.calcIndex(14,12)));
	}
	
	@Test
	public void adjBesideDoorTest() {
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 4));
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(0,3)));
		assertTrue(testList.contains(board.calcIndex(0,5)));
		assertTrue(testList.contains(board.calcIndex(1,4)));
		
		testList = board.getAdjList(board.calcIndex(7, 12));
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(7,11)));
		assertTrue(testList.contains(board.calcIndex(7,13)));
		assertTrue(testList.contains(board.calcIndex(6,12)));
		assertTrue(testList.contains(board.calcIndex(8,12)));
	}
	
	@Test
	public void targetWalkwaysTest() {
		board.calcTargets(board.calcIndex(5, 6), 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(10, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 5))));	
		
		board.calcTargets(board.calcIndex(4, 12), 1);
		targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 12))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 12))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 11))));
		
		board.calcTargets(board.calcIndex(10, 10), 1);
		targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 9))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 10))));
		
		board.calcTargets(board.calcIndex(11, 5), 2);
		targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 5))));	
	}
	
	@Test
	public void targetEnterRoomTest() {
		board.calcTargets(board.calcIndex(10, 2), 3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 5))));
		
		board.calcTargets(board.calcIndex(11, 10), 2);
		targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 11))));
	}
	
	@Test
	public void targetLeaveRoomTest() {
		board.calcTargets(board.calcIndex(3, 8), 1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 8))));
		
		board.calcTargets(board.calcIndex(12, 1), 2);
		targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 2))));
	}

}
