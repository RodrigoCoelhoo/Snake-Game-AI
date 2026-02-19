package geofig;
import java.util.Arrays;

public class Quadrado extends Retangulo {

	/**
	 * @Author Rodrigo Coelho 79786
	 * @param pontos representing the points of the square
	 * @inv all sides must be equal
	 * @inv must have only 4 points
	 */
	public Quadrado(Ponto<?>[] pontos)
	{
		super(pontos);

		if(!isSquare()) throw new IllegalArgumentException("Quadrado:vi");
	}

	/**
	 * @Author Rodrigo Coelho 79786
	 * @param str representing the points of the square
	 * @inv all sides must be equal
	 * @inv must have only 4 points
	 */
	public Quadrado(String str)
	{
		super(str);

		if(!isSquare()) throw new IllegalArgumentException("Quadrado:vi");
	}

	/**
	 * Check if the given points form a square
	 * @param p 
	 * @return
	 */
	private boolean isSquare()
	{
		Ponto<?>[] p = getPoints();
		double side = p[0].dist(p[1]);

		for(int i = 0; i < p.length; i++)
			if(p[i].dist(p[((i+1) % p.length)]) != side) return false;

		return true;
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String toString()
	{
		return "Quadrado: " + Arrays.toString(getPoints());
	}

	@Override
	protected Poligono createInstance(Ponto<?>[] points) {
        return new Quadrado(points);
    }
}
