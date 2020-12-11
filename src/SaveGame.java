import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class SaveGame implements ActionListener {
    File file;
    BufferedWriter writer;
    Frame frame;
    
    public SaveGame(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setCurrentDirectory(null);

        java.util.Date date = new java.util.Date();
        String timeStamp = "" + (new Timestamp(date.getTime()));
        timeStamp = timeStamp.replace("/", "_");
        timeStamp = timeStamp.replace(".", "");
        timeStamp = timeStamp.replace(":", "");
        timeStamp = timeStamp.replace(" ", "_");

        fileChooser.setSelectedFile(new File("board_" + timeStamp + ".txt"));

        int x = fileChooser.showSaveDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writeGameBoard();
                Player[] players = State.getAllPlayers();
                for (int i = 0; i < players.length; i++) {
                    writePlayerPieceSet(players[i]);
                }
                writeCurrentPlayer();
                writeTurnNumber();
                writeGameStatus();
                writer.close();
            } catch (IOException excp) {
                JOptionPane.showMessageDialog(frame, "Error loading file.");
            }
        }
    }

    private void writeGameBoard() {
        try {
            Color[][] currentBoard = State.getBoardColors();
            for (int i = 0; i < currentBoard.length; i++) {
                for (int j = 0; j < currentBoard[i].length; j++) {
                    writer.write(Integer.toBinaryString(currentBoard[i][j].getRGB()));
                }
            }
            writer.newLine();

        } catch (IOException excp) {
            JOptionPane.showMessageDialog(frame, "Error fetching game board/writing to file.");
        }
    }

    private void writePlayerPieceSet(Player player) {
        try {
            Set<Piece> playerPieces = player.getPieces();
            for (Piece p : playerPieces) {
                writer.write(formatPieceName(p.getName()));
            }
            writer.write("\n");
        } catch (IOException excp) {
            JOptionPane.showMessageDialog(frame, "Error fetching game pieces/writing to file.");
        }
    }
    
    private void writeCurrentPlayer() {
        try {
            writer.write("" + Integer.toHexString(State.getCurrentPlayer().getColor().getRGB()));
            writer.write("\n");
        } catch (IOException excp) {
            JOptionPane.showMessageDialog(frame, "Error fetching current player/writing to file.");
        }

    }
    
    private void writeTurnNumber() {
        try {
            writer.write("" + Integer.toHexString(State.getTurnNumber()));
            writer.write("\n");
        } catch (IOException excp) {
            JOptionPane.showMessageDialog(frame, "Error fetching current turn number/writing to file.");
        }
    }
    
    private void writeGameStatus() {
        try {
            if (!State.getStatus().equals("")) {
                String gameStatus = State.getStatus();
                for (int i = 0; i < gameStatus.length(); i++) { 
                    if (Character.isDigit(gameStatus.charAt(i))) {
                        int playerNum = Integer.parseInt(gameStatus.charAt(i) + "");
                        writer.write("" + Integer.toBinaryString(playerNum));
                    }
                }
            }
            else {
                writer.write("-");
            }
            
        } catch (IOException excp) {
            JOptionPane.showMessageDialog(frame, "Error fetching game status/writing to file.");
        }
    }

    private String formatPieceName(String pieceName) {
        if (pieceName.length() == 2) {
            return pieceName;
        }
        return new String(pieceName + " ");
    }

    
}