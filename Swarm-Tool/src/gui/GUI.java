package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.peer.FramePeer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.NumberFormatter;

import cells.CellDisplayBase;
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
	private static final int DIMENSION_HEIGHT_INITIAL = 1070;
	private static final int DIMENSION_WIDTH_INITIAL = 1536;
	public static final int SIZE_BOARD_MAXIMUM = 800;// pixel size of board
	public static final int COUNT_POLARITIES_MAXIMUM = 4;
	private static final String HEADER_PROPERTIES = "#--- Swarm Simulation Properties ---#";
	private static final String FILETYPE_SCREENSHOT = ".jpg";
	private static final String FILETYPE_PROPERTIES = ".properties";
	private static final String PROPERTY_BOARD_SIZE = "board.size";
	private static final String PROPERTY_BOARD_WRAPAROUND = "board.wraparound";
	private static final String PROPERTY_AGENTS_COUNT = "agents.count";
	private static final String PROPERTY_AGENTS_COUNT_SPECIAL = "agents.special.count";
	private static final String PROPERTY_AGENTS_RATE = "agents.rate";
	private static final String PROPERTY_AGENTS_ACTIVE = "agents.active";
	private static final String PROPERTY_AGENTS_VISIBLE = "agents.visible";
	private static final String PROPERTY_RULE_GOAL = "rule.goal";
	private static final String PROPERTY_RULE_POLARITY_DOMINANT = "rule.polarity.dominant";
	private static final String PROPERTY_RULE_EQUILIBRIUM = "rule.equilibrium";
	private static final String PROPERTY_RULE_AUTOMATIC = "rule.automatic";
	private static final String PROPERTY_RULE_AUTOMATIC_REPETITITONS = "rule.automatic.repetitions";
	private static final String PROPERTY_RULE_AUTOMATIC_STEPS = "rule.automatic.steps";
	private static final String PROPERTY_COLOR_AGENT = "color.agents";
	private static final String PROPERTY_COLOR_AGENT_SPECIAL = "color.agents.special";
	private static final String PROPERTY_COLOR_POLARITY = "color.polarity.";
	private static final String PROPERTY_EXPORT_POLARITIES_INTERVAL = "export.polarities.interval";
	private static final String PROPERTY_EXPORT_POLARITIES_DIRECTORY = "export.polarities.directory";
	private static final String PROPERTY_EXPORT_SCREENSHOT_INTERVAL = "export.screenshot.interval";
	private static final String PROPERTY_EXPORT_SCREENSHOT_DIRECTORY = "export.screenshot.directory";
	private static final DateTimeFormatter FORMATTER_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
	private static final JFileChooser SELECTOR_FILEPATH = new JFileChooser();
	private static final Color[] OPTIONS_COLORS = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	private static final String[] OPTIONS_COLORS_NAMES = {"BLACK", "BLUE", "CYAN", "GRAY", "GREEN", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW"};
	private static final String[] OPTIONS_STRATEGIES_NAMES = {"BLACKOUT", "CHECKERBOARD", "DIAGONALS", "LINES"};
	private static final String[] PROPERTIES_BOARD = {PROPERTY_BOARD_SIZE, PROPERTY_BOARD_WRAPAROUND};
	private static final String[] PROPERTIES_AGENTS = {PROPERTY_AGENTS_COUNT, PROPERTY_AGENTS_COUNT_SPECIAL, PROPERTY_AGENTS_RATE, PROPERTY_AGENTS_ACTIVE, PROPERTY_AGENTS_VISIBLE};
	private static final String[] PROPERTIES_RULES = {PROPERTY_RULE_GOAL, PROPERTY_RULE_POLARITY_DOMINANT, PROPERTY_RULE_EQUILIBRIUM, PROPERTY_RULE_AUTOMATIC, PROPERTY_RULE_AUTOMATIC_REPETITITONS, PROPERTY_RULE_AUTOMATIC_STEPS};
	private static final String[] PROPERTIES_COLORS = generateColorProperties();
	private static final String[] PROPERTIES_EXPORT = {PROPERTY_EXPORT_POLARITIES_INTERVAL, PROPERTY_EXPORT_POLARITIES_DIRECTORY, PROPERTY_EXPORT_SCREENSHOT_INTERVAL, PROPERTY_EXPORT_SCREENSHOT_DIRECTORY};
	private static final AbstractStrategy[] OPTIONS_STRATEGIES = {new AllBlack(), new CheckerBoard(), new DiagonalLines(), new Lines()};
	private static final String[][] PROPERTIES = {PROPERTIES_BOARD, PROPERTIES_AGENTS, PROPERTIES_RULES, PROPERTIES_COLORS, PROPERTIES_EXPORT};
	private static final HashMap<Color, String> MAP_COLORS_NAMES = generateMapColorsNames();
	private static final HashMap<String, Color> MAP_COLORS = generateMapColors();
	private static final HashMap<String, AbstractStrategy> MAP_STRATEGIES = generateMapStrategies();

	public boolean splitPolarity; //MODIFICATION: if true set the board to be "stuck"
	private boolean timerStarted;// timer or agent step
	public boolean attractOrRepel = true;
	private boolean boardWraparound;
	private boolean modeEquilibrium; //MODIFICATION #5 determines if the agents goal is a single polarity or three balanced polarities
	private boolean modeAutomatic;
	private boolean whetherAgentsVisible;
	public boolean threePol; //MODIFICATION #3   needs fixed
	public int layer2Draw = 1;// which cell array in board to display
	public int agentSliderRate;
	private int indexPolarityDominant;
	private int sizeBoard;
	private int indexRepetition;
	private int countRepetitionsMaximum;
	private int countStepsMaximum;
	private int countAgents, countAgentsSpecial;
	private int intervalExportPolarities;
	private int intervalExportScreenshot;
	private Path pathFrequencies, pathScreenshot;
	private Properties settings;
	private AbstractStrategy goalStrategy;
	private Board board;// board to be drawn
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
	private JLabel lblStepDisplay; //displays the number of steps that have occurred
	private JToggleButton tglbtnViewAgents, tglbtnWrapAgents, tglbtnRulesApply, buttonSwarm;
	private JSlider sliderSwarmSpeed;
	private DefaultComboBoxModel<String> optionsPolarityDominant;
	private JComboBox<String> menuDropDownGoal, menuDropDownPolarityDominant, menuDropDownColorAgents, menuDropDownColorAgentsSpecial;
	private int[] frequencyColorsInitial;
	private int[] frequencyColors;
	private int[] frequencyPolarities;
	private JLabel[] labelsFrequencyColorsInitialText;
	private JLabel[] labelsFrequencyColorsText;
	private JLabel[] labelsPercentPolaritiesText;
	private JLabel[] labelsFrequencyColorsInitial;
	private JLabel[] labelsFrequencyColors;
	private JLabel[] labelsPercentPolarities;
	private JLabel[] labelsPolarityComparison;
	private JLabel[] labelsPolarityComparisonText;
	private Color[] colorsPolarity;
	private ArrayList<double[]> frequenciesPolaritiesAverage;
	private JComboBox<String>[] menusDropDownPolarity;
	private HashMap<Object, Command> propertyCommands;

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

	/**
	 * Create the application.
	 */
	public GUI() {
		frmProjectLegion = new JFrame();
		frmProjectLegion.setTitle("Project Legion");
		frmProjectLegion.setBounds(100, 100, DIMENSION_WIDTH_INITIAL, DIMENSION_HEIGHT_INITIAL);
		frmProjectLegion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProjectLegion.setLayout(null);

		timerStarted = false;

		propertyCommands = new HashMap<Object, Command>();
		propertyCommands.put(PROPERTY_BOARD_SIZE, new CommandBoardSize());
		propertyCommands.put(PROPERTY_BOARD_WRAPAROUND, new CommandBoardWraparound());
		propertyCommands.put(PROPERTY_AGENTS_COUNT, new CommandAgentCount());
		propertyCommands.put(PROPERTY_AGENTS_COUNT_SPECIAL, new CommandAgentCountSpecial());
		propertyCommands.put(PROPERTY_AGENTS_RATE, new CommandAgentRate());
		propertyCommands.put(PROPERTY_AGENTS_ACTIVE, new CommandAgentActive());
		propertyCommands.put(PROPERTY_AGENTS_VISIBLE, new CommandAgentVisible());
		propertyCommands.put(PROPERTY_RULE_GOAL, new CommandRuleGoal());
		propertyCommands.put(PROPERTY_RULE_POLARITY_DOMINANT, new CommandRuleDominantPolarity());
		propertyCommands.put(PROPERTY_RULE_EQUILIBRIUM, new CommandRuleEquilibrium());
		propertyCommands.put(PROPERTY_RULE_AUTOMATIC, new CommandRuleAutomatic());
		propertyCommands.put(PROPERTY_RULE_AUTOMATIC_REPETITITONS, new CommandRuleAutomaticRepetitions());
		propertyCommands.put(PROPERTY_RULE_AUTOMATIC_STEPS, new CommandRuleAutomaticSteps());
		propertyCommands.put(PROPERTY_COLOR_AGENT, new CommandColorAgents());
		propertyCommands.put(PROPERTY_COLOR_AGENT_SPECIAL, new CommandColorAgentsSpecial());
		for(int indexPolarity = 0; indexPolarity < COUNT_POLARITIES_MAXIMUM; indexPolarity++) {
			propertyCommands.put(PROPERTY_COLOR_POLARITY + (indexPolarity + 1), new CommandColorPolarity(indexPolarity));
		}
		propertyCommands.put(PROPERTY_EXPORT_POLARITIES_INTERVAL, new CommandExportPolaritiesInterval());
		propertyCommands.put(PROPERTY_EXPORT_POLARITIES_DIRECTORY, new CommandExportPolaritiesDirectory());
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT_INTERVAL, new CommandExportScreenshotInterval());
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, new CommandExportScreenshotDirectory());
		
		String directoryHome = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		String directorySwarm = "Swarm";
		pathFrequencies = Paths.get(directoryHome, File.separator, directorySwarm, File.separator, "Data");
		if(Files.notExists(pathFrequencies)) {
			pathFrequencies.toFile().mkdirs();
		}

		pathScreenshot = Paths.get(directoryHome, File.separator, directorySwarm, File.separator, "Captures");
		if(Files.notExists(pathScreenshot)) {
			pathScreenshot.toFile().mkdirs();
		}

		settings = new Properties();

		settings.setProperty(PROPERTY_EXPORT_POLARITIES_DIRECTORY, pathFrequencies.toString());
		settings.setProperty(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, pathScreenshot.toString());

		try {
			InputStream streamInput = getClass().getResourceAsStream("/settings/simulation.properties");
			settings.load(streamInput);
			streamInput.close();
		}
		catch (IOException exceptionInput) {
			exceptionInput.printStackTrace();
		}

		colorsPolarity = new Color[COUNT_POLARITIES_MAXIMUM];

		// Makes the top menu bar that has file and edit
		JMenuBar menuBar = new JMenuBar();
		frmProjectLegion.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Import Preferences");
		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(SELECTOR_FILEPATH.showOpenDialog(frmProjectLegion) == JFileChooser.APPROVE_OPTION) {
					try {
						FileInputStream streamInput = new FileInputStream(SELECTOR_FILEPATH.getSelectedFile());
						settings.load(streamInput);
						streamInput.close();

						applyProperties();
					}
					catch (IOException exceptionInput) {
						exceptionInput.printStackTrace();
					}
				}
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmClose = new JMenuItem("Export Preferences");
		mntmClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timestamp = FORMATTER_TIMESTAMP.format(LocalDateTime.now());
				SELECTOR_FILEPATH.setSelectedFile(new File("Simulation_" + timestamp + FILETYPE_PROPERTIES));
				if(SELECTOR_FILEPATH.showSaveDialog(frmProjectLegion) == JFileChooser.APPROVE_OPTION) {
					storeProperties(SELECTOR_FILEPATH.getSelectedFile());
				}
			}
		});
		mnFile.add(mntmClose);

		// ************************************************************ This makes the
		// 800 by 800 JPanel that will be where the board goes every time it is painted.
		JPanel boardInGUI = new JPanel();
		JFrame frame = new JFrame();
		boardInGUI.setBackground(Color.WHITE);
		boardInGUI.setBounds(10, (DIMENSION_HEIGHT_INITIAL - SIZE_BOARD_MAXIMUM) / 8, SIZE_BOARD_MAXIMUM, SIZE_BOARD_MAXIMUM);
		frame.getContentPane().add(boardInGUI);

		// This is where the tabs for the layer options go.
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(830, 10, 700, 970);
		// used to change whether board.cells or board.cells2 is shown in board when the
		// tab selected is changed
		ChangeListener changeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				layer2Draw = index + 1;

				if(board != null) {
					board.scheduleRepaint();
				}
			}
		};
		tabbedPane.addChangeListener(changeListener);
		frmProjectLegion.getContentPane().add(tabbedPane);

		// ************************************************************ TAB 1 **************************************************************
		// ************************************************************ TAB 1 **************************************************************
		JPanel tabLayer1 = new JPanel();
		tabLayer1.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Base Layer", null, tabLayer1, null);
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

		int countStates = CellDisplayBase.getMaximumStateCount();
		labelsFrequencyColorsInitialText = new JLabel[countStates];
		labelsFrequencyColorsInitial = new JLabel[countStates];
		labelsFrequencyColorsText = new JLabel[countStates];
		labelsFrequencyColors = new JLabel[countStates];
		String nameColor;
		for(int indexLabel = 0; indexLabel < countStates; indexLabel++) {
			nameColor = getColorName(CellDisplayBase.COLORS_BASE[indexLabel]);
			nameColor = nameColor.substring(0, 1) + nameColor.substring(1).toLowerCase();
			labelsFrequencyColorsInitialText[indexLabel] = new JLabel("Initial " + nameColor + " Cells:");	
			labelsFrequencyColorsInitialText[indexLabel].setBounds(10, 11 + (indexLabel * 34), 125, 35);
			labelsFrequencyColorsInitialText[indexLabel].setHorizontalAlignment(SwingConstants.CENTER);
			tabLayer1.add(labelsFrequencyColorsInitialText[indexLabel]);

			labelsFrequencyColorsInitial[indexLabel] = new JLabel();
			labelsFrequencyColorsInitial[indexLabel].setBounds(145, 11 + (indexLabel * 34), 125, 35);
			labelsFrequencyColorsInitial[indexLabel].setHorizontalAlignment(SwingConstants.CENTER);
			tabLayer1.add(labelsFrequencyColorsInitial[indexLabel]);

			labelsFrequencyColorsText[indexLabel] = new JLabel("Current " + nameColor + " Cells:");	
			labelsFrequencyColorsText[indexLabel].setBounds(10, 300 + (indexLabel * 34), 125, 35);
			labelsFrequencyColorsText[indexLabel].setHorizontalAlignment(SwingConstants.CENTER);
			tabLayer1.add(labelsFrequencyColorsText[indexLabel]);

			labelsFrequencyColors[indexLabel] = new JLabel();
			labelsFrequencyColors[indexLabel].setBounds(145, 300 + (indexLabel * 34), 125, 35);	
			labelsFrequencyColors[indexLabel].setHorizontalAlignment(SwingConstants.CENTER);
			tabLayer1.add(labelsFrequencyColors[indexLabel]);
		}

		JLabel lblBoardSizeLayer = new JLabel("Board Size:");
		lblBoardSizeLayer.setForeground(Color.LIGHT_GRAY);
		lblBoardSizeLayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoardSizeLayer.setBounds(10, 157, 125, 35);

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
				//Call method that will cause some cells to flip
				board.flipCells(Integer.parseInt(textField_TriggerFlip.getText()));
			}
		});
		btnTriggerFlipCells.setBounds(286, 225, 125, 35);
		tabLayer1.add(btnTriggerFlipCells);

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
		tabbedPane.addTab("Polarity Layer", null, tabLayer2, null);
		tabLayer2.setLayout(null);

		ItemListener listenerDropDownColorPolarity = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					JComboBox<String> menuDropDownSource = (JComboBox<String>)event.getSource();

					for(int indexPolarity = 0; indexPolarity < menusDropDownPolarity.length; indexPolarity++) {
						if(menusDropDownPolarity[indexPolarity].equals(menuDropDownSource)) {
							setPolarityColor(indexPolarity, getColor((String)menuDropDownSource.getSelectedItem()));
							return;
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
			menusDropDownPolarity[indexMenu] = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
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

		menuDropDownGoal = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_STRATEGIES_NAMES));
		menuDropDownGoal.setBounds(238, 200, 150, 22);
		menuDropDownGoal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameStrategy = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
				goalStrategy = getStrategy(nameStrategy);

				if(board != null) {
					board.updateGoalStrategy(goalStrategy);
				}

				settings.setProperty(PROPERTY_RULE_GOAL, nameStrategy);

				updatePolarityCount();
			}
		});
		tabLayer2.add(menuDropDownGoal);

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
		tglbtnRulesApply = new JToggleButton();
		tglbtnRulesApply.setBounds(450, 200, 150, 22);
		tglbtnRulesApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modeEquilibrium = tglbtnRulesApply.isSelected()) {
					tglbtnRulesApply.setText("Mode: Equilibrium");
				}
				else {
					tglbtnRulesApply.setText("Mode: Single");
				}

				settings.setProperty(PROPERTY_RULE_EQUILIBRIUM, Boolean.toString(modeEquilibrium));

				updateEquilibriumLabelsText();
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
		optionsPolarityDominant = new DefaultComboBoxModel<String>();
		menuDropDownPolarityDominant = new JComboBox<String>(optionsPolarityDominant);
		menuDropDownPolarityDominant.setBounds(25, 350, 125, 22);
		menuDropDownPolarityDominant.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					setDominantPolarity(((JComboBox<String>)e.getSource()).getSelectedIndex());
				}
			}
		});
		tabLayer2.add(menuDropDownPolarityDominant);

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
		labelsPolarityComparisonText = new JLabel[COUNT_POLARITIES_MAXIMUM];
		for(int indexLabel = 0; indexLabel < labelsPolarityComparisonText.length; indexLabel++) {
			labelsPolarityComparisonText[indexLabel] = new JLabel();
			labelsPolarityComparisonText[indexLabel].setHorizontalAlignment(SwingConstants.CENTER);
			labelsPolarityComparisonText[indexLabel].setBounds(300, 400 + (20 * indexLabel), 125, 35);
			tabLayer2.add(labelsPolarityComparisonText[indexLabel]);
		}

		labelsPolarityComparison = new JLabel[COUNT_POLARITIES_MAXIMUM];
		for(int indexLabel = 0; indexLabel < labelsPolarityComparison.length; indexLabel++) {
			labelsPolarityComparison[indexLabel] = new JLabel();
			labelsPolarityComparison[indexLabel].setVerticalAlignment(SwingConstants.CENTER);
			labelsPolarityComparison[indexLabel].setBounds(420, (405 + (20 * indexLabel)), 50, 20);
			tabLayer2.add(labelsPolarityComparison[indexLabel]);
		}

		// ************************************************************ TAB 3 *************************************************************
		// ************************************************************ TAB 3 *************************************************************
		JPanel tabLayer3 = new JPanel();
		tabLayer3.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Interactive Layer", null, tabLayer3, null);
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
		menuDropDownColorAgents = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
		menuDropDownColorAgents.setBounds(568, 23, 100, 20);
		menuDropDownColorAgents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameColor = (String)((JComboBox<String>)e.getSource()).getSelectedItem();

				if(board != null) {
					board.updateAgentColor(getColor(nameColor));
				}

				settings.setProperty(PROPERTY_COLOR_AGENT, nameColor);
			}
		});
		tabLayer3.add(menuDropDownColorAgents);

		//MODIFICATION: Special Agent Color
		//
		//Added 5/21 by Morgan Might
		//
		// User can change the color of the "special" agents
		JLabel lblSpecialAgentsColor = new JLabel("Special Agent Color:");
		lblSpecialAgentsColor.setBounds(440, 58, 125, 14);
		tabLayer3.add(lblSpecialAgentsColor);

		// update the color of the special agent in board.agents[]
		menuDropDownColorAgentsSpecial = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
		menuDropDownColorAgentsSpecial.setBounds(568, 56, 100, 20);
		menuDropDownColorAgentsSpecial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nameColor = (String)((JComboBox<String>)e.getSource()).getSelectedItem();

				if(board != null) {
					board.updateSpecialAgentColor(getColor(nameColor));
				}

				settings.setProperty(PROPERTY_COLOR_AGENT_SPECIAL, nameColor);
			}
		});
		tabLayer3.add(menuDropDownColorAgentsSpecial);

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

		JComboBox<String> comboBox_PheromoneColor = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
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

		tglbtnViewAgents = new JToggleButton("View Agents");
		tglbtnViewAgents.setBounds(10, 495, 187, 69);
		tglbtnViewAgents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				whetherAgentsVisible = tglbtnViewAgents.isSelected();

				if(board != null) {
					board.toggleAgentVisibility();
				}

				settings.setProperty(PROPERTY_AGENTS_VISIBLE, Boolean.toString(whetherAgentsVisible));
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
				}
				else {
					tglbtnAttractOrRepel.setText("Repel");
				}
			}
		});
		tabLayer3.add(tglbtnAttractOrRepel);

		tglbtnWrapAgents = new JToggleButton();
		tglbtnWrapAgents.setBounds(420, 495, 187, 69);
		tglbtnWrapAgents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(board != null) {
					board.toggleWraparound();
				}

				if (boardWraparound = tglbtnWrapAgents.isSelected()) {
					tglbtnWrapAgents.setText("Wrap Agents");
				}
				else {
					tglbtnWrapAgents.setText("Bounce Agents");
				}

				settings.setProperty(PROPERTY_BOARD_WRAPAROUND, Boolean.toString(boardWraparound));
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
		tabbedPane.addTab("Persistence Layer", tabLayer4);
		tabLayer4.setLayout(null);

		/* Tab 5 */
		JPanel tabLayerCorrectness = new JPanel();
		tabLayerCorrectness.setBackground(new Color(211, 211, 211));
		tabbedPane.addTab("Correctness Layer", tabLayerCorrectness);
		tabLayerCorrectness.setLayout(null);

		// ************************************************************ Global Zone
		// Buttons + Slider ************************************************************
		// ************************************************************ Just shows
		// information
		JLabel lblBoardSizeGlobal = new JLabel("Board Size:");
		lblBoardSizeGlobal.setBounds(10, 830, 80, 30);
		frmProjectLegion.getContentPane().add(lblBoardSizeGlobal);

		lblBoardSizeInt = new JLabel();
		lblBoardSizeInt.setBounds(100, 830, 20, 30);
		lblBoardSizeInt.setHorizontalAlignment(SwingConstants.RIGHT);
		frmProjectLegion.getContentPane().add(lblBoardSizeInt);

		JLabel lblSwarmCount = new JLabel("Swarm Size:");
		lblSwarmCount.setBounds(10, 900, 80, 30);
		frmProjectLegion.getContentPane().add(lblSwarmCount);

		lblSwarmCountInt = new JLabel();
		lblSwarmCountInt.setBounds(100, 900, 20, 30);
		lblSwarmCountInt.setHorizontalAlignment(SwingConstants.RIGHT);
		frmProjectLegion.getContentPane().add(lblSwarmCountInt);

		JLabel lblSwarmRate = new JLabel("Swarm Rate (Cycles per 2s)");
		lblSwarmRate.setBounds(340, 930, 470, 30);
		lblSwarmRate.setHorizontalAlignment(SwingConstants.CENTER);
		frmProjectLegion.getContentPane().add(lblSwarmRate);

		// ************************************************************ Slider for the
		// user to change how fast the board will step
		sliderSwarmSpeed = new JSlider(1, 2 * Board.RATE_STEPS_MAXIMUM);
		sliderSwarmSpeed.setBounds(340, 900, 470, 30);
		sliderSwarmSpeed.setMajorTickSpacing(13);
		sliderSwarmSpeed.setPaintLabels(true);

		// slider to change the speed of the agents in board.agents[]
		sliderSwarmSpeed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider src = (JSlider)e.getSource();
				agentSliderRate = src.getValue();
				if(board != null) {
					board.setAgentRate(agentSliderRate);
				}

				settings.setProperty(PROPERTY_AGENTS_RATE, Integer.toString(agentSliderRate));
			}
		});
		frmProjectLegion.getContentPane().add(sliderSwarmSpeed);

		JLabel lblSlow = new JLabel("Slow");
		lblSlow.setBounds(340, 880, 50, 20);
		frmProjectLegion.getContentPane().add(lblSlow);

		JLabel lblFast = new JLabel("Fast");
		lblFast.setBounds(760, 880, 50, 20);
		lblFast.setHorizontalAlignment(SwingConstants.RIGHT);
		frmProjectLegion.getContentPane().add(lblFast);

		// ************************************************************ Buttons that
		// start stop and do other things that they are clearly labeled for.
		// button to freeze swarm agents
		buttonSwarm = new JToggleButton();
		buttonSwarm.setBounds(150, 900, 160, 30);
		buttonSwarm.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						if(board != null) {
							board.toggleTimer();
						}

						if(timerStarted = buttonSwarm.isSelected()) {
							buttonSwarm.setText("Swarm: Active");
						}
						else {
							buttonSwarm.setText("Swarm: Inactive");
							buttonSwarm.setBackground(new Color(255, 200, 200));
						}

						settings.setProperty(PROPERTY_AGENTS_ACTIVE, Boolean.toString(timerStarted));
					}
				}
				);
		frmProjectLegion.getContentPane().add(buttonSwarm);

		// ************************************************************ This code will
		// open a new JFrame that will ask the user the new dimensions for the new
		// board.
		JButton btnNewScreenSave = new JButton("Screenshot");
		btnNewScreenSave.setBackground(new Color(240, 230, 235));
		btnNewScreenSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(board != null) {
					if(timerStarted) {
						board.toggleTimer();
					}

					BufferedImage capture = board.capture();

					SELECTOR_FILEPATH.setSelectedFile(new File("Simulation_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + FILETYPE_SCREENSHOT));
					if(SELECTOR_FILEPATH.showSaveDialog(board) == JFileChooser.APPROVE_OPTION) {
						try {
							ImageIO.write(capture, "JPG", SELECTOR_FILEPATH.getSelectedFile());
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}

					if(timerStarted) {
						board.toggleTimer();
					}
				}
			}
		});
		btnNewScreenSave.setBounds(610, 830, 200, 30);
		frmProjectLegion.getContentPane().add(btnNewScreenSave);
		
		JFrame frameOptions = new JFrame("Simulation Settings");
		frameOptions.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameOptions.setAlwaysOnTop(true);
		frameOptions.setLayout(new GridBagLayout());
		frameOptions.setMinimumSize(new Dimension(1000, 1000));
		
		HashMap<Component, TitledBorder> mapBordersTitled = new HashMap<Component, TitledBorder>();
		TitledBorder borderTitled;
		
		JPanel panelOptionsGeneral = new JPanel(new GridBagLayout());
		borderTitled = BorderFactory.createTitledBorder("General");
		mapBordersTitled.put(panelOptionsGeneral, borderTitled);
		panelOptionsGeneral.setBorder(BorderFactory.createCompoundBorder(borderTitled, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		GridBagConstraints constraints;
		
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.insets = new Insets(30, 30, 30, 30);
		
		frameOptions.add(panelOptionsGeneral, constraints);
		
		JLabel labelSizeBoard = new JLabel("Board Size:");
		JLabel labelCountAgents = new JLabel("Standard Agents:");
		JLabel labelCountAgentsSpecial = new JLabel("Special Agents:");
		JLabel labelStrategy = new JLabel("Strategy:");
		
		panelOptionsGeneral.add(labelStrategy);

		JTextField fieldSizeBoard = new JFormattedTextField(getIntegerFormatter(Board.BREADTH_MINIMUM));
		
		NumberFormatter formatterInteger = getIntegerFormatter(0);
		
		JTextField fieldCountAgents = new JFormattedTextField(formatterInteger);
		
		JTextField fieldCountAgentsSpecial = new JFormattedTextField(formatterInteger);
		
		JComboBox<String> menuDropDownStrategy = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_STRATEGIES_NAMES));
		
		JLabel[] labelsOptions = {labelSizeBoard, labelCountAgents, labelCountAgentsSpecial, labelStrategy};
		JComponent[] componentsOptions = {fieldSizeBoard, fieldCountAgents, fieldCountAgentsSpecial, menuDropDownStrategy};
		
		ComponentListener listenerFont = new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent event) {
				Container container = (Container)event.getComponent();
				Font font = new Font("sans-serif", Font.PLAIN, Math.min(container.getWidth(), container.getHeight()) / 20);
				
				if(mapBordersTitled.containsKey(container)) {
					mapBordersTitled.get(container).setTitleFont(font);
				}
				
				for(Component component : container.getComponents()) {
					component.setFont(font);
				}
			}

			@Override
			public void componentMoved(ComponentEvent event) {
				
			}

			@Override
			public void componentShown(ComponentEvent event) {
				
			}

			@Override
			public void componentHidden(ComponentEvent event) {
				
			}
		};
		
		panelOptionsGeneral.addComponentListener(listenerFont);
		
		JLabel label;
		JComponent component;
		for(int indexComponent = 0; indexComponent < componentsOptions.length; indexComponent++) {
			label = labelsOptions[indexComponent];
			component = componentsOptions[indexComponent];
			
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setLabelFor(component);
			
			constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = 0;
			constraints.weightx = 0.1;
			constraints.weighty = 1.0;
			
			panelOptionsGeneral.add(label, constraints);
			
			constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridy = indexComponent;
			constraints.weightx = 1.0;
			constraints.weighty = 1.0;
			constraints.insets = new Insets(20, 20, 20, 20);
			
			panelOptionsGeneral.add(component, constraints);
		}
		
		JPanel panelOptionsBatch = new JPanel(new GridBagLayout());
		panelOptionsBatch.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panelOptionsBatch.addComponentListener(listenerFont);
		
		GridBagConstraints constraintsOptionsBatch = new GridBagConstraints();
		constraintsOptionsBatch.fill = GridBagConstraints.BOTH;
		constraintsOptionsBatch.gridy = 3;
		constraintsOptionsBatch.weightx = 1.0;
		constraintsOptionsBatch.weighty = 1.0;
		constraintsOptionsBatch.insets = new Insets(0, 30, 30, 30);
		
		JLabel labelCountRepetitions = new JLabel("Repetitions:");
		JLabel labelCountSteps = new JLabel("Steps:");
		
		JFormattedTextField fieldCountRepetitions = new JFormattedTextField(getIntegerFormatter(1));
		JFormattedTextField fieldCountSteps = new JFormattedTextField(getIntegerFormatter(1));
		
		labelsOptions = new JLabel[]{labelCountRepetitions, labelCountSteps};
		componentsOptions = new JComponent[]{fieldCountRepetitions, fieldCountSteps};
		for(int indexComponent = 0; indexComponent < componentsOptions.length; indexComponent++) {
			label = labelsOptions[indexComponent];
			component = componentsOptions[indexComponent];
			
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			label.setLabelFor(component);
			
			constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = 0;
			constraints.weightx = 0.1;
			constraints.weighty = 1.0;
			
			panelOptionsBatch.add(label, constraints);
			
			constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridy = indexComponent;
			constraints.weightx = 1.0;
			constraints.weighty = 1.0;
			constraints.insets = new Insets(20, 20, 20, 20);
			
			panelOptionsBatch.add(component, constraints);
		}
		
		JToggleButton buttonAutomatic = new JToggleButton("Automatic Run");
		buttonAutomatic.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(buttonAutomatic.isSelected()) {
							frameOptions.add(panelOptionsBatch, constraintsOptionsBatch);
						}
						else {
							frameOptions.remove(panelOptionsBatch);
						}
						
						frameOptions.revalidate();
					}
				}
		);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 0.2;
		constraints.insets = new Insets(30, 30, 0, 30);
		frameOptions.add(buttonAutomatic, constraints);
		
		JButton buttonRun = new JButton("Run Simulation");
		buttonRun.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						sizeBoard = Integer.parseInt(fieldSizeBoard.getText());
						countAgents = Integer.parseInt(fieldCountAgents.getText());
						countAgentsSpecial = Integer.parseInt(fieldCountAgentsSpecial.getText());
						if(modeAutomatic = buttonAutomatic.isSelected()) {
							countRepetitionsMaximum = Integer.parseInt(fieldCountRepetitions.getText());
							countStepsMaximum = Integer.parseInt(fieldCountSteps.getText());
						}
						menuDropDownGoal.setSelectedIndex(menuDropDownStrategy.getSelectedIndex());
						
						frameOptions.setVisible(false);
						run();
					}
				}
		);
		
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 4;
		constraints.weightx = 1.0;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(30, 30, 30, 30);
		frameOptions.add(buttonRun, constraints);
		
		frameOptions.pack();
		
		// create new NewBoardWindow to make new board
		JButton btnNewBoard = new JButton("Prepare Simulation");
		btnNewBoard.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fieldSizeBoard.setText(Integer.toString(sizeBoard));
					fieldCountAgents.setText(Integer.toString(countAgents));
					fieldCountAgentsSpecial.setText(Integer.toString(countAgentsSpecial));
					fieldCountRepetitions.setText(Integer.toString(countRepetitionsMaximum));
					fieldCountSteps.setText(Integer.toString(countStepsMaximum));
					menuDropDownStrategy.setSelectedIndex(menuDropDownGoal.getSelectedIndex());
					buttonAutomatic.setSelected(!modeAutomatic);
					buttonAutomatic.doClick();
					frameOptions.setVisible(true);
				}
			}
		);
		btnNewBoard.setBackground(new Color(170, 240, 255));
		btnNewBoard.setBounds(150, 830, 430, 30);
		frmProjectLegion.getContentPane().add(btnNewBoard);

		applyProperties();

		settings = new Properties(settings);

		frequenciesPolaritiesAverage = new ArrayList<double[]>();
	}

	public boolean getAgentVisibility() {
		return whetherAgentsVisible;
	}

	public boolean getBoardWraparound() {
		return boardWraparound;
	}

	public int getBoardSize() {
		return sizeBoard;
	}

	public int getAgentCount() {
		return countAgents;
	}

	public int getSpecialAgentCount() {
		return countAgentsSpecial;
	}

	public int getDominantPolarity() {
		return indexPolarityDominant;
	}

	public Color[] getPolarityColors() {
		return colorsPolarity;
	}

	public void setBoard(Board board) {
		if(this.board != null) {
			if(timerStarted) {
				this.board.toggleTimer();
			}

			frmProjectLegion.remove(this.board);
		}

		this.board = board;

		this.frequencyColorsInitial = board.getInitialColorFrequencies();
		this.frequencyColors = board.getColorFrequencies();
		this.frequencyPolarities = board.getPolarityFrequencies();

		sizeBoard = board.numCellsOnSide;
		countAgents = board.getAgentCount();
		countAgentsSpecial = board.getSpecialAgentCount();

		String sizeBoardString = Integer.toString(sizeBoard);

		settings.setProperty(PROPERTY_BOARD_SIZE, sizeBoardString);
		settings.setProperty(PROPERTY_AGENTS_COUNT, Integer.toString(countAgents));
		settings.setProperty(PROPERTY_AGENTS_COUNT_SPECIAL, Integer.toString(countAgentsSpecial));

		lblBoardSizeInt.setText(sizeBoardString);
		lblSwarmCountInt.setText(Integer.toString(countAgents + countAgentsSpecial));

		board.setBackground(Color.WHITE);
		board.setBounds(10, 10, SIZE_BOARD_MAXIMUM, SIZE_BOARD_MAXIMUM);
		
		frmProjectLegion.getContentPane().add(board);
		board.scheduleRepaint();

		for(int indexLabel = 0; indexLabel < labelsFrequencyColorsInitial.length; indexLabel++) {
			labelsFrequencyColorsInitial[indexLabel].setText(Integer.toString(frequencyColorsInitial[indexLabel]));
		}
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
	//Added 5/22 by Morgan Might
	public void setSplitPolarity(boolean selected) {
		splitPolarity = selected;
	}

	//MODIFICATION
	//Added 5/22 by Morgan Might
	public boolean getSplitPolarity() {
		return splitPolarity;
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
		return modeEquilibrium;
	}

	public boolean getToggleAgents() {
		return timerStarted;
	}

	public int getAgentRate() {
		return agentSliderRate;
	}

	public Color getAgentColor() {
		return getColor((String)menuDropDownColorAgents.getSelectedItem());
	}

	public Color getSpecialAgentColor() {
		return getColor((String)menuDropDownColorAgentsSpecial.getSelectedItem());
	}

	public AbstractStrategy getStrategy() {
		return goalStrategy;
	}

	private void setPolarityColor(int indexPolarity, Color colorPolarity) {
		colorsPolarity[indexPolarity] = colorPolarity;

		if(optionsPolarityDominant.getSize() > indexPolarity) {
			optionsPolarityDominant.removeElementAt(indexPolarity);
			optionsPolarityDominant.insertElementAt("(" + (indexPolarity + 1) + ") " + getColorName(colorPolarity), indexPolarity);
			menuDropDownPolarityDominant.setSelectedIndex(indexPolarityDominant);
		}

		settings.setProperty(PROPERTY_COLOR_POLARITY + (indexPolarity + 1), getColorName(colorsPolarity[indexPolarity]));
	}

	private void updatePolarityCount() {
		int countPolarities = goalStrategy.getPolarityCount();
		int indexPolarityDominant = this.indexPolarityDominant;

		int countPolaritiesPrevious = optionsPolarityDominant.getSize();

		for(int indexPolarity = countPolaritiesPrevious; indexPolarity < countPolarities; indexPolarity++) {
			optionsPolarityDominant.addElement("(" + (indexPolarity + 1) + ") " + getColorName(colorsPolarity[indexPolarity]));
		}

		for(int indexPolarity = countPolarities; indexPolarity < countPolaritiesPrevious; indexPolarity++) {
			optionsPolarityDominant.removeElementAt(countPolarities);
		}

		if(indexPolarityDominant >= countPolarities) {
			indexPolarityDominant = countPolarities - 1;
		}

		if(indexPolarityDominant == this.indexPolarityDominant) {
			updateEquilibriumLabelsText();
		}
		else {
			menuDropDownPolarityDominant.setSelectedIndex(indexPolarityDominant);
		}

		tglbtnRulesApply.setVisible(countPolarities > 2);
	}

	//MODIFICATION #10
	//Added 7/18/2018
	//By Morgan Might
	//This method updates the label and goal of the constraints
	private void setDominantPolarity(int indexPolarityDominant) {
		this.indexPolarityDominant = indexPolarityDominant;
		settings.setProperty(PROPERTY_RULE_POLARITY_DOMINANT, Integer.toString(indexPolarityDominant + 1));

		if(board != null) {
			board.updateCorrectnessCells();
		}

		updateEquilibriumLabelsText();
	}

	private void updateEquilibriumLabelsText() {
		int countPolarities = goalStrategy.getPolarityCount();
		if(countPolarities > 2 && modeEquilibrium) {
			String namePolarityDominant = getColorName(colorsPolarity[indexPolarityDominant]);
			String comparisonPolarityDominantGreater = " < " + namePolarityDominant;
			StringBuilder comparisonPolarityDominantLesser = new StringBuilder();

			int indexPolarityOther = 0;
			String namePolarityOther;
			for(int indexLabel = 0; indexLabel < countPolarities - 1; indexLabel++) {
				if(indexPolarityOther == indexPolarityDominant) {
					indexPolarityOther++;
				}

				namePolarityOther = getColorName(colorsPolarity[indexPolarityOther]);
				labelsPolarityComparisonText[indexLabel].setText(namePolarityOther + comparisonPolarityDominantGreater + " :");
				labelsPolarityComparisonText[indexLabel].setVisible(true);

				comparisonPolarityDominantLesser.append(namePolarityOther.charAt(0)).append(" + ");

				indexPolarityOther++;
			}
			comparisonPolarityDominantLesser.replace(comparisonPolarityDominantLesser.length() - 2, comparisonPolarityDominantLesser.length(), "").append(" > ").append(namePolarityDominant).append(" :");
			labelsPolarityComparisonText[countPolarities - 1].setText(comparisonPolarityDominantLesser.toString());
			labelsPolarityComparisonText[countPolarities - 1].setVisible(true);
		}
		else {
			countPolarities = 0;
		}

		for(int indexLabel = countPolarities; indexLabel < labelsPolarityComparisonText.length; indexLabel++) {
			labelsPolarityComparisonText[indexLabel].setVisible(false);
		}
	}

	public void updateStepCountLabel(String count) {
		lblStepDisplay.setText(count);
	}

	public Board getBoard() {
		return board;
	}

	public void run() {
		for(indexRepetition = 0; indexRepetition < countRepetitionsMaximum; indexRepetition++) {
			setBoard(generateBoard());
		}

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(pathFrequencies + "\\Swarm_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + ".csv"));

			List<String> headersColumn = new ArrayList<String>(1 + COUNT_POLARITIES_MAXIMUM);

			headersColumn.add("Step");

			int indexPolarity;
			for(indexPolarity = 1; indexPolarity <= COUNT_POLARITIES_MAXIMUM; indexPolarity++) {
				headersColumn.add("Polarity " + indexPolarity);
			}

			writer.write(String.join(", ", headersColumn));
			writer.newLine();

			StringBuilder line;
			for(double[] frequencyPolaritiesAverage : frequenciesPolaritiesAverage) {
				line = new StringBuilder();

				for(indexPolarity = 0; indexPolarity < frequencyPolaritiesAverage.length - 1; indexPolarity++) {
					line.append(frequencyPolaritiesAverage[indexPolarity]);
					line.append(", ");
				}
				line.append(frequencyPolaritiesAverage[indexPolarity]);

				writer.append(line);
				writer.newLine();
			}

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Board generateBoard() {
		return new Board(SIZE_BOARD_MAXIMUM, SIZE_BOARD_MAXIMUM, sizeBoard, countAgents, countAgentsSpecial, this);
	}

	public void step(int indexStep) {
		if(intervalExportPolarities > 0 && indexStep % intervalExportPolarities == 0) {
			storePolarities(indexStep);
		}

		if(intervalExportScreenshot > 0 && indexStep % intervalExportScreenshot == 0) {
			if(timerStarted) {
				board.toggleTimer();
			}

			try {
				ImageIO.write(board.capture(), "JPG", new File(pathScreenshot + File.separator + "Simulation_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + FILETYPE_SCREENSHOT));
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			if(timerStarted) {
				board.toggleTimer();
			}
		}

		lblStepDisplay.setText(Integer.toString(indexStep));

		for(int indexLabel = 0; indexLabel < labelsFrequencyColors.length; indexLabel++) {
			labelsFrequencyColors[indexLabel].setText(Integer.toString(frequencyColors[indexLabel]));
		}

		for(int indexLabel = 0; indexLabel < labelsPercentPolarities.length; indexLabel++) {
			labelsPercentPolarities[indexLabel].setText((frequencyPolarities[indexLabel] / (board.getTotalNumCells() / 100d)) + "%");
		}

		int indexLabelComparisonSum = goalStrategy.getPolarityCount() - 1;
		int indexPolarity = 1;
		int frequencyPolarity;
		int frequencyPolarityDominant = frequencyPolarities[indexPolarityDominant];
		int frequencyPolaritiesSum = 0;
		for(int indexLabel = 0; indexLabel < indexLabelComparisonSum; indexLabel++) {
			if(indexPolarity == indexPolarityDominant) {
				indexPolarity++;
			}

			frequencyPolarity = frequencyPolarities[indexPolarity];
			labelsPolarityComparison[indexLabel].setText(Boolean.toString(frequencyPolarity < frequencyPolarityDominant));
			frequencyPolaritiesSum += frequencyPolarity;
		}

		JLabel labelComparisonSum = labelsPolarityComparison[indexLabelComparisonSum];
		if(labelComparisonSum.isVisible()) {
			labelsPolarityComparison[indexLabelComparisonSum].setText(Boolean.toString(frequencyPolarityDominant < frequencyPolaritiesSum));
		}
	}

	private void storeProperties(File fileOutput) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput));

			writer.write(HEADER_PROPERTIES);
			writer.newLine();

			for(String[] properties : PROPERTIES) {
				for(String property : properties) {
					writer.write(property + '=' + settings.getProperty(property));
					writer.newLine();
				}
			}
			writer.close();
		}
		catch (IOException exceptionOutput) {
			exceptionOutput.printStackTrace();
		}
	}

	private void applyProperties() {
		Properties settings = (Properties)this.settings.clone();
		for(String[] groupProperties : PROPERTIES) {
			for(String property : groupProperties) {
				if(propertyCommands.containsKey(property)) {
					propertyCommands.get(property).execute(settings.getProperty(property));
				}
				else {
					System.out.println("'" + property + "' is not a valid property.");
				}
			}
		}
	}

	private void storePolarities(int index) {
		double[] frequencyPolaritiesAverage = new double[COUNT_POLARITIES_MAXIMUM + 1];
		frequencyPolaritiesAverage[0] = board.getStepCount() + 1;

		if(index < frequenciesPolaritiesAverage.size()) {
			for(int indexPolarity = 1; indexPolarity < frequencyPolaritiesAverage.length; indexPolarity++) {
				frequencyPolaritiesAverage[indexPolarity] = (frequenciesPolaritiesAverage.get(index)[indexPolarity] * (indexRepetition - 1) + frequencyPolarities[indexPolarity - 1]) / indexRepetition;
			}

			frequenciesPolaritiesAverage.set(index, frequencyPolaritiesAverage);
		}
		else {
			for(int indexPolarity = 1; indexPolarity < frequencyPolaritiesAverage.length; indexPolarity++) {
				frequencyPolaritiesAverage[indexPolarity] = frequencyPolarities[indexPolarity - 1];
			}

			frequenciesPolaritiesAverage.add(frequencyPolaritiesAverage);
		}
	}
	
	private static NumberFormatter getIntegerFormatter(int minimum) {
		NumberFormat formatInteger = NumberFormat.getIntegerInstance();
		formatInteger.setGroupingUsed(false);
		
		NumberFormatter formatterInteger = new NumberFormatter(formatInteger);
		formatterInteger.setMinimum(minimum);
		return formatterInteger;
	}

	private static Color getColor(String nameColor) {
		nameColor = nameColor.toUpperCase();

		if(MAP_COLORS.containsKey(nameColor)) {
			return MAP_COLORS.get(nameColor);
		}

		StringBuilder messageError = new StringBuilder("Error: Invalid Color '");
		messageError.append(nameColor);
		messageError.append("' (Allowed Colors:");
		for(String nameColorKey : MAP_COLORS.keySet()) {
			messageError.append(' ');
			messageError.append(nameColorKey);
		}
		messageError.append(')');
		System.out.println(messageError.toString());

		return Color.LIGHT_GRAY;
	}

	private static String getColorName(Color color) {
		if(MAP_COLORS_NAMES.containsKey(color)) {
			return MAP_COLORS_NAMES.get(color);
		}

		return null;
	}

	private static AbstractStrategy getStrategy(String nameStrategy) {
		nameStrategy = nameStrategy.toUpperCase();

		if(MAP_STRATEGIES.containsKey(nameStrategy)) {
			return MAP_STRATEGIES.get(nameStrategy);
		}

		StringBuilder messageError = new StringBuilder("Error: Invalid Strategy '");
		messageError.append(nameStrategy);
		messageError.append("' (Allowed Strategies:");
		for(String nameStrategyKey : MAP_COLORS.keySet()) {
			messageError.append(' ');
			messageError.append(nameStrategyKey);
		}
		messageError.append(')');
		System.out.println(messageError.toString());

		return MAP_STRATEGIES.get("CHECKERBOARD");
	}

	private static String[] generateColorProperties() {
		String[] propertiesColors = new String[2 + COUNT_POLARITIES_MAXIMUM];

		propertiesColors[0] = PROPERTY_COLOR_AGENT;
		propertiesColors[1] = PROPERTY_COLOR_AGENT_SPECIAL;
		for(int indexProperty = 2; indexProperty < propertiesColors.length; indexProperty++) {
			propertiesColors[indexProperty] = PROPERTY_COLOR_POLARITY + (indexProperty - 1);
		}

		return propertiesColors;
	}

	private static HashMap<Color, String> generateMapColorsNames() {
		HashMap<Color, String> mapColorsNames = new HashMap<Color, String>();

		for(int indexColorName = 0; indexColorName < OPTIONS_COLORS_NAMES.length; indexColorName++) {
			mapColorsNames.put(OPTIONS_COLORS[indexColorName], OPTIONS_COLORS_NAMES[indexColorName]);
		}

		return mapColorsNames;
	}

	private static HashMap<String, Color> generateMapColors() {
		HashMap<String, Color> mapColors = new HashMap<String, Color>();

		for(int indexColor = 0; indexColor < OPTIONS_COLORS.length; indexColor++) {
			mapColors.put(OPTIONS_COLORS_NAMES[indexColor], OPTIONS_COLORS[indexColor]);
		}

		return mapColors;
	}

	private static HashMap<String, AbstractStrategy> generateMapStrategies() {
		HashMap<String, AbstractStrategy> mapStrategies = new HashMap<String, AbstractStrategy>();

		for(int indexStrategy = 0; indexStrategy < OPTIONS_STRATEGIES.length; indexStrategy++) {
			mapStrategies.put(OPTIONS_STRATEGIES_NAMES[indexStrategy], OPTIONS_STRATEGIES[indexStrategy]);
		}

		return mapStrategies;
	}

	private abstract class Command {
		public abstract void execute(String value);
	}

	private class CommandBoardSize extends Command {
		@Override
		public void execute(String value) {
			sizeBoard = Integer.parseInt(value);
		}
	}

	private class CommandBoardWraparound extends Command {
		@Override
		public void execute(String value) {
			tglbtnWrapAgents.setSelected(!Boolean.parseBoolean(value));
			tglbtnWrapAgents.doClick();
		}
	}

	private class CommandAgentCount extends Command {
		@Override
		public void execute(String value) {
			countAgents = Integer.parseInt(value);
		}
	}

	private class CommandAgentCountSpecial extends Command {
		@Override
		public void execute(String value) {
			countAgentsSpecial = Integer.parseInt(value);
		}
	}

	private class CommandAgentRate extends Command {
		@Override
		public void execute(String value) {
			sliderSwarmSpeed.setValue(Integer.parseInt(value));
		}
	}

	private class CommandAgentActive extends Command {
		@Override
		public void execute(String value) {
			buttonSwarm.setSelected(!Boolean.parseBoolean(value));
			buttonSwarm.doClick();
		}
	}

	private class CommandAgentVisible extends Command {
		@Override
		public void execute(String value) {
			tglbtnViewAgents.setSelected(!Boolean.parseBoolean(value));
			tglbtnViewAgents.doClick();
		}
	}

	private class CommandRuleGoal extends Command {
		@Override
		public void execute(String value) {
			menuDropDownGoal.setSelectedItem(value);
		}
	}

	private class CommandRuleDominantPolarity extends Command {
		@Override
		public void execute(String value) {
			int valueInteger = Math.min(Math.max(Integer.parseInt(value), 1), goalStrategy.getPolarityCount());

			menuDropDownPolarityDominant.setSelectedItem("(" + valueInteger + ") " + getColorName(colorsPolarity[valueInteger - 1]));
		}
	}

	private class CommandRuleEquilibrium extends Command {
		@Override
		public void execute(String value) {
			tglbtnRulesApply.setSelected(!Boolean.parseBoolean(value));
			tglbtnRulesApply.doClick();
		}
	}

	private class CommandRuleAutomatic extends Command {
		@Override
		public void execute(String value) {
			modeAutomatic = Boolean.parseBoolean(value);
		}
	}
	
	private class CommandRuleAutomaticRepetitions extends Command {
		@Override
		public void execute(String value) {
			countRepetitionsMaximum = Integer.parseInt(value);
		}
	}

	private class CommandRuleAutomaticSteps extends Command {
		@Override
		public void execute(String value) {
			countStepsMaximum = Integer.parseInt(value);
		}
	}

	private class CommandColorAgents extends Command {
		@Override
		public void execute(String value) {
			menuDropDownColorAgents.setSelectedItem(value);
		}
	}

	private class CommandColorAgentsSpecial extends Command {
		@Override
		public void execute(String value) {
			menuDropDownColorAgentsSpecial.setSelectedItem(value);
		}
	}

	private class CommandColorPolarity extends Command {
		private int indexPolarity;

		public CommandColorPolarity(int indexPolarity) {
			this.indexPolarity = indexPolarity;
		}

		@Override
		public void execute(String value) {
			menusDropDownPolarity[indexPolarity].setSelectedItem(value);
		}
	}

	private class CommandExportPolaritiesInterval extends Command {
		@Override
		public void execute(String value) {
			intervalExportPolarities = Integer.parseInt(value);
		}
	}

	private class CommandExportPolaritiesDirectory extends Command {
		@Override
		public void execute(String value) {
			pathFrequencies = Paths.get(value);
		}
	}

	private class CommandExportScreenshotInterval extends Command {
		@Override
		public void execute(String value) {
			intervalExportScreenshot = Integer.parseInt(value);
		}
	}

	private class CommandExportScreenshotDirectory extends Command {
		@Override
		public void execute(String value) {
			pathScreenshot = Paths.get(value);
		}
	}
}