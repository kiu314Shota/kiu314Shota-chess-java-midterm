package chess.model.pieces;

import chess.model.BoardState;
import chess.model.Square;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTest {

    @Test
    public void testFirstMoveAllowsTwoSquares() {
        BoardState board = new BoardState();
        Pawn pawn = (Pawn) board.getSquareArray()[6][4].getOccupyingPiece();
        List<Square> moves = pawn.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquareArray()[5][4]));
        assertTrue(moves.contains(board.getSquareArray()[4][4]));
    }

    @Test
    public void testSecondMoveOnlyOneSquare() {
        BoardState board = new BoardState();
        Pawn pawn = (Pawn) board.getSquareArray()[6][4].getOccupyingPiece();
        pawn.move(board.getSquareArray()[5][4]);
        List<Square> moves = pawn.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquareArray()[4][4]));
        assertFalse(moves.contains(board.getSquareArray()[3][4]));
    }

    @Test
    public void testDiagonalCapture() {
        BoardState board = new BoardState();
        Pawn white = new Pawn(1, board.getSquareArray()[4][3], "/images/wpawn.png");
        board.getSquareArray()[4][3].setOccupyingPiece(white);

        Pawn black = new Pawn(0, board.getSquareArray()[3][4], "/images/bpawn.png");
        board.getSquareArray()[3][4].setOccupyingPiece(black);

        List<Square> moves = white.getLegalMoves(board);
        assertTrue(moves.contains(board.getSquareArray()[3][4]));
    }
}
