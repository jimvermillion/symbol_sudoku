package csc143.sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * The final installment of Sudoku.  This class integrates the Sudoku Game.
 * 
 * @author Jim Vermillion
 * @version HW11 Sudoku Controller 
 */
public class SudokuController  
{
    //the game being played, the newGame if new game is being used and the view we're displaying.
    SudokuModel game;
    SudokuModel newGame;
    SudokuView view;
    //all of these instance variables are used in inner classes, thus needed here
    JButton newGameButton;
    JButton cancel;
    JButton setGivens;
    JFrame win;
    JPanel mainPanel;
    /**
     * Constructs th SudokuController
     * @param base the model we're using for the game.
     */
    public SudokuController(SudokuModel base){
        game = base;
        mainPanel = new JPanel();
        win = new JFrame("SYMBOL SUDOKU");
        setupWin( base);
    }

    /**
     * initiates the setup mode
     */
    private void setupMode(){
        mainPanel.setBackground(Color.green);
        win.setTitle("setup mode -- setup mode -- setup mode");
    }

    /**
     * refactored game setup heavywork.
     * @param base the model we're using for the game. it's a param because it may switch
     * if a new game is called.
     */
    private void setupWin(SudokuModel base){
        //main panel
        mainPanel = new JPanel();
        //start in newGame until we're sure we set givens
        newGame = base;
        //register the new view with the new game
        view = new SudokuView(newGame);
        //setLayout of the main panel and some composite panels
        mainPanel.setLayout(new GridLayout(0,2));
        JPanel rightTools = new JPanel();
        rightTools.setLayout(new GridLayout(2,2));

        //these panals make up the state representations
        JPanel threeStates = new JPanel();
        threeStates.setLayout(new GridLayout( 1, 3));
        threeStates.add( new StatePanel(SudokuModel.Set.ROW, base) );
        threeStates.add( new StatePanel(SudokuModel.Set.REGION, base) );
        threeStates.add( new StatePanel(SudokuModel.Set.COLUMN, base) );

        //composite adding including black panels for space
        JPanel lowerRight = new JPanel();
        lowerRight.setLayout(new GridLayout(3,1));
        lowerRight.add( new JPanel() );
        lowerRight.add(new JPanel() );
        lowerRight.add(threeStates);

        //button initiations with mouse listener registering
        newGameButton = new JButton("NEW GAME");
        cancel = new JButton("cancel");
        setGivens = new JButton("Set Givens");
        Mousie mousie = new Mousie();
        setGivens.addMouseListener( mousie );
        cancel.addMouseListener( mousie );
        newGameButton.addMouseListener( mousie );

        //layout for a couple buttons
        JComponent setCancel = new JPanel();
        setCancel.setLayout( new GridLayout( 2,1 ) );
        setCancel.add(setGivens);
        setCancel.add(cancel);

        //add the symbol bank, buttons, and state panel
        rightTools.add(new SymbolBank(view) );
        rightTools.add( newGameButton );
        rightTools.add(lowerRight);
        rightTools.add( setCancel );

        //add the composite panels into the main panel
        mainPanel.add(view);
        mainPanel.add(rightTools);

        //we'll start in setup mode
        setupMode();
        //condition the background and window title to reflect if givens have been set
        if ( haveGivensBeenSet(base) ){
            givenSetter();
        }

        //add the main and show the window.
        win.add(mainPanel);
        win.pack();
        win.setVisible(true);
    }

    /**
     * makes conditions of normal play
     */
    private void givenSetter(){
        mainPanel.setBackground(Color.white);
        cancel.setEnabled(false);
        setGivens.setEnabled(false);
        win.setTitle("SYMBOL SUDOKU");
    }

    /**
     * checks to see if we have givens already 
     */
    private boolean haveGivensBeenSet(SudokuModel model){
        boolean anyGivens = false;
        for (int r = 0; r < model.getSize() ; r++){
            for(int c =0; c < model.getSize() && !anyGivens; c++){
                if ( model.isGiven(r,c)  ){
                    anyGivens = true;
                }
            }
        }
        return anyGivens;
    }
    /**
     * heavy inner mouse adapter class to control flow from events
     */
    private class Mousie extends MouseAdapter{
        /**
         * handles it the mouse has been clicked
         * @param e the MouseEvent
         */
        @Override
        public void mouseClicked( MouseEvent e ){
            if (e.getSource() == newGameButton){
                //prompt dialog box
                Object[] options = {"Create New Board", "Cancel"};
                JTextField r = new JTextField();
                JTextField c = new JTextField();
                final JComponent[] inputs = new JComponent[] {
                        new JLabel("Number of Rows"), r, new JLabel("Number of Columns"), c
                    };
                int ans = JOptionPane.showOptionDialog(null, inputs, "SYMBOL SUDOKU", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (ans == JOptionPane.NO_OPTION)
                    return;

                //input validation
                int rows = Integer.parseInt(r.getText());
                int cols = Integer.parseInt(c.getText() );
                if (rows*cols > 12){
                    JOptionPane.showMessageDialog(new JFrame(),
                        "Rows x Columns must be less than 12", "Unsupported",
                        JOptionPane.ERROR_MESSAGE);
                } else if (rows < 1 || cols < 1 ){
                    JOptionPane.showMessageDialog(new JFrame(),
                        "Must have positive numbers", "Unsupported",
                        JOptionPane.ERROR_MESSAGE);
                } else{
                    //if inputs are valid let's make a new model
                    newGame = new SudokuModel(rows, cols);
                    //take everything out of the window we're using
                    win.getContentPane().removeAll();
                    //and put in the new game and repaint()
                    setupWin( newGame );
                    win.repaint();
                }
            }
            if ( e.getSource() == setGivens ){
                //if set givens is pressed fixgivens and change up the display
                game = newGame;
                game.fixGivens();
                givenSetter();
            }
            if ( e.getSource() == cancel ){
                //if cancel is pressed, revert to the first game & repaint
                win.getContentPane().removeAll();
                setupWin( game );
                win.repaint();
            }
        }

    }
    /**
     * Starter board for the application to run from
     * @param args input parameters not supported
     */
    public static void main(String[] args){

        SudokuModel board = new SudokuModel(2, 3);
        board.setValue(0, 3, 6);
        board.setValue(0, 5, 1);
        board.setValue(1, 2, 4);
        board.setValue(1, 4, 5);
        board.setValue(1, 5, 3);
        board.setValue(2, 3, 3);
        board.setValue(3, 2, 6);
        board.setValue(4, 0, 2);
        board.setValue(4, 1, 3);
        board.setValue(4, 3, 1);
        board.setValue(5, 0, 6);
        board.setValue(5, 2, 1);
        board.fixGivens();

        new SudokuController( board );

    }
}
