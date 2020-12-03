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
    
    private Player p;
    private Color c;

    public PlayerPieceSet() {
        this.p = State.getCurrentPlayer();
        this.c = this.p.getColor();
        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);
        for (Piece piece : this.p.getPieces()) {
            JButton pieceButton = new JButton();
            Piece pieceModel = new Piece(piece.getStructure(), c);
            pieceButton.add(pieceModel);
            pieceButton.addActionListener(new PieceButtonListener(piece));
            pieceButton.setPreferredSize(piece.getPreferredSize());
            this.add(pieceButton);
        }
    }
    

    
    @Override
    public void paintComponent(Graphics gc) {
        gc.setColor(c);
        if (State.getCurrentPlayer().equals(this.p)) {
            System.out.println("haflway repainting pset");
            return;
        }
        
//        FlowLayout fl = new FlowLayout();
//        this.setLayout(fl);
//        System.out.println(this.p.getPieces().size());
//        for (Piece piece : this.p.getPieces()) {
//            JButton pieceButton = new JButton();
//            Piece pieceModel = new Piece(piece.getStructure(), this.c);
//            pieceButton.add(pieceModel);
//            //pieceButton.addActionListener(new PieceButtonListener(piece));
//            pieceButton.setPreferredSize(piece.getPreferredSize());
//            this.add(pieceButton);
//        }
//        System.out.println("fULL repainting pset");
    }
    
    
    private class PieceButtonListener implements ActionListener {
        private Piece piece;
        public PieceButtonListener(Piece piece) {
            this.piece = piece;
        }
        public void actionPerformed (ActionEvent e) {
            State.setPieceToMove(piece);
            repaint();
        }
    }

}
