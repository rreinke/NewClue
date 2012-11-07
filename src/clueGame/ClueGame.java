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
	DetectiveDialog dd = null;
	
	public ClueGame() {
		setTitle("Clue");
		setSize(680,685);
		setResizable(false);
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
		cg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cg.setVisible(true);
	}
}
