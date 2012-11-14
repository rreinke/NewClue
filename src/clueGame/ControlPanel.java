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
	
	Random rand = new Random();
	int rolling;
	int indx;
	Board b = new Board();
	HumanPlayer humanP = b.getHumanPlayer();
	ArrayList<ComputerPlayer> compP = b.getComputerPlayers();
	int cnt = compP.size();
	BoardCell bCell;
	Set<BoardCell> setCell;
	Graphics g;
	boolean hTurn;
	
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
					humanTurn();
					cnt = 0;
				}
				else {
					computerTurn();
				}
				//roll the dice and display the number -- DONE
				//display whose turn it is -- DONE
				//if computerPlayer turn call computerTurn()
				//if humanPlayer turn call humanTurn()
				//Do not go on to the next player if the it the target has not been selected
			}
		}

		nxtPlayer.addActionListener(new nxtPlayerListener());
		
		class makeAccusationListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//add logic to the human player making an accusation
			}
			
		}

		setLayout(new GridLayout(2,3));
		add(whosPanel);
		add(nxtPlayer);
		add(mkAccusation);
		add(diePanel);
		add(gPanel);
		add(gResultPanel);
	}
	
	public void computerTurn() {
		ComputerPlayer cp = compP.get(cnt);
		cnt++;
		
		// Rolling the die
		rolling = rand.nextInt(6)+1;
		dieField.setText(""+rolling);
		
		// Putting the name in its field
		turnField.setText(cp.getName());
		
		// Calculate the current index
		indx = cp.getCurrentLocation().row 
				 	* b.getNumColumns() + cp.getCurrentLocation().col;
		
		// Picking the targets from current location
		b.calcTargets(indx, rolling);
		setCell = b.getTargets(); 
		
		// Picking randomly target and set it as the current one
		bCell = cp.pickLocation(setCell);
		cp.setCurrentLocation(bCell);
		
		repaint();
		
		// In case we are in a room...
		if(bCell.isRoom()) {
			cp.createSuggestion(b.getCards());
			guessField.setText(cp.getSuggestedPerson().getName() +", "
								+ cp.getSuggestedWeapon().getName());
			respField.setText(((RoomCell)(bCell)).getRoomName());
		}
		
		
	}
	
	public void humanTurn() {
		hTurn = true;
		// Rolling the die
		rolling = rand.nextInt(6)+1;
		dieField.setText(""+rolling);
				
		// Putting the name in its field
		turnField.setText(humanP.getName());
		
		// Calculate the current index
		indx = humanP.getCurrentLocation().row 
				 	* b.getNumColumns() + humanP.getCurrentLocation().col;
				
		// Picking the targets from current location
		b.calcTargets(indx, rolling);
		setCell = b.getTargets();
		
		// Marking the targets
		for(BoardCell cell: setCell) {
			//g.setColor(Color.RED);
			//g.drawRect(cell.col*b.SIDE, cell.row*b.SIDE, b.SIDE, b.SIDE);
		}
		
		
		
	}
	
	public class CellChooser implements MouseListener {
		int x;
		int y;
		@Override
		public void mouseClicked(MouseEvent e) {
			if(hTurn){
				x = e.getX()/b.SIDE;
				y = e.getY()/b.SIDE;
				for(BoardCell bc : setCell) {
					if((x*b.SIDE == bc.col) && (y*b.SIDE) == bc.row) {
						humanP.setCurrentLocation(bc);
						// human turn is end after he choose a target
						hTurn = false;
						// Removing the targets' markers
						for(BoardCell cell: setCell) {
							//g.setColor(Color.BLACK);
							//g.drawRect(cell.col*b.SIDE, cell.row*b.SIDE, b.SIDE, b.SIDE);
						}
						repaint();
						break;
					}
					else {
						JOptionPane.showMessageDialog(b, "Invalid target!");
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	
	//TESTING
	public static void main(String[] args) {
		System.out.println((int)(Math.random()*10%6)+1);
	}
}
