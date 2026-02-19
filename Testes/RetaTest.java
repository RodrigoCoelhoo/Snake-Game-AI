import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geofig.Ponto;
import geofig.Reta;

public class RetaTest 
{
	@Test
	public void testConstructor() 
	{
		try {
			Reta reta = new Reta(new Ponto<>(0,0),new Ponto<>(1,1));
			assertNotNull(reta);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Reta reta = new Reta(new Ponto<>(5,0),new Ponto<>(1,7));
			assertNotNull(reta);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Reta reta = new Reta(new Ponto<>(0,4),new Ponto<>(1,6));
			assertNotNull(reta);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Reta(new Ponto<>(5,0),new Ponto<>(5,0));
		});
		assertEquals("Reta:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Reta(new Ponto<>(0,0),new Ponto<>(0,0));
		});
		assertEquals("Reta:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Reta(new Ponto<>(0,5),new Ponto<>(0,5));
		});
		assertEquals("Reta:vi", exception.getMessage());
	}

	@Test
	public void testAreCollinear()
	{
		assertEquals(true, 	(new Reta(new Ponto<>(0,0), new Ponto<>(3,3))).areCollinear(new Reta(new Ponto<>(2,2), new Ponto<>(4,4))));
		assertEquals(true, 	(new Reta(new Ponto<>(0,0), new Ponto<>(1,1))).areCollinear(new Reta(new Ponto<>(0,0), new Ponto<>(1,1))));
		assertEquals(false, (new Reta(new Ponto<>(0,0), new Ponto<>(1,1))).areCollinear(new Reta(new Ponto<>(1,1), new Ponto<>(3,1))));
		assertEquals(false, (new Reta(new Ponto<>(5,5), new Ponto<>(0,0))).areCollinear(new Reta(new Ponto<>(0,0), new Ponto<>(0,5))));
	}

	@Test
	public void testDeclive()
	{
		assertEquals(1, 	(new Reta(new Ponto<>(0,0), new Ponto<>(3,3))).declive(), 1e-4);
		assertEquals(5, 	(new Reta(new Ponto<>(0,0), new Ponto<>(1,5))).declive(), 1e-4);
		assertEquals(-1.5, 	(new Reta(new Ponto<>(7,4), new Ponto<>(9,1))).declive(), 1e-4);
		assertEquals(0, 	(new Reta(new Ponto<>(0,3), new Ponto<>(7,3))).declive(), 1e-4);
	}
}