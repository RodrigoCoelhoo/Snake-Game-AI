package game;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import geofig.Ponto;
import geofig.Quadrado;
import geofig.Retangulo;

public class Snake implements Iterable<Quadrado> {
	private Quadrado head;
	private final int headSize;
	private Queue<Quadrado> tail;
	private int xDirection;
	private int yDirection;

	/**
	 * 
	 * @param x				Represents the x position of the bottom left corner of the snake initial position
	 * @param y				Represents the y position of the bottom left corner of the snake initial position
	 * @param headSize  	Represents the n number of tiles the snake will occupy n x n
	 * @inv headSize > 0
	 * @inv x >= 0
	 * @inv y >= 0
	 */
	public Snake(int x, int y, int headSize)
	{
		if(headSize <= 0) throw new IllegalArgumentException("Head size must be bigger than 0");
		if(x < 0 || y < 0) throw new IllegalArgumentException("Snake initial position must be on the first quadrant");
		
		this.headSize = headSize;
		this.tail = new LinkedList<Quadrado>();
		this.xDirection = 0;
		this.yDirection = 0;

		@SuppressWarnings("unchecked")
		Ponto<Integer>[] p = (Ponto<Integer>[]) new Ponto<?>[4];
		p[0] = new Ponto<Integer>(x,y);
		p[1] = new Ponto<Integer>(x,y + headSize);
		p[2] = new Ponto<Integer>(x + headSize, y + headSize);
		p[3] = new Ponto<Integer>(x + headSize, y);

		this.head = new Quadrado(p);
	}

	public void setXDirection(int x) { this.xDirection = x; }
	public void setYDirection(int y) { this.yDirection = y; }
	public int getXDirection() { return this.xDirection; }
	public int getYDirection() { return this.yDirection; }
	public Quadrado getHead() { return this.head; }
	public Queue<Quadrado> getTail() { return this.tail; }

	/**
	 * Responsible for moving the snake <p>
	 * 
	 * @param canEat	Represents whether the snake can eat or not the food
	 * @param arena		Represents the arena of the game
	 */
	public void move(Arena arena)
	{
		if(getXDirection() == 0 && getYDirection() == 0) return;
		arena.mark(head, "S ", true);
		Quadrado newHead = (Quadrado) head.translate((int) xDirection * headSize, (int) yDirection * headSize);
		arena.mark(newHead, "H ", true);
		tail.add(head);

		this.head = newHead;
	}

	
	/** 
	 * @param arena
	 */
	public void removeTail(Arena arena)
	{
		Quadrado tmp = tail.remove();
		arena.unmark(tmp, false);
	}

	public void moveUp()
	{
		setXDirection(0);
		setYDirection(-1);
	}

	public void moveDown()
	{
		setXDirection(0);
		setYDirection(1);
	}

	public void moveLeft()
	{
		setXDirection(-1);
		setYDirection(0);
	}

	public void moveRight()
	{
		setXDirection(1);
		setYDirection(0);
	}

	/**
	 * Check if the snake can eat the food
	 * 
	 * @param f		Represents the food object
	 * @return true if can eat, false otherwise
	 */
	public boolean canEat(Food<?> f)
	{
		Retangulo foodInvolvingRectangle = f.getFood().calculateInvolvingRectangle();
		return head.isInside(foodInvolvingRectangle);
	}

	/**
	 * Check if the snake is colliding with his own tail
	 * @return true if the snake is colliding with his own tail, false otherwise
	 */
	public boolean isCollidingWithTail()
	{
		Iterator<Quadrado> it = iterator();
		while(it.hasNext())
		{
			Quadrado segment = it.next();
			if(head.isInside(segment)) 
				return true;
		}
		return false;
	}

	public Quadrado[] toArray()
	{
		Quadrado[] result = new Quadrado[tail.size() + 1];
		result[0] = this.head;

		Iterator<Quadrado> it = iterator();
		int count = 1;
		while(it.hasNext())
		{
			result[count++] = it.next();
		}
		return result;
	}

	@Override
    public Iterator<Quadrado> iterator() 
	{
        Stack<Quadrado> stack = new Stack<>();
		Queue<Quadrado> tmp = new LinkedList<>(getTail());
        while (!tmp.isEmpty()) {
            stack.push(tmp.remove());
        }

        return new Iterator<Quadrado>() 
		{
            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Quadrado next() 
			{
				return stack.pop();
            }
        };
	}

	public void draw(Graphics g, boolean fill)
	{
		Iterator<Quadrado> it = iterator();

		g.setColor(new Color(0, 153, 0));
		while(it.hasNext())
		{
			Quadrado segment = it.next();
			segment.draw(g, fill);
		}

		g.setColor(Color.red);
		head.draw(g, fill);
	} 
}
