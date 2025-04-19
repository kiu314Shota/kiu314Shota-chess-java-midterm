package chess.model.pieces;

import chess.model.BoardState;
import chess.model.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class KnightTest {
    private BoardState board;
    private Knight knight;

    @BeforeEach
    public void setup() {
        board = new BoardState();
        Square center = board.getSquareArray()[4][4];
        center.removePiece();
        knight = new Knight(1, center, "/images/wknight.png");
        center.put(knight);
    }

    @Test
    public void testKnightHasEightMoves() {
        BoardState board = new BoardState();
        Square[][] grid = board.getSquareArray();

        for (Square[] row : grid) {
            for (Square s : row) s.removePiece();
        }

        Knight knight = new Knight(1, grid[4][4], "/images/wknight.png");
        grid[4][4].put(knight);

        List<Square> moves = knight.getLegalMoves(board);
        assertEquals(8, moves.size());
    }

    @Test
    public void testKnightJumpsOverPieces() {
        for (Square[] row : board.getSquareArray()) {
            for (Square sq : row) {
                sq.removePiece();
            }
        }
        Square pos = board.getSquareArray()[0][1];
        knight.setPosition(pos);
        pos.put(knight);
        List<Square> moves = knight.getLegalMoves(board);
        assertFalse(moves.isEmpty());
    }
}