package main.java.chess.view;

import main.java.chess.model.BoardState;
import main.java.chess.model.Square;
import main.java.chess.model.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {
    private final BoardState boardState;
    private final SquarePanel[][] squarePanels;
    private boolean whiteTurn;
    private Piece currPiece;
    private int currX;
    private int currY;

    public BoardPanel() {
        this.boardState = new BoardState();
        this.squarePanels = new SquarePanel[BoardState.SIZE][BoardState.SIZE];
        this.whiteTurn = boardState.isWhiteTurn();

        setLayout(new GridLayout(BoardState.SIZE, BoardState.SIZE, 0, 0));
        addMouseListener(this);
        addMouseMotionListener(this);

        // Create and add SquarePanels
        Square[][] squares = boardState.getSquareArray();
        for (int y = 0; y < BoardState.SIZE; y++) {
            for (int x = 0; x < BoardState.SIZE; x++) {
                SquarePanel sqPanel = new SquarePanel(squares[y][x]);
                squarePanels[y][x] = sqPanel;
                add(sqPanel);
            }
        }
        setPreferredSize(new Dimension(400, 400));
    }

    public Square[][] getSquareArray() {
        return boardState.getSquareArray();
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // We let each SquarePanel paint itself.
        // If dragging a piece, optionally show its image at (currX, currY).
        if (currPiece != null) {
            Image img = currPiece.getImage();
            if (img != null) {
                g.drawImage(img, currX, currY, 64, 64, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();
        Component comp = getComponentAt(new Point(currX, currY));
        if (comp instanceof SquarePanel) {
            SquarePanel sqPanel = (SquarePanel) comp;
            if (sqPanel.getOccupyingPiece() != null) {
                currPiece = sqPanel.getOccupyingPiece();
                // Optionally check if the piece color matches the turn, etc.
                sqPanel.setDisplayPiece(false);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Component comp = getComponentAt(new Point(x, y));

        if (comp instanceof SquarePanel && currPiece != null) {
            SquarePanel targetSqPanel = (SquarePanel) comp;
            // Retrieve possible legal moves
            List<Square> legalMoves = currPiece.getLegalMoves(boardState);
            Square targetSquare = targetSqPanel.getSquareData();
            if (legalMoves.contains(targetSquare)) {
                // Move piece in the model
                currPiece.move(targetSquare);
                // Toggle turn in model
                boardState.toggleTurn();
                whiteTurn = boardState.isWhiteTurn();
            }
        }
        // Return the piece to normal display
        if (currPiece != null) {
            // The original SquarePanel that had the piece should display it again
            // But in the simple approach, we re-render everything
        }
        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;
        repaint();
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
