package csc143.sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;
/**
 * The final installment of Sudoku.  This class makes a panel that displays the applicable
 * symbols to choose from for the gameboard.
 * 
 * @author Jim Vermillion
 * @version HW11 Sudoku Controller 
 */

class StatePanel extends JComponent implements java.util.Observer{
    StatePanels[] stateComponents;
    SudokuBase model;
    SudokuModel.Set set;

    /**
     * Constructor for StatePanel
     * @param set lets the StatePanel know if it's dealing with rows, columns, or 
     * regions.
     * @param base the model to observe.
     */
    StatePanel(SudokuModel.Set set, SudokuBase base){
        //initiate instance variables
        this.model = base;
        this.set = set;
        stateComponents = new StatePanels[ model.getSize() ];

        setPreferredSize( new Dimension(50,50) );
        //get cues from the model.
        model.addObserver(this);

        //set the layout
        if ( set == SudokuModel.Set.ROW ){
            setLayout(new GridLayout( model.getSize(), 1 ) );
        }else if ( set == SudokuModel.Set.COLUMN ){
            setLayout(new GridLayout( 1, model.getSize() ) );
        }else { //( set == REGION ){
            setLayout(new GridLayout( model.getColumns(), model.getRows() ));
        }
        setBorder(SudokuBoard.borderGetter());
        //populate this panel with stateComponents[] panels 
        for ( int i = 0 ; i < model.getSize() ; i++ ){
            stateComponents[i] = new StatePanels(SudokuBase.State.INCOMPLETE);
            add( stateComponents[i] );
        }
    }

    /**
     * updates when model changes
     * @param o the object that is observed
     * @param obj the object that it may pass
     */
    @Override
    public void update(java.util.Observable o, Object obj){
        updateState();
    }

    /**
     * how the state is updated 
     */
    private void updateState(){
        //check the state (different procedures for row col or region)
        if ( set == SudokuModel.Set.ROW ){
            for ( int r = 0 ; r < model.getSize() ; r++ ){
                stateComponents[r].state = model.getRowState(r);
                stateComponents[r].repaint();
            }
        }else if ( set == SudokuModel.Set.COLUMN ){
            for ( int c = 0 ; c < model.getSize() ; c++ ){
                stateComponents[c].state = model.getColumnState(c);
                stateComponents[c].repaint();
            }
        } else if( set == SudokuModel.Set.REGION ){
            for ( int reg = 0 ; reg < model.getSize() ; reg++ ){
                stateComponents[reg].state = model.getRegionState(reg);
                stateComponents[reg].repaint();
            }
        } else System.out.println("nothing getting thru here");
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
     * private inner class to make up each part of the visual state panels
     * 
     */
    private class StatePanels extends JPanel{
        //the state of the StatePanel
        SudokuModel.State state;
        /**
         * each individual row or column's visual interpretation.
         * @param state the state of the this panel
         */
        private StatePanels(SudokuModel.State state){
            this.state = state;
        }

        /**
         * how the panel will be painted
         * @param g the graphics object to paint with
         */
        @Override
        public void paintComponent(Graphics g){

            //get the right color
            if ( state == SudokuModel.State.COMPLETE ){
                g.setColor( Color.green );
            }else if ( state == SudokuModel.State.INCOMPLETE ){
                g.setColor( Color.yellow );
            } else { //ERROR
                g.setColor( Color.red );
            }

            //fill the cell with the color
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}