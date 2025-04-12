package main.java.chess.view;

import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartMenu implements Runnable {
    @Override
    public void run() {
        JFrame startWindow = new JFrame("Chess Start Menu");
        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startWindow.setSize(260, 240);
        startWindow.setLocation(300, 100);
        startWindow.setResizable(false);

        Box components = Box.createVerticalBox();
        startWindow.add(components);

        JPanel titlePanel = new JPanel();
        components.add(titlePanel);
        JLabel titleLabel = new JLabel("Chess");
        titlePanel.add(titleLabel);

        JPanel blackPanel = new JPanel();
        components.add(blackPanel);
        JLabel blackIcon = new JLabel();
        try {
            Image blackImg = ImageIO.read(getClass().getResource("/main/resources/images/bp.png"));
            blackIcon.setIcon(new ImageIcon(blackImg));
        } catch (Exception e) {
            System.err.println("bp.png not found");
        }
        blackPanel.add(blackIcon);
        JTextField blackInput = new JTextField("Black", 10);
        blackPanel.add(blackInput);

        JPanel whitePanel = new JPanel();
        components.add(whitePanel);
        JLabel whiteIcon = new JLabel();
        try {
            Image whiteImg = ImageIO.read(getClass().getResource("/main/resources/images/wp.png"));
            whiteIcon.setIcon(new ImageIcon(whiteImg));
            startWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            System.err.println("wp.png not found");
        }
        whitePanel.add(whiteIcon);
        JTextField whiteInput = new JTextField("White", 10);
        whitePanel.add(whiteInput);

        // Timer settings
        String[] minSecInts = new String[60];
        for (int i = 0; i < 60; i++) {
            minSecInts[i] = String.format("%02d", i);
        }
        JComboBox<String> hours = new JComboBox<>(new String[]{"0", "1", "2", "3"});
        JComboBox<String> minutes = new JComboBox<>(minSecInts);
        JComboBox<String> seconds = new JComboBox<>(minSecInts);
        Box timerBox = Box.createHorizontalBox();
        timerBox.add(hours);
        timerBox.add(Box.createHorizontalStrut(10));
        timerBox.add(minutes);
        timerBox.add(Box.createHorizontalStrut(10));
        timerBox.add(seconds);
        components.add(timerBox);

        // Buttons
        Box buttonBox = Box.createHorizontalBox();
        JButton start = new JButton("Start");
        start.addActionListener((ActionEvent e) -> {
            String bn = blackInput.getText();
            String wn = whiteInput.getText();
            int hh = Integer.parseInt((String) hours.getSelectedItem());
            int mm = Integer.parseInt((String) minutes.getSelectedItem());
            int ss = Integer.parseInt((String) seconds.getSelectedItem());
            new GameWindow(bn, wn, hh, mm, ss);
            startWindow.dispose();
        });
        JButton instruct = new JButton("Instructions");
        instruct.addActionListener(e -> JOptionPane.showMessageDialog(startWindow,
                "Enter player names, set a timer (or 0 for untimed), then click Start.",
                "Instructions", JOptionPane.PLAIN_MESSAGE));
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> startWindow.dispose());
        buttonBox.add(start);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(instruct);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(quit);
        components.add(buttonBox);

        startWindow.setVisible(true);
    }
}
