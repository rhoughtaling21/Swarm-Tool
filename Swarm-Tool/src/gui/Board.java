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
import javax.swing.JPanel;

import agents.SwarmAgent;
import cells.CellDisplayBase;
import cells.CellDisplayCorrectness;
import cells.CellDisplay;
import cells.CellDisplayPersistence;
import cells.CellDisplayPolarity;
import planes.Plane;
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
	
	private boolean showAgents;
	private boolean wraparound; //whether the walls of the Board wrap or not; by default, they don't
	private boolean modeAttractor;
	public int breadth; //these are the numbers of cells in the board, NOT the graphical dimensions of the board
	private int countAgents, countAgentsSpecial; //MODIFICATION
	private int indexDisplay;
	private double sizeCell; //pixel dimensions of each cell
	private double attractorStrength = 1;
	private double rangeAttractor; //distance in cells, not pixels
	private Color colorAgents, colorAgentsSpecial;
	private Plane strategy;//strategy that the agents and layer 2 use for their calculations given the current goal
	private GUI gui;
	private int[] frequencyColorsInitial;
	private int[] frequencyColors;
	private int[] frequencyPolarities;
	private SwarmAgent[] agents, agentsNormal, agentsSpecial;
	private CellDisplayBase[][] layerBase;
	private CellDisplayPolarity[][] layerPolarity;
	private CellDisplayPersistence[][] layerPersistence;  //MODIFICATION #7: new layer of cells for persistency
	private CellDisplayCorrectness[][] layerCorrectness;
	private CellDisplay[][] layerDisplay; //layer to paint
	private CellDisplay[][][] layers;

	public Board(int breadth, int countAgents, int countAgentsSpecial, GUI gui) {
		this.breadth = breadth;
		this.countAgents = countAgents;
		this.countAgentsSpecial = countAgentsSpecial;
		this.gui = gui;
		
		wraparound = gui.getBoardWraparound();
		showAgents = gui.getAgentVisibility();
		modeAttractor = gui.getAttractorMode();
		strategy = gui.getStrategy();
		
		frequencyColorsInitial = new int[CellDisplayBase.getMaximumStateCount()];
		frequencyColors = new int[frequencyColorsInitial.length];
		frequencyPolarities = new int[GUI.COUNT_POLARITIES_MAXIMUM];
		
		//set the graphical dimensions of the cells themselves. the cells are always square, but the
		//space they take up is constrained by the width and height of the board and by the number of cells.
		sizeCell = (SCALE_BOARD / breadth);
		double sizeAgent = (sizeCell * 0.7);

		rangeAttractor = sizeCell * 5; //the attractor affects everything in a five cell block radius

		layers = new CellDisplay[][][]{null, layerBase = new CellDisplayBase[breadth][breadth], layerPolarity = new CellDisplayPolarity[breadth][breadth], null, layerPersistence = new CellDisplayPersistence[breadth][breadth], layerCorrectness = new CellDisplayCorrectness[breadth][breadth]};
		
		//layer 1
		if (!gui.getSplitPolarity()) {
			for (int indexRow = 0; indexRow < layerBase.length; indexRow++) {
				for (int indexColumn = 0; indexColumn < layerBase[indexRow].length; indexColumn++) {
					layerBase[indexRow][indexColumn] = new CellDisplayBase(indexRow * sizeCell, indexColumn * sizeCell, sizeCell, (int)(strategy.getStateCount() * Math.random()), this);
				}
			}
		}
		// MODIFICATION: This will test swarms when the polarity is split and the agents are no longer effective
		else if(strategy.getPolarityCount() == 2){
			int pos;
			int stateInitial;
			for (int indexRow = 0; indexRow < layerBase.length; indexRow++) {
				for (int indexColumn = 0; indexColumn < layerBase[indexRow].length; indexColumn++) {
					if(Math.random() < 0.5) {  
						pos = indexRow;
					}
					else { 
						pos = indexColumn;
					}
						
					if (pos < (3 + (int)(Math.random() * (breadth - 5)))) {
						if (indexRow % 2 == indexColumn % 2) {
							stateInitial = 0;
						}
						else {
							stateInitial = 1;
						}
					} 
					else {
						if (indexRow % 2 == indexColumn % 2) {
							stateInitial = 1;
						}
						else {
							stateInitial = 0;
						}
					}
					
					layerBase[indexRow][indexColumn] = new CellDisplayBase(indexRow * sizeCell, indexColumn * sizeCell, sizeCell, stateInitial, this);
				}
			}
		}

		for (int indexRow = 0; indexRow < breadth; indexRow++) {
			for (int indexColumn = 0; indexColumn < breadth; indexColumn++) {
				layerPolarity[indexRow][indexColumn] = strategy.createPolarityCell(this, indexRow, indexColumn);
				layerPersistence[indexRow][indexColumn] = new CellDisplayPersistence(indexRow * sizeCell, indexColumn * sizeCell, sizeCell, this);
				layerCorrectness[indexRow][indexColumn] = strategy.createCorrectnessCell(this, indexRow, indexColumn);
			}
		}
		
		//********************************************************************************************************	
		//*************************MODIFICATION******************************************************************
		//********************************************************************************************************		
		//generates the swarm and adjusts their positions
		
		agents = new SwarmAgent[countAgents + countAgentsSpecial];
		agentsSpecial = new SwarmAgent[countAgentsSpecial];
		colorAgentsSpecial = gui.getSpecialAgentColor();
		int indexAgent = 0;
		for(int indexAgentSpecial = 0; indexAgentSpecial < agentsSpecial.length; indexAgentSpecial++) {
			agentsSpecial[indexAgentSpecial] = new SwarmAgent(Math.random() * SCALE_BOARD, Math.random() * SCALE_BOARD, sizeAgent, colorAgentsSpecial, true, this, strategy.getDefaultStrategy());
			agents[indexAgent++] = agentsSpecial[indexAgentSpecial];
		}
		
		agentsNormal = new SwarmAgent[countAgents];
		colorAgents = gui.getAgentColor();
		for(int indexAgentNormal = 0; indexAgentNormal < agentsNormal.length; indexAgentNormal++) {
			agentsNormal[indexAgentNormal] = new SwarmAgent(Math.random() * SCALE_BOARD, Math.random() * SCALE_BOARD, sizeAgent, colorAgents, false, this, strategy.getDefaultStrategy());
			agents[indexAgent++] = agentsNormal[indexAgentNormal];
		}
		
		addMouseMotionListener(this);

		if (gui.indexLayerDisplay == 3) {
			gui.indexLayerDisplay = 1;
		}
	}
	
	public boolean getWraparound() {
		return wraparound;
	}
	
	public int getAgentCount() {
		return countAgents;
	}
	
	public int getSpecialAgentCount() {
		return countAgentsSpecial;
	}
	
	public double getCellSize() {
		return sizeCell;
	}
	
	public Plane getActiveStrategy() {
		return strategy;
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
	
	/**
	 * @author zgray17
	 * This method updates the color of the agents. Blah blah blah.
	 * @param colorAgents
	 */
	public void setAgentColor(Color colorAgents) {
		if(!this.colorAgents.equals(colorAgents)) {
			this.colorAgents = colorAgents;
			
			for (SwarmAgent agent : agentsNormal) {
				agent.setColor(colorAgents);
			}
			
			repaint();
		}
	}

	public void setSpecialAgentColor(Color colorAgentsSpecial) {
		if(!this.colorAgentsSpecial.equals(colorAgentsSpecial)) {
			this.colorAgentsSpecial = colorAgentsSpecial;
			
			for (SwarmAgent agent : agentsSpecial) {
				agent.setColor(colorAgentsSpecial);
			}
			
			repaint();
		}
	}

	public void setGoalStrategy(Plane strategy) {
		if(!strategy.equals(this.strategy)) {
			this.strategy = strategy;
			
			for(SwarmAgent agent : agents) {
				agent.setStrategy(strategy.getDefaultStrategy());
			}
			updatePolarityCells();
			gui.updatePolarityCount();
		}
	}
	
	public void toggleAgentVisibility() {
		showAgents = !showAgents;
		
		scheduleRepaint();
	}
	
	public int calculateAgentRow(SwarmAgent agent) {
		return (int)(agent.getCenterX() / sizeCell);
	}
	
	public int calculateAgentColumn(SwarmAgent agent) {
		return (int)(agent.getCenterY() / sizeCell);
	}
	
	public void updatePolarityColor(int indexPolarity) {
		if(indexPolarity < strategy.getPolarityCount()) {
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
			gui.indexLayerDisplay = indexDisplay;
			repaint();
		}
		else {
			super.paintComponent(helperGraphics);

			Graphics2D helperGraphics2D = (Graphics2D)helperGraphics;
			helperGraphics2D.scale(getWidth() / SCALE_BOARD, getHeight() / SCALE_BOARD);
			
			layerDisplay = layers[indexDisplay = gui.indexLayerDisplay];
			
			for(int indexRow = 0; indexRow < layerDisplay.length; indexRow++) {
				for(int indexColumn = 0; indexColumn < layerDisplay[indexRow].length; indexColumn++) {
					layerDisplay[indexRow][indexColumn].draw(helperGraphics2D);
				}
			}
			
			//draw agents
			if(showAgents) {
				for(SwarmAgent agent : agents) {
					agent.draw(helperGraphics2D);
				}
			}
		}
	}

	public void scheduleRepaint() {
		if(!gui.getTimerActive()) {
			repaint();
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
						strategy.updatePolarityCell(this, row, col);
					}
				}
			}

			agent.step(sizeCell);
			
			if (wraparound) {
				//since there's no walls, this lets the agents "wrap" to the other side of the screen. this is awesome.		
				agent.setX((((agent.getCenterX() % SCALE_BOARD) + SCALE_BOARD) % SCALE_BOARD) - (agent.getWidth() / 2));
				agent.setY((((agent.getCenterY() % SCALE_BOARD) + SCALE_BOARD) % SCALE_BOARD) - (agent.getHeight() / 2));

				//this is not perfect: what we actually want this to do is draw both, so long as it's sticking a bit
				//off of the screen. that makes the above operations much uglier. :(
			}
			else {
				//since there's walls, this checks whether the agent has crossed any of the four bounds of the board:
				//left, then top, then right, then bottom, and whether the agent's velocity has it headed further off
				//the board. If it does, then it has the agent "bounce" off the board's wall. This is kind of a hack--
				//the desirable behavior, actually, is actually just always bouncing it, but that will require refactoring
				//this, and the agent's own step method, at a later date.
				if (agent.getX() < agent.getWidth() && agent.getVelocity().getX() < 0) {
					agent.xBounce();
				}
				if (agent.getY() < agent.getHeight() && agent.getVelocity().getY() < 0) {
					agent.yBounce();
				}
				if (agent.getMaxX() > breadth*sizeCell+agent.getWidth() && agent.getVelocity().getX() > 0) {
					agent.xBounce();
				}
				if (agent.getMaxY() > breadth*sizeCell+agent.getHeight() && agent.getVelocity().getY() > 0) {
					agent.yBounce();
				}
			}
			
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
	 * @param cells
	 * @param rowNum
	 * @param colNum
	 * @return an array of all of the neighbors of the cell whose row and column number has been provided.
	 */
	public static CellDisplay[] getNeighbors(CellDisplay[][] cells, int rowNum, int colNum) {
		//each cell only has 8 neighbors! for now at least.... :(
		CellDisplay[] neighbors = new CellDisplay[8];
		int rowMax = cells.length-1;
		int colMax = cells[rowMax-1].length-1;
		
		//top left
		if (rowNum == 0 && colNum == 0) {
			neighbors[3] = cells[rowNum][colNum+1];
			neighbors[4] = cells[rowNum+1][colNum+1];
			neighbors[5] = cells[rowNum+1][colNum];
		}

		//bottom left
		if (rowNum == rowMax && colNum == 0) {
			neighbors[1] = cells[rowNum-1][colNum];
			neighbors[2] = cells[rowNum-1][colNum+1];
			neighbors[3] = cells[rowNum][colNum+1];
		}

		//top right
		if (rowNum == 0 && colNum == cells[0].length-1) {
			neighbors[5] = cells[rowNum+1][colNum];
			neighbors[6] = cells[rowNum+1][colNum-1];
			neighbors[7] = cells[rowNum][colNum-1];
		}

		//bottom right
		if (rowNum == rowMax && colNum == colMax) {
			neighbors[0] = cells[rowNum-1][colNum-1];
			neighbors[1] = cells[rowNum-1][colNum];
			neighbors[7] = cells[rowNum][colNum-1];
		}

		//top
		if (rowNum == 0 && colNum > 0 && colNum < colMax) {
			neighbors[3] = cells[rowNum][colNum+1];
			neighbors[4] = cells[rowNum+1][colNum+1];
			neighbors[5] = cells[rowNum+1][colNum];
			neighbors[6] = cells[rowNum+1][colNum-1];
			neighbors[7] = cells[rowNum][colNum-1];
		}

		//bottom
		if (rowNum == rowMax && colNum > 0 && colNum < colMax) {
			neighbors[0] = cells[rowNum-1][colNum-1];
			neighbors[1] = cells[rowNum-1][colNum];
			neighbors[2] = cells[rowNum-1][colNum+1];
			neighbors[3] = cells[rowNum][colNum+1];
			neighbors[7] = cells[rowNum][colNum-1];
		}

		//left
		if (rowNum > 0 && rowNum < rowMax && colNum == 0) {
			neighbors[1] = cells[rowNum-1][colNum];
			neighbors[2] = cells[rowNum-1][colNum+1];
			neighbors[3] = cells[rowNum][colNum+1];
			neighbors[4] = cells[rowNum+1][colNum+1];
			neighbors[5] = cells[rowNum+1][colNum];
		}

		//right
		if (rowNum > 0 && rowNum < rowMax && colNum == colMax) {
			neighbors[0] = cells[rowNum-1][colNum-1];
			neighbors[1] = cells[rowNum-1][colNum];
			neighbors[5] = cells[rowNum+1][colNum];
			neighbors[6] = cells[rowNum+1][colNum-1];
			neighbors[7] = cells[rowNum][colNum-1];
		}

		//middle cells obviously get everything
		if (rowNum > 0 && rowNum < rowMax && colNum > 0 && colNum < colMax) {
			neighbors[0] = cells[rowNum-1][colNum-1];
			neighbors[1] = cells[rowNum-1][colNum];
			neighbors[2] = cells[rowNum-1][colNum+1];
			neighbors[3] = cells[rowNum][colNum+1];
			neighbors[4] = cells[rowNum+1][colNum+1];
			neighbors[5] = cells[rowNum+1][colNum];
			neighbors[6] = cells[rowNum+1][colNum-1];
			neighbors[7] = cells[rowNum][colNum-1];
		}

		return neighbors;
	}


	/**
	 * This method returns the Cell occupied by a given agent in a given layer. Static because it's really just
	 * converting between x and y coordinates and 2D array position. Really, <em>that</em> should be the static method
	 * and may be coming up next.
	 * @param agent
	 * @param layer
	 * @return the cell occupied by a given agent in a given layer
	 */
	public CellDisplay getOccupiedCell(SwarmAgent agent, CellDisplay[][] layer) {
		return layer[(int)(agent.getCenterX()/layer[0][0].width)][(int)(agent.getCenterY()/layer[0][0].height)];
	}

	@Override
	//if you click in a spot on the board, the agents will be attracted to that spot with decreasing effect
	//the further away they are
	public void mouseDragged(MouseEvent event) {
		/* Offset the cursor's location to account for the Board having been resized */
		int offsetX = (int)(event.getX() * SCALE_BOARD / getWidth()) - event.getX();
		int offsetY = (int)(event.getY() * SCALE_BOARD / getHeight()) - event.getY();
		event.translatePoint(offsetX, offsetY);
		
		double multiplier = sizeCell;
		if(!modeAttractor) {
			multiplier *= -1;
		}
		
		double distance, distanceX, distanceY;
		double multiplierScale;
		for (SwarmAgent agent : agents) {
			distance = Math.hypot(distanceX = (event.getX() - agent.getCenterX()), distanceY = (event.getY() - agent.getCenterY()));

			if (distance < rangeAttractor) {
				//then the agent is in the specified range
				if (Math.random() < attractorStrength) {
					multiplierScale = multiplier / distance;
					agent.setVelocity(multiplierScale * distanceX, multiplierScale * distanceY);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {

	}
	
	private void updatePolarityCells() {
		for (int indexRow = 0; indexRow < layerPolarity.length; indexRow++) {
			for (int indexColumn = 0; indexColumn < layerPolarity[indexRow].length; indexColumn++) {
				strategy.updatePolarityCell(this, indexRow, indexColumn);
			}
		}
		
		gui.updatePolarityPercentageLabels();
		
		updateCorrectnessCells();
	}
	
	public void updateCorrectnessCells() {
		for (int indexRow = 0; indexRow < layerCorrectness.length; indexRow++) {
			for (int indexColumn = 0; indexColumn < layerCorrectness[indexRow].length; indexColumn++) {
				strategy.updateCorrectnessCell(this, indexRow, indexColumn);
			}
		}
		
		repaint();
	}
	
	public void toggleWraparound() {
		wraparound = !wraparound;
	}

	//MODIFICATION #2: This method selects random cells to flip
	public void flipCells(int percentFlipCells) {
		double percent = percentFlipCells/100.0;
		int numCells = (int) ((breadth*breadth) * percent);
		boolean[] flipCell = new boolean[(breadth*breadth)];
		int count = 0;
		Random rand = new Random();
		while(count < numCells) {
			int num = rand.nextInt(breadth*breadth);
			if(!flipCell[num]) {
				flipCell[num] = true;
				count++;
			}			
		}
		count = 0;
		for (int row = 0; row < layerBase.length; row++) {
			for (int col = 0; col < layerBase[row].length; col++) {
				if(flipCell[count]) {
					layerBase[row][col].shiftState();
					strategy.updatePolarityCell(this, row, col);
					System.out.println("FLIP: " + count);
				}
				count++;
			}
		}
		
		repaint();
		gui.updateColorFrequencyLabels();
	}

	//MODIFICATION #3
	public int getTotalCellCount() {
		return breadth * breadth;
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