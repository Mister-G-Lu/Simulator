import java.awt.Color;

/**
 *  Representation of Steam, Goodwin's Custom Element.
 */
public class Steam extends Element {
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return Color.WHITE;
	}
	
	
	/**
	* {@inheritDoc}
	* This element is lighter than Empty Void. Somehow.
	 */
	@Override
	public int getWeight() {
		return -1;
	}
	
	/**
	* {@inheritDoc}
	* When this element "falls" it rises instead:
        * It rises if it is lighter than the above element.
	* It first tries to push up.  
	* Then it chooses a random direction (left, right) 
	* and rises that way if any obstacles can be pushed. 
	* If the chosen direction isn’t possible, it gives up. 
	*/

	@Override
	public void fall(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		// only rise if you can do so.
		if (row>0){

			Element above = grid.get(row-1).get(col);
			if(this.compareTo(above)<0 && above.push(grid,row-1, col)){
				// System.out.println("Letting R:" + row + " C: " + col+ " RISE");
				swap(grid, row, col, row-1, col);
			}
			else{
				int randomInt = (int)(Math.random()*2);
			
			 	if(col<grid.get(0).size()-1 && randomInt == 0){
					Element right = grid.get(row).get(col+1);
					if(this.compareTo(right)<0 && right.push(grid,row, col+1)){
						swap(grid,row,col,row,col+1);
					}
				}
				else if(col>0 && randomInt == 1){
					Element left = grid.get(row).get(col-1);
					if(this.compareTo(left)<0 && left.push(grid,row, col-1)){
						swap(grid,row,col,row,col-1);
					}
				}
			}
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
		if(randomInt == 0 && !pushUp(grid,row,col)){
			pushLeft(grid,row,col);
		}
		else if(randomInt == 1 && !pushLeft(grid,row,col)){
			pushUp(grid,row,col);
		}
		return true;
	}
}