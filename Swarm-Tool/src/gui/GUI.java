package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	private static final int COUNT_POLARITIES_MAXIMUM = 4;
	private static final JFileChooser SELECTOR_FILEPATH = new JFileChooser();
	private static final String[] OPTIONS_COLORS_POLARITIES_NAMES = {"RED", "BLUE", "YELLOW", "GREEN", "CYAN", "WHITE", "BLACK"};
	private static final Color[] COLORS_BASE = {Color.WHITE, Color.GRAY, Color.BLACK};
	private static final Color[] OPTIONS_COLORS_POLARITIES = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.WHITE, Color.BLACK};
	
	public JFrame frmProjectLegion;// main frame
	private JTextField textField_NumAgents;
	private JTextField textField_NumAgentChanges;
	private JTextField textField_AgentCloseness;
	private JTextField textField_PheromoneStrength;
	private JTextField textField_BoardSize;
	private JTextField textField_TriggerFlip;
	private JTextField textField_TriggerFlipLayer2;
	private JLabel lblBoardSizeInt; // updates BoardSize Label
	private JLabel lblSwarmCountInt; // updates SwarmCount Label
//	private JLabel lblBooleanCompare1; //  displays boolean (polarity1 < polarity3)  MODIFICATION #4
//	private JLabel lblBooleanCompare2; //  displays boolean (polarity2 < polarity3)  MODIFICATION #4
//	private JLabel lblBooleanCompare3; //  displays boolean (polarity1+polarity2 < polarity3)  MODIFICATION #4
	private JLabel lblCompare1; // displays the current constraints MODIFICATION #10
	private JLabel lblCompare2; // displays the current constraints MODIFICATION #10
	private JLabel lblCompare3; // displays the current constraints MODIFICATION #10
	private JLabel lblStepDisplay; //displays the number of steps that have occurred
	private JLabel[] labelsFrequencyColorsInitial;
	private JLabel[] labelsFrequencyColors;
	private JLabel[] labelsPercentPolarities;
	private JLabel[] labels;
	private HashMap<Integer, Color> colorsPolarity;

	public int layer2Draw = 1;// which cell array in board to display
	public Board board;// board to be drawn
	private boolean timerStarted;// timer or agent step
	public int initBoardSize, initAgentCount;
	public boolean attractOrRepel = true;
	public Color agentColor = Color.GREEN;
	public Color specialAgentColor = Color.CYAN; //MODIFICATION color for the "special agent"
	public boolean whetherAgentsVisible = true;
	public AbstractStrategy goalStrategy = new CheckerBoard();
	public String newDominantPolarity = "YELLOW";	//MODIFICATION #10 added 7/18 by Morgan Might 	//Keep track of polarity that should be most dominant
	public String statementOne = "Red < Yellow :"; //MODIFICATION #10  //These variables will be used to display the constraints
	public String statementTwo = "Blue < Yellow :"; //MODIFICATION #10 
	public String statementThree = "R + B > Yellow :"; //MODIFICATION #10 
	public int agentSliderRate;
	public boolean wrap = false;
	public int toggleCount = 0; //MODIFICATION:  used in implementing the View Agents Button
	public int numSpecialAgents; //MODIFICATION: how many agents should be a separate color
	public boolean splitPolarity; //MODIFICATION: if true set the board to be "stuck"
	public boolean diagonalLineStart; //MODIFICATION #9
	private int indexPolarityDominant;

	public boolean threePol; //MODIFICATION #3   needs fixed
	public int percentToFlip; //MODIFICATION #2 stores the number of random cells to flip

	public boolean togglePolarity = false; //MODIFICATION #5 determines if the agents goal is a single polarity or three balanced polarities
	
	private JComboBox<String>[] menusDropDownPolarity;
	private HashMap<Color, Integer> frequencyColorsInitial;
	private HashMap<Color, Integer> frequencyColors;
	private HashMap<Color, Integer> frequencyPolarities;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmProjectLegion.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Color getPolarityColor(int polarity) {
		return colorsPolarity.get(polarity);
	}

	public void setBoard(Board board) {
		this.board = board;
		
		this.frequencyColorsInitial = board.getColorFrequenciesInitial();
		this.frequencyColors = board.getColorFrequencies();
		this.frequencyPolarities = board.getPolarityFrequencies();
		
		board.setAgentRate(agentSliderRate);
		board.repaint();
		
		for(int indexLabel = 0; indexLabel < labelsFrequencyColorsInitial.length; indexLabel++) {
			labelsFrequencyColorsInitial[indexLabel].setText(Integer.toString(getMapValueInteger(frequencyColorsInitial, COLORS_BASE[indexLabel])));
		}
		
		updateLabels(board.getStepCount());
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

		timerStarted = false;
		
		colorsPolarity = new HashMap<Integer, Color>(COUNT_POLARITIES_MAXIMUM);
		for(int indexPolarity = 0; indexPolarity < COUNT_POLARITIES_MAXIMUM; indexPolarity++) {
			colorsPolarity.put(indexPolarity + 1, OPTIONS_COLORS_POLARITIES[indexPolarity]);
		}
		
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
		frame.getContentPane().add(boardInGUI);

		// This is where the tabs for the layer options go.
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(818, 10, 700, 600);
		// used to change whether board.cells or board.cells2 is shown in board when the
		// tab selected is changed
		ChangeListener changeListener = new ChangeListener() {
			@Override
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
		JButton btnChangeBoardSize = new JButton("Update Size");   //DOES NOT WORK YET
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

		JLabel lblIntWhiteCells = new JLabel();
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

		JLabel lblIntGrayCells = new JLabel();
		lblIntGrayCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntGrayCells.setBounds(145, 44, 125, 35);
		tabLayer1.add(lblIntGrayCells);

		JLabel lblNumStartingBlack = new JLabel("Initial Black Cells:");
		lblNumStartingBlack.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumStartingBlack.setBounds(10, 79, 125, 35);
		tabLayer1.add(lblNumStartingBlack);

		JLabel lblIntBlackCells = new JLabel();
		lblIntBlackCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntBlackCells.setBounds(145, 79, 125, 35);
		tabLayer1.add(lblIntBlackCells);

		labelsFrequencyColorsInitial = new JLabel[]{lblIntWhiteCells, lblIntGrayCells, lblIntBlackCells};
		
		JLabel lblBoardSizeLayer = new JLabel("Board Size:");
		lblBoardSizeLayer.setForeground(Color.LIGHT_GRAY);
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
		textField_TriggerFlip = new JTextField("20", 10);
		textField_TriggerFlip.setBounds(177, 225, 50, 20);
		tabLayer1.add(textField_TriggerFlip);

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

		JLabel lblCurrWhiteCells = new JLabel();
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

		JLabel lblCurrGrayCells = new JLabel();
		lblCurrGrayCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrGrayCells.setBounds(145, 334, 125, 35);
		tabLayer1.add(lblCurrGrayCells);

		JLabel lblNumCurrentBlack = new JLabel("Current Black Cells:");
		lblNumCurrentBlack.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumCurrentBlack.setBounds(10, 368, 125, 35);
		tabLayer1.add(lblNumCurrentBlack);

		JLabel lblCurrBlackCells = new JLabel();
		lblCurrBlackCells.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrBlackCells.setBounds(145, 368, 125, 35);
		tabLayer1.add(lblCurrBlackCells);

		labelsFrequencyColors = new JLabel[]{lblCurrWhiteCells, lblCurrGrayCells, lblCurrBlackCells};
		
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

		ItemListener listenerDropDownColorPolarity = new ItemListener() {
			Object itemSelectedPrevious;
			
			@Override
			public void itemStateChanged(ItemEvent event) {
				int change = event.getStateChange();
				
				if(change == ItemEvent.DESELECTED) {
					itemSelectedPrevious = event.getItem();
				}
				else {
					JComboBox<String> menuDropDownSource = (JComboBox<String>)event.getSource();
					Color colorSelected = OPTIONS_COLORS_POLARITIES[menuDropDownSource.getSelectedIndex()];
					
					if(colorsPolarity.containsValue(colorSelected)) {
						menuDropDownSource.removeItemListener(this);
						menuDropDownSource.setSelectedItem(itemSelectedPrevious);
						menuDropDownSource.addItemListener(this);
					}
					else {
						int indexPolarity = 0;
						for(int index = 0; index < menusDropDownPolarity.length; index++) {
							if(menusDropDownPolarity[index].equals(menuDropDownSource)) {
								indexPolarity = index;
								break;
							}
						}
						
						int indexSelectedPrevious = 0;
						for(int indexItem = 0; indexItem < menuDropDownSource.getModel().getSize(); indexItem++) {
							if(menuDropDownSource.getItemAt(indexItem).equals(itemSelectedPrevious)) {
								indexSelectedPrevious = indexItem;
								break;
							}
						}
						
						board.updatePolarityColor(OPTIONS_COLORS_POLARITIES[indexSelectedPrevious], colorSelected);
						colorsPolarity.put(indexPolarity + 1, colorSelected);
						
						if (!timerStarted) {
							board.repaint();
						}
					}
				}
			}
		};
		
		// ************************************************************ Primary color
		// for polarity choice comboBox where you can change it
		JLabel lblPrimaryPolarityColor = new JLabel("Primary Polarity Color");
		lblPrimaryPolarityColor.setBounds(25, 25, 125, 14);
		tabLayer2.add(lblPrimaryPolarityColor);

		// ************************************************************ Secondary color
		// for polarity choice comboBox where you can change it
		JLabel lblSecondaryPolarityColor = new JLabel("Secondary Polarity Color");
		lblSecondaryPolarityColor.setBounds(238, 25, 150, 14);
		tabLayer2.add(lblSecondaryPolarityColor);

		// ************************************************************ Tertiary color
		// comboBox if we want it.
		JLabel lblTertiaryPolarityColor = new JLabel("Tertiary Polarity Color");
		lblTertiaryPolarityColor.setBounds(25, 100, 125, 14);
		tabLayer2.add(lblTertiaryPolarityColor);

		JLabel lblQuaternaryPolarityColor = new JLabel("Quaternary Polarity Color");
		lblQuaternaryPolarityColor.setBounds(238, 100, 150, 22);
		tabLayer2.add(lblQuaternaryPolarityColor);

		
		menusDropDownPolarity = new JComboBox[4];
		for(int indexMenu = 0; indexMenu < menusDropDownPolarity.length; indexMenu++) {
			menusDropDownPolarity[indexMenu] = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_POLARITIES_NAMES));
			menusDropDownPolarity[indexMenu].setSelectedIndex(indexMenu);
			menusDropDownPolarity[indexMenu].addItemListener(listenerDropDownColorPolarity);
			tabLayer2.add(menusDropDownPolarity[indexMenu]);
		}
		menusDropDownPolarity[0].setBounds(25, 50, 125, 22);
		menusDropDownPolarity[1].setBounds(238, 50, 125, 22);
		menusDropDownPolarity[2].setBounds(25, 125, 125, 22);
		menusDropDownPolarity[3].setBounds(238, 125, 125, 22);
		
		// ************************************************************ Sets what the
		// polarity ratios should be for the two colors.
		JLabel lblReginalStability = new JLabel("Regional Stability");  //Does not work
		lblReginalStability.setForeground(Color.LIGHT_GRAY);
		lblReginalStability.setBounds(25, 175, 125, 14);
		tabLayer2.add(lblReginalStability);

		JComboBox<String> regionalStability = new JComboBox<String>();  //Does not work
		regionalStability.setModel(new DefaultComboBoxModel<String>(new String[] {"50/50", "60/40", "70/30", "80/20", "90/10", "100/0" }));
		regionalStability.setBounds(25, 200, 125, 22);
		tabLayer2.add(regionalStability);

		// ************************************************************ Updates the
		// polarity to what is entered on the radio buttons
		JLabel lblPolarity = new JLabel("Goal");
		lblPolarity.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolarity.setBounds(238, 175, 150, 14);
		tabLayer2.add(lblPolarity);

		JComboBox<String> comboGoalStrategy = new JComboBox<String>();
		comboGoalStrategy.setModel(new DefaultComboBoxModel<String>(new String[] { "CheckerBoard", "Lines", "All Black", "Diagonal Lines" }));
		AllBlack allBlack = new AllBlack();
		CheckerBoard checkerBoard = new CheckerBoard();
		Lines lines = new Lines();
		DiagonalLines diagonalLines = new DiagonalLines();
		AbstractStrategy[] goalStrategyList = new AbstractStrategy[] {checkerBoard, lines, allBlack, diagonalLines};
		//MODIFICATION: start with Diagonal Lines as the Goal
		//Added 7/17 by Morgan Might
		if(diagonalLineStart) {
			goalStrategy = goalStrategyList[3]; //Still DOES NOT work
		}
		comboGoalStrategy.setBounds(238, 200, 150, 22);
		comboGoalStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> src = (JComboBox<String>)e.getSource();
				goalStrategy = goalStrategyList[src.getSelectedIndex()];
				System.out.println("----- STRATEGY CHANGE IN PROGRESS -----");
				board.updateGoalStrategy(goalStrategy);
				whetherAgentsVisible = true;
				System.out.println("----- STRATEGY CHANGE COMPLETE -----");
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
		JComboBox<String> dominantPolarity = new JComboBox<String>();  //Does not work
		dominantPolarity.setModel(new DefaultComboBoxModel<String>(new String[] { "YELLOW", "BLUE", "RED" }));
		String[] dominantPolarityList = new String[] { "YELLOW", "BLUE", "RED" };
		dominantPolarity.setBounds(25, 350, 125, 22);
		dominantPolarity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> src = (JComboBox<String>) e.getSource();
				indexPolarityDominant = src.getSelectedIndex();
				newDominantPolarity = dominantPolarityList[indexPolarityDominant++];
				updateDominantPolarity(newDominantPolarity);
			}
		});
		tabLayer2.add(dominantPolarity);

		//MODIFICATION #10:
		//Added 7/23 by Morgan Might
		//Display how many steps have occured
		JLabel lblStepCount = new JLabel("Step:");
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

		JLabel lblPolarityOnePercent = new JLabel();
		//lblPolarityOnePercent.setText(getPolOneCount() + "%");
		lblPolarityOnePercent.setBounds(137, 405, 50, 20);
		tabLayer2.add(lblPolarityOnePercent);

		//Polarity 2 label
		JLabel lblPolarityTwo = new JLabel("Blue :");
		lblPolarityTwo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolarityTwo.setBounds(10, 420, 125, 35);
		tabLayer2.add(lblPolarityTwo);

		JLabel lblPolarityTwoPercent = new JLabel();
		//lblPolarityTwoPercent.setText(getPolOneCount() + "%");
		lblPolarityTwoPercent.setBounds(137, 425, 50, 20);
		tabLayer2.add(lblPolarityTwoPercent);

		//Polarity 3 label
		JLabel lblPolarityThree = new JLabel("Yellow :");
		lblPolarityThree.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolarityThree.setBounds(10, 440, 125, 35);
		tabLayer2.add(lblPolarityThree);

		JLabel lblPolarityThreePercent = new JLabel();
		//lblPolarityThreePercent.setText(getPolOneCount() + "%");
		lblPolarityThreePercent.setBounds(137, 445, 50, 20);
		tabLayer2.add(lblPolarityThreePercent);

		//Polarity 4 label
		//		JLabel lblPolarityFour = new JLabel("Polarity 4 :");
		//		lblPolarityFour.setHorizontalAlignment(SwingConstants.CENTER);
		//		lblPolarityFour.setBounds(10, 460, 125, 35);
		//		tabLayer2.add(lblPolarityFour);
		//				
		//		JLabel lblPolarityFourPercent = new JLabel();
		//		lblPolarityFourPercent.setText(getPolOneCount() + "%");
		//		lblPolarityFourPercent.setBounds(137, 460, 50, 20);
		//		tabLayer2.add(lblPolarityFourPercent);

		labelsPercentPolarities = new JLabel[]{lblPolarityOnePercent, lblPolarityTwoPercent, lblPolarityThreePercent};
		
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

		labels = new JLabel[COUNT_POLARITIES_MAXIMUM];
		for(int indexLabel = 0; indexLabel < labels.length; indexLabel++) {
			labels[indexLabel] = new JLabel();
			labels[indexLabel].setVerticalAlignment(SwingConstants.CENTER);
			labels[indexLabel].setBounds(420, (405 + (20 * indexLabel)), 50, 20);
			tabLayer2.add(labels[indexLabel]);
		}

		//MODIFICATION #4: Display boolean statements (comparing polarity 2 and 3)
		lblCompare2 = new JLabel();
		lblCompare2.setText(statementTwo);
		lblCompare2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompare2.setBounds(300, 420, 125, 35);
		tabLayer2.add(lblCompare2);

		//MODIFICATION #4: Display boolean statements (comparing polarity 1+2 to 3)
		lblCompare3 = new JLabel();
		lblCompare3.setText(statementThree);
		lblCompare3.setHorizontalAlignment(SwingConstants.CENTER);
		lblCompare3.setBounds(300, 440, 125, 35);
		tabLayer2.add(lblCompare3);

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
		JComboBox<String> comboBox_AgentColor = new JComboBox<String>();
		comboBox_AgentColor.setModel(new DefaultComboBoxModel<String>(new String[] { "GREEN", "YELLOW", "ORANGE", "MAGENTA", "BLUE", "RED", "WHITE", "BLACK", "CYAN" }));
		Color[] agentColorList = new Color[] { Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.BLUE, Color.RED, Color.WHITE, Color.BLACK, Color.CYAN };
		comboBox_AgentColor.setBounds(568, 23, 100, 20);
		comboBox_AgentColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> src = (JComboBox<String>) e.getSource();
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
		JComboBox<String> comboBox_SpecialAgentColor = new JComboBox<String>();
		comboBox_SpecialAgentColor.setModel(new DefaultComboBoxModel<String>(new String[] {"CYAN", "GREEN", "YELLOW", "ORANGE", "MAGENTA", "BLUE", "RED", "WHITE", "BLACK"}));
		Color[] specialAgentColorList = new Color[] { Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.BLUE,
				Color.RED, Color.WHITE, Color.BLACK};
		comboBox_SpecialAgentColor.setBounds(568, 56, 100, 20);
		comboBox_SpecialAgentColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> src = (JComboBox<String>)e.getSource();
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

		JComboBox<String> comboBox_PheromoneColor = new JComboBox<String>();
		comboBox_PheromoneColor.setModel(new DefaultComboBoxModel<String>(new String[] { "WHITE", "GREEN", "YELLOW", "ORANGE", "MAGENTA", "BLUE", "RED", "BLACK" }));
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
				board.setWraparound(wrap);
				if (wrap) {
					tglbtnWrapAgents.setText("Wrap Agents");
				}
				else {
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

		lblBoardSizeInt = new JLabel();
		lblBoardSizeInt.setBounds(910, 621, 46, 14);
		frmProjectLegion.getContentPane().add(lblBoardSizeInt);

		JLabel lblSwarmCount = new JLabel("Swarm Count:");
		lblSwarmCount.setBounds(1014, 621, 100, 14);
		frmProjectLegion.getContentPane().add(lblSwarmCount);

		lblSwarmCountInt = new JLabel();
		lblSwarmCountInt.setBounds(1109, 621, 46, 14);
		frmProjectLegion.getContentPane().add(lblSwarmCountInt);

		JLabel lblSwarmRate = new JLabel("Swarm Rate:");
		lblSwarmRate.setBounds(820, 646, 90, 14);
		frmProjectLegion.getContentPane().add(lblSwarmRate);

		// ************************************************************ Slider for the
		// user to change how fast the board will step
		JSlider sliderSwarmSpeed = new JSlider(0, 100, 50);
		sliderSwarmSpeed.setBounds(953, 646, 450, 30);
		sliderSwarmSpeed.setMinimum(1);
		sliderSwarmSpeed.setMaximum(Board.RATE_STEPS_MAXIMUM);
		sliderSwarmSpeed.setValue((sliderSwarmSpeed.getMaximum() - sliderSwarmSpeed.getMinimum()) / 2);
		sliderSwarmSpeed.setMajorTickSpacing(sliderSwarmSpeed.getMaximum() / 10);
		sliderSwarmSpeed.setPaintLabels(true);
		
		// slider to change the speed of the agents in board.agents[]
		sliderSwarmSpeed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider src = (JSlider)e.getSource();
				agentSliderRate = src.getValue();
				board.setAgentRate(agentSliderRate);
			}
		});
		frmProjectLegion.getContentPane().add(sliderSwarmSpeed);

		agentSliderRate = sliderSwarmSpeed.getValue();
		
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
				board.stopTimer();
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
				if(board != null) {
					if (!timerStarted) {
						board.startTimer();
						timerStarted = true;
					}
				}
			}
		});
		frmProjectLegion.getContentPane().add(btnStartSwarm);

		//THESE BUTTONS DO NOT WORK

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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO SCREEENSHOT
				if(board != null) {
					BufferedImage capture = board.capture();
					
					SELECTOR_FILEPATH.setSelectedFile(new File("Simulation_" + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS").format(LocalDateTime.now()) + ".jpg"));
					int outcomeSave = SELECTOR_FILEPATH.showSaveDialog(btnNewScreenSave);
					if(outcomeSave == JFileChooser.APPROVE_OPTION) {
						try {
							ImageIO.write(capture, "JPG", SELECTOR_FILEPATH.getSelectedFile());
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
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

		btnNewBoard.addActionListener(
				new ActionListener() {
					private GUI gui;

					public ActionListener setGUI (GUI gui) {
						this.gui = gui;
						return this;
					}

					@Override
					public void actionPerformed(ActionEvent e) {
						// Object obj = new NewBoardWindow();
						NewBoardWindow newBoardWindow = new NewBoardWindow(frmProjectLegion, gui);
						newBoardWindow.setVisible(true);
						tglbtnWrapAgents.setSelected(true);
						comboGoalStrategy.setSelectedItem(goalStrategy);
						// lblBoardSizeInt.setText(String.valueOf(board.labelHandler.getInitBoardSize()));
					}
				}.setGUI(this)
				);
		btnNewBoard.setBackground(new Color(51, 102, 255));
		btnNewBoard.setBounds(1338, 726, 125, 23);
		frmProjectLegion.getContentPane().add(btnNewBoard);
	}

	// ************************************************************ OTHER *************************************************************
	// ************************************************************ OTHER *************************************************************	

//	public void setLblBoardSizeInt(int boardSize) {
//		lblBoardSizeInt.setText(String.valueOf(boardSize));
//	}

//	public void setLblSwarmSizeInt(int swarmSize) {
//		lblSwarmCountInt.setText(String.valueOf(swarmSize));
//	}

	//MODIFICATION
	//Added 5/18 by Morgan Might
	public void setNumOfSpecialAgents(int numAgents) {
		numSpecialAgents = numAgents;
	}

	//MODIFICATION
	//Added 5/18 by Morgan Might
	public int getNumOfSpecialAgents() {
		return numSpecialAgents;
	}

	//MODIFICATION
	//Added 5/22 by Morgan Might
	public void setSplitPolarity(boolean selected) {
		splitPolarity = selected;
	}

	//MODIFICATION
	//Added 5/22 by Morgan Might
	public boolean getSplitPolarity() {
		return splitPolarity;
	}

	//MODIFICATION #3  does not work yet
	//Added 5/30 by Morgan Might
	public void setDiagonalLineStart(boolean selected){
		diagonalLineStart = selected;
	}

	//MODIFICATION #3  does not work yet
	//Added 5/30 by Morgan Might
	public boolean getThreeColor() {
		return diagonalLineStart;
	}

	//MODIFICATION #3
	//Added 5/30 by Morgan Might
	public int getTotalNumOfCells() {
		return board.getTotalNumCells();
	}

//	//MODIFICATION #4:
//	//
//	//Added 6/4 by Morgan Might
//	//Comparison methods will compare the number of cells with different polarities and
//	//return "True" or "False" to be displayed in GUI
//	//
//	//MODIFICATION #4: compares Red < Yellow
//	//MODIFICATION #10 
//	//updated 7/19 bby Morgan Might
//	//Consider which polarity is the current dominant one
//	public String getBooleanCompareOne() {
//		if(newDominantPolarity.equalsIgnoreCase("YELLOW")) {
//			if(getPolOneCount() < getPolThreeCount()) { //Red < Yellow
//				return "True";
//			}
//
//			return "False";
//		}
//		else if(newDominantPolarity.equalsIgnoreCase("BLUE")) {
//			if(getPolOneCount() < getPolTwoCount()) { //Red < Blue
//				return "True";
//			}
//
//			return "False";
//		}
//		else if(newDominantPolarity.equalsIgnoreCase("RED")) {
//			if(getPolTwoCount() < getPolOneCount()) { //Blue < Red
//				return "True";
//			}
//
//			return "False";
//		}
//
//		return "Error";
//	}
//
//	//MODIFICATION #4: compares Blue < Yellow
//	//MODIFICATION #10 
//	//updated 7/19 bby Morgan Might
//	//Consider which polarity is the current dominant one
//	public String getBooleanCompareTwo() {
//		if(newDominantPolarity.equalsIgnoreCase("YELLOW")) {
//			if(getPolTwoCount() < getPolThreeCount()) { //Blue < Yellow
//				return "True";
//			}
//
//			return "False";
//		}
//		else if(newDominantPolarity.equalsIgnoreCase("BLUE")) {
//			if(getPolThreeCount() < getPolTwoCount()) { //Yellow < Blue
//				return "True";
//			}
//
//			return "False";
//		}
//		else if(newDominantPolarity.equalsIgnoreCase("RED")) {
//			if(getPolThreeCount() < getPolOneCount()) { //Yellow < Red
//				return "True";
//			}
//
//			return "False";
//		}
//
//		return "Error";
//	}
//
//	//MODIFICATION #4: compares Red+Blue > Yellow
//	//MODIFICATION #10 
//	//updated 7/19 bby Morgan Might
//	//Consider which polarity is the current dominant one
//	public String getBooleanCompareThree() {
//		if(newDominantPolarity.equalsIgnoreCase("YELLOW")) {
//			if(getPolOneCount() + getPolTwoCount() > getPolThreeCount()) { //R + B > Yellow
//				return "True";
//			}
//
//			return "False";
//		}
//		else if(newDominantPolarity.equalsIgnoreCase("BLUE")) {
//			if(getPolOneCount() + getPolThreeCount() > getPolTwoCount()) { //R + Y < Blue
//				return "True";
//			}
//
//			return "False";
//		}
//		else if(newDominantPolarity.equalsIgnoreCase("RED")) {
//			if(getPolTwoCount() + getPolThreeCount() > getPolOneCount()) { //B + Y < Red
//				return "True";
//			}
//
//			return "False";
//		}
//
//		return "Error";
//	}

//	//MODIFICATION #4: displays in the labels where the statements are "True" or "False"
//	public void setLblComparisons() {
//		lblBooleanCompare1.setText(getBooleanCompareOne());
//		lblBooleanCompare2.setText(getBooleanCompareTwo());
//		lblBooleanCompare3.setText(getBooleanCompareThree());
//	}

	//MODIFICATION #5:
	//Added 6/12 by Morgan Might
	//
	//Return the boolean that relates to the toggle button that determines if the agents
	//should solve for a single polarity or three balanced polarities
	public boolean getTogglePolarity() {
		return togglePolarity;
	}

	//MODIFICATION #10
	//Added 7/18/2018
	//By Morgan Might
	//This method updates the label and goal of the constraints
	public void updateDominantPolarity(String polarity) {
		newDominantPolarity = polarity;
		if(newDominantPolarity.equals("YELLOW")) {
			//Change Label
			statementOne = "Red < Yellow :";
			statementTwo = "Blue < Yellow :";
			statementThree = "R + B > Yellow :";
			//Change constraint
		}
		else if(newDominantPolarity.equals("BLUE")) {
			//Change Label
			statementOne = "Red < Blue :";
			statementTwo = "Yellow < Blue :";
			statementThree = "R + Y > Blue :";
		}
		else if(newDominantPolarity.equals("RED")) {
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
	public void updateComparisonLabels(String one, String two, String three) {
		lblCompare1.setText(one);
		lblCompare2.setText(two);
		lblCompare3.setText(three);
	}

	public void updateStepCountLabel(String count) {
		lblStepDisplay.setText(count);
	}

	public Board getBoard() {
		return board;
	}

	public void updateLabels(int step) {
		lblStepDisplay.setText(Integer.toString(step));
		
		for(int indexLabel = 0; indexLabel < labelsFrequencyColors.length; indexLabel++) {
			labelsFrequencyColors[indexLabel].setText(Integer.toString(getMapValueInteger(frequencyColors, COLORS_BASE[indexLabel])));
		}
		
		System.out.println("Polarity 1: " + getMapValueInteger(frequencyPolarities, colorsPolarity.get(1)));
		System.out.println("Polarity 2: " + getMapValueInteger(frequencyPolarities, colorsPolarity.get(2)));
		System.out.println("Polarity 3: " + getMapValueInteger(frequencyPolarities, colorsPolarity.get(3)));
		System.out.println("Polarity 4: " + getMapValueInteger(frequencyPolarities, colorsPolarity.get(4)));
		System.out.println("Cell Count: " + board.getTotalNumCells() + '\n');
		
		for(int indexLabel = 0; indexLabel < labelsPercentPolarities.length; indexLabel++) {
			labelsPercentPolarities[indexLabel].setText((getMapValueInteger(frequencyPolarities, colorsPolarity.get(indexLabel + 1)) / (board.getTotalNumCells() / 100d)) + "%");
		}
		
		int indexLabelComparisonSum = goalStrategy.getCountPolarities() - 1;
		int indexPolarity = 1;
		int frequencyPolarity;
		int frequencyPolarityDominant = getMapValueInteger(frequencyPolarities, colorsPolarity.get(indexPolarityDominant));
		int frequencyPolaritiesSum = 0;
		for(int indexLabel = 0; indexLabel < indexLabelComparisonSum; indexLabel++) {
			if(indexPolarity == indexPolarityDominant) {
				indexPolarity++;
			}
			
			frequencyPolarity = getMapValueInteger(frequencyPolarities, colorsPolarity.get(indexPolarity));
			labels[indexLabel].setText(Boolean.toString(frequencyPolarity < frequencyPolarityDominant));
			frequencyPolaritiesSum += frequencyPolarity;
		}
		
		JLabel labelComparisonSum = labels[indexLabelComparisonSum];
		if(labelComparisonSum.isVisible()) {
			labels[indexLabelComparisonSum].setText(Boolean.toString(frequencyPolarityDominant < frequencyPolaritiesSum));
		}
		
	}
	
	private static int getMapValueInteger(HashMap<?, Integer> map, Object key) {
		if(map != null) {
			if(map.containsKey(key)) {
				return map.get(key);
			}
		}
		
		return 0;
	}
}

