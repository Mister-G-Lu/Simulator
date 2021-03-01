/*This element is heavier than Empty Void but not as heavy as Rare Floating Metal. 
*This means it will fall down when placed in the void and push the void out of the way. 
* Do not hard code the relationship between elements, use getWeight() to determine the relative weight of elements.
*/

import java.awt.Color;

/**
 *  Representation of sand.
 */
public class Sand extends Element {
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public Color getColor() {
		return Color.YELLOW;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWeight() {
		return Integer.MAX_VALUE-1;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void fall(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		/* When this element "falls" it does the following: 
		* If possible, i.e. if it is heavier than the element under it, it falls straight down.
		* If it can’t fall straight down, then it might fall diagonal left or diagonal right, 
		* but only if it is heavier than the element in that direction.
		*  If it could fall either way, it randomly chooses between the two.
		*/
		// only fall if you can fall.
		if (row<grid.size()-1){
			Element below = grid.get(row+1).get(col);
			if(this.compareTo(below)>0 && below.push(grid, row+1, col)){
				// System.out.println("Letting R:" + row + " C: " + col+ " fall");
				// swap elements regarding this row and the lower row
				swap(grid, row, col, row+1, col);
			}
			else{
				Element curr = grid.get(row).get(col); //replace it with itself if can't access left/right
				Element leftBelow = (col>0)? grid.get(row+1).get(col-1): curr;
				Element rightBelow = (col< grid.get(row+1).size()-1) ? grid.get(row+1).get(col+1) : curr;
				
				boolean fallLeft = false;
				boolean fallRight = false;
				if(leftBelow!= null && this.compareTo(leftBelow)>0){fallLeft = true;}
				if(rightBelow!= null && this.compareTo(rightBelow)>0){fallRight = true;}
				if (fallLeft && fallRight){
					//generate 0 or 1
					int randomInt = (int)(Math.random()*2);
					if (randomInt == 0){
						//fall left
						swap(grid, row, col, row+1, col-1);
					}	
					else{	
						//fall right
						swap(grid, row, col, row+1, col+1);
					}
				}
				else if(fallLeft){
					swap(grid, row, col, row+1, col-1);
				}
				else if(fallRight){
					swap(grid, row, col, row+1, col+1);
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
	* {@inheritDoc}
	*/
	@Override
	public boolean push(DynamicArray<DynamicArray<Element>> grid, int row, int col) {
		/* When this element is "pushed" it tries to push elements upward.
		* If that doesn’t work, it tries to push to the left. 
		* If that doesn’t work, it compresses itself (see the Empty Void element).
		*/
		if(row>0 && grid.get(row-1)!=null && !pushUp(grid,row,col)){
		    if(col>0 && grid.get(row)!=null && grid.get(row).get(col-1)!=null && !pushLeft(grid,row,col)){
	            return true;
		    }
		}
		return true;
	}

	/**
	 *  Main method that shows an example of testing an element.
	 *  
	 *  @param args not used
	 */
	public static void main(String args[]){
		//This is an outline of how to test that an element
		//is doing what you want it to (without using the
		//simulator. You should definitely write similar tests
		//for Sand and Water.
		
		//All tests work like this:
		//    (1) setup a scenario
		//    (2) call some method(s) that will change (or not change) the scenario
		//    (3) check that what should happen did happen
		//Example:
		
		//(1) setup a scenario
		
		//create a grid and a piece of Sand
		DynamicArray<DynamicArray<Element>> grid = new DynamicArray<>();
		// initialize the grid to a 3x3 area
		for (int i=0;i<3;i++){	
			DynamicArray<Element> cols = new DynamicArray<>(3);
			for (int j=0;j<3;j++){
				cols.add(new Empty());
			}
			grid.add(cols);
		}
		Sand s = new Sand();
		
		//put the element in the middle
		grid.get(1).set(1, s);
		
		System.out.println("Sand Grid: " + grid.toString());
		
		//(2) call some method(s) that will change (or not change) the scenario
		s.fall(grid, 1, 1);
		
		//(3) check that what should happen did happen
		
		//if it was sand, it would be at grid.get(2).get(1)
		//if it was water, it might be at grid.get(1).get(0), grid.get(1).get(2), or grid.get(2).get(1)
		
		if(grid.get(2).get(1) == s) {
			//^ careful about == here, that's checking for the same memory location...
			System.out.println("Yay 1");
		}

		
		DynamicArray<DynamicArray<Element>> sandy = new DynamicArray<>();
		// initialize the grid to a 5x5 area
		for (int i=0;i<5;i++){	
			DynamicArray<Element> cols = new DynamicArray<>(3);
			for (int j=0;j<5;j++){
				cols.add(new Sand());
			}
			sandy.add(cols);
		}
		sandy.get(2).set(3,new Empty());
		System.out.println("Sandy: " +  sandy.toString());
		
		int rowsize = sandy.get(0).size();
		// test out the resize pushing left... (remove all but column 1)
		// this has problems with regards to small grid's idea, but has problem with bigger grid.
		for (int i=0; i<sandy.size();i++){
			if (sandy.get(i)!=null){
				for (int j= rowsize-1; j>0; j--){
				    if(sandy.get(i).get(j)!= null){
						sandy.get(i).get(j).pushLeft(sandy, i, j);	
					}
				}
			}
		}
		
		System.out.println("Sandy: " +  sandy.toString());
		
		for (int i=0; i<sandy.size();i++){
            if (sandy.get(i)!=null){
                System.out.println("Visiting i = " + i);
                for (int j= rowsize-1; j>0; j--){
                    System.out.println("\tVisiting j = " + j);
                    if(sandy.get(i).get(j)!= null){
                        try {
                            sandy.get(i).remove(j);  
                        }catch(IndexOutOfBoundsException e) {
                            System.out.println("Problem at i= "+ i + " j= " +j);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        System.out.println("Sandy: " +  sandy.toString());
	}
}