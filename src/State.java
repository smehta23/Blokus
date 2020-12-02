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
        //boardHistory.add(Arrays.copyOf(boardColors, boardColors.length));
        boardColors = colors;
    }

    public static void setPieceToMove(Piece piece) {
        pieceToMove = piece;

    }

    public static void placePieceOnBoard(int y, int x) {
        int[][] pieceStructure = pieceToMove.getStructure();
        // validating where the piece is initially placed; returning if piece would go
        // out of bounds
        if (pieceStructure.length + y - 1 >= BOARD_HEIGHT || pieceStructure[0].length + x - 1 >= BOARD_WIDTH) {
            System.out.println("Cannot place piece here: (" + y + ", " + x + ")");
            System.out.println((pieceStructure.length + y) + ", " + (pieceStructure[0].length + x));
            return;
        }
        //deep copy of current boardColors
        Color [][] clonedBoardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                clonedBoardColors[i][j] = new Color(boardColors[i][j].getRGB());
            }
        }
        boardHistory.add(clonedBoardColors);
        System.out.println("Prev placed on linkedlist\nBOARD COLORS");
        printArray(boardHistory.getLast());
        for (int i = y; i < y + pieceStructure.length; i++) {
            for (int j = x; j < x + pieceStructure[0].length; j++) {
                if (pieceStructure[i - y][j - x] == 1) {
                    State.setBoardColors(i, j, Color.RED);
                }
            }
        }
        System.out.println("BOARD COLORS");
        printArray(boardHistory.getLast());
        
    }
    
    public static void printArray(Color [][] arr) {
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].equals(Color.RED)) {
                    System.out.print("R ");
                    cnt++;
                }
                else System.out.print("G ");
            }
            System.out.println();
            
        }
        System.out.println(cnt);
    }

    public static void moveLastPlacedPiece(int y, int x) {
        if (boardHistory.size() < 1) { 
            System.out.println("Nothing");
            return; 
        }
        System.out.println("LList Size: " + boardHistory.size());
        Color [][] prev = boardHistory.removeLast();
        printArray(prev);
        System.out.println("setting to previous");
        State.setBoardColors(prev);
        //State.setBoardToDefault();
        
        State.placePieceOnBoard(y, x);
    }

}
