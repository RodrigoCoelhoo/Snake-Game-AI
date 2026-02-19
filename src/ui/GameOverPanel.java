package ui;
import javax.swing.*;

import game.SnakeGame;

import java.awt.*;

public class GameOverPanel extends JPanel {
    private SnakeGame game;

    public GameOverPanel(SnakeGame game) {
        this.game = game;
        setPreferredSize(new Dimension(game.getArena().getArenaSize(), game.getArena().getArenaSize()));
        setBackground(Color.black);
    }

    
    /** 
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameOverScreen(g);
    }

    private void drawGameOverScreen(Graphics g) {
        // GameOver string
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.setColor(Color.RED);
        String gameOverText = "Game Over";
        int xGameOver = (getWidth() - g.getFontMetrics().stringWidth(gameOverText)) / 2;
        int yGameOver = getHeight() / 2 - 100;
        g.drawString(gameOverText, xGameOver, yGameOver);
    
        // Player + Score + Time strings
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.WHITE);
        String playerName = game.getPlayerName();
        int score = game.getScore();

        String playerNameText = "Player: " + playerName;
        int xPlayerName = (getWidth() - g.getFontMetrics().stringWidth(playerNameText)) / 2;
        int yPlayerName = yGameOver + 40;
        g.drawString(playerNameText, xPlayerName, yPlayerName);

        String scoreText = "Score: " + score;
        int xScore = (getWidth() - g.getFontMetrics().stringWidth(scoreText)) / 2;
        int yScore = yPlayerName + 35;
        g.drawString(scoreText, xScore, yScore);

        String timeText = "Time: " + game.getTime().formatTime();
        int xTime = (getWidth() - g.getFontMetrics().stringWidth(timeText)) / 2;
        int yTime = yScore + 35;
        g.drawString(timeText, xTime, yTime);

        // Restart string
        String restartText = "Press 'R' to restart the game";
        int xRestart = (getWidth() - g.getFontMetrics().stringWidth(restartText)) / 2;
        int yRestart = yScore + 100;
        g.drawString(restartText, xRestart, yRestart);

        // Main menu string
        String mmText = "Press 'M' to go to the main menu";
        int xMm = (getWidth() - g.getFontMetrics().stringWidth(mmText)) / 2;
        int yMm = yRestart + 40;
        g.drawString(mmText, xMm, yMm);
    }
}
