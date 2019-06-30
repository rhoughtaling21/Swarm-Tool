package cells;
/*		CURRENTLY NOT USED
 * 		Author: Zak Gray and Nick Corrado
 * 		Description: This class is intended to use the Singleton Design Pattern to create a border around the cells. This border will be invisible. 
 * 					 This border will be populated with Null cells. If the cells go beyond the normal board, then they will disappear and be have null coordinates.
 */

public class CellNull implements Cell {
	private static CellNull nullCell;
	
	private CellNull() {
		//does nothing
	}
	
	public static CellNull getNullCell() {
		if (nullCell == null) {
			nullCell = new CellNull();
		}
		
		return nullCell;
	}
	
	//everything that a Cell would do, does nothing following this
	
	@Override
	public int getState() {
		return -1;
	}

	@Override
	public void setState(int indexState) {
		
	}
}