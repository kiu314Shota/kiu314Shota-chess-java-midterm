package chess.model;

import chess.model.pieces.King;
import chess.model.pieces.Queen;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckmateDetectorTest {

    @Test
    public void testBlackCheckmateScenario() {
        BoardState board = new BoardState();
        Square[][] sq = board.getSquareArray();

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                sq[y][x].removePiece();
            }
        }

        King blackKing = new King(0, sq[0][0], "/images/bking.png");
        sq[0][0].setOccupyingPiece(blackKing);

        Queen whiteQueen = new Queen(1, sq[1][1], "/images/wqueen.png");
        sq[1][1].setOccupyingPiece(whiteQueen);

        King whiteKing = new King(1, sq[2][2], "/images/wking.png");
        sq[2][2].setOccupyingPiece(whiteKing);

        chess.model.pieces.Rook whiteRook = new chess.model.pieces.Rook(1, sq[0][1], "/images/wrook.png");
        sq[0][1].setOccupyingPiece(whiteRook);

        LinkedList<chess.model.pieces.Piece> whitePieces = new LinkedList<>();
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);
        whitePieces.add(whiteRook);

        LinkedList<chess.model.pieces.Piece> blackPieces = new LinkedList<>();
        blackPieces.add(blackKing);

        CheckmateDetector detector = new CheckmateDetector(board, whitePieces, blackPieces, whiteKing, blackKing);
        assertTrue(detector.blackCheckMated());
    }
}
