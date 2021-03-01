/**
 *  The simulator. This tracks the elements in a grid
 *  and coordinates that with the display.
 *  
 *  @author Dave Feinberg, K. Raven Russell, and Goodwin Lu
 */
public class Simulation {

    //******************************************************
    //*******  DO NOT EDIT ANYTHING IN THIS SECTION  *******
    //*******        But do read this section!       *******
    //******************************************************

    /**
     *  The default number of rows the grid should have.
     */
    public static final int INIT_ROWS = 120;

    /**
     *  The default number of columns the grid should have.
     */
    public static final int INIT_COLS = 80;

    /**
     *  The grid that represents the location of each element.
     */
    private final DynamicArray<DynamicArray<Element>> grid;

    /**
     *  The GUI display.
     */
    private final Display display;

    //******************************************************
    //* END DO-NOT-EDIT SECTION -- DO NOT ADD MORE FIELDS! *
    //******************************************************

    /**
     *  Sets up the instance variables (grid and display).
     *  
     *  @param withDisplay whether or not the display should be created (for testing purposes)
     */
    public Simulation(boolean withDisplay) {
        // Initialize the grid (see above) to the default size (see above).
        // Fill the grid with empty void.

        // If the simulation should be created with a display, then set display
        // (see above) to a new display. Use the title "Project 1 Simulation",
        // the default number of rows and columns (see above), and the display
        // constructor (see Display.java) to do this.

        // If the simulation should be created without a display, then initialize
        // the display to null (or Java will yell at you).
        grid = new DynamicArray<DynamicArray<Element>>(INIT_ROWS);

        for (int i=0; i<INIT_ROWS;i++){
            DynamicArray<Element> cols = new DynamicArray<Element>(INIT_COLS);
            for (int j=0; j<INIT_COLS; j++){
                cols.add(new Empty());
            }
            grid.add(cols);
        }
        if (withDisplay){
            display = new Display("Project 1 Simulation", INIT_ROWS, INIT_COLS);
        }
        else{
            display = null;
        }

        // System.out.println("Finished constructing simulator.");
        //System.out.println("Grid: " + grid.toString());

    }

    /**
     *  This is called when the user clicks on a location using the given tool.
     *  
     *  @param row the row where the action happened
     *  @param col the column where the action happened
     *  @param newElem the element the user has created to put there
     */
    public void locationClicked(int row, int col, Element newElem) {
        // Put the new element in the grid at the row and column indicated.
        if(row>0 && row<grid.size() && grid.get(row)!=null){
            grid.get(row).set(col, newElem);
        }
    }

    /**
     *  Copies each element of grid's color into the display.
     */
    public void updateDisplay() {

        // Remember: Display has a setColor(row, col, color) method, and elements have a getColor() method.
        for (int i=0; i<grid.size();i++){
            for (int j=0; j<grid.get(i).size();j++){
                if(grid.get(i)!= null && grid.get(i).get(j)!= null){
                    display.setColor(i, j, grid.get(i).get(j).getColor());
                }
            }
        }
    }

    /**
     *  Resizes the grid (if needed) to a bigger or smaller size.
     *  
     *  @param numRows the new number of rows the grid should have
     *  @param numCols the new number of columns the grid should have
     *  @return whether or not anything was changed
     */
    public boolean resize(int numRows, int numCols) {		
        // Ignore the resize request if an invalid value is given
        boolean changed = false;
        if (numRows>0 && numCols >0){

            //    add/remove all rows before any columns		
            int rowSize = grid.get(0).size();
            int rowCount = grid.size();
            if(numRows!= rowCount && numCols!=rowSize){
                //as long as the new numrow and new numcol aren't same as before, it is changed.
                changed = true;	
            }

            // Rows are added to the top. Works thus far.
            for (int i=rowCount; i<numRows;i++){
                DynamicArray<Element> cols = new DynamicArray<Element>(rowSize);
                // Added space is just filled with empty void.
                for (int j= 0; j<cols.capacity();j++){	
                    cols.add(new Empty());
                }
                grid.add(0,cols);
            }

            // Removed rows are just removed. this works thus far.
            for (int i=rowCount; i>numRows;i--){
                System.out.println("Removing row");
                grid.remove(0);
            }

            // columns are added to (and removed from) the right. Use grid.size() as rows may have changed. Works thus far.
            for(int j=rowSize; j<numCols;j++){
                for (int i= 0; i<grid.size();i++){
                    if(grid.get(i)!=null){
                        grid.get(i).add(new Empty());
                    }
                }
            }
            
            // Removed columns work as follows:
            // If the removed element is as heavy (or heavier) than the element to the left, it will try to push left
            // If the removed element is lighter or can't be pushed, the removed element is just lost.
            int gridsize = grid.size();
            int rowsize = grid.get(0).size();
            for (int i=0; i<gridsize; i++){
                if (grid.get(i)!=null){
                    for (int j=rowsize-1; j>=numCols && j>0; j--){
                        if(grid.get(i).get(j)!= null){
                            grid.get(i).get(j).pushLeft(grid, i, j);
                        }
                    }
                }
            }

            for (int i=0; i<gridsize; i++){
                //System.out.println("i = " + i);
                if (grid.get(i)!=null){
                    for (int j=rowsize-1; j>=numCols && j>0; j--){
                        // System.out.println("\t j=" + j);
                        grid.get(i).remove(j);
                    }
                }
            }

        }
        return changed;

    }

    /**
     *  Indicates the private post where you have shown off your
     *  new element(s).
     *  
     *  @return the post where you describe your new element
     */
    public static String newElementPost() {
        //[GUI:Advanced] change this to return the FULL URL of
        //the post where the grader can find your new element
        //demo, but ONLY if you completed the [GUI:Advanced] part
        //of the project
        return "https://piazza.com/class/kjltepp378t4vh?cid=379";
    }

    //******************************************************
    //*******  DO NOT EDIT ANYTHING BELOW THIS LINE  *******
    //*******        But do read this section!       *******
    //******************************************************

    /**
     *  Causes one random particle to maybe do something. Called
     *  repeatedly.
     */
    public void step() {
        int row = (int)(Math.random()*grid.size());
        int col = (int)(Math.random()*grid.get(row).size()) ;

        grid.get(row).get(col).fall(grid, row, col);
    }

    /**
     *  Game loop of the program (step, redraw, handlers, etc.).
     */
    public void run() {
        while (true) {
            //step
            for (int i = 0; i < display.getSpeed(); i++) step();

            //redraw everything
            updateDisplay();
            display.repaint();

            //wait for redrawing and for mouse
            display.pause(1);

            //handle person actions (resize and tool usage)
            if(display.handle(this) && display.pauseMode()) {
                //for debugging
                updateDisplay();
                display.repaint();
                display.pause(5000);
            }
        }
    }

    /**
     *  Convenience method for GTA testing. Do not use this in
     *  your code... for testing purposes only.
     *  
     *  @return the private grid element
     */
    public DynamicArray<DynamicArray<Element>> getGrid() {
        return grid;
    }

    /**
     *  Main method that kicks off the simulator.
     *  
     *  @param args not used
     */
    public static void main(String[] args) {
        (new Simulation(true)).run();
    }
}
