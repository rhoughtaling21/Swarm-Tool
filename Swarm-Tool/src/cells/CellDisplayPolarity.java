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
import gui.GUI;

/**
 * @authors Zak, Nick, Gabriel, Tim
 * description: Cell is a rectangle of one color that is generated in two 2X2 Arrays (cells, cells2) in board
 * parameters: Constructor takes in an X and Y position, a width and height(size X size), and a Color to fill
 */
@SuppressWarnings("serial")
public class CellDisplayPolarity extends CellDisplay {
	public CellDisplayPolarity(double x, double y, double size, Color colorFill, Board board) {
		//a lot of these parameters actually belong to Rectangle2D.Double, so we call in the super class CellDisplay
		super(x, y, size, colorFill, board);
		board.adjustPolarityFrequency(colorFill, 1);
	}

	//flip color of Cell
	public void flipColor()	{
		//as cells2 can be different colors chosen by the user, the color is set in the GUI
		GUI gui = board.getGui();
		
		if (colorFill.equals(gui.getPolarityColor(1))) {
			setColor(gui.getPolarityColor(2));
		}
		else {
			setColor(gui.getPolarityColor(1));
		}
	}
	
	//MODIFICATION #3
	//change color of Cell
	@Override
	public void setColor(Color colorFill) {
		//Subtract counter for the old color
		board.adjustPolarityFrequency(this.colorFill, -1);
		//Set new Color
		this.colorFill = colorFill;
		//Set Polarity and add counter for new color
		board.adjustPolarityFrequency(colorFill, 1);
	}
}


