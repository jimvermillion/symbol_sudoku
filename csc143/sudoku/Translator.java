package csc143.sudoku;


/**
 * Write a description of class Translator here.
 * 
 * @author Jim Vermillion
 * @version HW10 SudokuView
 */
class Translator
{
    /**  
     * Input a row number and a column number in the static method 'translate' and it will return 
     * an int array with the region Number and cell index of the sudoku board.
     * @param row the row of the subject cell
     * @param col the column of the subject cell
     * @return int[] array containing the translated region number and index 
     */
    static int[] translateToView(int row, int col, SudokuBase model){
        //1 find how many horizontal regions have been passed
        int horizontalregion = col / model.getColumns();
        //2 find how many vertical regions have been passed
        int verticleregion = row / model.getRows();
        //3 was for 1 being start to array.
        //4 region is how many down * region columns (rows) + regions over to the right                        
        int region = verticleregion * model.getRows() + horizontalregion ;
        //5 index is the modded remnants multed and added up.                     
        int index = ( row % model.getRows() ) * model.getColumns() + col % model.getColumns();
        //6 organize and return
        int[] translated = {region, index};
        return translated;
    }
    /**  
     * Input a REGION number and a INDEX number in this static method and it will return 
     * an int array with the row Number and column of the cell for the sudoku board.
     * @param region the row of the subject cell
     * @param index the column of the subject cell
     * @return int[] array containing the translated row and col 
     */
    static int[] translateToModel(int region, int index, SudokuBase model){

        // region / getRows is how many rows we've passed to get to the start of this region
        // mult that by getRows to get to row at the start of the region
        // add index / getColumns to get to the row we're at 
        // and add one because the user starts counting at one. Then you have the row:
        int row = ( region / model.getRows() ) * model.getRows() + (index / model.getColumns() )  ;

        // region % getRows yields how far right we are at the start of the region
        // mult that by columns to find the column of the start of this region
        // add index / getColumns to go the column we're at
        // and add one because the user starts counting at one.  Then you have the column:
        int col = ( region % model.getRows() ) * model.getColumns() + (index % model.getColumns() ) ;

        int[] translated = {row, col};
        return translated;

    }
}
