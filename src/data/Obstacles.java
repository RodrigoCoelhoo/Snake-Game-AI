package data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.Arena;
import game.Snake;
import geofig.*;

import java.awt.*;

public class Obstacles 
{
	private final String obstacleFile;
	private List<Obstacle> obstacles;

    /**
     * @param obstacleFile          Represents the path of the obstacles file
     */
	public Obstacles(String obstacleFile)
	{
		this.obstacleFile = obstacleFile;
        loadObstacles();
    }

    public String getFile(){ return obstacleFile; }
    public List<Obstacle> getObstacles(){ return obstacles; }

    /**
     * Responsible for reading the obstacle file and loading the list of obstacles
     */
    public void loadObstacles() 
    {
        List<Obstacle> obstacleList = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(obstacleFile))) 
        {
            String line;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split("\t");

                if (parts.length == 5) {
                    String className = parts[0]; 
                    String constructorArgument = parts[1];
                    boolean isDynamic = Boolean.parseBoolean(parts[2]);
                    double rotationAngle = Double.parseDouble(parts[4]);

                    Ponto<Double> pointOfRotation;
                    if(!parts[3].equals("null"))
                    {
                        String[] tmp = parts[3].split(" ");
                        pointOfRotation = new Ponto<>(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]));
                    }
                    else pointOfRotation = null;

                    Poligono obstacle = createObstacle(className, constructorArgument);
                    Obstacle newObstacle = new Obstacle(obstacle, isDynamic, rotationAngle, pointOfRotation);
                    obstacleList.add(newObstacle);
                } else {
                    System.err.println("Formato de linha inválido: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Falha ao carregar obstáculos: " + e.getMessage());
        }

        obstacles = obstacleList;
    }


    /**
     * Creates an instance of an obstacle based on the specified class.
     * 
     * @param <T>                   Generic type representing a subclass of Poligono.
     * @param className             Represents the name of the obstacle class to instantiate.
     * @param constructorArgument   Represents the argument to pass to the constructor of the obstacle class.
     * @return An instance of the specified obstacle class, or null if an exception occurs.
     */
    @SuppressWarnings("unchecked")
    private <T extends Poligono> T createObstacle(String className, String constructorArgument) 
    {
        try 
        {
            Class<T> clazz = (Class<T>) Class.forName("geofig." + className);
            T instance = clazz.getConstructor(String.class).newInstance(constructorArgument);
            return instance;
        } 
        catch (Exception e) {
            System.err.println("Falha ao criar a instância de " + className + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates each obstacle
     * 
     * @param arena     Represents the arena of the game
     */
	public void updateObstacles(Arena arena)
	{
		for(Obstacle o : obstacles)
			o.update(arena);
	}

    /**
     * Check if the obstacle is colliding with the snake
     * 
     * @param s         Represents the snake
     * @return true if the obstacle is colliding with the snake, false otherwise
     */
    public boolean isSnakeCollidingWithObstacles(Snake s)
	{
        Quadrado[] snake = s.toArray();
		for(Obstacle o : obstacles)
        {
            for(Quadrado q : snake)
            {
                if(o.obstacle.arePolygonsIntercepting(q) || q.isInside(o.obstacle) || o.obstacle.isInside(q))
                    return true;
            }
        }
        return false;
	}

    
    /** 
     * @param g
     * @param fill
     */
    public void draw(Graphics g, boolean fill)
    {
        for(Obstacle o : obstacles)
        {
            o.draw(g, fill);
        }
    }

	public class Obstacle
	{
		private Poligono obstacle;
		private final boolean isDynamic;
		private final double rotationAngle;
        private final Ponto<?> pointOfRotation;
		
        /**
         * @param obstacle              Represents the polygon representing the obstacle
         * @param isDynamic             Represents a boolean indicating whether the obstacle is dynamic or static
         * @param rotationAngle         Represents the rotation angle of the obstacle
         * @param pointOfRotation       Represents the point around which the obstacle rotates. If null, the centroid of the obstacle will be used
         */
		private Obstacle(Poligono obstacle, boolean isDynamic, double rotationAngle, Ponto<?> pointOfRotation)
		{
			this.obstacle = obstacle;
			this.isDynamic = isDynamic;
			this.rotationAngle = rotationAngle;
            if(pointOfRotation == null)
            {
                double[] coordinates = obstacle.centroide();
                this.pointOfRotation = new Ponto<Double>(coordinates[0], coordinates[1]);
            }
            else this.pointOfRotation = pointOfRotation;
		}

        public Poligono getObstacle() { return this.obstacle; }
        public boolean isDynamic() { return this.isDynamic; }
        public double getRotationAngle() { return this.rotationAngle; }
        public Ponto<?> getPointOfRotation() { return this.pointOfRotation; }

        /**
         * Updates the obstacle in the arena
         * 
         * @param arena     Represents the arena
         */
		private void update(Arena arena)
		{
			if(isDynamic)
            {
                arena.unmark(obstacle, true);
                
                if(pointOfRotation != null)
                    this.obstacle = obstacle.rotate(rotationAngle, pointOfRotation.getX().doubleValue(), pointOfRotation.getY().doubleValue());
                else
                    this.obstacle = obstacle.rotate(rotationAngle);
            }

            arena.mark(obstacle, "O ", true);
		}

        private void draw(Graphics g, boolean fill)
        {
            g.setColor(new Color(255, 128, 0));
            obstacle.draw(g, fill);
        }
	}
}
