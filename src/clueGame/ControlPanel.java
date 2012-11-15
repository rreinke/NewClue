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
import javax.swing.JTextArea;
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

import clueGame.Card.CardType;

public class ControlPanel extends JPanel{
	JLabel whos, die;	
	JTextField turnField, dieField ;
	JTextArea guessField, respField;

	JButton nxtPlayer, mkAccusation, submitAcc, submitSugg;
	JPanel diePanel, gPanel, gResultPanel, whosPanel;
	JComboBox personAccusation, roomAccusation, weaponAccusation;
	JComboBox roomCombo, weaponCombo, personCombo;
	
	Random rand = new Random();
	int rolling;
	int indx;
	Board b = new Board();
	ArrayList<ComputerPlayer> compP = b.getComputerPlayers();
	int cnt = compP.size();
	BoardCell bCell;
	Set<BoardCell> setCell;
	//Graphics g;
	static boolean humanTurn = false;
	static boolean computerTurn = false;

	public ControlPanel(final Board b) {
		whos = new JLabel("Whose Turn?");
		die = new JLabel("Roll");

		turnField = new JTextField(20);
		respField = new JTextArea();
		dieField = new JTextField("5");
		guessField = new JTextArea(2,1);
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
		gPanel.add(guessField);
		gPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		gResultPanel.add(respField);
		gResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Response To Guess"));

		class nxtPlayerListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {

				if(cnt == compP.size()) {
					computerTurn = false;
					humanTurn(b,b.getHumanPlayer());
					cnt = 0;
				}
				else if (computerTurn) {
					computerTurn(b, b.getComputerPlayer(cnt));
				}
			}
		}

		nxtPlayer.addActionListener(new nxtPlayerListener());
		
		class submitAccListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if(personAccusation.getSelectedItem().toString().equals(b.solution.person.getName()) &&
						roomAccusation.getSelectedItem().toString().equals(b.solution.room.getName()) &&
						weaponAccusation.getSelectedItem().toString().equals(b.solution.weapon.getName())) {
					JOptionPane.showMessageDialog(submitAcc, "YOU WIN!!!!");
				} else {
					JOptionPane.showMessageDialog(submitAcc, "Not quite...");
				}
			}
		}

		//Class extends JPanel for the accusation panel	
		class accusationPanel extends JPanel {
			public accusationPanel() {
				JPanel ap = new JPanel();
				submitAcc = new JButton("Submit");
				submitAcc.addActionListener(new submitAccListener());
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
				ap.add(submitAcc);
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

	public void computerTurn(Board b , ComputerPlayer cp) {
		guessField.setText("-");
		respField.setText("-");

		cnt++;

		// Rolling the die
		rolling = rand.nextInt(6)+1;
		dieField.setText(""+rolling);

		// Putting the name in its field
		turnField.setText(cp.getName());

		// Calculate the current index
		indx = b.calcIndex(cp.getCurrentLocation().row, cp.getCurrentLocation().col);

		// Picking the targets from current location
		b.calcTargets(indx, rolling);
		setCell = b.getTargets(); 
		cp.setCurrentLocation(cp.pickLocation(setCell));

		//Displaying roll to the screen
		dieField.setText(Integer.toString(rolling));



		// In case computer player is in a room...
		if(cp.getCurrentLocation().isRoom()){
			cp.createSuggestion(b.getCards());
			guessField.setText(cp.getSuggestedPerson().getName() +" in "+((RoomCell) cp.getCurrentLocation()).getRoomName()+ " \nwith the " + cp.getSuggestedWeapon().getName());
			
			ArrayList<ComputerPlayer> compP = b.getComputerPlayers();
			HumanPlayer hplayer = b.getHumanPlayer();
			ArrayList<Card> compPCards = new ArrayList<Card>();
			ArrayList<Card> dealtCards = new ArrayList<Card>();
			
			for(Card hc : hplayer.getCards()){
				compPCards.add(hc);
			}
			
			for(ComputerPlayer c : compP) {
				if(!c.equals(cp)){
					for(Card cc: c.getCards())
					compPCards.add(cc);
				}
			}
			
			for(Card c: compPCards){
				if(c.equals(cp.getSuggestedPerson()) || c.equals(cp.getSuggestedRoom()) || c.equals(cp.getSuggestedWeapon())){
					dealtCards.add(c);
				}
			}
			if(dealtCards.isEmpty()) {
				respField.setText("no new clue");
			} else {
				respField.setText(dealtCards.get(0).getName());
			}
		}

		b.repaint();

	}


	public void humanTurn(Board b, HumanPlayer hp) {
		humanTurn = true;

		// Rolling the die
		rolling = rand.nextInt(6)+1;
		dieField.setText(""+rolling);

		// Putting the name in its field
		turnField.setText(hp.getName());

		// Calculate the current index
		indx = b.calcIndex(hp.getCurrentLocation().row, hp.getCurrentLocation().col);

		// Picking the targets from current location
		b.calcTargets(indx, rolling);
		setCell = b.getTargets();
		
		ArrayList<ComputerPlayer> compP = b.getComputerPlayers();
		ArrayList<Card> compPCards = new ArrayList<Card>();
		ArrayList<Card> dealtCards = new ArrayList<Card>();
		
		for(ComputerPlayer c : compP) {
			for(Card cc: c.getCards())
				compPCards.add(cc);
		}
		
		for(Card c: compPCards){
			if(c.getName().equals(b.choosenPerson) 
					|| c.getName().equals(b.choosenRoom) 
					|| c.getName().equals(b.choosenWeapon)){
				dealtCards.add(c);
			}
		}
		if(dealtCards.isEmpty()) {
			respField.setText("no new clue");
		} else {
			respField.setText(dealtCards.get(0).getName());
		}
		
		if(b.submitted) {
			guessField.setText(b.choosenPerson+" in "+b.choosenRoom+ " \nwith the " + b.choosenWeapon);
		}
		b.repaint();

	}


	public JComboBox createNewPersonComboBox(Board b) {
		personCombo = new JComboBox();
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
		weaponCombo = new JComboBox();
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
		roomCombo = new JComboBox();
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

