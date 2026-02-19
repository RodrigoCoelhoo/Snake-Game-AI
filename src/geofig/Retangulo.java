package geofig;
import java.util.Arrays;

public class Retangulo extends Poligono{

	/**
	 * @Author Rodrigo Coelho 79786
	 * @param p representing the points of the square
	 * @inv opposite sides must be equal
	 * @inv all segments have to form a 90 degree angle
	 * @inv must have only 4 points
	 */
	public Retangulo(Ponto<?>[] p)
	{
		super(p);
		
		if(pontos.length != 4 || !isRectangle()) 
		{
			for(Ponto<?> ponto : p)
				System.out.println(ponto.toString());
			throw new IllegalArgumentException("Retangulo:vi");
		}
	}

	/**
	 * @Author Rodrigo Coelho 79786
	 * @param str representing the points of the square
	 * @inv opposite sides must be equal
	 * @inv all segments have to form a 90 degree angle
	 * @inv must have only 4 points
	 */
	public Retangulo(String str)
	{
		super(str);

		if(pontos.length != 4 || !isRectangle()) throw new IllegalArgumentException("Retangulo:vi");
	}
	
	/**
	 * Check if the given points form a rectangle
	 * @return true if it forms a rectangle, false otherwise
	 */
	private boolean isRectangle()
	{
		Ponto<?>[] points = getPoints();
		
		// c1 - AB = CD && BC && DA
		boolean c1 = points[0].dist(points[1]) == points[2].dist(points[3]) && points[1].dist(points[2]) == points[3].dist(points[0]);
		
		// c2 - BD = AC
		boolean c2 = points[0].dist(points[2]) == points[1].dist(points[3]);

		return c1 && c2;
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String toString()
	{
		return "Retangulo: " + Arrays.toString(getPoints());
	}

	@Override
	protected Poligono createInstance(Ponto<?>[] points) {
        return new Retangulo(points);
    }
}
