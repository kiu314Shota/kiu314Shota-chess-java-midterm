package main.java.chess.view;

import main.java.chess.model.Square;
import main.java.chess.model.pieces.Piece;
import javax.swing.*;
import java.awt.*;

/**
 * SquarePanel is responsible for rendering a chess square.
 * It wraps a model Square and draws its background and piece image.
 */

public class SquarePanel extends JPanel {
    private final Square squareData;
    private boolean displayPiece = true;

    public SquarePanel(Square squareData) {
        this.squareData = squareData;
        setPreferredSize(new Dimension(64, 64));
        setOpaque(true);  // ensure the background is painted
    }

    /** Expose the underlying model Square. */
    public Square getSquareData() {
        return squareData;
    }

    /** Convenience: what piece (if any) is on this square? */
    public Piece getOccupyingPiece() {
        return squareData.getOccupyingPiece();
    }

    /** Show or hide the piece when repainting. */
    public void setDisplayPiece(boolean display) {
        this.displayPiece = display;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1) Paint the checkerboard background
        Color bg = (squareData.getColor() == 1)
                ? new Color(221, 192, 127)
                : new Color(101,  67,  33);
        g.setColor(bg);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 2) Paint the piece (if any) on top
        if (displayPiece) {
            Piece p = squareData.getOccupyingPiece();
            if (p != null) {
                Image img = p.getImage();
                if (img != null) {
                    g.drawImage(img,
                            0, 0,
                            getWidth(), getHeight(),
                            this);
                }
            }
        }
    }
}
