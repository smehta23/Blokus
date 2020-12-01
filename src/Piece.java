import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.TransferHandler;

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
        int initX, initY;
        initX = initY = 0;
        for (int i = 0; i < pieceStructure.length; i++) {
            initX = 0;
            for (int j = 0; j < pieceStructure[0].length; j++) {
                if (pieceStructure[i][j] == 1) {
                }
                initX += squareDimension.width + 1;
            }
            initY+= squareDimension.height + 1;
        }
        pieceDimension.height = initY;
        pieceDimension.width = initX;
        
        
    }
    
    public int[][] getStructure() {
        return Arrays.copyOf(pieceStructure, pieceStructure.length);
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
        return new Dimension(pieceDimension.width + 15, pieceDimension.height + 15);
    }
    
    
//    public int[][] getStructure () {
//        return piece;
//    }
    
    
}