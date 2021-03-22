import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This class makes the game interface using GUI.
 * 
 * @author coco
 * @version 1.0
 */
public class View {

	private JFrame frame;
	private JPanel[] panels;

	private JLabel label;
	
	private JButton[] buttons;
	
	private JButton submit;
	
	private JMenuBar menuBar;
	private JMenu controlMenu;
	private JMenu helpMenu;
	
	private JMenuItem control;
	private JMenuItem help;
	
	private JTextField text;
	
	/**
	 * This is the constructor of the view class.
	 */
	public View() {
		setFrame();
	}
	
	/**
	 * This method sets the frame of view.
	 * This method sets a game window by GUI components.
	 */
	public void setFrame() {

		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panels = new JPanel[3];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel();
		}
		
		label = new JLabel();
		label.setText("Enter your player name...");
		panels[0].add(label);
		
		buttons = new JButton[9];
		for(int i =0;i<9;i++) {
			buttons[i]=new JButton();
		}
		
		
		panels[1].setLayout(new GridLayout(3,3));
		panels[1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		for(int i=0; i<9; i++) {
			buttons[i].setEnabled(false);
			panels[1].add(buttons[i]);
		}
		
		text = new JTextField(10);
		submit = new JButton("Submit");
		panels[2].add(text);
		panels[2].add(submit);
		
		menuBar = new JMenuBar();
		
		controlMenu = new JMenu("Control");
		helpMenu = new JMenu("Help");
		menuBar.add(controlMenu);
		menuBar.add(helpMenu);
		
		control = new JMenuItem("Exit");
		control.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		controlMenu.add(control);
		
		help = new JMenuItem("Instruction");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
	        	JFrame helpFrame = new JFrame("Message");
	        	JOptionPane.showMessageDialog(helpFrame, "Some information about the game:\nCriteria for a valid move:\n-The move is nt occupied by any mark.\n-The move is made in the player's turn.\n-The move is made within the 3 x 3 board.\nThe game would continue and switch among the opposite player until it reaches either one of the following conditions:\n-Player 1 wins.\n-Player 2 wins.\n-Draw.");
	        }
		});
		helpMenu.add(help);
		
		
		frame.setSize(300, 300);
		frame.setVisible(true);

		frame.add(panels[0],BorderLayout.NORTH);
		frame.add(panels[1],BorderLayout.CENTER);
		frame.add(panels[2],BorderLayout.SOUTH);
		
		frame.setJMenuBar(menuBar);
	}
	
	/**
	 * This method set the label content.
	 * 
	 * @param string the content to be shown on the label.
	 */
	public void setLabel(String string) {
		label.setText(string);
	}
	
	/**
	 * This method returns the "submit" button.
	 * 
	 * @return submit the JButton needed.
	 */
	public JButton getSubmitButton() {
		return submit;
	}
	
	/**
	 * This method returns the button array which consist the board.
	 * 
	 * @return buttons the JButton array needed.
	 */
	public JButton[] getButtons() {
		return buttons;
	}
	
	/**
	 * This method returns the text field.
	 * 
	 * @return text the JTextField needed.
	 */
	public JTextField getTextField() {
		return text;
	}

	/**
	 * This method sets the title of the frame.
	 * 
	 * @param string the content to be set as the frame title.
	 */
	public void setFrameTitle(String string) {
		frame.setTitle(string);
	}
	
	/**
	 * This method sets moves on the board.
	 * 
	 * @param loc the location of the move on the board.
	 * @param mark the mark of certain player.
	 */
	public void setButtons(int loc, String mark) {
		buttons[loc].setFont(buttons[loc].getFont().deriveFont(34.0f));
		buttons[loc].setAlignmentX(Component.CENTER_ALIGNMENT);
		if(mark=="X") {
			buttons[loc].setForeground(Color.GREEN);
		}
		else if(mark=="O") {
			buttons[loc].setForeground(Color.RED);
		}
		buttons[loc].setText(mark);
	}
	
	/**
	 * This method shows message when the game ends.
	 * 
	 * @param string the message to be shown.
	 */
	public void setMessage(String string) {
		JFrame DialogFrame = new JFrame("Message");
    	JOptionPane.showMessageDialog(DialogFrame, string);
	}


}
