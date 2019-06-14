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
 * @authors zak, Nick, Gabriel, Tim
 * description: Cell is a rectangle of one color that is generated in two 2X2 Arrays (cells, cells2) in board
 * parameters: Constructor takes in an X and Y position, a width and height(size X size), and a Color to fill
 */
@SuppressWarnings("serial")
public class CellDisplayBase extends CellDisplay {
	private Color colorPolarity;
	
	public CellDisplayBase(double x, double y, double size, Color colorFill) {
		//a lot of these parameters actually belong to Rectangle2D.Double, so we call in the super class GenericCell
		super(x, y, size, colorFill);
	}

	//flip color of Cell
	public void flipColor()
	{
		//these first two cases apply to cells as they only are ever black or white
		if (colorFill == Color.BLACK) 
		{
			colorFill = Color.WHITE;
			Board.currBlackCellCounter--;
			Board.currWhiteCellCounter++;
		}
		else
		{
			colorFill = Color.BLACK;
			Board.currWhiteCellCounter--;
			Board.currBlackCellCounter++;
		}
	}

	@Override
	public Color getPolarityColor() {
		return colorPolarity;
	}
	
	//MODIFICATION #3
	//change color of Cell
	@Override
	public void setColor(Color newColor)
	{
		//Subtract counter for the old color
		if (colorFill == Color.BLACK) 
		{
			Board.currBlackCellCounter--;
		}
		else if (colorFill == Color.WHITE)
		{
			Board.currWhiteCellCounter--;
		}
		else if(colorFill == Color.GRAY) {
			Board.currGrayCellCounter--;
		}
		//Set new Color
		colorFill = newColor;
		//Set Polarity and add counter for new color
		//IF center is black; polarity is 2 (blue)
		if (newColor == Color.BLACK) 
		{
			Board.currBlackCellCounter++;
			//cellColor = GUI.getPolarity2();
		}
		//IF center is white; polarity is 1 (red)
		else if (newColor == Color.WHITE)
		{
			Board.currWhiteCellCounter++;
			//cellColor = GUI.getPolarity1();
		}
		//IF center is gray; polarity is 3 (yellow)
		else if(newColor == Color.GRAY) {
			Board.currGrayCellCounter++;
			//cellColor = GUI.getPolarity3();
		}	
	}
	
	@Override
	public void setPolarityColor(Color colorPolarity) {
		this.colorPolarity = colorPolarity;
	}
}


