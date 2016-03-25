package csc143.sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * The final installment of Sudoku.  This class makes a panel that displays the applicable
 * symbols to choose from for the gameboard.
 * 
 * @author Jim Vermillion
 * @version HW11 Sudoku Controller 
 */
public class SymbolBank extends JPanel
{
    //variables for the bank of symbols
    Symboler sym;
    SudokuBase model;
    SudokuView view;
    //instance variables for mouse listener
    private boolean aButtonIsPressed;
    private SymbolComponent selected;
    private SymbolComponent entered;
    /**
     * The constructor for the symbol bank
     * @param view the SudokuView we're using to sync with
     */
    public SymbolBank(SudokuView view)
    {
        this.view = view;

        model = view.model;

        //these control the states of the symbol bank
        aButtonIsPressed = false;
        sym = new Symboler();
        selected = null;
        entered = null;
        //set the layout and populate the bank

        setPreferredSize(new Dimension( 100, model.getSize()*50 + 50));
        setSize(model.getSize()*50 + 50, 100);

        setLayout( new GridLayout( model.getSize()/3 + 1 , 3 ));//size / 3 + 1, 2 ) );
        for (int i = 0 ; i <= model.getSize() ; i++ ){
            add(new SymbolComponent(i));
        }  
    }

    /**
     * Do what we can to keep this the right size
     * @return the dimension of the size I want this to be
     */
    @Override
    public Dimension getMinimumSize(){
        return new Dimension((model.getSize() / 2 + 1) * 50,100);
    }

    /**
     * Do what we can to keep this the right size
     * @return the dimension of the size I want this to be
     */
    @Override
    public Dimension getPreferredSize(){
        return new Dimension((model.getSize() / 2 + 1) * 50,100);
    }

    /**
     * Do what we can to keep this the right size
     * @return the dimension of the size I want this to be
     */
    @Override
    public Dimension getMaximumSize(){
        return new Dimension((model.getSize() / 2 + 1) * 50,100);
    }

    /**
     * Package Private inner class makes the components that are picked to upate 
     * the sudoku board.
     */
    class SymbolComponent extends JComponent{
        //hold the value of each symbol for the game
        int value;
        /**
         * Singular symbol that make up the symbol bank
         * @param val the value the symbol will draw/represent
         */
        SymbolComponent(int val){
            value = val;
            //set preferred size
            setPreferredSize( new Dimension(50,50));

            //register each symbol component to react to mouse events
            addMouseListener( new MouseAdapter(){
                    @Override
                    public void mouseEntered(MouseEvent e){
                        //highlight if hovered
                        entered = (SymbolComponent)e.getSource();
                        entered.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e){
                        //change the color back -- not hovered..repaint()
                        entered = null;
                        SymbolComponent temp = (SymbolComponent) e.getSource();
                        temp.repaint();
                    }

                    @Override
                    public void mousePressed(MouseEvent e){
                        //note that mouse was pressed and repaint to show it is pressed
                        aButtonIsPressed = true;
                        selected  = (SymbolComponent) e.getSource();
                        selected.repaint();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e){
                        //mouse was let go -- noted
                        aButtonIsPressed = false;
                        //selected isn't anymore 
                        selected  = null;
                        SymbolComponent temp = (SymbolComponent) e.getSource();
                        temp.repaint();
                        //beep or change!
                        if (entered == temp){
                            if ( model.isGiven(view.getSelectedRow(), 
                                view.getSelectedColumn() ) ){
                                //beep if given
                                Toolkit.getDefaultToolkit().beep();
                            } else {

                                model.setValue( view.getSelectedRow(), 
                                    view.getSelectedColumn(), temp.value );

                            }
                        }                 
                    } }) ;
        }

        /**
         * Do what we can to keep this the right size
         * @return the dimension of the size I want this to be
         */
        @Override
        public Dimension getMinimumSize(){
            return new Dimension(50,50);
        }

        /**
         * Do what we can to keep this the right size
         * @return the dimension of the size I want this to be
         */
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(50,50);
        }

        /**
         * Do what we can to keep this the right size
         * @return the dimension of the size I want this to be
         */
        @Override
        public Dimension getMaximumSize(){
            return new Dimension(50,50);
        }

        /**
         * paintComponent paints the Symbol component
         * @param g the graphics that the component is drawn with
         */
        @Override
        public void paintComponent(Graphics g){
            //pick a background color
            if (selected == this && entered == this){
                g.setColor( Color.green );
            } else if (entered == this && !aButtonIsPressed) {
                g.setColor( Color.pink );
            } else {
                g.setColor( Color.white );
            }

            //color background
            g.fillRect(0, 0, getWidth() - 1, getHeight()-1);

            //change color and draw symbol
            g.setColor(Color.black);
            sym.drawSymbol(g,5,5,this.value);
        }
    } 
}
