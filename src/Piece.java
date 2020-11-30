import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JComponent;

public class Piece extends JComponent {
    
    //private final int [][] piece;
    
    private Color c;
    private final Dimension pieceDimension = new Dimension (5, 5);
    private final Dimension squareDimension = new Dimension (10, 10);
    private int[][] pieceStructure;
    
    public Piece (int [][] pieceStructure, Color c) {
        this.pieceStructure = pieceStructure;
        this.c = c;
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(c);
        int initX, initY;
        initX = initY = 0;
        for (int i = 0; i < pieceStructure.length; i++) {
            initX = 0;
            for (int j = 0; j < pieceStructure[0].length; j++) {
                if (pieceStructure[i][j] == 1) {
                    gc.fillRect(initX, initY, squareDimension.width, squareDimension.height);
                    
                }
                initX += squareDimension.width + 1;
            }
            initY+= squareDimension.height + 1;
        }
        pieceDimension.height = initY;
        pieceDimension.width = initX;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return pieceDimension;
    }
    
    
//    public int[][] getStructure () {
//        return piece;
//    }
    
    
}


//private final static int [][] I1 = {{1}};
//private final static int [][] I2 = {{1, 1}};
//private final static int [][] I3 = {{1, 1, 1}};
//private final static int [][] I4 = {{1, 1, 1, 1}};
//private final static int [][] I5 = {{1, 1, 1, 1, 1}};
//private final static int [][] T = {{1, 1, 1}, {0, 1, 0}, {0, 1, 0}};
//private final static int [][] F = {{0, 1, 1}, {1, 1, 0}, {0, 1, 0}};
//private final static int [][] X = {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};
//private final static int [][] U = {{1, 0, 1}, {1, 1, 1}};
//private final static int [][] V5 = {{1, 0, 0}, {1, 0, 0}, {1, 1, 1}};
//private final static int [][] W = {{1, 0, 0}, {1, 1, 0}, {0, 1, 1}};
//private final static int [][] V3 = {{1, 0}, {1, 1}};
//private final static int [][] Z4 = {{1, 1, 0}, {0, 1, 1}};
//private final static int [][] P = {{1, 1}, {1, 1}, {1, 0}};
//private final static int [][] N = {{1, 0}, {1, 0}, {1, 1}, {0, 1}};
//private final static int [][] Y = {{1, 0}, {1, 1}, {1, 0}, {1, 0}};
//private final static int [][] L5 = {{1, 0}, {1, 0}, {1, 0}, {1, 1}};
//private final static int [][] Z5 = {{1, 1, 0}, {0, 1, 0}, {0, 1, 1}};
//private final static int [][] L4 = {{1, 0}, {1, 0}, {1, 1}};
//private final static int [][] T4 = {{1, 1, 1}, {0, 1, 0}};
//private final static int [][] O4 = {{1, 1}, {1, 1}};