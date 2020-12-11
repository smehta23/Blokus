import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class OpenGame implements ActionListener {
    File file;
    BufferedReader reader;
    Board board;
    PlayerPieceSet pset;
    StateLabel stateLabel;
    Frame frame;

    public OpenGame(Frame frame, Board board, PlayerPieceSet pset, StateLabel stateLabel) {
        this.board = board;
        this.pset = pset;
        this.stateLabel = stateLabel;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = 
                new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setCurrentDirectory(null);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);

        int x = fileChooser.showOpenDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            System.out.println("Opened file's path: " + 
                    fileChooser.getSelectedFile().getAbsolutePath());
            file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                reader = new BufferedReader(new FileReader(file));

                State.resetPieceToMove();
                State.resetStatus();

                if (!loadBoard()) {
                    throw new IOException();
                }
                reader.readLine();
                if (!loadPlayers()) {
                    throw new IOException();
                }
                if (!loadCurrentPlayer()) {
                    throw new IOException();
                }
                reader.readLine();
                if (!loadTurnNumber()) {
                    throw new IOException();
                }
                if (!loadGameStatus()) {
                    throw new IOException();
                }

                board.repaint();
                pset.repaint();
                stateLabel.repaint();

                reader.close();
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(frame, "File not found.");
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(frame, "Some/all file operations were unsuccessful.");
            }
        }

    }

    private boolean loadBoard() {
        try {
            Color[][] boardColors = new Color[State.BOARD_HEIGHT][State.BOARD_WIDTH];
            for (int i = 0; i < State.BOARD_HEIGHT; i++) {
                for (int j = 0; j < State.BOARD_WIDTH; j++) {
                    String s = readFixed(reader, 32);
                    int rgb = (int) Long.parseLong(s, 2);
                    boardColors[i][j] = new Color(rgb);
                }
            }
            State.resetGame(boardColors);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error creating board/reading from file.");
            return false;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error creating board; file likely modified.");
            return false;
        }
        return true;
    }

    private boolean loadPlayers() {
        try {
            for (int playerNum = 0; playerNum < State.NUM_OF_PLAYERS; playerNum++) {
                String playerPieces = reader.readLine();
                Player updatedPlayer = null;
                // reconstructing set of pieces
                Set<Piece> pieces = new HashSet<Piece>();
                for (int j = 0; j < playerPieces.length(); j += 2) {
                    String pieceName = playerPieces.charAt(j) + "" + playerPieces.charAt(j + 1);
                    // since some pieces' names 1 letter long while others 2
                    pieceName = pieceName.stripTrailing(); 
                    pieces.add(new Piece(GamePieces.valueOf(pieceName).getStructure(),
                            State.getPlayer(playerNum).getColor()));
                }
                updatedPlayer = State.getPlayer(playerNum);
                updatedPlayer.setPieces(pieces);
                State.setPlayer(updatedPlayer, playerNum);

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error making players/reading from file.");
            return false;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, "Error making players; values likely modified.");
            return false;
        }
        return true;
    }

    private boolean loadCurrentPlayer() {
        try {
            int currentColor = (int) Long.parseLong(readFixed(reader, 8), 16);
            Player[] players = State.getAllPlayers();
            for (int i = 0; i < players.length; i++) {
                if (players[i].getColor().equals(new Color(currentColor))) {
                    State.setCurrentPlayer(i);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error loading current player/reading from file.");
            return false;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, 
                    "Error loading current player; file was modified.");
            return false;
        }
        return true;
    }

    private boolean loadTurnNumber() {
        try {
            int turnNumber = (int) Long.parseLong(reader.readLine(), 16);
            State.setTurnNumber(turnNumber);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching turn number/reading from file.");
            return false;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching turn number: likely modified.");
            return false;
        }
        return true;
    }

    private boolean loadGameStatus() {
        try {
            LinkedList<Integer> winnerNums = new LinkedList<Integer>();
            String binary = reader.readLine();
            if (binary.equals("-")) {
                State.resetStatus();
                return true;
            }
            for (int i = 0; i < binary.length(); i += 2) {
                String binaryWinnerNum = binary.charAt(i) + "" + binary.charAt(i + 1);
                int playerNum = Integer.parseInt(binaryWinnerNum, 2);
                winnerNums.add(playerNum);
            }
            Integer[] copyOfWinnerNums = new Integer[winnerNums.size()];
            copyOfWinnerNums = winnerNums.toArray(copyOfWinnerNums);
            State.setStatus(copyOfWinnerNums);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching game status/reading from file.");
            return false;
        } catch (NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, 
                    "Error fetching game status; file likely modified.");
            return false;
        }
        return true;
    }

    private String readFixed(Reader reader, int length) throws IOException {
        char[] chars = new char[length];
        int offset = 0;

        while (offset < length) {
            int charsRead = reader.read(chars, offset, length - offset);
            if (charsRead <= 0) {
                throw new IOException();
            }
            offset += charsRead;
        }
        return new String(chars);

    }

}
