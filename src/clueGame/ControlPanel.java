package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import clueGame.Card.CardType;

/*1. Fix mouse listener
 * 2. Get rid of red squares for the computer turn (not have to push nxtPlayer twice)
 * 3. Make sure that a suggestion is made when a player enters a room
 * 4. Randomly choose a card to disprove a suggestion/accusation
 * 
 */

public class ControlPanel extends JPanel{
	JLabel whos, die, guess, response;	
	JTextField turnField, respField, dieField, guessField;
	JButton nxtPlayer, mkAccusation, submit;
	JPanel diePanel, gPanel, gResultPanel, whosPanel;
	JComboBox personAccusation, roomAccusation, weaponAccusation;
	Random rand = new Random();
	static Boolean humanTurn = false;
	static Boolean computerTurn = false;
	Graphics g;
	int counter;
	int dieNumber;
	Player currentPlayer;

	public ControlPanel(final Board b) {
		counter = b.getComputerPlayers().size();
		whos = new JLabel("Whose Turn?");
		die = new JLabel("Roll");
		guess = new JLabel("Guess");
		response = new JLabel("Response");

		turnField = new JTextField(20);
		respField = new JTextField("Response");
		dieField = new JTextField("5");
		guessField = new JTextField("Guess");
		respField.setEditable(false);
		dieField.setEditable(false);
		guessField.setEditable(false);

		nxtPlayer = new JButton("Next Player");
		mkAccusation = new JButton("Make Accusation");

		gPanel = new JPanel();
		gResultPanel = new JPanel();
		diePanel = new JPanel();
		whosPanel = new JPanel();
		whosPanel.setLayout(new GridLayout(2,1));

		whosPanel.add(whos);
		whosPanel.add(turnField);
		diePanel.add(die);
		diePanel.add(dieField);
		diePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		gPanel.add(guess);
		gPanel.add(guessField);
		gPanel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		gResultPanel.add(response);
		gResultPanel.add(respField);
		gResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		
		class nxtPlayerListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dieNumber = rand.nextInt(6)+1;
				int numCompPlayers = b.getComputerPlayers().size();

				if(counter == numCompPlayers) {
					computerTurn = false;
					humanTurn(b, b.getHumanPlayer());
					counter = 0;
				} else if(computerTurn){
					computerTurn(b, b.getComputerPlayers().get(counter));
				}
			}
		}

		nxtPlayer.addActionListener(new nxtPlayerListener());

		class submitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(personAccusation.getSelectedItem().toString().equals(b.solution.person.getName()) &&
						roomAccusation.getSelectedItem().toString().equals(b.solution.room.getName()) &&
						weaponAccusation.getSelectedItem().toString().equals(b.solution.weapon.getName())) {
					JOptionPane.showMessageDialog(submit, "YOU WIN!!!!");
				} else {
					JOptionPane.showMessageDialog(submit, "Not quite...");
				}
			}
		}

		//Class extends JPanel for the accusation panel	
		class accusationPanel extends JPanel {
			public accusationPanel() {
				JPanel ap = new JPanel();
				submit = new JButton("Submit");
				submit.addActionListener(new submitListener());
				JLabel room, person, weapon;

				room = new JLabel("Room");
				person = new JLabel("Person");
				weapon = new JLabel("Weapon");
				ap.setLayout(new GridLayout(4, 2));

				personAccusation = createNewPersonComboBox(b);
				roomAccusation = createNewRoomComboBox(b);
				weaponAccusation = createNewWeaponComboBox(b);

				ap.add(room);
				ap.add(roomAccusation);
				ap.add(person);
				ap.add(personAccusation);
				ap.add(weapon);
				ap.add(weaponAccusation);	
				ap.add(submit);
				add(ap);
			}
		}

		//class that extends JFrame for the accusation panel when make accusation is clicked
		class accusationFrame extends JFrame {
			public accusationFrame() {
				JFrame af = new JFrame();
				af.setTitle("Accusation!");
				af.setSize(300, 150);
				af.add(new accusationPanel(), BorderLayout.CENTER);
				af.setVisible(true);
			}
		}

		class makeAccusationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(humanTurn) {
					accusationFrame af = new accusationFrame();
				} else {
					JOptionPane.showMessageDialog(mkAccusation, "It is not your turn! You can't make an accusation!");

				}
			}
		}

		mkAccusation.addActionListener(new makeAccusationListener());

		setLayout(new GridLayout(2,3));
		add(whosPanel);
		add(nxtPlayer);
		add(mkAccusation);
		add(diePanel);
		add(gPanel);
		add(gResultPanel);
	}

	public void computerTurn(Board b, ComputerPlayer cp) {	
		currentPlayer = b.getComputerPlayer(counter);
		counter++;
		
		//Displaying roll to the screen
		dieField.setText(Integer.toString(dieNumber));
		
		//Display the player's name to the screen
		turnField.setText(b.getComputerPlayers().get(counter-1).getName());
		
		b.calcTargets(b.calcIndex(cp.currentLocation.row, cp.currentLocation.col), dieNumber);
		ArrayList<BoardCell> moveChoices = new ArrayList<BoardCell>();
		Set<BoardCell> options = b.getTargets();
		for(BoardCell bc : options) {
			moveChoices.add(bc);
		}
		int numChoice = rand.nextInt(moveChoices.size());
		BoardCell choice = moveChoices.get(numChoice);
		cp.setCurrentLocation(choice.row, choice.col);
		//BoardCell chosen = b.getBoardCellAt(b.calcIndex(cp.currentLocation.row, cp.currentLocation.col));
		if(cp.getCurrentLocation().isRoom()) {	
			cp.createSuggestion(b.getCards());
			guessField.setText(cp.getSuggestedPerson().getName() + " " + cp.getSuggestedWeapon().getName());
			respField.setText(((RoomCell) cp.getCurrentLocation()).getRoomName());
		}
		b.repaint();
	}

	public void humanTurn(Board b, HumanPlayer hp) {
		currentPlayer = b.getHumanPlayer();
		humanTurn = true;
		
		//Displaying roll to the screen
		dieField.setText(Integer.toString(dieNumber));
		
		b.calcTargets(b.calcIndex(hp.currentLocation.row, hp.currentLocation.col), dieNumber);
		b.repaint();
		/*if(b.getBoardCellAt(b.calcIndex(hp.currentLocation.row, hp.currentLocation.col)).isRoom()) {
			JPanel sp = new JPanel();
			JButton submit = new JButton("Submit");
			//submit.addActionListener(new submitListener());
			JLabel room, person, weapon;
			JTextField currentRoom =
			   new JTextField(b.getRoomCellAt(currentPlayer.currentLocation.row, currentPlayer.currentLocation.col).getRoomName());
			JComboBox personAccusation, weaponAccusation;

			room = new JLabel("Room");
			person = new JLabel("Person");
			weapon = new JLabel("Weapon");
			sp.setLayout(new GridLayout(4, 2));

			personAccusation = createNewPersonComboBox(b);
			weaponAccusation = createNewWeaponComboBox(b);

			sp.add(room);
			sp.add(currentRoom);
			currentRoom.setEditable(false);
			sp.add(person);
			sp.add(personAccusation);
			sp.add(weapon);
			sp.add(weaponAccusation);	
			sp.add(submit);
			add(sp);
			
			JFrame sf = new JFrame();
			sf.setTitle("Suggestion!");
			sf.setSize(300, 200);
			sf.add(sp, BorderLayout.CENTER);
			sf.setVisible(true);
		}*/
	}
	
	public JComboBox createNewPersonComboBox(Board b) {
		JComboBox personCombo = new JComboBox();
		ArrayList<String> names = new ArrayList<String>();
		names.add(b.getHumanPlayer().getName());
		for(int i = 0; i < b.getComputerPlayers().size(); i++) {
			names.add(b.getComputerPlayer(i).getName());
		}
		for(String n : names) {
			personCombo.addItem(n);
		}
		return personCombo;
	}

	public JComboBox createNewWeaponComboBox(Board b) {
		JComboBox weaponCombo = new JComboBox();
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<String> weapons = new ArrayList<String>();
		cards = b.getCards();
		for(Card c : cards) {
			if(c.getCardType() == CardType.WEAPON) {
				weapons.add(c.getName());
			}
		}
		for(String w : weapons) {
			weaponCombo.addItem(w);
		}
		return weaponCombo;
	}
	
	public JComboBox createNewRoomComboBox(Board b) {
		JComboBox roomCombo = new JComboBox();
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<String> rooms = new ArrayList<String>();
		cards = b.getCards();
		for(Card c : cards) {
			if(c.getCardType() == CardType.ROOM) {
				rooms.add(c.getName());
			}
		}
		for(String r : rooms) {
			roomCombo.addItem(r);
		}
		return roomCombo;
	}
}

