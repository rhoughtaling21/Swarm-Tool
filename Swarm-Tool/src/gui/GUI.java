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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.NumberFormatter;

import cells.CellTalliedBase;
import patterns.Pattern;
import patterns.PatternBlackout;
import patterns.PatternCheckerboard;
import patterns.PatternDiagonals;
import patterns.PatternLines;
import swarm.RegisterDefinitionsAgent;
import swarm.RegisterDefinitionsAgent.DefinitionAgent;

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
	private static final int RATE_STEPS_MAXIMUM = 85;
	public static final int COUNT_ENTRIES_AGENTS = RegisterDefinitionsAgent.getSize();
	private static final String TITLE = "Project Legion";
	private static final String HEADER_PROPERTIES = "#--- Swarm Simulation Properties ---#";
	private static final String FILETYPE_SCREENSHOT = ".jpg";
	private static final String FILETYPE_PROPERTIES = ".properties";
	private static final String PROPERTY_BOARD_SIZE = "board.size";
	private static final String PROPERTY_BOARD_WRAPAROUND = "board.wraparound";
	private static final String TEMPLATE_PROPERTY_AGENTS_COUNT = "agents.count.";
	private static final String PROPERTY_AGENTS_RATE = "agents.rate";
	private static final String PROPERTY_AGENTS_ACTIVE = "agents.active";
	private static final String PROPERTY_AGENTS_VISIBLE = "agents.visible";
	private static final String PROPERTY_PHEROMONES_MAGNET_STRENGTH = "pheromones.magnet.strength";
	private static final String PROPERTY_PHEROMONES_MAGNET_RANGE = "pheromones.magnet.range";
	private static final String PROPERTY_PHEROMONES_MAGNET_ATTRACT = "pheromones.magnet.attract";
	private static final String PROPERTY_RULE_PATTERN = "rule.pattern";
	private static final String PROPERTY_RULE_STRATEGY_SIGNAL = "rule.strategy.signal";
	private static final String PROPERTY_RULE_STRATEGY_SIGNAL_RANGE = "rule.strategy.signal.range";
	private static final String PROPERTY_RULE_POLARITY_DOMINANT = "rule.polarity.dominant";
	private static final String PROPERTY_RULE_EQUILIBRIUM = "rule.equilibrium";
	private static final String PROPERTY_RULE_AUTOMATIC = "rule.automatic";
	private static final String PROPERTY_RULE_AUTOMATIC_REPETITITONS = "rule.automatic.repetitions";
	private static final String PROPERTY_RULE_AUTOMATIC_STEPS = "rule.automatic.steps";
	private static final String TEMPLATE_PROPERTY_COLOR_AGENTS = "color.agents.";
	private static final String TEMPLATE_PROPERTY_COLOR_POLARITY = "color.polarity.";
	private static final String PROPERTY_EXPORT_DATA = "export.polarities";
	private static final String PROPERTY_EXPORT_DATA_INTERVAL = "export.polarities.interval";
	private static final String PROPERTY_EXPORT_DATA_DIRECTORY = "export.polarities.directory";
	private static final String PROPERTY_EXPORT_SCREENSHOT = "export.screenshot";
	private static final String PROPERTY_EXPORT_SCREENSHOT_INTERVAL = "export.screenshot.interval";
	private static final String PROPERTY_EXPORT_SCREENSHOT_DIRECTORY = "export.screenshot.directory";
	private static final Color COLOR_ACCENT = new Color(211, 211, 211);
	private static final Insets INSETS_NONE = new Insets(0, 0, 0, 0);
	private static final Insets INSETS_SLIM = new Insets(10, 20, 10, 20);
	private static final Insets INSETS_THICK = new Insets(40, 40, 40, 40);
	private static final DateTimeFormatter FORMATTER_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
	private static final JFileChooser SELECTOR_FILEPATH = new JFileChooser();
	private static final Color[] OPTIONS_COLORS = {Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	private static final String[] OPTIONS_COLORS_NAMES = {"BLACK", "BLUE", "CYAN", "GRAY", "GREEN", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW"};
	private static final String[] OPTIONS_PATTERNS_NAMES = {"BLACKOUT", "CHECKERBOARD", "DIAGONALS", "LINES"};
	private static final String[] ENTRIES_REGISTRY = RegisterDefinitionsAgent.getEntries();
	private static final String[] PROPERTIES_BOARD = {PROPERTY_BOARD_SIZE, PROPERTY_BOARD_WRAPAROUND};
	private static final String[] PROPERTIES_AGENTS_COUNTS = applySuffixes(TEMPLATE_PROPERTY_AGENTS_COUNT, ENTRIES_REGISTRY);
	private static final String[] PROPERTIES_AGENTS = {PROPERTY_AGENTS_RATE, PROPERTY_AGENTS_ACTIVE, PROPERTY_AGENTS_VISIBLE};
	private static final String[] PROPERTIES_PHEROMONES = {PROPERTY_PHEROMONES_MAGNET_STRENGTH, PROPERTY_PHEROMONES_MAGNET_RANGE, PROPERTY_PHEROMONES_MAGNET_ATTRACT};
	private static final String[] PROPERTIES_RULES = {PROPERTY_RULE_PATTERN, PROPERTY_RULE_STRATEGY_SIGNAL, PROPERTY_RULE_STRATEGY_SIGNAL_RANGE, PROPERTY_RULE_POLARITY_DOMINANT, PROPERTY_RULE_EQUILIBRIUM, PROPERTY_RULE_AUTOMATIC, PROPERTY_RULE_AUTOMATIC_REPETITITONS, PROPERTY_RULE_AUTOMATIC_STEPS};
	private static final String[] PROPERTIES_COLORS_AGENTS = applySuffixes(TEMPLATE_PROPERTY_COLOR_AGENTS, ENTRIES_REGISTRY);
	private static final String[] PROPERTIES_COLORS_POLARITIES = applySuffixes(TEMPLATE_PROPERTY_COLOR_POLARITY, 1, COUNT_POLARITIES_MAXIMUM);
	private static final String[] PROPERTIES_EXPORT = {PROPERTY_EXPORT_DATA, PROPERTY_EXPORT_DATA_INTERVAL, PROPERTY_EXPORT_DATA_DIRECTORY, PROPERTY_EXPORT_SCREENSHOT, PROPERTY_EXPORT_SCREENSHOT_INTERVAL, PROPERTY_EXPORT_SCREENSHOT_DIRECTORY};
	public static final DefinitionAgent[] REGISTRY = RegisterDefinitionsAgent.getRegistry();
	private static final Pattern[] OPTIONS_STRATEGIES = {new PatternBlackout(), new PatternCheckerboard(), new PatternDiagonals(), new PatternLines()};
	private static final String[][] PROPERTIES = {PROPERTIES_BOARD, PROPERTIES_AGENTS_COUNTS, PROPERTIES_AGENTS, PROPERTIES_PHEROMONES, PROPERTIES_RULES, PROPERTIES_COLORS_AGENTS, PROPERTIES_COLORS_POLARITIES, PROPERTIES_EXPORT};
	private static final HashMap<Color, String> MAP_COLORS_NAMES = generateMapColorsNames();
	private static final HashMap<String, Color> MAP_COLORS = generateMapColors();
	private static final HashMap<String, Pattern> MAP_PATTERNS = generateMapStrategies();

	private boolean timerReady;
	private boolean timerActive;
	private boolean modeMagnetAttract;
	private boolean boardWraparound;
	private boolean modeEquilibrium; //MODIFICATION #5 determines if the agents goal is a single polarity or three balanced polarities
	private boolean swarmVisible;
	private int indexLayerDisplay;// which cell array in board to display
	private int indexPolarityDominant;
	private int indexRepetition;
	private int indexStep;
	private long rateExecute;
	private Value<Boolean> modeSignal;
	private Value<Boolean> modeAutomatic;
	private Value<Boolean> exportData, exportScreenshot;
	private Value<Integer> sizeBoard;
	private Value<Integer> countRepetitionsMaximum;
	private Value<Integer> countStepsMaximum;
	private Value<Integer> intervalExportData;
	private Value<Integer> intervalExportScreenshot;
	private Value<Double> strengthMagnet;
	private Value<Double> rangeMagnet;
	private Value<Double> rangeSignal;
	private Value<Path> pathData, pathScreenshot;
	private Properties settings;
	private Timer timer;
	private TimerTask task;
	private Pattern goalStrategy;
	private Board board;// board to be drawn
	private BufferedWriter writerData;
	private JFrame frmProjectLegion;// main frame
	private JTextField fieldCountAgentsNormal;
	private JTextField fieldCountModifications;
	private JTextField fieldAgentCloseness;
	private JTextField fieldStrengthPheromone;
	private JTextField fieldSizeBoard;
	private JTextField fieldFlip;
	private JLabel labelSizeBoardValue; // updates BoardSize Label
	private JLabel labelCountAgentsValue; // updates SwarmCount Label
	private JLabel labelCountStepsValue; //displays the number of steps that have occurred
	private JToggleButton buttonAgentVisiblity, buttonWraparound, buttonModeEquilibrium, buttonSwarmActive, buttonModeMagnet;
	private JSlider sliderRateSwarm;
	private JPanel panelBoard;
	private DefaultComboBoxModel<String> optionsPolarityDominant;
	private JComboBox<String> menuDropDownPattern, menuDropDownPolarityDominant;
	private int[] frequencyColorsInitial;
	private int[] frequencyColors;
	private int[] frequencyPolarities;
	private Integer[] countsAgents;
	private Color[] colorsPolarity;
	private Color[] colorsAgents;
	private JLabel[] labelsFrequencyColorsInitialText;
	private JLabel[] labelsFrequencyColorsText;
	private JLabel[] labelsPolaritiesPercentText;
	private JLabel[] labelsFrequencyColorsInitial;
	private JLabel[] labelsFrequencyColors;
	private JLabel[] labelsPolaritiesPercent;
	private JLabel[] labelsPolarityComparison;
	private JLabel[] labelsPolarityComparisonText;
	private ArrayList<double[]> frequenciesPolaritiesAverage;
	private JComboBox<String>[] menusDropDownColorsAgents, menusDropDownColorsPolarity;
	private HashMap<Object, Command> propertyCommands;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(
			new Runnable() {
				@Override
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
	@SuppressWarnings("unchecked")
	public GUI() {
		countsAgents = new Integer[COUNT_ENTRIES_AGENTS];
		
		colorsAgents = new Color[COUNT_ENTRIES_AGENTS];
		colorsPolarity = new Color[COUNT_POLARITIES_MAXIMUM];

		timer = new Timer();
		timerReady = false;
		timerActive = false;
		indexStep = 0;

		menusDropDownColorsAgents = new JComboBox[COUNT_ENTRIES_AGENTS];
		menusDropDownColorsPolarity = new JComboBox[COUNT_POLARITIES_MAXIMUM];
		
		propertyCommands = new HashMap<Object, Command>();
		propertyCommands.put(PROPERTY_BOARD_SIZE, new CommandValueInteger(sizeBoard = new Value<Integer>()));
		propertyCommands.put(PROPERTY_BOARD_WRAPAROUND, new CommandComponentButtonToggle(buttonWraparound = new JToggleButton()));
		for(int indexEntryAgent = 0; indexEntryAgent < COUNT_ENTRIES_AGENTS; indexEntryAgent++) {
			propertyCommands.put(PROPERTIES_AGENTS_COUNTS[indexEntryAgent], new CommandElementArrayInteger(countsAgents, indexEntryAgent));
			propertyCommands.put(PROPERTIES_COLORS_AGENTS[indexEntryAgent], new CommandComponentMenuDropDownSelectedItemString(menusDropDownColorsAgents[indexEntryAgent] = new JComboBox<String>()));
		}
		propertyCommands.put(PROPERTY_AGENTS_RATE, new CommandComponentSlider(sliderRateSwarm = new JSlider()));
		propertyCommands.put(PROPERTY_AGENTS_ACTIVE, new CommandComponentButtonToggle(buttonSwarmActive = new JToggleButton()));
		propertyCommands.put(PROPERTY_AGENTS_VISIBLE, new CommandComponentButtonToggle(buttonAgentVisiblity = new JToggleButton()));
		propertyCommands.put(PROPERTY_PHEROMONES_MAGNET_STRENGTH, new CommandValueDouble(strengthMagnet = new Value<Double>()));
		propertyCommands.put(PROPERTY_PHEROMONES_MAGNET_RANGE, new CommandValueDouble(rangeMagnet = new Value<Double>()));
		propertyCommands.put(PROPERTY_PHEROMONES_MAGNET_ATTRACT, new CommandComponentButtonToggle(buttonModeMagnet = new JToggleButton()));
		propertyCommands.put(PROPERTY_RULE_PATTERN, new CommandComponentMenuDropDownSelectedItemString(menuDropDownPattern = new JComboBox<String>()));
		propertyCommands.put(PROPERTY_RULE_STRATEGY_SIGNAL, new CommandValueBoolean(modeSignal = new Value<Boolean>()));
		propertyCommands.put(PROPERTY_RULE_STRATEGY_SIGNAL_RANGE, new CommandValueDouble(rangeSignal = new Value<Double>()));
		propertyCommands.put(PROPERTY_RULE_POLARITY_DOMINANT, new CommandComponentMenuDropDownSelectedIndex(menuDropDownPolarityDominant = new JComboBox<String>()));
		propertyCommands.put(PROPERTY_RULE_EQUILIBRIUM, new CommandComponentButtonToggle(buttonModeEquilibrium = new JToggleButton()));
		propertyCommands.put(PROPERTY_RULE_AUTOMATIC, new CommandValueBoolean(modeAutomatic = new Value<Boolean>()));
		propertyCommands.put(PROPERTY_RULE_AUTOMATIC_REPETITITONS, new CommandValueInteger(countRepetitionsMaximum = new Value<Integer>()));
		propertyCommands.put(PROPERTY_RULE_AUTOMATIC_STEPS, new CommandValueInteger(countStepsMaximum = new Value<Integer>()));
		for(int indexPolarity = 0; indexPolarity < COUNT_POLARITIES_MAXIMUM; indexPolarity++) {
			propertyCommands.put(PROPERTIES_COLORS_POLARITIES[indexPolarity], new CommandComponentMenuDropDownSelectedItemString(menusDropDownColorsPolarity[indexPolarity] = new JComboBox<String>()));
		}
		propertyCommands.put(PROPERTY_EXPORT_DATA, new CommandValueBoolean(exportData = new Value<Boolean>()));
		propertyCommands.put(PROPERTY_EXPORT_DATA_INTERVAL, new CommandValueInteger(intervalExportData = new Value<Integer>()));
		propertyCommands.put(PROPERTY_EXPORT_DATA_DIRECTORY, new CommandValuePath(pathData = new Value<Path>()));
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT, new CommandValueBoolean(exportScreenshot = new Value<Boolean>()));
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT_INTERVAL, new CommandValueInteger(intervalExportScreenshot = new Value<Integer>()));
		propertyCommands.put(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, new CommandValuePath(pathScreenshot = new Value<Path>()));

		String directoryHome = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		String directorySwarm = "Swarm";

		pathData.setValue(Paths.get(directoryHome, File.separator, directorySwarm, File.separator, "Data"));
		pathScreenshot.setValue(Paths.get(directoryHome, File.separator, directorySwarm, File.separator, "Captures"));

		settings = new Properties();
		settings.setProperty(PROPERTY_EXPORT_DATA_DIRECTORY, pathData.getValue().toString());
		settings.setProperty(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, pathScreenshot.getValue().toString());

		try {
			InputStream streamInput = getClass().getResourceAsStream("/settings/simulation.properties");
			settings.load(streamInput);
			streamInput.close();
		}
		catch (IOException exceptionInput) {
			exceptionInput.printStackTrace();
		}

		frmProjectLegion = new JFrame(TITLE);
		frmProjectLegion.setLayout(new GridBagLayout());
		frmProjectLegion.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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

							if(modeAutomatic.getValue()) {
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
		
		ComponentAdapter listenerFonts = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent event) {
				if(event.getComponent().isVisible()) {
					scaleComponentFonts(event);
				}
			}
			
			@Override
			public void componentShown(ComponentEvent event) {
				scaleComponentFonts(event);
			}

			private void setComponentFonts(Container container, int sizeFont) {
				Font font = new Font("sans-serif", Font.PLAIN, sizeFont);
					
				container.setFont(font);
					
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
							
							if(board != null) {
								setBoard(null);
							}
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

		frmProjectLegion.add(menuBar, createConstraints(0, 0, 1, 1.0, 0.02, INSETS_NONE));

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
					scaleBoard();
				}
			}
		);
		
		panelBoardFrame.add(panelBoard, createConstraints(0, 0, 1, 1.0, 1.0, INSETS_THICK));
				
		panelWorkbench.add(panelBoardFrame, createConstraints(0, 0, 1, 1.0, 1.0, INSETS_THICK));
				
		JPanel panelWorkbenchInformation = new JPanel();
		panelWorkbenchInformation.setLayout(new GridBagLayout());
				
		GridBagConstraints constraintsInformationLabel = createConstraints(0, 0, 1, 0.0, 1.0, INSETS_NONE);
		GridBagConstraints constraintsInformation = createConstraints(1, 0, 1, 0.0, 1.0, INSETS_SLIM);
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
				
		panelWorkbench.add(panelWorkbenchInformation, createConstraints(0, 1, 1, 1.0, 0.02, INSETS_SLIM));
				
		JPanel panelWorkbenchBoard = new JPanel();
		panelWorkbenchBoard.setLayout(new GridBagLayout());
		
		// ************************************************************ This code will
		// open a new JFrame that will ask the user the new dimensions for the new
		// board.
		JFrame frameOptions = new JFrame("Simulation Settings");
		frameOptions.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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
		JLabel labelOptionsStrategy = new JLabel("Strategy:");
		JLabel labelOptionsRangeSignal = new JLabel("Signal Range:");
		JLabel labelOptionsPattern = new JLabel("Pattern:");

		JLabel[] labelsOptionsCountAgents = new JLabel[COUNT_ENTRIES_AGENTS];
		for(int indexLabel = 0; indexLabel < labelsOptionsCountAgents.length; indexLabel++) {
			labelsOptionsCountAgents[indexLabel] = new JLabel(REGISTRY[indexLabel].getName() + " Agents:");
		}
		
		JTextField fieldOptionsSizeBoard = new JFormattedTextField(getIntegerFormatter(Board.BREADTH_MINIMUM));
		
		NumberFormatter formatterInteger = getIntegerFormatter(0);

		JTextField[] fieldsOptionsCountAgents = new JTextField[COUNT_ENTRIES_AGENTS];
		for(int indexField = 0; indexField < fieldsOptionsCountAgents.length; indexField++) {
			fieldsOptionsCountAgents[indexField] = new JFormattedTextField(formatterInteger);
		}

		NumberFormat formatDouble = new DecimalFormat();
		NumberFormatter formatterDouble = new NumberFormatter(formatDouble);
		
		JTextField fieldOptionsRangeSignal = new JFormattedTextField(formatterDouble);
		
		JComboBox<String> menuDropDownOptionsStrategy = new JComboBox<String>();
		menuDropDownOptionsStrategy.setModel(new DefaultComboBoxModel<String>(OPTIONS_PATTERNS_NAMES));
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
		
		JToggleButton buttonModeSignal = new JToggleButton("Signal Transmission");
		buttonModeSignal.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					boolean modeSignal = buttonModeSignal.isSelected();
					
					boolean display;
					for(int indexField = 0; indexField < fieldsOptionsCountAgents.length; indexField++) {
						display = (modeSignal == (REGISTRY[indexField].getMode() == 1));
						
						fieldsOptionsCountAgents[indexField].setVisible(display);
						labelsOptionsCountAgents[indexField].setVisible(display);
					}
					labelOptionsRangeSignal.setVisible(modeSignal);
					fieldOptionsRangeSignal.setVisible(modeSignal);
				}
			}
		);

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

		int estimateOptionsGeneral = 4 + COUNT_ENTRIES_AGENTS;
		
		List<JLabel> labelsOptionsGeneralList = new ArrayList<JLabel>(estimateOptionsGeneral);
		labelsOptionsGeneralList.addAll(Arrays.asList(new JLabel[]{labelOptionsSizeBoard, labelOptionsStrategy}));
		labelsOptionsGeneralList.addAll(Arrays.asList(labelsOptionsCountAgents));
		labelsOptionsGeneralList.addAll(Arrays.asList(new JLabel[]{labelOptionsRangeSignal, labelOptionsPattern}));
		JLabel[] labelsOptionsGeneral = labelsOptionsGeneralList.toArray(new JLabel[labelsOptionsGeneralList.size()]);
		
		List<Component> componentsOptionsGeneralList = new ArrayList<Component>(estimateOptionsGeneral);
		componentsOptionsGeneralList.addAll(Arrays.asList(new Component[]{fieldOptionsSizeBoard, buttonModeSignal}));
		componentsOptionsGeneralList.addAll(Arrays.asList(fieldsOptionsCountAgents));
		componentsOptionsGeneralList.addAll(Arrays.asList(new Component[]{fieldOptionsRangeSignal, menuDropDownOptionsStrategy}));
		Component[] componentsOptionsGeneral = componentsOptionsGeneralList.toArray(new Component[labelsOptionsGeneralList.size()]);
		
		JPanel[] panelsOptions = {panelOptionsGeneral, panelOptionsAutomatic, panelOptionsExportData, panelOptionsExportScreenshot};
		JLabel[][] labelsOptions = {labelsOptionsGeneral, {labelOptionsCountRepetitions, labelOptionsCountSteps}, {labelOptionsExportDataInterval, labelOptionsExportDataPath}, {labelOptionsExportScreenshotInterval, labelOptionsExportScreenshotPath}};
		Component[][] componentsOptions = {componentsOptionsGeneral, {fieldOptionsCountRepetitions, fieldOptionsCountSteps}, {fieldOptionsExportDataInterval, fieldOptionsExportDataPath}, {fieldOptionsExportScreenshotInterval, fieldOptionsExportScreenshotPath}};

		JPanel panel;
		JLabel label;
		Component component;

		for(int indexPanel = 0; indexPanel < panelsOptions.length; indexPanel++) {
			panel = panelsOptions[indexPanel];

			for(int indexComponent = 0; indexComponent < componentsOptions[indexPanel].length; indexComponent++) {
				component = componentsOptions[indexPanel][indexComponent];

				if(indexComponent < labelsOptions[indexPanel].length) {
					constraintsLabel.gridy = indexComponent;
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
					sizeBoard.setValue(Integer.parseInt(fieldOptionsSizeBoard.getText()));
					
					menuDropDownPattern.setSelectedIndex(menuDropDownOptionsStrategy.getSelectedIndex());
					
					int mode;
					boolean buttonModeSignalSelected = buttonModeSignal.isSelected();
					modeSignal.setValue(buttonModeSignalSelected);
					if(buttonModeSignalSelected) {
						String rangeSignalString = fieldOptionsRangeSignal.getText();
						rangeSignal.setValue(Double.parseDouble(rangeSignalString));
						settings.setProperty(PROPERTY_RULE_STRATEGY_SIGNAL_RANGE, rangeSignalString);

						mode = 1;
					}
					else {
						mode = 0;
					}
					settings.setProperty(PROPERTY_RULE_STRATEGY_SIGNAL, Boolean.toString(buttonModeSignalSelected));
					
					int countAgents;
					String countAgentsString;
					for(int indexEntryAgent = 0; indexEntryAgent < fieldsOptionsCountAgents.length; indexEntryAgent++) {
						if(REGISTRY[indexEntryAgent].getMode() == mode) {
							countAgentsString = fieldsOptionsCountAgents[indexEntryAgent].getText();
							countAgents = Integer.parseInt(countAgentsString);
							settings.setProperty(PROPERTIES_AGENTS_COUNTS[indexEntryAgent], countAgentsString);
						}
						else {
							countAgents = 0;
						}
						
						countsAgents[indexEntryAgent] = countAgents;
					}
					
					boolean headerOptionsAutomaticSelected = headerOptionsAutomatic.isSelected();
					modeAutomatic.setValue(headerOptionsAutomaticSelected);
					if(headerOptionsAutomaticSelected) {
						indexRepetition = 0;
						
						String countRepetitionsMaximumString = fieldOptionsCountRepetitions.getText();
						countRepetitionsMaximum.setValue(Integer.parseInt(countRepetitionsMaximumString));
						settings.setProperty(PROPERTY_RULE_AUTOMATIC_REPETITITONS, countRepetitionsMaximumString);

						String countStepsMaximumString = fieldOptionsCountSteps.getText();
						countStepsMaximum.setValue(Integer.parseInt(countStepsMaximumString));
						settings.setProperty(PROPERTY_RULE_AUTOMATIC_STEPS, countStepsMaximumString);
					}
					settings.setProperty(PROPERTY_RULE_AUTOMATIC, Boolean.toString(headerOptionsAutomaticSelected));

					boolean headerOptionsExportSelected = headerOptionsExport.isSelected();
					boolean headerOptionsExportDataSelected = (headerOptionsExportSelected && headerOptionsExportData.isSelected());
					exportData.setValue(headerOptionsExportDataSelected);
					if(headerOptionsExportDataSelected) {
						String intervalExportDataString = fieldOptionsExportDataInterval.getText();
						intervalExportData.setValue(Integer.parseInt(intervalExportDataString));
						settings.setProperty(PROPERTY_EXPORT_DATA_INTERVAL, intervalExportDataString);

						String pathDataString = fieldOptionsExportDataPath.getText();
						Path path = Paths.get(fieldOptionsExportDataPath.getText());
						path.toFile().mkdirs();
						pathData.setValue(path);
						settings.setProperty(PROPERTY_EXPORT_DATA_DIRECTORY, pathDataString);
					}
					settings.setProperty(PROPERTY_EXPORT_DATA, Boolean.toString(headerOptionsExportDataSelected));

					boolean headerOptionsExportScreenshotSelected = (headerOptionsExportSelected && headerOptionsExportScreenshot.isSelected());
					exportScreenshot.setValue(headerOptionsExportScreenshotSelected);
					if(headerOptionsExportScreenshotSelected) {
						String intervalExportScreenshotString = fieldOptionsExportScreenshotInterval.getText();
						intervalExportScreenshot.setValue(Integer.parseInt(intervalExportScreenshotString));
						settings.setProperty(PROPERTY_EXPORT_SCREENSHOT_INTERVAL, intervalExportScreenshotString);

						String pathScreenshotString = fieldOptionsExportScreenshotPath.getText();
						Path path = Paths.get(pathScreenshotString);
						path.toFile().mkdirs();
						pathScreenshot.setValue(path);
						settings.setProperty(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY, pathScreenshotString);
					}
					settings.setProperty(PROPERTY_EXPORT_SCREENSHOT, Boolean.toString(headerOptionsExportScreenshotSelected));

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
					fieldOptionsSizeBoard.setText(settings.getProperty(PROPERTY_BOARD_SIZE));
					for(int indexField = 0; indexField < fieldsOptionsCountAgents.length; indexField++) {
						fieldsOptionsCountAgents[indexField].setText(settings.getProperty(PROPERTIES_AGENTS_COUNTS[indexField]));
					}
					
					fieldOptionsRangeSignal.setText(settings.getProperty(PROPERTY_RULE_STRATEGY_SIGNAL_RANGE));
					
					fieldOptionsCountRepetitions.setText(settings.getProperty(PROPERTY_RULE_AUTOMATIC_REPETITITONS));
					fieldOptionsCountSteps.setText(settings.getProperty(PROPERTY_RULE_AUTOMATIC_STEPS));
							
					fieldOptionsExportDataInterval.setText(settings.getProperty(PROPERTY_EXPORT_DATA_INTERVAL));
					fieldOptionsExportDataPath.setText(settings.getProperty(PROPERTY_EXPORT_DATA_DIRECTORY));
					fieldOptionsExportScreenshotInterval.setText(settings.getProperty(PROPERTY_EXPORT_SCREENSHOT_INTERVAL));
					fieldOptionsExportScreenshotPath.setText(settings.getProperty(PROPERTY_EXPORT_SCREENSHOT_DIRECTORY));
					
					menuDropDownOptionsStrategy.setSelectedIndex(menuDropDownPattern.getSelectedIndex());
					
					setButtonSelected(buttonModeSignal, modeSignal.getValue());
					setButtonSelected(headerOptionsAutomatic, Boolean.parseBoolean(settings.getProperty(PROPERTY_RULE_AUTOMATIC)));
					setButtonSelected(headerOptionsExport, exportData.getValue() || exportScreenshot.getValue());
					setButtonSelected(headerOptionsExportData, exportData.getValue());
					setButtonSelected(headerOptionsExportScreenshot, exportScreenshot.getValue());
					
					frameOptions.setVisible(true);
				}
			}
		);
		buttonSetup.setBackground(new Color(170, 240, 255));
		panelWorkbenchBoard.add(buttonSetup, createConstraints(0, 0, 1, 1.0, 1.0, INSETS_SLIM));

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
		panelWorkbenchBoard.add(buttonScreenshot, createConstraints(1, 0, 1, 0.44, 1.0, INSETS_SLIM));
				
		panelWorkbench.add(panelWorkbenchBoard, createConstraints(0, 2, 1, 1.0, 0.06, INSETS_SLIM));
				
		JPanel panelWorkbenchSwarm = new JPanel();
		panelWorkbenchSwarm.setLayout(new GridBagLayout());
				
		// button to freeze swarm agents
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
		panelWorkbenchSwarm.add(buttonSwarmActive, createConstraints(0, 0, 1, 0.44, 1.0, INSETS_SLIM));
				
		JPanel panelWorkbenchSwarmSlider = new JPanel();
		panelWorkbenchSwarmSlider.setLayout(new GridBagLayout());
				
		// ************************************************************ Slider for the
		// user to change how fast the board will step
		sliderRateSwarm.setMinimum(1);
		sliderRateSwarm.setMaximum(2 * RATE_STEPS_MAXIMUM);
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
						
		panelWorkbenchSwarm.add(panelWorkbenchSwarmSlider, createConstraints(1, 0, 1, 1.0, 1.0, INSETS_SLIM));
				
		panelWorkbench.add(panelWorkbenchSwarm, createConstraints(0, 3, 1, 1.0, 0.02, INSETS_SLIM));
				
		paneSplit.setLeftComponent(panelWorkbench);
		
		// This is where the tabs for the layer options go.
		JTabbedPane paneTabbed = new JTabbedPane(SwingConstants.TOP);
		paneTabbed.setMinimumSize(new Dimension(0, 0));
		paneTabbed.addComponentListener(listenerFonts);
		paneTabbed.addChangeListener(
			new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent event) {
					indexLayerDisplay = ((JTabbedPane)event.getSource()).getSelectedIndex();
					
					if(board != null) {
						board.setDisplayedLayer(indexLayerDisplay);
					}
				}
			}
		);

		// ************************************************************ TAB 1 **************************************************************
		// ************************************************************ TAB 1 **************************************************************
		JPanel tabLayerBase = new JPanel();
		tabLayerBase.setBackground(COLOR_ACCENT);
		tabLayerBase.setLayout(new GridBagLayout());

		// ************************************************************ Labels
		// displaying information

		int countStates = CellTalliedBase.getMaximumStateCount();
		labelsFrequencyColorsInitialText = new JLabel[countStates];
		labelsFrequencyColorsInitial = new JLabel[countStates];
		labelsFrequencyColorsText = new JLabel[countStates];
		labelsFrequencyColors = new JLabel[countStates];
		String nameColor;
		for(int indexLabel = 0; indexLabel < countStates; indexLabel++) {
			nameColor = getColorName(CellTalliedBase.retrieveCellColor(indexLabel));
			nameColor = nameColor.substring(0, 1) + nameColor.substring(1).toLowerCase();

			labelsFrequencyColorsInitial[indexLabel] = new JLabel("0");
			labelsFrequencyColorsInitial[indexLabel].setHorizontalAlignment(SwingConstants.LEFT);
			tabLayerBase.add(labelsFrequencyColorsInitial[indexLabel], createConstraints(1, indexLabel, 1, 0.04, 0.6, INSETS_SLIM));
			
			labelsFrequencyColorsInitialText[indexLabel] = new JLabel("Initial " + nameColor + " Cells:");	
			labelsFrequencyColorsInitialText[indexLabel].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsFrequencyColorsInitialText[indexLabel].setLabelFor(labelsFrequencyColorsInitial[indexLabel]);
			tabLayerBase.add(labelsFrequencyColorsInitialText[indexLabel], createConstraints(0, indexLabel, 1, 0.04, 0.6, INSETS_NONE));

			labelsFrequencyColors[indexLabel] = new JLabel("0");
			labelsFrequencyColors[indexLabel].setHorizontalAlignment(SwingConstants.LEFT);
			tabLayerBase.add(labelsFrequencyColors[indexLabel], createConstraints(1, indexLabel + 3, 1, 0.04, 0.6, INSETS_SLIM));
			
			labelsFrequencyColorsText[indexLabel] = new JLabel("Current " + nameColor + " Cells:");	
			labelsFrequencyColorsText[indexLabel].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsFrequencyColorsText[indexLabel].setLabelFor(labelsFrequencyColors[indexLabel]);
			tabLayerBase.add(labelsFrequencyColorsText[indexLabel], createConstraints(0, indexLabel + 3, 1, 0.04, 0.6, INSETS_NONE));
		}

		// ************************************************************ Text field that
		// will trigger when button pushed, this is the value that will be the new board
		// size
		fieldSizeBoard = new JTextField("20", 10); //Does NOT work
		tabLayerBase.add(fieldSizeBoard, createConstraints(1, 6, 1, 1.0, 1.0, INSETS_SLIM));

		// ************************************************************ Change Size of
		// the Board Button
		JButton buttonSizeBoard = new JButton("Update Size"); //DOES NOT WORK YET
		buttonSizeBoard.setForeground(Color.LIGHT_GRAY);
		buttonSizeBoard.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					if(board != null) {
						
					}
				}
			}
		);
		tabLayerBase.add(buttonSizeBoard, createConstraints(2, 6, 1, 1.0, 1.0, INSETS_SLIM));

		JLabel labelSizeBoardUpdate = new JLabel("Board Size:");
		labelSizeBoardUpdate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSizeBoardUpdate.setLabelFor(fieldSizeBoard);
		tabLayerBase.add(labelSizeBoardUpdate, createConstraints(0, 6, 1, 0.04, 1.0, INSETS_NONE));

		NumberFormatter formatterIntegerPercent = getIntegerFormatter(0);
		formatterIntegerPercent.setMaximum(100);
		
		//MODIFICATION #2  stores the % of random cells to flip, the user may change the number
		fieldFlip = new JFormattedTextField(formatterIntegerPercent);
		fieldFlip.setText("20");
		tabLayerBase.add(fieldFlip, createConstraints(1, 7, 1, 1.0, 1.0, INSETS_SLIM));

		//MODIFICATION #2  button will flip the percent of cells based on number in text field
		JButton buttonFlip = new JButton("Flip Cells");
		buttonFlip.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(board != null) {
						//set number in the text field to an int variable
						//Call method that will cause some cells to flip
						board.flipCells(Math.min(Integer.parseInt(fieldFlip.getText()), 100));
					}
				}
			}
		);
		tabLayerBase.add(buttonFlip, createConstraints(2, 7, 1, 1.0, 1.0, INSETS_SLIM));
		
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
			tabLayerPolarity.add(labelsPolaritiesPercent[indexPolarity], createConstraints(1, indexPolarity, 1, 1.0, 1.0, INSETS_SLIM));
			
			labelsPolarityComparison[indexPolarity] = new JLabel("false");
			labelsPolarityComparison[indexPolarity].setHorizontalAlignment(SwingConstants.LEFT);
			tabLayerPolarity.add(labelsPolarityComparison[indexPolarity], createConstraints(1, COUNT_POLARITIES_MAXIMUM + 4 + indexPolarity, 1, 1.0, 1.0, INSETS_SLIM));
			
			labelsPolaritiesPercentText[indexPolarity] = new JLabel("(" + (indexPolarity + 1) + ") " + getColorName(colorsPolarity[indexPolarity]) + ':');
			labelsPolaritiesPercentText[indexPolarity].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsPolaritiesPercentText[indexPolarity].setLabelFor(labelsPolaritiesPercent[indexPolarity]);
			labelsPolaritiesPercentText[indexPolarity].setVisible(false);
			tabLayerPolarity.add(labelsPolaritiesPercentText[indexPolarity], createConstraints(0, indexPolarity, 1, 0.02, 1.0, INSETS_NONE));
			
			labelsPolarityComparisonText[indexPolarity] = new JLabel();
			labelsPolarityComparisonText[indexPolarity].setHorizontalAlignment(SwingConstants.RIGHT);
			labelsPolarityComparisonText[indexPolarity].setLabelFor(labelsPolarityComparison[indexPolarity]);
			tabLayerPolarity.add(labelsPolarityComparisonText[indexPolarity], createConstraints(0, COUNT_POLARITIES_MAXIMUM + 4 + indexPolarity, 1, 0.02, 1.0, INSETS_SLIM));
		}
		
		// ************************************************************ Updates the
		// polarity to what is entered on the radio buttons
		menuDropDownPattern.setModel(new DefaultComboBoxModel<String>(OPTIONS_PATTERNS_NAMES));
		menuDropDownPattern.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String nameStrategy = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
					goalStrategy = getPattern(nameStrategy);

					if(board == null) {
						updatePolarityCount();
					}
					else {
						board.setPattern(goalStrategy);
					}

					settings.setProperty(PROPERTY_RULE_PATTERN, nameStrategy);
				}
			}
		);
		tabLayerPolarity.add(menuDropDownPattern, createConstraints(1, COUNT_POLARITIES_MAXIMUM, 1, 1.0, 1.0, INSETS_SLIM));

		JLabel labelGoal = new JLabel("Goal:");
		labelGoal.setHorizontalAlignment(SwingConstants.RIGHT);
		labelGoal.setLabelFor(menuDropDownPattern);
		tabLayerPolarity.add(labelGoal, createConstraints(0, COUNT_POLARITIES_MAXIMUM, 1, 0.02, 1.0, INSETS_NONE));

		//MODIFICATION #5:
		//Added 6/12 by Morgan Might
		//
		//This button will determine if the agents should follow rules to get a single polarity
		//or try to balance the 3 different polarities.
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

					updatePolarityEquilibriumLabelsText();
				}
			}
		);
		tabLayerPolarity.add(buttonModeEquilibrium, createConstraints(0, COUNT_POLARITIES_MAXIMUM + 1, 2, 1.0, 1.0, INSETS_SLIM));

		// ************************************************************ Sets what the
		// polarity ratios should be for the two colors.
		JComboBox<String> menuDropDownStabilityRegional = new JComboBox<String>(); //Does not work
		menuDropDownStabilityRegional.setModel(new DefaultComboBoxModel<String>(new String[] {"50/50", "60/40", "70/30", "80/20", "90/10", "100/0" }));
		tabLayerPolarity.add(menuDropDownStabilityRegional, createConstraints(1, COUNT_POLARITIES_MAXIMUM + 2, 1, 1.0, 1.0, INSETS_SLIM));

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
		menuDropDownPolarityDominant.setModel(optionsPolarityDominant);
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
		tabLayerPolarity.add(menuDropDownPolarityDominant, createConstraints(1, COUNT_POLARITIES_MAXIMUM + 3, 1, 1.0, 1.0, INSETS_SLIM));
		
		JLabel labelColorPolarity;
		for(int indexMenu = 0; indexMenu < menusDropDownColorsPolarity.length; indexMenu++) {
			menusDropDownColorsPolarity[indexMenu].setModel(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
			menusDropDownColorsPolarity[indexMenu].addItemListener(new ItemListenerIndexedColorPolarity(indexMenu));
			tabLayerPolarity.add(menusDropDownColorsPolarity[indexMenu], createConstraints(1, 2 * COUNT_POLARITIES_MAXIMUM + 4 + indexMenu, 1, 1.0, 1.0, INSETS_SLIM));
			
			labelColorPolarity = new JLabel("Polarity (" + (indexMenu + 1) + ") Color:");
			labelColorPolarity.setHorizontalAlignment(SwingConstants.RIGHT);
			labelColorPolarity.setLabelFor(menusDropDownColorsPolarity[indexMenu]);
			tabLayerPolarity.add(labelColorPolarity, createConstraints(0, 2 * COUNT_POLARITIES_MAXIMUM + 4 + indexMenu, 1, 0.02, 1.0, INSETS_SLIM));
		}
		
		paneTabbed.addTab("Polarity Layer", tabLayerPolarity);

		// ************************************************************ TAB 3 *************************************************************
		// ************************************************************ TAB 3 *************************************************************
		JPanel tabLayerInteractive = new JPanel();
		tabLayerInteractive.setBackground(COLOR_ACCENT);
		tabLayerInteractive.setLayout(new GridBagLayout());
		
		int positionY = 0;

		// ************************************************************ User can set the
		// number of agents
		fieldCountAgentsNormal = new JTextField("4", 10);
		tabLayerInteractive.add(fieldCountAgentsNormal, createConstraints(1, positionY, 1, 1.0, 1.0, INSETS_SLIM));

		JButton buttonCountAgents = new JButton("Update Agents");
		buttonCountAgents.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonCountAgents, createConstraints(2, positionY, 2, 1.0, 1.0, INSETS_SLIM));
		
		JLabel labelCountAgentsUpdate = new JLabel("Number of Agents:");
		labelCountAgentsUpdate.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCountAgentsUpdate.setForeground(Color.LIGHT_GRAY);
		labelCountAgentsUpdate.setLabelFor(fieldCountAgentsNormal);
		tabLayerInteractive.add(labelCountAgentsUpdate, createConstraints(0, positionY++, 1, 0.04, 1.0, INSETS_NONE));

		// ************************************************************ User can select
		// how many changes the agent can make
		fieldCountModifications = new JTextField("-1", 10);
		tabLayerInteractive.add(fieldCountModifications, createConstraints(1, positionY, 1, 1.0, 1.0, INSETS_SLIM));

		JButton buttonCountModifications = new JButton("Update Changes");
		buttonCountModifications.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonCountModifications, createConstraints(2, positionY, 2, 1.0, 1.0, INSETS_SLIM));
				
		JLabel labelCountModifications = new JLabel("Number of Changes:");
		labelCountModifications.setHorizontalAlignment(SwingConstants.RIGHT);
		labelCountModifications.setForeground(Color.LIGHT_GRAY);
		labelCountModifications.setLabelFor(fieldCountModifications);
		tabLayerInteractive.add(labelCountModifications, createConstraints(0, positionY++, 1, 0.04, 1.0, INSETS_NONE));

		// ************************************************************ User can choose
		// how close an agent can get to another. 0 implies that many spaces between,
		// thus they could overlap.
		fieldAgentCloseness = new JTextField("0", 10);
		tabLayerInteractive.add(fieldAgentCloseness, createConstraints(1, positionY, 1, 1.0, 1.0, INSETS_SLIM));

		JButton buttonAgentCloseness = new JButton("Update Closeness");
		buttonAgentCloseness.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonAgentCloseness, createConstraints(2, positionY, 2, 1.0, 1.0, INSETS_SLIM));

		JLabel labelAgentCloseness = new JLabel("Agent Closeness:");
		labelAgentCloseness.setHorizontalAlignment(SwingConstants.RIGHT);
		labelAgentCloseness.setForeground(Color.LIGHT_GRAY);
		labelAgentCloseness.setLabelFor(fieldAgentCloseness);
		tabLayerInteractive.add(labelAgentCloseness, createConstraints(0, positionY++, 1, 0.04, 1.0, INSETS_NONE));
		
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
		tabLayerInteractive.add(buttonWraparound, createConstraints(0, positionY++, 4, 1.0, 1.0, INSETS_SLIM));
		
		GridBagConstraints constraintsButtonAgentVisibility = createConstraints(2, positionY, 2, 1.0, 1.0, INSETS_SLIM);
		constraintsButtonAgentVisibility.gridheight = COUNT_ENTRIES_AGENTS;
		
		// ************************************************************ User can change
		// the color of the agents
		JComboBox<String> menuDropDownColorAgents;
		JLabel labelAgentColor;
		for(int indexEntryAgent = 0; indexEntryAgent < COUNT_ENTRIES_AGENTS; indexEntryAgent++) {
			menuDropDownColorAgents = menusDropDownColorsAgents[indexEntryAgent];
			
			menuDropDownColorAgents.setModel(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
			menuDropDownColorAgents.addItemListener(new ItemListenerIndexedColorAgents(indexEntryAgent));
			tabLayerInteractive.add(menuDropDownColorAgents, createConstraints(1, positionY, 1, 1.0, 1.0, INSETS_SLIM));
			
			labelAgentColor = new JLabel(REGISTRY[indexEntryAgent].getName() + " Agent Color:");
			labelAgentColor.setHorizontalAlignment(SwingConstants.RIGHT);
			labelAgentColor.setLabelFor(menuDropDownColorAgents);
			tabLayerInteractive.add(labelAgentColor, createConstraints(0, positionY++, 1, 0.04, 1.0, INSETS_NONE));
		}
		
		buttonAgentVisiblity.setText("View Agents");
		buttonAgentVisiblity.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					swarmVisible = buttonAgentVisiblity.isSelected();

					if(board != null) {
						board.toggleAgentVisibility();
					}

					settings.setProperty(PROPERTY_AGENTS_VISIBLE, Boolean.toString(swarmVisible));
				}
			}
		);
		tabLayerInteractive.add(buttonAgentVisiblity, constraintsButtonAgentVisibility);

		JButton buttonPheromoneTrailAdd = new JButton("Set Phrmn Trail");
		buttonPheromoneTrailAdd.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneTrailAdd, createConstraints(0, positionY, 2, 1.0, 1.0, INSETS_SLIM));

		JButton buttonPheromoneTrailRemove = new JButton("Remove Phrmn Trail");
		buttonPheromoneTrailRemove.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneTrailRemove, createConstraints(2, positionY++, 2, 1.0, 1.0, INSETS_SLIM));

		JButton buttonPheromoneZoneAdd = new JButton("Set Phrmn Zone");
		buttonPheromoneZoneAdd.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneZoneAdd, createConstraints(0, positionY, 2, 1.0, 1.0, INSETS_SLIM));

		JButton buttonPheromoneZoneRemove = new JButton("Remove Phrmn Zone");
		buttonPheromoneZoneRemove.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonPheromoneZoneRemove, createConstraints(2, positionY++, 2, 1.0, 1.0, INSETS_SLIM));

		// ************************************************************ User can set how
		// strongly the agents should follow the swarm.
		fieldStrengthPheromone = new JTextField("1", 10);
		tabLayerInteractive.add(fieldStrengthPheromone, createConstraints(1, positionY, 1, 1.0, 1.0, INSETS_SLIM));

		JButton buttonStrengthPheromone = new JButton("Update P Strength");
		buttonStrengthPheromone.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(buttonStrengthPheromone, createConstraints(2, positionY, 2, 1.0, 1.0, INSETS_SLIM));
				
		JLabel labelStrengthPheromone = new JLabel("Pheromone Strength:");
		labelStrengthPheromone.setHorizontalAlignment(SwingConstants.RIGHT);
		labelStrengthPheromone.setForeground(Color.LIGHT_GRAY);
		labelStrengthPheromone.setLabelFor(fieldStrengthPheromone);
		tabLayerInteractive.add(labelStrengthPheromone, createConstraints(0, positionY++, 1, 0.04, 1.0, INSETS_NONE));
		
		// ************************************************************ User changes the
		// color of the pheromone trails on the board.
		JComboBox<String> menuDropDownColorPheromone = new JComboBox<String>();
		menuDropDownColorPheromone.setModel(new DefaultComboBoxModel<String>(OPTIONS_COLORS_NAMES));
		tabLayerInteractive.add(menuDropDownColorPheromone, createConstraints(1, positionY, 3, 1.0, 1.0, INSETS_SLIM));
		
		JLabel labelColorPheromoneTrail = new JLabel("Pheromone Color:");
		labelColorPheromoneTrail.setHorizontalAlignment(SwingConstants.RIGHT);
		labelColorPheromoneTrail.setForeground(Color.LIGHT_GRAY);
		tabLayerInteractive.add(labelColorPheromoneTrail, createConstraints(0, positionY++, 1, 0.04, 1.0, INSETS_NONE));
		
		buttonModeMagnet.setText("Attract");
		buttonModeMagnet.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (modeMagnetAttract = buttonModeMagnet.isSelected()) {
						buttonModeMagnet.setText("Attract");
					}
					else {
						buttonModeMagnet.setText("Repel");
					}
					
					if(board != null) {
						board.setAttractorMode(modeMagnetAttract);
					}
					
					settings.setProperty(PROPERTY_PHEROMONES_MAGNET_ATTRACT, Boolean.toString(modeMagnetAttract));
				}
			}
		);
		tabLayerInteractive.add(buttonModeMagnet, createConstraints(0, positionY++, 4, 1.0, 1.0, INSETS_SLIM));

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
		return swarmVisible;
	}

	public boolean getBoardWraparound() {
		return boardWraparound;
	}
	
	public boolean getStrategySignalMode() {
		return modeSignal.getValue();
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
		return modeMagnetAttract;
	}

	public int getDominantPolarity() {
		return indexPolarityDominant;
	}
	
	public int getSelectedLayerIndex() {
		return indexLayerDisplay;
	}
	
	public double getMagnetStrength() {
		return strengthMagnet.getValue();
	}
	
	public double getMagnetRange() {
		return rangeMagnet.getValue();
	}
	
	public double getSignalRange() {
		return rangeSignal.getValue();
	}

	public Pattern getPattern() {
		return goalStrategy;
	}

	public Board getBoard() {
		return board;
	}
	
	public Color[] getAgentColors() {
		return colorsAgents;
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

		if(board != null) {
			sizeBoard.setValue(board.getBreadth());
			
			countsAgents = board.getAgentCounts();
			int countAgents;
			int countAgentsTotal = 0;
			for(int indexEntryAgent = 0; indexEntryAgent < COUNT_ENTRIES_AGENTS; indexEntryAgent++) {
				countAgents = countsAgents[indexEntryAgent];
				
				countAgentsTotal += countAgents;
			}
			
			frequencyColorsInitial = board.getInitialColorFrequencies();
			frequencyColors = board.getColorFrequencies();
			frequencyPolarities = board.getPolarityFrequencies();
			
			String sizeBoardString = Integer.toString(sizeBoard.getValue());

			settings.setProperty(PROPERTY_BOARD_SIZE, sizeBoardString);

			labelSizeBoardValue.setText(sizeBoardString);
			labelCountAgentsValue.setText(Integer.toString(countAgentsTotal));
			labelCountStepsValue.setText(Integer.toString(indexStep = 0));
			
			panelBoard.add(board);
			scaleBoard();
			
			updateInitialColorFrequencyLabels();

			if(exportData.getValue()) {
				try {
					writerData = new BufferedWriter(new FileWriter(pathData.getValue() + "\\Swarm-Frequencies_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + ".csv"));
					addDataColumnHeaders(writerData);
					outputPolarityData(writerData);
				}
				catch (IOException e) {
					e.printStackTrace();
				}

				if(modeAutomatic.getValue()) {
					storeAveragePolarityData(indexStep);
				}
			}
			
			frmProjectLegion.validate();
			
			if(exportScreenshot.getValue()) {
				try {
					ImageIO.write(board.capture(), "JPG", new File(pathScreenshot.getValue() + File.separator + "Simulation_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + FILETYPE_SCREENSHOT));
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
	
	private void setAgentsColor(int indexEntryAgent, Color colorAgents) {
		colorsAgents[indexEntryAgent] = colorAgents;
		
		if(board != null) {
			board.setAgentColor(indexEntryAgent, colorAgents);
		}

		settings.setProperty(PROPERTIES_COLORS_AGENTS[indexEntryAgent], getColorName(colorAgents));
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
		else {
			updatePolarityEquilibriumLabelsText();
		}
		
		if(board != null) {
			board.updatePolarityColor(indexPolarity);
		}

		settings.setProperty(PROPERTIES_COLORS_POLARITIES[indexPolarity], getColorName(colorsPolarity[indexPolarity]));
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

		updatePolarityEquilibriumLabelsText();
	}

	public void updatePolarityCount() {
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
			updatePolarityEquilibriumLabelsText();
		}

		buttonModeEquilibrium.setVisible(countPolarities > 2);
	}
	
	public void updateInitialColorFrequencyLabels() {
		for(int indexLabel = 0; indexLabel < labelsFrequencyColorsInitial.length; indexLabel++) {
			labelsFrequencyColorsInitial[indexLabel].setText(Integer.toString(frequencyColorsInitial[indexLabel]));
		}
		
		updateColorFrequencyLabels();
	}
	
	public void updateColorFrequencyLabels() {
		for(int indexLabel = 0; indexLabel < labelsFrequencyColors.length; indexLabel++) {
			labelsFrequencyColors[indexLabel].setText(Integer.toString(frequencyColors[indexLabel]));
		}
		
		updatePolarityPercentageLabels();
	}
	
	public void updatePolarityPercentageLabels() {
		for(int indexLabel = 0; indexLabel < labelsPolaritiesPercent.length; indexLabel++) {
			labelsPolaritiesPercent[indexLabel].setText((frequencyPolarities[indexLabel] / (board.getCellCount() / 100d)) + "%");
		}
		
		updatePolarityEquilibriumLabels();
	}
	
	private void updatePolarityEquilibriumLabels() {
		int indexLabelComparisonSum = goalStrategy.getPolarityCount() - 1;
		
		int indexPolarity = 0;
		int frequencyPolarity;
		int frequencyPolarityDominant = frequencyPolarities[indexPolarityDominant];
		int frequencyPolaritiesSum = 0;
		for(int indexLabel = 0; indexLabel < indexLabelComparisonSum; indexLabel++) {
			if(indexPolarity == indexPolarityDominant) {
				indexPolarity++;
			}

			frequencyPolarity = frequencyPolarities[indexPolarity++];
			labelsPolarityComparison[indexLabel].setText(Boolean.toString(frequencyPolarity < frequencyPolarityDominant));
			frequencyPolaritiesSum += frequencyPolarity;
		}

		labelsPolarityComparison[indexLabelComparisonSum].setText(Boolean.toString(frequencyPolarityDominant < frequencyPolaritiesSum));
	}
	
	private void updatePolarityEquilibriumLabelsText() {
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
			
			if(board != null) {
				updatePolarityEquilibriumLabels();
			}
		}
		else {
			countPolarities = 0;
		}

		for(int indexLabel = countPolarities; indexLabel < labelsPolarityComparisonText.length; indexLabel++) {
			labelsPolarityComparison[indexLabel].setVisible(false);
			labelsPolarityComparisonText[indexLabel].setVisible(false);
		}
	}
	
	public void scaleBoard() {
		if(board != null) {
			int sizeBoard = Math.min(panelBoard.getWidth(), panelBoard.getHeight());
			Dimension dimensionsBoard = new Dimension(sizeBoard, sizeBoard);
			board.setMinimumSize(dimensionsBoard);
			board.setPreferredSize(dimensionsBoard);
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

	public Board generateBoard() {
		return new Board(sizeBoard.getValue(), countsAgents, this);
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

		updateColorFrequencyLabels();
		updatePolarityPercentageLabels();
		updatePolarityEquilibriumLabels();
		
		labelCountStepsValue.setText(Integer.toString(indexStep));
		
		if(exportData.getValue() && indexStep % intervalExportData.getValue() == 0) {
			outputPolarityData(writerData);

			if(modeAutomatic.getValue()) {
				storeAveragePolarityData(indexStep);
			}
		}

		if(exportScreenshot.getValue() && indexStep % intervalExportScreenshot.getValue() == 0) {
			if(timerReady) {
				toggleTimer();
			}

			try {
				ImageIO.write(board.capture(), "JPG", new File(pathScreenshot.getValue() + File.separator + "Simulation_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + FILETYPE_SCREENSHOT));
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			if(timerReady) {
				toggleTimer();
			}
		}

		if(modeAutomatic.getValue()) {
			
			if(indexStep >= countStepsMaximum.getValue()) {
				indexRepetition++;
				
				if(indexRepetition >= countRepetitionsMaximum.getValue()) {
					buttonSwarmActive.setSelected(true);
					buttonSwarmActive.doClick();

					if(exportData.getValue()) {
						outputAveragePolarityData();
					}
					
					modeAutomatic.setValue(false);
				}
				else {
					setBoard(generateBoard());
				}
			}
		}
	}

	private void storeAveragePolarityData(int index) {
		double[] frequencyPolaritiesAverage = new double[COUNT_POLARITIES_MAXIMUM + 1];
		frequencyPolaritiesAverage[0] = indexStep;

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
			BufferedWriter writer = new BufferedWriter(new FileWriter(pathData.getValue() + "\\Swarm-Frequencies-Averages_" + FORMATTER_TIMESTAMP.format(LocalDateTime.now()) + ".csv"));

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

	private static void setButtonSelected(JToggleButton button, boolean select) {
		button.setSelected(!select);
		button.doClick();
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

	private static Pattern getPattern(String namePattern) {
		namePattern = namePattern.toUpperCase();

		if(MAP_PATTERNS.containsKey(namePattern)) {
			return MAP_PATTERNS.get(namePattern);
		}

		StringBuilder messageError = new StringBuilder("Error: Invalid Pattern '");
		messageError.append(namePattern);
		messageError.append("' (Allowed Patterns:");
		for(String namePatternKey : MAP_COLORS.keySet()) {
			messageError.append(' ');
			messageError.append(namePatternKey);
		}
		messageError.append(')');
		System.out.println(messageError.toString());

		return MAP_PATTERNS.get("CHECKERBOARD");
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
	
	private static String[] applySuffixes(String template, String[] suffixes) {
		String[] product = new String[suffixes.length];
		
		for(int indexSuffix = 0; indexSuffix < product.length; indexSuffix++) {
			product[indexSuffix] = template + suffixes[indexSuffix];
		}
		
		return product;
	}
	
	private static String[] applySuffixes(String template, int suffixMinimum, int suffixMaximum) {
		String[] suffixes = new String[1 + suffixMaximum - suffixMinimum];
		
		for(int indexSuffix = 0; indexSuffix < suffixes.length; indexSuffix++) {
			suffixes[indexSuffix] = Integer.toString(suffixMinimum + indexSuffix);
		}
		
		return applySuffixes(template, suffixes);
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

	private static HashMap<String, Pattern> generateMapStrategies() {
		HashMap<String, Pattern> mapStrategies = new HashMap<String, Pattern>();

		for(int indexStrategy = 0; indexStrategy < OPTIONS_STRATEGIES.length; indexStrategy++) {
			mapStrategies.put(OPTIONS_PATTERNS_NAMES[indexStrategy], OPTIONS_STRATEGIES[indexStrategy]);
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
	
	private abstract class ItemListenerIndexedColor implements ItemListener {
		protected int index;
		
		public ItemListenerIndexedColor(int index) {
			this.index = index;
		}
		
		@Override
		public final void itemStateChanged(ItemEvent event) {
			if(event.getStateChange() == ItemEvent.SELECTED) {
				itemSelected(getColor((String)(event.getItem())));
			}
		}
		
		protected abstract void itemSelected(Color colorSelected);
	}
	
	private class ItemListenerIndexedColorPolarity extends ItemListenerIndexedColor {
		public ItemListenerIndexedColorPolarity(int index) {
			super(index);
		}

		@Override
		protected void itemSelected(Color colorSelected) {
			setPolarityColor(index, colorSelected);
		}
	};
	
	private class ItemListenerIndexedColorAgents extends ItemListenerIndexedColor {
		public ItemListenerIndexedColorAgents(int index) {
			super(index);
		}

		@Override
		protected void itemSelected(Color colorSelected) {
			setAgentsColor(index, colorSelected);
		}
	};
	
	private class Value<T> {
		T value;
		
		public T getValue() {
			return value;
		}
		
		public void setValue(T value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value.toString();
		}
	}

	private abstract class Command {
		public abstract void execute(String valueString);
	}
	
	private abstract class CommandValue<T> extends Command {
		private Value<T> value;
		
		public CommandValue(Value<T> value) {
			this.value = value;
		}
		
		@Override
		public void execute(String valueString) {
			value.setValue(parseValue(valueString));
		}
		
		protected abstract T parseValue(String valueString);
	}
	
	private class CommandValueBoolean extends CommandValue<Boolean> {
		public CommandValueBoolean(Value<Boolean> value) {
			super(value);
		}
		
		@Override
		protected Boolean parseValue(String valueString) {
			return Boolean.parseBoolean(valueString);
		}
	}
	
	private class CommandValueInteger extends CommandValue<Integer> {
		public CommandValueInteger(Value<Integer> value) {
			super(value);
		}
		
		@Override
		protected Integer parseValue(String valueString) {
			return Integer.parseInt(valueString);
		}
	}
	
	private class CommandValueDouble extends CommandValue<Double> {
		public CommandValueDouble(Value<Double> value) {
			super(value);
		}
		
		@Override
		protected Double parseValue(String valueString) {
			return Double.parseDouble(valueString);
		}
	}
	
	private class CommandValuePath extends CommandValue<Path> {
		public CommandValuePath(Value<Path> value) {
			super(value);
		}
		
		@Override
		protected Path parseValue(String value) {
			return Paths.get(value);
		}
	}
	
	private abstract class CommandComponent<T extends Component> extends Command {
		protected T component;
		
		public CommandComponent(T component) {
			this.component = component;
		}
	}
	
	private class CommandComponentButtonToggle extends CommandComponent<JToggleButton> {
		public CommandComponentButtonToggle(JToggleButton component) {
			super(component);
		}

		@Override
		public void execute(String valueString) {
			setButtonSelected(component, Boolean.parseBoolean(valueString));
		}
	}
	
	private class CommandComponentMenuDropDownSelectedIndex extends CommandComponent<JComboBox<?>> {
		public CommandComponentMenuDropDownSelectedIndex(JComboBox<?> component) {
			super(component);
		}

		@Override
		public void execute(String valueString) {
			int countOptions = component.getModel().getSize();
			
			if(countOptions > 0) {
				component.setSelectedIndex(Math.min(Math.max(0, (Integer.parseInt(valueString) - 1)), countOptions));
			}
		}
	}
	
	private class CommandComponentMenuDropDownSelectedItemString extends CommandComponent<JComboBox<?>> {
		public CommandComponentMenuDropDownSelectedItemString(JComboBox<String> component) {
			super(component);
		}

		@Override
		public void execute(String valueString) {
			component.setSelectedItem(valueString);
		}
	}
	
	private class CommandComponentSlider extends CommandComponent<JSlider> {
		public CommandComponentSlider(JSlider component) {
			super(component);
		}

		@Override
		public void execute(String valueString) {
			component.setValue(Integer.parseInt(valueString));
		}
	}
	
	private abstract class CommandElementArray<T> extends Command {
		protected int index;
		protected T[] array;
		
		public CommandElementArray(T[] array, int index) {
			this.array = array;
			this.index = index;
		}
		
		@Override
		public void execute(String valueString) {
			array[index] = parseValue(valueString);
		}
		
		protected abstract T parseValue(String valueString);
	}
	
	private class CommandElementArrayInteger extends CommandElementArray<Integer> {
		public CommandElementArrayInteger(Integer[] array, int index) {
			super(array, index);
		}

		@Override
		protected Integer parseValue(String valueString) {
			return Integer.valueOf(valueString);
		}
	}
}