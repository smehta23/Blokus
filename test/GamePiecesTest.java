import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePiecesTest {

    @Test
    public void testBackwardsT4CC180() {
        int [][] struct = 
            {{0, 1, 0}, {1, 1, 1}};
       assertEquals(GamePieces.getName(struct), "T4");
    }
    
    @Test
    public void testBackwardsT4CC90() {
        int [][] struct = 
            {{1, 0}, {1, 1}, {1, 0}};
       assertEquals(GamePieces.getName(struct), "T4");
    }
    
    @Test
    public void rotateTestT4() {
        int[][] struct = State.ccRotation(GamePieces.T4.getStructure());
        
        assertEquals(GamePieces.getName(struct), "T4");
    }
    
    @Test
    public void removePieceFromSet() {
        Player player1 = new Player();
        int [][] struct = 
            {{0, 1, 0}, {1, 1, 1}};
        int [][] struct2 = 
            {{1, 0}, {1, 1}, {1, 0}};
        boolean removalSuccess = player1.pieceMoved(new Piece(struct2, player1.getColor()));
        assertTrue(removalSuccess);
    }

}
