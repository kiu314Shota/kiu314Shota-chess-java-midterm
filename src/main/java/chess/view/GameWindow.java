package main.java.chess.view;

import main.java.chess.model.Clock;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameWindow implements TimerStarter {
    private final Clock blackClock, whiteClock;
    private JLabel         blackTimeLabel, whiteTimeLabel;
    private final BoardPanel boardPanel;
    private final JFrame     gameWindow;
    private Timer            timer;
    private boolean          timerStarted = false;

    public GameWindow(String blackName, String whiteName, int hh, int mm, int ss) {
        // 1) clocks
        blackClock = new Clock(hh, mm, ss);
        whiteClock = new Clock(hh, mm, ss);

        // 2) frame
        gameWindow = new JFrame("Chess");
        gameWindow.setLayout(new BorderLayout(10, 10));
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 3) optional icon
        URL iconUrl = getClass().getClassLoader().getResource("images/wp.png");
        if (iconUrl != null) {
            try {
                gameWindow.setIconImage(ImageIO.read(iconUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 4) top panel (Black)
        JPanel top = new JPanel(new BorderLayout());
        JLabel bName = new JLabel(blackName, SwingConstants.CENTER);
        bName.setForeground(Color.RED);
        blackTimeLabel = new JLabel(blackClock.getTime(), SwingConstants.CENTER);
        top.add(bName,           BorderLayout.NORTH);
        top.add(blackTimeLabel,  BorderLayout.SOUTH);

        // 5) bottom panel (White)
        JPanel bot = new JPanel(new BorderLayout());
        JLabel wName = new JLabel(whiteName, SwingConstants.CENTER);
        wName.setForeground(Color.BLUE);
        whiteTimeLabel = new JLabel(whiteClock.getTime(), SwingConstants.CENTER);
        bot.add(wName,           BorderLayout.NORTH);
        bot.add(whiteTimeLabel,  BorderLayout.SOUTH);

        // 6) center board
        boardPanel = new BoardPanel(this);

        // 7) east buttons
        JPanel buttons = new JPanel(new GridLayout(3,1,5,5));
        JButton instr = new JButton("Instructions");
        instr.addActionListener(e ->
                JOptionPane.showMessageDialog(gameWindow,
                        "Drag pieces to move them.\nTimer starts on your first valid move.",
                        "Instructions",
                        JOptionPane.PLAIN_MESSAGE)
        );
        JButton newG = new JButton("New Game");
        newG.addActionListener(e -> {
            SwingUtilities.invokeLater(new StartMenu());
            gameWindow.dispose();
        });
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> {
            if (timer != null) timer.stop();
            gameWindow.dispose();
        });
        buttons.add(instr);
        buttons.add(newG);
        buttons.add(quit);

        // 8) assemble
        gameWindow.add(top,         BorderLayout.NORTH);
        gameWindow.add(bot,         BorderLayout.SOUTH);
        gameWindow.add(boardPanel,  BorderLayout.CENTER);
        gameWindow.add(buttons,     BorderLayout.EAST);

        gameWindow.pack();
        gameWindow.setResizable(false);
        gameWindow.setVisible(true);
    }

    /**
     * Called by BoardPanel on the first legal move.
     */
    @Override
    public void startTimerIfNotStarted() {
        if (timerStarted) return;
        timerStarted = true;

        timer = new Timer(1000, e -> {
            // decrement the correct clock
            if (boardPanel.isWhiteTurn()) whiteClock.decr();
            else                            blackClock.decr();

            // refresh labels
            whiteTimeLabel.setText(whiteClock.getTime());
            blackTimeLabel.setText(blackClock.getTime());

            // end on time‐out
            if (whiteClock.outOfTime() || blackClock.outOfTime()) {
                timer.stop();
                JOptionPane.showMessageDialog(gameWindow, "Time's up!");
                gameWindow.dispose();
            }
        });
        timer.start();
    }

    /**
     * Called by BoardPanel when a checkmate is detected.
     * @param winnerColor 1=white, 0=black
     */
    @Override
    public void gameOver(int winnerColor) {
        if (timer != null) timer.stop();
        String msg = (winnerColor == 1)
                ? "White wins by checkmate!"
                : "Black wins by checkmate!";
        int ans = JOptionPane.showConfirmDialog(
                gameWindow,
                msg + "  Play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION
        );
        if (ans == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(new StartMenu());
        }
        gameWindow.dispose();
    }
}
