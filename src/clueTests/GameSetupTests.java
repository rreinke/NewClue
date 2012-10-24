package clueTests;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Card.CardType;
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
		Assert.assertEquals("Orion", player.getName());
		Assert.assertEquals(Color.BLUE, player.getColor());
		Assert.assertEquals(new WalkwayCell(5,1), player.getStartingLocation());
		
		player = board.getComputerPlayers().get(0);
		Assert.assertEquals("Rachel", player.getName());
		Assert.assertEquals(Color.PINK, player.getColor());
		Assert.assertEquals(new WalkwayCell(1,5), player.getStartingLocation());
		
		player = board.getComputerPlayers().get(4);
		Assert.assertEquals("Adam", player.getName());
		Assert.assertEquals(Color.RED, player.getColor());
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
		Assert.assertTrue(testCards.contains(new Card("Orion", CardType.PERSON)));
		Assert.assertTrue(testCards.contains(new Card("Candlestick", CardType.WEAPON)));
		Assert.assertTrue(testCards.contains(new Card("Kafadar", CardType.ROOM)));
	}
}
