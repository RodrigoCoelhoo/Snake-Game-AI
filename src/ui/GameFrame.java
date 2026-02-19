package ui;
import javax.swing.*;

import game.SnakeGame;

import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;
    private MainMenuPanel mainMenuPanel;

    public GameFrame(SnakeGame game) {
        super("SnakeGame");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        gamePanel = new GamePanel(game);
        gameOverPanel = new GameOverPanel(game);
        mainMenuPanel = new MainMenuPanel(game, this);

        mainPanel.add(mainMenuPanel, "MainMenu");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(gameOverPanel, "GameOver");

        add(mainPanel);
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addKeyListener(new GameKeyListener(game, this));
    }

    public void showMainMenu() {
        cardLayout.show(mainPanel, "MainMenu");
        mainMenuPanel.requestFocus();
    }

    public void showGame() {
        cardLayout.show(mainPanel, "Game");
        requestFocus();
    }

    public void showGameOver() {
        cardLayout.show(mainPanel, "GameOver");
    }

    public void updateGamePanel() {
        gamePanel.repaint();
    }
}
