import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

@SuppressWarnings("serial")
public class PlayerPieceSet extends JComponent{
    
    private Color c;

    public PlayerPieceSet(Color c) {
        this.c = c;
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);
        GamePieces [] pieceStructures = GamePieces.class.getEnumConstants();
        for (GamePieces structure : pieceStructures) {
            JButton pieceButton = new JButton();
            Piece piece = new Piece(structure.getStructure(), c);
            pieceButton.add(piece);
            pieceButton.addActionListener(new PieceButtonListener(piece));
            pieceButton.setPreferredSize(piece.getPreferredSize());
            this.add(pieceButton);
        }
        
    }
    

    
    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(c);
    }
    
    
    private class PieceButtonListener implements ActionListener {
        private Piece piece;
        public PieceButtonListener(Piece piece) {
            this.piece = piece;
        }
        public void actionPerformed (ActionEvent e) {
            State.setPieceToMove(piece);
        }
    }

}
