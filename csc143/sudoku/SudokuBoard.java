package csc143.sudoku;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
/**
 * Sudoku Board is set up to take a sudoku base that delivers a set of instructions including
 * how many rows and columns to include in the sudoku board.  This class takes these instructions
 * and creates an empty sudoku board component.
 * 
 *
 * @author Jim Vermillion
 * @version HW8 Sudoku Board
 */
public class SudokuBoard extends javax.swing.JComponent {
    //size of each cell
    public final static int CELL_SIZE = 50;

    // instance variables of size, rows and columns
    private int size;
    private int rows;
    private int columns;
    private static final Dimension D = new Dimension( 50,50 );
    private static final Color SELECTED_COLOR = Color.yellow;
    private Color given = new Color(0, 128, 0);
    private Color normal = Color.black;

    SPanel[][] cellPanel;
    private SudokuBase base;

    /**
     * Sets up an empty board, specifications given by the SudokuBase in the parameter
     * @param b the SudokuBase to work from
     */
    public SudokuBoard(SudokuBase b) {
        //initialize instance variables
        size = b.getSize();
        rows = b.getRows();
        columns = b.getColumns();

        //make a reference to the sudoku base.
        base = b;
         
        //and a way to access the cells
        cellPanel = new SPanel[size][size];

        //set preferred size
        setPreferredSize(new java.awt.Dimension(b.getSize() * CELL_SIZE, b.getSize() * CELL_SIZE));

        //make a starting and next color for each row.
        java.awt.Color  startingColor = java.awt.Color.white; 
        java.awt.Color nextColor = java.awt.Color.lightGray;

        //declare a column position variable that points to where the loop is in regards to section
        //columns (note: since rows/cols are reversed, this is actually based on getRows)
        //the loop populates the 
        int colPosition = 0;
        for (int j = 0; j < rows * columns ; j++){
            for (int i = 0; i< size ; i++){

                Color bc;
                //assign the i cell panel to a new SPanel

                if ( colPosition % 2 == 0 ){
                    bc = startingColor;
                }else{    
                    bc = nextColor;
                }
                
                
                
                cellPanel[j][i] = new SPanel(j, i, java.awt.Color.black, bc);
            }

            //update the column position for the next column
            colPosition = (j+1) % rows;

            //switch color scheme at the end of the rows if even numbers of columns
            if ( colPosition == 0 ){
                java.awt.Color temp = startingColor;
                startingColor = nextColor;
                nextColor = temp;
            }
        }
        
        //make a main panel with a border (type P) and set the layout to gride (reversed rows and cols)
        JPanel theMainPanel = new JPanel();
        theMainPanel.setSize( CELL_SIZE * size, CELL_SIZE * size );
        theMainPanel.setLayout(new GridLayout( columns, rows ) );

        //make a main border around the main panel
        theMainPanel.setBorder( borderGetter() );
        
        for (int m=0 ; m < size ; m++ ){
            
            //new panel to put each SPanel in (gridlayout)
            JPanel section = new JPanel();
            section.setLayout( new GridLayout( rows,columns ) );
            for (int n = 0 ; n < size ; n++){
                section.add( cellPanel[m][n] );
            }
            //add the completed section to the main panel
            theMainPanel.add(section);
        }

        //add the completed panel to the SudokuBoard
        add(theMainPanel);
    }

    /**
     * Paints The Sudoku Board component
     * @param g The graphics object to render the SudokuBoard Component
     */
    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        for (int m=0 ; m < size ; m++ ){
            for (int n = 0 ; n < size ; n++){
                cellPanel[m][n].repaint();
            }
            
        }
        

    }
    
    /**
     * creates a border to spec and returns it.
     * @return the border that was created.
     */
    public static Border borderGetter(){
        //make an inner and outer border
        Border lineBorder = BorderFactory.createLineBorder(java.awt.Color.black);
        Border emptyBorder = BorderFactory.createEmptyBorder(2,2,2,2);
        //and compound them
        Border outsideBorder = BorderFactory.createCompoundBorder( emptyBorder, lineBorder );
        return outsideBorder;
    }
    
    /**
     * SPanels are what each cell of the sudoku board is.  The class is like a JPanel but 
     * has a Symboler that draws the value set to the row and column it is assigned to.
     * 
     */
    class SPanel extends javax.swing.JComponent
    {
        int region;
        int index;
        Color c;
        Color bc;
        boolean isSelected;
        Symboler sym;

        /**
         * Constructor for objects of class SPanel
         * @param region of symbol drawing.
         * @param index of symbol drawing.
         * @param c color of symbol drawing.
         * @param bc background color of symbol drawing.
         */
        private SPanel(int region, int index, Color c, Color bc)
        {
            this.region = region;
            this.index = index;
            this.c = c;
            this.bc = bc; 
            sym = new Symboler();

            isSelected = false;
            setPreferredSize( D );
            setBorder( borderGetter() );
        }
        
         /**
         * Paints The Sudoku Board cell
         * @param g The graphics object to render the SudokuBoard Component
         */
        public void paintComponent(Graphics g){
            if ( isSelected ){
                g.setColor( SELECTED_COLOR );
            }
            else{
                g.setColor( bc );
            }
            
            //draw the background
            g.fillRect(0,0,CELL_SIZE,CELL_SIZE);
            
            //get value from translating this region and index
            int[] rowCol = Translator.translateToModel( region, index, base );
            
            int row = rowCol[0];
            int col = rowCol[1];
            
            //set the color for drawing the symbol
            if (base.isGiven(row, col)){
                g.setColor(given);
            }
            else{
                g.setColor(normal);
            }
            
            
            int value = base.getValue( row, col);
            sym.drawSymbol(g, 5, 5, value);

            
        }
    }
}
