package agents;
/*		Author: Nick Corrado and Tim Dobeck
 * 		Description: This is the constructor class for the Agents to be created and drawn. Velocity determined the direction each agent will move and
 * 		Color is for determining if the color of the agent will be green or invisible. The rest of the agents are being made because it is extending the
 * 		Rectangle2D superclass.
 * 		Parameters: SwarmAgent is made up of its x coordinate, y coordinate, fixed size of agent, Point2D is the direction the agent will move, and color of agent
 * 					Draw is made up of Graphics g which is the type for drawing things in a JPanel
 * 					SetColor is made up of the specific color of the agent, this is called in the Board class
 * 					Both setVelocity's determine a new x and y coordinate for the Agents to have after they step
 * 					SetX and SetY determines where each agent will be placed originally
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import gui.Board;
import gui.GUI;
import strategies.Strategy;

/**
 * @author Nick
 * An agent is a freely moving and freely acting member of the swarm. An agent's goal is to
 * bring layer 1 of the simulation to a desired state as defined by the user. Agents move
 * around the board interacting with the board's cells. Agents can ask a given cell what its
 * state is, change that state if it wishes. The SwarmAgent class extends Ellipse2D.Double because
 * agents are, graphically, small circles with a position, width, height, etc.
 */
@SuppressWarnings("serial")
public class SwarmAgent extends Ellipse2D.Double {
	private static final int MEMORY = 20;
	public static final int THRESHOLD_POLARITY_DOMINANT_MEMORY = 18;
	public static final int THRESHOLD_POLARITY_DOMINANT_NEIGHBORING = 2;
	
	private boolean agentSpecial; //MODIFICATION: one agent will be a different color (when true)
	private boolean memoryFilled;
	private Point2D velocity; //adds direction to our agents
	private Color colorFill; //only adding a color here so we can make it green or invisible in the board class
	private Board board;
	private Strategy strategy;
	private int[] countsPolaritiesRecent;
	private ArrayList<Integer> polaritiesRecent;
	
	/**
	 * @author Nick
	 * Constructor that makes an agent using given coordinates, Color, and velocity vector.
	 * The super class is Ellipse2D.Double; the constructor is passing it an x and y and
	 * the ellipse's dimensions.
	 * @param x
	 * @param y
	 * @param size
	 * @param colorFill
	 */
	public SwarmAgent(double x, double y, double size, Color colorFill, boolean agentSpecial, Board board, Strategy strategy) {
		super(x, y, size, size);
		
		this.colorFill = colorFill;
		this.agentSpecial = agentSpecial;
		this.board = board;
		this.strategy = strategy;
		
		double sizeCell = board.getCellSize();
		velocity = new Point2D.Double(sizeCell * (Math.random() - 0.5), sizeCell * (Math.random() - 0.5));
		
		polaritiesRecent = new ArrayList<Integer>(MEMORY);
		countsPolaritiesRecent = new int[GUI.COUNT_POLARITIES_MAXIMUM];
		memoryFilled = false;
	}

	/**
	 * @author Nick
	 * Draws the agent as an ellipse.
	 * @param helperGraphics2D
	 */
	public void draw(Graphics2D helperGraphics2D) {
		helperGraphics2D.setColor(colorFill);
		helperGraphics2D.fill(this);
		
		if(board.getWraparound()) {
			double scaleBoard = Board.SCALE_BOARD;
			
			boolean offTop = y < 0;
			boolean offBottom = getMaxY() > scaleBoard;
			
			if(x < 0) {
				helperGraphics2D.fillOval((int)(x + scaleBoard), (int)y, (int)width, (int)height);
				
				if(offTop) {
					helperGraphics2D.fillOval((int)(x + scaleBoard), (int)(y + scaleBoard), (int)width, (int)height);
				}
			}
			else if(getMaxX() > scaleBoard) {
				helperGraphics2D.fillOval((int)(x - scaleBoard), (int)y, (int)width, (int)height);
				
				if(offBottom) {
					helperGraphics2D.fillOval((int)(x - scaleBoard), (int)(y - scaleBoard), (int)width, (int)height);
				}
			}
			
			if(offTop) {
				helperGraphics2D.fillOval((int)x, (int)(y + scaleBoard), (int)width, (int)height);
			}
			else if(offBottom) {
				helperGraphics2D.fillOval((int)x, (int)(y - scaleBoard), (int)width, (int)height);
			}
		}
		
	}

	//MODIFICATION
	public boolean isSpecial() {
		return agentSpecial;
	}
		
	//MODIFICATION #3
	public boolean getMemoryFilled() {
		return memoryFilled;
	}
		
	public int getPolarityCount(int indexPolarity) {
		return countsPolaritiesRecent[indexPolarity];
	}
	
	public Point2D getVelocity() {
		return velocity;
	}
	
	public void setColor(Color color) {
		this.colorFill = color;
	}
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
		
		polaritiesRecent.clear();
		memoryFilled = false;
	}

	//determines how much an agent will move in a particular direction
	public void setVelocity(double x, double y) {
		velocity.setLocation(x, y);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @author Nick
	 * This method is called when the simulation is being "stepped" forward once. It
	 * updates the agent's position by adding its velocity, and 10% of the time also
	 * changes the agent's velocity. This is so that agents sometimes change direction,
	 * but not too often. The range of the velocity is between (-5, 5) in both the x
	 * and y directions so that values will be evenly distributed in each direction.
	 */
	public void step(double cellSize) {
		setX(x + velocity.getX());
		setY(y + velocity.getY());

		if (Math.random() < 0.1) {
			setVelocity(cellSize * (Math.random() - 0.5), cellSize * (Math.random() - 0.5));
		}
	}

	/**
	 * @author Nick
	 * This method negates the agent's x component of its vector, as if the agent
	 * bounced off the wall of the simulation.
	 */
	public void xBounce() {
		setVelocity(-1 * getVelocity().getX(), getVelocity().getY());
	}

	/**
	 * @author Nick
	 * This method negates the agent's y component of its vector, as if the agent
	 * bounced off the ceiling or floor of the simulation.
	 */
	public void yBounce() {
		setVelocity(getVelocity().getX(), -1 * getVelocity().getY());
	}

	public void seePolarity(int indexPolarity) {
		if(memoryFilled = polaritiesRecent.size() >= MEMORY) {
			countsPolaritiesRecent[polaritiesRecent.remove(0)]--;
		}
		
		polaritiesRecent.add(indexPolarity);
		countsPolaritiesRecent[indexPolarity]++;
	}
	
	public void logic() {
		strategy.logic(board, this);
	}
}