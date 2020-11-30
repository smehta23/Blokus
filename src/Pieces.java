import java.awt.Graphics;
import java.util.*;

import javax.swing.JComponent;

public enum Pieces {
    
    I1(new int[][] {{1}}),
    I2(new int[][] {{1, 1}}),
    I3(new int[][] {{1, 1, 1}}),
    I4(new int[][] {{1, 1, 1, 1}}),
    I5(new int[][] {{1, 1, 1, 1, 1}}),
    T (new int[][] {{1, 1, 1}, {0, 1, 0}, {0, 1, 0}}),
    F (new int[][] {{0, 1, 1}, {1, 1, 0}, {0, 1, 0}}), 
    X (new int[][] {{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}),
    U (new int[][] {{1, 0, 1}, {1, 1, 1}}),
    V5 (new int[][] {{1, 0, 0}, {1, 0, 0}, {1, 1, 1}}),
    W (new int[][] {{1, 0, 0}, {1, 1, 0}, {0, 1, 1}}),
    V3 (new int[][] {{1, 0}, {1, 1}}),
    Z4 (new int[][] {{1, 1, 0}, {0, 1, 1}}),
    P (new int[][] {{1, 1}, {1, 1}, {1, 0}}),
    N (new int[][] {{1, 0}, {1, 0}, {1, 1}, {0, 1}}),
    Y (new int[][] {{1, 0}, {1, 1}, {1, 0}, {1, 0}}),
    L5 (new int[][] {{1, 0}, {1, 0}, {1, 0}, {1, 1}}),
    Z5 (new int[][] {{1, 1, 0}, {0, 1, 0}, {0, 1, 1}}),
    L4 (new int[][] {{1, 0}, {1, 0}, {1, 1}}),
    T4 (new int[][] {{1, 1, 1}, {0, 1, 0}}),
    O4 (new int[][] {{1, 1}, {1, 1}});
    
    private final int [][] piece;
    
    private Pieces (int [][] piece) {
        this.piece = piece;
    }
    
    public int[][] getStructure () {
        return piece;
    }
    
    
}
