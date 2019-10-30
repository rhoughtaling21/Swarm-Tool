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

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class RectangleCellDisplay extends Rectangle2D.Double {
	private Cell cellActive;
	private Cell[] cells;
	
	public RectangleCellDisplay(double x, double y, double size, Cell[] cells, int indexLayer) {
		super(x, y, size, size);
		
		this.cells = cells;
		
		setLayer(indexLayer);
	}

	public void draw(Graphics2D helperGraphics2D) {
		helperGraphics2D.setColor(cellActive.getColor());
		helperGraphics2D.fill(this);
	}
	
	public void setLayer(int indexLayer) {
		cellActive = cells[indexLayer];
	}
}