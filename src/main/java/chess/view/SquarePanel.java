package main.java.chess.view;

import main.java.chess.model.Square;
import main.java.chess.model.pieces.Piece;

import javax.swing.*;
import java.awt.*;

public class SquarePanel extends JPanel {
    private final Square squareData;
    private boolean displayPiece;

    public SquarePanel(Square squareData) {
        this.squareData = squareData;
        this.displayPiece = true;
        setBorder(null);
    }

    public Square getSquareData() {
        return squareData;
    }

    public Piece getOccupyingPiece() {
        return squareData.getOccupyingPiece();
    }

    public void setDisplayPiece(boolean show) {
        displayPiece = show;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Paint the background color
        if (squareData.getColor() == 1) {
            g.setColor(new Color(221, 192, 127));
        } else {
            g.setColor(new Color(101, 67, 33));
        }
        g.fillRect(0, 0, getWidth(), getHeight());

        // If a piece is present and we are displaying it, draw the piece's image
        if (displayPiece && squareData.isOccupied()) {
            Piece occupant = squareData.getOccupyingPiece();
            Image img = occupant.getImage();
            if (img != null) {
                g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }
}
