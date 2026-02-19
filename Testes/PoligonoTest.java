import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geofig.Poligono;
import geofig.Ponto;
import geofig.SegmentoReta;

public class PoligonoTest 
{
	static private Poligono p = new Poligono(new Ponto<?>[]{new Ponto<>(1,1), new Ponto<>(3,1), new Ponto<>(3,5), new Ponto<>(1, 5)});

	@Test
	public void testConstructorP() 
	{
		try {
			Poligono Poligono = new Poligono(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(3, 5), new Ponto<>(1, 5)});
			assertNotNull(Poligono);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try {
			Poligono Poligono = new Poligono(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(5, 3), new Ponto<>(4, 4), new Ponto<>(2,3)});
			assertNotNull(Poligono);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Poligono(new Ponto[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(2, 3), new Ponto<>(5, 2)});
		});
		assertEquals("Poligono:vi", exception.getMessage());

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Poligono(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(1, 5), new Ponto<>(3, 3), new Ponto<>(1, 2)});
		});
		assertEquals("Poligono:vi", exception.getMessage());
	}

	@Test
	public void testConstructorS() 
	{
		try { 
			Poligono Poligono = new Poligono("4 1 1 3 1 3 5 1 5");
			assertNotNull(Poligono);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}

		try { 
			Poligono Poligono = new Poligono("5 1 1 3 1 5 3 4 4 2 3");
			assertNotNull(Poligono);
		} catch (Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
		
		Throwable exception;
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Poligono("4 1 1 3 1 2 3 5 2");
		});
		assertEquals("Poligono:vi", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			new Poligono("4 1 1 1 5 3 3 1 2");
		});
		assertEquals("Poligono:vi", exception.getMessage());
	}
	
	@Test
	public void testEdgeIntersectsPolygon()
	{
		assertEquals(true, 	p.edgeIntersectsPolygon(new SegmentoReta(new Ponto<>(2,3), new Ponto<>(4,3))));
		assertEquals(true, 	p.edgeIntersectsPolygon(new SegmentoReta(new Ponto<>(0,0), new Ponto<>(4,6))));
		assertEquals(false, p.edgeIntersectsPolygon(new SegmentoReta(new Ponto<>(3,3), new Ponto<>(4,3))));
		assertEquals(false, p.edgeIntersectsPolygon(new SegmentoReta(new Ponto<>(3,0), new Ponto<>(3,7))));
	}

	@Test
	public void testIsPointInsidePolygon()
	{
		assertEquals(true, 	p.isPointInsidePolygon(new Ponto<>(2, 2)));
		assertEquals(true, 	p.isPointInsidePolygon(new Ponto<>(2, 3)));
		assertEquals(true, 	p.isPointInsidePolygon(new Ponto<>(2, 4)));
		assertEquals(true, p.isPointInsidePolygon(new Ponto<>(1, 1)));
		assertEquals(true, p.isPointInsidePolygon(new Ponto<>(3, 5)));
		assertEquals(true, p.isPointInsidePolygon(new Ponto<>(3, 3)));
		assertEquals(false, p.isPointInsidePolygon(new Ponto<>(7, 7)));
		assertEquals(false, p.isPointInsidePolygon(new Ponto<>(0, 3)));
		
	}

	@Test
	public void testIsPointOnPolygon()
	{
		assertEquals(false, p.isPointOnPolygon(new Ponto<>(2, 2)));
		assertEquals(false, p.isPointOnPolygon(new Ponto<>(2, 3)));
		assertEquals(false, p.isPointOnPolygon(new Ponto<>(2, 4)));
		assertEquals(true, 	p.isPointOnPolygon(new Ponto<>(1, 1)));
		assertEquals(true, 	p.isPointOnPolygon(new Ponto<>(3, 5)));
		assertEquals(true, 	p.isPointOnPolygon(new Ponto<>(3, 3)));
		assertEquals(false, p.isPointOnPolygon(new Ponto<>(7, 7)));
		assertEquals(false, p.isPointOnPolygon(new Ponto<>(0, 3)));
	}

	@Test
	public void testArePolygonsIntercepting() 
	{
		Ponto<?>[] p1 = new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(4, 3), new Ponto<>(3, 4)};
		Ponto<?>[] p2 = new Ponto<?>[]{new Ponto<>(3, 2), new Ponto<>(6, 2), new Ponto<>(6, 0)};
		assertEquals(true, (new Poligono(p1).arePolygonsIntercepting(new Poligono(p2))));

		p2 = new Ponto<?>[]{new Ponto<>(5, 3), new Ponto<>(7, 3), new Ponto<>(6, 0)};
		assertEquals(false, (new Poligono(p1).arePolygonsIntercepting(new Poligono(p2))));

		p2 = new Ponto<?>[]{new Ponto<>(2, 5), new Ponto<>(5, 2), new Ponto<>(5, 5)};
		assertEquals(false, (new Poligono(p1).arePolygonsIntercepting(new Poligono(p2))));

		p2 = new Ponto<?>[]{new Ponto<>(1, 3), new Ponto<>(4, 1), new Ponto<>(5, 4), new Ponto<>(3, 5)};
		assertEquals(true, (new Poligono(p1).arePolygonsIntercepting(new Poligono(p2))));

		p2 = new Ponto<?>[]{new Ponto<>(1,1), new Ponto<>(3,1), new Ponto<>(5,3)};
		assertEquals(true, (new Poligono(p1).arePolygonsIntercepting(new Poligono(p2))));
	}

	@Test
	public void testPerimeter()
	{
		Poligono Poligono = p;
		assertEquals(12, Poligono.perimeter(), 1e-3);
		
		Poligono = new Poligono(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(5, 3), new Ponto<>(4, 4), new Ponto<>(2,3)});
		assertEquals(10.714776642, Poligono.perimeter(), 1e-3);

		Poligono = new Poligono(new Ponto<?>[]{new Ponto<>(1, 1), new Ponto<>(3, 1), new Ponto<>(3, 3)});
		assertEquals(6.8284271,  Poligono.perimeter(), 1e-3);
	}

	@Test
	public void testToString()
	{
		assertEquals(true, ((new Poligono("3 1 1 2 2 2 1")).toString()).equals("Poligono de 3 vertices: [(1.0,1.0), (2.0,2.0), (2.0,1.0)]"));
		assertEquals(true, ((new Poligono("4 1 1 2 2 4 2 3 1")).toString()).equals("Poligono de 4 vertices: [(1.0,1.0), (2.0,2.0), (4.0,2.0), (3.0,1.0)]"));
		assertEquals(true, ((new Poligono("5 2 1 1 3 3 4 5 3 4 1")).toString()).equals("Poligono de 5 vertices: [(2.0,1.0), (1.0,3.0), (3.0,4.0), (5.0,3.0), (4.0,1.0)]"));
		assertEquals(true, ((new Poligono("6 2 1 1 3 3 4 6 4 6 2 4 1")).toString()).equals("Poligono de 6 vertices: [(2.0,1.0), (1.0,3.0), (3.0,4.0), (6.0,4.0), (6.0,2.0), (4.0,1.0)]"));
	}

	@Test
	public void testEquals()
	{
		assertEquals(true,  ((new Poligono("3 1 1 2 2 2 1"))).equals((new Poligono("3 1 1 2 2 2 1"))));
		assertEquals(false, ((new Poligono("4 1 1 2 2 4 2 3 1"))).equals((new Poligono("4 1 1 2 3 4 2 3 1"))));
		assertEquals(false, ((new Poligono("5 2 1 1 3 3 4 5 3 4 1"))).equals((new Poligono("4 1 1 2 2 4 2 3 1"))));
		assertEquals(true,  ((new Poligono("6 2 1 1 3 3 4 6 4 6 2 4 1"))).equals((new Poligono("6 2 1 1 3 3 4 6 4 6 2 4 1"))));
	}

	@Test
	public void testCentroid()
	{
		assertArrayEquals(new Poligono("0 0 4 0 4 4 0 4").centroide(), new double[]{2,2});
		assertArrayEquals(new Poligono("3 0 4 2 6 3 4 4 3 6 2 4 0 3 2 2").centroide(), new double[]{3,3});
		assertArrayEquals(new Poligono("1 1 5 1 5 5").centroide(), new double[]{3.6666,2.3333}, 1e-3); 
		assertArrayEquals(new Poligono("1 2 5 2 5 4 1 4").centroide(), new double[]{3,3}); 
	}

	@Test
	public void testRotate()
	{
		assertEquals(true, (new Poligono("3 0 4 2 6 3 4 4 3 6 2 4 0 3 2 2")).rotate(180).toString().equals("Poligono de 8 vertices: [(3.0,6.0), (2.0,4.0), (0.0,3.0), (2.0,2.0), (3.0,0.0), (4.0,2.0), (6.0,3.0), (4.0,4.0)]"));
		assertEquals(true, (new Poligono("3 0 4 2 6 3 4 4 3 6 2 4 0 3 2 2")).rotate(180, 3,3).toString().equals("Poligono de 8 vertices: [(3.0,6.0), (2.0,4.0), (0.0,3.0), (2.0,2.0), (3.0,0.0), (4.0,2.0), (6.0,3.0), (4.0,4.0)]"));

		assertEquals(true, (new Poligono("1 1 5 1 5 5")).rotate(-80).toString().equals("Poligono de 3 vertices: [(2.0,5.0), (3.0,1.0), (7.0,1.0)]"));
		assertEquals(true, (new Poligono("1 1 5 1 5 5")).rotate(-80, 4,2).toString().equals("Poligono de 3 vertices: [(2.0,5.0), (3.0,1.0), (7.0,2.0)]"));

		assertEquals(true, (new Poligono("1 2 5 2 5 4 1 4")).rotate(90).toString().equals("Poligono de 4 vertices: [(4.0,1.0), (4.0,5.0), (2.0,5.0), (2.0,1.0)]"));
		assertEquals(true, (new Poligono("1 2 5 2 5 4 1 4")).rotate(90, 3,3).toString().equals("Poligono de 4 vertices: [(4.0,1.0), (4.0,5.0), (2.0,5.0), (2.0,1.0)]"));

		
		assertEquals(true, (new Poligono("1 2 5 2 5 4 1 4")).rotate(0).toString().equals("Poligono de 4 vertices: [(1.0,2.0), (5.0,2.0), (5.0,4.0), (1.0,4.0)]"));
		assertEquals(true, (new Poligono("1 2 5 2 5 4 1 4")).rotate(0, 3,3).toString().equals("Poligono de 4 vertices: [(1.0,2.0), (5.0,2.0), (5.0,4.0), (1.0,4.0)]"));
		
		assertEquals(true, (new Poligono("1 2 5 2 5 4 1 4")).rotate(360).toString().equals("Poligono de 4 vertices: [(1.0,2.0), (5.0,2.0), (5.0,4.0), (1.0,4.0)]"));
		assertEquals(true, (new Poligono("1 2 5 2 5 4 1 4")).rotate(360, 3,3).toString().equals("Poligono de 4 vertices: [(1.0,2.0), (5.0,2.0), (5.0,4.0), (1.0,4.0)]"));
	}

	@Test
	public void testTranslate()
	{
		assertEquals(true, (new Poligono("3 1 1 2 2 2 1").translate(1, 1)).equals(new Poligono("2 2 3 3 3 2")));
		assertEquals(true, (new Poligono("4 1 1 5 1 5 5 1 5").translate(-1, -1)).equals(new Poligono("4 0 0 4 0 4 4 0 4")));
		assertEquals(true, (new Poligono("8 3 0 4 2 6 3 4 4 3 6 2 4 0 3 2 2").translate(3, 7)).equals(new Poligono("8 6 7 7 9 9 10 7 11 6 13 5 11 3 10 5 9")));
		assertEquals(true, (new Poligono("4 1 2 5 2 5 4 1 4").translate(10, 10)).equals(new Poligono("4 11 12 15 12 15 14 11 14")));

		assertEquals(true, (new Poligono("4 1 1 3 1 3 3 1 3").translate(new Ponto<>(3,3))).equals(new Poligono("4 2 2 4 2 4 4 2 4")));
		assertEquals(true, (new Poligono("4 3 1 4 2 3 3 2 2").translate(new Ponto<>(6,7))).equals(new Poligono("4 6 6 7 7 6 8 5 7")));
		assertEquals(true, (new Poligono("4 2 1 6 1 5 3 3 3").translate(new Ponto<>(10,4))).equals(new Poligono("4 8 3 12 3 11 5 9 5")));
		assertEquals(true, (new Poligono("8 4 1 6 1 8 3 8 5 6 7 4 7 2 5 2 3").translate(new Ponto<>(11,6))).equals(new Poligono("8 10 3 12 3 14 5 14 7 12 9 10 9 8 7 8 5")));
	}
}