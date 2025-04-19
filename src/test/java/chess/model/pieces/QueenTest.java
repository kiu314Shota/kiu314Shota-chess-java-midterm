package chess.model.pieces;

import chess.model.BoardState;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {
    private BoardState board;
    private Queen queen;

    @BeforeEach
    public void setup() {
        board = new BoardState();
        Square center = board.getSquareArray()[4][4];
        center.removePiece();
        queen = new Queen(1, center, "/images/wqueen.png");
        center.put(queen);
    }

    @Test
    public void testQueenHasMoves() {
        List<Square> moves = queen.getLegalMoves(board);
        assertFalse(moves.isEmpty());
    }
}