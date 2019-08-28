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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

import cells.CellDisplayBase;
import cells.CellDisplayCorrectness;
import cells.Cell;
import cells.CellDisplay;
import cells.CellDisplayPersistence;
import cells.CellDisplayPolarity;
import patterns.Pattern;
import strategies.Strategy;
import strategies.StrategyPolarityAlternator;
import strategies.StrategyPolarityEdges;
import strategies.StrategyPolaritySignal;
import swarm.Motion;
import swarm.MotionEdge;
import swarm.MotionFree;
import swarm.MotionOrthogonal;
import swarm.MotionStationary;
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
	private static final double STRENGTH_ATTRACTOR = 1.0;
	
	private boolean swarmVisible;
	private boolean wraparound; //whether the walls of the Board wrap or not; by default, they don't
	private boolean modeAttractor;
	private int breadth; //these are the numbers of cells in the board, NOT the graphical dimensions of the board
	private int countCells;
	private int countAgents, countAgentsNormal, countAgentsSpecial, countAgentsAlternator; //MODIFICATION
	private int indexLayerDisplay;
	private double sizeCell; //pixel dimensions of each cell
	private double rangeAttractor; //distance in cells, not pixels
	private Color colorAgentsNormal, colorAgentsSpecial, colorAgentsAlternator, colorAgentsEdger;
	private Pattern pattern;//strategy that the agents and layer 2 use for their calculations given the current goal
	private GUI gui;
	private int[] frequencyColorsInitial;
	private int[] frequencyColors;
	private int[] frequencyPolarities;
	private SwarmAgent[] agents, agentsStrategyPatternDefault, agentsNormal, agentsSpecial, agentsAlternator, agentsEdger;
	private CellDisplayBase[][] layerBase;
	private CellDisplayPolarity[][] layerPolarity;
	private CellDisplayPersistence[][] layerPersistence;  //MODIFICATION #7: new layer of cells for persistency
	private CellDisplayCorrectness[][] layerCorrectness;
	private CellDisplay[][] layerDisplay; //layer to paint
	private CellDisplay[][][] layers;

	public Board(int breadth, int countAgentsNormal, int countAgentsSpecial, int countAgentsAlternator, int countAgentsEdger, GUI gui) {
		this.breadth = breadth;
		this.countAgentsNormal = countAgentsNormal;
		this.countAgentsSpecial = countAgentsSpecial;
		this.countAgentsAlternator = countAgentsAlternator;
		this.gui = gui;
		
		wraparound = gui.getBoardWraparound();
		swarmVisible = gui.getAgentVisibility();
		modeAttractor = gui.getAttractorMode();
		pattern = gui.getPattern();
		
		frequencyColorsInitial = new int[CellDisplayBase.getMaximumStateCount()];
		frequencyColors = new int[frequencyColorsInitial.length];
		frequencyPolarities = new int[GUI.COUNT_POLARITIES_MAXIMUM];
		
		countCells = breadth * breadth;
		
		//set the graphical dimensions of the cells themselves. the cells are always square, but the
		//space they take up is constrained by the width and height of the board and by the number of cells.
		sizeCell = (SCALE_BOARD / breadth);
		double sizeAgent = (sizeCell * 0.7);

		rangeAttractor = sizeCell * 5; //the attractor affects everything in a five cell block radius

		layers = new CellDisplay[][][]{null, layerBase = new CellDisplayBase[breadth][breadth], layerPolarity = new CellDisplayPolarity[breadth][breadth], null, layerPersistence = new CellDisplayPersistence[breadth][breadth], layerCorrectness = new CellDisplayCorrectness[breadth][breadth]};
		
		Random generatorNumbersRandom = ThreadLocalRandom.current();
		
		//layer 1
		int countStates = pattern.getStateCount();
		for (int indexRow = 0; indexRow < layerBase.length; indexRow++) {
			for (int indexColumn = 0; indexColumn < layerBase[indexRow].length; indexColumn++) {
				layerBase[indexRow][indexColumn] = new CellDisplayBase(indexRow * sizeCell, indexColumn * sizeCell, sizeCell, generatorNumbersRandom.nextInt(countStates), this);
			}
		}

		for (int indexRow = 0; indexRow < breadth; indexRow++) {
			for (int indexColumn = 0; indexColumn < breadth; indexColumn++) {
				layerPolarity[indexRow][indexColumn] = pattern.createPolarityCell(this, indexRow, indexColumn);
				layerPersistence[indexRow][indexColumn] = new CellDisplayPersistence(indexRow * sizeCell, indexColumn * sizeCell, sizeCell, this);
				layerCorrectness[indexRow][indexColumn] = pattern.createCorrectnessCell(this, indexRow, indexColumn);
			}
		}
		
		//********************************************************************************************************	
		//*************************MODIFICATION******************************************************************
		//********************************************************************************************************		
		//generates the swarm and adjusts their positions
		colorAgentsNormal = gui.getAgentColor();
		colorAgentsSpecial = gui.getSpecialAgentColor();
		colorAgentsAlternator = gui.getAlternatorAgentColor();
		colorAgentsEdger = gui.getEdgerAgentColor();
		
		int countAgentsStandard = countAgentsNormal + countAgentsSpecial;
		countAgents = countAgentsStandard + countAgentsAlternator + countAgentsEdger;
		
		agents = new SwarmAgent[countAgents];
		agentsNormal = new SwarmAgent[countAgentsNormal];
		agentsSpecial = new SwarmAgent[countAgentsSpecial];
		
		int indexAgent = 0;
		SwarmAgent agent;
		Motion motionSwarm;
		Strategy strategySwarm;
		
		if(gui.getStrategySignalMode()) {
			strategySwarm = new StrategyPolaritySignal(agentsSpecial);
			motionSwarm = new MotionStationary();
			double rangeSignal = gui.getSignalRange();
			for(int indexAgentSignalTransmitter = 0; indexAgentSignalTransmitter < agentsSpecial.length; indexAgentSignalTransmitter++) {
				agent = new SwarmAgent(generatorNumbersRandom.nextDouble() * SCALE_BOARD, generatorNumbersRandom.nextDouble() * SCALE_BOARD, sizeAgent, colorAgentsSpecial, this, motionSwarm, true, strategySwarm, rangeSignal);
				agentsSpecial[indexAgentSignalTransmitter] = agent;
				agents[indexAgent++] = agent;
			}
			
			motionSwarm = new MotionFree();
			for(int indexAgentSignalReceiver = 0; indexAgentSignalReceiver < agentsNormal.length; indexAgentSignalReceiver++) {
				agent = new SwarmAgent(generatorNumbersRandom.nextDouble() * SCALE_BOARD, generatorNumbersRandom.nextDouble() * SCALE_BOARD, sizeAgent, colorAgentsNormal, this, motionSwarm, false, strategySwarm);
				agentsNormal[indexAgentSignalReceiver] = agent;
				agents[indexAgent++] = agent;
			}
		}
		else {
			agentsStrategyPatternDefault = new SwarmAgent[countAgentsStandard];
			strategySwarm = pattern.getDefaultStrategy();
			motionSwarm = new MotionFree();
			
			agentsSpecial = new SwarmAgent[countAgentsSpecial];
			for(int indexAgentStandardSpecial = 0; indexAgentStandardSpecial < agentsSpecial.length; indexAgentStandardSpecial++) {
				agent = new SwarmAgent(generatorNumbersRandom.nextDouble() * SCALE_BOARD, generatorNumbersRandom.nextDouble() * SCALE_BOARD, sizeAgent, colorAgentsSpecial, this, motionSwarm, false, strategySwarm);
				agentsSpecial[indexAgentStandardSpecial] = agent;
				agentsStrategyPatternDefault[indexAgent] = agent;
				agents[indexAgent++] = agent;
			}
			
			for(int indexAgentStandardNormal = 0; indexAgentStandardNormal < agentsNormal.length; indexAgentStandardNormal++) {
				agent = new SwarmAgent(generatorNumbersRandom.nextDouble() * SCALE_BOARD, generatorNumbersRandom.nextDouble() * SCALE_BOARD, sizeAgent, colorAgentsNormal, this, motionSwarm, false, strategySwarm);
				agentsNormal[indexAgentStandardNormal] = agent;
				agentsStrategyPatternDefault[indexAgent] = agent;
				agents[indexAgent++] = agent;
			}
			
			motionSwarm = new MotionOrthogonal();
			strategySwarm = new StrategyPolarityAlternator();
			agentsAlternator = new SwarmAgent[countAgentsAlternator];
			for(int indexAgentAlternator = 0; indexAgentAlternator < agentsAlternator.length; indexAgentAlternator++) {
				agent = new SwarmAgent(generatorNumbersRandom.nextDouble() * SCALE_BOARD, generatorNumbersRandom.nextDouble() * SCALE_BOARD, sizeAgent, colorAgentsAlternator, this, motionSwarm, false, strategySwarm);
				agentsAlternator[indexAgentAlternator] = agent;
				agents[indexAgent++] = agent;
			}
			
			motionSwarm = new MotionEdge();
			strategySwarm = new StrategyPolarityEdges();
			agentsEdger = new SwarmAgent[countAgentsEdger];
			for(int indexAgentEdger = 0; indexAgentEdger < agentsEdger.length; indexAgentEdger++) {
				agent = new SwarmAgent(generatorNumbersRandom.nextDouble() * SCALE_BOARD, generatorNumbersRandom.nextDouble() * SCALE_BOARD, sizeAgent, colorAgentsEdger, this, motionSwarm, false, strategySwarm);
				agentsEdger[indexAgentEdger] = agent;
				agents[indexAgent++] = agent;
			}
		}
		
		addMouseMotionListener(this);

		if (gui.indexLayerDisplay == 3) {
			gui.indexLayerDisplay = 1;
		}
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
	
	public int getNormalAgentCount() {
		return countAgentsNormal;
	}
	
	public int getSpecialAgentCount() {
		return countAgentsSpecial;
	}
	
	public int getAlternatorAgentCount() {
		return countAgentsAlternator;
	}
	
	public double getCellSize() {
		return sizeCell;
	}
	
	public Pattern getPattern() {
		return pattern;
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
	
	public CellDisplayBase[][] getBaseLayer() {
		return layerBase;
	}
	
	public CellDisplayPolarity[][] getPolarityLayer() {
		return layerPolarity;
	}
	
	public CellDisplayPersistence[][] getPersistenceLayer() {
		return layerPersistence;
	}
	
	public CellDisplayCorrectness[][] getCorrectnessLayer(){
		return layerCorrectness;
	}
	
	public void setAttractorMode(boolean modeAttractor) {
		this.modeAttractor = modeAttractor;
	}
	
	public void setAgentColor(SwarmAgent[] agents, Color colorAgents) {
		for (SwarmAgent agent : agents) {
			agent.setColor(colorAgents);
		}
		
		repaint();
	}
	
	/**
	 * @author zgray17
	 * This method updates the color of the agents. Blah blah blah.
	 * @param colorAgentsNormal
	 */
	public void setNormalAgentColor(Color colorAgentsNormal) {
		if(!this.colorAgentsNormal.equals(colorAgentsNormal)) {
			setAgentColor(agentsNormal, this.colorAgentsNormal = colorAgentsNormal);
		}
	}

	public void setSpecialAgentColor(Color colorAgentsSpecial) {
		if(!this.colorAgentsSpecial.equals(colorAgentsSpecial)) {
			setAgentColor(agentsSpecial, this.colorAgentsSpecial = colorAgentsSpecial);
		}
	}
	
	public void setAlternatorAgentColor(Color colorAgentsAlternator) {
		if(!this.colorAgentsAlternator.equals(colorAgentsAlternator)) {
			setAgentColor(agentsAlternator, this.colorAgentsAlternator = colorAgentsAlternator);
		}
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
			CellDisplayPolarity cell;
			for(int indexRow = 0; indexRow < layerPolarity.length; indexRow++) {
				for(int indexColumn = 0; indexColumn < layerPolarity[indexRow].length; indexColumn++) {
					if((cell = layerPolarity[indexRow][indexColumn]).getState() == indexPolarity) {
						cell.updatePolarityColor();
					}
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics helperGraphics) {
		//draw boards
		if(gui.indexLayerDisplay == 3) {
			gui.indexLayerDisplay = indexLayerDisplay;
			repaint();
		}
		else {
			super.paintComponent(helperGraphics);

			Graphics2D helperGraphics2D = (Graphics2D)helperGraphics;
			helperGraphics2D.scale(getWidth() / SCALE_BOARD, getHeight() / SCALE_BOARD);
			
			layerDisplay = layers[indexLayerDisplay = gui.indexLayerDisplay];
			
			for(int indexRow = 0; indexRow < layerDisplay.length; indexRow++) {
				for(int indexColumn = 0; indexColumn < layerDisplay[indexRow].length; indexColumn++) {
					layerDisplay[indexRow][indexColumn].draw(helperGraphics2D);
				}
			}
			
			//draw agents
			if(swarmVisible) {
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
		for (SwarmAgent agent : agents) {
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
		Cell[] neighbors = new CellDisplay[9];
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
		if(!modeAttractor) {
			multiplier *= -1;
		}
		
		double distance, distanceX, distanceY;
		double multiplierScale;
		Random generatorNumbersRandom = ThreadLocalRandom.current();
		for (SwarmAgent agent : agents) {
			distance = Math.hypot(distanceX = (event.getX() - agent.getCenterX()), distanceY = (event.getY() - agent.getCenterY()));

			if (distance < rangeAttractor) {
				//then the agent is in the specified range
				if (generatorNumbersRandom.nextDouble() < STRENGTH_ATTRACTOR) {
					multiplierScale = multiplier / distance;
					agent.setVelocityCells(multiplierScale * distanceX, multiplierScale * distanceY);
				}
				else {
					System.out.println("failed");
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

	public GUI getGui() {
		return gui;
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