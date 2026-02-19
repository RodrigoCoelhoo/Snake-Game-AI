package strategy;

import game.Arena;
import game.Snake;

public interface IGameStrategy {
	public void execute(Snake snake);
	public void setup(Arena arena);
}
