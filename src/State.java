import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {

    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static List<Color[][]> boardHistory = new LinkedList<Color[][]> ();
    private static Color [][] boardColors = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    
    public static Color [][] getBoardColors() {
        return Arrays.copyOf(boardColors, boardColors.length);
    }
    

    public static void setBoardColors(Color [][] colors){
        boardColors = colors;
        boardHistory.add(colors);
    }
    
}
