package cells;
/*		Author: Zak Gray and Tim Dobeck
 * 		Description: This is the constuctor for determining the first board's cells. The cells in layer 1 can be only black or white. This class mainly just
 * 					creates the instance of the cells in layer 1. FlipColor allows the color to be flipped if any cell is clicked. This class extends GenericCell 
 * 					which is an abstract class that creates the cells as rectangles
 * 		Parameters: Cell is made up of an x coordinate, a y coordinate, a fixed size, and a color and for this class (layer 1) the cells can only be either 
 * 					black or white.
 * 					setColor is made up of an object color which determines what color each cell can be.
 */

import java.awt.Color;

import gui.Board;

/**
 * @authors Zak, Nick, Gabriel, Tim
 * description: Cell is a rectangle of one color that is generated in two 2X2 Arrays (cells, cells2) in board
 * parameters: Constructor takes in an X and Y position, a width and height(size X size), and a Color to fill
 */
@SuppressWarnings("serial")
public class CellDisplayBase extends CellDisplay {
	public static final Color[] COLORS_BASE = {Color.BLACK, Color.WHITE, Color.GRAY};
	private int[] frequenciesBase;
	
	public static int getMaximumStateCount() {
		return COLORS_BASE.length;
	}
	
	public CellDisplayBase(double x, double y, double size, int indexState, Board board) {
		//a lot of these parameters actually belong to Rectangle2D.Double, so we call in the super class CellDisplay
		super(x, y, size, board);
		
		frequenciesBase = board.getColorFrequencies();
		
		board.getInitialColorFrequencies()[indexState]++;
		setStateValue(indexState);
	}

	//MODIFICATION #3
	//change color of Cell
	@Override
	public void setState(int indexState) {
		frequenciesBase[this.indexState]--;
		setStateValue(indexState);
	}
	
	//flip color of Cell
	public void shiftState() {
		setState((indexState + 1) % board.getActiveStrategy().getStateCount());
	}
	
	private void setStateValue(int indexState) {
		this.indexState = indexState;
		colorFill = COLORS_BASE[indexState];
		frequenciesBase[this.indexState]++;
	}
}


