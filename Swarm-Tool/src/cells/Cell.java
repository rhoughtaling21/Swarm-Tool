package cells;

import java.awt.Color;
import java.awt.Graphics2D;

public interface Cell {
	public Color getColor();
	public Color getPolarityColor();
	public void setColor(Color colorFill);
	public void setPolarityColor(Color colorPolarity);
	public void draw(Graphics2D helperGraphics2D);
}
