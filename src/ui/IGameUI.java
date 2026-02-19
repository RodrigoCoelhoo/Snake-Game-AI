package ui;
import game.SnakeGame;

public interface IGameUI 
{
    /**
     * Link game with the UI
     * @param game      Represents the game
     */
    void setGame(SnakeGame game);
    void setDelay(int delay);
    
    void initialize();

    void render();

    int getUpdateDelay();
}
