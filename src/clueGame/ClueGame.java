package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	DetectiveDialog dd = null;
	Board b = new Board();

	public ClueGame() {
		setTitle("Clue");
		setSize(720,705);
		add(b, BorderLayout.CENTER);
		add(new PlayerPanel(b.getHumanPlayer()), BorderLayout.EAST);
		add(new ControlPanel(), BorderLayout.SOUTH);
		//Add a file menu with two options
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		menu.add(createFileMenu());
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileItem("Detective Notes"));
		menu.add(createFileItem("Exit"));
		return menu;
	}

	private JMenuItem createFileItem(String name) {
		JMenuItem newItem = new JMenuItem(name);
		if(name.equals("Exit"))
		{
			newItem.addActionListener(new ExitItemListener());
		} else if (name.equals("Detective Notes"))
		{
			newItem.addActionListener(new NotesItemListener());
		}

		return newItem;

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
			if (dd == null)
				dd = new DetectiveDialog();
			dd.setVisible(true);
		}
	}


	public static void main(String [] args) {
		ClueGame cg = new ClueGame();
		String title = "Welcome to Clue!";
		String message = "You are " + cg.b.getHumanPlayer().getName() + ", press Next Player to begin play";
		
		cg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cg.setVisible(true);
		JOptionPane.showMessageDialog(cg, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
