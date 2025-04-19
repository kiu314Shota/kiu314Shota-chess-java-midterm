package chess.model.pieces;

import chess.model.BoardState;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RookTest {
    private BoardState board;
    private Rook rook;

    @BeforeEach
    public void setup() {
        board = new BoardState();
        Square center = board.getSquareArray()[4][4];
        center.removePiece();
        rook = new Rook(1, center, "/images/wrook.png");
        center.put(rook);
    }

    @Test
    public void testRookHasValidLinearMoves() {
        List<Square> moves = rook.getLegalMoves(board);
        assertFalse(moves.isEmpty());
    }
}