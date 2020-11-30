import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class PlayerPieceSet extends JComponent{
    
    private final int width = 2;
    private final int height = 11;
    private Color c;

    public PlayerPieceSet(Color c) {
        this.c = c;
        GridLayout layout = new GridLayout(height, width, 10, 10);
        this.setLayout(layout);
        GamePieces [] pieceStructures = GamePieces.class.getEnumConstants();
        for (GamePieces structure : pieceStructures) {
            this.add(new Piece(structure.getStructure(), c));
        }
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(c);
    }
    
    

}
