import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geofig.Ponto;
import geofig.Quadrado;

public class QuadradoTest 
{
	@Test
	public void testConstructorP() 
	{
		try {
			Quadrado Quadrado = new Quadrado(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(2, 1), new Ponto<>(2, 2), new Ponto<>(1, 2)});
			assertNotNull(Quadrado);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Quadrado Quadrado = new Quadrado(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(4, 1), new Ponto<>(4, 4), new Ponto<>(1,4)});
			assertNotNull(Quadrado);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Quadrado(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(5, 1), new Ponto<>(5, 3), new Ponto<>(1, 3)});
		});
		assertEquals("Quadrado:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Quadrado(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(4, 1), new Ponto<>(4, 2), new Ponto<>(4, 4), new Ponto<>(1,4)});
		});
		assertEquals("Retangulo:vi", exception.getMessage());
	}

	@Test
	public void testConstructorS() 
	{
		try { 
			Quadrado Quadrado = new Quadrado("1 1 4 1 4 4 1 4");
			assertNotNull(Quadrado);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try { 
			Quadrado Quadrado = new Quadrado("1 1 2 1 2 2 1 2");
			assertNotNull(Quadrado);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Quadrado("1 1 5 1 5 3 1 3");
		});
		assertEquals("Quadrado:vi", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Quadrado("1 1 4 1 4 2 1 2");
		});
		assertEquals("Quadrado:vi", exception.getMessage());
	}
	@Test
	public void testToString()
	{
		assertEquals(true, ((new Quadrado("3 1 4 2 3 3 2 2")).toString()).equals("Quadrado: [(3.0,1.0), (4.0,2.0), (3.0,3.0), (2.0,2.0)]"));
		assertEquals(true, ((new Quadrado("1 1 3 1 3 3 1 3")).toString()).equals("Quadrado: [(1.0,1.0), (3.0,1.0), (3.0,3.0), (1.0,3.0)]"));
		assertEquals(true, ((new Quadrado("3 1 5 3 3 5 1 3")).toString()).equals("Quadrado: [(3.0,1.0), (5.0,3.0), (3.0,5.0), (1.0,3.0)]"));
		assertEquals(true, ((new Quadrado("1 1 5 1 5 5 1 5")).toString()).equals("Quadrado: [(1.0,1.0), (5.0,1.0), (5.0,5.0), (1.0,5.0)]"));
	}
}