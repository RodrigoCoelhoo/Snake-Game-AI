import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geofig.Ponto;
import geofig.Retangulo;

public class RetanguloTest 
{
	@Test
	public void testConstructorP() 
	{
		try {
			Retangulo Retangulo = new Retangulo(new Ponto<?>[]{new Ponto<>(3, 1), new Ponto<>(6, 4), new Ponto<>(4, 6), new Ponto<>(1, 3)});
			assertNotNull(Retangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Retangulo Retangulo = new Retangulo(new Ponto<?>[]{new Ponto<>(1, 3), new Ponto<>(4, 3), new Ponto<>(4, 5), new Ponto<>(1,5)});
			assertNotNull(Retangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Retangulo(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(3, 3), new Ponto<>(1, 3)});
		});
		assertEquals("Retangulo:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Retangulo(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(4, 1), new Ponto<>(5, 3), new Ponto<>(4, 4), new Ponto<>(1,4)});
		});
		assertEquals("Retangulo:vi", exception.getMessage());
	}

	@Test
	public void testConstructorS() 
	{
		try { 
			Retangulo Retangulo = new Retangulo("3 1 6 4 4 6 1 3");
			assertNotNull(Retangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try { 
			Retangulo Retangulo = new Retangulo("1 3 4 3 4 5 1 5");
			assertNotNull(Retangulo);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Retangulo("1 1 3 1 3 3 1 3");
		});
		assertEquals("Retangulo:vi", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Retangulo("1 1 4 1 5 3 4 4 1 4");
		});
		assertEquals("Retangulo:vi", exception.getMessage());
	}
	@Test
	public void testToString()
	{
		assertEquals(true, ((new Retangulo("3 1 6 4 4 6 1 3")).toString()).equals("Retangulo: [(3.0,1.0), (6.0,4.0), (4.0,6.0), (1.0,3.0)]"));
		assertEquals(true, ((new Retangulo("1 3 4 3 4 5 1 5")).toString()).equals("Retangulo: [(1.0,3.0), (4.0,3.0), (4.0,5.0), (1.0,5.0)]"));
		assertEquals(true, ((new Retangulo("1 1 3 1 3 2 1 2")).toString()).equals("Retangulo: [(1.0,1.0), (3.0,1.0), (3.0,2.0), (1.0,2.0)]"));
	}
}