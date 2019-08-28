package swarm;
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
	private static final double SCALE_BOARD = Board.SCALE_BOARD;
	private static final double DIVISOR_MAGNITUDE_COMPONENT_VECTOR_VELOCITY_SLOW = 5;
	
	private boolean modeMemory;
	private int capacityMemory;
	private double multiplierVelocity;
	private double rangeSignal;
	private Point2D velocity; //adds direction to our agents
	private Color colorFill;
	private Board board;
	private Motion motion;
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
	public SwarmAgent(double x, double y, double size, Color colorFill, Board board, Motion motion, boolean slow, Strategy strategy) {
		this(x, y, size, colorFill, board, motion, slow, strategy, 0);
	}
	
	public SwarmAgent(double x, double y, double size, Color colorFill, Board board, Motion motion, boolean slow, Strategy strategy, double rangeSignal) {
		super(x, y, size, size);
		
		this.board = board;
		this.motion = motion;
		
		setColor(colorFill);
		setStrategy(strategy);
		setSignalRange(rangeSignal);
		
		multiplierVelocity = board.getCellSize();
		
		if(slow) {
			multiplierVelocity /= DIVISOR_MAGNITUDE_COMPONENT_VECTOR_VELOCITY_SLOW;
		}
		
		velocity = new Point2D.Double();
		motion.initializeVelocityVector(this);
	}
	
	public int getPolarityCount(int indexPolarity) {
		if(modeMemory) {
			return countsPolaritiesRecent[indexPolarity];
		}
		
		return 0;
	}
	
	public double getSignalRange() {
		return rangeSignal;
	}
		
	public Point2D getVelocity() {
		return velocity;
	}
	
	Board getBoard() {
		return board;
	}
	
	public void setSignalRange(double rangeSignal) {
		this.rangeSignal = rangeSignal;
	}

	//determines how much an agent will move in a particular direction
	public void setVelocityCells(double velocityX, double velocityY) {
		setVelocity(velocityX * multiplierVelocity, velocityY * multiplierVelocity);
	}
	
	private void setVelocity(double velocityX, double velocityY) {
		velocity.setLocation(velocityX, velocityY);
	}
	
	public void setColor(Color color) {
		this.colorFill = color;
	}
		
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
		
		ArrayList<Integer> polaritiesRecent = null;
		int[] countsPolaritiesRecent = null;
		
		if(modeMemory = ((capacityMemory = strategy.getMemoryCapacity()) > 0)) {
			polaritiesRecent = new ArrayList<Integer>(capacityMemory);
			countsPolaritiesRecent = new int[board.getPattern().getPolarityCount()];
		}
		
		this.polaritiesRecent = polaritiesRecent;
		this.countsPolaritiesRecent = countsPolaritiesRecent;
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
	
	/**
	 * @author Nick
	 * This method is called when the simulation is being "stepped" forward once. It
	 * updates the agent's position by adding its velocity, and 10% of the time also
	 * changes the agent's velocity. This is so that agents sometimes change direction,
	 * but not too often. The range of the velocity is between (-5, 5) in both the x
	 * and y directions so that values will be evenly distributed in each direction.
	 */
	public void step() {
		double coordinateX = x + velocity.getX();
		double coordinateY = y + velocity.getY();

		motion.questionVelocity(this);
		
		double offsetCenter = width / 2;
		double coordinateXCenter = coordinateX + offsetCenter;
		double coordinateYCenter = coordinateY + offsetCenter;
		
		if (board.getWraparound()) {
			//since there's no walls, this lets the agents "wrap" to the other side of the screen. this is awesome.
			if(coordinateXCenter < 0 || coordinateXCenter > SCALE_BOARD) {
				coordinateX = (((coordinateXCenter % SCALE_BOARD) + SCALE_BOARD) % SCALE_BOARD) - offsetCenter;
			}
			
			if(coordinateYCenter < 0 || coordinateYCenter > SCALE_BOARD) {
				coordinateY = (((coordinateYCenter % SCALE_BOARD) + SCALE_BOARD) % SCALE_BOARD) - offsetCenter;
			}
		}
		else {
			double velocityX = velocity.getX();
			double velocityY = velocity.getY();
			//since there's walls, this checks whether the agent has crossed any of the four bounds of the board:
			//left, then top, then right, then bottom, and whether the agent's velocity has it headed further off
			//the board. If it does, then it has the agent "bounce" off the board's wall.
			boolean bounceX = (velocityX < 0 && coordinateXCenter < 0) || (velocityX > 0 && coordinateXCenter > SCALE_BOARD);
			if(bounceX) {
				velocityX *= -1;
			}
			
			boolean bounceY = (velocityY < 0 && coordinateYCenter < 0) || (velocityY > 0 && coordinateYCenter > SCALE_BOARD);
			if(bounceY) {
				velocityY *= -1;
			}
			
			if(bounceX || bounceY) {
				setVelocity(velocityX, velocityY);
			}
		}
		
		x = coordinateX;
		y = coordinateY;
	}

	public void seePolarity(int indexPolarity) {
		if(polaritiesRecent.size() >= capacityMemory) {
			countsPolaritiesRecent[polaritiesRecent.remove(0)]--;
		}
		
		polaritiesRecent.add(indexPolarity);
		countsPolaritiesRecent[indexPolarity]++;
	}
	
	public void logic() {
		strategy.logic(board, this);
	}
}