package chess.app;

import chess.view.StartMenu;

import javax.swing.*;

public class Game implements Runnable {
    @Override
    public void run() {
        SwingUtilities.invokeLater(new StartMenu());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
