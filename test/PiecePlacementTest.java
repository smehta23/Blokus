
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PiecePlacementTest {
    
    @BeforeEach
    public void doHardReset() {
        State.hardReset();
    }
    
    @Test
    public void testWhenPieceInBounds() {
        State.setPieceToMove(new Piece(
                GamePieces.T.getStructure(), Color.RED));
        assertTrue(State.inBounds(17, 17));
    }
    
    @Test
    public void testWhenPieceOutOfBounds() {
        State.setPieceToMove(new Piece(
                GamePieces.T.getStructure(), Color.RED));
        assertFalse(State.inBounds(0, 18));
    }
    
    @Test
    public void testFirstMoveAtCornerInvalidated() {
        State.setPieceToMove(new Piece(
                GamePieces.X, Color.RED));
        assertFalse(State.validateFirstMove(17, 17));
    }
    
    @Test
    public void testFirstMoveAtCornerValidated() {
        State.setPieceToMove(new Piece(
                GamePieces.U, Color.RED));
        assertTrue(State.validateFirstMove(18, 17));
    }
    
    @Test
    public void testCornerTouchFalse() {
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.printBoard(State.getBoardColors());
        State.setPieceToMove(new Piece(GamePieces.T,
                Color.RED));
        assertFalse(State.validateCornerTouch(14, 5));
    }
    
    @Test
    public void testCornerTouchTrue() {
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.printBoard(State.getBoardColors());
        State.setPieceToMove(new Piece(GamePieces.T,
                Color.RED));
        assertTrue(State.validateCornerTouch(14, 2));
    }
    
    @Test
    public void testNoEdgeTouch() {
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.printBoard(State.getBoardColors());
        State.setPieceToMove(new Piece(GamePieces.T,
                Color.RED));
        assertTrue(State.validateEdges(14, 2));
    }
    
    @Test
    public void testEdgeTouch() {
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.printBoard(State.getBoardColors());
        State.setPieceToMove(new Piece(GamePieces.T,
                Color.RED));
        assertFalse(State.validateEdges(15, 2));
    }
    
    @Test
    public void testNoOverlapFalse() {
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.printBoard(State.getBoardColors());
        State.setPieceToMove(new Piece(GamePieces.T,
                Color.RED));
        assertFalse(State.noOverlap(15, 1));
    }
    
    @Test
    public void testNoOverlapDifColorFalse() {
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.printBoard(State.getBoardColors());
        State.setPieceToMove(new Piece(GamePieces.T,
                Color.RED));
        assertFalse(State.noOverlap(16, 16));
    }
    
    @Test
    public void testNoOverlapTrue() {
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.nextTurn();
        State.printBoard(State.getBoardColors());
        State.setPieceToMove(new Piece(GamePieces.T,
                Color.RED));
        assertTrue(State.noOverlap(5, 5));
    }

}
