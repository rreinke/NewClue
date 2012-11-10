package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlPanel extends JPanel{

		JLabel whos;
		JLabel die;
		JLabel guess;
		JLabel response;
		
		JTextField turnField;
		JTextField respField;
		JTextField dieField;
		JTextField guessField;
		
		JButton nxtPlayer;
		JButton mkAccusation;
		
		JPanel diePanel;
		JPanel gPanel;
		JPanel gResultPanel;
		JPanel whosPanel;
		
		public ControlPanel() {
			
			whos = new JLabel("Whose Turn?");
			die = new JLabel("Die");
			guess = new JLabel("Guess");
			response = new JLabel("Response");
			
			turnField = new JTextField(20);
			respField = new JTextField("Response");
			dieField = new JTextField("Die #");
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
	
			
			setLayout(new GridLayout(2,3));
			add(whosPanel);
			add(nxtPlayer);
			add(mkAccusation);
			add(diePanel);
			add(gPanel);
			add(gResultPanel);

		}
		
		
		// TESTING
		public static void main(String[] args) {
			JFrame  frame  = new JFrame("TEST");
			
			frame.add(new ControlPanel(), BorderLayout.SOUTH);
			frame.add(new PlayerPanel(), BorderLayout.EAST);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800,800);
			frame.setVisible(true);
		}

}
