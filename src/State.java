import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {

    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static LinkedList<Color[][]> boardHistory = new LinkedList<Color[][]>();
    private static Color[][] boardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private static Piece pieceToMove;
    private static Point pieceToMovePos;
    private static boolean togglePlacePiece = false;

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
        boardColors[row][col] = c;
    }

    public static void setBoardColors(Color[][] colors) {
        boardHistory.add(deepCopyOfBoard(boardColors));
        boardColors = colors;
    }

    public static void setPieceToMove(Piece piece) {
        pieceToMove = piece;

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

    public static void placePieceOnBoard(int y, int x) {
        pieceToMovePos = new Point(x, y);
        int[][] pieceStructure = pieceToMove.getStructure();
        // validating where the piece is initially placed; returning if piece would go
        // out of bounds
        if (pieceStructure.length + y - 1 >= BOARD_HEIGHT || pieceStructure[0].length + x - 1 >= BOARD_WIDTH) {
            System.out.println("Cannot place piece here: (" + y + ", " + x + ")");
            return;
        }
        
        Color [][] clonedBoardColors = deepCopyOfBoard(boardColors);
        boardHistory.add(clonedBoardColors);
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    State.setBoardColors(i, j, Color.RED);
                }
            }
        }
        
    }
    

    public static void moveLastPlacedPiece(int y, int x) {
        if (boardHistory.size() < 1) { return; }
        Color [][] prev = boardHistory.removeLast();
        State.setBoardColors(prev);
        State.placePieceOnBoard(y, x);
    }
    
    public static void rotatePiece() {
        int[][] pieceStructure = pieceToMove.getStructure();
        int[][] rotatedPieceStructure = new int[pieceStructure[0].length][pieceStructure.length];
        for (int i = 0; i < rotatedPieceStructure.length; i++) {
            for (int j = 0; j < rotatedPieceStructure[i].length; j++) {
                if (pieceStructure[j][i] == 1) {
                    rotatedPieceStructure[rotatedPieceStructure.length - i - 1][j] = 1;
                }
            }
        }
        pieceToMove = new Piece(rotatedPieceStructure, Color.RED);
        moveLastPlacedPiece(pieceToMovePos.y, pieceToMovePos.x);
    }

}
