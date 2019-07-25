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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
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
	public static final int COUNT_POLARITIES_MAXIMUM = 4;
	public static final int RATE_STEPS_MAXIMUM = 85;
	private static final String TITLE = "Project Legion";
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
	private static final String PROPERTY_EXPORT_DATA = "export.polarities";
	private static final String PROPERTY_EXPORT_DATA_INTERVAL = "export.polarities.interval";
	private static final String PROPERTY_EXPORT_DATA_DIRECTORY = "export.polarities.directory";
	private static final String PROPERTY_EXPORT_SCREENSHOT = "export.screenshot";
	private static final String PROPERTY_EXPORT_SCREENSHOT_INTERVAL = "export.screenshot.interval";
	private static final String PROPERTY_EXPORT_SCREENSHOT_DIRECTORY = "export.screenshot.directory";
	private static final Color COLOR_ACCENT = new Color(211, 211, 211);
	private static final Insets INSETS_NONE = new Insets(0, 0, 0, 0);
	private static final Insets INSETS_40 = new Insets(40, 40, 40, 40);
	private static final Insets INSETS_EDGES = new Insets(10, 20, 10, 20);
	private static final DateTimeFormatter FORMATTER_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
	private static final JFileChooser SELECTOR_FILEPATH = new JFileChooser();
	private static final Color[] OPTIONS_COLORS = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	private static final String[] OPTIONS_COLORS_NAMES = {"BLACK", "BLUE", "CYAN", "GRAY", "GREEN", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW"};
	private static final String[] OPTIONS_STRATEGIES_NAMES = {"BLACKOUT", "CHECKERBOARD", "DIAGONALS", "LINES"};
	private static final String[] PROPERTIES_BOARD = {PROPERTY_BOARD_SIZE, PROPERTY_BOARD_WRAPAROUND};
	private static final String[] PROPERTIES_AGENTS = {PROPERTY_AGENTS_COUNT, PROPERTY_AGENTS_COUNT_SPECIAL, PROPERTY_AGENTS_RATE, PROPERTY_AGENTS_ACTIVE, PROPERTY_AGENTS_VISIBLE};
	private static final String[] PROPERTIES_RULES = {PROPERTY_RULE_GOAL, PROPERTY_RULE_POLARITY_DOMINANT, PROPERTY_RULE_EQUILIBRIUM, PROPERTY_RULE_AUTOMATIC, PROPERTY_RULE_AUTOMATIC_REPETITITONS, PROPERTY_RULE_AUTOMATIC_STEPS};
	private static final String[] PROPERTIES_COLORS = generateColorProperties();
	private static final String[] PROPERTIES_EXPORT = {PROPERTY_EXPORT_DATA, PROPERTY_EXPORT_DATA_INTERVAL, PROPERTY_EXPORT_DATA_DIRECTORY, PROPERTY_EXPORT_SCREENSHOT, PROPERTY_EXPORT_SCREENSHOT_INTERVAL, PROPERTY_EXPORT_SCREENSHOT_DIRECTORY};
	private static final AbstractStrategy[] OPTIONS_STRATEGIES = {new AllBlack(), new CheckerBoard(), new DiagonalLines(), new Lines()};
	private static final String[][] PROPERTIES = {PROPERTIES_BOARD, PROPERTIES_AGENTS, PROPERTIES_RULES, PROPERTIES_COLORS, PROPERTIES_EXPORT};
	private static final HashMap<Color, String> MAP_COLORS_NAMES = generateMapColorsNames();
	private static final HashMap<String, Color> MAP_COLORS = generateMapColors();
	private static final HashMap<String, AbstractStrategy> MAP_STRATEGIES = generateMapStrategies();

	public boolean splitPolarity; //MODIFICATION: if true set the board to be "stuck"
	private boolean timerReady;// timer or agent step
	private boolean timerActive;
	public boolean modeAttractor = true;
	private boolean boardWraparound;
	private boolean modeEquilibrium; //MODIFICATION #5 determines if the agents goal is a single polarity or three balanced polarities
	private boolean modeAutomatic;
	private boolean whetherAgentsVisible;
	public boolean threePol; //MODIFICATION #3   needs fixed
	private boolean exportData, exportScreenshot;
	public int layer2Draw = 1;// which cell array in board to display
	private int indexPolarityDominant;
	private int sizeBoard;
	private int indexRepetition;
	private int countRepetitionsMaximum;
	private int indexStep;
	private int countStepsMaximum;
	private int countAgents, countAgentsSpecial;
	private int intervalExportData;
	private int intervalExportScreenshot;
	private long rateExecute;
	private Path pathData, pathScreenshot;
	private Properties settings;
	private Timer timer;
	private TimerTask task;
	private AbstractStrategy goalStrategy;
	private Board board;// board to be drawn
	private BufferedWriter writerData;
	public JFrame frmProjectLegion;// main frame
	private JTextField fieldCountAgents;
	private JTextField fieldCountModifications;
	private JTextField fieldAgentCloseness;
	private JTextField fieldStrengthPheromone;
	private JTextField fieldSizeBoard;
	private JTextField fieldFlip;
	private JLabel labelSizeBoardValue; // updates BoardSize Label
	private JLabel labelCountAgentsValue; // updates SwarmCount Label
	private JLabel labelCountStepsValue; //displays the number of steps that have occurred
	private JToggleButton buttonAgentVisiblity, buttonWraparound, buttonModeEquilibrium, buttonSwarmActive;
	private JSlider sliderRateSwarm;
	private JPanel panelBoard;
	private DefaultComboBoxModel<String> optionsPolarityDominant;
	private JComboBox<String> menuDropDownGoal, menuDropDownPolarityDominant, menuDropDownColorAgents, menuDropDownColorAgentsSpecial;
	private int[] frequencyColorsInitial;
	private int[] frequencyColors;
	private int[] frequencyPolarities;
	private JLabel[] labelsFrequencyColorsInitialText;
	private JLabel[] labelsFrequencyColorsText;
	private JLabel[] labelsPolaritiesPercentText;
	private JLabel[] labelsFrequencyColorsInitial;
	private JLabel[] labelsFrequencyColors;
	private JLabel[] labelsPolaritiesPercent;
	private JLabel[] labelsPolarityComparison;
	private JLabel[] labelsPolarityComparisonText;
	private Color[] colorsPolarity;
	private ArrayList<double[]> frequenciesPolaritiesAverage;
	private JComboBox<String>[] menusDropDownColorsPolarity;
	private HashMap<Object, Command> propertyCommands;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					try {
						GUI window = new GUI();
						window.frmProjectLegion.setVisible(true);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		);
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		colorsPolarity = new Color[COUNT_POLARITIES_MAXIMUM];

		timer = new Timer();
		timerReady = false;
		timerActive = false;
		indexStep = 0;

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
		propertyCommands.put(PROPERTY_EXPORT_DATA, new CommandExportPolarities());
		propertyCommands.put(PROPERTY_EXPORT_DATA_INTERVAL, new CommandExportPolaritiesInterval());
		propertyCommands.put(PROPERTY_EXPORT_DATA_DIRECTORY, new CommandExportPolaritiesDirectory());
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT, new CommandExportScreenshot());
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT_INTERVAL, new CommandExportScreenshotInterval());
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, new CommandExportScreenshotDirectory());

		String directoryHome = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		String directorySwarm = "Swarm";

		pathData = Paths.get(directoryHome, File.separator, directorySwarm, File.separator, "Data");
		pathScreenshot = Paths.get(directoryHome, File.separator, directorySwarm, File.separator, "Captures");

		settings = new Properties();
		settings.setProperty(PROPERTY_EXPORT_DATA_DIRECTORY, pathData.toString());
		settings.setProperty(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, pathScreenshot.toString());

		try {
			InputStream streamInput = getClass().getResourceAsStream("/settings/simulation.properties");
			settings.load(streamInput);
			streamInput.close();
		}
		catch (IOException exceptionInput) {
			exceptionInput.printStackTrace();
		}

		ComponentAdapter listenerFonts = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent event) {
				scaleComponentFonts(event);
			}

			@Override
			public void componentMoved(ComponentEvent event) {
				scaleComponentFonts(event);
			}

			@Override
			public void componentShown(ComponentEvent event) {
				scaleComponentFonts(event);
			}

			private void setComponentFonts(Container container, int sizeFont) {
				Font font = new Font("sans-serif", Font.PLAIN, sizeFont);

				for(Component component : container.getComponents()) {
					if(component instanceof Container) {
						setComponentFonts((Container)component, (int)(sizeFont * 0.84));
					}

					component.setFont(font);
				}
			}

			private void scaleComponentFonts(ComponentEvent event) {
				Container container = (Container)event.getComponent();
				setComponentFonts(container, Math.min(container.getWidth(), container.getHeight()) / 34);
			}
		};

		frmProjectLegion = new JFrame(TITLE);
		frmProjectLegion.setLayout(new GridBagLayout());
		frmProjectLegion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmProjectLegion.addWindowListener(
			new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent event) {
					if(timerActive) {
						toggleTimer();
					}

					if(writerData != null) {
						try {
							writerData.close();

							if(modeAutomatic) {
								outputAveragePolarityData();
							}
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}

					event.getWindow().dispose();
					System.exit(0);
				}
			}
		);

		// Makes the top menu bar that has file and edit
		JMenuBar menuBar = new JMenuBar();

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Import Preferences");
		mntmOpen.addActionListener(
			new ActionListener() {
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
			}
		);
		mnFile.add(mntmOpen);

		JMenuItem mntmClose = new JMenuItem("Export Preferences");
		mntmClose.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String timestamp = FORMATTER_TIMESTAMP.format(LocalDateTime.now());
					SELECTOR_FILEPATH.setSelectedFile(new File("Simulation_" + timestamp + FILETYPE_PROPERTIES));
					if(SELECTOR_FILEPATH.showSaveDialog(frmProjectLegion) == JFileChooser.APPROVE_OPTION) {
						outputProperties(SELECTOR_FILEPATH.getSelectedFile());
					}
				}
			}
		);
		mnFile.add(mntmClose);

		frmProjectLegion.add(menuBar, createConstraints(0, 0, 1, 1.0, 0.06, INSETS_NONE));

		JSplitPane paneSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		paneSplit.setResizeWeight(0.4);

		// ************************************************************ Global Zone
		// Buttons + Slider ************************************************************
		// ************************************************************ Just shows
		// information
		JPanel panelWorkbench = new JPanel();
		panelWorkbench.setMinimumSize(new Dimension(400, 0));
		panelWorkbench.setLayout(new GridBagLayout());
		panelWorkbench.addComponentListener(listenerFonts);
				
		JPanel panelBoardFrame = new JPanel();
		panelBoardFrame.setBackground(COLOR_ACCENT);
		panelBoardFrame.setLayout(new GridBagLayout());
		
		panelBoard = new JPanel();
		panelBoard.setBackground(COLOR_ACCENT);
		panelBoard.setLayout(new GridBagLayout());
		panelBoard.addComponentListener(
			new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent event) {
					if(board != null) {
						int sizeBoard = Math.min(panelBoard.getWidth(), panelBoard.getHeight());
						board.setPreferredSize(new Dimension(sizeBoard, sizeBoard));
					}
				}
			}
		);
		
		panelBoardFrame.add(panelBoard, createConstraints(0, 0, 1, 1.0, 1.0, INSETS_40));
				
		panelWorkbench.add(panelBoardFrame, createConstraints(0, 0, 1, 1.0, 1.0, INSETS_40));
				
		JPanel panelWorkbenchInformation = new JPanel();
		panelWorkbenchInformation.setLayout(new GridBagLayout());
				
		GridBagConstraints constraintsInformationLabel = createConstraints(0, 0, 1, 0.0, 1.0, INSETS_NONE);
		GridBagConstraints constraintsInformation = createConstraints(1, 0, 1, 0.0, 1.0, INSETS_EDGES);
		JLabel[][] labelsInformation = new JLabel[][]{{new JLabel("Board Size:"), new JLabel("Swarm Size:"), new JLabel("Step:")}, {labelSizeBoardValue = new JLabel("0"), labelCountAgentsValue = new JLabel("0"), labelCountStepsValue = new JLabel("0")}};
		for(int indexLabel = 0; indexLabel < labelsInformation[0].length; indexLabel++) {
			constraintsInformation.gridx = 2 * indexLabel + 1;
			labelsInformation[1][indexLabel].setHorizontalAlignment(SwingConstants.LEFT);
			panelWorkbenchInformation.add(labelsInformation[1][indexLabel], constraintsInformation);
			
			constraintsInformationLabel.gridx = 2 * indexLabel;
			labelsInformation[0][indexLabel].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsInformation[0][indexLabel].setLabelFor(labelsInformation[1][indexLabel]);
			panelWorkbenchInformation.add(labelsInformation[0][indexLabel], constraintsInformationLabel);
		}
				
		panelWorkbench.add(panelWorkbenchInformation, createConstraints(0, 1, 1, 1.0, 0.02, INSETS_EDGES));
				
		JPanel panelWorkbenchBoard = new JPanel();
		panelWorkbenchBoard.setLayout(new GridBagLayout());
				
		JFrame frameOptions = new JFrame("Simulation Settings");
		frameOptions.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameOptions.setAlwaysOnTop(true);
		frameOptions.setLayout(new GridBagLayout());
		frameOptions.setMinimumSize(new Dimension(1000, 1000));
		frameOptions.getContentPane().addComponentListener(listenerFonts);

		GridBagConstraints constraintsHeader = new GridBagConstraints();
		constraintsHeader.fill = GridBagConstraints.BOTH;
		constraintsHeader.gridx = 0;
		constraintsHeader.weightx = 1.0;
		constraintsHeader.weighty = 0.2;
		constraintsHeader.insets = new Insets(24, 20, 0, 20);

		GridBagConstraints constraintsHeaderInternal = (GridBagConstraints)constraintsHeader.clone();
		constraintsHeaderInternal.insets = new Insets(12, 8, 0, 8);

		GridBagConstraints constraintsPanel = new GridBagConstraints();
		constraintsPanel.fill = GridBagConstraints.BOTH;
		constraintsPanel.gridx = 0;
		constraintsPanel.weightx = 1.0;
		constraintsPanel.weighty = 1.0;
		constraintsPanel.insets = new Insets(0, 20, 0, 20);

		GridBagConstraints constraintsPanelInternal = (GridBagConstraints)constraintsPanel.clone();
		constraintsPanelInternal.insets = new Insets(0, 8, 0, 8);

		GridBagConstraints constraintsLabel = new GridBagConstraints();
		constraintsLabel.fill = GridBagConstraints.BOTH;
		constraintsLabel.gridx = 0;
		constraintsLabel.weightx = 0.03;
		constraintsLabel.weighty = 1.0;

		GridBagConstraints constraintsField = new GridBagConstraints();
		constraintsField.fill = GridBagConstraints.BOTH;
		constraintsField.gridx = 1;
		constraintsField.weightx = 1.0;
		constraintsField.weighty = 1.0;
		constraintsField.insets = new Insets(10, 10, 10, 10);

		GridBagConstraints constraintsButton = new GridBagConstraints();
		constraintsButton.fill = GridBagConstraints.BOTH;
		constraintsButton.gridy = 10;
		constraintsButton.weightx = 1.0;
		constraintsButton.weighty = 0.5;
		constraintsButton.insets = new Insets(30, 20, 20, 20);

		JToggleButton headerOptionsGeneral = new JToggleButton("General");
		headerOptionsGeneral.setSelected(true);
		headerOptionsGeneral.setEnabled(false);
		headerOptionsGeneral.setHorizontalAlignment(SwingConstants.LEADING);

		frameOptions.add(headerOptionsGeneral, constraintsHeader);

		Border borderPadding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border borderLined = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 3), borderPadding);

		JPanel panelOptionsGeneral = new JPanel(new GridBagLayout());
		panelOptionsGeneral.setBorder(borderLined);

		frameOptions.add(panelOptionsGeneral, constraintsPanel);

		JLabel labelOptionsSizeBoard = new JLabel("Board Size:");
		JLabel labelOptionsCountAgents = new JLabel("Standard Agents:");
		JLabel labelOptionsCountAgentsSpecial = new JLabel("Special Agents:");
		JLabel labelOptionsStrategy = new JLabel("Strategy:");

		JTextField fieldOptionsSizeBoard = new JFormattedTextField(getIntegerFormatter(Board.BREADTH_MINIMUM));

		NumberFormatter formatterInteger = getIntegerFormatter(0);

		JTextField fieldOptionsCountAgents = new JFormattedTextField(formatterInteger);
		JTextField fieldOptionsCountAgentsSpecial = new JFormattedTextField(formatterInteger);

		JComboBox<String> menuDropDownOptionsStrategy = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_STRATEGIES_NAMES));
		menuDropDownOptionsStrategy.setBackground(fieldOptionsSizeBoard.getBackground());

		JPanel panelOptionsAutomatic = new JPanel(new GridBagLayout());
		panelOptionsAutomatic.setBorder(borderLined);

		JLabel labelOptionsCountRepetitions = new JLabel("Repetitions:");
		JLabel labelOptionsCountSteps = new JLabel("Steps:");

		JTextField fieldOptionsCountRepetitions = new JFormattedTextField(getIntegerFormatter(1));
		JTextField fieldOptionsCountSteps = new JFormattedTextField(getIntegerFormatter(1));

		Color colorDisabled = new Color(255, 200, 200);

		JToggleButton headerOptionsAutomatic = new JToggleButton("Automatic Run");
		headerOptionsAutomatic.setBackground(colorDisabled);
		headerOptionsAutomatic.setHorizontalAlignment(SwingConstants.LEADING);
		headerOptionsAutomatic.addActionListener(new ActionListenerVisibility(panelOptionsAutomatic));

		frameOptions.add(headerOptionsAutomatic, constraintsHeader);
		frameOptions.add(panelOptionsAutomatic, constraintsPanel);

		JPanel panelOptionsExport = new JPanel(new GridBagLayout());
		panelOptionsExport.setBorder(borderLined);

		JPanel panelOptionsExportData = new JPanel(new GridBagLayout());
		panelOptionsExportData.setBorder(borderLined);

		JLabel labelOptionsExportDataInterval = new JLabel("Interval:");
		JLabel labelOptionsExportDataPath = new JLabel("Path:");

		JTextField fieldOptionsExportDataInterval = new JFormattedTextField(getIntegerFormatter(1));
		JTextField fieldOptionsExportDataPath = new JTextField();

		JToggleButton headerOptionsExportData = new JToggleButton("Data");
		headerOptionsExportData.setBackground(colorDisabled);
		headerOptionsExportData.setHorizontalAlignment(SwingConstants.LEADING);
		headerOptionsExportData.addActionListener(new ActionListenerVisibility(panelOptionsExportData));

		panelOptionsExport.add(headerOptionsExportData, constraintsHeaderInternal);
		panelOptionsExport.add(panelOptionsExportData, constraintsPanelInternal);

		JPanel panelOptionsExportScreenshot = new JPanel(new GridBagLayout());
		panelOptionsExportScreenshot.setBorder(borderLined);

		JLabel labelOptionsExportScreenshotInterval = new JLabel("Interval:");
		JLabel labelOptionsExportScreenshotPath = new JLabel("Path:");

		JTextField fieldOptionsExportScreenshotInterval = new JFormattedTextField(getIntegerFormatter(1));
		JTextField fieldOptionsExportScreenshotPath = new JTextField();

		JToggleButton headerOptionsExportScreenshot = new JToggleButton("Screenshot");
		headerOptionsExportScreenshot.setBackground(colorDisabled);
		headerOptionsExportScreenshot.setHorizontalAlignment(SwingConstants.LEADING);
		headerOptionsExportScreenshot.addActionListener(new ActionListenerVisibility(panelOptionsExportScreenshot));

		panelOptionsExport.add(headerOptionsExportScreenshot, constraintsHeaderInternal);
		panelOptionsExport.add(panelOptionsExportScreenshot, constraintsPanelInternal);

		JToggleButton headerOptionsExport = new JToggleButton("Export");
		headerOptionsExport.setBackground(colorDisabled);
		headerOptionsExport.setHorizontalAlignment(SwingConstants.LEADING);
		headerOptionsExport.addActionListener(new ActionListenerVisibility(panelOptionsExport));

		frameOptions.add(headerOptionsExport, constraintsHeader);
		frameOptions.add(panelOptionsExport, constraintsPanel);

		JPanel[] panelsOptions = {panelOptionsGeneral, panelOptionsAutomatic, panelOptionsExportData, panelOptionsExportScreenshot};
		JLabel[][] labelsOptions = {{labelOptionsSizeBoard, labelOptionsCountAgents, labelOptionsCountAgentsSpecial, labelOptionsStrategy}, {labelOptionsCountRepetitions, labelOptionsCountSteps}, {labelOptionsExportDataInterval, labelOptionsExportDataPath}, {labelOptionsExportScreenshotInterval, labelOptionsExportScreenshotPath}};
		Component[][] componentsOptions = {{fieldOptionsSizeBoard, fieldOptionsCountAgents, fieldOptionsCountAgentsSpecial, menuDropDownOptionsStrategy}, {fieldOptionsCountRepetitions, fieldOptionsCountSteps}, {fieldOptionsExportDataInterval, fieldOptionsExportDataPath}, {fieldOptionsExportScreenshotInterval, fieldOptionsExportScreenshotPath}};

		JPanel panel;
		JLabel label;
		Component component;

		for(int indexPanel = 0; indexPanel < panelsOptions.length; indexPanel++) {
			panel = panelsOptions[indexPanel];

			for(int indexComponent = 0; indexComponent < componentsOptions[indexPanel].length; indexComponent++) {
				component = componentsOptions[indexPanel][indexComponent];

				if(indexComponent < labelsOptions[indexPanel].length) {
					label = labelsOptions[indexPanel][indexComponent];

					label.setHorizontalAlignment(SwingConstants.RIGHT);
					label.setLabelFor(component);

					panel.add(label, constraintsLabel);
				}

				constraintsField.gridy = indexComponent;
				panel.add(component, constraintsField);
			}
		}

		JButton buttonOptionsRun = new JButton("Run Simulation");
		buttonOptionsRun.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					sizeBoard = Integer.parseInt(fieldOptionsSizeBoard.getText());
					countAgents = Integer.parseInt(fieldOptionsCountAgents.getText());
					countAgentsSpecial = Integer.parseInt(fieldOptionsCountAgentsSpecial.getText());
					menuDropDownGoal.setSelectedIndex(menuDropDownOptionsStrategy.getSelectedIndex());

					if(modeAutomatic = headerOptionsAutomatic.isSelected()) {
						String countRepetitionsMaximumString = fieldOptionsCountRepetitions.getText();
						countRepetitionsMaximum = Integer.parseInt(countRepetitionsMaximumString);
						settings.setProperty(PROPERTY_RULE_AUTOMATIC_REPETITITONS, countRepetitionsMaximumString);

						String countStepsMaximumString = fieldOptionsCountSteps.getText();
						countStepsMaximum = Integer.parseInt(countStepsMaximumString);
						settings.setProperty(PROPERTY_RULE_AUTOMATIC_STEPS, countStepsMaximumString);
					}
					settings.setProperty(PROPERTY_RULE_AUTOMATIC, Boolean.toString(modeAutomatic));

					if(exportData = (headerOptionsExport.isSelected() && headerOptionsExportData.isSelected())) {
						String intervalExportDataString = fieldOptionsExportDataInterval.getText();
						intervalExportData = Integer.parseInt(intervalExportDataString);
						settings.setProperty(PROPERTY_EXPORT_DATA_INTERVAL, intervalExportDataString);

						String pathDataString = fieldOptionsExportDataPath.getText();
						pathData = Paths.get(fieldOptionsExportDataPath.getText());
						pathData.toFile().mkdirs();
						settings.setProperty(PROPERTY_EXPORT_DATA_DIRECTORY, pathDataString);
					}
					settings.setProperty(PROPERTY_EXPORT_DATA, Boolean.toString(exportData));

					if(exportScreenshot = (headerOptionsExport.isSelected() && headerOptionsExportScreenshot.isSelected())) {
						String intervalExportScreenshotString = fieldOptionsExportScreenshotInterval.getText();
						intervalExportScreenshot = Integer.parseInt(intervalExportScreenshotString);
						settings.setProperty(PROPERTY_EXPORT_SCREENSHOT_INTERVAL, intervalExportScreenshotString);

						String pathScreenshotString = fieldOptionsExportScreenshotPath.getText();
						pathScreenshot = Paths.get(pathScreenshotString);
						pathScreenshot.toFile().mkdirs();
						settings.setProperty(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, pathScreenshotString);
					}
					settings.setProperty(PROPERTY_EXPORT_SCREENSHOT, Boolean.toString(exportScreenshot));

					frameOptions.setVisible(false);

					setBoard(generateBoard());
				}
			}
		);

		frameOptions.add(buttonOptionsRun, constraintsButton);

		frameOptions.pack();

		// create new NewBoardWindow to make new board
		JButton buttonSetup = new JButton("Prepare Simulation");
		buttonSetup.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fieldOptionsSizeBoard.setText(Integer.toString(sizeBoard));
					fieldOptionsCountAgents.setText(Integer.toString(countAgents));
					fieldOptionsCountAgentsSpecial.setText(Integer.toString(countAgentsSpecial));
							
					fieldOptionsCountRepetitions.setText(Integer.toString(countRepetitionsMaximum));
					fieldOptionsCountSteps.setText(Integer.toString(countStepsMaximum));
							
					fieldOptionsExportDataInterval.setText(Integer.toString(intervalExportData));
					fieldOptionsExportDataPath.setText(pathData.toString());
					fieldOptionsExportScreenshotInterval.setText(Integer.toString(intervalExportScreenshot));
					fieldOptionsExportScreenshotPath.setText(pathScreenshot.toString());
					
					menuDropDownOptionsStrategy.setSelectedIndex(menuDropDownGoal.getSelectedIndex());
						
					headerOptionsAutomatic.setSelected(!modeAutomatic);
					headerOptionsAutomatic.doClick();
							
					headerOptionsExport.setSelected(!(exportData || exportScreenshot));
					headerOptionsExport.doClick();
							
					headerOptionsExportData.setSelected(!exportData);
					headerOptionsExportData.doClick();
					
					headerOptionsExportScreenshot.setSelected(!exportScreenshot);
					headerOptionsExportScreenshot.doClick();
					
					frameOptions.setVisible(true);
				}
			}
		);
		buttonSetup.setBackground(new Color(170, 240, 255));
		panelWorkbenchBoard.add(buttonSetup, createConstraints(0, 0, 1, 1.0, 1.0, INSETS_EDGES));

		// ************************************************************ This code will
		// open a new JFrame that will ask the user the new dimensions for the new
		// board.
		JButton buttonScreenshot = new JButton("Screenshot");
		buttonScreenshot.setBackground(new Color(240, 230, 235));
		buttonScreenshot.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(board != null) {
						if(timerReady) {
							toggleTimer();
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

						if(timerReady) {
							toggleTimer();
						}
					}
				}
			}
		);
		panelWorkbenchBoard.add(buttonScreenshot, createConstraints(1, 0, 1, 0.44, 1.0, INSETS_EDGES));
				
		panelWorkbench.add(panelWorkbenchBoard, createConstraints(0, 2, 1, 1.0, 0.06, INSETS_EDGES));
				
		JPanel panelWorkbenchSwarm = new JPanel();
		panelWorkbenchSwarm.setLayout(new GridBagLayout());
				
		// button to freeze swarm agents
		buttonSwarmActive = new JToggleButton();
		buttonSwarmActive.setBackground(colorDisabled);
		buttonSwarmActive.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					if(timerReady = buttonSwarmActive.isSelected()) {
						buttonSwarmActive.setText(" Swarm: Active ");
					}
					else {
						buttonSwarmActive.setText("Swarm: Inactive");
					}

					toggleTimer();

					settings.setProperty(PROPERTY_AGENTS_ACTIVE, Boolean.toString(timerReady));
				}
			}
		);
		panelWorkbenchSwarm.add(buttonSwarmActive, createConstraints(0, 0, 1, 0.44, 1.0, INSETS_EDGES));
				
		JPanel panelWorkbenchSwarmSlider = new JPanel();
		panelWorkbenchSwarmSlider.setLayout(new GridBagLayout());
				
		// ************************************************************ Slider for the
		// user to change how fast the board will step
		sliderRateSwarm = new JSlider(1, 2 * RATE_STEPS_MAXIMUM);
		sliderRateSwarm.setMajorTickSpacing(13);
		sliderRateSwarm.setPaintLabels(true);
		// slider to change the speed of the agents in board.agents[]
		sliderRateSwarm.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent event) {
					int agentRate = ((JSlider)event.getSource()).getValue();
					rateExecute = Math.max(2000 / agentRate, 2000 / RATE_STEPS_MAXIMUM);

					if(timerActive) {
						toggleTimer();
						toggleTimer();
					}

					settings.setProperty(PROPERTY_AGENTS_RATE, Integer.toString(agentRate));
				}
			}
		);
		panelWorkbenchSwarmSlider.add(sliderRateSwarm, createConstraints(0, 1, 3, 1.0, 1.0, INSETS_NONE));
						
		JLabel labelRateSwarm = new JLabel("Swarm Rate (Cycles per 2s)");
		labelRateSwarm.setHorizontalAlignment(SwingConstants.CENTER);
		labelRateSwarm.setLabelFor(sliderRateSwarm);
		panelWorkbenchSwarmSlider.add(labelRateSwarm, createConstraints(0, 2, 3, 1.0, 0.24, INSETS_NONE));

		JLabel labelRateSwarmSlow = new JLabel("Slow");
		labelRateSwarmSlow.setVerticalAlignment(SwingConstants.BOTTOM);
		panelWorkbenchSwarmSlider.add(labelRateSwarmSlow, createConstraints(0, 0, 1, 0.1, 0.08, INSETS_NONE));

		JLabel labelRateSwarmFast = new JLabel("Fast");
		labelRateSwarmFast.setHorizontalAlignment(SwingConstants.RIGHT);
		labelRateSwarmSlow.setVerticalAlignment(SwingConstants.BOTTOM);
		panelWorkbenchSwarmSlider.add(labelRateSwarmFast, createConstraints(2, 0, 1, 0.1, 0.08, INSETS_NONE));
						
		panelWorkbenchSwarm.add(panelWorkbenchSwarmSlider, createConstraints(1, 0, 1, 1.0, 1.0, INSETS_EDGES));
				
		panelWorkbench.add(panelWorkbenchSwarm, createConstraints(0, 3, 1, 1.0, 0.02, INSETS_EDGES));
				
		paneSplit.setLeftComponent(panelWorkbench);
		
		// This is where the tabs for the layer options go.
		JTabbedPane paneTabbed = new JTabbedPane(JTabbedPane.TOP);
		paneTabbed.setMinimumSize(new Dimension(0, 0));
		paneTabbed.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent changeEvent) {
					JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
					int index = sourceTabbedPane.getSelectedIndex();
					layer2Draw = index + 1;

					if(board != null) {
						board.scheduleRepaint();
					}
				}
			}
		);

		// ************************************************************ TAB 1 **************************************************************
		// ************************************************************ TAB 1 **************************************************************
		JPanel tabLayerBase = new JPanel();
		tabLayerBase.setBackground(COLOR_ACCENT);
		tabLayerBase.setLayout(new GridBagLayout());
		tabLayerBase.addComponentListener(listenerFonts);

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

			labelsFrequencyColorsInitial[indexLabel] = new JLabel("0");
			labelsFrequencyColorsInitial[indexLabel].setHorizontalAlignment(SwingConstants.LEFT);
			tabLayerBase.add(labelsFrequencyColorsInitial[indexLabel], createConstraints(1, indexLabel, 1, 0.04, 0.6, INSETS_EDGES));
			
			labelsFrequencyColorsInitialText[indexLabel] = new JLabel("Initial " + nameColor + " Cells:");	
			labelsFrequencyColorsInitialText[indexLabel].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsFrequencyColorsInitialText[indexLabel].setLabelFor(labelsFrequencyColorsInitial[indexLabel]);
			tabLayerBase.add(labelsFrequencyColorsInitialText[indexLabel], createConstraints(0, indexLabel, 1, 0.04, 0.6, INSETS_NONE));

			labelsFrequencyColors[indexLabel] = new JLabel("0");
			labelsFrequencyColors[indexLabel].setHorizontalAlignment(SwingConstants.LEFT);
			tabLayerBase.add(labelsFrequencyColors[indexLabel], createConstraints(1, indexLabel + 3, 1, 0.04, 0.6, INSETS_EDGES));
			
			labelsFrequencyColorsText[indexLabel] = new JLabel("Current " + nameColor + " Cells:");	
			labelsFrequencyColorsText[indexLabel].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsFrequencyColorsText[indexLabel].setLabelFor(labelsFrequencyColors[indexLabel]);
			tabLayerBase.add(labelsFrequencyColorsText[indexLabel], createConstraints(0, indexLabel + 3, 1, 0.04, 0.6, INSETS_NONE));
		}

		// ************************************************************ Text field that
		// will trigger when button pushed, this is the value that will be the new board
		// size
		fieldSizeBoard = new JTextField("20", 10); //Does NOT work
		tabLayerBase.add(fieldSizeBoard, createConstraints(1, 6, 1, 1.0, 1.0, INSETS_EDGES));

		// ************************************************************ Change Size of
		// the Board Button
		JButton buttonSizeBoard = new JButton("Update Size"); //DOES NOT WORK YET
		buttonSizeBoard.setForeground(Color.LIGHT_GRAY);
		buttonSizeBoard.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {

				}
			}
		);
		tabLayerBase.add(buttonSizeBoard, createConstraints(2, 6, 1, 1.0, 1.0, INSETS_EDGES));

		JLabel labelSizeBoardUpdate = new JLabel("Board Size:");
		labelSizeBoardUpdate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSizeBoardUpdate.setLabelFor(fieldSizeBoard);
		tabLayerBase.add(labelSizeBoardUpdate, createConstraints(0, 6, 1, 0.04, 1.0, INSETS_NONE));

		//MODIFICATION #2  stores the % of random cells to flip, the user may change the number
		fieldFlip = new JTextField("20", 10);
		tabLayerBase.add(fieldFlip, createConstraints(1, 7, 1, 1.0, 1.0, INSETS_EDGES));

		//MODIFICATION #2  button will flip the percent of cells based on number in text field
		JButton buttonFlip = new JButton("Flip Cells");
		buttonFlip.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//set number in the text field to an int variable
					//Call method that will cause some cells to flip
					board.flipCells(Integer.parseInt(fieldFlip.getText()));
				}
			}
		);
		tabLayerBase.add(buttonFlip, createConstraints(2, 7, 1, 1.0, 1.0, INSETS_EDGES));
		
		//MODIFICATION #2: Flip random cells that the user has specified the amount (Percent)
		//Added 5/23 Morgan Might
		JLabel labelFlip = new JLabel("Percent to Flip:");
		labelFlip.setHorizontalAlignment(SwingConstants.RIGHT);
		labelFlip.setLabelFor(fieldFlip);
		tabLayerBase.add(labelFlip, createConstraints(0, 7, 1, 0.04, 1.0, INSETS_NONE));
		
		paneTabbed.addTab("Base Layer", tabLayerBase);

		// ************************************************************ TAB 2 **************************************************************
		// ************************************************************ TAB 2 **************************************************************
		JPanel tabLayerPolarity = new JPanel();
		tabLayerPolarity.setBackground(COLOR_ACCENT);
		tabLayerPolarity.setLayout(new GridBagLayout());
		tabLayerPolarity.addComponentListener(listenerFonts);

		//MODIFICATION #4:
		//Added 6/4 by Morgan Might
		//These JLabels will display the percentage of each polarity
		labelsPolaritiesPercent = new JLabel[COUNT_POLARITIES_MAXIMUM];
		labelsPolarityComparison = new JLabel[COUNT_POLARITIES_MAXIMUM];
		labelsPolaritiesPercentText = new JLabel[COUNT_POLARITIES_MAXIMUM];
		labelsPolarityComparisonText = new JLabel[COUNT_POLARITIES_MAXIMUM];
		for(int indexPolarity = 0; indexPolarity < COUNT_POLARITIES_MAXIMUM; indexPolarity++) {
			labelsPolaritiesPercent[indexPolarity] = new JLabel("0");
			labelsPolaritiesPercent[indexPolarity].setHorizontalAlignment(SwingConstants.LEFT);
			labelsPolaritiesPercent[indexPolarity].setVisible(false);
			tabLayerPolarity.add(labelsPolaritiesPercent[indexPolarity], createConstraints(1, indexPolarity, 1, 1.0, 1.0, INSETS_EDGES));
			
			labelsPolarityComparison[indexPolarity] = new JLabel("false");
			labelsPolarityComparison[indexPolarity].setHorizontalAlignment(SwingConstants.LEFT);
			tabLayerPolarity.add(labelsPolarityComparison[indexPolarity], createConstraints(1, COUNT_POLARITIES_MAXIMUM + 4 + indexPolarity, 1, 1.0, 1.0, INSETS_EDGES));
			
			labelsPolaritiesPercentText[indexPolarity] = new JLabel("(" + (indexPolarity + 1) + ") " + getColorName(colorsPolarity[indexPolarity]) + ':');
			labelsPolaritiesPercentText[indexPolarity].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsPolaritiesPercentText[indexPolarity].setLabelFor(labelsPolaritiesPercent[indexPolarity]);
			labelsPolaritiesPercentText[indexPolarity].setVisible(false);
			tabLayerPolarity.add(labelsPolaritiesPercentText[indexPolarity], createConstraints(0, indexPolarity, 1, 0.02, 1.0, INSETS_NONE));
			
			labelsPolarityComparisonText[indexPolarity] = new JLabel();
			labelsPolarityComparisonText[indexPolarity].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsPolarityComparisonText[indexPolarity].setLabelFor(labelsPolarityComparison[indexPolarity]);
			tabLayerPolarity.add(labelsPolarityComparisonText[indexPolarity], createConstraints(0, COUNT_POLARITIES_MAXIMUM + 4 + indexPolarity, 1, 0.02, 1.0, INSETS_EDGES));
		}
		
		// ************************************************************ Updates the
		// polarity to what is entered on the radio buttons
		menuDropDownGoal = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_STRATEGIES_NAMES));
		menuDropDownGoal.addActionListener(
			new ActionListener() {
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
			}
		);
		tabLayerPolarity.add(menuDropDownGoal, createConstraints(1, COUNT_POLARITIES_MAXIMUM, 1, 1.0, 1.0, INSETS_EDGES));

		JLabel labelGoal = new JLabel("Goal:");
		labelGoal.setHorizontalAlignment(SwingConstants.RIGHT);
		labelGoal.setLabelFor(menuDropDownGoal);
		tabLayerPolarity.add(labelGoal, createConstraints(0, COUNT_POLARITIES_MAXIMUM, 1, 0.02, 1.0, INSETS_NONE));

		//MODIFICATION #5:
		//Added 6/12 by Morgan Might
		//
		//This button will determine if the agents should follow rules to get a single polarity
		//or try to balance the 3 different polarities.
		buttonModeEquilibrium = new JToggleButton();
		buttonModeEquilibrium.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					if(modeEquilibrium = buttonModeEquilibrium.isSelected()) {
						buttonModeEquilibrium.setText("Mode: Equilibrium");
					}
					else {
						buttonModeEquilibrium.setText("Mode: Monopoly");
					}

					settings.setProperty(PROPERTY_RULE_EQUILIBRIUM, Boolean.toString(modeEquilibrium));

					updateEquilibriumLabelsText();
				}
			}
		);
		tabLayerPolarity.add(buttonModeEquilibrium, createConstraints(0, COUNT_POLARITIES_MAXIMUM + 1, 2, 1.0, 1.0, INSETS_EDGES));

		// ************************************************************ Sets what the
		// polarity ratios should be for the two colors.
		JComboBox<String> menuDropDownStabilityRegional = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] {"50/50", "60/40", "70/30", "80/20", "90/10", "100/0" })); //Does not work
		tabLayerPolarity.add(menuDropDownStabilityRegional, createConstraints(1, COUNT_POLARITIES_MAXIMUM + 2, 1, 1.0, 1.0, INSETS_EDGES));

		JLabel labelStabilityRegional = new JLabel("Regional Stability:"); //Does not work
		labelStabilityRegional.setForeground(Color.LIGHT_GRAY);
		labelStabilityRegional.setHorizontalAlignment(SwingConstants.RIGHT);
		labelStabilityRegional.setLabelFor(menuDropDownStabilityRegional);
		tabLayerPolarity.add(labelStabilityRegional, createConstraints(0, COUNT_POLARITIES_MAXIMUM + 2, 1, 0.02, 1.0, INSETS_NONE));
		
		//MODIFICATION #10
		//Added 7/17 by Morgan Might
		//Dominant Polarity Label: Choose which polarity should be the majority
		JLabel labelPolarityDominant = new JLabel("Dominant Polarity:");
		labelPolarityDominant.setHorizontalAlignment(SwingConstants.RIGHT);
		tabLayerPolarity.add(labelPolarityDominant, createConstraints(0, COUNT_POLARITIES_MAXIMUM + 3, 1, 0.02, 1.0, INSETS_NONE));

		//Action added 7/18 by Morgan Might
		optionsPolarityDominant = new DefaultComboBoxModel<String>();
		menuDropDownPolarityDominant = new JComboBox<String>(optionsPolarityDominant);
		menuDropDownPolarityDominant.addItemListener(
			new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED) {
						setDominantPolarity(((JComboBox<String>)e.getSource()).getSelectedIndex());
					}
				}
			}
		);
		tabLayerPolarity.add(menuDropDownPolarityDominant, createConstraints(1, COUNT_POLARITIES_MAXIMUM + 3, 1, 1.0, 1.0, INSETS_EDGES));
		
		ItemListener listenerDropDownColorPolarity = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					JComboBox<String> menuDropDownSource = (JComboBox<String>)event.getSource();

					for(int indexPolarity = 0; indexPolarity < menusDropDownColorsPolarity.length; indexPolarity++) {
						if(menusDropDownColorsPolarity[indexPolarity].equals(menuDropDownSource)) {
							setPolarityColor(indexPolarity, getColor((String)menuDropDownSource.getSelectedItem()));
							return;
						}
					}
				}
			}
		};
		
		menusDropDownColorsPolarity = new JComboBox[COUNT_POLARITIES_MAXIMUM];
		JLabel labelColorPolarity;
		for(int indexMenu = 0; indexMenu < menusDropDownColorsPolarity.length; indexMenu++) {
			menusDropDownColorsPolarity[indexMenu] = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
			menusDropDownColorsPolarity[indexMenu].addItemListener(listenerDropDownColorPolarity);
			tabLayerPolarity.add(menusDropDownColorsPolarity[indexMenu], createConstraints(1, 2 * COUNT_POLARITIES_MAXIMUM + 4 + indexMenu, 1, 1.0, 1.0, INSETS_EDGES));
			
			labelColorPolarity = new JLabel("Polarity (" + (indexMenu + 1) + ") Color:");
			labelColorPolarity.setHorizontalAlignment(SwingConstants.RIGHT);
			labelColorPolarity.setLabelFor(menusDropDownColorsPolarity[indexMenu]);
			tabLayerPolarity.add(labelColorPolarity, createConstraints(0, 2 * COUNT_POLARITIES_MAXIMUM + 4 + indexMenu, 1, 0.02, 1.0, INSETS_EDGES));
		}
		
		paneTabbed.addTab("Polarity Layer", tabLayerPolarity);

		// ************************************************************ TAB 3 *************************************************************
		// ************************************************************ TAB 3 *************************************************************
		JPanel tabLayerInteractive = new JPanel();
		tabLayerInteractive.setBackground(COLOR_ACCENT);
		tabLayerInteractive.setLayout(new GridBagLayout());
		tabLayerInteractive.addComponentListener(listenerFonts);

		// ************************************************************ User can set the
		// number of agents
		fieldCountAgents = new JTextField("4", 10);
		tabLayerInteractive.add(fieldCountAgents, createConstraints(1, 0, 1, 1.0, 1.0, INSETS_EDGES));

		JButton buttonCountAgents = new JButton("Update Agents");
		buttonCountAgents.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonCountAgents, createConstraints(2, 0, 2, 1.0, 1.0, INSETS_EDGES));
		
		JLabel labelCountAgentsUpdate = new JLabel("Number of Agents:");
		labelCountAgentsUpdate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCountAgentsUpdate.setForeground(Color.LIGHT_GRAY);
		labelCountAgentsUpdate.setLabelFor(fieldCountAgents);
		tabLayerInteractive.add(labelCountAgentsUpdate, createConstraints(0, 0, 1, 0.04, 1.0, INSETS_NONE));

		// ************************************************************ User can select
		// how many changes the agent can make
		fieldCountModifications = new JTextField("-1", 10);
		tabLayerInteractive.add(fieldCountModifications, createConstraints(1, 1, 1, 1.0, 1.0, INSETS_EDGES));

		JButton buttonCountModifications = new JButton("Update Changes");
		buttonCountModifications.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonCountModifications, createConstraints(2, 1, 2, 1.0, 1.0, INSETS_EDGES));
				
		JLabel labelCountModifications = new JLabel("Number of Changes:");
		labelCountModifications.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCountModifications.setForeground(Color.LIGHT_GRAY);
		labelCountModifications.setLabelFor(fieldCountModifications);
		tabLayerInteractive.add(labelCountModifications, createConstraints(0, 1, 1, 0.04, 1.0, INSETS_NONE));

		// ************************************************************ User can choose
		// how close an agent can get to another. 0 implies that many spaces between,
		// thus they could overlap.
		fieldAgentCloseness = new JTextField("0", 10);
		tabLayerInteractive.add(fieldAgentCloseness, createConstraints(1, 2, 1, 1.0, 1.0, INSETS_EDGES));

		JButton buttonAgentCloseness = new JButton("Update Closeness");
		buttonAgentCloseness.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonAgentCloseness, createConstraints(2, 2, 2, 1.0, 1.0, INSETS_EDGES));

		JLabel labelAgentCloseness = new JLabel("Agent Closeness:");
		labelAgentCloseness.setHorizontalAlignment(SwingConstants.RIGHT);
		labelAgentCloseness.setForeground(Color.LIGHT_GRAY);
		labelAgentCloseness.setLabelFor(fieldAgentCloseness);
		tabLayerInteractive.add(labelAgentCloseness, createConstraints(0, 2, 1, 0.04, 1.0, INSETS_NONE));
		
		buttonWraparound = new JToggleButton();
		buttonWraparound.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(board != null) {
						board.toggleWraparound();
					}

					if (boardWraparound = buttonWraparound.isSelected()) {
						buttonWraparound.setText("Wrap Agents");
					}
					else {
						buttonWraparound.setText("Bounce Agents");
					}

					settings.setProperty(PROPERTY_BOARD_WRAPAROUND, Boolean.toString(boardWraparound));
				}
			}
		);
		tabLayerInteractive.add(buttonWraparound, createConstraints(0, 3, 4, 1.0, 1.0, INSETS_EDGES));
		
		// ************************************************************ User can change
		// the color of the agents
		menuDropDownColorAgents = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
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
		tabLayerInteractive.add(menuDropDownColorAgents, createConstraints(1, 4, 1, 1.0, 1.0, INSETS_EDGES));

		JLabel labelAgentColor = new JLabel("Agents Color:");
		labelAgentColor.setHorizontalAlignment(SwingConstants.RIGHT);
		labelAgentColor.setLabelFor(menuDropDownColorAgents);
		tabLayerInteractive.add(labelAgentColor, createConstraints(0, 4, 1, 0.04, 1.0, INSETS_NONE));
		
		//MODIFICATION: Special Agent Color
		//Added 5/21 by Morgan Might
		// User can change the color of the "special" agents
		menuDropDownColorAgentsSpecial = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
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
		tabLayerInteractive.add(menuDropDownColorAgentsSpecial, createConstraints(1, 5, 1, 1.0, 1.0, INSETS_EDGES));

		JLabel labelColorAgentsSpecial = new JLabel("Special Agent Color:");
		labelColorAgentsSpecial.setHorizontalAlignment(SwingConstants.RIGHT);
		labelColorAgentsSpecial.setLabelFor(menuDropDownColorAgentsSpecial);
		tabLayerInteractive.add(labelColorAgentsSpecial, createConstraints(0, 5, 1, 0.04, 1.0, INSETS_NONE));
		
		GridBagConstraints constraintsButtonAgentVisibility = createConstraints(2, 4, 2, 1.0, 1.0, INSETS_EDGES);
		constraintsButtonAgentVisibility.gridheight = 2;
		
		buttonAgentVisiblity = new JToggleButton("View Agents");
		buttonAgentVisiblity.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					whetherAgentsVisible = buttonAgentVisiblity.isSelected();

					if(board != null) {
						board.toggleAgentVisibility();
					}

					settings.setProperty(PROPERTY_AGENTS_VISIBLE, Boolean.toString(whetherAgentsVisible));
				}
			}
		);
		tabLayerInteractive.add(buttonAgentVisiblity, constraintsButtonAgentVisibility);

		JButton buttonPheromoneTrailAdd = new JButton("Set Phrmn Trail");
		buttonPheromoneTrailAdd.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneTrailAdd, createConstraints(0, 6, 2, 1.0, 1.0, INSETS_EDGES));

		JButton buttonPheromoneTrailRemove = new JButton("Remove Phrmn Trail");
		buttonPheromoneTrailRemove.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneTrailRemove, createConstraints(2, 6, 2, 1.0, 1.0, INSETS_EDGES));

		JButton buttonPheromoneZoneAdd = new JButton("Set Phrmn Zone");
		buttonPheromoneZoneAdd.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneZoneAdd, createConstraints(0, 7, 2, 1.0, 1.0, INSETS_EDGES));

		JButton buttonPheromoneZoneRemove = new JButton("Remove Phrmn Zone");
		buttonPheromoneZoneRemove.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneZoneRemove, createConstraints(2, 7, 2, 1.0, 1.0, INSETS_EDGES));

		// ************************************************************ User can set how
		// strongly the agents should follow the swarm.
		fieldStrengthPheromone = new JTextField("1", 10);
		tabLayerInteractive.add(fieldStrengthPheromone, createConstraints(1, 8, 1, 1.0, 1.0, INSETS_EDGES));

		JButton buttonStrengthPheromone = new JButton("Update P Strength");
		buttonStrengthPheromone.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonStrengthPheromone, createConstraints(2, 8, 2, 1.0, 1.0, INSETS_EDGES));
				
		JLabel labelStrengthPheromone = new JLabel("Pheromone Strength:");
		labelStrengthPheromone.setHorizontalAlignment(SwingConstants.RIGHT);
		labelStrengthPheromone.setForeground(Color.LIGHT_GRAY);
		labelStrengthPheromone.setLabelFor(fieldStrengthPheromone);
		tabLayerInteractive.add(labelStrengthPheromone, createConstraints(0, 8, 1, 0.04, 1.0, INSETS_NONE));
		
		// ************************************************************ User changes the
		// color of the pheromone trails on the board.
		JComboBox<String> menuDropDownColorPheromone = new JComboBox<String>(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
		tabLayerInteractive.add(menuDropDownColorPheromone, createConstraints(1, 9, 3, 1.0, 1.0, INSETS_EDGES));
		
		JLabel labelColorPheromoneTrail = new JLabel("Pheromone Color:");
		labelColorPheromoneTrail.setHorizontalAlignment(SwingConstants.RIGHT);
		labelColorPheromoneTrail.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(labelColorPheromoneTrail, createConstraints(0, 9, 1, 0.04, 1.0, INSETS_NONE));
		
		JToggleButton buttonModeAttractor = new JToggleButton("Attract");
		buttonModeAttractor.setSelected(true);
		buttonModeAttractor.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (modeAttractor = buttonModeAttractor.isSelected()) {
						buttonModeAttractor.setText("Attract");
					}
					else {
						buttonModeAttractor.setText("Repel");
					}
					
					if(board != null) {
						board.setAttractorMode(modeAttractor);
					}
				}
			}
		);
		tabLayerInteractive.add(buttonModeAttractor, createConstraints(0, 10, 4, 1.0, 1.0, INSETS_EDGES));

		paneTabbed.addTab("Interactive Layer", tabLayerInteractive);
		
		//MODIFICATION #7: New layer, Layer 4, is concerned with the persistence of the cells
		//on Layer 1
		//
		//by Morgan Might
		//July 5, 2018

		// ************************************************************ TAB 4 *************************************************************
		// ************************************************************ TAB 4 *************************************************************
		JPanel tabLayerPersistence = new JPanel();
		tabLayerPersistence.setBackground(COLOR_ACCENT);
		paneTabbed.addTab("Persistence Layer", tabLayerPersistence);
		tabLayerPersistence.setLayout(null);

		/* Tab 5 */
		JPanel tabLayerCorrectness = new JPanel();
		tabLayerCorrectness.setBackground(COLOR_ACCENT);
		paneTabbed.addTab("Correctness Layer", tabLayerCorrectness);
		tabLayerCorrectness.setLayout(null);

		paneSplit.setRightComponent(paneTabbed);
		
		frmProjectLegion.add(paneSplit, createConstraints(0, 1, 1, 1.0, 1.0, INSETS_NONE));
		
		frmProjectLegion.pack();
		
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
			if(timerActive) {
				toggleTimer();
			}

			if(writerData != null) {
				try {
					writerData.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

			panelBoard.remove(this.board);
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

		labelSizeBoardValue.setText(sizeBoardString);
		labelCountAgentsValue.setText(Integer.toString(countAgents + countAgentsSpecial));

		panelBoard.add(board);

		for(int indexLabel = 0; indexLabel < labelsFrequencyColorsInitial.length; indexLabel++) {
			labelsFrequencyColorsInitial[indexLabel].setText(Integer.toString(frequencyColorsInitial[indexLabel]));
		}

		indexStep = 0;

		if(exportData) {
			try {
				writerData = new BufferedWriter(new FileWriter(pathData + "\\Swarm-Frequencies_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + ".csv"));
				addDataColumnHeaders(writerData);
				outputPolarityData(writerData);
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			if(modeAutomatic) {
				storeAveragePolarityData(indexStep);
			}
		}
		
		if(exportScreenshot) {
			try {
				ImageIO.write(board.capture(), "JPG", new File(pathScreenshot + File.separator + "Simulation_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + FILETYPE_SCREENSHOT));
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(timerReady) {
			toggleTimer();
		}
	}

	//MODIFICATION
	//Added 5/22 by Morgan Might
	public boolean getSplitPolarity() {
		return splitPolarity;
	}

	public boolean getTimerActive() {
		return timerActive;
	}

	//MODIFICATION #5:
	//Added 6/12 by Morgan Might
	//
	//Return the boolean that relates to the toggle button that determines if the agents
	//should solve for a single polarity or three balanced polarities
	public boolean getEquilibriumMode() {
		return modeEquilibrium;
	}
	
	public boolean getAttractorMode() {
		return modeAttractor;
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

	public Board getBoard() {
		return board;
	}

	private void setPolarityColor(int indexPolarity, Color colorPolarity) {
		colorsPolarity[indexPolarity] = colorPolarity;

		String namePolarity = "(" + (indexPolarity + 1) + ") " + getColorName(colorPolarity);
		
		labelsPolaritiesPercentText[indexPolarity].setText(namePolarity + ':');
		
		if(optionsPolarityDominant.getSize() > indexPolarity) {
			int indexPolarityDominant = this.indexPolarityDominant;
			optionsPolarityDominant.removeElementAt(indexPolarity);
			optionsPolarityDominant.insertElementAt(namePolarity, indexPolarity);
			menuDropDownPolarityDominant.setSelectedIndex(indexPolarityDominant);
		}

		settings.setProperty(PROPERTY_COLOR_POLARITY + (indexPolarity + 1), getColorName(colorsPolarity[indexPolarity]));
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

	private void updatePolarityCount() {
		int countPolarities = goalStrategy.getPolarityCount();
		
		int countPolaritiesPrevious = optionsPolarityDominant.getSize();

		for(int indexPolarity = countPolaritiesPrevious; indexPolarity < countPolarities; indexPolarity++) {
			labelsPolaritiesPercent[indexPolarity].setVisible(true);
			labelsPolaritiesPercentText[indexPolarity].setVisible(true);
			
			optionsPolarityDominant.addElement("(" + (indexPolarity + 1) + ") " + getColorName(colorsPolarity[indexPolarity]));
		}

		for(int indexPolarity = countPolarities; indexPolarity < countPolaritiesPrevious; indexPolarity++) {
			labelsPolaritiesPercent[indexPolarity].setVisible(false);
			labelsPolaritiesPercentText[indexPolarity].setVisible(false);
			
			optionsPolarityDominant.removeElementAt(countPolarities);
		}

		if(indexPolarityDominant >= countPolarities) {
			menuDropDownPolarityDominant.setSelectedIndex(countPolarities - 1);
		}
		else {
			updateEquilibriumLabelsText();
		}

		buttonModeEquilibrium.setVisible(countPolarities > 2);
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
				labelsPolarityComparison[indexLabel].setVisible(true);

				comparisonPolarityDominantLesser.append(namePolarityOther.charAt(0)).append(" + ");

				indexPolarityOther++;
			}
			comparisonPolarityDominantLesser.replace(comparisonPolarityDominantLesser.length() - 2, comparisonPolarityDominantLesser.length(), "").append(" > ").append(namePolarityDominant).append(" :");
			labelsPolarityComparisonText[countPolarities - 1].setText(comparisonPolarityDominantLesser.toString());
			labelsPolarityComparisonText[countPolarities - 1].setVisible(true);
			labelsPolarityComparison[countPolarities - 1].setVisible(true);
		}
		else {
			countPolarities = 0;
		}

		for(int indexLabel = countPolarities; indexLabel < labelsPolarityComparisonText.length; indexLabel++) {
			labelsPolarityComparison[indexLabel].setVisible(false);
			labelsPolarityComparisonText[indexLabel].setVisible(false);
		}
	}

	public void updateStepCountLabel(String count) {
		labelCountStepsValue.setText(count);
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

	public Board generateBoard() {
		return new Board(sizeBoard, countAgents, countAgentsSpecial, this);
	}

	public void toggleTimer() {
		if(board != null) {
			if(timerActive) {
				task.cancel();
				timer.purge();
			}
			else {
				task = new TimerTask() {
					@Override
					public void run() {
						step();
						board.repaint();
					}
				};

				timer.scheduleAtFixedRate(task, rateExecute, rateExecute);
			}

			timerActive = !timerActive;
		}
	}

	public void step() {
		board.step();
		indexStep++;

		for(int indexLabel = 0; indexLabel < labelsFrequencyColors.length; indexLabel++) {
			labelsFrequencyColors[indexLabel].setText(Integer.toString(frequencyColors[indexLabel]));
		}

		for(int indexLabel = 0; indexLabel < labelsPolaritiesPercent.length; indexLabel++) {
			labelsPolaritiesPercent[indexLabel].setText((frequencyPolarities[indexLabel] / (board.getTotalNumCells() / 100d)) + "%");
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
		
		labelCountStepsValue.setText(Integer.toString(indexStep));
		
		if(exportData && indexStep % intervalExportData == 0) {
			outputPolarityData(writerData);

			if(modeAutomatic) {
				storeAveragePolarityData(indexStep);
			}
		}

		if(exportScreenshot && indexStep % intervalExportScreenshot == 0) {
			if(timerReady) {
				toggleTimer();
			}

			try {
				ImageIO.write(board.capture(), "JPG", new File(pathScreenshot + File.separator + "Simulation_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + FILETYPE_SCREENSHOT));
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			if(timerReady) {
				toggleTimer();
			}
		}

		if(modeAutomatic) {
			if(indexStep >= countStepsMaximum) {
				indexRepetition++;

				if(indexRepetition >= countRepetitionsMaximum) {
					buttonSwarmActive.setSelected(true);
					buttonSwarmActive.doClick();

					if(exportData) {
						outputAveragePolarityData();
					}
					
					modeAutomatic = false;
				}
				else {
					setBoard(generateBoard());
				}
			}
		}
	}

	private void storeAveragePolarityData(int index) {
		double[] frequencyPolaritiesAverage = new double[COUNT_POLARITIES_MAXIMUM + 1];
		frequencyPolaritiesAverage[0] = indexStep + 1;

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

	private static String concatenatePolarityData(double[] frequencyPolarities) {
		StringBuilder line = new StringBuilder();

		int indexPolarity;
		for(indexPolarity = 0; indexPolarity < frequencyPolarities.length - 1; indexPolarity++) {
			line.append(frequencyPolarities[indexPolarity]);
			line.append(", ");
		}
		line.append(frequencyPolarities[indexPolarity]);

		return line.toString();
	}

	private void outputPolarityData(BufferedWriter writer) {
		double[] frequencyPolaritiesTagged = new double[frequencyPolarities.length + 1];
		frequencyPolaritiesTagged[0] = indexStep;
		for(int indexPolarity = 0; indexPolarity < frequencyPolarities.length; indexPolarity++) {
			frequencyPolaritiesTagged[indexPolarity + 1] = frequencyPolarities[indexPolarity];
		}

		try {
			writer.append(concatenatePolarityData(frequencyPolaritiesTagged));
			writer.newLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void outputAveragePolarityData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(pathData + "\\Swarm-Frequencies-Averages_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + ".csv"));

			addDataColumnHeaders(writer);

			for(double[] frequencyPolaritiesAverage : frequenciesPolaritiesAverage) {
				writer.append(concatenatePolarityData(frequencyPolaritiesAverage));
				writer.newLine();
			}

			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void outputProperties(File fileOutput) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput));

			writer.write(HEADER_PROPERTIES);
			writer.newLine();

			for(String[] properties : PROPERTIES) {
				for(String property : properties) {
					writer.write(property + '=' + settings.getProperty(property).replace('\\', '/'));
					writer.newLine();
				}
			}
			writer.close();
		}
		catch (IOException exceptionOutput) {
			exceptionOutput.printStackTrace();
		}
	}

	private static void addDataColumnHeaders(BufferedWriter writer) {
		List<String> headersColumn = new ArrayList<String>(1 + COUNT_POLARITIES_MAXIMUM);

		headersColumn.add("Step");

		int indexPolarity;
		for(indexPolarity = 1; indexPolarity <= COUNT_POLARITIES_MAXIMUM; indexPolarity++) {
			headersColumn.add("Polarity " + indexPolarity);
		}

		try {
			writer.write(String.join(", ", headersColumn));
			writer.newLine();
		}
		catch (IOException e) {
			e.printStackTrace();
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
	
	private static GridBagConstraints createConstraints(int x, int y, int width, double weightX, double weightY, Insets insets) {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.weightx = weightX;
		constraints.weighty = weightY;
		constraints.insets = insets;
		
		return constraints;
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

	private class ActionListenerVisibility implements ActionListener {
		private Component component;

		public ActionListenerVisibility(Component component) {
			this.component = component;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			component.setVisible(((JToggleButton)event.getSource()).isSelected());
			component.revalidate();
		}
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
			buttonWraparound.setSelected(!Boolean.parseBoolean(value));
			buttonWraparound.doClick();
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
			sliderRateSwarm.setValue(Integer.parseInt(value));
		}
	}

	private class CommandAgentActive extends Command {
		@Override
		public void execute(String value) {
			buttonSwarmActive.setSelected(!Boolean.parseBoolean(value));
			buttonSwarmActive.doClick();
		}
	}

	private class CommandAgentVisible extends Command {
		@Override
		public void execute(String value) {
			buttonAgentVisiblity.setSelected(!Boolean.parseBoolean(value));
			buttonAgentVisiblity.doClick();
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

			menuDropDownPolarityDominant.setSelectedIndex(valueInteger - 1);
		}
	}

	private class CommandRuleEquilibrium extends Command {
		@Override
		public void execute(String value) {
			buttonModeEquilibrium.setSelected(!Boolean.parseBoolean(value));
			buttonModeEquilibrium.doClick();
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
			menusDropDownColorsPolarity[indexPolarity].setSelectedItem(value);
		}
	}

	private class CommandExportPolarities extends Command {
		@Override
		public void execute(String value) {
			exportData = Boolean.parseBoolean(value);
		}
	}


	private class CommandExportPolaritiesInterval extends Command {
		@Override
		public void execute(String value) {
			intervalExportData = Integer.parseInt(value);
		}
	}

	private class CommandExportPolaritiesDirectory extends Command {
		@Override
		public void execute(String value) {
			pathData = Paths.get(value);
		}
	}

	private class CommandExportScreenshot extends Command {
		@Override
		public void execute(String value) {
			exportScreenshot = Boolean.parseBoolean(value);
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