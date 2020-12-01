import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {

    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static List<Color[][]> boardHistory = new LinkedList<Color[][]> ();
    private static Color [][] boardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    
    public static void setBoardToDefault() {
        for (int i = 0; i < boardColors.length; i++) {
            for (int j = 0; j < boardColors[0].length; j++) {
                boardColors[i][j] = Color.GRAY;
            }
        }
        boardHistory.add(Arrays.copyOf(boardColors, boardColors.length));
    }
    
    public static Color [][] getBoardColors() {
        return boardColors;
    }
    

    public static void setBoardColors(int row, int col, Color c){
        boardColors[row][col] = c;
    }
    
    public static void setBoardColors(Color [][] colors){
        boardColors = colors;
        boardHistory.add(Arrays.copyOf(boardColors, boardColors.length));
    }
    
}
