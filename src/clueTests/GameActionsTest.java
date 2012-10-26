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

	//Declaration of board, solution, and multiple card variables.
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
		//Initialization of the variables declared above.
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
		//Make sure checkAccusation returns true only when all three components are correct.
		Assert.assertFalse(board.checkAccusation(mustardCard, revolverCard, greenCenterCard));
		
		Assert.assertFalse(board.checkAccusation(peacockCard, revolverCard, greenCenterCard));
		
		Assert.assertFalse(board.checkAccusation(mustardCard, ropeCard, greenCenterCard));
		
		Assert.assertFalse(board.checkAccusation(mustardCard, revolverCard, meyerCard));
		
		Assert.assertTrue(board.checkAccusation(peacockCard, ropeCard, meyerCard));
	}
	
	@Test
	public void testTargetPickRoom() {
		//Make sure the computer player picks the room target unless it was the last room visited.
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
		//Make sure that the computer player randomly chooses a target with about equal probability.
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
	//Make sure that the computer player randomly chooses a target with about equal probability.
	//This time including a room.
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
		//Make sure the correct card is shown if a guess has a card belonging to one of the other players.
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
		
		board.setCurrentPlayer(human);
		
		Assert.assertEquals(null, board.handleSuggestion(person, room, weapon));
		Assert.assertEquals(revolverCard, board.handleSuggestion(person, room, revolverCard));
		Assert.assertEquals(meyerCard, board.handleSuggestion(person, meyerCard, weapon));
		Assert.assertEquals(mustardCard, board.handleSuggestion(mustardCard, room, weapon));
	}
	
	@Test
	public void onePlayer_multipleMatch() {
		//Make sure when multiple cards in a guess belong to one player that only one of those
		//cards is randomly shown. Each card should have about likely probability of being chosen.
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
		//Make sure when multiple cards in a guess belong to various players that only one of those
		//cards is randomly shown. Each card should have about likely probability of being chosen.
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
		
		//These are the tests for the computer player suggestions
		board.setCurrentPlayer(player);
		
		Assert.assertEquals(peacockCard, board.handleSuggestion(peacockCard, room, weapon));
		Assert.assertEquals(null, board.handleSuggestion(mustardCard, room, weapon));
		
		//The rest of the tests are for the human
		board.setCurrentPlayer(human);
		
		Assert.assertEquals(null, board.handleSuggestion(person, room, weapon));
		Assert.assertEquals(null, board.handleSuggestion(peacockCard, room, weapon));
		
		
		for (int i=0; i<100; i++)
		{
			Card temp = board.handleSuggestion(mustardCard, greenCenterCard, ropeCard);
			
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
		//Make sure the computerPlayer "intelligently" makes a suggestion with cards not yet seen.
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
