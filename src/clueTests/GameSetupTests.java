package clueTests;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests {

	private static Board board;
	
	@BeforeClass
	public static void initBoard()
	{
		board = new Board();
	}
	
	@Test
	public void checkPlayers() {
		//Make sure the human player is assigned name, color, and location correctly.
		Player player = board.getHumanPlayer();
		Assert.assertEquals("Mrs. Peacock", player.getName());
		Assert.assertEquals(Color.BLUE, player.getColor());
		Assert.assertEquals(board.getBoardCellAt(board.calcIndex(4,0)), player.getCurrentLocation());
		
		//Make sure the other five computer players are assigned name, color, and location correctly.
		player = board.getComputerPlayer(0);
		Assert.assertEquals("Colonel Mustard", player.getName());
		Assert.assertEquals(Color.YELLOW, player.getColor());
		Assert.assertEquals(board.getBoardCellAt(board.calcIndex(0,5)), player.getCurrentLocation());
		
		player = board.getComputerPlayer(4);
		Assert.assertEquals("Professor Plum", player.getName());
		Assert.assertEquals(Color.PINK, player.getColor());
		Assert.assertEquals(board.getBoardCellAt(board.calcIndex(15,4)), player.getCurrentLocation());
		
		player = board.getComputerPlayer(1);
		Assert.assertEquals("Mrs. White", player.getName());
		Assert.assertEquals(Color.WHITE, player.getColor());
		Assert.assertEquals(board.getBoardCellAt(board.calcIndex(0,12)), player.getCurrentLocation());
		
		player = board.getComputerPlayer(2);
		Assert.assertEquals("Mr. Green", player.getName());
		Assert.assertEquals(Color.GREEN, player.getColor());
		Assert.assertEquals(board.getBoardCellAt(board.calcIndex(10, 16)), player.getCurrentLocation());
		
		player = board.getComputerPlayer(3);
		Assert.assertEquals("Miss Scarlet", player.getName());
		Assert.assertEquals(Color.RED, player.getColor());
		Assert.assertEquals(board.getBoardCellAt(board.calcIndex(15, 11)), player.getCurrentLocation());
	}

	@Test
	public void checkCards() {
		ArrayList<Card> testCards;
		
		int roomCards = 0;
		int weaponCards = 0;
		int personCards = 0;
		
		testCards = board.getCards();
		
		//Make sure all cards are initialized.
		for (Card c : testCards)
		{
			if (c.getCardType() == CardType.PERSON)
			{
				personCards++;
			}
			else if (c.getCardType() == CardType.ROOM)
			{
				roomCards++;
			}
			else if (c.getCardType() == CardType.WEAPON)
			{
				weaponCards++;
			}
		}
		
		//Test correct number of cards, rooms, weapons, and people.
		Assert.assertEquals(21, testCards.size());
		Assert.assertEquals(9, roomCards);
		Assert.assertEquals(6, weaponCards);
		Assert.assertEquals(6, personCards);
		Assert.assertTrue(testCards.contains(new Card("Mrs. Peacock", CardType.PERSON)));
		Assert.assertTrue(testCards.contains(new Card("Candlestick", CardType.WEAPON)));
		Assert.assertTrue(testCards.contains(new Card("Green Center", CardType.ROOM)));
		Assert.assertTrue(testCards.contains(new Card("Meyer", CardType.ROOM)));
		Assert.assertTrue(testCards.contains(new Card("Miss Scarlet", CardType.PERSON)));
	}
	
	@Test
	public void checkDeal() {
		//Check that all cards are dealt evenly.
		ArrayList<ComputerPlayer> computerPlayers = board.getComputerPlayers();
		HumanPlayer humanPlayer = board.getHumanPlayer();
		ArrayList<Card> cards = humanPlayer.getCards();
		
		int maxCards = humanPlayer.getCards().size();
		int minCards = humanPlayer.getCards().size();
		
		for (ComputerPlayer p : computerPlayers)
		{
			int cardCount = 0;
			
			for (Card c : p.getCards())
			{
				cardCount++;
				
				Assert.assertFalse(cards.contains(c));
				cards.add(c);
			}
			
			if (cardCount > maxCards)
			{
				maxCards = cardCount;
			}
			else if (cardCount < minCards)
			{
				minCards = cardCount;
			}
		}
		
		Assert.assertTrue((maxCards - minCards) <= 1);
		Assert.assertEquals(18, cards.size());
	}
}
