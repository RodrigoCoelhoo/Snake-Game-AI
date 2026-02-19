package ui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Snake;
import game.SnakeGame;

public class GameKeyListener implements KeyListener {
    private SnakeGame game;
    private GameFrame gameFrame;

    public GameKeyListener(SnakeGame game, GameFrame gameFrame) {
        this.game = game;
        this.gameFrame = gameFrame;
    }

    
    /** 
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (game.getInputRecieved()) {
            System.out.println("Input Cooldown");
            return;
        }
        game.setInputRecieved(true);

        Snake snake = game.getSnake();
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (snake.getYDirection() != 1) {
                    snake.moveUp();
                    game.setStarted(true);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (snake.getYDirection() != -1) {
                    snake.moveDown();
                    game.setStarted(true);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (snake.getXDirection() != 1) {
                    snake.moveLeft();
                    game.setStarted(true);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (snake.getXDirection() != -1) {
                    snake.moveRight();
                    game.setStarted(true);
                }
                break;
            case KeyEvent.VK_R:
                if (game.getGameOver()) {
                    game.restart();
                    gameFrame.showGame();
                }
                break;
            case KeyEvent.VK_M:
                if (game.getGameOver()) {
                    game.restart();
                    game.setStarted(false);
                    gameFrame.showMainMenu();
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }
}
