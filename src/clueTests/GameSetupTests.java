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
import clueGame.WalkwayCell;

public class GameSetupTests {

	private static Board board;
	
	@BeforeClass
	public static void initBoard()
	{
		board = new Board();
	}
	
	@Test
	public void checkPlayers() {
		Player player = board.getHumanPlayer();
		Assert.assertEquals("Mrs. Peacock", player.getName());
		Assert.assertEquals(Color.BLUE, player.getColor());
		Assert.assertEquals(new WalkwayCell(5,1), player.getStartingLocation());
		
		player = board.getComputerPlayer(0);
		Assert.assertEquals("Colonel Mustard", player.getName());
		Assert.assertEquals(Color.YELLOW, player.getColor());
		Assert.assertEquals(new WalkwayCell(1,5), player.getStartingLocation());
		
		player = board.getComputerPlayer(4);
		Assert.assertEquals("Professor Plum", player.getName());
		Assert.assertEquals(Color.PINK, player.getColor());
		Assert.assertEquals(new WalkwayCell(16,4), player.getStartingLocation());
	}

	@Test
	public void checkCards() {
		ArrayList<Card> testCards;
		
		int roomCards = 0;
		int weaponCards = 0;
		int personCards = 0;
		
		testCards = board.getCards();
		
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
		
		Assert.assertEquals(26, testCards.size());
		Assert.assertEquals(10, roomCards);
		Assert.assertEquals(10, weaponCards);
		Assert.assertEquals(6, personCards);
		Assert.assertTrue(testCards.contains(new Card("Mrs. Peacock", CardType.PERSON)));
		Assert.assertTrue(testCards.contains(new Card("Candlestick", CardType.WEAPON)));
		Assert.assertTrue(testCards.contains(new Card("Kafadar", CardType.ROOM)));
	}
	
	@Test
	public void checkDeal() {
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
		Assert.assertEquals(26, cards.size());
	}
}
