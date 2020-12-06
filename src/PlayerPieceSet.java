import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Set;

import javax.swing.*;

@SuppressWarnings("serial")
public class PlayerPieceSet extends JComponent{
    
    private Player player;
    public Color color;
    private Set<Piece> pieces;

    public PlayerPieceSet() {
        this.player = State.getCurrentPlayer();
        this.color = player.getColor();
        this.pieces = player.getPieces();
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);
//        for (Piece piece : this.pieces) {
//            this.add(piece);
//        }
    }
    

    
    @Override
    public void paintComponent(Graphics gc) {

        this.player = State.getCurrentPlayer();
        this.color = player.getColor();
        this.pieces = player.getPieces();
        //gc.setColor(this.color);

        this.removeAll();
//        FlowLayout fl = new FlowLayout();
//        this.setLayout(fl);
        for (Piece piece : this.pieces) {
            piece.addMouseListener(new PieceSelectedListener(piece));
            this.add(piece);
        }
    }
    
    
    private class PieceSelectedListener extends MouseAdapter {
        private Piece piece;
        public PieceSelectedListener(Piece piece) {
            this.piece = piece;
        }
        @Override
        public void mouseClicked (MouseEvent e) {
            System.out.println("Piece selected.");
            State.setPieceToMove(piece);
            //repaint();
        }
    }

}
