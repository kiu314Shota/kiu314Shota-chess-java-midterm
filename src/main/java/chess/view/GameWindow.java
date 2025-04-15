package main.java.chess.view;

import main.java.chess.model.Clock;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameWindow implements TimerStarter {
    private final Clock blackClock, whiteClock;
    private final JLabel blackTimeLabel, whiteTimeLabel;
    private final BoardPanel boardPanel;
    private Timer timer;
    private boolean timerStarted = false;

    public GameWindow(String blackName, String whiteName, int hh, int mm, int ss) {
        blackClock = new Clock(hh, mm, ss);
        whiteClock = new Clock(hh, mm, ss);

        JFrame frame = new JFrame("Chess");
        frame.setLayout(new BorderLayout(10,10));

        // Load icon
        URL iconUrl = getClass().getClassLoader().getResource("images/wp.png");
        if (iconUrl != null) {
            try { frame.setIconImage(ImageIO.read(iconUrl)); }
            catch (Exception e) { e.printStackTrace(); }
        }

        // Top = Black info
        JPanel top = new JPanel(new BorderLayout());
        JLabel bName = new JLabel(blackName, SwingConstants.CENTER);
        bName.setForeground(Color.RED);
        blackTimeLabel = new JLabel(blackClock.getTime(), SwingConstants.CENTER);
        top.add(bName, BorderLayout.NORTH);
        top.add(blackTimeLabel, BorderLayout.SOUTH);

        // Bottom = White info
        JPanel bot = new JPanel(new BorderLayout());
        JLabel wName = new JLabel(whiteName, SwingConstants.CENTER);
        wName.setForeground(Color.BLUE);
        whiteTimeLabel = new JLabel(whiteClock.getTime(), SwingConstants.CENTER);
        bot.add(wName, BorderLayout.NORTH);
        bot.add(whiteTimeLabel, BorderLayout.SOUTH);

        // Center = BoardPanel, pass this as TimerStarter
        boardPanel = new BoardPanel(this);

        // East = Buttons
        JPanel buttons = new JPanel(new GridLayout(3,1,5,5));
        JButton instr = new JButton("Instructions");
        instr.addActionListener(e ->
                JOptionPane.showMessageDialog(frame,
                        "Drag pieces to move them.\nTimer starts on your first valid move.",
                        "Instructions", JOptionPane.PLAIN_MESSAGE)
        );
        JButton newG = new JButton("New Game");
        newG.addActionListener(e -> {
            SwingUtilities.invokeLater(new StartMenu());
            frame.dispose();
        });
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> { if (timer!=null) timer.stop(); frame.dispose(); });

        buttons.add(instr);
        buttons.add(newG);
        buttons.add(quit);

        // Assemble
        frame.add(top,    BorderLayout.NORTH);
        frame.add(bot,    BorderLayout.SOUTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(buttons,    BorderLayout.EAST);

        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void startTimerIfNotStarted() {
        if (!timerStarted) {
            timerStarted = true;
            timer = new Timer(1000, e -> {
                if (boardPanel.isWhiteTurn()) whiteClock.decr();
                else                            blackClock.decr();
                whiteTimeLabel.setText(whiteClock.getTime());
                blackTimeLabel.setText(blackClock.getTime());
                if (whiteClock.outOfTime() || blackClock.outOfTime()) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Time's up!");
                }
            });
            timer.start();
        }
    }
}
