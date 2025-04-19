package chess.model.pieces;

import chess.model.BoardState;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    private Piece dummy;
    private Square target;

    static class DummyPiece extends Piece {
        public DummyPiece(int color, Square sq, String imgFile) {
            super(color, sq, imgFile);
        }

        @Override
        public List<Square> getLegalMoves(BoardState boardState) {
            return java.util.List.of();
        }
    }

    @BeforeEach
    public void setup() {
        Square sq = new Square(4, 4, 1);
        dummy = new DummyPiece(1, sq, "/images/wpawn.png");
        sq.put(dummy);
        target = new Square(5, 5, 0);
    }

    @Test
    public void testMoveToEmptySquare() {
        assertTrue(dummy.move(target));
        assertEquals(target, dummy.getPosition());
        assertEquals(dummy, target.getOccupyingPiece());
    }

    @Test
    public void testMoveToSameColorOccupiedSquare() {
        Piece sameColor = new DummyPiece(1, target, "");
        target.put(sameColor);
        assertFalse(dummy.move(target));
    }

    @Test
    public void testMoveToDifferentColorOccupiedSquare() {
        Piece opponent = new DummyPiece(0, target, "");
        target.put(opponent);
        assertTrue(dummy.move(target));
        assertEquals(dummy, target.getOccupyingPiece());
    }
}
