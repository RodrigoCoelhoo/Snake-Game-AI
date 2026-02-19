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
        
        // If TextGameUI > 1000 for a better experience
        // gameUI.setDelay(200);
        
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