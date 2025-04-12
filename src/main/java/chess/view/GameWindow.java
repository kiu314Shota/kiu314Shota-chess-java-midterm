package main.java.chess.view;

import main.java.chess.model.Clock;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameWindow {
    private JFrame gameWindow;
    public Clock blackClock, whiteClock;
    private Timer timer;
    private BoardPanel boardPanel;

    public GameWindow(String blackName, String whiteName, int hh, int mm, int ss) {
        blackClock = new Clock(hh, mm, ss);
        whiteClock = new Clock(hh, mm, ss);

        gameWindow = new JFrame("Chess");
        try {
            Image icon = ImageIO.read(getClass().getResource("/main/resources/images/wp.png"));
            gameWindow.setIconImage(icon);
        } catch (Exception e) {
            System.err.println("wp.png not found");
        }
        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20,20));

        JPanel dataPanel = createDataPanel(blackName, whiteName, hh, mm, ss);
        gameWindow.add(dataPanel, BorderLayout.NORTH);

        boardPanel = new BoardPanel();
        gameWindow.add(boardPanel, BorderLayout.CENTER);

        JPanel btnPanel = createButtonPanel();
        gameWindow.add(btnPanel, BorderLayout.SOUTH);

        gameWindow.pack();
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameWindow.setVisible(true);

        if (hh != 0 || mm != 0 || ss != 0) {
            startTimer();
        }
    }

    private JPanel createDataPanel(String bn, String wn, int hh, int mm, int ss) {
        JPanel panel = new JPanel(new GridLayout(2,2));
        JLabel whiteLabel = new JLabel(wn, SwingConstants.CENTER);
        JLabel blackLabel = new JLabel(bn, SwingConstants.CENTER);
        JLabel whiteTime = new JLabel(whiteClock.getTime(), SwingConstants.CENTER);
        JLabel blackTime = new JLabel(blackClock.getTime(), SwingConstants.CENTER);
        panel.add(whiteLabel);
        panel.add(blackLabel);
        panel.add(whiteTime);
        panel.add(blackTime);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(1,3,10,0));
        JButton instruct = new JButton("Instructions");
        instruct.addActionListener(e ->
                JOptionPane.showMessageDialog(gameWindow,
                        "Drag pieces to move them.\nWin by checkmate or on time.",
                        "Instructions", JOptionPane.PLAIN_MESSAGE));
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(e -> {
            SwingUtilities.invokeLater(new StartMenu());
            gameWindow.dispose();
        });
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> {
            if (timer != null) timer.stop();
            gameWindow.dispose();
        });
        panel.add(instruct);
        panel.add(newGame);
        panel.add(quit);
        return panel;
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (boardPanel.getTurn()) {
                    whiteClock.decr();
                } else {
                    blackClock.decr();
                }
                if (whiteClock.outOfTime() || blackClock.outOfTime()) {
                    timer.stop();
                    JOptionPane.showMessageDialog(gameWindow, "Time's up!");
                    gameWindow.dispose();
                }
            }
        });
        timer.start();
    }
}
