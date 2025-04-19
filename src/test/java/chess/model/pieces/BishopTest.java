package chess.model.pieces;

import chess.model.BoardState;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {
    private BoardState board;
    private Bishop bishop;

    @BeforeEach
    public void setup() {
        board = new BoardState();
        Square center = board.getSquareArray()[4][4];
        center.removePiece();
        bishop = new Bishop(1, center, "/images/wbishop.png");
        center.put(bishop);
    }

    @Test
    public void testBishopHasDiagonalMoves() {
        List<Square> moves = bishop.getLegalMoves(board);
        assertFalse(moves.isEmpty());
    }
}