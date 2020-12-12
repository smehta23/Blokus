import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Set;

import javax.swing.*;

@SuppressWarnings("serial")
public class PlayerPieceSet extends JComponent {

    private Player player;
    private Set<Piece> pieces;

    public PlayerPieceSet() {
        this.player = State.getCurrentPlayer();
        this.pieces = player.getPieces();
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);
    }

    @Override
    public void paintComponent(Graphics gc) {

        this.player = State.getCurrentPlayer();
        this.pieces = player.getPieces();

        this.removeAll();
        for (Piece piece : this.pieces) {
            piece.addMouseListener(new PieceSelectedListener(piece));
            this.add(piece);
        }
    }

    public void deselectAll() {
        for (Piece piece : pieces) {
            piece.toggleDeselected();
        }
    }

    private class PieceSelectedListener extends MouseAdapter {
        private Piece piece;

        public PieceSelectedListener(Piece piece) {
            this.piece = piece;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Piece selected.");
            for (Piece piece : pieces) {
                piece.toggleDeselected();
            }
            piece.toggleSelected();
            repaint();
            State.setPieceToMove(piece);
        }
    }

}
