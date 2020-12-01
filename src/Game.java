import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

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