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
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveAsItem = new JMenuItem("Save As..."); 
        JMenuItem saveItem = new JMenuItem("Save..."); 
        JMenuItem openItem = new JMenuItem("Open"); 
        fileMenu.add(saveAsItem); 
        fileMenu.add(saveItem); 
        fileMenu.add(openItem); 
        menuBar.add(fileMenu);
        saveAsItem.addActionListener(new SaveAsListener());
        
        
        frame.setJMenuBar(menuBar);

        
        
        
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
    
    private class SaveAsListener implements ActionListener {
        File file;
        BufferedWriter writer;
        @Override
        public void actionPerformed (ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setCurrentDirectory(null);
            
            java.util.Date date= new java.util.Date();
            
            fileChooser.setSelectedFile(new File("board " + (new Timestamp(date.getTime())) + ".txt"));
            
            
            int x = fileChooser.showSaveDialog(null);
            if (x == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    writer = new BufferedWriter(new FileWriter(file));
                    writeGameBoard();
                    Player [] players = State.getAllPlayers();
                    for (int i = 0; i < players.length; i++) {
                        writePlayerPieceSet(players[i]);
                    }
                    writeCurrentPlayer();
                    writer.close();
                } catch (IOException excp) {
                    System.out.println("Error loading file.");
                }
            }
        }
        
        public void writeGameBoard() {
            try {
                Color [][] currentBoard = State.getBoardColors();
                for (int i = 0; i < currentBoard.length; i++) {
                    for (int j = 0; j < currentBoard[i].length; j++) {
                        writer.write(Integer.toBinaryString(currentBoard[i][j].getRGB()));
                    }
                }
                writer.newLine();
                
            } catch (IOException excp) {
                System.out.println("Error fetching game board/writing to file.");
            }
        }
        
        public void writePlayerPieceSet(Player player) {
            try {
                Set<Piece> playerPieces = player.getPieces();
                for (Piece p : playerPieces) {
                    writer.write(formatPieceName(p.getName()));
                }
                writer.write("\n");
            } catch (IOException excp) {
                System.out.println("Error fetching game pieces/writing to file.");
            }
        }
        
        private String formatPieceName(String pieceName) {
            if (pieceName.length() == 2) {
                return pieceName;
            }
            return pieceName + " ";
        }
        private void writeCurrentPlayer() {
            try {
                writer.write(""+Integer.toHexString(State.getCurrentPlayer().getColor().getRGB()));
            } catch (IOException excp) {
                System.out.println("Error fetching current player/writing to file.");
            }
            
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}