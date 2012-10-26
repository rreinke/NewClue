package clueTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.util.ArrayList;
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
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.RoomCell;
import clueGame.Solution;
import clueGame.WalkwayCell;

public class GameActionsTest {

	private static Board board;
	private static Card peacockCard;
	private static Card mustardCard;
	private static Card ropeCard;
	private static Card revolverCard;
	private static Card meyerCard;
	private static Card greenCenterCard;
	private static Solution solution;
	
	@BeforeClass
	public static void initBoard()
	{
		board = new Board();
		
		peacockCard = new Card("Mrs. Peacock", CardType.PERSON);
		mustardCard = new Card("Colonel Mustard", CardType.PERSON);
		ropeCard = new Card("Rope", CardType.WEAPON);
		revolverCard = new Card("Revolver", CardType.WEAPON);
		meyerCard = new Card("Meyer", CardType.ROOM);
		greenCenterCard = new Card("Green Center", CardType.ROOM);
		
		solution = new Solution(peacockCard, ropeCard, meyerCard);
		
		board.setSolution(solution);
	}

	
	@Test 
	public void checkAccusation() {
		Assert.assertTrue(board.checkAccusation(mustardCard, revolverCard, greenCenterCard));
		
		Assert.assertFalse(board.checkAccusation(peacockCard, revolverCard, greenCenterCard));
		
		Assert.assertFalse(board.checkAccusation(mustardCard, ropeCard, greenCenterCard));
		
		Assert.assertFalse(board.checkAccusation(mustardCard, revolverCard, meyerCard));
		
		Assert.assertTrue(board.checkAccusation(peacockCard, ropeCard, meyerCard));
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
			computerPlayer.setLastRoomVisited('?');
			
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

		board.calcTargets(board.calcIndex(15, 11), 2);
		int loc15_13 = 0;
		int loc13_11 = 0;
		int loc14_12 = 0;
		
		for (int i=0; i<100; i++) 
		{			
			BoardCell picked = player.pickLocation(board.getTargets());
			if (picked == board.getCellAt(board.calcIndex(15, 13)))
			{
				loc15_13++;
			}
			else if (picked == board.getCellAt(board.calcIndex(13, 11)))
			{
				loc13_11++;
			}
			else if (picked == board.getCellAt(board.calcIndex(14, 12)))
			{
				loc14_12++;
			}
			else
			{
				fail("Invalid target selected");
			}
		}
		
		assertEquals(100, loc15_13 + loc13_11 + loc14_12);
		
		assertTrue(loc15_13 > 10);
		assertTrue(loc13_11 > 10);
		assertTrue(loc14_12 > 10);							
	}
	
	@Test
	public void onePlayer_oneMatch() {
		HumanPlayer human = new HumanPlayer();
		ComputerPlayer player = new ComputerPlayer();
		ArrayList<ComputerPlayer> computerPlayers = new ArrayList<ComputerPlayer>();
		Card room = new Card ("Stratton", CardType.ROOM);
		Card weapon = new Card ("Candlestick", CardType.WEAPON);
		Card person = new Card ("Mr. Green", CardType.PERSON);
				
		player.addCard(peacockCard);
		player.addCard(mustardCard);
		player.addCard(ropeCard);
		player.addCard(revolverCard);
		player.addCard(meyerCard);
		player.addCard(greenCenterCard);
		
		computerPlayers.add(player);
		
		board.setComputerPlayers(computerPlayers);
		board.setHumanPlayer(human);
		
		Assert.assertEquals(null, board.handleSuggestion(person, room, weapon, human));
		Assert.assertEquals(revolverCard, board.handleSuggestion(person, room, revolverCard, human));
		Assert.assertEquals(meyerCard, board.handleSuggestion(person, meyerCard, weapon, human));
		Assert.assertEquals(mustardCard, board.handleSuggestion(mustardCard, room, weapon, human));
	}
	
	@Test
	public void onePlayer_multipleMatch() {
		HumanPlayer human = new HumanPlayer();
		ComputerPlayer player = new ComputerPlayer();
		ArrayList<ComputerPlayer> computerPlayers = new ArrayList<ComputerPlayer>();
		Card room = new Card ("Stratton", CardType.ROOM);
		Card weapon = new Card ("Candlestick", CardType.WEAPON);
		Card person = new Card ("Mr. Green", CardType.PERSON);
		int roomCount = 0;
		int weaponCount = 0;
		int personCount = 0;
				
		player.addCard(peacockCard);
		player.addCard(mustardCard);
		player.addCard(ropeCard);
		player.addCard(revolverCard);
		player.addCard(meyerCard);
		player.addCard(greenCenterCard);
		
		computerPlayers.add(player);
		
		board.setComputerPlayers(computerPlayers);
		board.setHumanPlayer(human);
		
		for (int i=0; i<100; i++)
		{
			Card temp = human.disproveSuggestion(peacockCard, greenCenterCard, ropeCard);
			
			if (temp.equals(peacockCard))
			{
				personCount++;
			}
			else if (temp.equals(greenCenterCard))
			{
				roomCount++;
			}
			else if (temp.equals(ropeCard))
			{
				weaponCount++;
			}
			else
			{
				fail("Invalid card returned!");
			}
		}
		
		Assert.assertEquals(100, roomCount + personCount + weaponCount);
		Assert.assertTrue(roomCount > 10);
		Assert.assertTrue(weaponCount > 10);
		Assert.assertTrue(personCount > 10);
	}
	
	@Test 
	public void multiplePlayer_match() {
		HumanPlayer human = new HumanPlayer();
		ComputerPlayer player = new ComputerPlayer();
		ComputerPlayer player2 = new ComputerPlayer();
		ComputerPlayer player3 = new ComputerPlayer();
		ComputerPlayer player4 = new ComputerPlayer();
		ComputerPlayer player5 = new ComputerPlayer();
		ArrayList<ComputerPlayer> computerPlayers = new ArrayList<ComputerPlayer>();
		Card room = new Card ("Stratton", CardType.ROOM);
		Card weapon = new Card ("Candlestick", CardType.WEAPON);
		Card person = new Card ("Mr. Green", CardType.PERSON);
		int player5Count = 0;
		int player2Count = 0;
		int playerCount = 0;
				
		human.addCard(peacockCard);
		player.addCard(mustardCard);
		player2.addCard(ropeCard);
		player3.addCard(revolverCard);
		player4.addCard(meyerCard);
		player5.addCard(greenCenterCard);
		
		computerPlayers.add(player);
		computerPlayers.add(player2);
		computerPlayers.add(player3);
		computerPlayers.add(player4);
		computerPlayers.add(player5);
		
		board.setComputerPlayers(computerPlayers);
		board.setHumanPlayer(human);
		
		Assert.assertEquals(null, board.handleSuggestion(person, room, weapon, human));
		Assert.assertEquals(peacockCard, board.handleSuggestion(peacockCard, room, weapon, player));
		Assert.assertEquals(null, board.handleSuggestion(peacockCard, room, weapon, human));
		Assert.assertEquals(null, board.handleSuggestion(mustardCard, room, weapon, player));
		
		for (int i=0; i<100; i++)
		{
			Card temp = board.handleSuggestion(mustardCard, greenCenterCard, ropeCard, human);
			
			if (temp.equals(mustardCard))
			{
				playerCount++;
			}
			else if (temp.equals(greenCenterCard))
			{
				player5Count++;
			}
			else if (temp.equals(ropeCard))
			{
				player2Count++;
			}
			else
			{
				fail("Invalid card returned!");
			}
		}
		
		Assert.assertEquals(100, player5Count + playerCount + player2Count);
		Assert.assertTrue(player5Count > 10);
		Assert.assertTrue(player2Count > 10);
		Assert.assertTrue(playerCount > 10);
	}
	
	@Test
	public void computerSuggestion() {
		ComputerPlayer computerPlayer = new ComputerPlayer();
		
		computerPlayer.updateSeen(peacockCard);
		computerPlayer.updateSeen(mustardCard);
		computerPlayer.updateSeen(ropeCard);
		computerPlayer.updateSeen(revolverCard);
		computerPlayer.updateSeen(greenCenterCard);
		computerPlayer.updateSeen(meyerCard);
		
		computerPlayer.setCurrentLocation(board.getRoomCellAt(7,3));
		computerPlayer.createSuggestion();
		
		Assert.assertEquals(new Card("Alderson", CardType.ROOM), computerPlayer.getSuggestedRoom());
		Assert.assertFalse(computerPlayer.getSeenCards().contains(peacockCard));
		Assert.assertFalse(computerPlayer.getSeenCards().contains(mustardCard));
		Assert.assertFalse(computerPlayer.getSeenCards().contains(ropeCard));
		Assert.assertFalse(computerPlayer.getSeenCards().contains(revolverCard));
	}
}
