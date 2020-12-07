import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;

public class State {
    
    public static final Color RED = new Color (255, 0, 0);
    public static final Color BLUE = new Color (0, 0, 255);
    public static final Color GREEN = new Color (0, 204, 0);
    public static final Color YELLOW = new Color (255, 255, 45);
    public static final int NUM_OF_PLAYERS = 4;
    public static final int BOARD_WIDTH = 20;
    public static final int BOARD_HEIGHT = 20;
    public static final Color START_COLOR = Color.GRAY;
    public static final Color [] GAME_COLORS = {BLUE, GREEN, RED, YELLOW};
    
    private static LinkedList<Color[][]> boardHistory = new LinkedList<Color[][]>();
    public static Player [] players = {new Player(), new Player(), new Player(), new Player()};
    private static Color[][] boardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    public static Piece pieceToMove;
    public static Piece piecePrevMoved;
    public static Point pieceToMovePos;
    public static Player currentPlayer = players[0];
    public static int turnNumber = 0;
    public static String gameStatus = "";
    
    public static Player getCurrentPlayer() {
        return new Player(
                currentPlayer.getPieces(),
                currentPlayer.getName(),
                currentPlayer.getColor(),
                currentPlayer.getNumber()
            );
    }
    
    public static void nextTurn() {
        finishTurn();
        turnNumber++;
        System.out.println(turnNumber);
        currentPlayer = players[turnNumber % NUM_OF_PLAYERS];
    }
    
    private static void finishTurn() {
        boolean pieceSuccessfullyMoved = validateTurn();
        if (pieceToMove!=null && pieceSuccessfullyMoved) {
            currentPlayer.pieceMoved(pieceToMove); //remove the piece from the currentPlayer's set
        }
        boardHistory.add(deepCopyOfBoard(boardColors)); //save board state
        pieceToMove = null; //Reinitialize the pieceToMove to null
        updateGameStatus();
    }
    
    private static int findPlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(player)) {
                return i;
            }
        }
        return -1;
    }
    //looks at the next 4 players; if they all cannot make moves the game ends and winner is determined
    private static void updateGameStatus() {
        Player originalCurrentPlayer = State.getCurrentPlayer();
        boolean playerCanMove = false;
        for (int i = 0; i < 4 && !playerCanMove; i++) {
            playerCanMove = validateTurn();
            if (playerCanMove) {
                State.setBoardColors(boardHistory.getLast());
                currentPlayer = originalCurrentPlayer;
                return;
            }
            System.out.println("Player " + (i + 1)  + " cannot move");
            currentPlayer = players[(State.findPlayer(currentPlayer) + 1) % NUM_OF_PLAYERS];        
        }
        
        Player lowestScorePlayer = players[0];
        for (int i = 1; i < players.length; i++) {
            if (players[i].getScore() < lowestScorePlayer.getScore()) {
                lowestScorePlayer = players[i];
            }
        }
        gameStatus = "GAME OVER: " + lowestScorePlayer.getName() + " wins!";
    }
    
  //makes sure that a piece was placed on the board by the currentPlayer if it could be
    private static boolean validateTurn() {
        //if the player has more than 21-(1+turnNumber / NUM_OF_PLAYERS) this indicates
        //that they didn't place a piece down
        boolean pieceCanBeMoved = true;
        //if (currentPlayer.getPiecesSize() > 21 - (1 + turnNumber / NUM_OF_PLAYERS)) {
        if (deepEquals(boardColors, boardHistory.getLast())) {
            System.out.println("Piece wasn't moved by player.");
            pieceCanBeMoved = false;
            //go through each piece; see if it can be placed at every position on the grid, 
            //testing all 4 orientations 
            for (Piece p: currentPlayer.getPieces()) {
                for (int row = 0; row < BOARD_HEIGHT && !pieceCanBeMoved; row++) {
                    for (int col = 0; col < BOARD_WIDTH && !pieceCanBeMoved; col++) {
                        pieceToMove = new Piece(p.getStructure(), p.getColor());
                        int rotateDegree = 0;
                        while (!pieceCanBeMoved && rotateDegree <= 360) {
                            //automatically places the piece that can be moved if possible
                            pieceCanBeMoved = State.movePiece(row, col);
                            if (pieceCanBeMoved) { break; }
                            pieceToMove = new Piece (State.ccRotation(pieceToMove.getStructure()), pieceToMove.getColor());
                            rotateDegree+=90;
                        }
                    }
                }
            }
        }
        
        return pieceCanBeMoved;

    }

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

    public static void setPieceToMove(Piece piece) {

        if (!piece.equals(pieceToMove)) {
            if (pieceToMove!=null) {
                piecePrevMoved = new Piece(pieceToMove.getStructure(), pieceToMove.getColor());
                System.out.println("piecePrevMoved: " + piecePrevMoved);
            }
            
            pieceToMove = new Piece(piece.getStructure(), piece.getColor());
            System.out.println("pieceToMove: " + pieceToMove);
        }
    }
    
    public static void moveLastPlacedPiece(int y, int x) {
        if (boardHistory.size() < 1) { return; }
        Color [][] prev = boardHistory.getLast();
        State.setBoardColors(prev);
        State.movePiece(y, x);
    }
    
    public static void rotatePlacedPiece() {
        int[][] pieceStructure = pieceToMove.getStructure();
        piecePrevMoved = new Piece(pieceToMove.getStructure(), pieceToMove.getColor());
        pieceToMove = new Piece(ccRotation(pieceStructure), currentPlayer.getColor());
        moveLastPlacedPiece(pieceToMovePos.y, pieceToMovePos.x);
    }
    


    public static void placePieceOnBoard(int y, int x) {
        if (piecePrevMoved == null || !piecePrevMoved.getColor().equals(pieceToMove.getColor())) {
            //System.out.println(piecePrevMoved);
            piecePrevMoved = new Piece(pieceToMove.getStructure(), pieceToMove.getColor());
            boardHistory.add(deepCopyOfBoard(boardColors));
            State.movePiece(y,x);
        }
        else {
            State.moveLastPlacedPiece(y, x);
        }
    }
    

    
    public static int [][] ccRotation(int[][] pieceStructure) {
        int[][] rotatedPieceStructure = new int[pieceStructure[0].length][pieceStructure.length];
        for (int i = 0; i < rotatedPieceStructure.length; i++) {
            for (int j = 0; j < rotatedPieceStructure[i].length; j++) {
                if (pieceStructure[j][i] == 1) {
                    rotatedPieceStructure[rotatedPieceStructure.length - i - 1][j] = 1;
                }
            }
        }
        return rotatedPieceStructure;
    }
    
    
    
    private static boolean movePiece(int y, int x) {
        pieceToMovePos = new Point(x, y);
        int[][] pieceStructure = pieceToMove.getStructure();
        // validating where the piece is placed; returning if piece would go out of bounds of the array
        if (pieceStructure.length + y - 1 >= BOARD_HEIGHT || pieceStructure[0].length + x - 1 >= BOARD_WIDTH) {
            System.out.println("Cannot place piece here: (" + y + ", " + x + ")");
            return false;
        }
        
        //validating first move being at one of the board's corners
        boolean boardCornerTouch = false;
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if ((i == 0 && j == 0) || 
                            (i == BOARD_HEIGHT-1 && j == 0) || 
                            (i == 0 && j == BOARD_WIDTH-1 ) ||
                            (i == BOARD_HEIGHT-1 && j == BOARD_WIDTH-1)) {
                        boardCornerTouch = true;
                    }
                }
            }
        }
        if (!boardCornerTouch && currentPlayer.getPiecesSize() == 21) {
            System.out.println("The piece must be placed adjacent to a board corner.");
            return false;
        }
        
        
        //validating using Blokus rules--corners must be of same color except on first turn
        boolean cornerSameColorTouch = false;
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if (validateIndex (i-1, j-1) && boardColors[i-1][j-1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    } else if (validateIndex (i+1, j+1) && boardColors[i+1][j+1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    } else if (validateIndex (i+1, j-1) && boardColors[i+1][j-1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    } else if (validateIndex (i-1, j+1) && boardColors[i-1][j+1].equals(pieceToMove.getColor())) {
                        cornerSameColorTouch = true;
                    }
                }
            }
        }
        if (!cornerSameColorTouch && currentPlayer.getPiecesSize() < 21) {
            System.out.println("Piece not touching corner of another piece with the same color.");
            return false;
        }
        
        //validating using Blokus rules--no edge touch
        boolean edgeDifferentColorTouch = true;
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    if (validateIndex (i-1, j) && boardColors[i-1][j].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    } else if (validateIndex (i+1, j) && boardColors[i+1][j].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    } else if (validateIndex (i, j-1) && boardColors[i][j-1].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    } else if (validateIndex (i, j+1) && boardColors[i][j+1].equals(pieceToMove.getColor())) {
                        edgeDifferentColorTouch = false;
                    }
                }
            }
        }
        if (!edgeDifferentColorTouch) {
            System.out.println("Cannot move here. Would touch edge of same-colored piece.");
            return false;
        }
        
        
        //validating using Blokus rules--no overlap with pieces already on the board
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1 && !boardColors[i][j].equals(Color.GRAY)) {
                    System.out.println("Another piece is already here.");
                    return false;
                }
            }
        }
        
        
        //updating the board
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                //coloring in board if array index of array representing piece is 1
                if (pieceStructure[i - y][j - x] == 1) {
                    State.setBoardColors(i, j, currentPlayer.getColor());
                }
            }
        }
        
        return true;
    }
    
    
    
    
    private static Color[][] deepCopyOfBoard(Color [][] currentBoard){
        Color [][] clonedBoardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                clonedBoardColors[i][j] = new Color(currentBoard[i][j].getRGB());
            }
        }
        return clonedBoardColors;
    }
    
    private static boolean deepEquals(Color [][] board1, Color[][] board2) {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (!board1[i][j].equals(board2[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean validateIndex(int row, int col) {
        return row >= 0 && row < boardColors.length && col >= 0 && col<boardColors[0].length;
    }
    
    

}
