package geofig;
public class SegmentoReta {
	private Ponto<?> p1, p2;

	/**
	 * @author Rodrigo Coelho 79786
	 * @inv p1.x != p2.x
	 * @inv p1.y != p2.y
	 */
	public SegmentoReta(Ponto<?> p1, Ponto<?> p2)
	{
		if(Math.abs(p1.getX().doubleValue() - p2.getX().doubleValue()) < 1e-9 && Math.abs(p1.getY().doubleValue() - p2.getY().doubleValue()) < 1e-9)
		{
			throw new IllegalArgumentException("SegmentoReta:vi");
		}

		this.p1 = p1;
		this.p2 = p2;
	}

	
	/** 
	 * @return Ponto<?>
	 */
	public Ponto<?> getP1()
	{
		return this.p1;
	}

	public Ponto<?> getP2()
	{
		return this.p2;
	}

	/**
	 * Checks if three points are in a counter-clockwise orientation
	 * @param A		Object from class Ponto
	 * @param B		Object from class Ponto
	 * @param C		Object from class Ponto
	 * @see https://stackoverflow.com/questions/3838329/how-can-i-check-if-two-segments-intersect
	 * @see https://bryceboe.com/2006/10/23/line-segment-intersection-algorithm/
	 * @return true if the points are in a counter-clockwise orientation, false otherwise
	 */
	private static boolean isCounterClockwise(Ponto<?> A, Ponto<?> B, Ponto<?> C) {
		return (C.getY().doubleValue() - A.getY().doubleValue()) * (B.getX().doubleValue() - A.getX().doubleValue()) > 
			   (B.getY().doubleValue() - A.getY().doubleValue()) * (C.getX().doubleValue() - A.getX().doubleValue());
	}

	/**
	 * Checks if 2 segments intersect
	 * @param A		Object from class SegmentoReta
	 * @param B		Object from class SegmentoReta
	 * @see https://stackoverflow.com/questions/3838329/how-can-i-check-if-two-segments-intersect
	 * @see https://bryceboe.com/2006/10/23/line-segment-intersection-algorithm/
	 * @return true if the segments intersect, false otherwise
	 */
	public static boolean intersect(SegmentoReta A, SegmentoReta B)
	{
		boolean a1b1b2 = isCounterClockwise(A.p1, B.p1, B.p2);
		boolean a2b1b2 = isCounterClockwise(A.p2, B.p1, B.p2);
		boolean a1a2b1 = isCounterClockwise(A.p1, A.p2, B.p1);
		boolean a1a2b2 = isCounterClockwise(A.p1, A.p2, B.p2);

		return 	(a1b1b2 != a2b1b2 && a1a2b1 != a1a2b2);
	}

	/**
	 * Checks whether at least one of point the segment A is on the segment B or vice-versa
	 * @param A		Object of class SegmentoReta
	 * @param B		Object of class SegmentoReta
	 * @return true if at least one of the points of the segment is on the other, false otherwise
	 */
	public static boolean isSegmentOnSegment(SegmentoReta A, SegmentoReta B)
	{
		return (A.isPointOnSegment(B.p1) || A.isPointOnSegment(B.p2) || B.isPointOnSegment(A.p1) || B.isPointOnSegment(A.p2));
	}

	/**
	 * Checks if a point belongs to a segment
	 * @param p			Object of class Ponto
	 * @return true if the point is on the segment, false otherwise
	 */
	public boolean isPointOnSegment(Ponto<?> p)
	{
		double x1 = this.getP1().getX().doubleValue();
		double y1 = this.getP1().getY().doubleValue();
		double x2 = this.getP2().getX().doubleValue();
		double y2 = this.getP2().getY().doubleValue();

		if (p.getX().doubleValue() < Math.min(x1, x2) || p.getX().doubleValue() > Math.max(x1, x2)) return false;
		if (p.getY().doubleValue() < Math.min(y1, y2) || p.getY().doubleValue() > Math.max(y1, y2)) return false;

		if(x1 == x2 && (p.getY().doubleValue() >= Math.min(y1, y2) && p.getY().doubleValue() <= Math.max(y1, y2))) return true;
		if(y1 == y2 && (p.getX().doubleValue() >= Math.min(x1, x2) && p.getX().doubleValue() <= Math.max(x1, x2))) return true;

		double m = this.declive();
		double b = this.p1.getY().doubleValue() - m * this.p1.getX().doubleValue();

		return Math.abs(p.getY().doubleValue() - (m * p.getX().doubleValue() + b)) < 1e-9;
	}

	/**
	 * @param r		Object of class SegmentoReta
	 * @return the slope of a segment
	 */
	public double declive()
	{
		return ((p2.getY().doubleValue() - p1.getY().doubleValue())/(p2.getX().doubleValue() - p1.getX().doubleValue()));
	}
}