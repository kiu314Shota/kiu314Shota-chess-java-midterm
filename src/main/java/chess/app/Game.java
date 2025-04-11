package main.java.chess.app;

import main.java.chess.view.StartMenu;

import javax.swing.*;

public class Game implements Runnable {
    public void run() {
        SwingUtilities.invokeLater(new StartMenu());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
