package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel{
	JLabel whos, die, guess, response;	
	JTextField turnField, respField, dieField, guessField;
	JButton nxtPlayer, mkAccusation;
	JPanel diePanel, gPanel, gResultPanel, whosPanel;

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
				//roll the dice and display the number
				//display whose turn it is
				//if computerPlayer turn call computerTurn()
				//if humanPlayer turn call humanTurn()
				//Do not go on to the next player if the it the target has not been selected
			}
		}

		nxtPlayer.addActionListener(new nxtPlayerListener());
		
		class submitListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//add logic for checking the accusation
			}
		}

		//Class extends JPanel for the accusation panel	
		class accusationPanel extends JPanel {
			public accusationPanel() {
				JPanel ap = new JPanel();
				JButton submit = new JButton("Submit");
				submit.addActionListener(new submitListener());
				JComboBox personAccusation, roomAccusation, weaponAccusation;
				JLabel room, person, weapon;

				room = new JLabel("Room");
				person = new JLabel("Person");
				weapon = new JLabel("Weapon");
				ap.setLayout(new GridLayout(4, 2));

				personAccusation = createNewPersonComboBox();
				roomAccusation = createNewRoomComboBox();
				weaponAccusation = createNewWeaponComboBox();

				ap.add(room);
				ap.add(roomAccusation);
				ap.add(person);
				ap.add(personAccusation);
				ap.add(weapon);
				ap.add(weaponAccusation);	
				ap.add(submit);
				add(ap);
			}

			private JComboBox createNewPersonComboBox() {
				JComboBox personCombo = new JComboBox();
				personCombo.addItem("Rachel");
				personCombo.addItem("Paul");
				personCombo.addItem("Tigger");
				return personCombo;
			}

			private JComboBox createNewRoomComboBox() {
				JComboBox roomCombo = new JComboBox();
				roomCombo.addItem("Kitchen");
				roomCombo.addItem("Dining room");
				return roomCombo;
			}

			private JComboBox createNewWeaponComboBox() {
				JComboBox weaponCombo = new JComboBox();
				weaponCombo.addItem("Spoon");
				weaponCombo.addItem("Spork");
				return weaponCombo;
			}
		}

		//class that extends JFrame for the accusation panel when make accusation is clicked
		class accusationFrame extends JFrame {
			public accusationFrame() {
				JFrame af = new JFrame();
				af.setTitle("Accusation!");
				af.setSize(300, 200);
				af.add(new accusationPanel(), BorderLayout.CENTER);
				af.setVisible(true);
			}
		}

		class makeAccusationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				accusationFrame af = new accusationFrame();
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

	private void computerTurn() {
		//logic for computer's turn
	}

	private void humanTurn() {
		//logic for human's turn
	}
}
