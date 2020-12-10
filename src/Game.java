import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

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
        
        JButton undo = new JButton("Undo Move");
        undo.addActionListener( new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                State.undo();
                board.repaint();
                pset.repaint();
                stateLabel.repaint();
                
            }
        });
        panel.add(undo);
        

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveAsItem = new JMenuItem("Save As..."); 
        //JMenuItem saveItem = new JMenuItem("Save..."); 
        JMenuItem openItem = new JMenuItem("Open"); 
        fileMenu.add(saveAsItem); 
        //fileMenu.add(saveItem); 
        fileMenu.add(openItem); 
        menuBar.add(fileMenu);
        saveAsItem.addActionListener(new SaveGame());
        openItem.addActionListener(new OpenGame(board, pset, stateLabel));
        frame.setJMenuBar(menuBar);

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