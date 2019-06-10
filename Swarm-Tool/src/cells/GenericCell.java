package cells;
/*		Author: Zak Gray and Tim Dobeck
 * 		Description: GenericCell allows for the objects of Cell and Cell_2 to be created and the rectangles (cells) to be drawn. This extends Rectangle2D and uses the
 * 					 Rectangle2D superclass for creating the cells.
 * 		Parameters: GenericCell is the constructor. Each cell has an X coordinate, Y coordinate, fixed size, and Cell is either black or white while Cell_2 is either red or blue
 * 					Draw is allowing the graphics to appear on the JPanel
 * 
 * 		Modified by Morgan Might
 * 		Date: June 1, 2018
 * 
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class GenericCell extends Rectangle2D.Double {
	public double x;
	public double y;
	protected double size;
	protected Color cellColor;
	protected Color polarityColor;

	//constructor
	public GenericCell(double x, double y, double size, Color color)
	{
		super(x,y,size,size);
		this.x = x;
		this.y = y;
		this.size = size;
		this.cellColor = color;
	}

	public GenericCell() {
		//does nothing; just exists for NullCell to work
	}

	public Color getColor(){
		return cellColor;
	}

	public void draw(Graphics2D g) {
		g.setColor(cellColor);
		g.fill(this);
		g.fillRect((int)x, (int)y, (int)size, (int)size);
	}
	
	//Getter and setter for polarity color variable
	//Added 6/1 by Morgan Might
	public Color getPolarityColor() {
		return polarityColor;
	}
	
	//Added 6/1 by Morgan Might
	public void setPolarityColor(Color color) {
		polarityColor = color;
	}
}


