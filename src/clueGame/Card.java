package clueGame;

public class Card {

	public enum CardType { ROOM, WEAPON, PERSON };
	
	public String name;
	private CardType cardType;
	
	public Card(String name, CardType cardType) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.cardType = cardType;
	}

	public String getName()
	{
		return name;
	}
	
	public CardType getCardType()
	{
		return cardType;
	}
	
	public void setName (String name)
	{
		this.name = name;
		
		return;
	}
	
	public void setCardType(CardType cardType)
	{
		this.cardType = cardType;
		
		return;
	}
	
	public boolean equals(Object cardToCompare)
	{
		if (((Card)cardToCompare).name.equals(this.name) && ((Card)cardToCompare).cardType.equals(this.cardType))
		{
			return true;
		}
		
		return false;
	}
}
