import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {

    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static List<Color[][]> boardHistory = new LinkedList<Color[][]>();
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
        boardHistory.add(Arrays.copyOf(boardColors, boardColors.length));
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
        boardHistory.add(Arrays.copyOf(boardColors, boardColors.length));
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
        State.setBoardColors(((LinkedList<Color[][]>) boardHistory).removeLast());
        State.placePieceOnBoard(y, x);
    }

}
