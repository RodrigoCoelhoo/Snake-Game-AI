package ui;
import game.SnakeGame;

public class GraphicalGameUI implements IGameUI
{
    private SnakeGame game;
    private GameFrame gameFrame;
    private int delay = 100;
    
    /** 
     * @param game
     */
    @Override
    public void setGame(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public int getUpdateDelay() {
        return this.delay;
    }

    @Override
    public void initialize() {
        gameFrame = new GameFrame(game);
        gameFrame.setVisible(true);
    }

    @Override
    public void render() {
        if (gameFrame != null) {
            gameFrame.updateGamePanel();
        }
    }

    public void showGameOver() {
        if (gameFrame != null) {
            gameFrame.showGameOver();
        }
    }

    public void showGame() {
        if (gameFrame != null) {
            gameFrame.showGame();
        }
    }

    public void showMainMenu() {
        if (gameFrame != null) {
            gameFrame.showMainMenu();
        }
    }
}
