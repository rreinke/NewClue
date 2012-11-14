package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class ControlPanel extends JPanel{
	JLabel whos, die, guess, response;	
	JTextField turnField, respField, dieField, guessField;
	JButton nxtPlayer, mkAccusation, submit;
	JPanel diePanel, gPanel, gResultPanel, whosPanel;
	JComboBox personAccusation, roomAccusation, weaponAccusation;
	
	Random rand = new Random();
	int rolling;
	int indx;
	Board b = new Board();
	HumanPlayer humanP = b.getHumanPlayer();
	ArrayList<ComputerPlayer> compP = b.getComputerPlayers();
	int cnt = compP.size();
	BoardCell bCell;
	Set<BoardCell> setCell;
	//Graphics g;
	static boolean humanTurn = false;
	static boolean computerTurn = false;
	
	public ControlPanel() {
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
				
				if(cnt == compP.size()) {
					computerTurn = false;
					humanTurn(b,b.getHumanPlayer());
					cnt = 0;
				}
				else {
					computerTurn(b, b.getComputerPlayer(cnt));
				}
				//roll the dice and display the number -- DONE
				//display whose turn it is -- DONE
				//if computerPlayer turn call computerTurn()
				//if humanPlayer turn call humanTurn()
				//Do not go on to the next player if the it the target has not been selected
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
	
	public void computerTurn(Board b , ComputerPlayer cp) {
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
		
		// Picking randomly target and set it as the current one
		bCell = cp.pickLocation(setCell);
		cp.setCurrentLocation(bCell);
		
		// In case computer player is in a room...
		if(bCell.isRoom()) {
			cp.createSuggestion(b.getCards());
			guessField.setText(cp.getSuggestedPerson().getName() +", "
								+ cp.getSuggestedWeapon().getName());
			respField.setText(((RoomCell)(bCell)).getRoomName());
		}
		b.repaint();
		
	}
	
	public void humanTurn(Board b, HumanPlayer hp) {
		humanTurn = true;
		
		// Rolling the die
		rolling = rand.nextInt(6)+1;
		dieField.setText(""+rolling);
				
		// Putting the name in its field
		turnField.setText(humanP.getName());
		
		// Calculate the current index
		indx = b.calcIndex(humanP.getCurrentLocation().row, humanP.getCurrentLocation().col);
				
		// Picking the targets from current location
		b.calcTargets(indx, rolling);
		setCell = b.getTargets();
		
		b.repaint();
		
		
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
