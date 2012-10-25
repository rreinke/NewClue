package clueTests;

import junit.framework.Assert;

import org.junit.*;

import clueGame.Board;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.Solution;

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
		roomCard = new Card("Kafadar", CardType.ROOM);
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
}
