import org.junit.jupiter.api.*;

import data.Obstacles;
import game.Arena;
import game.Snake;
import geofig.Poligono;
import geofig.Triangulo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.List;

class ObstaclesTest 
{
    
    /** 
     * @throws IOException
     */
    @Test
    public void testObstacles() throws IOException 
	{
        String filename = "obstacles_test.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) 
		{
            writer.write("Class name\tConstructor Argument\tisDynamic\tPoint of rotation\tAngle of rotation\n");
            writer.write("Triangulo\t100 100 222 100 150 195\tfalse\tnull\t0\n");
            writer.write("Poligono\t800 500 900 500 900 600 800 600\ttrue\t900 500\t25\n");
            writer.write("Poligono\t450 300 500 400 600 450 500 500 450 600 400 500 300 450 400 400\ttrue\tnull\t15\n");
        }

        Obstacles obstacles = new Obstacles(filename);
        
        List<Obstacles.Obstacle> loadedObstacles = obstacles.getObstacles();
        assertNotNull(loadedObstacles);
        assertEquals(3, loadedObstacles.size());
        
        Obstacles.Obstacle obstacle1 = loadedObstacles.get(0);
        assertTrue(obstacle1.getObstacle() instanceof Triangulo);
        assertFalse(obstacle1.isDynamic());
        assertEquals(0, obstacle1.getRotationAngle());
        
        Obstacles.Obstacle obstacle2 = loadedObstacles.get(1);
        assertTrue(obstacle2.getObstacle() instanceof Poligono);
        assertTrue(obstacle2.isDynamic());
        assertEquals(25, obstacle2.getRotationAngle());

        File file = new File(filename);
        file.delete();
    }

    @Test
    public void testCollision() throws IOException
    {
        String filename = "obstacles_test.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) 
		{
            writer.write("Class name\tConstructor Argument\tisDynamic\tPoint of rotation\tAngle of rotation\n");
            writer.write("Triangulo\t0 50 50 50 50 75\tfalse\tnull\t0\n");
            writer.write("Retangulo\t50 0 75 0 75 50 50 50\tfalse\tnull\t0\n");
        }

        
        Obstacles obstacles = new Obstacles(filename);

        /**
         * S    .    O    .
         * .    .    O    .
         * O    O    .    .
         * .    O    .    .
         */
        
        Arena a = new Arena(100, 25, false);
        Snake s = new Snake(0, 0, 25);
        assertEquals(false, obstacles.isSnakeCollidingWithObstacles(s));

        s.moveDown();
        s.move(a);
        s.removeTail(a);
        assertEquals(false, obstacles.isSnakeCollidingWithObstacles(s));

        s.moveRight();
        s.move(a);
        s.removeTail(a);
        assertEquals(false, obstacles.isSnakeCollidingWithObstacles(s));
        
        s.move(a);
        s.removeTail(a);
        assertEquals(true, obstacles.isSnakeCollidingWithObstacles(s));

        s.moveDown();
        s.move(a);
        s.removeTail(a);
        assertEquals(false, obstacles.isSnakeCollidingWithObstacles(s));

        s.moveLeft();
        s.move(a);
        s.removeTail(a);
        assertEquals(true, obstacles.isSnakeCollidingWithObstacles(s));

        File file = new File(filename);
        file.delete();
    }
}