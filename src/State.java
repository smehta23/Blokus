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
    private static Player [] players = {new Player(), new Player(), new Player(), new Player()};
    private static Color[][] boardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    public static Piece pieceToMove;
    public static Piece piecePrevMoved;
    public static Point pieceToMovePos;
    public static Player currentPlayer = players[0];
    public static int turnNumber = 0;
    
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
        if (pieceToMove!=null) {
            currentPlayer.pieceMoved(pieceToMove); //remove the piece from the currentPlayer's set
        }
        boardHistory.add(deepCopyOfBoard(boardColors)); //save board state
        pieceToMove = null; //Reinitialize the pieceToMove to null
        
    }

    public static void setBoardToDefault() {
        for (int i = 0; i < boardColors.length; i++) {
            for (int j = 0; j < boardColors[0].length; j++) {
                boardColors[i][j] = Color.GRAY;
            }
        }
    }

    public static Color[][] getBoardColors() {
        return boardColors;
    }

    public static void setBoardColors(int row, int col, Color c) {
        boardColors[row][col] = new Color(c.getRGB());
    }

    public static void setBoardColors(Color[][] bc) {
        //boardHistory.add(deepCopyOfBoard(boardColors));
        boardColors = deepCopyOfBoard(bc);
    }

    public static void setPieceToMove(Piece piece) {
        if (pieceToMove!=null && !pieceToMove.equals(piecePrevMoved)) {
            piecePrevMoved = new Piece(pieceToMove.getStructure(), pieceToMove.getColor());
        } else {
            piecePrevMoved = null;
        }
//        try {
//            System.out.println("piecePrevMoved " + piecePrevMoved.getColor());
//        } catch (NullPointerException e){
//            System.out.println("piecePrevMoved null");
//        }
        
        
        pieceToMove = piece;
//        try {
//            System.out.println("pieceToMove " + pieceToMove.getColor());
//        }
//        catch (NullPointerException e){
//            System.out.println("pieceToMove null");
//        }
    }
    
    public static void moveLastPlacedPiece(int y, int x) {
        if (boardHistory.size() < 1) { return; }
        Color [][] prev = boardHistory.removeLast();
        State.setBoardColors(prev);
        State.movePiece(y, x);
    }
    
    public static void rotatePlacedPiece() {
        int[][] pieceStructure = pieceToMove.getStructure();
        pieceToMove = new Piece(ccRotation(pieceStructure), currentPlayer.getColor());
        moveLastPlacedPiece(pieceToMovePos.y, pieceToMovePos.x);
    }
    


    public static void placePieceOnBoard(int y, int x) {
        if (piecePrevMoved == null || !piecePrevMoved.getColor().equals(pieceToMove.getColor())) {
            boardHistory.add(deepCopyOfBoard(boardColors));
            System.out.println("YEEEHAW");
            State.movePiece(y,x);
        }
        else {
            //boardHistory.add(deepCopyOfBoard(boardColors));
            System.out.println("noYEEEHAW");
            System.out.println(piecePrevMoved.getColor());
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
    
    private static void movePiece(int y, int x) {
        //boardHistory.add(deepCopyOfBoard(boardColors));
        pieceToMovePos = new Point(x, y);
        int[][] pieceStructure = pieceToMove.getStructure();
        // validating where the piece is initially placed; returning if piece would go
        // out of bounds of the array
        if (pieceStructure.length + y - 1 >= BOARD_HEIGHT || pieceStructure[0].length + x - 1 >= BOARD_WIDTH) {
            System.out.println("Cannot place piece here: (" + y + ", " + x + ")");
            return;
        }
        
        
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    State.setBoardColors(i, j, currentPlayer.getColor());
                }
            }
        }
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
    
    
    

}
