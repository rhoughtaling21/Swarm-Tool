package cells;
/*		Author: Zak Gray and Tim Dobeck
 * 		Description: GenericCell allows for the objects of Cell and Cell_2 to be created and the rectangles (cells) to be drawn. This extends Rectangle2D and uses the
 * 					 Rectangle2D superclass for creating the cells.
 * 		Parameters: GenericCell is the constructor. Each cell has an X coordinate, Y coordinate, fixed size, and Cell is either black or white while Cell_2 is either red or blue
 * 					Draw is allowing the graphics to appear on the JPanel
 * 
 * 		Modified by Morgan Might
 * 		Date: June 1, 2018
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import gui.Board;

@SuppressWarnings("serial")
public abstract class CellDisplay extends Rectangle2D.Double implements Cell {
	protected Color colorFill;
	protected Board board;
	
	public CellDisplay(double x, double y, double size, Color colorFill, Board board) {
		super(x, y, size, size);
		this.colorFill = colorFill;
		this.board = board;
	}

	public Color getColor() {
		return colorFill;
	}
	
	public Board getBoard() {
		return board;
	}

	public void draw(Graphics2D helperGraphics2D) {
		helperGraphics2D.setColor(colorFill);
		helperGraphics2D.fill(this);
	}
}


