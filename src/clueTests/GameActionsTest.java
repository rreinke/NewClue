package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ComputerPlayer;
import clueGame.RoomCell;
import clueGame.Solution;
import clueGame.WalkwayCell;

public class GameActionsTest {

	private static Board board;
	private static Card personCard;
	private static Card solutionPerson;
	private static Card weaponCard;
	private static Card solutionWeapon;
	private static Card roomCard;
	private static Card solutionRoom;
	private static Solution solution;
	
	@BeforeClass
	public static void initBoard()
	{
		board = new Board();
		
		personCard = new Card("Mrs. Peacock", CardType.PERSON);
		solutionPerson = new Card("Colonel Mustard", CardType.PERSON);
		weaponCard = new Card("Rope", CardType.WEAPON);
		solutionWeapon = new Card("Revolver", CardType.WEAPON);
		roomCard = new Card("Meyer", CardType.ROOM);
		solutionRoom = new Card("Green Center", CardType.ROOM);
		
		solution = new Solution(personCard, weaponCard, roomCard);
		
		board.setSolution(solution);
	}

	
	@Test 
	public void checkAccusation() {
		Assert.assertTrue(board.checkAccusation(solutionPerson, solutionWeapon, solutionRoom));
		
		Assert.assertFalse(board.checkAccusation(personCard, solutionWeapon, solutionRoom));
		
		Assert.assertFalse(board.checkAccusation(solutionPerson, weaponCard, solutionRoom));
		
		Assert.assertFalse(board.checkAccusation(solutionPerson, solutionWeapon, roomCard));
		
		Assert.assertTrue(board.checkAccusation(personCard, weaponCard, roomCard));
	}
	
	@Test
	public void testTargetPickRoom() {
		BoardCell startLocation = new WalkwayCell(15,4);
		ComputerPlayer computerPlayer = new ComputerPlayer("Test", Color.BLUE, startLocation);
		
		Set<BoardCell> rooms = new HashSet<BoardCell>();
		
		rooms.add(new RoomCell("KU",1,1));
		rooms.add(new WalkwayCell(0,0));
		rooms.add(new WalkwayCell(1,2));
		rooms.add(new WalkwayCell(2,1));
		
		for (int i=0; i<10; i++)
		{
			Assert.assertTrue(computerPlayer.pickLocation(rooms).isRoom());
		}
	}
		
	@Test
	public void testTargetNoRoom() {
		ComputerPlayer player = new ComputerPlayer();

		board.calcTargets(board.calcIndex(1, 11), 2);
		int loc0_12 = 0;
		int loc2_12 = 0;
		int loc3_11 = 0;
	
		for (int i=0; i<100; i++) 
		{
			BoardCell picked = player.pickLocation(board.getTargets());
			if (picked == board.getCellAt(board.calcIndex(0, 12)))
			{
				loc0_12++;
			}
			else if (picked == board.getCellAt(board.calcIndex(14, 2)))
			{
				loc2_12++;
			}
			else if (picked == board.getCellAt(board.calcIndex(15, 1)))
			{
				loc3_11++;
			}
			else
			{
				fail("Invalid target selected");
			}
		}
		
		assertEquals(100, loc0_12 + loc2_12 + loc3_11);
		
		assertTrue(loc0_12 > 10);
		assertTrue(loc2_12 > 10);
		assertTrue(loc3_11 > 10);							
	}
	
	@Test
	public void testTargetPreviousVisited() {
		ComputerPlayer player = new ComputerPlayer();

		board.calcTargets(board.calcIndex(1, 11), 2);
		int loc0_12 = 0;
		int loc2_12 = 0;
		int loc3_11 = 0;
	
		for (int i=0; i<100; i++) 
		{
			BoardCell picked = player.pickLocation(board.getTargets());
			if (picked == board.getCellAt(board.calcIndex(0, 12)))
			{
				loc0_12++;
			}
			else if (picked == board.getCellAt(board.calcIndex(14, 2)))
			{
				loc2_12++;
			}
			else if (picked == board.getCellAt(board.calcIndex(15, 1)))
			{
				loc3_11++;
			}
			else
			{
				fail("Invalid target selected");
			}
		}
		
		assertEquals(100, loc0_12 + loc2_12 + loc3_11);
		
		assertTrue(loc0_12 > 10);
		assertTrue(loc2_12 > 10);
		assertTrue(loc3_11 > 10);							
	}
}
