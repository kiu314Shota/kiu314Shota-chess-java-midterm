package main.java.chess.model.pieces;

import main.java.chess.model.Square;
import main.java.chess.view.BoardPanel;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    //Board b
    @Override
    public List<Square> getLegalMoves(BoardPanel b) {
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        //return getDiagonalOccupations(board, x, y);
        return null;
    }
}
