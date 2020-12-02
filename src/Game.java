import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game implements Runnable {

    public Game() {
        // TODO Auto-generated constructor stub
    }

    public void run() {
        JFrame frame = new JFrame("Blokus");

        // Create a panel to store the two components
        // and make this panel the contentPane of the frame
        
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        Board board = new Board ();
        panel.add(board);
        PlayerPieceSet pset = new PlayerPieceSet(Color.RED);
        panel.add(pset);
        JButton rotPiece = new JButton("Rotate Piece");
        rotPiece.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                State.rotatePiece();
                board.repaint();
            }
        });
        panel.add(rotPiece);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.getContentPane().setSize(500, 1000);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}