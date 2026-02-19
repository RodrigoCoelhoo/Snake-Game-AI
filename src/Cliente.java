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
        SnakeGame snakeGame = new SnakeGame(
            36, 
            2, 
            true, 
            2, 
            "Circulo", 
            10, 
            "data/obstacles.txt", 
            gameUI, 
            "data/leaderboard.txt"
        );
        snakeGame.start();
    }
}