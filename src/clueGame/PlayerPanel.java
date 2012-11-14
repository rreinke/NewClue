package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class PlayerPanel extends JPanel{
	JLabel myCards;
	JTextArea peopleField, roomField, weaponField;
	JPanel pPanel, rPanel, wPanel;

	public PlayerPanel(HumanPlayer player) {
		myCards = new JLabel("My Cards");
		peopleField = new JTextArea();
		roomField = new JTextArea();
		weaponField = new JTextArea();

		//Displaying the players cards to the screen
		ArrayList<Card> humanPlayerCards  = new ArrayList<Card>();
		humanPlayerCards = player.getCards();
		//String variables to keep track of more than one type of card
		String roomTxt = "";
		String personTxt = "";
		String weaponTxt = "";

		for(Card c : humanPlayerCards) {
			//Add name to the text if there is already text
			if(c.getCardType() == CardType.PERSON) {
				if(!(personTxt.equals(""))) {
					personTxt = personTxt + " \n" + (c.getName());
				} else {
					personTxt = c.getName();
				}
			} else if(c.getCardType() == CardType.ROOM) {
				if(!(roomTxt.equals(""))) {
					roomTxt = roomTxt + " \n" + (c.getName());
				} else {
					roomTxt = c.getName();
				}
			} else {
				if(!(weaponTxt.equals(""))) {
					weaponTxt = weaponTxt + " \n" + (c.getName());
				} else {
					weaponTxt = c.getName();
				}
			}
		}
		roomField.setText(roomTxt);
		roomField.setEditable(false);
		peopleField.setText(personTxt);
		peopleField.setEditable(false);
		weaponField.setText(weaponTxt);
		weaponField.setEditable(false);

		pPanel = new JPanel();
		rPanel = new JPanel();
		wPanel = new JPanel();

		pPanel.add(peopleField);
		rPanel.add(roomField);
		wPanel.add(weaponField);
		pPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		rPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		wPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));

		setLayout(new GridLayout(4,1));
		add(myCards);
		add(pPanel);
		add(rPanel);
		add(wPanel);
	}
}
