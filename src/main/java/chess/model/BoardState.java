package main.java.chess.model;

import main.java.chess.model.pieces.*;
import java.util.LinkedList;
import java.util.List;

public class BoardState {
    public static final int SIZE = 8;
    private Square[][] board;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private boolean whiteTurn;

    public BoardState() {
        board = new Square[SIZE][SIZE];
        whitePieces = new LinkedList<>();
        blackPieces = new LinkedList<>();
        whiteTurn = true;
        initializeSquares();
        initializePieces();
    }

    private void initializeSquares() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                int color = ((x + y) % 2 == 0) ? 1 : 0;
                board[y][x] = new Square(x, y, color);
            }
        }
    }

    private void initializePieces() {
        // Initializing Pawns
        for (int x = 0; x < SIZE; x++) {
            Pawn blackPawn = new Pawn(0, board[1][x], "/main/resources/images/bpawn.png");
            board[1][x].setOccupyingPiece(blackPawn);
            blackPieces.add(blackPawn);

            Pawn whitePawn = new Pawn(1, board[6][x], "/main/resources/images/wpawn.png");
            board[6][x].setOccupyingPiece(whitePawn);
            whitePieces.add(whitePawn);
        }
        // Row 0 (Black pieces)
        Rook blackRook1 = new Rook(0, board[0][0], "/main/resources/images/brook.png");
        board[0][0].setOccupyingPiece(blackRook1);
        blackPieces.add(blackRook1);

        Knight blackKnight1 = new Knight(0, board[0][1], "/main/resources/images/bknight.png");
        board[0][1].setOccupyingPiece(blackKnight1);
        blackPieces.add(blackKnight1);

        Bishop blackBishop1 = new Bishop(0, board[0][2], "/main/resources/images/bbishop.png");
        board[0][2].setOccupyingPiece(blackBishop1);
        blackPieces.add(blackBishop1);

        Queen blackQueen = new Queen(0, board[0][3], "/main/resources/images/bqueen.png");
        board[0][3].setOccupyingPiece(blackQueen);
        blackPieces.add(blackQueen);

        King blackKing = new King(0, board[0][4], "/main/resources/images/bking.png");
        board[0][4].setOccupyingPiece(blackKing);
        blackPieces.add(blackKing);

        Bishop blackBishop2 = new Bishop(0, board[0][5], "/main/resources/images/bbishop.png");
        board[0][5].setOccupyingPiece(blackBishop2);
        blackPieces.add(blackBishop2);

        Knight blackKnight2 = new Knight(0, board[0][6], "/main/resources/images/bknight.png");
        board[0][6].setOccupyingPiece(blackKnight2);
        blackPieces.add(blackKnight2);

        Rook blackRook2 = new Rook(0, board[0][7], "/main/resources/images/brook.png");
        board[0][7].setOccupyingPiece(blackRook2);
        blackPieces.add(blackRook2);

        // Row 7 (White pieces)
        Rook whiteRook1 = new Rook(1, board[7][0], "/main/resources/images/wrook.png");
        board[7][0].setOccupyingPiece(whiteRook1);
        whitePieces.add(whiteRook1);

        Knight whiteKnight1 = new Knight(1, board[7][1], "/main/resources/images/wknight.png");
        board[7][1].setOccupyingPiece(whiteKnight1);
        whitePieces.add(whiteKnight1);

        Bishop whiteBishop1 = new Bishop(1, board[7][2], "/main/resources/images/wbishop.png");
        board[7][2].setOccupyingPiece(whiteBishop1);
        whitePieces.add(whiteBishop1);

        Queen whiteQueen = new Queen(1, board[7][3], "/main/resources/images/wqueen.png");
        board[7][3].setOccupyingPiece(whiteQueen);
        whitePieces.add(whiteQueen);

        King whiteKing = new King(1, board[7][4], "/main/resources/images/wking.png");
        board[7][4].setOccupyingPiece(whiteKing);
        whitePieces.add(whiteKing);

        Bishop whiteBishop2 = new Bishop(1, board[7][5], "/main/resources/images/wbishop.png");
        board[7][5].setOccupyingPiece(whiteBishop2);
        whitePieces.add(whiteBishop2);

        Knight whiteKnight2 = new Knight(1, board[7][6], "/main/resources/images/wknight.png");
        board[7][6].setOccupyingPiece(whiteKnight2);
        whitePieces.add(whiteKnight2);

        Rook whiteRook2 = new Rook(1, board[7][7], "/main/resources/images/wrook.png");
        board[7][7].setOccupyingPiece(whiteRook2);
        whitePieces.add(whiteRook2);
    }

    public Square[][] getSquareArray() {
        return board;
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void toggleTurn() {
        whiteTurn = !whiteTurn;
    }
}