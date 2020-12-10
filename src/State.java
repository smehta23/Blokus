import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

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
    private static LinkedList<Piece> pieceHistory = new LinkedList<Piece>();
    private static Player[] players = { new Player(), new Player(), new Player(), new Player() };
    private static Color[][] boardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private static Piece pieceToMove;
    private static Piece piecePrevMoved;
    private static Point pieceToMovePos;
    private static Player currentPlayer = players[0];
    private static int turnNumber = 0;
    private static String gameStatus = "";
    private static boolean turn_finish = true;

    public static void undo() {
        if (boardHistory.size() > 1) {
//            if (turn_finish == true) {
//                boardHistory.removeLast();
//                turn_finish = false;
//            }
            boardHistory.removeLast();
            //boardHistory.removeLast();
            State.setBoardColors(deepCopyOfBoard(boardHistory.getLast()));
            printBoard(boardHistory.getLast());
            boardHistory.removeLast();
            
            Piece prevPiecePlaced = pieceHistory.getLast();
            System.out.println(prevPiecePlaced.getName());
            Player prevPlayer = State.getPlayer(prevPiecePlaced.getColor());
            prevPlayer.addPiece(prevPiecePlaced);
//            updatedPieces.add(prevPiecePlaced);
//            prevPlayer.setPieces(updatedPieces);
            int index = getPlayerNum(prevPlayer);
            players[index] = prevPlayer;
            pieceHistory.removeLast();
            currentPlayer = prevPlayer;
            boardHistory.add(deepCopyOfBoard(getBoardColors()));
            //updateGameStatus();
            
            turnNumber--;
        }
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
    
    public static Player getPlayer(Color c) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].getColor().equals(c)) {
                return copyPlayer(players[i]); 
            }
        }
        return null;
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
     * Increments the turnNumber and sets the currentPlayer to the next player that
     * occurs in the array of Players.
     */
    public static void nextTurn() {
        finishTurn();
        turnNumber++;
        System.out.println(turnNumber);
        currentPlayer = players[turnNumber % NUM_OF_PLAYERS];
    }

    /**
     * Removes the piece moved (manually or automatically) from currentPlayer's set
     * of pieces. Saves the state of the board to the boardHistory LinkedList, and
     * updates the game's status by calling updateGameStatus() to see if the game is
     * complete. Resets the pieceToMove to null.
     */
    private static void finishTurn() {
        boolean pieceSuccessfullyMoved = validateTurn();
        if (pieceToMove != null && pieceSuccessfullyMoved) {
            currentPlayer.pieceMoved(pieceToMove); // remove the piece from the currentPlayer's set
        }
        pieceHistory.add(pieceToMove);
        boardHistory.add(deepCopyOfBoard(boardColors)); // save board state
        updateGameStatus();
        pieceToMove = null; // Reinitialize the pieceToMove to null
        turn_finish = true;
    }

    /*
     * The pieces of the next 4 players (including the current) are inspected; if
     * none of the 4 players can make any moves, the game ends and the winner is
     * determined and the gameStatus string is updated.
     */
    public static void updateGameStatus() {
        //boardHistory.add(deepCopyOfBoard(boardColors));
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
     * Makes sure that a piece was placed on the board when the user requests the
     * next turn to be staged by determining whether the previous state of the board
     * is the same as the current. If a piece was not placed, then each of the
     * pieces of currentPlayer is tested to see if it can be placed on the board.
     * Every square on the board is tested and all 4 orientations of each piece are
     * also tested. If the algorithm finds that a piece can be placed on the board,
     * it automatically places it before the next player's turn.
     * 
     * @return whether or not the currentPlayer is able to move a piece if the
     *         currentPlayer did not make a move before clicking "Next Turn"
     */
    private static boolean validateTurn() {
        State.setBoardColors(boardHistory.getLast()); //returning to the board before preview
        if (pieceToMove!=null) { //player could have opted to not choose a piece at all
            movePiece(pieceToMovePos.y, pieceToMovePos.x);
        }
        boolean pieceCanBeMoved = true;
        if (boardsDeepEquals(boardColors, boardHistory.getLast())) {
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
    // Operations prior to placing a piece on the board
    // ==========================================================================

    /**
     * Sets pieceToMove to be a piece that the currentPlayer has selected. This
     * method is triggered every time the currentPlayer clicks on a piece other than
     * the one that is already chosen. If the previous piece selected was not null
     * (i.e. the previous pieceToMove), then it is stored in piecePrevMoved, and
     * pieceToMove is updated with the new piece selected.
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
     * Moves the last piece placed on the board (hence, pieceToMove is not
     * modified). Restores the previous state of the board (previous the particular
     * piece was even placed) and then uses the movePiece method to move the
     * particular piece to the desired location.
     * 
     * @param y : the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     */
    public static void moveLastPlacedPiece(int y, int x) {
        pieceToMovePos = new Point(x, y);
        if (boardHistory.size() < 1) {
            return;
        }
        Color[][] prev = boardHistory.getLast();
        State.setBoardColors(prev);
        State.previewPiece(y, x);
    }

    /**
     * Retrieves the current piece's structure represented as a 2D array and applies
     * a counterclockwise rotation to the array using method ccRotation. pieceToMove
     * is modified to be a new Piece (technically the same), but with its structure
     * the rotated 2D array. Then, moveLastPlacedPiece is called; the arguments are
     * the location at which piece (unrotated) was placed originally.
     */
    public static void rotatePlacedPiece() {
        // piecePrevMoved = new Piece(pieceToMove.getStructure(),
        // pieceToMove.getColor());
        pieceToMove = Piece.rotatedCC(pieceToMove);
        moveLastPlacedPiece(pieceToMovePos.y, pieceToMovePos.x);
    }

    /**
     * Places the pieceToMove on the board at the specified location.
     * 
     * If the previous piece moved (piecePrevMoved) was of a different color than
     * the (current) pieceToMove, then the piece is placed, keeping the last placed
     * piece on the board (as this indicates a new player is moving for the first
     * time). However, if the previous piece moved was of the same color, then given
     * the pieceToMove has been updated, moveLastPlacedPiece is called.
     * 
     * @param y : the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     */
    public static void placePieceOnBoard(int y, int x) {
        pieceToMovePos = new Point(x, y);
        if (piecePrevMoved == null || !piecePrevMoved.getColor().equals(pieceToMove.getColor())) {
            // System.out.println(piecePrevMoved);
            piecePrevMoved = Piece.copy(pieceToMove);
            // boardHistory.add(deepCopyOfBoard(boardColors));
            State.previewPiece(y, x);
        } else {
            State.moveLastPlacedPiece(y, x);
        }
    }
    
    public static void previewPiece(int y, int x) {
        int[][] pieceStructure = pieceToMove.getStructure();
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                // coloring in board if index of 2D (rectangular) array representing piece is 1
                if (pieceStructure[i - y][j - x] == 1) {
                    State.setBoardColors(i, j, currentPlayer.getColor().darker());
                }
            }
        }
    }

    // ==========================================================================
    // Operations to place a piece on the board
    // ==========================================================================
    
    
    /**
     * If the length of the piece plus the y position OR the 
     * width of the piece plus the x position is greater than the board height
     * or board width respectively, false is returned.
     * 
     * @param the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     * @return if the piece is placed in bounds with the top corner 
     *         of the piece being (y, x)
     */
    private static boolean inBounds(int y, int x) {
        int[][] pieceStructure = pieceToMove.getStructure();
        if (pieceStructure.length + y - 1 >= BOARD_HEIGHT || 
                pieceStructure[0].length + x - 1 >= BOARD_WIDTH) {
            System.out.println("Cannot place piece here: (" + y + ", " + x + ")");
            return false;
        }
        return true;
    }

    /**
     * Assesses whether the first piece placed by each player is touching 
     * one of the four corners of the board. Does this by iterating through the
     * piece's structure (2D array), and if none one of the piece's squares
     * are touching any of the four corners, then false is returned.
     * 
     * @param the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     * @return if the first move is valid given Blokus rules
     */
    private static boolean validateFirstMove(int y, int x) {
        if (currentPlayer.getPiecesSize() < 21) {
            return true;
        }
        int[][] pieceStructure = pieceToMove.getStructure();
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if ((i == 0 && j == 0) || (i == BOARD_HEIGHT - 1 && j == 0) || (i == 0 && j == BOARD_WIDTH - 1)
                            || (i == BOARD_HEIGHT - 1 && j == BOARD_WIDTH - 1)) {
                        return true;
                    }
                }
            }
        }

        System.out.println("The piece must be placed adjacent to a board corner.");
        return false;
    }

    /**
     * Determines whether the pieces placed by each player after their first 
     * moves are touching the corner of a piece already placed of the same
     * color. Iterates through each of the piece's squares' positions when the
     * top left corner of the piece is placed at (y, x); if, for any of 
     * those positions (squares) there is a color matching the piece's color at 
     * one of the position's four corners, then the move is valid and true is 
     * returned.
     * 
     * @param y : the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     * @return whether placing piece would touch the corner of another piece
     *         of the same color
     */
    private static boolean validateCornerTouch(int y, int x) {
        if (currentPlayer.getPieces().size() == 21) {
            return true;
        }
        int[][] pieceStructure = pieceToMove.getStructure();
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if (validateIndex(i - 1, j - 1) 
                            && boardColors[i - 1][j - 1].equals(pieceToMove.getColor())) {
                        return true;
                    } else if (validateIndex(i + 1, j + 1)
                            && boardColors[i + 1][j + 1].equals(pieceToMove.getColor())) {
                        return true;
                    } else if (validateIndex(i + 1, j - 1)
                            && boardColors[i + 1][j - 1].equals(pieceToMove.getColor())) {
                        return true;
                    } else if (validateIndex(i - 1, j + 1)
                            && boardColors[i - 1][j + 1].equals(pieceToMove.getColor())) {
                        return true;
                    }
                }
            }
        }
        System.out.println("Piece not touching corner of another piece with the same color.");
        return false;
    }
    
    /**
     * Determines whether the pieceToMove would touch the edge of another
     * square/piece on the board of the same color if placed at (y, x).
     * Iterates through each of the piece's squares' positions when the
     * top left corner of the piece is placed at (y, x); if, for any of 
     * those positions (squares) there is a color matching the piece's color at 
     * one of the position's four edges, then the move is invalid and false is 
     * returned.
     * 
     * @param y : the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     * @return whether placing piece would not touch the edge of another piece
     *         of the same color
     */
    private static boolean validateEdges(int y, int x) {
        int[][] pieceStructure = pieceToMove.getStructure();
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if ((validateIndex(i - 1, j) && boardColors[i - 1][j].equals(pieceToMove.getColor())) || 
                        (validateIndex(i + 1, j) && boardColors[i + 1][j].equals(pieceToMove.getColor())) || 
                        (validateIndex(i, j - 1) && boardColors[i][j - 1].equals(pieceToMove.getColor())) || 
                        (validateIndex(i, j + 1) && boardColors[i][j + 1].equals(pieceToMove.getColor()))) {
                        System.out.println("Cannot move here. Would touch edge of same-colored piece.");
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Iterates through each position for which a piece's square would be
     * if placed on the board, and if the position is of a color that is 
     * not gray, indicating a piece is already at that position, 
     * false is returned, indicating overlap.
     * 
     * @param y : the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     * @return whether placing the piece at (y, x) would cause the piece
     * to overlap with other pieces already on the board
     */
    private static boolean noOverlap(int y, int x) {
        int[][] pieceStructure = pieceToMove.getStructure();
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1 && !boardColors[i][j].equals(Color.GRAY)) {
                    System.out.println("Another piece is already here.");
                    return false;
                }
            }
        }
        return true;
    }
    

    /**
     * Using the methods above, determines if the piece can be moved. If it
     * can, then performs the move operation by iterating through the piece's
     * structure and copying the piece's color to the board (2D Color array, 
     * boardColors) at the designated locations.
     * 
     * @param y : the y-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_HEIGHT - 1)
     * @param x : the x-comp of where the previously placed piece should go
     *          (relative to board). Must be in the range (0, BOARD_WIDTH - 1)
     * @return if the piece moving operation was successful
     */
    private static boolean movePiece(int y, int x) {
        pieceToMovePos = new Point(x, y);
        int[][] pieceStructure = pieceToMove.getStructure();
        
        if (inBounds(y, x) && validateFirstMove(y, x) && validateCornerTouch (y, x) && 
                validateEdges(y, x) && noOverlap(y, x)) {
            for (int i = y; i < y + pieceStructure.length; i++) {
                for (int j = x; j < x + pieceStructure[0].length; j++) {
                    // coloring in board if index of 2D (rectangular) array representing piece is 1
                    if (pieceStructure[i - y][j - x] == 1) {
                        State.setBoardColors(i, j, currentPlayer.getColor());
                    }
                }
            }

            return true;
        }

        return false;
        
    }

    /*
     * general helper methods
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

    private static boolean boardsDeepEquals(Color[][] board1, Color[][] board2) {
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
