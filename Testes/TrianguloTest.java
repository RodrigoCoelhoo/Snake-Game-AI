
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geofig.Ponto;
import geofig.Triangulo;

public class TrianguloTest 
{
	@Test
	public void testConstructorP() 
	{
		try {
			Triangulo Triangulo = new Triangulo(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(2, 1), new Ponto<>(1, 3)});
			assertNotNull(Triangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Triangulo Triangulo = new Triangulo(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(2, 3)});
			assertNotNull(Triangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Triangulo(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(3, 3), new Ponto<>(1, 3)});
		});
		assertEquals("Triangulo:vi", exception.getMessage());
	}

	@Test
	public void testConstructorS() 
	{
		try { 
			Triangulo Triangulo = new Triangulo("1 1 2 1 1 3");
			assertNotNull(Triangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try { 
			Triangulo Triangulo = new Triangulo("1 1 3 1 2 3");
			assertNotNull(Triangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Triangulo("1 1 3 1 3 3 1 3");
		});
		assertEquals("Triangulo:vi", exception.getMessage());
	}
	@Test
	public void testToString()
	{
		assertEquals(true, ((new Triangulo("1 1 2 1 1 3")).toString()).equals("Triangulo: [(1.0,1.0), (2.0,1.0), (1.0,3.0)]"));
		assertEquals(true, ((new Triangulo("1 1 3 1 2 3")).toString()).equals("Triangulo: [(1.0,1.0), (3.0,1.0), (2.0,3.0)]"));
		assertEquals(true, ((new Triangulo("1 5 3 3 4 6")).toString()).equals("Triangulo: [(1.0,5.0), (3.0,3.0), (4.0,6.0)]"));
		assertEquals(true, ((new Triangulo("7 6 9 6 8 4")).toString()).equals("Triangulo: [(7.0,6.0), (9.0,6.0), (8.0,4.0)]"));
	}
}