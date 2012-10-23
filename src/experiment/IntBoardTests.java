package experiment;

import java.util.LinkedList;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntBoardTests {
	IntBoard board;
	
	@Before
	public void setUpBeforeClass() {
		board = new IntBoard(4,4);
	}

	@Test
	public void testCalcIndex5() {
		long expected = 5;
		long actual = board.calcIndex(1,1);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCalcIndex15() {
		long expected = 15;
		long actual = board.calcIndex(3,3);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCalcIndex2() {
		long expected = 2;
		long actual = board.calcIndex(0,2);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testAdjacency0(){
		LinkedList<Integer> testList = board.getAdjList(0);
		Assert.assertTrue(testList.contains(1));
		Assert.assertTrue(testList.contains(4));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency15(){
		LinkedList<Integer> testList = board.getAdjList(15);
		Assert.assertTrue(testList.contains(14));
		Assert.assertTrue(testList.contains(11));
		Assert.assertEquals(2,testList.size());
	}
	
	@Test
	public void testAdjacency8(){
		LinkedList<Integer> testList = board.getAdjList(8);
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(12));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency7(){
		LinkedList<Integer> testList = board.getAdjList(7);
		Assert.assertTrue(testList.contains(3));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(11));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency9(){
		LinkedList<Integer> testList = board.getAdjList(9);
		Assert.assertTrue(testList.contains(8));
		Assert.assertTrue(testList.contains(10));
		Assert.assertTrue(testList.contains(5));
		Assert.assertTrue(testList.contains(13));
		Assert.assertEquals(4, testList.size());
	}

	@Test
	public void testAdjacency6(){
		LinkedList<Integer> testList = board.getAdjList(6);
		Assert.assertTrue(testList.contains(5));
		Assert.assertTrue(testList.contains(7));
		Assert.assertTrue(testList.contains(2));
		Assert.assertTrue(testList.contains(10));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testTarget0_2(){
		board.calcTargets(0, 2);
		TreeSet<Integer> testTree = board.getTargets();
		Assert.assertTrue(testTree.contains(2));
		Assert.assertTrue(testTree.contains(8));
		Assert.assertTrue(testTree.contains(5));
		Assert.assertEquals(3, testTree.size());
	}
	
	@Test
	public void testTarget2_3(){
		board.calcTargets(2, 3);
		TreeSet<Integer> testTree = board.getTargets();
		Assert.assertTrue(testTree.contains(4));
		Assert.assertTrue(testTree.contains(9));
		Assert.assertTrue(testTree.contains(14));
		Assert.assertTrue(testTree.contains(11));
		Assert.assertTrue(testTree.contains(1));
		Assert.assertTrue(testTree.contains(3));
		Assert.assertTrue(testTree.contains(6));
		Assert.assertEquals(7, testTree.size());
	}
	
	@Test
	public void testTarget10_6(){
		board.calcTargets(10, 6);
		TreeSet<Integer> testTree = board.getTargets();
		Assert.assertTrue(testTree.contains(0));
		Assert.assertTrue(testTree.contains(2));
		Assert.assertTrue(testTree.contains(5));
		Assert.assertTrue(testTree.contains(7));
		Assert.assertTrue(testTree.contains(8));
		Assert.assertTrue(testTree.contains(13));
		Assert.assertTrue(testTree.contains(15));
		Assert.assertEquals(7, testTree.size());
	}
	
	@Test
	public void testTarget8_5(){
		board.calcTargets(8,5);
		TreeSet<Integer> testTree = board.getTargets();
		Assert.assertTrue(testTree.contains(1));
		Assert.assertTrue(testTree.contains(3));
		Assert.assertTrue(testTree.contains(4));
		Assert.assertTrue(testTree.contains(6));
		Assert.assertTrue(testTree.contains(9));
		Assert.assertTrue(testTree.contains(11));
		Assert.assertTrue(testTree.contains(12));
		Assert.assertTrue(testTree.contains(14));
		Assert.assertEquals(8, testTree.size());
	}
	
	@Test
	public void testTarget5_1(){
		board.calcTargets(5,1);
		TreeSet<Integer> testTree = board.getTargets();
		Assert.assertTrue(testTree.contains(4));
		Assert.assertTrue(testTree.contains(6));
		Assert.assertTrue(testTree.contains(1));
		Assert.assertTrue(testTree.contains(9));
		Assert.assertEquals(4, testTree.size());
	}
	
	@Test
	public void testTarget14_4(){
		board.calcTargets(14,4);
		TreeSet<Integer> testTree = board.getTargets();
		Assert.assertTrue(testTree.contains(4));
		Assert.assertTrue(testTree.contains(6));
		Assert.assertTrue(testTree.contains(1));
		Assert.assertTrue(testTree.contains(3));
		Assert.assertTrue(testTree.contains(9));
		Assert.assertTrue(testTree.contains(11));
		Assert.assertTrue(testTree.contains(12));
		Assert.assertEquals(7, testTree.size());
	}
}
