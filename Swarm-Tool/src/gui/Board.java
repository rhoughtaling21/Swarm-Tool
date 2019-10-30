package gui;

/*
 *		Authors: Zakary Gray, Tim Dobeck, Nick Corrado, Gabriel Petkac
 *		Description:  This is currently the main class for all intents and purposes.  The board holds the cells of layers 1 and 2
 *               as well as the agents in the layer 3 swarm. The jframe of Board are displayed in the GUI after a new board is created
 *               in NewBoardWindow.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

import cells.CellTalliedBase;
import cells.CellCorrectness;
import cells.Cell;
import cells.RectangleCellDisplay;
import cells.CellPersistence;
import cells.CellTalliedPolarity;
import patterns.Pattern;
import strategies.Strategy;
import swarm.RegisterDefinitionsAgent.DefinitionAgent;
import swarm.SwarmAgent;
/*
 * Authors: Nick, Tim, Zak, Gabriel
 * Description: This is the guts of the program. Two 2x2 Cell arrays of size[size X size] are created to be layers 1 and 2,
 * Layer 2 gives information about layer 1, for example... Layer one currently tells which polarity (black in the corners or white)
 * of a checker board the cells in layer 1 are in.  An array of Agents is also created with random movement over the layers 1 & 2
 * while randomly changing the cells underneath them. In the future, the agents will have a low level of intelligence.
 * Parameters: width of board in pixels, height of board in pixels, number of cells on a side, and number of Agents
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseMotionListener {
	public static final int BREADTH_MINIMUM = 1;
	public static final double SCALE_BOARD = 800;
	
	private static final int COUNT_ENTRIES_AGENTS = GUI.COUNT_ENTRIES_AGENTS;
	
	private boolean swarmVisible;
	private boolean wraparound; //whether the walls of the Board wrap or not; by default, they don't
	private boolean modeMagnetAttract;
	private int breadth; //these are the numbers of cells in the board, NOT the graphical dimensions of the board
	private int countCells;
	private int indexLayerDisplay;
	private double sizeCell; //pixel dimensions of each cell
	private double rangeMagnet; //distance in cells, not pixels
	private double strengthMagnet;
	private Pattern pattern;//strategy that the agents and layer 2 use for their calculations given the current goal
	private GUI gui;
	private int[] frequencyColorsInitial;
	private int[] frequencyColors;
	private int[] frequencyPolarities;
	private Integer[] countsAgents;
	private Color[] colorsAgents;
	private SwarmAgent[] agentsSignalTransmitter, agentsStrategyPatternDefault;
	private SwarmAgent[][] swarm;
	private CellTalliedBase[][] layerBase;
	private CellTalliedPolarity[][] layerPolarity;
	private CellPersistence[][] layerPersistence;  //MODIFICATION #7: new layer of cells for persistency
	private CellCorrectness[][] layerCorrectness;
	private RectangleCellDisplay[][] layerDisplay; //layer to paint
	private Cell[][][] layers;

	public Board(int breadth, Integer[] countsAgents, GUI gui) {
		this.breadth = breadth;
		this.countsAgents = countsAgents;
		this.gui = gui;
		
		wraparound = gui.getBoardWraparound();
		swarmVisible = gui.getAgentVisibility();
		modeMagnetAttract = gui.getAttractorMode();
		strengthMagnet = gui.getMagnetStrength();
		indexLayerDisplay = gui.getSelectedLayerIndex();
		pattern = gui.getPattern();
		
		frequencyColorsInitial = new int[CellTalliedBase.getMaximumStateCount()];
		frequencyColors = new int[frequencyColorsInitial.length];
		frequencyPolarities = new int[GUI.COUNT_POLARITIES_MAXIMUM];
		
		countCells = breadth * breadth;
		
		//set the graphical dimensions of the cells themselves. the cells are always square, but the
		//space they take up is constrained by the width and height of the board and by the number of cells.
		sizeCell = (SCALE_BOARD / breadth);
		double sizeAgent = (sizeCell * 0.7);

		rangeMagnet = sizeCell * gui.getMagnetRange(); //the attractor affects everything in a five cell block radius
		
		
		layers = new Cell[][][]{layerBase = new CellTalliedBase[breadth][breadth], layerPolarity = new CellTalliedPolarity[breadth][breadth], null, layerPersistence = new CellPersistence[breadth][breadth], layerCorrectness = new CellCorrectness[breadth][breadth]};

		layerDisplay = new RectangleCellDisplay[breadth][breadth];
		
		if(layers[indexLayerDisplay] == null) {
			indexLayerDisplay = 0;
		}
		
		Cell cellBase, cellPolarity, cellPersistence, cellCorrectness;
		for(int indexRow = 0; indexRow < breadth; indexRow++) {
			for(int indexColumn = 0; indexColumn < breadth; indexColumn++) {
				cellBase = (layerBase[indexRow][indexColumn] = new CellTalliedBase(this));
				cellPolarity = (layerPolarity[indexRow][indexColumn] = pattern.createPolarityCell(this, indexRow, indexColumn));
				cellPersistence = (layerPersistence[indexRow][indexColumn] = new CellPersistence(this));
				cellCorrectness = (layerCorrectness[indexRow][indexColumn] = pattern.createCorrectnessCell(this, indexRow, indexColumn));
				
				layerDisplay[indexRow][indexColumn] = new RectangleCellDisplay(indexRow * sizeCell, indexColumn * sizeCell, sizeCell, new Cell[]{cellBase, cellPolarity, null, cellPersistence, cellCorrectness}, indexLayerDisplay);
			}
		}
		
		//********************************************************************************************************	
		//*************************MODIFICATION******************************************************************
		//********************************************************************************************************		
		//generates the swarm and adjusts their positions
		colorsAgents = gui.getAgentColors();
		
		swarm = new SwarmAgent[COUNT_ENTRIES_AGENTS][];
		
		ArrayList<SwarmAgent> agentsStrategyPatternDefaultList = new ArrayList<SwarmAgent>();
		
		int countAgents;
		Color colorAgents;
		SwarmAgent agent;
		DefinitionAgent definition;
		DefinitionAgent[] registry = GUI.REGISTRY;
		for(int indexEntryAgent = 0; indexEntryAgent < COUNT_ENTRIES_AGENTS; indexEntryAgent++) {
			definition = registry[indexEntryAgent];
			
			countAgents = countsAgents[indexEntryAgent];
			colorAgents = colorsAgents[indexEntryAgent];
			
			swarm[indexEntryAgent] = new SwarmAgent[countAgents];
			
			for(int indexAgent = 0; indexAgent < countAgents; indexAgent++) {
				agent = new SwarmAgent(sizeAgent, colorAgents, this, definition);
				
				swarm[indexEntryAgent][indexAgent] = agent;
			}
			
			if(definition.getStrategy() == null) {
				agentsStrategyPatternDefaultList.addAll(Arrays.asList(swarm[indexEntryAgent]));
			}
		}
		
		agentsStrategyPatternDefault = agentsStrategyPatternDefaultList.toArray(agentsStrategyPatternDefault = new SwarmAgent[] {});
		agentsSignalTransmitter = swarm[4];
		
		if(agentsSignalTransmitter != null) {
			double rangeSignal = gui.getSignalRange();
			for(SwarmAgent agentSignalTransmitter : agentsSignalTransmitter) {
				agentSignalTransmitter.setSignalRange(rangeSignal);
			}
		}
		
		addMouseMotionListener(this);
	}
	
	public boolean getWraparound() {
		return wraparound;
	}
	
	public int getBreadth() {
		return breadth;
	}
	
	public int getCellCount() {
		return countCells;
	}
	
	public Integer[] getAgentCounts() {
		return countsAgents;
	}
	
	public double getCellSize() {
		return sizeCell;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public GUI getGui() {
		return gui;
	}
	
	public int[] getInitialColorFrequencies() {
		return frequencyColorsInitial;
	}
	
	public int[] getColorFrequencies() {
		return frequencyColors;
	}
	
	public int[] getPolarityFrequencies() {
		return frequencyPolarities;
	}
	
	public SwarmAgent[] getSignalTransmitterAgents() {
		return agentsSignalTransmitter;
	}
	
	public CellTalliedBase[][] getBaseLayer() {
		return layerBase;
	}
	
	public CellTalliedPolarity[][] getPolarityLayer() {
		return layerPolarity;
	}
	
	public CellPersistence[][] getPersistenceLayer() {
		return layerPersistence;
	}
	
	public CellCorrectness[][] getCorrectnessLayer(){
		return layerCorrectness;
	}
	
	public void setAttractorMode(boolean modeAttractor) {
		this.modeMagnetAttract = modeAttractor;
	}
	
	public void setDisplayedLayer(int indexLayerDisplay) {
		if(layers[indexLayerDisplay] != null && this.indexLayerDisplay != indexLayerDisplay) {
			this.indexLayerDisplay = indexLayerDisplay;
			
			for(RectangleCellDisplay[] cellRow : layerDisplay) {
				for(RectangleCellDisplay cell : cellRow) {
					cell.setLayer(indexLayerDisplay);
				}
			}
			
			repaint();
		}
	}
	
	public void setAgentColor(int indexEntryAgent, Color colorAgents) {
		for(SwarmAgent agent : swarm[indexEntryAgent]) {
			agent.setColor(colorAgents);
		}
		
		repaint();
	}

	public void setPattern(Pattern pattern) {
		if(!pattern.equals(this.pattern)) {
			this.pattern = pattern;
			
			if(agentsStrategyPatternDefault != null) {
				Strategy strategySwarm = pattern.getDefaultStrategy();
				
				for(SwarmAgent agent : agentsStrategyPatternDefault) {
					agent.setStrategy(strategySwarm);
				}
			}
			
			gui.updatePolarityCount();
			updatePolarityCells();
		}
	}
	
	public void toggleAgentVisibility() {
		swarmVisible = !swarmVisible;
		
		repaint();
	}
	
	public int calculateAgentRow(SwarmAgent agent) {
		return (int)(agent.getCenterX() / sizeCell);
	}
	
	public int calculateAgentColumn(SwarmAgent agent) {
		return (int)(agent.getCenterY() / sizeCell);
	}
	
	public void updatePolarityColor(int indexPolarity) {
		if(indexPolarity < pattern.getPolarityCount()) {
			CellTalliedPolarity cell;
			for(int indexRow = 0; indexRow < layerPolarity.length; indexRow++) {
				for(int indexColumn = 0; indexColumn < layerPolarity[indexRow].length; indexColumn++) {
					if((cell = layerPolarity[indexRow][indexColumn]).getState() == indexPolarity) {
						cell.updateColor();
					}
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics helperGraphics) {
		super.paintComponent(helperGraphics);

		Graphics2D helperGraphics2D = (Graphics2D)helperGraphics;
		helperGraphics2D.scale(getWidth() / SCALE_BOARD, getHeight() / SCALE_BOARD);
			
		for(int indexRow = 0; indexRow < layerDisplay.length; indexRow++) {
			for(int indexColumn = 0; indexColumn < layerDisplay[indexRow].length; indexColumn++) {
				layerDisplay[indexRow][indexColumn].draw(helperGraphics2D);
			}
		}
			
		//draw agents
		if(swarmVisible) {
			for(SwarmAgent[] agents : swarm) {
				for(SwarmAgent agent : agents) {
					agent.draw(helperGraphics2D);
				}
			}
		}
	}
	
	/**
	 * @author Nick, zgray17, Tim
	 * This method handles the "stepping forward" of the simulation, which for now just means updating the positions of all
	 * of the agents and changing the colors of the board's cells based on these agents' actions.
	 */
	public void step() {
		//for each agent, have the agent decide randomly whether to flip its cell's color
		
		int indexRow, indexColumn;
		for(SwarmAgent[] agents : swarm) {
			for(SwarmAgent agent : agents) {
				//10% of the time, the agent will determine algebraically which cell it's in, then flip the color of that cell.
				//a better approach than this would be to have the agent store which cell it's currently in, then just flip that
				//color 10% of the time. this would also make it easy to keep the agent from flipping the same cell many times
				//before leaving it--something we haven't gotten to yet.

				//TESTING NEIGHBORS
				//neighbors = getNeighbors(layer1, (int)(agent.getCenterX() / cellSize), (int)(agent.getCenterY() / cellSize));
				indexRow = calculateAgentRow(agent);
				indexColumn = calculateAgentColumn(agent);
				
				if(indexRow >= 0 && indexRow < layerBase.length && indexColumn >= 0 && indexColumn < layerBase[indexRow].length) {
					agent.logic();
					
					for (int row = 0; row < layerPolarity.length; row++) {
						for (int col = 0; col < layerPolarity[row].length; col++) {
							pattern.updatePolarityCell(this, row, col);
						}
					}
				}

				agent.step();
				
				updateCorrectnessCells();
			}
		}
	}

	/**
	 * @author Nick
	 * This method takes in the board's cells and a particular row and column number and returns an array
	 * of all of the neighbors of the cell whose row and column number has been provided. For our purposes,
	 * "neighbor" means the eight cells directly and diagonally adjacent to the given cell. In the future,
	 * that can be restricted by the given user-defined rules to just the cells directly adjacent. This
	 * must also be revised to take into account whether the Board has wrap or not.
	 * @param layerPolarity2
	 * @param indexRow
	 * @param indexColumn
	 * @return an array of all of the neighbors of the cell whose row and column number has been provided.
	 */
	public Cell[] getNeighbors(Cell[][] layerPolarity2, int indexRow, int indexColumn) {
		//each cell only has 8 neighbors! for now at least.... :(
		int indexNeighbor = 0;
		int indexColumnNeighbor;
		int indexRowNeighborMaximum = indexRow + 1;
		int indexColumnNeighborMinimum = indexColumn - 1;
		int indexColumnNeighborMaximum = indexColumn + 1;
		Cell[] neighbors = new Cell[9];
		for(int indexRowNeighbor = indexRow - 1; indexRowNeighbor <= indexRowNeighborMaximum; indexRowNeighbor++) {
			for(indexColumnNeighbor = indexColumnNeighborMinimum; indexColumnNeighbor <= indexColumnNeighborMaximum; indexColumnNeighbor++) {
				if(indexRowNeighbor != indexRow || indexColumnNeighbor != indexColumn) {
					if(indexRowNeighbor >= 0 && indexRowNeighbor < breadth && indexColumnNeighbor >= 0 && indexColumnNeighbor < breadth) {
						neighbors[indexNeighbor] = layerPolarity2[indexRowNeighbor][indexColumnNeighbor];
					}
					else if(wraparound) {
						int indexRowNeighborWrapped;
						if(indexRowNeighbor < 0 || indexRowNeighbor >= breadth) {
							indexRowNeighborWrapped = (((indexRowNeighbor % breadth) + breadth) % breadth);
						}
						else {
							indexRowNeighborWrapped = indexRowNeighbor;
						}
						
						int indexColumnNeighborWrapped;
						if(indexColumnNeighbor < 0 || indexColumnNeighbor >= breadth) {
							indexColumnNeighborWrapped = (((indexColumnNeighbor % breadth) + breadth) % breadth);
						}
						else {
							indexColumnNeighborWrapped = indexColumnNeighbor;
						}
						
						neighbors[indexNeighbor] = layerPolarity2[indexRowNeighborWrapped][indexColumnNeighborWrapped];
					}
				}
				
				indexNeighbor++;
			}
		}
		
		return neighbors;
	}

	@Override
	//if you click in a spot on the board, the agents will be attracted to that spot with decreasing effect
	//the further away they are
	public void mouseDragged(MouseEvent event) {
		/* Offset the cursor's location to account for the Board having been resized */
		int offsetX = (int)(event.getX() * SCALE_BOARD / getWidth()) - event.getX();
		int offsetY = (int)(event.getY() * SCALE_BOARD / getHeight()) - event.getY();
		event.translatePoint(offsetX, offsetY);
		
		double multiplier = 1;
		if(!modeMagnetAttract) {
			multiplier *= -1;
		}
		
		double distance, distanceX, distanceY;
		double multiplierScale;
		Random generatorNumbersRandom = ThreadLocalRandom.current();
		for (SwarmAgent[] agents : swarm) {
			for(SwarmAgent agent : agents) {
				distance = Math.hypot(distanceX = (event.getX() - agent.getCenterX()), distanceY = (event.getY() - agent.getCenterY()));

				if (distance < rangeMagnet) {
					//then the agent is in the specified range
					if (generatorNumbersRandom.nextDouble() < strengthMagnet) {
						multiplierScale = multiplier / distance;
						agent.setVelocityCells(multiplierScale * distanceX, multiplierScale * distanceY);
					}
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {

	}
	
	public void updatePolarityCells() {
		for (int indexRow = 0; indexRow < layerPolarity.length; indexRow++) {
			for (int indexColumn = 0; indexColumn < layerPolarity[indexRow].length; indexColumn++) {
				pattern.updatePolarityCell(this, indexRow, indexColumn);
			}
		}
		
		gui.updatePolarityPercentageLabels();
		
		updateCorrectnessCells();
	}
	
	public void updateCorrectnessCells() {
		for (int indexRow = 0; indexRow < layerCorrectness.length; indexRow++) {
			for (int indexColumn = 0; indexColumn < layerCorrectness[indexRow].length; indexColumn++) {
				pattern.updateCorrectnessCell(this, indexRow, indexColumn);
			}
		}
		
		repaint();
	}
	
	public void toggleWraparound() {
		wraparound = !wraparound;
	}

	//MODIFICATION #2: This method selects random cells to flip
	public void flipCells(int percentCellsFlip) {
		int countCellsFlip = (int)(countCells * (percentCellsFlip / 100d));
		boolean[] flipCell = new boolean[countCells];
		
		Random generatorNumbersRandom = ThreadLocalRandom.current();
		while(countCellsFlip > 0) {
			int indexCellFlip = generatorNumbersRandom.nextInt(countCells);
			if(!flipCell[indexCellFlip]) {
				flipCell[indexCellFlip] = true;
				countCellsFlip--;
			}			
		}
		
		int indexCell = 0;
		for (int indexRow = 0; indexRow < layerBase.length; indexRow++) {
			for (int indexColumn = 0; indexColumn < layerBase[indexRow].length; indexColumn++) {
				if(flipCell[indexCell]) {
					layerBase[indexRow][indexColumn].shiftState();
					pattern.updatePolarityCell(this, indexRow, indexColumn);
					System.out.println("FLIP: " + indexCell);
				}
				
				indexCell++;
			}
		}
		
		repaint();
		gui.updateColorFrequencyLabels();
	}

	public void adjustInitialColorFrequency(int stateAdjust, int adjustment) {
		frequencyColorsInitial[stateAdjust] += adjustment;
	}
	
	public void adjustColorFrequency(int colorAdjust, int adjustment) {
		frequencyColors[colorAdjust] += adjustment;
	}
	
	public void adjustPolarityFrequency(int colorAdjust, int adjustment) {
		frequencyPolarities[colorAdjust] += adjustment;
	}
	
	public BufferedImage capture() {
		BufferedImage image = new BufferedImage((int)SCALE_BOARD, (int)SCALE_BOARD, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D helperGraphics2D = image.createGraphics();
		helperGraphics2D.scale(image.getWidth() / (double)getWidth(), image.getHeight() / (double)getHeight());
		
		paint(helperGraphics2D);
		
		return image;
	}
}