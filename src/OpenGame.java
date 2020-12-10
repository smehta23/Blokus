import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class OpenGame implements ActionListener {
    File file;
    BufferedReader reader;
    Board board;
    PlayerPieceSet pset;
    StateLabel stateLabel;

    public OpenGame(Board board, PlayerPieceSet pset, StateLabel stateLabel) {
        this.board = board;
        this.pset = pset;
        this.stateLabel = stateLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setCurrentDirectory(null);

        int x = fileChooser.showOpenDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
            file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                reader = new BufferedReader(new FileReader(file));

                loadBoard();
                // reads a line (since on save, a newline is added after the last 
                // index of the State color array; then, load player pieces
                reader.readLine();
                loadPlayers();
                loadCurrentPlayer();
                //reads a line since on save, a newline is added after currentPlayer color
                reader.readLine();
                loadTurnNumber();
                // check if modifications have been made that corrupted the game state
                checkForModifications();

                
                board.repaint();
                pset.repaint();
                

                State.updateGameStatus();
                stateLabel.repaint();
                
                
                reader.close();
            } catch (FileNotFoundException e1) {
                System.out.println("File not found.");
            } catch (IOException e2) {
                System.out.println("File operations unsuccessful.");
            }
        }

        

    }

    //to ascertain that file was not edited so that >4 colors are on the board
    private void checkForModifications() {
        // TODO Auto-generated method stub

    }

    private void loadBoard() {
        try {
            Color[][] boardColors = new Color[State.BOARD_HEIGHT][State.BOARD_WIDTH];
            for (int i = 0; i < State.BOARD_HEIGHT; i++) {
                for (int j = 0; j < State.BOARD_WIDTH; j++) {
                    String s = readExactly(reader, 32);
                    // int x = reader.read();
                    int RGB = (int) Long.parseLong(s, 2);
                    boardColors[i][j] = new Color(RGB);
                    System.out.println(RGB);
                }
            }
            State.setBoardColors(boardColors);
        } catch (IOException e) {
            System.out.println("Error creating board/reading from file.");
        }

    }

    private void loadPlayers() {
        try {
            for (int playerNum = 0; playerNum < State.NUM_OF_PLAYERS; playerNum++) {
                String playerPieces = reader.readLine();
                System.out.println(playerPieces);
                Player updatedPlayer = null;
                //reconstructing set of pieces
                Set<Piece> pieces = new HashSet<Piece>();
                for (int j = 0; j < playerPieces.length(); j+=2) {
                    String pieceName = playerPieces.charAt(j) + "" + playerPieces.charAt(j+1);
                    pieceName = pieceName.stripTrailing(); //since some pieces' names 1 letter long while others 2

//                    System.out.print(pieceName + ": ");
//                    System.out.println(GamePieces.valueOf(pieceName));
                    pieces.add(new Piece (GamePieces.valueOf(pieceName).getStructure(), 
                                    State.getPlayer(playerNum).getColor()));
                    
//                    updatedPlayer = State.getPlayer(playerNum);
//                    updatedPlayer.pieceMoved(GamePieces.valueOf(pieceName).getStructure());
                    
                }
                updatedPlayer = State.getPlayer(playerNum);
                updatedPlayer.setPieces(pieces);
                State.setPlayer(updatedPlayer, playerNum);
                System.out.println(pieces.size());

            }
        } catch (IOException e) {
            System.out.println("Error making players/reading from file.");
        }
    }
    
    private void loadCurrentPlayer() {
        try {
            int currentColor = (int)Long.parseLong(readExactly(reader, 8), 16);
            //System.out.println(currentColor);
            Player [] players = State.getAllPlayers();
            for (int i = 0; i < players.length; i++) {
                if (players[i].getColor().equals(new Color(currentColor))) {
                    State.setCurrentPlayer(players[i]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading current player/reading from file.");
        }
    }
    
    private void loadTurnNumber() {
        try {
            int turnNumber = (int)Long.parseLong(reader.readLine(), 16);
            System.out.println(turnNumber);
            State.setTurnNumber(turnNumber);
        } catch (IOException e) {
            System.out.println("Error fetching turn number/reading from file.");
        }
    }

    private String readExactly(Reader reader, int length) throws IOException {
        char[] chars = new char[length];
        int offset = 0;
        while (offset < length) {
            int charsRead = reader.read(chars, offset, length - offset);
            if (charsRead <= 0) {
                throw new IOException("Stream terminated early");
            }
            offset += charsRead;
        }
        return new String(chars);
    }

}
