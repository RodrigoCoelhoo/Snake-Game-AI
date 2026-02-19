package geofig;
import java.util.Arrays;

public class Triangulo extends Poligono{

	/**
	 * @Author Rodrigo Coelho 79786
	 * @param pontos representing the points of the square
	 * @inv must have only 3 points
	 */
	public Triangulo(Ponto<?>[] pontos){
		super(pontos);

		if(pontos.length != 3)
		{
			throw new IllegalArgumentException("Triangulo:vi");
		}
	}

	/**
	 * @Author Rodrigo Coelho 79786
	 * @param str representing the points of the square
	 * @inv must have only 3 points
	 */
	public Triangulo(String str){
		super(str);

		if(pontos.length != 3) throw new IllegalArgumentException("Triangulo:vi");
	}

	
	/** 
	 * @return String
	 */
	@Override
	public String toString()
	{
		return "Triangulo: " + Arrays.toString(getPoints());
	}

	@Override
	protected Poligono createInstance(Ponto<?>[] points) {
        return new Triangulo(points);
    }
}
