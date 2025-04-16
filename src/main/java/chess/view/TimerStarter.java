package main.java.chess.view;

public interface TimerStarter {
    void startTimerIfNotStarted();
    void gameOver(int winnerColor);
}
