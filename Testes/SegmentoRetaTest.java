import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geofig.Ponto;
import geofig.SegmentoReta;

public class SegmentoRetaTest 
{
	@Test
	public void testConstructor() 
	{
		try {
			SegmentoReta SegmentoReta = new SegmentoReta(new Ponto<>(0,0),new Ponto<>(1,1));
			assertNotNull(SegmentoReta);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			SegmentoReta SegmentoReta = new SegmentoReta(new Ponto<>(5,0),new Ponto<>(1,7));
			assertNotNull(SegmentoReta);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			SegmentoReta SegmentoReta = new SegmentoReta(new Ponto<>(0,4),new Ponto<>(1,6));
			assertNotNull(SegmentoReta);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new SegmentoReta(new Ponto<>(5,0),new Ponto<>(5,0));
		});
		assertEquals("SegmentoReta:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new SegmentoReta(new Ponto<>(0,0),new Ponto<>(0,0));
		});
		assertEquals("SegmentoReta:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new SegmentoReta(new Ponto<>(0,5),new Ponto<>(0,5));
		});
		assertEquals("SegmentoReta:vi", exception.getMessage());
	}

	@Test
	public void testDeclive()
	{
		assertEquals(1, 	(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(3,3))).declive(), 1e-4);
		assertEquals(5, 	(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(1,5))).declive(), 1e-4);
		assertEquals(-1.5, 	(new SegmentoReta(new Ponto<>(7,4), new Ponto<>(9,1))).declive(), 1e-4);
		assertEquals(0, 	(new SegmentoReta(new Ponto<>(0,3), new Ponto<>(7,3))).declive(), 1e-4);
		assertTrue(Double.isInfinite((new SegmentoReta(new Ponto<>(3,0), new Ponto<>(3,7))).declive()));
	}

	@Test
	public void testIsPointOnSegment()
	{
		assertEquals(true, 	(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(2,2))).isPointOnSegment(new Ponto<>(0,0)));
		assertEquals(true, 	(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(2,2))).isPointOnSegment(new Ponto<>(1,1)));
		assertEquals(true, 	(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(2,2))).isPointOnSegment(new Ponto<>(2,2)));
		assertEquals(false, (new SegmentoReta(new Ponto<>(3,4), new Ponto<>(8,7))).isPointOnSegment(new Ponto<>(0,0)));
		assertEquals(false, (new SegmentoReta(new Ponto<>(3,4), new Ponto<>(8,7))).isPointOnSegment(new Ponto<>(8,8)));
		assertEquals(false, (new SegmentoReta(new Ponto<>(3,4), new Ponto<>(8,7))).isPointOnSegment(new Ponto<>(6,4)));
	}

	@Test
	public void testIsSegmentOnSegment()
	{
		assertEquals(true, 	SegmentoReta.isSegmentOnSegment(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(2,2)), new SegmentoReta(new Ponto<>(1,1), new Ponto<>(8,7))));
		assertEquals(true, 	SegmentoReta.isSegmentOnSegment(new SegmentoReta(new Ponto<>(1,1), new Ponto<>(2,2)), new SegmentoReta(new Ponto<>(0,0), new Ponto<>(3,3))));
		assertEquals(false, SegmentoReta.isSegmentOnSegment(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(2,2)), new SegmentoReta(new Ponto<>(5,7), new Ponto<>(10,5))));
		assertEquals(false, SegmentoReta.isSegmentOnSegment(new SegmentoReta(new Ponto<>(3,0), new Ponto<>(0,3)), new SegmentoReta(new Ponto<>(4,0), new Ponto<>(8,3))));
	}

    @Test
    public void testIntersect() {
        assertEquals(true,  SegmentoReta.intersect(new SegmentoReta(new Ponto<>(0, 0), new Ponto<>(2, 2)), new SegmentoReta(new Ponto<>(1, 0), new Ponto<>(0, 1))));
        assertEquals(true, 	SegmentoReta.intersect(new SegmentoReta(new Ponto<>(1, 0), new Ponto<>(2, 2)), new SegmentoReta(new Ponto<>(1, 1), new Ponto<>(3, 1))));
        assertEquals(true,  SegmentoReta.intersect(new SegmentoReta(new Ponto<>(0, 0), new Ponto<>(2, 2)), new SegmentoReta(new Ponto<>(3, 0), new Ponto<>(0, 3))));
		assertEquals(false, SegmentoReta.intersect(new SegmentoReta(new Ponto<>(0, 0), new Ponto<>(2, 2)), new SegmentoReta(new Ponto<>(1, 1), new Ponto<>(3, 3))));
        assertEquals(false, SegmentoReta.intersect(new SegmentoReta(new Ponto<>(0, 0), new Ponto<>(1, 1)), new SegmentoReta(new Ponto<>(3, 3), new Ponto<>(4, 4))));
        assertEquals(false, SegmentoReta.intersect(new SegmentoReta(new Ponto<>(0, 0), new Ponto<>(2, 2)), new SegmentoReta(new Ponto<>(0, 5), new Ponto<>(5, 0))));
    }
}