import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Set;

import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

@SuppressWarnings("serial")
public class PlayerPieceSet extends JComponent{
    
    private Player player;
    private Color color;
    private Set<Piece> pieces;

    public PlayerPieceSet() {
        this.player = State.getCurrentPlayer();
        this.color = player.getColor();
        this.pieces = player.getPieces();
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);
        for (Piece piece : this.pieces) {
//            JButton pieceButton = new JButton();
//            Piece pieceModel = new Piece(piece.getStructure(), color);
//            pieceButton.add(pieceModel);
//            pieceButton.addActionListener(new PieceButtonListener(piece));
//            pieceButton.setPreferredSize(piece.getPreferredSize());
//            this.add(pieceButton);
            this.add(piece);
        }
    }
    

    
    @Override
    public void paintComponent(Graphics gc) {

        this.player = State.getCurrentPlayer();
        this.color = player.getColor();
        this.pieces = player.getPieces();
        gc.setColor(this.color);
        System.out.println(this.player.getName() + this.color);
//        if (State.getCurrentPlayer().equals(this.player)) {
//            System.out.println("haflway repainting pset");
//            return;
//        }
        this.removeAll();
        //this.setLayout(new FlowLayout());
        for (Piece piece : this.pieces) {
//            JButton pieceButton = new JButton();
//            Piece pieceModel = new Piece(piece.getStructure(), this.color);
//            pieceButton.add(pieceModel);
//            pieceButton.setPreferredSize(piece.getPreferredSize());
//            this.add(pieceButton);
//            pieceButton.addActionListener(new PieceButtonListener(piece));
            System.out.println("adding a piece");
            this.add(piece);
        }
    }
    
    
    private class PieceButtonListener implements ActionListener {
        private Piece piece;
        public PieceButtonListener(Piece piece) {
            this.piece = piece;
        }
        @Override
        public void actionPerformed (ActionEvent e) {
            State.setPieceToMove(piece);
            //repaint();
        }
    }

}
