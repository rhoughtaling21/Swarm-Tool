package cells;
/*		Author: Zak Gray and Tim Dobeck
 * 		Description: This is the constructor for determining the first board's cells. The cells in layer 1 can be only black or white. This class mainly just
 * 					creates the instance of the cells in layer 1. FlipColor allows the color to be flipped if any cell is clicked. This class extends GenericCell 
 * 					which is an abstract class that creates the cells as rectangles
 * 		Parameters: Cell is made up of an x coordinate, a y coordinate, a fixed size, and a color and for this class (layer 1) the cells can only be either 
 * 					black or white.
 * 					setColor is made up of an object color which determines what color each cell can be.
 */

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import gui.Board;

/**
 * @authors Zak, Nick, Gabriel, Tim
 * description: Cell is a rectangle of one color that is generated in two 2X2 Arrays (cells, cells2) in board
 * parameters: Constructor takes in an X and Y position, a width and height(size X size), and a Color to fill
 */
public class CellTalliedBase extends CellTallied {
	public static final Color[] COLORS_BASE = {Color.BLACK, Color.WHITE, Color.GRAY};
	
	public static int getMaximumStateCount() {
		return COLORS_BASE.length;
	}
	
	public CellTalliedBase(int indexState, Board board) {
		super(indexState, board, board.getColorFrequencies(), board.getInitialColorFrequencies());
		
		updateState();
	}
	
	public CellTalliedBase(Board board) {
		this(ThreadLocalRandom.current().nextInt(board.getPattern().getStateCount()), board);
	}
	
	@Override
	protected Color determineColor() {
		return COLORS_BASE[indexState];
	}
	
	//flip color of Cell
	public void shiftState() {
		setState((indexState + 1) % board.getPattern().getStateCount());
	}
}