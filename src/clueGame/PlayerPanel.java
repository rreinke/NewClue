package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerPanel extends JPanel{
	JLabel myCards;
	
	JTextField peopleField;
	JTextField roomField;
	JTextField weaponField;
	
	JPanel pPanel;
	JPanel rPanel;
	JPanel wPanel;
	
	public PlayerPanel() {
		myCards = new JLabel("My Cards");
		peopleField = new JTextField(30);
		roomField = new JTextField(30);
		weaponField = new JTextField(30);
		
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
	
	
	// TESTING
	public static void main(String[] args) {
		JFrame  frame  = new JFrame("TEST");
		
		frame.add(new PlayerPanel(), BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setVisible(true);
	}

}
