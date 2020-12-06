import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Piece extends JPanel{
    
    //private final int [][] piece;
    
    private Color c;
    private String name;
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
        name = GamePieces.getName(pieceStructure);
        if (name == null) {
            System.out.println("name is null");
        }
        
        
    }
    
    public String getName() {
        return name;
    }
    
    public int[][] getStructure() {
        return Arrays.copyOf(pieceStructure, pieceStructure.length);
    }
    
    public Color getColor() {
        return new Color(this.c.getRGB());
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
    
    
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Piece) {
            Piece u = (Piece) o;
            //result = u.getName().equals(this.getName());
            result = u.getName().equals(this.getName()) && u.getColor().equals(this.getColor());
//            int rotateDegree = 0;
//            int [][] struct = u.getStructure();
//            while (!result && rotateDegree <= 360) {
//                result = Arrays.deepEquals(struct, this.getStructure())
//                        && u.getColor().equals(this.getColor());
//                struct = State.ccRotation(struct);
//                rotateDegree+=90;
//            }
            
        }
        return result;
    }

    @Override
    public int hashCode() {
        return this.getStructure().length * this.getStructure()[0].length;
    }
    
    @Override
    public String toString() {
        return name + " " + c.toString();
    }
    

    
    
    
}