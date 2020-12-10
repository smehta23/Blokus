import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;


public class State {

    public static final Color RED = new Color(255, 0, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color GREEN = new Color(0, 204, 0);
    public static final Color YELLOW = new Color(255, 255, 45);
    public static final int NUM_OF_PLAYERS = 4;
    public static final int BOARD_WIDTH = 20;
    public static final int BOARD_HEIGHT = 20;
    public static final Color START_COLOR = Color.GRAY;
    public static final Color[] GAME_COLORS = { BLUE, GREEN, RED, YELLOW };

    private static LinkedList<Color[][]> boardHistory = new LinkedList<Color[][]>();
    private static Player[] players = { new Player(), new Player(), new Player(), new Player() };
    private static Color[][] boardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private static Piece pieceToMove;
    private static Piece piecePrevMoved;
    private static Point pieceToMovePos;
    private static Player currentPlayer = players[0];
    private static int turnNumber = 0;
    private static String gameStatus = "";
    
    public static void undo() {
        State.setBoardColors(deepCopyOfBoard(boardHistory.getLast()));
        printBoard(boardHistory.getLast());
        boardHistory.removeLast();
        turnNumber--;
    }


    /*
     * mutators/accessors for player-related variables
     */
    public static Player getCurrentPlayer() {
        return copyPlayer(currentPlayer);
    }

    public static void setCurrentPlayer(Player p) {
        currentPlayer = copyPlayer(p);
    }

    public static Player getPlayer(int num) {
        return copyPlayer(players[num]);
    }

    public static void setPlayer(Player p, int num) {
        players[num] = copyPlayer(p);
    }
    
    private static int getPlayerNum(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(player)) {
                return i;
            }
        }
        return -1;
    }

    public static Player[] getAllPlayers() {
        Player[] copyOfPlayers = new Player[NUM_OF_PLAYERS];
        for (int i = 0; i < players.length; i++) {
            copyOfPlayers[i] = copyPlayer(players[i]);
        }
        return copyOfPlayers;
    }

    /*
     * mutators/accessors for board (main state)
     */

    public static void setBoardToDefault() {
        for (int i = 0; i < boardColors.length; i++) {
            for (int j = 0; j < boardColors[0].length; j++) {
                boardColors[i][j] = Color.GRAY;
            }
        }
        boardHistory.add(State.getBoardColors());
    }

    public static Color[][] getBoardColors() {
        return deepCopyOfBoard(boardColors);
    }

    public static void setBoardColors(int row, int col, Color c) {
        boardColors[row][col] = new Color(c.getRGB());
    }

    public static void setBoardColors(Color[][] bc) {
        boardColors = deepCopyOfBoard(bc);
    }
    
    /*
     * get the game status
     */
    public static String getStatus() {
        return gameStatus;
    }

    /*
     * mutator/accessors for turn number
     */
    public static int getTurnNumber() {
        return turnNumber;
    }

    public static void setTurnNumber(int n) {
        turnNumber = n;
    }
    
    // ==========================================================================
    // Next turn operations
    // ==========================================================================

    /**
     * Increments the turnNumber and sets the currentPlayer
     * to the next player that occurs in the array of Players.
     */
    public static void nextTurn() {
        finishTurn();
        turnNumber++;
        System.out.println(turnNumber);
        currentPlayer = players[turnNumber % NUM_OF_PLAYERS];
    }

    /**
     * Removes the piece moved (manually or automatically) from 
     * currentPlayer's set of pieces. Saves the state of the board to
     * the boardHistory LinkedList, and updates the game's status by 
     * calling updateGameStatus() to see if the game is complete.
     * Resets the pieceToMove to null.
     */
    private static void finishTurn() {
        boolean pieceSuccessfullyMoved = validateTurn();
        if (pieceToMove != null && pieceSuccessfullyMoved) {
            currentPlayer.pieceMoved(pieceToMove); // remove the piece from the currentPlayer's set
        }
        boardHistory.add(deepCopyOfBoard(boardColors)); // save board state
        updateGameStatus();
        pieceToMove = null; // Reinitialize the pieceToMove to null
    }




    /*
     * The pieces of the next 4 players (including the current) are inspected; 
     * if none of the 4 players can make any moves, the game ends and the 
     * winner is determined and the gameStatus string is updated. 
     */
    public static void updateGameStatus() {
        // boardHistory.add(deepCopyOfBoard(boardColors));
        gameStatus = "";
        Player originalCurrentPlayer = State.getCurrentPlayer();
        boolean playerCanMove = false;
        for (int i = 0; i < 4 && !playerCanMove; i++) {
            playerCanMove = validateTurn();
            if (playerCanMove) {
                State.setBoardColors(boardHistory.getLast());
                currentPlayer = originalCurrentPlayer;
                return;
            }
            System.out.println("Player " + (i + 1) + " cannot move");
            currentPlayer = players[(State.getPlayerNum(currentPlayer) + 1) % NUM_OF_PLAYERS];
        }

        Player lowestScorePlayer = players[0];
        for (int i = 1; i < players.length; i++) {
            if (players[i].getScore() < lowestScorePlayer.getScore()) {
                lowestScorePlayer = players[i];
            }
        }
        gameStatus = "GAME OVER: " + lowestScorePlayer.getName() + " wins!";
    }



    /**
     * Makes sure that a piece was placed on the board when the user requests
     * the next turn to be staged by determining whether the previous state of the 
     * board is the same as the current. If a piece was not placed, then 
     * each of the pieces of currentPlayer is tested to see if it can 
     * be placed on the board. Every square on the board is tested and all 
     * 4 orientations of each piece are also tested. If the algorithm finds 
     * that a piece can be placed on the board, it automatically places it before
     * the next player's turn.
     * 
     * @return whether or not the currentPlayer is able to move a piece
     * if the currentPlayer did not make a move before clicking "Next Turn" 
     */
    private static boolean validateTurn() {
        boolean pieceCanBeMoved = true;
        if (deepEquals(boardColors, boardHistory.getLast())) {
            System.out.println("Piece wasn't moved by player.");
            pieceCanBeMoved = false;
            for (Piece p : currentPlayer.getPiecesReverse()) {
                for (int row = 0; row < BOARD_HEIGHT && !pieceCanBeMoved; row++) {
                    for (int col = 0; col < BOARD_WIDTH && !pieceCanBeMoved; col++) {
                        pieceToMove = Piece.copy(p);
                        int rotateDegree = 0;
                        while (!pieceCanBeMoved && rotateDegree <= 360) {
                            // automatically places the piece that can be moved if possible
                            pieceCanBeMoved = State.movePiece(row, col);
                            if (pieceCanBeMoved) {
                                break;
                            }
                            pieceToMove = Piece.rotatedCC(pieceToMove);
                            rotateDegree += 90;
                        }
                    }
                }
            }
        }

        return pieceCanBeMoved;

    }
    
    
    // ==========================================================================
    // Operations to place a piece on the board
    // ==========================================================================

    /**
     * Sets pieceToMove to be a piece that the currentPlayer has selected.
     * This method is triggered every time the currentPlayer clicks on 
     * a piece other than the one that is already chosen.
     * If the previous piece selected was not null (i.e. the previous pieceToMove), 
     * then it is stored in piecePrevMoved, and pieceToMove is updated with 
     * the new piece selected.
     * 
     * @param piece : the piece that the currentPlayer would like to place
     */
    public static void setPieceToMove(Piece piece) {

        if (!piece.equals(pieceToMove)) {
            if (pieceToMove != null) {
                piecePrevMoved = Piece.copy(pieceToMove);
                System.out.println("piecePrevMoved: " + piecePrevMoved);
            }

            pieceToMove = Piece.copy(piece);
            System.out.println("pieceToMove: " + pieceToMove);
        }
    }

    /**
     * Moves the last piece placed on the board (hence, pieceToMove is not modified).
     * Restores the previous state of the board (previous the particular piece was 
     * even placed) and then uses the movePiece method to move the particular piece 
     * to the desired location.
     * 
     * @param y : the y-comp of where the previously placed piece should go 
     * (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go 
     * (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     */
    public static void moveLastPlacedPiece(int y, int x) {
        if (boardHistory.size() < 1) {
            return;
        }
        Color[][] prev = boardHistory.getLast();
        State.setBoardColors(prev);
        State.movePiece(y, x);
    }


    /**
     * Retrieves the current piece's structure represented as a 2D array and
     * applies a counterclockwise rotation to the array using method ccRotation.
     * pieceToMove is modified to be a new Piece (technically the same), but
     * with its structure the rotated 2D array. Then, moveLastPlacedPiece
     * is called; the arguments are the location at which piece (unrotated)
     * was placed originally.
     */
    public static void rotatePlacedPiece() {
        //piecePrevMoved = new Piece(pieceToMove.getStructure(), pieceToMove.getColor());
        pieceToMove = Piece.rotatedCC(pieceToMove);
        moveLastPlacedPiece(pieceToMovePos.y, pieceToMovePos.x);
    }

    /**
     * Places the pieceToMove on the board at the specified location. 
     * 
     * If the previous piece moved (piecePrevMoved) was of a different
     * color than the (current) pieceToMove, then the piece is placed,
     * keeping the last placed piece on the board (as this indicates a new 
     * player is moving for the first time). However, if the previous piece 
     * moved was of the same color, then given the pieceToMove has
     * been updated, moveLastPlacedPiece is called.
     * 
     * @param y : the y-comp of where the previously placed piece should go 
     * (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go 
     * (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     */
    public static void placePieceOnBoard(int y, int x) {
        if (piecePrevMoved == null || !piecePrevMoved.getColor().equals(pieceToMove.getColor())) {
            // System.out.println(piecePrevMoved);
            piecePrevMoved = Piece.copy(pieceToMove);
            //boardHistory.add(deepCopyOfBoard(boardColors));
            State.movePiece(y, x);
        } else {
            State.moveLastPlacedPiece(y, x);
        }
    }



    private static boolean movePiece(int y, int x) {
        pieceToMovePos = new Point(x, y);
        int[][] pieceStructure = pieceToMove.getStructure();
        // validating where the piece is placed; returning if piece would go out of
        // bounds of the array
        if (pieceStructure.length + y - 1 >= BOARD_HEIGHT || pieceStructure[0].length + x - 1 >= BOARD_WIDTH) {
            System.out.println("Cannot place piece here: (" + y + ", " + x + ")");
            return false;
        }
        // validating first move being at one of the board's corners
        boolean boardCornerTouch = false;
        for (int i = y; i < y + pieceStructure.length && !boardCornerTouch; i++) {
            for (int j = x; j < x + pieceStructure[0].length && !boardCornerTouch; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if ((i == 0 && j == 0) || (i == BOARD_HEIGHT - 1 && j == 0) || (i == 0 && j == BOARD_WIDTH - 1)
                            || (i == BOARD_HEIGHT - 1 && j == BOARD_WIDTH - 1)) {

                        boardCornerTouch = true;
                        break;
                    }
                }
            }
        }
        if (!boardCornerTouch && currentPlayer.getPiecesSize() == 21) {
            System.out.println("The piece must be placed adjacent to a board corner.");
            return false;
        }

        // validating using Blokus rules--corners must be of same color except on first
        // turn
        boolean cornerSameColorTouch = false;
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if (validateIndex(i - 1, j - 1) && boardColors[i - 1][j - 1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    } else if (validateIndex(i + 1, j + 1)
                            && boardColors[i + 1][j + 1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    } else if (validateIndex(i + 1, j - 1)
                            && boardColors[i + 1][j - 1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    } else if (validateIndex(i - 1, j + 1)
                            && boardColors[i - 1][j + 1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    }
                }
            }
        }
        if (!cornerSameColorTouch && currentPlayer.getPiecesSize() < 21) {
            System.out.println("Piece not touching corner of another piece with the same color.");
            return false;
        }

        // validating using Blokus rules--no edge touch
        boolean edgeDifferentColorTouch = true;
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if (validateIndex(i - 1, j) && boardColors[i - 1][j].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    } else if (validateIndex(i + 1, j) && boardColors[i + 1][j].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    } else if (validateIndex(i, j - 1) && boardColors[i][j - 1].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    } else if (validateIndex(i, j + 1) && boardColors[i][j + 1].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    }
                }
            }
        }
        if (!edgeDifferentColorTouch) {
            System.out.println("Cannot move here. Would touch edge of same-colored piece.");
            return false;
        }

        // validating using Blokus rules--no overlap with pieces already on the board
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1 && !boardColors[i][j].equals(Color.GRAY)) {
                    System.out.println("Another piece is already here.");
                    return false;
                }
            }
        }

        // updating the board
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                // coloring in board if array index of array representing piece is 1
                if (pieceStructure[i - y][j - x] == 1) {
                    State.setBoardColors(i, j, currentPlayer.getColor());
                }
            }
        }

        return true;
    }
    
    /*
     * helper methods 
     */

    private static Color[][] deepCopyOfBoard(Color[][] currentBoard) {
        Color[][] clonedBoardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                clonedBoardColors[i][j] = new Color(currentBoard[i][j].getRGB());
            }
        }
        return clonedBoardColors;
    }
    
    private static Player copyPlayer(Player p) {
        return new Player(p.getPieces(), p.getName(), p.getColor(), p.getNumber());
    }

    private static boolean deepEquals(Color[][] board1, Color[][] board2) {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (!board1[i][j].equals(board2[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void printBoard(Color[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].equals(RED)) {
                    System.out.print("R ");
                } else if (arr[i][j].equals(BLUE)) {
                    System.out.print("B ");
                } else if (arr[i][j].equals(GREEN)) {
                    System.out.print("G ");
                } else if (arr[i][j].equals(YELLOW)) {
                    System.out.print("Y ");
                } else
                    System.out.print("- ");
            }
            System.out.println();

        }
    }

    private static boolean validateIndex(int row, int col) {
        return row >= 0 && row < boardColors.length && col >= 0 && col < boardColors[0].length;
    }

}
