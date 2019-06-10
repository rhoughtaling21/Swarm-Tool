package cells;
/*		CURRENTLY NOT USED
 * 		Author: Zak Gray and Nick Corrado
 * 		Description: This class is intended to use the Singleton Design Pattern to create a border around the cells. This border will be invisible. 
 * 					 This border will be populated with Null cells. If the cells go beyond the normal board, then they will disappear and be have null coordinates.
 */

@SuppressWarnings("serial")
public class NullCell extends GenericCell {
	private static NullCell nullCell;
	
	private NullCell() {
		//does nothing
	}
	
	public static NullCell getNullCell() {
		if (nullCell == null) {
			nullCell = new NullCell();
		}
		return nullCell;
	}
	
	//everything that a cell extending gencell would do, does nothing following this
}

