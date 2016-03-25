package csc143.sudoku;
/**
 * Sudoku Base is a set of blueprints that can then be turned into a setup sudoku board
 * @author Dan, Jim Vermillion
 * @version HW8 Sudoku Layout
 */
public abstract class SudokuBase extends java.util.Observable {
    
   private final int rows;
   private final int columns;
   private final int size;
   
   /**
    * enums of constants that describe the states of the rows, blocks, or columns.
    */
   public enum State {COMPLETE, INCOMPLETE, ERROR};

   /**
    * Constructor for the sudoku base. Initializes the instance variables
    * @param layoutRows the rows requested for the board
    * @param layoutColumns the columns instructed for the board
    */
   public SudokuBase(int layoutRows, int layoutColumns) {
      rows = layoutRows;
      columns = layoutColumns;
      size = columns * rows;
   }
   
   /**
    * Returns the number of rows of each section of the board.
    * @return int the rows of the board
    */
   public int getRows() {
      return rows;
   }
   
   /**
    * Returns the number of rows of each section of the board.
    * @return int the columns of the board
    */
   public int getColumns() {
      return columns;
   }
   
   /**
    * Returns the number size (rows * columns) of the board.
    * @return int the size of the board.
    */
   public int getSize() {
      return size;
   }
   
   /**
    * Return the value at the specified columns.
    * @param row the specified row to get the value of
    * @param col the specified column of the value
    * @return int returns the value at the specified columns
    */
   public abstract int getValue(int row, int col);
   
   /**
    * Sets the value of the cell in the specified row and column.
    * @param row the specified row to set
    * @param col the specified column to set
    * @param value the specified value to set the cell to.
    */
   public abstract void setValue(int row, int col, int value);

   /**
    * Returns whether the specified cell is given or not.
    * @param row the specified row to see if the cell is given
    * @param col the specified column to see if the cell is given
    * @return whether the cell is given or not.
    */
   public abstract boolean isGiven(int row, int col);
   
   /**
    * holds the given cells in place so they cannot be reset.
    */
   public abstract void fixGivens();

   /**
    * Returns the state (COMPLETE, INCOMPLETE, or ERROR) of the specified row.
    * @param n the queried row
    * @return State the enum version of the state current state of the row (COMPLETE, 
    *         INCOMPLETE, or ERROR)
    */
   public abstract State getRowState(int n);
   
   /**
    * Returns the state (COMPLETE, INCOMPLETE, or ERROR) of the specified column.
    * @param n the queried column
    * @return State the enum version of the state current state of the row (COMPLETE, 
    *         INCOMPLETE, or ERROR)
    */
   public abstract State getColumnState(int n);
   
   /**
    * Returns the state (COMPLETE, INCOMPLETE, or ERROR) of the specified region.
    * @param n the queried region
    * @return State the enum version of the state current state of the row (COMPLETE, 
    *         INCOMPLETE, or ERROR)
    */
   public abstract State getRegionState(int n);

}
