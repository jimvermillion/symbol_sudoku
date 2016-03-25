package csc143.sudoku;

/**
 * SudokuModel is a set of blueprints that can then be turned into a setup sudoku board
 * @author Dan, Jim Vermillion
 * @version HW8 Sudoku Layout
 */
public class SudokuModel extends SudokuCore {
    
    @Override
    public void setValue(int row, int col, int value) {
        super.setValue(row, col, value);
        rowColumn rcv = new rowColumn(row, col) ;
        notifyObservers( rcv );
    }
    
    /**
     * Sets up the sudoku board with number of rows and columns
     * @param r rows in the model
     * @param c columns in the model
     */
    public SudokuModel(int r, int c) {
        super(r, c);
    }
    
    /**
     * gets the state of a row
     * @param n the row to be checked
     * @return the state of the row
     */
    public State getRowState(int n) { 
        return checkState(getRowIterator(n));
    }
    
    /**
     * gets the state of a column
     * @param n the column to be checked
     * @return the state of the column
     */
    public State getColumnState(int n) { 
        return checkState(getColumnIterator(n)); 
    }
    
    /**
     * gets the state of a region
     * @param n the region to be checked
     * @return the state of the region
     */
    public State getRegionState(int n) { 
        return checkState(getRegionIterator(n));
    }
    
    /**
     *  gets a row iterator
     * @param n the row in question
     * @return iter iterator of a row 
     */
    private java.util.Iterator getRowIterator(int n){
        return new SudokuIterator(n, Set.ROW);
    }
    
    /**
     * gets a column iterator
     * @param n the column in question
     * @return iter iterator of a column
     */
    private java.util.Iterator getColumnIterator(int n){
        return new SudokuIterator(n, Set.COLUMN);
    }
    
    /**
     * gets a region iterator
     * @param n the region in question
     * @return iter iterator of a region
     */
    private java.util.Iterator getRegionIterator(int n){
        return new SudokuIterator(n, Set.REGION);
    }
    
    /**
     * a refactored method to check the state given an iterator
     * @param iter iterator of a row, column or region
     * @return the State of the row, column of region
     */
    private State checkState( java.util.Iterator iter){
        //create a boolean array to keep track if a value has been used. true for has been used...
        boolean[] values = new boolean[getSize() + 1];
        
        while ( iter.hasNext() ) {
            int v = (int)iter.next();
            if ( !values[v] ){ //if this value is not true (open) //non repeater
                values[v] = true; //and continue with loop.
            }else if( v != 0 && values[v] ){ //not 0 and it's been used! that's an error!
                return State.ERROR;
            }else{} //(v == 0) keep checking
        }
        
        boolean complete = true;
        for (int i = 1 ; i < values.length ; i++){
            if ( values[i] == false ){
                complete = false;
            }
        }
        
        if ( !complete )
            return State.INCOMPLETE;
        else
        return State.COMPLETE;
      
    }
   

    /**
    * enums of constants that describe the which of three: rows, columns, or regions.
    */
    public enum Set {ROW, COLUMN, REGION};
    
    /**
     * Inner class whose object iterates over a region
     */
    private class SudokuIterator implements java.util.Iterator {
        //instance variables 
        Integer startingIndex;
        //refers to next index (not next value)
        Integer next; 
        Set set;
        /**
         * constructs an Iterator that iterates throughout the indexes of a region, row or column.
         * @param region the region to iterate through
         */
        SudokuIterator(int n, Set set){
            //assign which type we're working with (row, column, or region)
            this.set = set;
            int starter;
            
            if (this.set == set.ROW){
                starter = n * getSize();
            }else if (this.set == set.COLUMN){
                starter = n;
            }else // ( this.set == set.REGION )
            /*this equation figures out the first indext of the region.  we need to find out how many
            region rows have been passed with the int division n/rows then multiply that by how many 
            indexes are in a region row (size * rows)...then to find the first index just add the
            remainder multiplied by columns (n % rows)*getCol....easy as that*/
                starter = ( (n/getRows())*getSize()*getRows() + (n % getRows())*getColumns() );
            
            
            //assign the starter to starting index and next
            startingIndex = next = new Integer( starter );
            
        }
        /**
         * returns the next value from the iterator
         * @return Integer
         */
        public Integer next(){
            //translate next (index), to value in the index
            int row = next / getSize();
            int col = next % getSize();
            int value = getValue(row, col);
            
            if(this.set == Set.ROW){      
                //update what this.next will be next
                next++;
                //must check if we've gone out of the region range before we're done
                if ( next >= startingIndex + getSize() )
                    next = null;  
            }else if(this.set == Set.COLUMN){
                //update what this.next will be next
                next += getSize();
                //must check if we've gone out of the region range before we're done
                if ( next >= startingIndex + getSize() * getSize() )
                    next = null;  
                
            }else if (this.set == Set.REGION){
                //find what this.next will be next, check if we're switching rows with a mod.
                if ( (next + 1) % getColumns() != 0 )
                    next++;
                else //add size to get to next row
                    next += getSize() - getColumns() + 1;
                
                //must check if we've gone out of the region range before we're done
                if ( next >= startingIndex + ( getSize() * getRows() ) )
                    next = null;
            }    
                    
            
            return value;
        }
        /**
         * returns whether or not there is another element to iterate over
         * @return whether or not there is a next item
         */
        public boolean hasNext() {
            if (this.next != null)
                return true;
            else
                return false;
        }
        /**
         * unsupported remove method. no point to have in this program
         */
        public void remove(){
            throw new java.lang.UnsupportedOperationException("cannot remove a value");
        }
    }
}