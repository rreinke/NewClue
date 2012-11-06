package clueGame;

public class Solution {
	public Card person;
	public Card weapon;
	public Card room;
	
	public Solution(Card person, Card weapon, Card room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public Card getPerson()
	{
		return person;
	}
	
	public Card getWeapon()
	{
		return weapon;
	}
	
	public Card getRoom()
	{
		return room;
	}
}
