package main.java.chess.view;

import main.java.chess.model.BoardState;
import main.java.chess.model.Square;
import main.java.chess.model.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BoardPanel extends JPanel
        implements MouseListener, MouseMotionListener {
    private static final int TILE = 64;

    private final BoardState model;
    private final TimerStarter timerStarter;

    // Drag state
    private Piece draggingPiece;
    private Square sourceSquare;
    private SquarePanel sourcePanel;
    private boolean dragging;
    private boolean ghostActive;
    private int dragX, dragY;

    public BoardPanel(TimerStarter starter) {
        this.model = new BoardState();
        this.timerStarter = starter;

        int N = BoardState.SIZE;
        setLayout(new GridLayout(N, N));
        setPreferredSize(new Dimension(N * TILE, N * TILE));

        // Build UI from model
        Square[][] board = model.getSquareArray();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                add(new SquarePanel(board[r][c]));
            }
        }

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    // Paint children first, then ghost on top
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (ghostActive && draggingPiece != null) {
            g.drawImage(draggingPiece.getImage(),
                    dragX - TILE/2,
                    dragY - TILE/2,
                    TILE, TILE,
                    null);
        }
    }

    private SquarePanel panelAt(int x, int y) {
        Component comp = getComponentAt(x, y);
        return (comp instanceof SquarePanel) ? (SquarePanel) comp : null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        SquarePanel sp = panelAt(e.getX(), e.getY());
        if (sp == null) return;
        Piece p = sp.getOccupyingPiece();
        if (p == null) return;
        // only pick up if it's your color
        if ((model.isWhiteTurn() && p.getColor() == 1) ||
                (!model.isWhiteTurn() && p.getColor() == 0)) {
            draggingPiece = p;
            sourceSquare  = sp.getSquareData();
            sourcePanel   = sp;
            ghostActive   = true;
            dragging      = true;
            dragX         = e.getX();
            dragY         = e.getY();
            // hide original piece in its panel
            sourcePanel.setDisplayPiece(false);
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!dragging) return;
        dragX = e.getX();
        dragY = e.getY();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragging) return;
        SquarePanel tp = panelAt(e.getX(), e.getY());
        boolean moved = false;

        if (tp != null) {
            Square targetSq = tp.getSquareData();

            // 1) Cancel if released back on source
            if (targetSq.equals(sourceSquare)) {
                sourcePanel.setDisplayPiece(true);
                moved = true; // handled
            } else {
                // 2) Check legal moves
                List<Square> legal = draggingPiece.getLegalMoves(model);
                if (legal.contains(targetSq)) {
                    // commit the move
                    sourceSquare.removePiece();
                    targetSq.put(draggingPiece);
                    tp.setDisplayPiece(true);
                    model.toggleTurn();
                    timerStarter.startTimerIfNotStarted();
                    moved = true;
                }
            }
        }

        if (!moved) {
            // illegal: snap back and shake
            sourceSquare.put(draggingPiece);
            sourcePanel.setDisplayPiece(true);
            shakeWindow();
        }

        // reset drag state
        draggingPiece = null;
        sourceSquare  = null;
        sourcePanel   = null;
        dragging      = false;
        ghostActive   = false;
        repaint();
    }

    private void shakeWindow() {
        Window w = SwingUtilities.getWindowAncestor(this);
        Point orig = w.getLocation();
        int dist = 10, times = 6;
        Timer shake = new Timer(50, null);
        shake.addActionListener(new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                int offset = (count % 2 == 0) ? dist : -dist;
                w.setLocation(orig.x + offset, orig.y);
                count++;
                if (count >= times) {
                    shake.stop();
                    w.setLocation(orig);
                }
            }
        });
        shake.start();
    }

    /** Expose whose turn it is for the timer. */
    public boolean isWhiteTurn() {
        return model.isWhiteTurn();
    }

    // unused stubs
    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e)  { }
    @Override public void mouseMoved(MouseEvent e)   { }
}