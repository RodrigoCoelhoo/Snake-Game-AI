package game;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Timer;

import data.Leaderboard;
import data.Obstacles;
import data.Time;
import geofig.Ponto;
import geofig.Quadrado;
import strategy.AStar;
import strategy.IGameStrategy;
import ui.GraphicalGameUI;
import ui.IGameUI;

public class SnakeGame
{
	private final Arena arena;
	private Snake snake;
	private Food<?> food;
	private final Leaderboard leaderboard;
	private final IGameUI gameUI;
	private IGameStrategy strategy = null;
	private final Obstacles obstacles;
	private final int tileSize = 25;
	private final int snakeHeadSize;
	private final int foodSize;

	private boolean gameOver = false;
	private boolean started = false;
	private String playerName;
	private int score = 0;
	private Timer updateTimer;
	private boolean inputReceived = false;
	private int delay = 500;
	private final Time time;

	/** Snake game constructor
	 * 
	 * @param arenaDim			Represents the dimension of the arena 
	 * @param snakeHeadSize		Represents the size of the snakeHead in tiles
	 * @param rasterizacao		Represents if the rasterizacao is turned off (false) or on (true)
	 * @param foodSize			Represents the size of the food in tiles
	 * @param foodType			Represents the class of the food ("Circulo" or "Quadrado")
	 * @param foodScore			Represents how many points each food gives to the player when he eats it
	 * @param obstacles			Represents the file of obstacles
	 * @param gameUI			Represents the user interface of the game
	 * @param leaderboard		Represents the file of leaderboard
	 * @pre snakeHeadSize >= foodSize
	 */
	public SnakeGame(
		int arenaDim, 
		int snakeHeadSize, 
		boolean rasterizacao, 
		int foodSize, String 
		foodType, int foodScore, 
		String obstacles, 
		IGameUI gameUI, 
		String leaderboard
	) 
	{
		this.arena = new Arena(arenaDim, tileSize, rasterizacao);
		this.snakeHeadSize = snakeHeadSize;
		this.foodSize = foodSize;
		this.leaderboard = new Leaderboard(leaderboard);
		this.obstacles = new Obstacles(obstacles);
		this.gameUI = gameUI;
		this.time = new Time(0);
		
		// Allows every dynamic object to do a full rotation, avoiding the creation of food/snake inside the trajectory of the objects
		for(int i = 0; i < 100; i++)
			this.obstacles.updateObstacles(arena);

        try {
            this.food = new Food<>(foodType, 75, 75, foodSize*tileSize, foodScore);
			this.food = food.generateFood(arena, snakeHeadSize*tileSize);
			arena.mark(food.getFood().calculateInvolvingRectangle(), "F ", true);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar instância de Food<T>: " + e.getMessage(), e);
        }

		Ponto<Integer>[] availablePoints = arena.availableArea(snakeHeadSize*tileSize);
		Random r = new Random();
		int tmp =  r.nextInt(availablePoints.length);
		int x = availablePoints[tmp].getX().intValue() * tileSize;
		int y = availablePoints[tmp].getY().intValue() * tileSize;
		this.snake = new Snake(x, y, snakeHeadSize * tileSize);
		arena.mark(snake.getHead(), "H ", true);

		if(gameUI != null)
			delay = gameUI.getUpdateDelay();

		updateTimer = new Timer(delay, e -> updateGame());
        updateTimer.start();
	}

	public Snake getSnake() { return this.snake; }
	public Arena getArena() { return this.arena; }
	public String getPlayername() { return this.playerName; }
	public boolean getGameOver() { return this.gameOver; }
	public int getScore() { return this.score; }
	public String getPlayerName() { return this.playerName; }
	public Food<?> getFood() { return this.food; }
	public int getFoodSize() { return this.foodSize; }
	public boolean getInputRecieved() { return this.inputReceived; }
	public IGameStrategy getGameStrategy() { return this.strategy; }
	public Time getTime() { return this.time; }
	public boolean getStarted() { return this.started; }

	public void setInputRecieved(boolean inputReceived) { this.inputReceived = inputReceived; }
	public void setStarted(boolean started) { this.started = started; }
	public void setPlayerName(String playerName) { this.playerName = playerName; }
	public void setStrategy(IGameStrategy strategy) { this.strategy = strategy; }

	/**
	 * Starts the game
	 */
	public void start()
	{
		if(gameUI == null)
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Player Name: ");
			while(playerName == null)
				playerName = sc.nextLine();
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
				setStrategy(null);
				snake.moveUp();
			} else {
				setStrategy(new AStar());
				setStarted(true);
			}
			sc.close();
			this.started = true;
		}
		else
		{
			gameUI.setGame(this);
			gameUI.initialize();
		}
	}

	/**
	 * Change the state of the game to Game Over
	 */
	public void gameOver() {
		this.gameOver = true;
	
		if (arena.availableArea(snakeHeadSize * tileSize).length == 0)
			score = Integer.MAX_VALUE;
	
		leaderboard.addEntry(playerName, score, this.time.getMilliseconds());
	
		if(gameUI != null)
			gameUI.render();
		else
			System.exit(0);
			
		if (gameUI instanceof GraphicalGameUI) {
			((GraphicalGameUI) gameUI).showGameOver();
		}
	}
	
	/**
	 * Restart the game by creating a new object SnakeGame
	 */
	public void restart() 
	{
		if (getGameOver()) {
			
			Quadrado[] snakebody = this.getSnake().toArray();
			for(int i = 0; i < snakebody.length; i++)
				arena.unmark(snakebody[i], false);

			// Allows every dynamic object to do a full rotation, avoiding the creation of food/snake inside the trajectory of the objects
			for(int i = 0; i < 100; i++)
				this.obstacles.updateObstacles(arena);

			try {
				arena.unmark(food.getFood().calculateInvolvingRectangle(), false);
				food = food.generateFood(arena, snakeHeadSize * arena.getTileSize());
				arena.mark(food.getFood().calculateInvolvingRectangle(), "F ", true);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}

			Ponto<Integer>[] availablePoints = arena.availableArea(snakeHeadSize*tileSize);
			Random r = new Random();
			int tmp =  r.nextInt(availablePoints.length);
			int x = availablePoints[tmp].getX().intValue() * tileSize;
			int y = availablePoints[tmp].getY().intValue() * tileSize;
			this.snake = new Snake(x, y, snakeHeadSize * tileSize);
			arena.mark(snake.getHead(), "H ", true);

			this.gameOver = false;
			this.started = false;
			this.time.setMilliseconds(0);
			this.score = 0;

			if (strategy != null) {
                setStarted(true);
            }
			
			if (gameUI instanceof GraphicalGameUI) {
				((GraphicalGameUI) gameUI).showGameOver();
			}
		}
	}
	
	/**
	 * Updates every object of the game
	 */
    public void updateGame() {
        inputReceived = false;

        if (!started)
            return;
        else
            this.time.add(this.delay);

        if (!gameOver) {
            if (arena.isSnakeOutside(snake) || snake.isCollidingWithTail() || arena.availableArea(snakeHeadSize * tileSize).length == 0 || obstacles.isSnakeCollidingWithObstacles(snake)) {
                gameOver();
                return;
            }

            if (strategy != null) {
                strategy.setup(arena);
                strategy.execute(snake);
                inputReceived = true; // Disable user input while in auto mode
            }

            snake.move(arena);
            try {
                if (snake.canEat(food)) {
                    food = food.generateFood(arena, snakeHeadSize * arena.getTileSize());
                    arena.mark(food.getFood().calculateInvolvingRectangle(), "F ", true);
                    score += food.getScore();
                } else {
                    snake.removeTail(arena);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            obstacles.updateObstacles(arena);
			
			if(gameUI != null)
            	gameUI.render();
        }
    }
	
	
	/** 
	 * @param g
	 */
	public void draw(Graphics g)
	{
		int arenaSize = arena.getArenaSize();
		int hudHeight = 40;

		arena.draw(g);
		food.draw(g, arena.getRasterizacao());
		snake.draw(g, arena.getRasterizacao());
		obstacles.draw(g, arena.getRasterizacao());

		g.setColor(Color.BLACK);
		g.fillRect(0, arenaSize, arenaSize, hudHeight);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 16));

		String timeStr = time.formatTime();

		int textY = arenaSize + 25;

		g.drawString("Player: " + playerName, 10, textY);
		g.drawString("Score: " + score, arenaSize / 2 - 40, textY);
		g.drawString("Time: " + timeStr, arenaSize - 120, textY);
	}

}