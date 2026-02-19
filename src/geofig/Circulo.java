package geofig;
import java.awt.*;

public class Circulo implements IFiguraGeo
{
	private final Ponto<?> center;
	private final double diameter;

	/**
	 * 
	 * @param center
	 * @param diameter
	 * @inv Circulo must be inside of the first quadrant
	 */
	public Circulo(Ponto<?> center, double diameter)
	{
		if(center.getX().doubleValue() - diameter/2 < 0 || center.getY().doubleValue() - diameter/2 < 0) 
			throw new IllegalArgumentException("Circulo:vi");
	
		this.center = center;
		this.diameter = diameter;
	}

	public Ponto<?> getCenter() { return center; }
	public double getDiameter() { return diameter; }

	
	/** 
	 * @param obj
	 * @return String
	 */
	@Override
	public String toString() { return "C: " + center.toString() + " | D: " + getDiameter(); }

	@Override
	public boolean equals(Object obj)
	{
		Circulo that = (Circulo) obj;
		return (this.getCenter() == that.getCenter() && this.getDiameter() == that.getDiameter());
	}

	/**
	 * Calculates the involving rectangle of a circle
	 * @return an object of class Retangulo representing the involving rectangle of a circle
	 */
	public Retangulo calculateInvolvingRectangle() 
	{
		Ponto<?> c = getCenter();
		double d = getDiameter(); 
		double r = d/2;
		double x = c.getX().doubleValue();
		double y = c.getY().doubleValue();


		Ponto<?>[] points = new Ponto<?>[4];
		points[0] = new Ponto<>(x - r, y - r);
		points[1] = new Ponto<>(x + r, y - r);
		points[2] = new Ponto<>(x + r, y + r);
		points[3] = new Ponto<>(x - r, y + r);

        return new Retangulo(points);
    }

	@Override
	public void draw(Graphics g, boolean fill)
	{
		if(fill)
			g.fillOval(getCenter().getX().intValue() - (int) (getDiameter()/2), getCenter().getY().intValue() - (int) getDiameter()/2, (int) getDiameter(), (int) getDiameter());
		else
			g.drawOval(getCenter().getX().intValue() - (int) (getDiameter()/2), getCenter().getY().intValue() - (int) getDiameter()/2, (int) getDiameter(), (int) getDiameter());
	}
}