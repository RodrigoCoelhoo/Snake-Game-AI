package geofig;
public class Ponto<T extends Number> {	
	private T x, y;
	
    /**
     * @author Rodrigo Coelho 79786
     * @inv A point must be in the first quadrant of the Cartesian graph.
     */
    public Ponto(T x, T y) {
        //if (x.doubleValue() < 0 || y.doubleValue() < 0) throw new IllegalArgumentException("Ponto:vi");

        this.x = x;
        this.y = y;
    }

    
	/** 
	 * @return T
	 */
	public T getX() {
        return this.x;
    }

    public T getY() {
        return this.y;
    }

    /**
     * Calculates the distance between 2 given points.
     * @param that Object from class Ponto
     * @return distance between 2 given points.
     */
    public double dist(Ponto<?> that) {
        double dx = this.x.doubleValue() - that.getX().doubleValue();
        double dy = this.y.doubleValue() - that.getY().doubleValue();
        return Math.sqrt(dx * dx + dy * dy);
    }

	/**
	 * Checks if there is colinearity of 3 consecutive points on an array of Objects Ponto
	 * @param pontos
	 * @return true if 3 consecutive points are collinear, false otherwise
	 * @pre array must have at least 3 points
	 */
	public static boolean areThreeConsecutivePointsCollinear(Ponto<?>[] pontos)
	{
		boolean condition = false;
		for(int i = 0; i < pontos.length; i++)
		{
			Reta r1 = new Reta(pontos[i], pontos[(i + 1) % pontos.length]);
			Reta r2 = new Reta(pontos[(i + 1) % pontos.length], pontos[(i + 2) % pontos.length]);

			condition = r1.areCollinear(r2);

			if(condition) return condition;
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "(" + this.getX() + "," + this.getY() + ")";
	}

	@Override
	public boolean equals(Object that)
	{
		Ponto<?> p = (Ponto<?>) that;
		if(this.getX().doubleValue() == p.getX().doubleValue() && this.getY().doubleValue() == p.getY().doubleValue()) return true;

		return false;
	}

	/**
	 * Created by chatGPT in order to override equals
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getX().hashCode();
		result = prime * result + this.getY().hashCode();
		return result;
	}

	/**
	 * Rotates the point around the pivot by the given angle in degrees
	 * @param degrees 	representing the angle of rotation in degrees
	 * @param pivotX	representing the x coordinate of the center of rotation of the point
	 * @param pivotY	representing the y coordinate of the center of rotation of the point  
	 * @return an object from class Ponto representing the rotated point
	 */
	public Ponto<Double> rotate(double degrees, double pivotX, double pivotY) 
	{
		double radian = degrees * Math.PI / 180;
	
		double xTemp = this.getX().doubleValue() - pivotX;
		double yTemp = this.getY().doubleValue() - pivotY;
	
		double xFinal = Math.round(xTemp * Math.cos(radian) - yTemp * Math.sin(radian) + pivotX);
		double yFinal = Math.round(xTemp * Math.sin(radian) + yTemp * Math.cos(radian) + pivotY);
		
		return new Ponto<>(xFinal, yFinal);
	}
}