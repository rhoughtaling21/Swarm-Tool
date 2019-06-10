package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import java.awt.Frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cells.Cell;
import other.SwarmAgent;
import strategies.AbstractStrategy;
import strategies.AllBlack;
import strategies.CheckerBoard;
import strategies.DiagonalLines;
import strategies.Lines;

/*
 * Authors: Gabriel, Zak
 * Description: Gui created through Auto-generated code. Most of the code is simple creation, with functionality within certain labels and buttons.
 * 
 * Modified By: Morgan Might
 * July 18, 2018
 * 
 * MODIFICATION #10: This version will be modified so that of the three polarities we may choose
 * which to be most dominant (as the yellow has been in previous versions).
 *  */
public class GUI {

	public static final int HEIGHT = 864;
	public static final int WIDTH = 1536;
	public static final int MAXBOARDSIZE = 800;// pixel size of board

	public JFrame frmProjectLegion;// main frame
	private JTextField textField_NumAgents;
	private JTextField textField_NumAgentChanges;
	private JTextField textField_AgentCloseness;
	private JTextField textField_PheromoneStrength;
	private JTextField textField_BoardSize;
	private JTextField textField_TriggerFlip;
	private JTextField textField_TriggerFlipLayer2;

	public static JLabel lblBoardSizeInt = new JLabel(); // updates BoardSize Label
	public static JLabel lblSwarmCountInt = new JLabel(); // updates SwarmCount Label
	public static JLabel lblIntWhiteCells = new JLabel(); // updates InitWhiteCells
	public static JLabel lblIntBlackCells = new JLabel(); // updates InitBlackCells
	public static JLabel lblIntGrayCells = new JLabel(); // updates InitGrayCells	MODIFICATION #3
	public static JLabel lblCurrWhiteCells = new JLabel(); // updates CurrentWhiteCells
	public static JLabel lblCurrBlackCells = new JLabel(); // updates CurrentBlackCells
	public static JLabel lblCurrGrayCells = new JLabel(); // updates CurrentGrayCells	MODIFICATION #3
	public static JLabel lblPolarityOnePercent = new JLabel(); //  updates number of cells with polarity 1  MODIFICATION #4
	public static JLabel lblPolarityTwoPercent = new JLabel(); //  updates number of cells with polarity 2  MODIFICATION #4
	public static JLabel lblPolarityThreePercent = new JLabel(); //  updates number of cells with polarity 3  MODIFICATION #4
	public static JLabel lblPolarityFourPercent = new JLabel(); //  updates number of cells with polarity 4  MODIFICATION #4
	public static JLabel lblBooleanCompare1 = new JLabel(); //  displays boolean (polarity1 < polarity3)  MODIFICATION #4
	public static JLabel lblBooleanCompare2 = new JLabel(); //  displays boolean (polarity2 < polarity3)  MODIFICATION #4
	public static JLabel lblBooleanCompare3 = new JLabel(); //  displays boolean (polarity1+polarity2 < polarity3)  MODIFICATION #4
	public static JLabel lblCompare1 = new JLabel(); // displays the current constraints MODIFICATION #10
	public static JLabel lblCompare2 = new JLabel(); // displays the current constraints MODIFICATION #10
	public static JLabel lblCompare3 = new JLabel(); // displays the current constraints MODIFICATION #10
	public static JLabel lblStepDisplay = new JLabel(); //displays the number of steps that have occured

	
	public static int layer2Draw = 1;// which cell array in board to display
	public static Board board;// board to be drawn
	private boolean timerStarted = true;// timer or agent step
	public static Color polarity1 = Color.RED;// color1 of board.cells2
	public static Color polarity2 = Color.BLUE;// color2 of board.cells2
	public static Color polarity3 = Color.YELLOW;
	public static Color polarity4 = Color.WHITE;
	public static int initBoardSize, initAgentCount;
	public static boolean attractOrRepel = true;
	public static Color agentColor = Color.GREEN;
	public static Color specialAgentColor = Color.CYAN; //MODIFICATION color for the "special agent"
	public static boolean whetherAgentsVisible = true;
	public static AbstractStrategy goalStrategy = new CheckerBoard();
	public static String newDominantPolarity = "YELLOW";	//MODIFICATION #10 added 7/18 by Morgan Might 	//Keep track of polarity that should be most dominant
	public static String statementOne = "Red < Yellow :"; //MODIFICATION #10  //These variables will be used to display the constraints
	public static String statementTwo = "Blue < Yellow :"; //MODIFICATION #10 
	public static String statementThree = "R + B > Yellow :"; //MODIFICATION #10 
	public static int agentSliderRate;
	public static boolean wrap = false;
	public int toggleCount = 0; //MODIFICATION:  used in implementing the View Agents Button
	public static int numSpecialAgents; //MODIFICATION: how many agents should be a separate color
	public static boolean splitPolarity; //MODIFICATION: if true set the board to be "stuck"
	public static boolean diagonalLineStart; //MODIFICATION #9 
	
	public boolean threePol; //MODIFICATION #3   needs fixed
	public int percentToFlip; //MODIFICATION #2 stores the number of random cells to flip
	public static int polOneCount = 0; //MODIFICATION #4  stores the number of cells that have polarity 1
	public static int polTwoCount = 0; //MODIFICATION #4  stores the number of cells that have polarity 2
	public static int polThreeCount = 0; //MODIFICATION #4  stores the number of cells that have polarity 3
	public static int polFourCount = 0; //MODIFICATION #4  stores the number of cells that have polarity 4
	
	public static boolean togglePolarity = false; //MODIFICATION #5 determines if the agents goal is a single polarity or three balanced polarities

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmProjectLegion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static Color getPolarity1() {
		return polarity1;
	}

	public static void setPolarity1(Color polarity1) {
		GUI.polarity1 = polarity1;
	}

	public static Color getPolarity2() {
		return polarity2;
	}

	public static void setPolarity2(Color polarity2) {
		GUI.polarity2 = polarity2;
	}

	public static Color getPolarity3() {
		return polarity3;
	}

	public static void setPolarity3(Color polarity3) {
		GUI.polarity3 = polarity3;
	}

	public static Color getPolarity4() {
		return polarity4;
	}

	public static void setPolarity4(Color polarity4) {
		GUI.polarity4 = polarity4;
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProjectLegion = new JFrame();
		frmProjectLegion.setTitle("Project Legion");
		frmProjectLegion.setBounds(100, 100, WIDTH, HEIGHT);
		frmProjectLegion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Makes the top menu bar that has file and edit
		JMenuBar menuBar = new JMenuBar();
		frmProjectLegion.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");  //Does not work as a function
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open");  //Does not work as a function
		mnFile.add(mntmOpen);

		JMenuItem mntmClose = new JMenuItem("Close");  //Does not work as a function
		mnFile.add(mntmClose);

		JMenu mnEdit = new JMenu("Edit");  //Does not work as a function
		menuBar.add(mnEdit);

		JMenuItem mntmNew = new JMenuItem("New");  //Does not work as a function
		mnEdit.add(mntmNew);
		frmProjectLegion.getContentPane().setLayout(null);

		// ************************************************************ This makes the
		// 800 by 800 JPanel that will be where the board goes every time it is painted.
		JPanel boardInGUI = new JPanel();
		JFrame frame = new JFrame();
		boardInGUI.setBackground(Color.WHITE);
		boardInGUI.setBounds(10, (HEIGHT - MAXBOARDSIZE) / 8, MAXBOARDSIZE, MAXBOARDSIZE);
		// System.out.print((HEIGHT-BOARDSIZE)/8);
		frame.getContentPane().add(boardInGUI);

		// This is where the tabs for the layer options go.
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(818, 10, 700, 600);
		// used to change whether board.cells or board.cells2 is shown in board when the
		// tab selected is changed
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				layer2Draw = index + 1;
			}
		};
		tabbedPane.addChangeListener(changeListener);
		frmProjectLegion.getContentPane().add(tabbedPane);

		// ************************************************************ TAB 1 **************************************************************
		// ************************************************************ TAB 1 **************************************************************
		JPanel tabLayer1 = new JPanel();
		tabLayer1.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Layer 1", null, tabLayer1, null);
		tabLayer1.setLayout(null);

		// ************************************************************ Change Size of
		// the Board Button
		JButton btnChangeBoardSize = new JButton("Update Size");   //DEOS NOT WORK YET
		btnChangeBoardSize.setForeground(Color.LIGHT_GRAY);
		btnChangeBoardSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Button gains actions here
				// board.size = Integer.parseInt(textField_BoardSize.getText());
			}
		});
		btnChangeBoardSize.setBounds(286, 157, 125, 35);
		tabLayer1.add(btnChangeBoardSize);

		// ************************************************************ Labels
		// displaying information
		JLabel lblNumStartingWhite = new JLabel("Initial White Cells:");
		lblNumStartingWhite.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumStartingWhite.setBounds(10, 11, 125, 35);
		tabLayer1.add(lblNumStartingWhite);

		lblIntWhiteCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntWhiteCells.setBounds(145, 11, 125, 35);
		tabLayer1.add(lblIntWhiteCells);
		
		//MODIFICATION #3 Gray Cells
		//
		//Added 5/30 Morgan Might
		//Displays the original amount of gray cells
		JLabel lblNumStartingGray = new JLabel("Initial Gray Cells:");
		lblNumStartingGray.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumStartingGray.setBounds(10, 44, 125, 35);
		tabLayer1.add(lblNumStartingGray);

		lblIntGrayCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntGrayCells.setBounds(145, 44, 125, 35);
		tabLayer1.add(lblIntGrayCells);

		JLabel lblNumStartingBlack = new JLabel("Initial Black Cells:");
		lblNumStartingBlack.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumStartingBlack.setBounds(10, 79, 125, 35);
		tabLayer1.add(lblNumStartingBlack);

		lblIntBlackCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntBlackCells.setBounds(145, 79, 125, 35);
		tabLayer1.add(lblIntBlackCells);

		JLabel lblBoardSizeLayer = new JLabel("Board Size:");
		lblBoardSizeLayer.setForeground(Color.lightGray);
		lblBoardSizeLayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoardSizeLayer.setBounds(10, 157, 125, 35);
		tabLayer1.add(lblBoardSizeLayer);
		
		//MODIFICATION #2: Flip random cells that the user has specified the amount (Percent)
		//
		//Added 5/23 Morgan Might
		JLabel lblTriggerFlip= new JLabel("Percent to Flip:");
		lblTriggerFlip.setHorizontalAlignment(SwingConstants.CENTER);
		lblTriggerFlip.setBounds(10, 225, 125, 35);
		tabLayer1.add(lblTriggerFlip);
			
		//MODIFICATION #2  stores the % of random cells to flip, the user may change the number
		textField_TriggerFlip = new JTextField();
		textField_TriggerFlip.setText("20");
		textField_TriggerFlip.setBounds(177, 225, 50, 20);
		tabLayer1.add(textField_TriggerFlip);
		textField_TriggerFlip.setColumns(10);
		
		//MODIFICATION #2  button will flip the percent of cells based on number in text field
		JButton btnTriggerFlipCells = new JButton("Flip Cells");
		btnTriggerFlipCells.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//set number in the text field to an int variable
				percentToFlip = Integer.parseInt(textField_TriggerFlip.getText());
				//Call method that will cause some cells to flip
				board.flipCells(percentToFlip);
			}
		});
		btnTriggerFlipCells.setBounds(286, 225, 125, 35);
		tabLayer1.add(btnTriggerFlipCells);

		JLabel lblNumCurrentWhite = new JLabel("Current White Cells:");
		lblNumCurrentWhite.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumCurrentWhite.setBounds(10, 300, 125, 35);
		tabLayer1.add(lblNumCurrentWhite);

		lblCurrWhiteCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrWhiteCells.setBounds(145, 300, 125, 35);
		tabLayer1.add(lblCurrWhiteCells);
		
		//MODIFICATION #3 current Gray Cells
		//
		//Added 5/30 by Morgan Might
		//Label to display the updated amount of cells that are currently gray
		JLabel lblNumCurrentGray = new JLabel("Current Gray Cells:");
		lblNumCurrentGray.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumCurrentGray.setBounds(10, 334, 125, 35);
		tabLayer1.add(lblNumCurrentGray);

		lblCurrGrayCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrGrayCells.setBounds(145, 334, 125, 35);
		tabLayer1.add(lblCurrGrayCells);

		JLabel lblNumCurrentBlack = new JLabel("Current Black Cells:");
		lblNumCurrentBlack.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumCurrentBlack.setBounds(10, 368, 125, 35);
		tabLayer1.add(lblNumCurrentBlack);

		lblCurrBlackCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrBlackCells.setBounds(145, 368, 125, 35);
		tabLayer1.add(lblCurrBlackCells);

		// ************************************************************ Text field that
		// will trigger when button pushed, this is the value that will be the new board
		// size
		textField_BoardSize = new JTextField();   //Does NOT work
		textField_BoardSize.setText("20");
		textField_BoardSize.setBounds(177, 164, 50, 20);
		tabLayer1.add(textField_BoardSize);
		textField_BoardSize.setColumns(10);

		// ************************************************************ TAB 2 **************************************************************
		// ************************************************************ TAB 2 **************************************************************
		JPanel tabLayer2 = new JPanel();
		tabLayer2.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Layer 2", null, tabLayer2, null);
		tabLayer2.setLayout(null);

		// ************************************************************ Primary color
		// for polarity choice comboBox where you can change it
		JLabel lblPrimaryPolarityColor = new JLabel("Primary Polarity Color");
		lblPrimaryPolarityColor.setBounds(25, 25, 125, 14);
		tabLayer2.add(lblPrimaryPolarityColor);
		// update the first color of cells in board.cells2
		JComboBox comboPrimary = new JComboBox();
		comboPrimary.setModel(new DefaultComboBoxModel(new String[] { "RED", "BLUE", "GREEN", "CYAN", "YELLOW" }));
		Color[] primaryColorList = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW };
		comboPrimary.setBounds(25, 50, 125, 22);
		comboPrimary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JComboBox src = (JComboBox) e.getSource();
				if (polarity2 == primaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(primaryColorList).indexOf(polarity1);
					comboPrimary.setSelectedIndex(temp);
				} else {
					polarity1 = primaryColorList[src.getSelectedIndex()];
					board.updateNewPolarityColor1(polarity1);
				}
				if (!timerStarted) {
					board.repaint();
				}

			}
		});
		tabLayer2.add(comboPrimary);

		// ************************************************************ Secondary color
		// for polarity choice comboBox where you can change it
		JLabel lblSecondaryPolarityColor = new JLabel("Secondary Polarity Color");
		lblSecondaryPolarityColor.setBounds(238, 25, 150, 14);
		tabLayer2.add(lblSecondaryPolarityColor);
		// update the second color of cells in board.cells2
		JComboBox comboSecondary = new JComboBox();
		comboSecondary.setModel(new DefaultComboBoxModel(new String[] { "BLUE", "RED", "GREEN", "CYAN" }));
		Color[] secondaryColorList = new Color[] { Color.BLUE, Color.RED, Color.GREEN, Color.CYAN };
		comboSecondary.setBounds(238, 50, 125, 22);
		comboSecondary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox src = (JComboBox) e.getSource();
				if (polarity1 == secondaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(secondaryColorList).indexOf(polarity2);
					comboSecondary.setSelectedIndex(temp);
				} else {
					polarity2 = secondaryColorList[src.getSelectedIndex()];
					board.updateNewPolarityColor2(polarity2);
				}
				if (!timerStarted) {
					board.repaint();
				}

			}
		});
		tabLayer2.add(comboSecondary);

		// ************************************************************ Tertiary color
		// comboBox if we want it.
		JLabel lblTertiaryPolarityColor = new JLabel("Tertiary Polarity Color");
		lblTertiaryPolarityColor.setBounds(25, 100, 125, 14);
		tabLayer2.add(lblTertiaryPolarityColor);

		JComboBox comboTertiary = new JComboBox();
		comboTertiary.setModel(
				new DefaultComboBoxModel(new String[] { "YELLOW", "BLUE", "RED", "GREEN", "CYAN", "BLACK", "WHITE" }));
		Color[] tertiaryColorList = new Color[] { Color.YELLOW, Color.BLUE, Color.RED, Color.GREEN, Color.CYAN,
				Color.BLACK, Color.WHITE };
		comboTertiary.setBounds(25, 125, 125, 22);
		comboTertiary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox src = (JComboBox) e.getSource();
				if (polarity1 == tertiaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(tertiaryColorList).indexOf(polarity3);
					comboTertiary.setSelectedIndex(temp);
				} else if (polarity2 == tertiaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(tertiaryColorList).indexOf(polarity3);
					comboTertiary.setSelectedIndex(temp);
				} else if (polarity4 == tertiaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(tertiaryColorList).indexOf(polarity3);
					comboTertiary.setSelectedIndex(temp);
				} else {
					polarity3 = tertiaryColorList[src.getSelectedIndex()];
					board.updateNewPolarityColor3(polarity3);
				}
				if (!timerStarted) {
					board.repaint();
				}

			}
		});
		tabLayer2.add(comboTertiary);

		JLabel lblQuaternaryPolarityColor = new JLabel("Quaternary Polarity Color");
		lblQuaternaryPolarityColor.setBounds(238, 100, 150, 22);
		tabLayer2.add(lblQuaternaryPolarityColor);

		JComboBox comboQuaternary = new JComboBox();
		comboQuaternary.setModel(
				new DefaultComboBoxModel(new String[] { "WHITE", "BLUE", "RED", "GREEN", "CYAN", "BLACK", "YELLOW" }));
		Color[] quaternaryColorList = new Color[] { Color.WHITE, Color.BLUE, Color.RED, Color.GREEN, Color.CYAN,
				Color.BLACK, Color.YELLOW };
		comboQuaternary.setBounds(238, 125, 125, 22);
		comboQuaternary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox src = (JComboBox) e.getSource();
				if (polarity1 == tertiaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(tertiaryColorList).indexOf(polarity4);
					comboQuaternary.setSelectedIndex(temp);
				} else if (polarity2 == tertiaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(tertiaryColorList).indexOf(polarity4);
					comboQuaternary.setSelectedIndex(temp);
				} else if (polarity3 == tertiaryColorList[src.getSelectedIndex()]) {
					int temp = Arrays.asList(tertiaryColorList).indexOf(polarity4);
					comboQuaternary.setSelectedIndex(temp);
				} else {
					polarity4 = quaternaryColorList[src.getSelectedIndex()];
					board.updateNewPolarityColor4(polarity4);
				}
				if (!timerStarted) {
					board.repaint();
				}

			}
		});
		tabLayer2.add(comboQuaternary);

		// ************************************************************ Sets what the
		// polarity ratios should be for the two colors.
		JLabel lblReginalStability = new JLabel("Reginal Stability");  //Does not work
		lblReginalStability.setForeground(Color.LIGHT_GRAY);
		lblReginalStability.setBounds(25, 175, 125, 14);
		tabLayer2.add(lblReginalStability);

		JComboBox reginalStablility = new JComboBox();  //Does not work
		reginalStablility.setModel(
				new DefaultComboBoxModel(new String[] { "50/50", "60/40", "70/30", "80/20", "90/10", "100/0" }));
		reginalStablility.setBounds(25, 200, 125, 22);
		tabLayer2.add(reginalStablility);

		// ************************************************************ Updates the
		// polarity to what is entered on the radio buttons
		JLabel lblPolarity = new JLabel("Goal");
		lblPolarity.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolarity.setBounds(238, 175, 150, 14);
		tabLayer2.add(lblPolarity);

		JComboBox comboGoalStrategy = new JComboBox();
		comboGoalStrategy.setModel(new DefaultComboBoxModel(new String[] { "CheckerBoard", "Lines", "All Black", "Diagonal Lines" }));
		AllBlack allBlack = new AllBlack();
		CheckerBoard checkerBoard = new CheckerBoard();
		Lines lines = new Lines();
		DiagonalLines diagonalLines = new DiagonalLines();
		AbstractStrategy[] goalStrategyList = new AbstractStrategy[] { checkerBoard, lines, allBlack, diagonalLines };
		//MODIFICATION: start with Diagonal Lines as the Goal
		//Added 7/17 by Morgan Might
		if(diagonalLineStart == true) {
			goalStrategy = goalStrategyList[3]; //Still DOES NOT work
		}
		comboGoalStrategy.setBounds(238, 200, 150, 22);
		comboGoalStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox src = (JComboBox) e.getSource();
				goalStrategy = goalStrategyList[src.getSelectedIndex()];
				board.updateGoalStrategy(goalStrategy);
				whetherAgentsVisible = true;
			}
		});
		tabLayer2.add(comboGoalStrategy);
		
		//MODIFICATION #5:
		//Added 6/12 by Morgan Might
		//
		//Label for the apply rules toggle button
		JLabel lblRulesApply = new JLabel("Apply Rules");
		lblRulesApply.setHorizontalAlignment(SwingConstants.CENTER);
		lblRulesApply.setBounds(450, 175, 150, 14);
		tabLayer2.add(lblRulesApply);
				
		//MODIFICATION #5:
		//Added 6/12 by Morgan Might
		//
		//This button will determine if the agents should follow rules to get a single polarity
		//or try to balance the 3 different polarities.
		JToggleButton tglbtnRulesApply = new JToggleButton("Single Polarity");
		tglbtnRulesApply.setSelected(true);
		tglbtnRulesApply.setBounds(450, 200, 150, 22);
		tglbtnRulesApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				togglePolarity = !togglePolarity;
			}
		});
		tabLayer2.add(tglbtnRulesApply);
		
		
//		//MODIFICATION #2 (Layer 2) label
//		//
//		//Added 5/23 by Morgan Might
//		//Same as above, this button will allow the use to flip cells randomly (with a view of the polarity layer)
//		JLabel lblTriggerFlipLayer2= new JLabel("Percent to Flip:");
//		lblTriggerFlipLayer2.setHorizontalAlignment(SwingConstants.CENTER);
//		lblTriggerFlipLayer2.setBounds(10, 270, 125, 35);
//		tabLayer2.add(lblTriggerFlipLayer2);
//							
//		//MODIFICATION #2 (Layer 2) stores the % of random cells to flip
//		textField_TriggerFlipLayer2 = new JTextField();
//		textField_TriggerFlipLayer2.setText("20");
//		textField_TriggerFlipLayer2.setBounds(177, 277, 50, 20);
//		tabLayer2.add(textField_TriggerFlipLayer2);
//		textField_TriggerFlipLayer2.setColumns(10);
//			
//		//MODIFICATION #2 (Layer 2) button triggers the flip
//		JButton btnTriggerFlipCellsLayer2 = new JButton("Flip Cells");
//		btnTriggerFlipCellsLayer2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				//set number in the text field to an int variable
//				percentToFlip = Integer.parseInt(textField_TriggerFlipLayer2.getText());
//				//Call method that will cause some cells to flip
//				board.flipCells(percentToFlip);
//			}
//		});
//		btnTriggerFlipCellsLayer2.setBounds(286, 270, 125, 35);
//		tabLayer2.add(btnTriggerFlipCellsLayer2);
		
		//MODIFICATION #10
		//Added 7/17 by Morgan Might
		//Dominant Polarity Label: Choose which polarity should be the majority
		JLabel lblDominantPolairy = new JLabel("Dominant Polarity :");
		lblDominantPolairy.setHorizontalAlignment(SwingConstants.CENTER);
		lblDominantPolairy.setBounds(25, 325, 125, 14);
		tabLayer2.add(lblDominantPolairy);

		//Action added 7/18 by Morgan Might
		JComboBox dominantPolarity = new JComboBox();  //Does not work
		dominantPolarity.setModel(new DefaultComboBoxModel(new String[] { "YELLOW", "BLUE", "RED" }));
		String[] dominantPolarityList = new String[] { "YELLOW", "BLUE", "RED" };
		dominantPolarity.setBounds(25, 350, 125, 22);
		dominantPolarity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox src = (JComboBox) e.getSource();
				newDominantPolarity = dominantPolarityList[src.getSelectedIndex()];
				updateDominantPolarity(newDominantPolarity);
				
			}
		});
		tabLayer2.add(dominantPolarity);
		
		//MODIFICATION #10:
		//Added 7/23 by Morgan Might
		//Display how many steps have occured
		JLabel lblStepCount = new JLabel("Step :");
		lblStepCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblStepCount.setBounds(300, 300, 125, 35);
		tabLayer2.add(lblStepCount);
		
		lblStepDisplay = new JLabel();
		lblStepDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		lblStepDisplay.setText("0");
		lblStepDisplay.setBounds(400, 310, 50, 20);
		tabLayer2.add(lblStepDisplay);
		
				
		
		//MODIFICATION #4:
		//Added 6/4 by Morgan Might
		//These JLabels will display the percentage of each polarity
		//
		//Polarity 1 label
		JLabel lblPolarityOne = new JLabel("Red :");
		lblPolarityOne.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolarityOne.setBounds(10, 400, 125, 35);
		tabLayer2.add(lblPolarityOne);
		
		lblPolarityOnePercent = new JLabel();
		lblPolarityOnePercent.setText(getPolOneCount() + "%");
		lblPolarityOnePercent.setBounds(137, 405, 50, 20);
		tabLayer2.add(lblPolarityOnePercent);
		
		//Polarity 2 label
		JLabel lblPolarityTwo = new JLabel("Blue :");
		lblPolarityTwo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolarityTwo.setBounds(10, 420, 125, 35);
		tabLayer2.add(lblPolarityTwo);
				
		lblPolarityTwoPercent = new JLabel();
		lblPolarityTwoPercent.setText(getPolOneCount() + "%");
		lblPolarityTwoPercent.setBounds(137, 425, 50, 20);
		tabLayer2.add(lblPolarityTwoPercent);
		
		//Polarity 3 label
		JLabel lblPolarityThree = new JLabel("Yellow :");
		lblPolarityThree.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolarityThree.setBounds(10, 440, 125, 35);
		tabLayer2.add(lblPolarityThree);
				
		lblPolarityThreePercent = new JLabel();
		lblPolarityThreePercent.setText(getPolOneCount() + "%");
		lblPolarityThreePercent.setBounds(137, 445, 50, 20);
		tabLayer2.add(lblPolarityThreePercent);
		
		//Polarity 4 label
//		JLabel lblPolarityFour = new JLabel("Polarity 4 :");
//		lblPolarityFour.setHorizontalAlignment(SwingConstants.CENTER);
//		lblPolarityFour.setBounds(10, 460, 125, 35);
//		tabLayer2.add(lblPolarityFour);
//				
//		lblPolarityFourPercent = new JLabel();
//		lblPolarityFourPercent.setText(getPolOneCount() + "%");
//		lblPolarityFourPercent.setBounds(137, 460, 50, 20);
//		tabLayer2.add(lblPolarityFourPercent);
		
		//MODIFICATION #4:
		//Added 6/4 by Morgan Might
		//Display the comparisons that are the current rules dealing with polarity. Display True or False.
		//
		//MODIFICATION #4: Display boolean statements (comparing polarity 1 and 3)
		lblCompare1 = new JLabel();
		lblCompare1.setText(statementOne);
		lblCompare1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompare1.setBounds(300, 400, 125, 35);
		tabLayer2.add(lblCompare1);
				
		lblBooleanCompare1 = new JLabel();
		lblBooleanCompare1.setText(getBooleanCompareOne());
		lblBooleanCompare1.setBounds(420, 405, 50, 20);
		tabLayer2.add(lblBooleanCompare1);
		
		//MODIFICATION #4: Display boolean statements (comparing polarity 2 and 3)
		lblCompare2 = new JLabel();
		lblCompare2.setText(statementTwo);
		lblCompare2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompare2.setBounds(300, 420, 125, 35);
		tabLayer2.add(lblCompare2);
						
		lblBooleanCompare2 = new JLabel();
		lblBooleanCompare2.setText(getBooleanCompareTwo());
		lblBooleanCompare2.setBounds(420, 425, 50, 20);
		tabLayer2.add(lblBooleanCompare2);
		
		//MODIFICATION #4: Display boolean statements (comparing polarity 1+2 to 3)
		lblCompare3 = new JLabel();
		lblCompare3.setText(statementThree);
		lblCompare3.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompare3.setBounds(300, 440, 125, 35);
		tabLayer2.add(lblCompare3);
						
		lblBooleanCompare3 = new JLabel();
		lblBooleanCompare3.setText(getBooleanCompareThree());
		lblBooleanCompare3.setBounds(420, 445, 50, 20);
		tabLayer2.add(lblBooleanCompare3);
		
			
		
		// ************************************************************ TAB 3 *************************************************************
		// ************************************************************ TAB 3 *************************************************************
		JPanel tabLayer3 = new JPanel();
		tabLayer3.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Layer 3", null, tabLayer3, null);
		tabLayer3.setLayout(null);

		// ************************************************************ User can set the
		// number of agents
		JLabel lblNumberOfAgents = new JLabel("Number of Agents:");
		lblNumberOfAgents.setForeground(Color.LIGHT_GRAY);
		lblNumberOfAgents.setBounds(10, 25, 150, 14);
		tabLayer3.add(lblNumberOfAgents);

		textField_NumAgents = new JTextField();
		textField_NumAgents.setText("4");
		textField_NumAgents.setBounds(150, 23, 40, 20);
		tabLayer3.add(textField_NumAgents);
		textField_NumAgents.setColumns(10);

		JButton btnUpdateAgents = new JButton("Update Agents");
		btnUpdateAgents.setForeground(Color.LIGHT_GRAY);
		btnUpdateAgents.setBounds(215, 23, 150, 20);
		tabLayer3.add(btnUpdateAgents);

		// ************************************************************ User can change
		// the color of the agents
		JLabel lblAgentsColor = new JLabel("Agents Color:");
		lblAgentsColor.setBounds(440, 25, 125, 14);
		tabLayer3.add(lblAgentsColor);
		// update the color of agents in board.agents[]
		JComboBox comboBox_AgentColor = new JComboBox();
		comboBox_AgentColor.setModel(new DefaultComboBoxModel(
				new String[] { "GREEN", "YELLOW", "ORANGE", "MAGENTA", "BLUE", "RED", "WHITE", "BLACK", "CYAN" }));
		Color[] agentColorList = new Color[] { Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.BLUE,
				Color.RED, Color.WHITE, Color.BLACK, Color.CYAN };
		comboBox_AgentColor.setBounds(568, 23, 100, 20);
		comboBox_AgentColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JComboBox src = (JComboBox) e.getSource();
				agentColor = agentColorList[src.getSelectedIndex()];
				board.updateAgentColor(agentColor);
				whetherAgentsVisible = true;
				board.repaint();

				if (!timerStarted) {
					board.repaint();
				}
			}
		});
		tabLayer3.add(comboBox_AgentColor);
		
		//MODIFICATION: Special Agent Color
		//
		//Added 5/21 by Morgan Might
		//
		// User can change the color of the "special" agents
		JLabel lblSpecialAgentsColor = new JLabel("Special Agent Color:");
		lblSpecialAgentsColor.setBounds(440, 58, 125, 14);
		tabLayer3.add(lblSpecialAgentsColor);
		// update the color of the special agent in board.agents[]
		JComboBox comboBox_SpecialAgentColor = new JComboBox();
		comboBox_SpecialAgentColor.setModel(new DefaultComboBoxModel(
				new String[] {"CYAN", "GREEN", "YELLOW", "ORANGE", "MAGENTA", "BLUE", "RED", "WHITE", "BLACK"}));
		Color[] specialAgentColorList = new Color[] { Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.BLUE,
				Color.RED, Color.WHITE, Color.BLACK};
		comboBox_SpecialAgentColor.setBounds(568, 56, 100, 20);
		comboBox_SpecialAgentColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox src = (JComboBox) e.getSource();
				specialAgentColor = specialAgentColorList[src.getSelectedIndex()];
				board.updateSpecialAgentColor(specialAgentColor);
				whetherAgentsVisible = true;
				board.repaint();

				if (!timerStarted) {
					board.repaint();
				}
			}
		});
		tabLayer3.add(comboBox_SpecialAgentColor);
		
		// ************************************************************ User can select
		// how many changes the agent can make
		JLabel lblNumberOfChanges = new JLabel("Number of Changes:");
		lblNumberOfChanges.setForeground(Color.LIGHT_GRAY);
		lblNumberOfChanges.setBounds(10, 58, 150, 14);
		tabLayer3.add(lblNumberOfChanges);

		textField_NumAgentChanges = new JTextField();
		textField_NumAgentChanges.setText("-1");
		textField_NumAgentChanges.setBounds(150, 56, 40, 20);
		tabLayer3.add(textField_NumAgentChanges);
		textField_NumAgentChanges.setColumns(10);

		JButton btnUpdateChanges = new JButton("Update Changes");
		btnUpdateChanges.setForeground(Color.LIGHT_GRAY);
		btnUpdateChanges.setBounds(215, 56, 150, 20);
		tabLayer3.add(btnUpdateChanges);

		// ************************************************************ User can choose
		// how close an agent can get to another. 0 implies that many spaces between,
		// thus they could overlap.
		JLabel lblAgentCloseness = new JLabel("Agent Closeness:");
		lblAgentCloseness.setForeground(Color.LIGHT_GRAY);
		lblAgentCloseness.setBounds(10, 92, 150, 14);
		tabLayer3.add(lblAgentCloseness);

		textField_AgentCloseness = new JTextField();
		textField_AgentCloseness.setText("0");
		textField_AgentCloseness.setBounds(150, 90, 40, 20);
		tabLayer3.add(textField_AgentCloseness);
		textField_AgentCloseness.setColumns(10);

		JButton btnUpdateCloseness = new JButton("Update Closeness");
		btnUpdateCloseness.setForeground(Color.LIGHT_GRAY);
		btnUpdateCloseness.setBounds(215, 90, 150, 20);
		tabLayer3.add(btnUpdateCloseness);

		// ************************************************************ User changes the
		// color of the pheromone trails on the board.
		JLabel lblPharamoneTrailColor = new JLabel("Pheromone Color:");
		lblPharamoneTrailColor.setForeground(Color.LIGHT_GRAY);
		lblPharamoneTrailColor.setBounds(440, 91, 125, 14);
		tabLayer3.add(lblPharamoneTrailColor);

		JComboBox comboBox_PheromoneColor = new JComboBox();
		comboBox_PheromoneColor.setModel(new DefaultComboBoxModel(
				new String[] { "WHITE", "GREEN", "YELLOW", "ORANGE", "MAGENTA", "BLUE", "RED", "BLACK" }));
		comboBox_PheromoneColor.setBounds(568, 89, 100, 20);
		tabLayer3.add(comboBox_PheromoneColor);

		// ************************************************************ User can set how
		// strongly the agents should follow the swarm.
		JLabel lblPheromoneStrength = new JLabel("Pheromone Strength:");
		lblPheromoneStrength.setForeground(Color.LIGHT_GRAY);
		lblPheromoneStrength.setBounds(10, 125, 150, 14);
		tabLayer3.add(lblPheromoneStrength);

		textField_PheromoneStrength = new JTextField();
		textField_PheromoneStrength.setText("1");
		textField_PheromoneStrength.setColumns(10);
		textField_PheromoneStrength.setBounds(150, 123, 40, 20);
		tabLayer3.add(textField_PheromoneStrength);

		JButton btnUpdatePStrength = new JButton("Update P Strength");
		btnUpdatePStrength.setForeground(Color.LIGHT_GRAY);
		btnUpdatePStrength.setBounds(215, 123, 150, 18);
		tabLayer3.add(btnUpdatePStrength);

		//MODIFICATION:
		//
		//Altered 5/18 by Morgan Might
		//Added toggleCount integer to allow button to properly set the agents color back to visible
		JToggleButton tglbtnViewAgents = new JToggleButton("View Agents");
		tglbtnViewAgents.setSelected(true);
		tglbtnViewAgents.setBounds(10, 495, 187, 69);
		tglbtnViewAgents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				whetherAgentsVisible = !whetherAgentsVisible;
				toggleCount++;
				//MODIFICATION: if the agents are now visible restore their color
				if(toggleCount > 0 && whetherAgentsVisible) {
					board.updateVisibleAgentColors();					
				}
				if (!timerStarted) {
					board.repaint();
				}
			}
		});
		tabLayer3.add(tglbtnViewAgents);

		JButton btnSetPhrmnTrail = new JButton("Set Phrmn Trail");
		btnSetPhrmnTrail.setForeground(Color.LIGHT_GRAY);
		btnSetPhrmnTrail.setBounds(10, 272, 187, 30);
		tabLayer3.add(btnSetPhrmnTrail);

		JButton btnRemovePhrmnTrail = new JButton("Remove Phrmn Trail");
		btnRemovePhrmnTrail.setForeground(Color.LIGHT_GRAY);
		btnRemovePhrmnTrail.setBounds(10, 316, 187, 30);
		tabLayer3.add(btnRemovePhrmnTrail);

		JButton btnSetPhrmnZone = new JButton("Set Phrmn Zone");
		btnSetPhrmnZone.setForeground(Color.LIGHT_GRAY);
		btnSetPhrmnZone.setBounds(215, 272, 187, 30);
		tabLayer3.add(btnSetPhrmnZone);

		JButton btnRemovePhrmnZone = new JButton("Remove Phrmn Zone");
		btnRemovePhrmnZone.setForeground(Color.LIGHT_GRAY);
		btnRemovePhrmnZone.setBounds(215, 316, 187, 30);
		tabLayer3.add(btnRemovePhrmnZone);

		JToggleButton tglbtnAttractOrRepel = new JToggleButton("Attract");
		tglbtnAttractOrRepel.setSelected(true);
		tglbtnAttractOrRepel.setBounds(215, 495, 187, 69);
		tglbtnAttractOrRepel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attractOrRepel = !attractOrRepel;
				if (attractOrRepel) {
					tglbtnAttractOrRepel.setText("Attract");
				} else {
					tglbtnAttractOrRepel.setText("Repel");
				}
			}
		});
		tabLayer3.add(tglbtnAttractOrRepel);

		JToggleButton tglbtnWrapAgents = new JToggleButton("Bounce Agents");
		tglbtnWrapAgents.setSelected(true);
		tglbtnWrapAgents.setBounds(420, 495, 187, 69);
		tglbtnWrapAgents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wrap = !wrap;
				board.setWrap(wrap);
				if (wrap) {
					tglbtnWrapAgents.setText("Wrap Agents");
				} else {
					tglbtnWrapAgents.setText("Bounce Agents");
				}
			}
		});
		tabLayer3.add(tglbtnWrapAgents);
		
		//MODIFICATION #7: New layer, Layer 4, is concerned with the persistence of the cells
		//on Layer 1
		//
		//by Morgan Might
		//July 5, 2018
		
		// ************************************************************ TAB 4 *************************************************************
		// ************************************************************ TAB 4 *************************************************************
		JPanel tabLayer4 = new JPanel();
		tabLayer4.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Layer 4", null, tabLayer4, null);
		tabLayer4.setLayout(null);
		
		

		// ************************************************************ Global Zone
		// Buttons + Slider ************************************************************
		// ************************************************************ Just shows
		// information
		JLabel lblBoardSizeGlobal = new JLabel("Board Size:");
		lblBoardSizeGlobal.setBounds(820, 621, 74, 14);
		frmProjectLegion.getContentPane().add(lblBoardSizeGlobal);

		lblBoardSizeInt.setBounds(910, 621, 46, 14);
		frmProjectLegion.getContentPane().add(lblBoardSizeInt);

		JLabel lblSwarmCount = new JLabel("Swarm Count:");
		lblSwarmCount.setBounds(1014, 621, 100, 14);
		frmProjectLegion.getContentPane().add(lblSwarmCount);

		lblSwarmCountInt.setBounds(1109, 621, 46, 14);
		frmProjectLegion.getContentPane().add(lblSwarmCountInt);

		JLabel lblSwarmRate = new JLabel("Swarm Rate:");
		lblSwarmRate.setBounds(820, 646, 90, 14);
		frmProjectLegion.getContentPane().add(lblSwarmRate);

		// ************************************************************ Slider for the
		// user to change how fast the board will step
		JSlider sliderSwarmSpeed = new JSlider(0, 100, 50);
		sliderSwarmSpeed.setBounds(953, 646, 450, 30);
		sliderSwarmSpeed.setMajorTickSpacing(5);
		sliderSwarmSpeed.setPaintLabels(true);
		// slider to change the speed of the agents in board.agents[]
		sliderSwarmSpeed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider src = (JSlider) e.getSource();
				agentSliderRate = src.getValue();
				board.setAgentRate((int) agentSliderRate * 5);
			}
		});
		frmProjectLegion.getContentPane().add(sliderSwarmSpeed);

		JLabel lblSlow = new JLabel("Slow");
		lblSlow.setBounds(910, 646, 46, 14);
		frmProjectLegion.getContentPane().add(lblSlow);

		JLabel lblFast = new JLabel("Fast");
		lblFast.setBounds(1417, 646, 46, 14);
		frmProjectLegion.getContentPane().add(lblFast);

		// ************************************************************ Buttons that
		// start stop and do other things that they are clearly labeled for.
		// button to freeze swarm agents
		JButton btnStopSwarm = new JButton("Stop Swarm");
		btnStopSwarm.setBackground(new Color(255, 51, 51));
		btnStopSwarm.setBounds(1030, 726, 125, 23);
		btnStopSwarm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				board.t.cancel();
				timerStarted = false;
			}
		});
		frmProjectLegion.getContentPane().add(btnStopSwarm);
		// button used to unfreeze swarm agents
		JButton btnStartSwarm = new JButton("Start Swarm");
		btnStartSwarm.setBackground(new Color(0, 255, 0));
		btnStartSwarm.setBounds(895, 726, 125, 23);
		btnStartSwarm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!timerStarted) {
					board.StartTimer();
					timerStarted = true;
				}
			}
		});
		frmProjectLegion.getContentPane().add(btnStartSwarm);
		
		//THESE BUTTONS DO NOT WORK

		JButton btnRecord = new JButton("Record");
		btnRecord.setForeground(Color.LIGHT_GRAY);
		btnRecord.setBackground(new Color(102, 255, 153));
		btnRecord.setBounds(895, 767, 125, 23);
		frmProjectLegion.getContentPane().add(btnRecord);

		JButton btnStopRecord = new JButton("Stop Record");
		btnStopRecord.setForeground(Color.LIGHT_GRAY);
		btnStopRecord.setBackground(new Color(255, 102, 102));
		btnStopRecord.setBounds(1030, 767, 125, 23);
		frmProjectLegion.getContentPane().add(btnStopRecord);

		JButton btnSaveRec = new JButton("Save Rec.");
		btnSaveRec.setForeground(Color.LIGHT_GRAY);
		btnSaveRec.setBackground(new Color(204, 51, 255));
		btnSaveRec.setBounds(1203, 767, 125, 23);
		frmProjectLegion.getContentPane().add(btnSaveRec);

		JButton btnRestart = new JButton("Restart");
		btnRestart.setForeground(Color.LIGHT_GRAY);
		btnRestart.setBackground(new Color(51, 204, 255));
		btnRestart.setBounds(1203, 726, 125, 23);
		frmProjectLegion.getContentPane().add(btnRestart);

		// ************************************************************ This code will
		// open a new JFrame that will ask the user the new dimensions for the new
		// board.
		JButton btnNewScreenSave = new JButton("Screen Shot");
		btnNewScreenSave.setForeground(Color.LIGHT_GRAY);
		btnNewScreenSave.setBackground(new Color(204, 51, 255));
		btnNewScreenSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewScreenSave.setBounds(1338, 767, 125, 23);
		frmProjectLegion.getContentPane().add(btnNewScreenSave);

		JLabel lblSlowCycless = new JLabel("1/2 Cycle/s");
		lblSlowCycless.setBounds(910, 671, 100, 14);
		frmProjectLegion.getContentPane().add(lblSlowCycless);

		JLabel lblFastCycless = new JLabel("5 Cycles/s");
		lblFastCycless.setBounds(1417, 671, 100, 14);
		frmProjectLegion.getContentPane().add(lblFastCycless);
		// create new NewBoardWindow to make new board
		JButton btnNewBoard = new JButton("New Board");
		btnNewBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Object obj = new NewBoardWindow();
				NewBoardWindow newBoardWindow = new NewBoardWindow(frmProjectLegion);
				newBoardWindow.setVisible(true);
				tglbtnWrapAgents.setSelected(true);
				comboGoalStrategy.setSelectedItem(goalStrategy);
				// lblBoardSizeInt.setText(String.valueOf(board.labelHandler.getInitBoardSize()));
			}
		});
		btnNewBoard.setBackground(new Color(51, 102, 255));
		btnNewBoard.setBounds(1338, 726, 125, 23);
		frmProjectLegion.getContentPane().add(btnNewBoard);
	}
	
	// ************************************************************ OTHER *************************************************************
	// ************************************************************ OTHER *************************************************************	

	public static void setLblBoardSizeInt(int boardSize) {
		lblBoardSizeInt.setText(String.valueOf(boardSize));
	}

	public static void setLblSwarmSizeInt(int swarmSize) {
		lblSwarmCountInt.setText(String.valueOf(swarmSize));
	}

	public static void setLblIntWhiteCells(int whiteCellSize) {
		lblIntWhiteCells.setText(String.valueOf(whiteCellSize));
	}
	
	//MODIFICATION #3
	//Added 5/24 by Morgan Might
	public static void setLblIntGrayCells(int grayCellSize) {
		lblIntGrayCells.setText(String.valueOf(grayCellSize));
	}

	public static void setLblIntBlackCells(int blackCellSize) {
		lblIntBlackCells.setText(String.valueOf(blackCellSize));
	}

	public static void setLblCurrWhiteCells(int currWhiteCells) {
		lblCurrWhiteCells.setText(String.valueOf(currWhiteCells));
	}
	
	//MODIFICATION #3
	//Added 5/24 by Morgan Might
	public static void setLblCurrGrayCells(int currGrayCells) {
		lblCurrGrayCells.setText(String.valueOf(currGrayCells));
	}

	public static void setLblCurrBlackCells(int currBlackCells) {
		lblCurrBlackCells.setText(String.valueOf(currBlackCells));
	}
	
	//MODIFICATION
	//Added 5/18 by Morgan Might
	public static void setNumOfSpecialAgents(int numAgents) {
		numSpecialAgents = numAgents;
	}
	
	//MODIFICATION
	//Added 5/18 by Morgan Might
	public static int getNumOfSpecialAgents() {
		return numSpecialAgents;
	}
	
	//MODIFICATION
	//Added 5/22 by Morgan Might
	public static void setSplitPolarity(boolean selected) {
		splitPolarity = selected;
	}
	
	//MODIFICATION
	//Added 5/22 by Morgan Might
	public static boolean getSplitPolarity() {
		return splitPolarity;
	}
	
	//MODIFICATION #3  does not work yet
	//Added 5/30 by Morgan Might
	public static void setDiagonalLineStart(boolean selected){
		diagonalLineStart = selected;
	}
	
	//MODIFICATION #3  does not work yet
	//Added 5/30 by Morgan Might
	public static boolean getThreeColor() {
		return diagonalLineStart;
	}

	//MODIFICATION #3
	//Added 5/30 by Morgan Might
	public static int getTotalNumOfCells() {
		// TODO Auto-generated method stub
		return board.getTotalNumCells();
	}
	
	//MODIFICATION #4:
	//
	//Added 6/4 by Morgan Might
	//Polarity Counters getters and setters to keep track of how many cells have each type of polarity
	//
	//
	//MODIFICATION #4: polarity 1
	public static int getPolOneCount() {
		return polOneCount;
	}
	
	public static void setPolOneCount(int num) {
		polOneCount = num;
		double fraction = (double) num/getTotalNumOfCells();
		lblPolarityOnePercent.setText(String.valueOf(fraction*100) + "%");
	}
		
	//MODIFICATION #4: polarity 2
	public static int getPolTwoCount() {
		return polTwoCount;
	}
	
	public static void setPolTwoCount(int num) {
		polTwoCount = num;
		double fraction = (double) num/getTotalNumOfCells();
		lblPolarityTwoPercent.setText(String.valueOf(fraction*100) + "%");
	}
		
	//MODIFICATION #4: polarity 3
	public static int getPolThreeCount() {
		return polThreeCount;
	}
	
	public static void setPolThreeCount(int num) {
		polThreeCount = num;
		double fraction = (double) num/getTotalNumOfCells();
		lblPolarityThreePercent.setText(String.valueOf(fraction*100) + "%");
	}
		
	//MODIFICATION #4: polarity 4
	public static int getPolFourCount() {
		return polFourCount;
	}
	
	public static void setPolFourCount(int num) {
		polFourCount = num;
		double fraction = (double) num/getTotalNumOfCells();
		lblPolarityFourPercent.setText(String.valueOf(fraction*100) + "%");
	}
	
	//MODIFICATION #4:
	//
	//Added 6/4 by Morgan Might
	//Comparison methods will compare the number of cells with different polarities and
	//return "True" or "False" to be displayed in GUI
	//
	//MODIFICATION #4: compares Red < Yellow
	//MODIFICATION #10 
	//updated 7/19 bby Morgan Might
	//Consider which polarity is the current dominant one
	public static String getBooleanCompareOne() {
		if(newDominantPolarity == "YELLOW") {
			if(getPolOneCount() < getPolThreeCount()) { //Red < Yellow
				return "True";
			}
			return "False";
		}
		else if(newDominantPolarity == "BLUE") {
			if(getPolOneCount() < getPolTwoCount()) { //Red < Blue
				return "True";
			}
			return "False";
		}
		else if(newDominantPolarity == "RED") {
			if(getPolTwoCount() < getPolOneCount()) { //Blue < Red
				return "True";
			}
			return "False";
		}
		else {
			return "Error";
		}
	}
	
	//MODIFICATION #4: compares Blue < Yellow
	//MODIFICATION #10 
	//updated 7/19 bby Morgan Might
	//Consider which polarity is the current dominant one
	public static String getBooleanCompareTwo() {
		if(newDominantPolarity == "YELLOW") {
			if(getPolTwoCount() < getPolThreeCount()) { //Blue < Yellow
				return "True";
			}
			return "False";
		}
		else if(newDominantPolarity == "BLUE") {
			if(getPolThreeCount() < getPolTwoCount()) { //Yellow < Blue
				return "True";
			}
			return "False";
		}
		else if(newDominantPolarity == "RED") {
			if(getPolThreeCount() < getPolOneCount()) { //Yellow < Red
				return "True";
			}
			return "False";
		}
		else {
			return "Error";
		}
	}
	
	//MODIFICATION #4: compares Red+Blue > Yellow
	//MODIFICATION #10 
	//updated 7/19 bby Morgan Might
	//Consider which polarity is the current dominant one
	public static String getBooleanCompareThree() {
		if(newDominantPolarity == "YELLOW") {
			if(getPolOneCount() + getPolTwoCount() > getPolThreeCount()) { //R + B > Yellow
				return "True";
			}
			return "False";
		}
		else if(newDominantPolarity == "BLUE") {
			if(getPolOneCount() + getPolThreeCount() > getPolTwoCount()) { //R + Y < Blue
				return "True";
			}
			return "False";
		}
		else if(newDominantPolarity == "RED") {
			if(getPolTwoCount() + getPolThreeCount() > getPolOneCount()) { //B + Y < Red
				return "True";
			}
			return "False";
		}
		else {
			return "Error";
		}
		
	}
	
	//MODIFICATION #4: displays in the labels where the statements are "True" or "False"
	public static void setLblComparisons() {
		lblBooleanCompare1.setText(getBooleanCompareOne());
		lblBooleanCompare2.setText(getBooleanCompareTwo());
		lblBooleanCompare3.setText(getBooleanCompareThree());
	}
	
	//MODIFICATION #5:
	//Added 6/12 by Morgan Might
	//
	//Return the boolean that relates to the toggle button that determines if the agents
	//should solve for a single polarity or three balanced polarities
	public static boolean getTogglePolarity() {
		return togglePolarity;
	}
	
	//MODIFICATION #10
		//Added 7/18/2018
		//By Morgan Might
		//This method updates the label and goal of the constraints
		public void updateDominantPolarity(String polarity) {
			newDominantPolarity = polarity;
			if(newDominantPolarity == "YELLOW") {
				//Change Label
				statementOne = "Red < Yellow :";
				statementTwo = "Blue < Yellow :";
				statementThree = "R + B > Yellow :";
				//Change constraint
				
				
			}
			else if(newDominantPolarity == "BLUE") {
				//Change Label
				statementOne = "Red < Blue :";
				statementTwo = "Yellow < Blue :";
				statementThree = "R + Y > Blue :";
				
			}
			else if(newDominantPolarity == "RED") {
				//Change Label
				statementOne = "Blue < Red :";
				statementTwo = "Yellow < Red :";
				statementThree = "B + Y > Red :";
			}
			updateComparisonLabels(statementOne, statementTwo, statementThree);
			
		}

	//MODIFICATION #10
		//Added 7/18 
		//By Morgan Might
		//Updates the text in the JLables to display the new constraints		
		public static void updateComparisonLabels(String one, String two, String three) {
			lblCompare1.setText(one);
			lblCompare2.setText(two);
			lblCompare3.setText(three);
		}

		public static void updateStepCountLabel(String count) {
			lblStepDisplay.setText(count);
			
		}
		
}

