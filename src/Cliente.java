import game.SnakeGame;
import ui.GraphicalGameUI;
import ui.IGameUI;

public class Cliente
{
    
    /** 
     * @param args
     */
    public static void main(String[] args)
    {
        IGameUI gameUI = new GraphicalGameUI(); // new TextGameUI();
        gameUI.setDelay(2000);
        
        SnakeGame snakeGame = new SnakeGame(
            36, 
            1, 
            true, 
            1, 
            "Circulo", 
            10, 
            "data/obstacles.txt", 
            gameUI, 
            "data/leaderboard.txt"
        );
        snakeGame.getArena().setShowHitboxs(false);

        snakeGame.start();
    }
}