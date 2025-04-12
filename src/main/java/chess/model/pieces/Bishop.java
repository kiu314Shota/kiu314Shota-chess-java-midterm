package main.java.chess.model.pieces;

import main.java.chess.model.BoardState;
import main.java.chess.model.Square;

import java.util.List;


public class Bishop extends Piece {
    public Bishop(int color, Square initSq, String imgFile) {
        super(color, initSq, imgFile);
    }

    @Override
    public List<Square> getLegalMoves(BoardState boardState) {
        Square[][] board = boardState.getSquareArray();
        int x = getPosition().getXNum();
        int y = getPosition().getYNum();
        return getDiagonalOccupations(board, x, y);
    }
}

