package chess.model.pieces;

import chess.model.BoardState;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
    private BoardState board;
    private King king;

    @BeforeEach
    public void setup() {
        board = new BoardState();
        Square center = board.getSquareArray()[4][4];
        center.removePiece();
        king = new King(1, center, "/images/wking.png");
        center.put(king);
    }

    @Test
    public void testKingHasEightMoves() {
        List<Square> moves = king.getLegalMoves(board);
        assertEquals(8, moves.size());
    }

    @Test
    public void testKingCannotCaptureSameColor() {
        Square sq = board.getSquareArray()[3][3];
        sq.setOccupyingPiece(new Pawn(1, sq, ""));
        List<Square> moves = king.getLegalMoves(board);
        assertFalse(moves.contains(sq));
    }
}