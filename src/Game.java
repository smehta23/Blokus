import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game implements Runnable {

    public Game() {
    }

    public void run() {


        JFrame frame = new JFrame("Blokus");
        
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        
        JLabel currentPlayerLabel = new JLabel(
                State.getCurrentPlayer().getName() + "\n" 
                + State.currentPlayer.getPieces().size() + "\n" +
                        State.pieceToMove + "\n" + 
                        State.piecePrevMoved + "\n");
        panel.add(currentPlayerLabel);
        
        Board board = new Board ();
        panel.add(board);
        
        PlayerPieceSet pset = new PlayerPieceSet();
        panel.add(pset);
        
        JButton rotPiece = new JButton("Rotate Piece");
        rotPiece.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                State.rotatePlacedPiece();
                board.repaint();
                
            }
        });
        panel.add(rotPiece);
        
        JButton nextTurn = new JButton("Next Turn");
        nextTurn.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                
                currentPlayerLabel.setText(
                        State.getCurrentPlayer().getName() + "\n" 
                                + State.currentPlayer.getPieces().size() + "\n"
                                        );
                State.nextTurn();
                board.repaint();
                pset.repaint();
            }
        });
        panel.add(nextTurn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}