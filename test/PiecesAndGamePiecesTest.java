import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PiecesAndGamePiecesTest {

    @BeforeEach
    public void doHardReset() {
        State.hardReset();
    }

    @Test
    public void testBackwardsT4CC180() {
        int[][] struct = { { 0, 1, 0 }, { 1, 1, 1 } };
        assertEquals(GamePieces.getName(struct), "T4");
    }

    @Test
    public void testBackwardsT4CC90() {
        int[][] struct = { { 1, 0 }, { 1, 1 }, { 1, 0 } };
        assertEquals(GamePieces.getName(struct), "T4");
    }

    @Test
    public void rotateCCTestOnT4() {
        int[][] struct = Piece.rotatedCC(GamePieces.T4.getStructure());
        assertEquals(GamePieces.getName(struct), "T4");
    }

    @Test
    public void removePieceFromSetUnrotated() {
        int[][] struct = { { 0, 1, 0 }, { 1, 1, 1 } };
        boolean removalSuccess = State.getPlayer(0).pieceMoved(new Piece(struct, 
                State.getPlayer(0).getColor()));
        assertTrue(removalSuccess);
    }

    @Test
    public void removePieceFromSetRotated() {
        int[][] struct2 = { { 1, 0 }, { 1, 1 }, { 1, 0 } };
        boolean removalSuccess = State.getPlayer(0).pieceMoved(new Piece(struct2, 
                State.getPlayer(0).getColor()));
        assertTrue(removalSuccess);
    }

}
