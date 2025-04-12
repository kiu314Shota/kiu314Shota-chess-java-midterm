package main.java.chess.model;

import main.java.chess.model.pieces.Piece;

/**
 * Represents a single square on the board (pure data).
 */
public class Square {
    private final int xNum;
    private final int yNum;
    private final int color; // 1 = light, 0 = dark
    private Piece occupyingPiece;

    public Square(int xNum, int yNum, int color) {
        this.xNum = xNum;
        this.yNum = yNum;
        this.color = color;
    }

    public int getXNum() {
        return xNum;
    }

    public int getYNum() {
        return yNum;
    }

    public int getColor() {
        return color;
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public void setOccupyingPiece(Piece p) {
        occupyingPiece = p;
    }

    public boolean isOccupied() {
        return occupyingPiece != null;
    }

    @Override
    public int hashCode() {
        return 31 * (31 + xNum) + yNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Square)) return false;
        Square other = (Square) obj;
        return this.xNum == other.xNum && this.yNum == other.yNum;
    }
}
