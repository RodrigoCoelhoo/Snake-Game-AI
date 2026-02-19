import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geofig.Ponto;

public class PontoTest 
{
	@Test
	public void testConstructor() 
	{
		try {
			Ponto<?> point = new Ponto<>(0,0);
			assertNotNull(point);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Ponto<?> point = new Ponto<>(3,2);
			assertNotNull(point);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Ponto<?> point = new Ponto<>(15,1000);
			assertNotNull(point);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Ponto<>(-1,-1);
		});
		assertEquals("Ponto:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Ponto<>(-1,1);
		});
		assertEquals("Ponto:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Ponto<>(1,-1);
		});
		assertEquals("Ponto:vi", exception.getMessage());
	}
	
	@Test
	public void testDist()
	{
		Ponto<?> p1, p2;
		p1 = new Ponto<>(1,1);
		p2 = new Ponto<>(4,5);
		assertEquals(5, p1.dist(p2));

		p1 = new Ponto<>(3,1);
		p2 = new Ponto<>(4,5);
		assertEquals(4.12310562, p1.dist(p2), 1e-7);

		p1 = new Ponto<>(7,1);
		p2 = new Ponto<>(32,40);
		assertEquals(46.32493928, p1.dist(p2), 1e-7);
	}

	@Test
	public void testAreThreeConsecutivePointsCollinear()
	{
		assertEquals(true, 	Ponto.areThreeConsecutivePointsCollinear(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(2, 2), new Ponto<>(3, 3)}));
		assertEquals(true, 	Ponto.areThreeConsecutivePointsCollinear(new Ponto<?>[]{new Ponto<>(1, 0), new Ponto<>(2, 1), new Ponto<>(3, 0), new Ponto<>(2, 0)}));
		assertEquals(true, 	Ponto.areThreeConsecutivePointsCollinear(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(2, 2), new Ponto<>(7, 3), new Ponto<>(6, 1), new Ponto<>(3,3)}));
		assertEquals(false, Ponto.areThreeConsecutivePointsCollinear(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(2, 2), new Ponto<>(2, 1), new Ponto<>(3, 1), new Ponto<>(3, 3), new Ponto<>(1, 3)}));
		assertEquals(false, Ponto.areThreeConsecutivePointsCollinear(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(2, 3), new Ponto<>(3, 1), new Ponto<>(4, 3), new Ponto<>(4, 0)}));
		assertEquals(false, Ponto.areThreeConsecutivePointsCollinear(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(2, 3), new Ponto<>(3, 1), new Ponto<>(4, 3), new Ponto<>(4, 0), new Ponto<>(6,5), new Ponto<>(1,5)}));
	}

	@Test
	public void testToString()
	{
		assertEquals(true, 	((new Ponto<>(1,2)).toString()).equals("(1,2)"));
		assertEquals(true, 	((new Ponto<>(3,5)).toString()).equals("(3,5)"));
		assertEquals(true, 	((new Ponto<>(7,9)).toString()).equals("(7,9)"));
		assertEquals(false, ((new Ponto<>(4,2)).toString()).equals("(1,3)"));
		assertEquals(false, ((new Ponto<>(18,9)).toString()).equals("(42,15)"));
		assertEquals(false, ((new Ponto<>(10,9)).toString()).equals("(9,10)"));
	}

	@Test
	public void testEquals()
	{
		assertEquals(true, 	((new Ponto<>(1,2))).equals((new Ponto<>(1,2))));
		assertEquals(true, 	((new Ponto<>(3,5))).equals((new Ponto<>(3,5))));
		assertEquals(true, 	((new Ponto<>(7,9))).equals((new Ponto<>(7,9))));
		assertEquals(false, ((new Ponto<>(4,2))).equals((new Ponto<>(3,5))));
		assertEquals(false, ((new Ponto<>(18,9))).equals((new Ponto<>(9,18))));
		assertEquals(false, ((new Ponto<>(10,9))).equals((new Ponto<>(7,9))));
	}

	@Test
	public void testRotate()
	{
		assertEquals(true , (new Ponto<>(0,0)).rotate(180, 2,2).equals(new Ponto<>(4,4)));
		assertEquals(true , (new Ponto<>(3,7)).rotate(-45, 0,0).equals(new Ponto<>(7,3)));
		assertEquals(true , (new Ponto<>(5,1)).rotate(-90, 4,1).equals(new Ponto<>(4,0)));
		assertEquals(true , (new Ponto<>(7,9)).rotate(-75, 4,3).equals(new Ponto<>(11,2)));
	}
}