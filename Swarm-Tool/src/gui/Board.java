package gui;

/*
 *		Authors: Zakary Gray, Tim Dobeck, Nick Corrado, Gabriel Petkac
 *		Description:  This is currently the main class for all intents and purposes.  The board holds the cells of layers 1 and 2
 *               as well as the agents in the layer 3 swarm. The jframe of Board are displayed in the GUI after a new board is created
 *               in NewBoardWindow.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import cells.CellDisplayBase;
import cells.CellDisplay;
import cells.CellDisplayPersistence;
import cells.CellDisplayPolarity;
import other.SwarmAgent;
import strategies.AbstractStrategy;
import strategies.CheckerBoard;
import strategies.DiagonalLines;
/*
 * Authors: Nick, Tim, Zak, Gabriel
 * Description: This is the guts of the program. Two 2x2 Cell arrays of size[size X size] are created to be layers 1 and 2,
 * Layer 2 gives information about layer 1, for example... Layer one currently tells which polarity (black in the corners or white)
 * of a checker board the cells in layer 1 are in.  An array of Agents is also created with random movement over the layers 1 & 2
 * while randomly changing the cells underneath them. In the future, the agents will have a low level of intelligence.
 * Parameters: width of board in pixels, height of board in pixels, number of cells on a side, and number of Agents
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseInputListener {
	public static final int RATE_STEPS_MAXIMUM = 900;
	private static final Color COLOR_TRANSPARENT = new Color(0, 1, 0, 0);
	
	public int numCellsOnSide; //these are the numbers of cells in the board, NOT the graphical dimensions of the board
	private boolean wraparound; //whether the walls of the Board wrap or not; by default, they don't
	private double cellSize; //pixel dimensions of each cell
	private SwarmAgent[] agents;
	private double attractorStrength = 1;
	private double attractorMaxDistance; //distance in cells, not pixels
	private int attractOrRepel = 1; //1 if attract, -1 if repel
	private int indexDisplay;
	private int agentRate; //delay between timer firing
	private boolean timerActive;
	private Timer timer;//main timer
	private TimerTask task;
	public Color storeVisibleColor;	//MODIFICATION: used to store color so the "View Agents" button toggles properly
	public Color storeSpecialColor; //MODIFICATION: store color of the special agent
	public boolean viewAgentsToggle; //MODIFICATION: tracks if the button has just been clicked
	private CellDisplay[] neighbors = new CellDisplay[8];//the 8 cells that neighbor a given cell in layer 1 are stored here
	private AbstractStrategy strategy;//strategy that the agents and layer 2 use for their calculations given the current goal
	public int countAgents; //MODIFICATION
	String exportInfoString = "Red,Blue,Yellow\n"; 
	public int countSteps = 0; //MODIFICATION #6
	private long rateExecute;
	private GUI gui;
	private int[] frequencyColorsInitial;
	private int[] frequencyColors;
	private int[] frequencyPolarities;
	private CellDisplayBase[][] layer1;
	private CellDisplayPolarity[][] layer2;
	private CellDisplayPersistence[][] layer4;  //MODIFICATION #7: new layer of cells for persistency
	private CellDisplay[][] layerDisplay; //layer to paint
	private HashMap<Integer, CellDisplay[][]> layers;

	public Board(int width, int height, int numCellsOnSide, int countAgents, boolean wrap, GUI gui) {
		this.countAgents = countAgents;
		this.gui = gui;

		timer = new Timer();
		
		frequencyColorsInitial = new int[CellDisplayBase.getMaximumStateCount()];
		frequencyPolarities = new int[GUI.COUNT_POLARITIES_MAXIMUM];
		layers = new HashMap<Integer, CellDisplay[][]>(4);
		
		//MODIFICATION
		//Added 7/17/18
		//By Morgan Might
		//Allow the Board to start with the goal of Diagonal Lines
		if(gui.diagonalLineStart) {
			strategy = new DiagonalLines();
		}
		else {
			strategy = new CheckerBoard();
		}

		//set preferred graphical dimensions of the board
		setPreferredSize(new Dimension(width, height));
		this.numCellsOnSide = numCellsOnSide;
		this.wraparound = wrap;
		
		//set the graphical dimensions of the cells themselves. the cells are always square, but the
		//space they take up is constrained by the width and height of the board and by the number of cells.
		cellSize = (width / numCellsOnSide);
		double agentSize = (cellSize * 0.7);

		attractorMaxDistance = cellSize * 5; //the attractor affects everything in a five cell block radius

		//layer 1
		layer1 = new CellDisplayBase[numCellsOnSide][numCellsOnSide];

		if (!gui.getSplitPolarity()) {
			for (int indexRow = 0; indexRow < layer1.length; indexRow++) {
				for (int indexColumn = 0; indexColumn < layer1[indexRow].length; indexColumn++) {
					layer1[indexRow][indexColumn] = new CellDisplayBase(indexRow * cellSize, indexColumn * cellSize, cellSize, (int)(strategy.getStateCount() * Math.random()), this);
				}
			}
		}
		// MODIFICATION: This will test swarms when the polarity is split and the agents are no longer effective
		else if(!gui.getThreeColor()){
			int pos;
			int stateInitial;
			for (int indexRow = 0; indexRow < layer1.length; indexRow++) {
				for (int indexColumn = 0; indexColumn < layer1[indexRow].length; indexColumn++) {
					if(Math.random() < 0.5) {  
						pos = indexRow;
					}
					else { 
						pos = indexColumn;
					}
						
					if (pos < (3 + (int)(Math.random() * (numCellsOnSide - 5)))) {
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
					
					layer1[indexRow][indexColumn] = new CellDisplayBase(indexRow * cellSize, indexColumn * cellSize, cellSize, stateInitial, this);
				}
			}
		}
		layers.put(1, layer1);

		layer2 = new CellDisplayPolarity[numCellsOnSide][numCellsOnSide];
		for (int row = 0; row < layer2.length; row++) {
			for (int col = 0; col < layer2[row].length; col++) {
				layer2[row][col] = strategy.createPolarityCell(this, row, col);
			}
		}
		layers.put(2, layer2);

		//MODIFICATION #7
		//by Morgan Might
		//July 5, 2018
		//Layer 4 cells start as white
		//
		layer4 = new CellDisplayPersistence[numCellsOnSide][numCellsOnSide];
		for (int row = 0; row < layer4.length; row++) {
			for (int col = 0; col < layer4[row].length; col++) {
				layer4[row][col] = new CellDisplayPersistence(row * cellSize, col * cellSize, cellSize, this);
			}
		}
		layers.put(4, layer4);
		
		//********************************************************************************************************	
		//*************************MODIFICATION******************************************************************
		//********************************************************************************************************		
		//generates the swarm and adjusts their positions
		agents = new SwarmAgent[countAgents];
		boolean specialAgent = false;
		for (int indexAgent = 0; indexAgent < agents.length; indexAgent++) {
			//these agents generate in a random spot on the board, with a random starting vector.
			if(indexAgent < gui.getNumOfSpecialAgents()) {
				specialAgent = true;
			}
			agents[indexAgent] = new SwarmAgent(width, cellSize, agentSize, specialAgent);
			specialAgent = false;
		}

		frequencyColors = frequencyColorsInitial.clone();
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		if (gui.layer2Draw == 3) {
			gui.layer2Draw = 1;
		}
	}

	public int getStepCount() {
		return countSteps;
	}
	
	public AbstractStrategy getActiveStrategy() {
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
	
	public void startTimer() {
		if(!timerActive) {
			task = new TimerTask() {
				@Override
				public void run() {
					step();
					repaint();
				}
			};
			
			timer.scheduleAtFixedRate(task, rateExecute, rateExecute);
			timerActive = true;
		}
	}
	
	public void stopTimer() {
		if(timerActive) {
			task.cancel();
			timer.purge();
			timerActive = false;
		}
	}

	@Override
	protected void paintComponent(Graphics helperGraphics) {
		super.paintComponent(helperGraphics);

		Graphics2D helperGraphics2D = (Graphics2D)helperGraphics;
		
		//draw boards
		if(gui.layer2Draw == 3) {
			gui.layer2Draw = indexDisplay;
			repaint();
		}
		else {
			indexDisplay = gui.layer2Draw;
			layerDisplay = layers.get(indexDisplay);
			
			for(int indexRow = 0; indexRow < layerDisplay.length; indexRow++) {
				for(int indexColumn = 0; indexColumn < layerDisplay[indexRow].length; indexColumn++) {
					layerDisplay[indexRow][indexColumn].draw(helperGraphics2D);
				}
			}
		}
		
		//*************************************************************************************************
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MODIFICATION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//*************************************************************************************************
		//draw agents
		for (SwarmAgent agent : agents) {
			// if the agents have been set to non-visible, sets them to a transparent color
			if (gui.whetherAgentsVisible) {
				agent.setColor(agent.getColor());
			} 
			else {
				if(agent.getAgentStatus() && !agent.getColor().equals(COLOR_TRANSPARENT)) {
					storeSpecialColor = agent.getColor();
				}
				else if(!agent.getColor().equals(COLOR_TRANSPARENT)){
					storeVisibleColor = agent.getColor();
				}
				agent.setColor(COLOR_TRANSPARENT);
			}
			agent.draw(helperGraphics2D);
			//if you're sticking off the right or bottom of map, draw another ellipse there too
			//this is a hack; really, i think this should be a job for the agent's draw method.
			if (wraparound && agent.getMaxX() > getWidth()) {
				helperGraphics2D.fill(new Ellipse2D.Double(agent.getX() - this.getWidth(), agent.getY(), agent.getWidth(), agent.getHeight()));
			}
			if (wraparound && agent.getMaxY() > getHeight()) {
				helperGraphics2D.fill(new Ellipse2D.Double(agent.getX(), agent.getY() - this.getHeight(), agent.getWidth(), agent.getHeight()));
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
		for (SwarmAgent agent : agents) {
			//10% of the time, the agent will determine algebraically which cell it's in, then flip the color of that cell.
			//a better approach than this would be to have the agent store which cell it's currently in, then just flip that
			//color 10% of the time. this would also make it easy to keep the agent from flipping the same cell many times
			//before leaving it--something we haven't gotten to yet.

			//TESTING NEIGHBORS
			neighbors = getNeighbors(layer1, (int)(agent.getCenterX() / cellSize), (int)(agent.getCenterY() / cellSize));
			int rowMax = layer1.length-1;
			int colMax = layer1[rowMax-1].length-1;
			if((int)(agent.getCenterX() / cellSize) <= rowMax && (int)(agent.getCenterY() / cellSize) <= colMax) {
				strategy.logic(agent, layer1, layer2, neighbors, cellSize);
				for (int row = 0; row < layer2.length; row++) {
					for (int col = 0; col < layer2[row].length; col++) {
						strategy.updatePolarityCell(this, row, col);
					}
				}
			}

			agent.step(cellSize);
			if (wraparound) {
				//since there's no walls, this lets the agents "wrap" to the other side of the screen. this is awesome.
				agent.setX((agent.getX()+getWidth())%getWidth());
				agent.setY((agent.getY()+getHeight())%getHeight());

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
				if (agent.getMaxX() > numCellsOnSide*cellSize+agent.getWidth() && agent.getVelocity().getX() > 0) {
					agent.xBounce();
				}
				if (agent.getMaxY() > numCellsOnSide*cellSize+agent.getHeight() && agent.getVelocity().getY() > 0) {
					agent.yBounce();
				}
			}
		}

		countSteps++; //keep track of how many steps
		
		gui.updateLabels(countSteps);
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
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		double magnitude;
		Point2D.Double towardMouse;

		//if you click in a spot on the board, the agents will be attracted to that spot with decreasing effect
		//the further away they are
		if (gui.attractOrRepel) {
			attractOrRepel=1;
		}
		else {
			attractOrRepel=-1;
		}
		for (SwarmAgent agent : agents) {
			magnitude = Math.sqrt((agent.getCenterX()-arg0.getX())*(agent.getCenterX()-arg0.getX()) + (agent.getCenterY()-arg0.getY())*(agent.getCenterY()-arg0.getY()));
			towardMouse = new Point2D.Double(attractOrRepel*cellSize*(arg0.getX()-agent.getCenterX())/magnitude, attractOrRepel*cellSize*(arg0.getY()-agent.getCenterY())/magnitude);

			if (magnitude < attractorMaxDistance) {
				//then the agent is in the specified range
				//double angle = Math.asin(distance/(agent.getCenterY()-arg0.getY()));
				if (Math.random() < attractorStrength) {
					agent.setVelocity(towardMouse);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	/**
	 * @author zgray17
	 * This method updates the rate of the agent clock. Blah blah blah.
	 * @param rate
	 */
	public void setAgentRate(int rate) {
		agentRate = rate;
		rateExecute = Math.max(1000 / agentRate, 1000 / RATE_STEPS_MAXIMUM);
		
		if(timerActive) {
			stopTimer();
			startTimer();
		}
	}

	/**
	 * @author zgray17
	 * This method updates the color of the agents. Blah blah blah.
	 * @param newColor
	 */
	public void updateAgentColor(Color colorAgent) {
		for (int i = 0; i < agents.length; i++) {
			if(!agents[i].getAgentStatus()) {
				agents[i].setColor(colorAgent);
			}
		}
	}

	public void updateSpecialAgentColor(Color colorAgentSpecial) {
		for (int i = 0; i < agents.length; i++) {
			if(agents[i].getAgentStatus()) {
				agents[i].setColor(colorAgentSpecial);
			}
		}
	}

	public void updateGoalStrategy(AbstractStrategy strategy) {
		if(!strategy.equals(this.strategy)) {
			this.strategy = strategy;
			
			updatePolarityCells();
		}
	}
	
	private void updatePolarityCells() {
		for (int indexRow = 0; indexRow < layer2.length; indexRow++) {
			for (int indexColumn = 0; indexColumn < layer2[indexRow].length; indexColumn++) {
				strategy.updatePolarityCell(this, indexRow, indexColumn);
			}
		}
	}

	public void setWraparound(boolean wraparound) {
		this.wraparound = wraparound;
	}

	//MODIFICATION: set the color for each agent using the stored color (this will be the color it was before it went invisible)
	public void updateVisibleAgentColors() {
		for (SwarmAgent agent : agents) {
			// if the agents have been set to non-visible, sets them to a transparent color
			if (agent.getAgentStatus()) {
				agent.setColor(storeSpecialColor);
			}
			else {
				agent.setColor(storeVisibleColor);
			}		
		}				
	}

	//MODIFICATION #2: This method selects random cells to flip
	public void flipCells(int percentFlipCells) {
		double percent = percentFlipCells/100.0;
		int numCells = (int) ((numCellsOnSide*numCellsOnSide) * percent);
		boolean[] flipCell = new boolean[(numCellsOnSide*numCellsOnSide)];
		int count = 0;
		Random rand = new Random();
		while(count < numCells) {
			int num = rand.nextInt(numCellsOnSide*numCellsOnSide);
			if(!flipCell[num]) {
				flipCell[num] = true;
				count++;
			}			
		}
		count = 0;
		for (int row = 0; row < layer1.length; row++) {
			for (int col = 0; col < layer1[row].length; col++) {
				if(flipCell[count]) {
					layer1[row][col].shiftState();
					strategy.updatePolarityCell(this, row, col);
					System.out.println("FLIP: " + count);
				}
				count++;
			}
		}
	}

	//MODIFICATION #3
	public int getTotalNumCells() {
		return numCellsOnSide * numCellsOnSide;
	}

	//MODIFICATION #3
	public void updateAgents(int numAgents) {
		Color[] nullArray = new Color[10];
		SwarmAgent[] newAgents = new SwarmAgent[agents.length];
		for(int i= 0; i < numAgents; i++) {
			newAgents[i] = new SwarmAgent(agents[i].getX(), agents[i].getY(), agents[i].getWidth(), agents[i].getVelocity(), agents[i].getColor(), nullArray);
		}
	}

	//MODIFICATION #7:
	//Morgan Might on July 5, 2018
	//This method will reset the layer 4 cell to white
	public void resetToWhite(SwarmAgent agent, double cellSize){
		layer4[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].setState(0);
	}

	public void addPurple(SwarmAgent agent, double cellSize) {
		layer4[(int)(agent.getCenterX() / cellSize)][(int)(agent.getCenterY() / cellSize)].shiftState();
	}

	public GUI getGui() {
		return gui;
	}
	
//	public int getColorFrequencyInitial(Color color) {
//		if(frequencyColorsInitial.containsKey(color)) {
//			return frequencyColorsInitial.get(color);
//		}
//		
//		return 0;
//	}
//	
//	public int getColorFrequency(Color color) {
//		if(frequencyColors.containsKey(color)) {
//			return frequencyColors.get(color);
//		}
//		
//		return 0;
//	}
	
	public void adjustInitialColorFrequency(int stateAdjust, int adjustment) {
		frequencyColorsInitial[stateAdjust] += adjustment;
	}
	
	public void adjustColorFrequency(int colorAdjust, int adjustment) {
		frequencyColors[colorAdjust] += adjustment;
	}
	
	public void adjustPolarityFrequency(int colorAdjust, int adjustment) {
		frequencyPolarities[colorAdjust] += adjustment;
	}
	
	public CellDisplay[][] getLayer(int indexLayer) {
		return layers.get(indexLayer);
	}
	
	public double getCellSize() {
		return cellSize;
	}
	
	public BufferedImage capture() {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		paint(image.createGraphics());
		return image;
	}
}


