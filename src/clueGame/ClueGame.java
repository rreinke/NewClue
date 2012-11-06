package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.border.*;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import clueGame.Card.CardType;

public class ClueGame extends JFrame {
	Board b = new Board();
	
	public ClueGame() {
		setTitle("Clue");
<<<<<<< HEAD
		setSize(680,680);
		setResizable(false);
=======
		setSize(300, 300);
>>>>>>> 7b55c34fa3a6cfdab2bba7902a6ce6aeb1ad0b25
		add(b, BorderLayout.CENTER);
		Animation anim = new Animation();
		add(anim);

		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		menu.add(createFileMenu());
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileItem("Exit"));
		menu.add(createFileItem("Detective Notes"));
		return menu;
	}

	private JMenuItem createFileItem(String name) {
<<<<<<< HEAD
		JMenuItem newItem = new JMenuItem(name);
		if(name.equals("Exit"))
		{
			newItem.addActionListener(new ExitItemListener());
		} else if (name.equals("Detective Notes"))
		{
			newItem.addActionListener(new NotesItemListener());
		}
		
		return newItem;
=======
		JMenuItem exitItem = new JMenuItem(name);
		if(name.equals("Exit"))
		{
			exitItem.addActionListener(new ExitItemListener());
		} else if (name.equals("Detective Notes"))
		{
			exitItem.addActionListener(new NotesItemListener());
		}
		
		return exitItem;
>>>>>>> 7b55c34fa3a6cfdab2bba7902a6ce6aeb1ad0b25
	}
	
	private class ExitItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) 
		{
			System.exit(0);
			
		}
			
	}
	
	private class NotesItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) 
		{
			//Main JPanel on Detective Notes
			JFrame det = new JFrame("Detective Notes");
			det.setLayout(new GridLayout(1, 2));
			det.setSize(800, 800);
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
<<<<<<< HEAD
			JPanel roomGuess = new JPanel();
			JPanel weaponGuess = new JPanel();
			guesses.setLayout(new GridLayout(3, 1));
			guesses.add(personGuess);
			guesses.add(roomGuess);
			guesses.add(weaponGuess);
			JComboBox<String> person = new JComboBox<String>();
			JComboBox<String> room = new JComboBox<String>();
			JComboBox<String> weapon = new JComboBox<String>();
			//ArrayList<JCheckBox> cbPlayers = new ArrayList<JCheckBox>();
=======
			guesses.setLayout(new GridLayout(3, 1));
			guesses.add(personGuess);
			//JComboBox person = new JComboBox();
			//personGuess.add();
>>>>>>> 7b55c34fa3a6cfdab2bba7902a6ce6aeb1ad0b25
			
			//Create the people check boxes on the people panel
			ArrayList<ComputerPlayer> cplayers = b.getComputerPlayers();
			HumanPlayer hplayer = b.getHumanPlayer();
			for(ComputerPlayer cp : cplayers) {
				people.add(new JCheckBox(cp.getName()));
<<<<<<< HEAD
				person.addItem(cp.getName());
=======
>>>>>>> 7b55c34fa3a6cfdab2bba7902a6ce6aeb1ad0b25
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
<<<<<<< HEAD
				room.addItem(roomList.get(rc));
=======
>>>>>>> 7b55c34fa3a6cfdab2bba7902a6ce6aeb1ad0b25
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
<<<<<<< HEAD
					weapon.addItem(c.getName());
=======
>>>>>>> 7b55c34fa3a6cfdab2bba7902a6ce6aeb1ad0b25
				}
			}
			weapons.setLayout(new GridLayout(3, 2));
			weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
<<<<<<< HEAD
			
			personGuess.add(person);
			personGuess.setBorder(new TitledBorder(new EtchedBorder(), "Person"));
			roomGuess.add(room);
			roomGuess.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
			weaponGuess.add(weapon);
			weaponGuess.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
			det.add(cardOptions);
			det.add(guesses);
=======
			det.add(cardOptions);
>>>>>>> 7b55c34fa3a6cfdab2bba7902a6ce6aeb1ad0b25
			det.setVisible(true);
		}
	}

	public static void main(String [] args) {
		ClueGame cg = new ClueGame();
		cg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cg.setVisible(true);
	}
}
