import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Piece extends JPanel {

    // private final int [][] piece;

    private Color c;
    private String name;
    private final Dimension pieceDimension = new Dimension(5, 5);
    private final Dimension squareDimension = new Dimension(10, 10);
    private int[][] pieceStructure;

    public Piece(int[][] pieceStructure, Color c) {
        this.pieceStructure = pieceStructure;
        this.c = c;
        int initX, initY;
        initX = initY = 0;
        //determines the length and width of the piece
        for (int i = 0; i < pieceStructure.length; i++) {
            initX = 0;
            for (int j = 0; j < pieceStructure[0].length; j++) {
                initX += squareDimension.width + 1;
            }
            initY += squareDimension.height + 1;
        }
        pieceDimension.height = initY;
        pieceDimension.width = initX;
        name = GamePieces.getName(pieceStructure);

    }
    
    public Piece(GamePieces gp, Color c) {
        this(gp.getStructure(), c);
    }

    public String getName() {
        return name;
    }

    public int[][] getStructure() {
        return Arrays.copyOf(pieceStructure, pieceStructure.length);
    }

    public int getNumOfSquares() {
        int count = 0;
        for (int i = 0; i < pieceStructure.length; i++) {
            for (int j = 0; j < pieceStructure[i].length; j++) {
                if (pieceStructure[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
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
            initY += squareDimension.height + 1;
        }
        pieceDimension.height = initY;
        pieceDimension.width = initX;
    }

    public void toggleSelected() {
        squareDimension.width = 15;
        squareDimension.height = 15;
        repaint();
    }

    public void toggleDeselected() {
        squareDimension.width = 10;
        squareDimension.height = 10;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(pieceDimension.width + 20, pieceDimension.height + 20);
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof Piece) {
            Piece u = (Piece) o;
            result = u.getName().equals(this.getName()) && u.getColor().equals(this.getColor());

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

    public static int[][] rotatedCC(int[][] pieceStructure) {
        int[][] rotatedPieceStructure = new int[pieceStructure[0].length][pieceStructure.length];
        for (int i = 0; i < rotatedPieceStructure.length; i++) {
            for (int j = 0; j < rotatedPieceStructure[i].length; j++) {
                if (pieceStructure[j][i] == 1) {
                    rotatedPieceStructure[rotatedPieceStructure.length - i - 1][j] = 1;
                }
            }
        }
        return rotatedPieceStructure;
    }

    public static Piece rotatedCC(Piece p) {
        int[][] rotatedPieceStructure = rotatedCC(p.getStructure());
        return new Piece(rotatedPieceStructure, p.getColor());
    }

    public static Piece copy(Piece p) {
        return new Piece(p.getStructure(), p.getColor());
    }

}