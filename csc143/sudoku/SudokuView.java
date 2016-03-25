package csc143.sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * @author Jim Vermillion
 * @version HW10 SudokuView
 */
public class SudokuView extends SudokuBoard implements java.util.Observer, SelectedCell
{
    SudokuBase model;
    Selected selected;
    SudokuBoard board;

    private static final Color GIVEN_COLOR = new Color(0, 128, 0);
    private static final Color NORMAL_COLOR = Color.black;
    private static final Color SELECTED_COLOR = Color.yellow;

    /**
     * Constructor for objects of class SudokuView. Takes a sudoku base and creates and updates a view of the 
     * board.
     * @param b the sudoku model
     */
    public SudokuView(SudokuBase b){
        /*Pass this SudokuBase object to the SudokuBoard constructor to create a board 
        of the appropriate size and arrangement of regions. */
        super(b); 
        model = b;
        model.addObserver(this);

        //initiate selected to 0,0
        selected = new Selected(0,0);
        setSelected( 0,0 );

        setPreferredSize(new Dimension(50*model.getSize(), 50*model.getSize()));
        setSize(50*model.getSize(), 50*model.getSize());

        //add mouselisteners to each cell.
        for (int r = 0 ; r <  model.getRows() * model.getColumns() ; r++ ){
            for ( int c = 0 ; c <  model.getRows() * model.getColumns() ; c++ ){
                // row and col will be translated to region, index in view
                int[] regionIndex = Translator.translateToView( r, c, model );
                // make it easier to read
                int region = regionIndex[0];
                int index = regionIndex[1];

                //these are made again for the mouseListener anonymous class...won't compile otherwise
                int row = r;
                int col = c;
                cellPanel[region][index].addMouseListener(new MouseAdapter() {
                        /**
                         * the mouse was clicked!
                         * @param e the mouse event
                         */
                        public void mouseClicked(MouseEvent e) {
                            setSelected( row, col );
                        }
                    });

            }
        }
    }

    /**
     * Paints the sudoku view
     * @param g the graphics object to paint the view with.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //since the cells internally figure out what value to draw, the view doesn't need to do anything
        //but call the super's paintComponent...except make sure to paint given colors at this point
        for (int r = 0 ; r <  model.getRows() * model.getColumns() ; r++ ){
            for ( int c = 0 ; c <  model.getRows() * model.getColumns() ; c++ ){

                // row and col will be translated to region, index in view
                int[] regionIndex = Translator.translateToView( r, c, model );
                // make it easier to read
                int region = regionIndex[0];
                int index = regionIndex[1];

                //different color if given
                if ( model.isGiven( r, c ) ){
                    cellPanel[region][index].c = GIVEN_COLOR ;
                } else {
                    cellPanel[region][index].c = NORMAL_COLOR ;
                }
                cellPanel[region][index].repaint();
            }
        }
    }

    /**
     * this is called whenever the observed object is changed. 
     * An application calls an Observable object's notifyObservers 
     * method to have all the object's observers notified of the change.
     * @param o the Observable object that is changed
     * @param arg an argument passed to the notifyObservers method.
     */
    public void update(java.util.Observable o, Object arg) {
        if (arg != null){
            //translate the arg contents
            rowColumn rcv = ( rowColumn ) arg;
            int[] rI = Translator.translateToView( rcv.row() , rcv.col(), model );
            //repaint just that cell
            cellPanel[rI[0]][rI[1]].repaint();
        } else{
            repaint();
            super.repaint();
        }
    }

    /**
     * Set the selected cell to the given row and column.
     * @param row The indicated row
     * @param col The indicated column
     */
    public void setSelected(int row, int col){
        //clean up old background color back to og
        cellPanel[ selected.region ][ selected.index ].isSelected = false;
        cellPanel[ selected.region ][ selected.index ].repaint();

        //set selected to a new Selected object with updated row and col and color it selected
        this.selected.changeSelected(row, col);
        cellPanel[ selected.region ][ selected.index ].isSelected = true;
        cellPanel[ selected.region ][ selected.index ].repaint();
    }

    /**
     * Retrieve the row of the currently selected cell.
     * @return The row in which the selected cell is located.
     */
    public int getSelectedRow(){
        //translate and return
        int[] rowCol = Translator.translateToModel(this.selected.region, this.selected.index, model);
        return rowCol[0];
    }

    /**
     * Retrieve the column of the currently selected cell.
     * @return The column in which the selected cell is located.
     */
    public int getSelectedColumn(){
        //translate and return
        int[] rowCol = Translator.translateToModel(this.selected.region, this.selected.index, model);
        return rowCol[1];
    }

    /**
     * Class that handles the selected cell
     */
    private class Selected {
        private int region;
        private int index;
        /**
         * creates a Selected object to hold which cell is Selected at this time.
         * @param row the row of the cell to be selected
         * @param col the column of the cell to be selected
         */
        private Selected( int row, int col ){
            //hold location and selected original background color
            int[] regionIndex = Translator.translateToView(row, col, model);
            this.region = regionIndex[0];
            this.index = regionIndex[1];
        }

        /**
         * pretty much the same as make a new Selected, but we only need one per board...
         * so changing its instance variables works fine
         * @param row the row of the cell to be selected
         * @param col the column of the cell to be selected
         */
        private void changeSelected(int row, int col){
            //hold location and selected original background color
            int[] regionIndex = Translator.translateToView(row, col, model);
            this.region = regionIndex[0];
            this.index = regionIndex[1];
        }
    }
    public static void test(){
        SudokuBase board = new SudokuModel(2, 3);
        board.setValue(0, 3, 6);
        board.setValue(0, 5, 1);
        board.setValue(1, 2, 4);
        board.setValue(1, 4, 5);
        board.setValue(1, 5, 3);
        board.setValue(2, 3, 3);
        board.setValue(3, 2, 6);
        board.fixGivens();
        board.setValue(4, 0, 2);
        board.setValue(4, 1, 3);
        board.setValue(4, 3, 1);
        board.setValue(5, 0, 6);
        board.setValue(5, 2, 1);
        JFrame win = new JFrame();
        SudokuView v = new SudokuView(board);
        win.add(v);
        win.pack();
        win.setVisible(true);
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        //board.fixGivens();
        
    }
}
