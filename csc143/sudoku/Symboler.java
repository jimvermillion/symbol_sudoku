package csc143.sudoku;

import java.awt.*;
import javax.swing.*;
/**
 * This implementation is create 12 symbols and at this point, is checked with SudokuTest1b.
 * @author Jim Vermillion
 * @version HW8 Sudoku Layout
 */
public class Symboler  implements SymbolRenderer {
    /**
     * Renders a single symbol for the Sudoku game
     * @param x The x-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param y The y-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param g The Graphics object used to draw the symbol
     * @param value The value to be drawn, between 0 and 12,
     * inclusive
     */
    public void drawSymbol(java.awt.Graphics g, int x, int y, int value){
        //widens the width of strokes (3 => one extra pixel on each side.)
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3)); 
        
        switch (value) {
            case 1: 
                //xx
                g.drawLine( x+2, y+2,  x+38, y+19);
                g.drawLine( x+2, y+18, x+38, y+2);
                g.drawLine( x+2, y+19, x+38, y+38);
                g.drawLine( x+2, y+38, x+38, y+19);
                break;
            case 2: 
                // K
                g.drawLine( x+38, y+2,  x+2,  y+18);
                g.drawLine( x+2,  y+19, x+38, y+38);
                break;
            case 3:  
                // L ...sorta the verizon symbol
                g.drawLine( x+2,  y+2,  x+2,  y+38);
                g.drawLine( x+2,  y+38, x+38, y+30);
                break;
            case 4: 
                //sideways spikes
                g.drawLine( x+2,  y+2,  x+38, y+14);
                g.drawLine( x+38, y+15, x+2,  y+19);
                g.drawLine( x+2,  y+19, x+38, y+29);
                g.drawLine( x+38, y+30, x+2,  y+38);
                g.drawLine( x+2,  y+3,  x+2,  y+37);
                break;
            case 5:  
                //vampire teeth
                g.drawLine( x+2,  y+2,  x+38, y+2);
                g.drawLine( x+14, y+38, x+2, y+2);
                g.drawLine( x+15, y+38, x+19, y+2);
                g.drawLine( x+19, y+2,  x+29, y+38);
                g.drawLine( x+38, y+2,  x+30, y+38);
                break;
            case 6: 
                //bubbles
                g.drawOval( x+2,  y+2,  20, 20);
                g.drawOval( x+18, y+18, 20, 20);
                g.drawOval( x+29,  y+10,  2, 2);
                g.drawOval( x+10, y+29, 2, 2);
                
                break;
            case 7:
                //eyeball
                g.drawArc(x+2, y+2, 40, 40, 45, 190);
                g.drawLine(x+11, y +38, x+38, y+9);
                g.drawOval(x+10, y+10, 10, 10);
                g.drawOval(x+30, y+30, 3, 3);
                break;
            case 8:
                // funny n
                g.drawArc(x+2, y+2, 20, 73, 0, 180);
                g.drawLine(x+22, y+38, x+38, y+2);
                break;
            case 9:  
                //oval slash
                g.drawOval(x+2, y+2, 36, 36);
                g.drawOval(x+2, y+12, 36, 10);
                g.drawLine(x+20, y+2, x+20, y+38);
                break;
            case 10: 
                //sharp hourglass 
                int[] xZ= {x+4, x+36, x+4, x+36};
                int[] yZ= {y+2, y+2, y+38, y+38};
                g.drawPolygon(xZ, yZ, 4);
                break;
            case 11:  
                //stellagmites 
                int[] xA= {x+2, x+14, x+14, x+26, x+26, x+38, x+2};
                int[] yA= {y+7, y+33, y+7,  y+33, y+8,  y+38, y+38};
                g.drawPolygon(xA, yA, 7);
                break;
            case 12:  
                // X with little circles
                g.drawLine(x+2, y+2,  x+38, y+38);
                g.drawLine(x+2, y+38, x+38, y+2);
                g.drawOval(x+5, y+19, 1,1);
                g.drawOval(x+19, y+5, 1,1);
                g.drawOval(x+35, y+19, 1,1);
                g.drawOval(x+19, y+35, 1,1);
                break;
            case 100:
                g.drawString("SYMBOL\nSUDOKU",0,0);
        }
    }
    
    
}
