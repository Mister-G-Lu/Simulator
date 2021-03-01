import java.awt.Color;

/**
 *  Representation of water.
 */
public class Water extends Element {
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return Color.BLUE;
	}
	
	
	/**
	* {@inheritDoc}
	* This element is heavier than Empty Void but not as light as Crystalline Grainy Stuff. 
	* This means it will fall down when placed in the void and push the void out of the way, 
	* but Crystalline Grainy Stuff should "sink" when dropped into it. 
	 */
	@Override
	public int getWeight() {
		return 1;
	}
	
	/**
	* {@inheritDoc}
	* When this element "falls" it does the following: 
	*It chooses a random direction (left, right, or down) 
	*and falls that way if it is heavier than that element. 
	*If the chosen direction isn’t possible, it gives up. 
	*/

	@Override
	public void fall(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		// only fall if you can fall.
		int randomInt = (int)(Math.random()*3);
		int belowRow = (row<grid.size()-1) ? row+1: row;
		Element curr = grid.get(row).get(col); //replace it with itself if can't access left/right
		Element below = grid.get(belowRow).get(col);
		Element right = col<grid.get(0).size()-1 ? grid.get(row).get(col+1): curr;
		Element left = col>0 ? grid.get(row).get(col-1) : curr;

		if(randomInt == 0 && this.compareTo(below)>0 && below.push(grid,belowRow, col)){
			// System.out.println("Letting R:" + row + " C: " + col+ " fall");
			swap(grid, row, col, row+1, col);
		}
		else if(randomInt == 1 && this.compareTo(right)>0 && right.push(grid,row, col+1)){
			swap(grid,row,col,row,col+1);
		}
		else if(randomInt == 2 && this.compareTo(left)>0 && left.push(grid,row, col-1)){
			swap(grid,row,col,row,col-1);
		}
	}
	
	/**
	* Swaps row1 col1 with row2 col2.
	* @param grid the grid
	* @param row1 currrow
	* @param col1 currcol
	* @param row2 otherRow
	* @param col2 othercol
	*/
	private void swap(DynamicArray<DynamicArray<Element>> grid, int row1, int col1, int row2, int col2) {
		grid.get(row1).set(col1, grid.get(row2).get(col2));
		grid.get(row2).set(col2, this);
	}

	/**
	*  {@inheritDoc}
	* When this element is "pushed" it randomly chooses between try pushing left or up first. 
	* If its chosen direction doesn’t work, it tries the other way.
	*/
	@Override
	public boolean push(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		int randomInt = (int)(Math.random()*2);
		if(randomInt == 0 && row>0 && grid.get(row-1)!= null && !pushUp(grid,row,col)){
			pushLeft(grid,row,col);
		}
		else if(randomInt == 1 && col>0 && grid.get(row)!=null && grid.get(row).get(col-1)!=null && !pushLeft(grid,row,col)){
			pushUp(grid,row,col);
		}
		return true;
	}
	/**
	 *  Main method that shows an example of testing an element.
	 *  
	 *  @param args not used
	 */
	public static void main(String args[]){
		//create a grid and a piece of Water
		DynamicArray<DynamicArray<Element>> grid = new DynamicArray<>();
		// initialize the grid to a 3x3 area
		for (int i=0;i<3;i++){	
			DynamicArray<Element> cols = new DynamicArray<>(3);
			for (int j=0;j<3;j++){
				cols.add(new Empty());
			}
			grid.add(cols);
		}
		Water w = new Water();
		
		//put the element in the middle
		grid.get(1).set(1, w);
		
		System.out.println("Water Grid: " + grid.toString());
		
		//(2) call some method(s) that will change (or not change) the scenario
		w.fall(grid, 1, 1);
		
		//(3) check that what should happen did happen
		
		//if it was sand, it would be at grid.get(2).get(1)
		//if it was water, it might be at grid.get(1).get(0), grid.get(1).get(2), or grid.get(2).get(1)
		
		if(grid.get(2).get(1) == w || grid.get(1).get(2) == w || grid.get(1).get(0) == w) {
			//^ careful about == here, that's checking for the same memory location...
			System.out.println("Yay 1");
		}
	}
}