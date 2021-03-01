import java.util.Collection;
import java.util.Iterator;

/**
* Dynamic Array class with iterator.
* @param <T> generic object
*/
public class DynamicArray<T> implements Iterable<T> {
	/**
	* Professor's set initial cap.
	*/
	private static final int INITCAP = 2; //default initial capacity
	/**
	* Grid storing the elements.
	*/
	private T[] storage; //underlying array, you MUST use this for credit (do not change the name or type)
	/**
	* Personal variable used to keep track of how many elements within O(1) performance.
	*/
	private int elements = 0; //how much is in the size	
	
	/**
	*default constructor.
	*/
	@SuppressWarnings("unchecked")
	public DynamicArray(){
		// Initial capacity of the storage should be INITCAP
		Object[] a = new Object[INITCAP];
		storage = (T[])a;
	}
	/**
	*constructor that sets size to initCapacity.
	*@param initCapacity the initial size
	*/
	@SuppressWarnings("unchecked")
	public DynamicArray(int initCapacity) {
		
		// Throw IllegalArgumentException if initCapacity < 1
		//    "Capacity cannot be zero or negative."
		if(initCapacity<1){
			throw new IllegalArgumentException("Capacity cannot be zero or negative.");
		}
		
		// The initial capacity of the storage should be initCapacity
		Object[] a = new Object[initCapacity];
		storage = (T[])a;
		
	}

	/**
	* Report the current number of elements. O(1) performance.
	* @return size 
	*/
	public int size() {	
		return elements;
	}  

	/** 
	* Report the max number of elements before expansion. O(1)
	* @return capacity	
	*/
	public int capacity() {
		return storage.length;
	}
	
	/**
 	/* sets the index to value, returning the old item.
	* @param index the index
	* @param value the new value
	* @return old value
	*/
	public T set(int index, T value) {
		
		// For an invalid index, throw an IndexOutOfBoundsException
		// Use this code to produce the correct error message for
		// the exception (do not use a different message):
		//	  "Index " + index + " out of bounds!"
		if(index>=size() || index<0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		T temp = storage[index];
		storage[index] = value;
		return temp;
	}
	/**
	* Get index.
	* @param index the index
	* @return the item at the given index
	*/
	public T get(int index) {
		
		if((index>=size() && index!= 0) || index<0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		
		return storage[index];
	}

	/**
	* Append an element to the *END* of the list.
	* Double the capacity if no space available.
	* @param value the value
	* @return true
	*/
	public boolean add(T value) {
		if (elements==capacity()){
			expand();
		}
		storage[elements] = value;
		elements++;
		
		return true;
	}
	
	/**
	* expands storage array size by 2. (Loop is too slow)
	*/
	
	@SuppressWarnings("unchecked")
	private void expand(){
		Object temp[] = new Object[capacity()*2];
		System.arraycopy(storage, 0, temp, 0, capacity());
		storage = (T[])temp;
	}

	/**
	* shrinks storage array size by 1/2. (Loop is too slow)
	*/
	
	@SuppressWarnings("unchecked")
	private void shrink(){
		Object temp[] = new Object[capacity()/2];
		System.arraycopy(storage, 0, temp, 0, capacity()/2);
		storage = (T[])temp;
	}
	/**
	* Insert the given value at the given index. Shift elements if needed,  
	* double capacity if no space available, throw an exception if you cannot
	* insert at the given index. You _can_ append items with this method.
	* @param index desired index
	* @param value the value
	*/
	public void add(int index, T value) {
		if(index==size()){
			//System.out.println("Transferring to add.");
			add(value);
			return;
		}
		if (elements==capacity()){
			expand();
		}
		
		//System.out.println("Array: " + toString());
		// For the exception, use the same exception and message as set() and get()... 
		if(index>=capacity() || index<0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		if(elements>index){
			try{
				System.arraycopy(storage, index, storage, index+1, elements-index);
			}
			catch(ArrayIndexOutOfBoundsException e){
				int x = elements-index;
				System.out.println("Error at index " + index +" copy " + x + " numbers");
				e.printStackTrace();
			}
		}
		
		if(value!=null){
			storage[index]=value;
			elements++;
		}
	}
	
	/**
	* Remove and return the element the given index. Shift elements
	* to remove the gap. Throw an exception when there is an invalid
	* index (see set(), get(), etc. above).
	* Halve capacity of the storage if the number of elements falls
	* below 1/3 of the capacity.
	* @param index the index
	* @return the element the given index
	*/
	public T remove(int index) {
		if(index>=size() || index<0){
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		
		//System.out.println("Array: " + toString());
		elements--;
		T data = storage[index];
		if (elements<=capacity()/3){
			shrink();
		}
		else{
			if(index<capacity() && elements>index){
				//System.out.println("Elements: "+elements);
				try{
					System.arraycopy(storage, index+1, storage, index, elements-index);
				}
				catch(ArrayIndexOutOfBoundsException e){
					int x = elements-index;
					System.out.println("Error at index " + index +" copy " + x + " numbers");
					e.printStackTrace();
				}
			}	
			
		}
		return data;
	}
	
	/** 
	* the iterator class. Anonymous class style.
	* @return the Iterator
	*/
	public Iterator<T> iterator() {
		
		return new Iterator<T>() {
			//instance variables here
			private int current = 0; //keep track of iteration
			
			/** 
			* return current data and increment.
			*/
			public T next() {
				T data = storage[current];
				current++;
				return data;
			}
			
			public boolean hasNext() {
				return storage[current] != null;
			}
		};
	}
	
	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE.    *******
	//*******      Edit it as much as you'd like!    *******
	//******************************************************
	

	/**
	* testing only method.
	* @return a string version of grid
	*/
	public String toString() {
		//This method is provided for debugging purposes
		//(use/modify as much as you'd like), it just prints
		//out the list ifor easy viewing.
		StringBuilder s = new StringBuilder("Dynamic array with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();
		
	}
	
	/**
	* Main method.
	* @param args dummy variable
	*/
	public static void main(String args[]){

		DynamicArray<Integer> zerotest = new DynamicArray<>();
		for (int i=0;i<3;i++){
			//System.out.println(zerotest.toString());
			zerotest.add(0,i);
		}
		if(zerotest.get(0)!= null  && zerotest.get(1)!= null && zerotest.get(2)!= null&& zerotest.size()==3 && zerotest.capacity()==4){
			System.out.println("Yay 0 (Zero test)");
		}
		if(zerotest.remove(2) == 0 && zerotest.get(1)!= null && zerotest.get(0)!= null){
			System.out.println("Yay 0.1 (Remove Final Element)");
		}
		else{
			System.out.println("Failed to remove final. ToString: " + zerotest.toString());
		}
		//These are _sample_ tests. If you're seeing all the "yays" that's
		//an excellend first step! But it might not mean your code is 100%
		//working... You may edit this as much as you want, so you can add
		//own tests here, modify these tests, or whatever you need!
		
		DynamicArray<Integer> ida = new DynamicArray<>();
		if ((ida.size() == 0) && (ida.capacity() == 2)){
			System.out.println("Yay 1 (Initialization)");
		}

		boolean ok = true;
		for (int i=0; i<3;i++)
			ok = ok && ida.add(i*5);
		
		// array: 0, 5, 10
		if (ok && ida.size()==3 && ida.get(2) == 10 && ida.capacity() == 4 ){
			System.out.println("Yay 2 (Adding at End)");
		}
		
		ida.add(1,-10);
		ida.add(4,100);
		// array: 0, -10, 5, 10, 100
		if (ida.set(1,-20)==-10 && ida.get(2) == 5 && ida.size() == 5 
			&& ida.capacity() == 8 ){
			System.out.println("Yay 3 (Set and Get)");
		}
	
		// 0, -20, 5, 10, 100
		if (ida.remove(0) == 0 && ida.remove(0) == -20 && ida.remove(2) == 100 
			&& ida.size() == 2 && ida.get(0) == 5 && ida.get(1) == 10 &&  ida.capacity() == 4 ){
			System.out.println("Yay 4 (Removing first ones)");
		}
		else{
			System.out.println("Failed remove, expected 5, 10 but got: " + ida.toString());
		}
		
		ida.add(0,1);
		if(ida.get(0)==1 && ida.get(1) == 5 && ida.get(2) == 10 && ida.size() == 3 && ida.capacity()==4){
			System.out.println("Yay 4.1 (Adding at Index after Removing)");
		}
		else
		{
			System.out.println("Failed. Ida: " + ida.toString() + " size: " + ida.size() + " cap: " + ida.capacity());
		}

		//******************************************************
		// more complex tests.
		//******************************************************
		int rownum = 10;
		int colnum = 10;
		int delnum=5;
		DynamicArray<DynamicArray<Element>> pushTest = new DynamicArray<DynamicArray<Element>>(rownum);
		for (int i=0; i<delnum;i++){
            DynamicArray<Element> cols = new DynamicArray<Element>(colnum);
            for (int j=0; j<delnum; j++){
                cols.add(new Sand());
            }
            pushTest.add(cols);
        }
		
		if(pushTest.get(3).get(3).pushLeft(pushTest,1,2)) { 
            System.out.println("Yay 4.11 (Push)");
		}
		if(pushTest.get(1).remove(1)!=null) {
            System.out.println("Yay 4.12 (Removal after Push)");
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
        
        int rowsize = sandy.get(0).size();
        
		for (int i=0; i<sandy.size();i++){
            if (sandy.get(i)!=null){
                System.out.println("Visiting i = " + i);
                for (int j= rowsize-1; j>0; j--){
                    System.out.println("\tVisiting j = " + j);
                    if(sandy.get(i).get(j)!= null){
                        try{
                            sandy.get(i).get(j).pushLeft(sandy, i, j);  
                            sandy.get(i).remove(j);
                        }catch(IndexOutOfBoundsException e){
                            System.out.println("Problem at i = " + i +" j= " + j);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
		DynamicArray<DynamicArray<Element>> grid = new DynamicArray<DynamicArray<Element>>(rownum);
		if(grid.size()== 0 && grid.capacity()==rownum){
			System.out.println("Yay 4.5 (2D initialization)");
		}
		for (int i=0; i<rownum;i++){
			DynamicArray<Element> cols = new DynamicArray<Element>(colnum);
			for (int j=0; j<colnum; j++){
				cols.add(new Empty());
			}
			grid.add(cols);
		}

		for(int i=0;i<delnum;i++){
			grid.remove(0);
		}

		//should be a (rownum-delnum)xcolnum grid now
		//System.out.println("Grid after removing " + delnum + " rows of emptiness: " + grid.toString());
		if(grid.size() == rownum-delnum && grid.capacity() == rownum && grid.get(0).size()==colnum && grid.get(0).capacity() == colnum){
			System.out.println("Yay 5 (Removing Rows)");
		}

		int addnum=5;
		//try to add "addnum" arrount of rows.
		for (int i=0; i<addnum; i++){
			DynamicArray<Element> cols = new DynamicArray<Element>(colnum);
			for (int j= 0; j<cols.capacity();j++){	
				cols.add(new Empty());
			}
			grid.add(0, cols);
		}
	
		//System.out.println("Grid after adding " + addnum + " rows of emptiness: " + grid.toString());
		// (rownum-delnum+addnum)xcolnum array
		if(grid.size()==rownum && grid.capacity()==rownum && grid.get(0).size() == colnum && grid.get(0).capacity()==colnum ){
			System.out.println("Yay 6 (Adding rows)");
		}
		
		int fullsize = 20;
		int gridsize = grid.size();
		rowsize = grid.get(0).capacity();

		// "resize" to size fullsize
		for (int i=gridsize; i<fullsize;i++){
			DynamicArray<Element> cols = new DynamicArray<Element>(rowsize);
			// Added space is just filled with empty void.
			for (int j= 0; j<cols.capacity();j++){	
				cols.add(new Empty());
			}
			grid.add(0, cols);
		}

		//should be fullsize x rowsize grid
		//System.out.println("Resized grid (DyArr): " + grid.toString());
		for (int i=0;i<grid.size(); i++){
			if(grid.get(i)== null){
				//System.out.println("found null at i = " + i);
			}
		}
		//grab the first and a new element
		if(grid.get(0)!=null && grid.size()==fullsize && grid.get(0).size() == colnum){
			System.out.println("Yay 7 (Resize Basics)");
		}

		//System.out.println("DyArr Before Falling: " + grid.toString());
		boolean fallsucceed=true;
		for (int i=0;i<500;i++){
			int row = (int)(Math.random()*grid.size());
			int col = (int)(Math.random()*grid.get(row).size()) ;
			if(grid.get(row)== null) {
				fallsucceed=false;
			}
			else if(grid.get(row).get(col)== null){
				fallsucceed=false;
			}
			else{
				grid.get(row).get(col).fall(grid, row, col);
			}
		}
		if (fallsucceed){
			System.out.println("Yay 8 (Random Fall in DyArrTest)");
		}
		
		
		//Uncomment this after doing the iterator for testing
		/*
		System.out.print("Printing values: ");
		for(Integer i : ida) {
			System.out.print(i);
			System.out.print(" ");
		}
		*/
	}
}