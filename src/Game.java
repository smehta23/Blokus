import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game implements Runnable {

    public Game() {

//      State.createPlayer();
//      State.createPlayer();
//      State.createPlayer();
//      State.createPlayer();
    }

    public void run() {


        JFrame frame = new JFrame("Blokus");

        // Create a panel to store the two components
        // and make this panel the contentPane of the frame
        
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        
        JLabel currentPlayerLabel = new JLabel(State.getCurrentPlayer().getName());
        panel.add(currentPlayerLabel);
        
        Board board = new Board ();
        panel.add(board);
        
        PlayerPieceSet pset = new PlayerPieceSet();
        panel.add(pset);
        
        JButton rotPiece = new JButton("Rotate Piece");
        rotPiece.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                State.rotatePiece();
                board.repaint();
                
            }
        });
        panel.add(rotPiece);
        
        JButton nextTurn = new JButton("Finish Turn");
        nextTurn.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                State.nextTurn();
                currentPlayerLabel.setText(State.getCurrentPlayer().getName());
                board.repaint();
                pset.repaint();
            }
        });
        panel.add(nextTurn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setSize(new Dimension(1500, 1000));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}