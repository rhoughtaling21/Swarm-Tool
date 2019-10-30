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
public class CellTalliedPolarity extends CellTallied {
	private Color[] colorsPolarity;
	
	public CellTalliedPolarity(int indexState, Board board) {
		super(indexState, board, board.getPolarityFrequencies());
		
		colorsPolarity = board.getGui().getPolarityColors();
		
		updateState();
	}
	
	@Override
	protected Color determineColor() {
		return colorsPolarity[indexState];
	}
}