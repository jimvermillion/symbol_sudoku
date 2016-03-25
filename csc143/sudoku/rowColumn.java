package csc143.sudoku;


/**
 * rowColumn is a class to package the row and column of something to be changed for sudokuview
 * 
 */
class rowColumn
{
    
    int row;
    int col;
    /**
     * Constructor for objects of class rowColumn
     * @param row the row to be changed
     * @param col the column to be changed
     */
    rowColumn(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    /**
     * returns the row of the rowColumn
     * @return the row of the rowCoulmn
     */
    int row(){
        return row;
    }
    /**
     * returns the col of the rowColumn
     * @return the col of the rowCoulmn
     */
    int col(){
        return col;
    }
}
