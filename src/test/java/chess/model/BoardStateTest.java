package chess.model;

import chess.model.pieces.Queen;
import chess.model.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardStateTest {

    @Test
    public void testIsKingSafeAfterMoveAllowsCapture() {
        BoardState board = new BoardState();
        Square[][] squares = board.getSquareArray();

        Queen whiteQueen = new Queen(1, squares[5][5], "/images/wqueen.png");
        squares[5][5].put(whiteQueen);

        Rook blackRook = new Rook(0, squares[1][4], "/images/brook.png");
        squares[1][4].put(blackRook);

        squares[6][4].removePiece();
        squares[5][4].removePiece();

        boolean isSafe = board.isKingSafeAfterMove(whiteQueen, squares[1][4]);

        assertTrue(isSafe);
    }
}
