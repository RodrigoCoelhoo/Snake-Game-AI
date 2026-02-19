import org.junit.jupiter.api.*;

import game.Arena;
import game.Food;
import game.Snake;
import geofig.Quadrado;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
	@Test
	public void testConstructor() 
	{
		try {
			Snake snake = new Snake(0, 0, 1 * 25);
			assertNotNull(snake);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Snake snake = new Snake(5, 5, 1 * 25);
			assertNotNull(snake);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Snake(0, 0, 0 * 25);
		});
		assertEquals("Head size must be bigger than 0", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Snake(3, -5, 3 * 25);
		});
		assertEquals("Snake initial position must be on the first quadrant", exception.getMessage());
	}

	@Test
	public void testCanEat()
	{
		Snake s = new Snake(0, 0, 3);
		assertEquals(true, s.canEat(new Food<>("Quadrado", 0, 0, 3, 10)));
		assertEquals(true, s.canEat(new Food<>("Circulo", 0, 0, 3, 10)));
		assertEquals(true, s.canEat(new Food<>("Quadrado", 1, 1, 2, 10)));
		
		assertEquals(false, s.canEat(new Food<>("Quadrado", 1, 1, 3, 10)));
		assertEquals(false, s.canEat(new Food<>("Quadrado", 3, 3, 3, 10)));
	}

	@Test
	public void testMove()
	{
		Arena a = new Arena(100, 25, false);
		Snake s = new Snake(0, 0, 1*25);

		s.moveDown();
        s.move(a);
        s.removeTail(a);
		assertEquals(true, s.getHead().equals(new Quadrado("0 25 0 50 25 50 25 25")));
		
		s.moveUp();
        s.move(a);
        s.removeTail(a);
		assertEquals(true, s.getHead().equals(new Quadrado("0 0 25 0 25 25 0 25")));
		
		s.moveRight();
        s.move(a);
        s.removeTail(a);
		assertEquals(true, s.getHead().equals(new Quadrado("25 0 25 25 50 25 50 0")));

		s.moveLeft();
        s.move(a);
        s.removeTail(a);
		assertEquals(true, s.getHead().equals(new Quadrado("0 0 25 0 25 25 0 25")));
	}

	@Test
	public void testCollidingWithTail()
	{
		/**
		* S    H    S    .
		* .    S    S    .
		* .    .    .    .
		* .    .    .    .
        */

		Arena a = new Arena(100, 25, false);
		Snake s = new Snake(0, 0, 25);
		
		s.moveRight();
        s.move(a);
		assertEquals(false, s.isCollidingWithTail());
		
		s.move(a);
		assertEquals(false, s.isCollidingWithTail());

		s.moveDown();
		s.move(a);
		assertEquals(false, s.isCollidingWithTail());

		s.moveLeft();
		s.move(a);
		assertEquals(false, s.isCollidingWithTail());

		s.moveUp();
		s.move(a);
		assertEquals(true, s.isCollidingWithTail());
	}
}