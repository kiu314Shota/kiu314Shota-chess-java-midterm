package chess.model;

import chess.model.pieces.Pawn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {

    @Test
    public void testPutAndRemovePiece() {
        Square square = new Square(0, 0, 1);
        Pawn pawn = new Pawn(1, square, "/images/wpawn.png");
        square.put(pawn);
        assertTrue(square.isOccupied());
        square.removePiece();
        assertFalse(square.isOccupied());
    }

    @Test
    public void testEquality() {
        Square s1 = new Square(2, 3, 1);
        Square s2 = new Square(2, 3, 0);
        assertEquals(s1, s2);
    }

    @Test
    public void testNotEqual() {
        Square s1 = new Square(1, 2, 1);
        Square s2 = new Square(1, 3, 0);
        assertNotEquals(s1, s2);
    }
}
