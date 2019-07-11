package gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
 * Authors: Gabriel, Zak
 * Description: A window that creates itself when GUI button "New Board" is pressed. Allows for a creation of a new board with a input
 * board size and number of agents
 */
@SuppressWarnings("serial")
public class NewBoardWindow extends JFrame {

	private JPanel contentPane;
	private JTextField NewBoardSize;
	private JTextField txtNewswarmsize;
	private JTextField newNumSpecialAgents; //MODIFICATION
	private JCheckBox splitPolarityBox; //MODIFICATION
	private int numCellsOnSide, numAgents;
	private int numSpecialAgents;
	private GUI gui;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public NewBoardWindow(JFrame frame, GUI gui) {
		this.gui = gui;
		
		//System.out.println("test");
		setTitle("New Board Size");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewBoardSize = new JLabel("New Board Size");
		lblNewBoardSize.setBounds(95, 48, 100, 20);
		contentPane.add(lblNewBoardSize);

		JLabel lblNewSwarmSize = new JLabel("New Swarm Size");
		lblNewSwarmSize.setBounds(95, 75, 100, 20);
		contentPane.add(lblNewSwarmSize);
		
		//MODIFICATION
		JLabel lblNewNumSpecialAgents = new JLabel("Special Agents");
		lblNewNumSpecialAgents.setBounds(95, 102, 100, 20);
		contentPane.add(lblNewNumSpecialAgents);
		
		//MODIFICATION
		JLabel lblSplitPolarity = new JLabel("Split Polarity");
		lblSplitPolarity.setBounds(95, 129, 100, 20);
		contentPane.add(lblSplitPolarity);
		
		NewBoardSize = new JTextField(Integer.toString(gui.getBoardSize()));
		NewBoardSize.setBounds(292, 48, 40, 20);
		contentPane.add(NewBoardSize);
		NewBoardSize.setColumns(10);


		txtNewswarmsize = new JTextField(Integer.toString(gui.getAgentCount()));
		txtNewswarmsize.setBounds(292, 75, 40, 20);
		contentPane.add(txtNewswarmsize);
		txtNewswarmsize.setColumns(10);
		
		//MODIFICATION
		newNumSpecialAgents = new JTextField(Integer.toString(gui.getSpecialAgentCount()));
		newNumSpecialAgents.setBounds(292, 102, 40, 20);
		contentPane.add(newNumSpecialAgents);
		newNumSpecialAgents.setColumns(10);
		
		//MODIFICATION #3 update from text box to check box
		splitPolarityBox = new JCheckBox();
		splitPolarityBox.setSelected(false);
		splitPolarityBox.setBounds(292, 129, 40, 20);
		contentPane.add(splitPolarityBox);

		//This button will close the JFrame and set the request Board class to create a new board that will be shown in the Main GUI
		JButton btnMakeNewBoard = new JButton("Make New Board");
		btnMakeNewBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numCellsOnSide = Integer.parseInt(NewBoardSize.getText());
				numAgents = Integer.parseInt(txtNewswarmsize.getText());
				numSpecialAgents = Integer.parseInt(newNumSpecialAgents.getText());
				//splitPolNum = Integer.parseInt(splitPolarity.getText());
				gui.setSplitPolarity(splitPolarityBox.isSelected());
				gui.setBoard(generateBoard());
				dispose();
			}
		});
		btnMakeNewBoard.setBounds(95, 184, 237, 49);
		contentPane.add(btnMakeNewBoard);
	}

	//This will be the code that will make a new board and set the variables in the Primary GUI to the selected ones in this.
	protected Board generateBoard() {
		//I factored out the borderForCentering so that the border is around the Board JPanel itself.
		//This math is the same math that used to be done at the beginning of the Board constructor.
		int spareSpace = GUI.SIZE_BOARD_MAXIMUM%numCellsOnSide;
		int borderForCentering = spareSpace/2;
		int boardSize = GUI.SIZE_BOARD_MAXIMUM-borderForCentering*2;

		Board board = new Board(boardSize, boardSize, numCellsOnSide, numAgents, numSpecialAgents, gui);
		board.setBackground(Color.WHITE);
		board.setBounds(10+borderForCentering, 10+borderForCentering, boardSize, boardSize);
		
		return board;
	}
}
