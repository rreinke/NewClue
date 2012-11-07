package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class DetectiveDialog extends JDialog {
	Board b = new Board();
	
	public DetectiveDialog() {
		//Main JPanel on Detective Notes
		setTitle("Detective Notes");
		setLayout(new GridLayout(1, 2));
		setSize(800, 800);
		//Create a nested panel for the right-hand side of the notes for the Cards
		JPanel cardOptions = new JPanel();
		//The cardOptions panel must have one panel each for the people, room and weapon cards
		JPanel people = new JPanel();
		cardOptions.add(people);
		cardOptions.setLayout(new GridLayout(3, 1));
		//Create a nested panel for the left-hand side of the notes for the guess
		
		JPanel guesses = new JPanel();
		//The guesses panel must have panel each for the person, room, and weapon guess
		JPanel personGuess = new JPanel();
		JPanel roomGuess = new JPanel();
		JPanel weaponGuess = new JPanel();
		guesses.setLayout(new GridLayout(3, 1));
		guesses.add(personGuess);
		guesses.add(roomGuess);
		guesses.add(weaponGuess);
		JComboBox person = new JComboBox();
		JComboBox room = new JComboBox();
		JComboBox weapon = new JComboBox();
		
		//Create the people check boxes on the people panel
		ArrayList<ComputerPlayer> cplayers = b.getComputerPlayers();
		HumanPlayer hplayer = b.getHumanPlayer();
		for(ComputerPlayer cp : cplayers) {
			people.add(new JCheckBox(cp.getName()));
			person.addItem(cp.getName());
		}
		people.add(new JCheckBox(hplayer.getName()));
		people.setLayout(new GridLayout(3, 2));
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		//Create the room check boxes on the rooms panel
		JPanel rooms = new JPanel();
		cardOptions.add(rooms);
		Map<Character, String> roomList = b.getRooms();
		Set<Character>roomChars = roomList.keySet();
		for(char rc : roomChars) {
			rooms.add(new JCheckBox(roomList.get(rc)));

			room.addItem(roomList.get(rc));

		}
		rooms.setLayout(new GridLayout(5, 2));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		
		//Create the weapons check boxes for the weapons panel
		JPanel weapons = new JPanel();
		cardOptions.add(weapons);
		ArrayList<Card> cards = b.getCards();
		for(Card c : cards) {
			if(c.getCardType().equals(CardType.WEAPON)) {
				weapons.add(new JCheckBox(c.getName()));
				weapon.addItem(c.getName());

			}
		}
		weapons.setLayout(new GridLayout(3, 2));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		
		personGuess.add(person);
		personGuess.setBorder(new TitledBorder(new EtchedBorder(), "Person"));
		roomGuess.add(room);
		roomGuess.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		weaponGuess.add(weapon);
		weaponGuess.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		add(cardOptions);
		add(guesses);
		
	
	}
}
