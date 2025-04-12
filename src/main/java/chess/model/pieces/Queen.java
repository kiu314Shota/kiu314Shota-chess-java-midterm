package main.java.chess.model.pieces;

import main.java.chess.model.BoardState;
import main.java.chess.model.Square;

import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece {
    public Queen(int color, Square initSq, String imgFile) {
        super(color, initSq, imgFile);
    }

    @Override
    public List<Square> getLegalMoves(BoardState boardState) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = boardState.getSquareArray();
        int x = getPosition().getXNum();
        int y = getPosition().getYNum();
        int[] occups = getLinearOccupations(board, x, y);

        for (int i = occups[0]; i <= occups[1]; i++) {
            if (i != y) {
                legalMoves.add(board[i][x]);
            }
        }
        for (int i = occups[2]; i <= occups[3]; i++) {
            if (i != x) {
                legalMoves.add(board[y][i]);
            }
        }
        legalMoves.addAll(getDiagonalOccupations(board, x, y));
        return legalMoves;
    }
}
