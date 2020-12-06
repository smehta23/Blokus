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
        
        StateLabel stateLabel = new StateLabel();
        panel.add(stateLabel);
        
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
                State.nextTurn();
                board.repaint();
                pset.repaint();
                stateLabel.repaint();
            }
        });
        panel.add(nextTurn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setSize(1500, 1000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}