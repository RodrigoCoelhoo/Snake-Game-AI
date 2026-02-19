package ui;
import java.util.Scanner;

import game.Snake;
import game.SnakeGame;
import strategy.AStar;

public class TextGameUI implements IGameUI
{
	private SnakeGame game;
	private int delay = 1000;

	public TextGameUI()
	{
		this.game = null;
	}
	
	/**
	 * Link the UI with the game
	 * @param game 		Represents the game
	 */
	public void setGame(SnakeGame game){ this.game = game; }

	@Override
    public void setDelay(int delay) {
        this.delay = delay;
    }

	public int getUpdateDelay() { return delay; }

	private Scanner sc;

	@Override
	public void initialize() 
	{
		sc = new Scanner(System.in);

		System.out.print("Player Name: ");
		while(game.getPlayerName() == null)
			game.setPlayerName(sc.nextLine());
		System.out.println("");

		System.out.println("Choose game mode:");
		System.out.println("1. Manual");
		System.out.println("2. Automatic");
	
		int modeChoice = 0;
		while (modeChoice != 1 && modeChoice != 2) {
			System.out.print("Game Mode: ");
			try {
				modeChoice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {

			}

			if (modeChoice != 1 && modeChoice != 2) {
				System.out.println("Inválido");
			}
		}

		if (modeChoice == 1) {
			game.setStrategy(null);;
		} else {
			game.setStrategy(new AStar());
			game.setStarted(true);
		}

		render();
		readPlayerInput();
	}	

	@Override
	public void render()
	{
		if(!game.getGameOver())
		{
			System.out.println("G03 - Player: " + game.getPlayername());
			game.getArena().print();
			System.out.println("Dir H: " + getDir() + " Pontos: " + game.getScore() + " Time: " + game.getTime().formatTime());
			if(!game.getInputRecieved())
				System.out.print("Head direction ('UP' [W] | 'DOWN' [S] | 'LEFT' [A] | 'RIGHT' [D]): ");
			System.out.println("");
		}
		else
		{
			System.out.println();
			System.out.println("--------------------");
			System.out.println("Game Over");
			System.out.println("--------------------");
			System.out.println("Player Name: " + game.getPlayername());
			System.out.println("Score: " + game.getScore());
			System.out.println("Time: " + game.getTime().formatTime());
			System.out.println();
			System.out.println("Input 'R' to restart the game");
			System.out.println("Input 'Q' to quit the game");
			System.out.println("--------------------");
		}
	}

	
	/** 
	 * @return int
	 */
	private int getDir()
	{
		if(game.getSnake().getXDirection() == -1)
			return 180;
		else if(game.getSnake().getXDirection() == 1)
			return 0;
		else if(game.getSnake().getYDirection() == -1)
			return 90;
		else if(game.getSnake().getYDirection() == 1)
			return 270;
		
		return 0;
	}
	
	private void readPlayerInput()
	{
		while (!game.getGameOver()) 
		{
			boolean validInput = false;
			Snake s = game.getSnake();
			
			while (!validInput) {
				String str = sc.nextLine().toUpperCase();
				switch (str) {
					case "W":
						if(getDir() == 270) break;
						s.moveUp();
						game.setStarted(true);
						validInput = true;
						break;
					case "S":
						if(getDir() == 90) break;
						s.moveDown();
						game.setStarted(true);
						validInput = true;
						break;
					case "A":
						if(getDir() == 0) break;
						s.moveLeft();
						game.setStarted(true);
						validInput = true;
						break;
					case "D":
						if(getDir() == 180) break;
						s.moveRight();
						game.setStarted(true);
						validInput = true;
						break;
					case "R":
						if(game.getGameOver())
							game.restart();
							initialize();
						break;
					case "Q":
						if(game.getGameOver())
							System.exit(0);
					default:
						System.out.println("Invalid input");
						break;
				}
			}
			System.out.println();
		}
		sc.close();
	}
}
