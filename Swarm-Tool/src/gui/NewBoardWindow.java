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
	private JCheckBox threeColorsBox; //MODIFICATION #3
	private int numCellsOnSide, numAgents;
	public int numSpecialAgents, splitPolNum;
	int totalCells; //MODIFICATION #3
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		//MODIFICATION #3
		JLabel lblThreeColor = new JLabel("Diagonal Line");
		lblThreeColor.setBounds(95, 156, 100, 20);
		contentPane.add(lblThreeColor);
		

		NewBoardSize = new JTextField("20",5);
		NewBoardSize.setBounds(292, 48, 40, 20);
		contentPane.add(NewBoardSize);
		NewBoardSize.setColumns(10);


		txtNewswarmsize = new JTextField("20");
		txtNewswarmsize.setBounds(292, 75, 40, 20);
		contentPane.add(txtNewswarmsize);
		txtNewswarmsize.setColumns(10);
		
		//MODIFICATION
		newNumSpecialAgents = new JTextField("0");
		newNumSpecialAgents.setBounds(292, 102, 40, 20);
		contentPane.add(newNumSpecialAgents);
		newNumSpecialAgents.setColumns(10);
		
		//MODIFICATION #3 update from text box to check box
		splitPolarityBox = new JCheckBox();
		splitPolarityBox.setSelected(false);
		splitPolarityBox.setBounds(292, 129, 40, 20);
		contentPane.add(splitPolarityBox);
					
		//MODIFICATION #3 
		threeColorsBox = new JCheckBox();
		threeColorsBox.setSelected(false);
		threeColorsBox.setBounds(292, 156, 40, 20);
		contentPane.add(threeColorsBox);


		//This button will close the JFrame and set the request Board class to create a new board that will be shown in the Main GUI
		JButton btnMakeNewBoard = new JButton("Make New Board");
		btnMakeNewBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numCellsOnSide = Integer.parseInt(NewBoardSize.getText());
				setTotalNumOfCells();
				numAgents = Integer.parseInt(txtNewswarmsize.getText());
				numSpecialAgents = Integer.parseInt(newNumSpecialAgents.getText());
				//splitPolNum = Integer.parseInt(splitPolarity.getText());
				gui.setLblBoardSizeInt(numCellsOnSide);
				gui.setLblSwarmSizeInt(numAgents);
				gui.setNumOfSpecialAgents(numSpecialAgents);
				gui.setSplitPolarity(splitPolarityBox.isSelected());
				gui.setDiagonalLineStart(threeColorsBox.isSelected());
				MakeNewBoard(frame);
				dispose();
			}
		});
		btnMakeNewBoard.setBounds(95, 184, 237, 49);
		contentPane.add(btnMakeNewBoard);
	}

	//This will be the code that will make a new board and set the variables in the Primary GUI to the selected ones in this.
	protected void MakeNewBoard(JFrame frame) {
		if(gui.board != null)
		{
			frame.remove(gui.board);
		}
		//		GUI.board.getGraphics().setColor(Color.WHITE);
		//		GUI.board.getGraphics().drawRect(-5, -5, 810, 810);
		//I factored out the borderForCentering so that the border is around the Board JPanel itself.
		//This math is the same math that used to be done at the beginning of the Board constructor.
		int spareSpace = GUI.MAXBOARDSIZE%numCellsOnSide;
		int borderForCentering = spareSpace/2;
		int boardSize = GUI.MAXBOARDSIZE-borderForCentering*2;

		boolean whetherBoardWraps = gui.wrap;
		Board board = new Board(boardSize,boardSize,numCellsOnSide,numAgents, whetherBoardWraps, null, gui);
		board.setBackground(Color.WHITE);
		board.setBounds(10+borderForCentering, 10+borderForCentering, boardSize, boardSize);
		//displayPanel.add();
		frame.getContentPane().add(board);
		gui.board = board;
		//This section is to avoid bugs in the GUI.
		board.oldPolarity1 = gui.getPolarity1();
		board.oldPolarity2 = gui.getPolarity2();
		board.updateGoalStrategy(gui.goalStrategy);
		board.setAgentRate(gui.agentSliderRate);

	}
	
	public void setTotalNumOfCells() {
		totalCells = numCellsOnSide*numCellsOnSide;
	}
	
	public int getTotalNumOfCells() {
		return totalCells;
	}

}
