package clueGame;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerPanel extends JPanel{
	JLabel myCards;
	JTextField peopleField, roomField, weaponField;
	JPanel pPanel, rPanel, wPanel;
	
	public PlayerPanel() {
		myCards = new JLabel("My Cards");
		peopleField = new JTextField(10);
		roomField = new JTextField(10);
		weaponField = new JTextField(5);
		
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
