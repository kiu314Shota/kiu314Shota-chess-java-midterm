package main.java.chess.model;

import main.java.chess.model.pieces.King;
import main.java.chess.model.pieces.Piece;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CheckmateDetector {
    private BoardState boardState;
    private LinkedList<Piece> whitePieces;
    private LinkedList<Piece> blackPieces;
    private LinkedList<Square> movableSquares;
    private final LinkedList<Square> squares;
    private King blackKing;
    private King whiteKing;
    private HashMap<Square, List<Piece>> whiteMoves;
    private HashMap<Square, List<Piece>> blackMoves;

    public CheckmateDetector(BoardState boardState, LinkedList<Piece> whitePieces,
                             LinkedList<Piece> blackPieces, King whiteKing, King blackKing) {
        this.boardState = boardState;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.whiteKing = whiteKing;
        this.blackKing = blackKing;
        squares = new LinkedList<>();
        movableSquares = new LinkedList<>();
        whiteMoves = new HashMap<>();
        blackMoves = new HashMap<>();

        Square[][] brd = boardState.getSquareArray();
        for (int y = 0; y < BoardState.SIZE; y++) {
            for (int x = 0; x < BoardState.SIZE; x++) {
                Square sq = brd[y][x];
                squares.add(sq);
                whiteMoves.put(sq, new LinkedList<>());
                blackMoves.put(sq, new LinkedList<>());
            }
        }
        update();
    }

    public void update() {
        for (List<Piece> list : whiteMoves.values()) {
            list.clear();
        }
        for (List<Piece> list : blackMoves.values()) {
            list.clear();
        }
        movableSquares.clear();

        for (Iterator<Piece> it = whitePieces.iterator(); it.hasNext();) {
            Piece p = it.next();
            if (!(p instanceof King)) {
                if (p.getPosition() == null) {
                    it.remove();
                    continue;
                }
                for (Square sq : p.getLegalMoves(boardState)) {
                    whiteMoves.get(sq).add(p);
                }
            }
        }
        for (Iterator<Piece> it = blackPieces.iterator(); it.hasNext();) {
            Piece p = it.next();
            if (!(p instanceof King)) {
                if (p.getPosition() == null) {
                    it.remove();
                    continue;
                }
                for (Square sq : p.getLegalMoves(boardState)) {
                    blackMoves.get(sq).add(p);
                }
            }
        }
    }

    public boolean blackInCheck() {
        update();
        Square kingSq = blackKing.getPosition();
        if (whiteMoves.get(kingSq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        }
        return true;
    }

    public boolean whiteInCheck() {
        update();
        Square kingSq = whiteKing.getPosition();
        if (blackMoves.get(kingSq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        }
        return true;
    }

    public boolean blackCheckMated() {
        if (!blackInCheck()) return false;
        boolean checkmate = true;
        if (canEvade(whiteMoves, blackKing)) checkmate = false;
        List<Piece> threats = whiteMoves.get(blackKing.getPosition());
        if (canCapture(blackMoves, threats, blackKing)) checkmate = false;
        if (canBlock(threats, blackMoves, blackKing)) checkmate = false;
        return checkmate;
    }

    public boolean whiteCheckMated() {
        if (!whiteInCheck()) return false;
        boolean checkmate = true;
        if (canEvade(blackMoves, whiteKing)) checkmate = false;
        List<Piece> threats = blackMoves.get(whiteKing.getPosition());
        if (canCapture(whiteMoves, threats, whiteKing)) checkmate = false;
        if (canBlock(threats, whiteMoves, whiteKing)) checkmate = false;
        return checkmate;
    }

    private boolean canEvade(Map<Square, List<Piece>> movesMap, King king) {
        boolean evade = false;
        List<Square> moves = king.getLegalMoves(boardState);
        for (Square sq : moves) {
            if (!testMove(king, sq)) continue;
            if (movesMap.get(sq).isEmpty()) {
                movableSquares.add(sq);
                evade = true;
            }
        }
        return evade;
    }

    private boolean canCapture(Map<Square, List<Piece>> poss, List<Piece> threats, King king) {
        boolean capture = false;
        if (threats.size() == 1) {
            Square threatSq = threats.get(0).getPosition();
            if (king.getLegalMoves(boardState).contains(threatSq)) {
                movableSquares.add(threatSq);
                if (testMove(king, threatSq)) {
                    capture = true;
                }
            }
            List<Piece> caps = poss.get(threatSq);
            for (Piece p : new ConcurrentLinkedDeque<>(caps)) {
                if (testMove(p, threatSq)) {
                    movableSquares.add(threatSq);
                    capture = true;
                }
            }
        }
        return capture;
    }

    private boolean canBlock(List<Piece> threats, Map<Square, List<Piece>> blockMoves, King king) {
        boolean blockable = false;
        if (threats.size() != 1) return false;
        Square threatSq = threats.get(0).getPosition();
        Square kingSq = king.getPosition();
        Square[][] brd = boardState.getSquareArray();

        if (kingSq.getXNum() == threatSq.getXNum()) {
            int minY = Math.min(kingSq.getYNum(), threatSq.getYNum());
            int maxY = Math.max(kingSq.getYNum(), threatSq.getYNum());
            for (int y = minY + 1; y < maxY; y++) {
                Square sq = brd[y][kingSq.getXNum()];
                for (Piece p : new ConcurrentLinkedDeque<>(blockMoves.get(sq))) {
                    if (testMove(p, sq)) {
                        movableSquares.add(sq);
                        blockable = true;
                    }
                }
            }
        }

        if (kingSq.getYNum() == threatSq.getYNum()) {
            int minX = Math.min(kingSq.getXNum(), threatSq.getXNum());
            int maxX = Math.max(kingSq.getXNum(), threatSq.getXNum());
            for (int x = minX + 1; x < maxX; x++) {
                Square sq = brd[kingSq.getYNum()][x];
                for (Piece p : new ConcurrentLinkedDeque<>(blockMoves.get(sq))) {
                    if (testMove(p, sq)) {
                        movableSquares.add(sq);
                        blockable = true;
                    }
                }
            }
        }

        return blockable;
    }

    public List<Square> getAllowableSquares(boolean whiteTurn) {
        movableSquares.clear();
        if (whiteInCheck()) {
            whiteCheckMated();
        } else if (blackInCheck()) {
            blackCheckMated();
        }
        return movableSquares;
    }

    public boolean testMove(Piece p, Square sq) {
        Piece originalPiece = sq.getOccupyingPiece();
        boolean moveTest = true;
        Square origPos = p.getPosition();

        p.move(sq);
        update();
        if (p.getColor() == 0 && blackInCheck())
            moveTest = false;
        else if (p.getColor() == 1 && whiteInCheck())
            moveTest = false;

        p.move(origPos);
        sq.setOccupyingPiece(originalPiece);
        update();
        movableSquares.addAll(squares);
        return moveTest;
    }
}
