import org.junit.jupiter.api.*;

import game.Arena;
import game.Snake;
import geofig.Ponto;
import geofig.Quadrado;

import static org.junit.jupiter.api.Assertions.*;

public class ArenaTest 
{
	@Test
	public void testConstructor() 
	{
		try {
			Arena arena = new Arena(600, 25, false);
			assertNotNull(arena);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Arena arena = new Arena(1, 25, false);
			assertNotNull(arena);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Arena(-1, 25, false);
		});
		assertEquals("Arena dimensions must be greater than 0", exception.getMessage());


		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Arena(500, 0, false);
		});
		assertEquals("Tile size must be greater than 0", exception.getMessage());
	}

	@Test
	public void testIsSnakeOutside()
	{
		Arena arena = new Arena(500, 25, false);
		
		assertEquals(false, arena.isSnakeOutside(new Snake(0, 0, 2*25)));
		assertEquals(false, arena.isSnakeOutside(new Snake(450, 450, 2*25)));
		assertEquals(false, arena.isSnakeOutside(new Snake(301, 400, 4*25)));
		
		assertEquals(true, arena.isSnakeOutside(new Snake(500, 500, 2*25)));
		assertEquals(true, arena.isSnakeOutside(new Snake(450, 450, 3*25)));
		assertEquals(true, arena.isSnakeOutside(new Snake(500, 450, 2*25)));
	}

	@Test
	public void testMarkAndUnmark()
	{
		// Arena 4x4
		Arena arena1 = new Arena(100, 25, true);  // Preenchido
		Arena arena2 = new Arena(100, 25, false); // Contorno
		
		// Quadrado "3x3" (metade do terceiro quadrado)
		Quadrado q = new Quadrado("0 0 60 0 60 60 0 60");
		arena1.mark(q, "Q ", true);
		arena2.mark(q, "Q ", false);
		
		Arena.Tile[][] grid1 = arena1.getGrid();
		Arena.Tile[][] grid2 = arena2.getGrid();

		String[] expectedMarks1 = {
			"Q ", "Q ", "Q ", ". ",
			"Q ", "Q ", "Q ", ". ",
			"Q ", "Q ", "Q ", ". ",
			". ", ". ", ". ", ". "
		};

		int count = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				assertEquals(expectedMarks1[count++], grid1[j][i].getMark());
				if(i != 3 && j != 3)
					assertEquals(true, grid1[j][i].isOccupied());
			}
		}
		
		String[] expectedMarks2 = {
			"Q ", "Q ", "Q ", ". ",
			"Q ", ". ", "Q ", ". ",
			"Q ", "Q ", "Q ", ". ",
			". ", ". ", ". ", ". "
		};

		count = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				assertEquals(expectedMarks2[count++], grid2[j][i].getMark());
				assertEquals(false, grid2[j][i].isOccupied());
			}
		}

		arena1.unmark(q, true);
		arena2.unmark(q, false);

		String[] expectedMarks3 = {
			". ", ". ", ". ", ". ",
			". ", ". ", ". ", ". ",
			". ", ". ", ". ", ". ",
			". ", ". ", ". ", ". "
		};

		count = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				assertEquals(expectedMarks3[count], grid1[j][i].getMark());
				assertEquals(expectedMarks3[count++], grid2[j][i].getMark());
				if(i != 3 && j != 3)
					assertEquals(true, grid1[j][i].isOccupied());
				assertEquals(false, grid2[j][i].isOccupied());
			}
		}
	}

	@Test
	public void testAvailableArea()
	{
		// 6x6
		Arena arena = new Arena(150, 25, true);
		
		// 4x4
		Quadrado q = new Quadrado("50 50 150 50 150 150 50 150");
		arena.mark(q, "Q ", true);
		Ponto<Integer>[] points = arena.availableArea(50);

		assertEquals(5, points.length);
		assertEquals(points[0], new Ponto<Integer>(0, 0));
		assertEquals(points[1], new Ponto<Integer>(0, 2));
		assertEquals(points[2], new Ponto<Integer>(0, 4));
		assertEquals(points[3], new Ponto<Integer>(2, 0));
		assertEquals(points[4], new Ponto<Integer>(4, 0));

	}
}
