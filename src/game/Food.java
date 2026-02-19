package game;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.Random;

import geofig.Circulo;
import geofig.IFiguraGeo;
import geofig.Ponto;
import geofig.Quadrado;

public class Food<T extends IFiguraGeo>
{
	private final T food;
    private final String foodType;
    private final int x;
    private final int y;
	private final int size;
	private final int score;

    /**
     * Constructor for a Food instance with a Quadrado or Circulo object.
     * @param foodType      String with the name of the class of the food.
     * @param size          Represents the number of tiles that the food will occupy * tileSize.
     * @param score         The score for this food instance.
     * @throws ReflectiveOperationException if there is an issue with reflection.
     * @throws IllegalArgumentException if the specified food type is not recognized.
     * @inv x < 0
     * @inv y < 0
     * @inv size < 0
     */
    @SuppressWarnings("unchecked")
    public Food(String foodType, int x, int y, int size, int score)
    {
        if(x < 0 || y < 0) throw new IllegalArgumentException("Food must be inside the first quadrant");

        this.foodType = foodType;
        this.size = size;
        this.score = score;
        this.x = x;
        this.y = y;

        try{
            Class<?> cl;
            if (foodType.equals("Quadrado")) 
            {
                cl = Quadrado.class;
                Constructor<?> constructor = cl.getConstructor(Ponto[].class);
                Ponto<Double>[] pontos = new Ponto[]{new Ponto<Integer>(x, y), new Ponto<Integer>(x + size, y),
                                             new Ponto<Integer>(x + size, y + size), new Ponto<Integer>(x, y + size)};
                this.food = (T) constructor.newInstance((Object) pontos);
            } 
            else if (foodType.equals("Circulo")) 
            {
                cl = Circulo.class;
                Constructor<?> constructor = cl.getConstructor(Ponto.class, double.class);
                Ponto<Double> center = new Ponto<Double>((x + (double) size / 2), (y + (double) size / 2));
                this.food = (T) constructor.newInstance(center, (double) size);
            } 
            else 
            {
                throw new IllegalArgumentException("Invalid food type: " + foodType);
            }
        }
        catch(ReflectiveOperationException e){
            throw new IllegalArgumentException("Reflection error occurred while creating food: " + e.getMessage());
        }
    }

    public T getFood() { return this.food; }
    public String getFoodType() { return this.foodType; }
	public int getSize() { return this.size; }
	public int getScore() { return this.score; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
	
    /**
     * Generate a new food object based on the available area of the arena considering snake and obstacles positions
     * @param arena             Represents the arena of the game
     * @param snakeHeadSize     Represents the number of tiles that the snake will occupy * tileSize.
     * @return a new object representing the next food position 
     * @throws ReflectiveOperationException
     */
	public Food<T> generateFood(Arena arena, int snakeHeadSize) throws ReflectiveOperationException
	{
		Random r = new Random();
        
        Ponto<Integer>[] availablePoints = arena.availableArea(snakeHeadSize);
        
        int tmp =  r.nextInt(availablePoints.length);
        int x = availablePoints[tmp].getX().intValue() + r.nextInt((snakeHeadSize/arena.getTileSize() - getSize()/arena.getTileSize()) + 1);
        int y = availablePoints[tmp].getY().intValue() + r.nextInt((snakeHeadSize/arena.getTileSize() - getSize()/arena.getTileSize()) + 1);
		
		return new Food<T>(getFoodType(), x*arena.getTileSize(), y*arena.getTileSize(), getSize(), getScore());
	}

    /** 
     * @param g
     * @param fill
     */
    public void draw(Graphics g, boolean fill)
	{
		g.setColor(new Color(51, 153, 255));
		food.draw(g, fill);
	}
}