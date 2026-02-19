package geofig;
public class Reta {
	private Ponto<?> p1, p2;

	/**
	 * @author Rodrigo Coelho 79786
	 * @inv p1.x != p2.x
	 * @inv p1.y != p2.y
	 */
	public Reta(Ponto<?> p1, Ponto<?> p2)
	{
		if(Math.abs(p1.getX().doubleValue() - p2.getX().doubleValue()) < 1e-9 && Math.abs(p1.getY().doubleValue() - p2.getY().doubleValue()) < 1e-9)
			throw new IllegalArgumentException("Reta:vi");

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
	 * Checks if 2 lines are collinear
	 * @param this		Object from class Reta
	 * @param that		Object from class Reta
	 * @return true if lines are collinear, false otherwise
	 */
	public boolean areCollinear(Reta that)
	{
		boolean result = false;
		double m1 = this.declive();
		double m2 = that.declive();

		result = Math.abs(m1-m2) < 1e-9 ? true : false;
		
		if(!result) return false; // Declives diferentes não podem ser colineares

		SegmentoReta srThis = new SegmentoReta(this.getP1(), this.getP2());
		SegmentoReta srThat = new SegmentoReta(that.getP1(), that.getP2());

		return SegmentoReta.isSegmentOnSegment(srThis, srThat);
	}

	/**
	 * @param r		Object of class SegmentoReta
	 * @return the slope of a line
	 */
	public double declive() 
	{
		double deltaX = p2.getX().doubleValue() - p1.getX().doubleValue();
		if (deltaX == 0) return Double.NaN;
		
		return (p2.getY().doubleValue() - p1.getY().doubleValue()) / deltaX;
	}
}