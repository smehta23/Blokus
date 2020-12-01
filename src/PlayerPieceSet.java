import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class PlayerPieceSet extends JComponent{
    
    private final int width = 2;
    private final int height = 11;
    private Color c;

    public PlayerPieceSet(Color c) {
        this.c = c;
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);
        GamePieces [] pieceStructures = GamePieces.class.getEnumConstants();
        for (GamePieces structure : pieceStructures) {
            JButton pieceButton = new JButton();
            Piece p = new Piece(structure.getStructure(), c);
            pieceButton.add(p);
            pieceButton.setPreferredSize(p.getPreferredSize());
            this.add(pieceButton);
        }
    }
    
    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(c);
    }
    
    

}
