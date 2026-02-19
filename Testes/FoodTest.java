import org.junit.jupiter.api.*;

import game.Arena;
import game.Food;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest 
{
	@Test
	public void testConstructor() 
	{
		try {
			Food<?> f = new Food<>("Quadrado", 50, 50, 10, 10);
			assertNotNull(f);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Food<?> f = new Food<>("Circulo", 50, 50, 10, 10);
			assertNotNull(f);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Food<>("Circulo", -50, 50, 1, 10);
		});
		assertEquals("Food must be inside the first quadrant", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Food<>("Quadrado", 50, 50, 0, 10);
		});
		assertEquals("Reflection error occurred while creating food: null", exception.getMessage());
	}

	@Test
	public void testGenerateFood()
	{
		Arena arena = new Arena(100, 25, false);
		int snakeHeadSize = 3 * 25;
		Food<?> f = new Food<>("Quadrado", 500, 500, 3*25, 10);

		try {
			Food<?> generatedFood = f.generateFood(arena, snakeHeadSize);
			assertEquals(new Food<>("Quadrado", 0, 0, 3 * 25, 10).getFood(), generatedFood.getFood());
		} catch (ReflectiveOperationException e) {
			fail("Unexpected ReflectiveOperationException: " + e.getMessage());
		}
	
		arena = new Arena(75, 25, false);
		snakeHeadSize = 2 * 25;
		f = new Food<>("Circulo", 500, 500, 2 * 25, 10);
		
		try {
			Food<?> generatedFood = f.generateFood(arena, snakeHeadSize);
			assertEquals(new Food<>("Circulo", 0, 0, 2 * 25, 10).getFood(), generatedFood.getFood());
		} catch (ReflectiveOperationException e) {
			fail("Unexpected ReflectiveOperationException: " + e.getMessage());
		}
	}
}
