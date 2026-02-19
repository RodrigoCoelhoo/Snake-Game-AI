package game;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import geofig.Poligono;
import geofig.Ponto;
import geofig.Quadrado;
import geofig.Retangulo;

public class Arena 
{
	private final int arenaSize;
	private final int xUnits;
	private final int yUnits;
	private final int tileSize;
	private final Tile[][] grid;
	private final Retangulo arenaBounds;
	private final boolean rasterizacao;
	private boolean showHitboxs = true;

	/**
	 * @param arenaDim			Represents the dimension of the arena in tiles
	 * @param tileSize			Represents the size of each tile in pixels
	 * @param rasterizacao		Represents the way objects will be rendered at the arena, if true then  
	 * @inv arenaDim > 0
	 * @inv tileSize > 0
	 * @pre arenaDim % SnakeHeadSize == 0
	 */
	public Arena(int arenaDim, int tileSize, boolean rasterizacao)
	{
		if(arenaDim <= 0) throw new IllegalArgumentException("Arena dimensions must be greater than 0");
		if(tileSize <= 0) throw new IllegalArgumentException("Tile size must be greater than 0");

		this.arenaSize = arenaDim * tileSize;
		this.xUnits = arenaSize / tileSize;
		this.yUnits = arenaSize / tileSize;
		this.tileSize = tileSize;
		this.rasterizacao = rasterizacao;

		List<Ponto<Integer>> pontoList = new ArrayList<>();
		pontoList.add(new Ponto<>(0, 0));
		pontoList.add(new Ponto<>(arenaSize, 0));
		pontoList.add(new Ponto<>(arenaSize, arenaSize));
		pontoList.add(new Ponto<>(0, arenaSize));

		@SuppressWarnings("unchecked")
		Ponto<Integer>[] pontoArray = pontoList.toArray(new Ponto[0]);

		this.arenaBounds = new Retangulo(pontoArray);
		this.grid = new Tile[xUnits][yUnits];
		for(int i = 0; i < xUnits; i++)
		{
			for(int j = 0; j < yUnits; j++)
			{
				this.grid[i][j] = new Tile(i*tileSize, j*tileSize);
			}
		}
	}

	/**
	 * Check if the snake is outside the arena bounds
	 * @param s		Represents the snake
	 * @return true if the snake is outside, false otherwise
	 */
	public boolean isSnakeOutside(Snake s)
	{
		return !arenaBounds.isInside(s.getHead());
	}

	/**
	 * Calculates the limits of the area of a polygon based on the lower left corner and upper right corner
	 * @param p		Represents the left bottom corner [0] and top right corner [1]
	 * @return a matrix representing the limits {{x, xLimit}, {y, yLimit}}
	 */
	private int[][] getLimits(Ponto<?>[] p)
	{
		int x = (p[0].getX().intValue() - (p[0].getX().intValue()%getTileSize()))/getTileSize();
		int xLimit = (p[1].getX().intValue())/getTileSize();
		xLimit += (p[1].getX().intValue() % getTileSize() > 0) ? 1 : 0;
		
		int y = (p[0].getY().intValue() - (p[0].getY().intValue()%getTileSize()))/getTileSize();
		int yLimit = (p[1].getY().intValue())/getTileSize();
		yLimit += (p[1].getY().intValue() % getTileSize() > 0) ? 1 : 0;
		
		// Handle error cases
		if(xLimit > getXUnits()) xLimit = getXUnits();
		if(yLimit > getYUnits()) yLimit = getYUnits();
		if(x < 0) x = 0;
		if(y < 0) y = 0;

		return new int[][] {{x, xLimit}, {y, yLimit}};
	}

	/**
	 * Mark the area of the arena occupied by the given polygon
	 * @param obj			Represents the polygon that will be added to the arena
	 * @param str			Represents the character of this type of object (Food[F], Snake[H/S], Object[O])
	 * @param isOccupied	Represents whether the area will be marked as occupied or not (Helps in food random generation)
	 */
	public void mark(Poligono obj, String str, boolean isOccupied)
	{
		Ponto<?>[] p = obj.minMax();
		int[][] range = getLimits(p);
		Tile[][] grid = getGrid();

		for(int j = range[1][0]; j < range[1][1]; j++)
		{
			for(int i = range[0][0]; i < range[0][1]; i++)
			{
				if(grid[i][j].getTile().arePolygonsIntercepting(obj) || obj.isInside(grid[i][j].getTile()) || grid[i][j].getTile().isInside(obj))
				{
					grid[i][j].setIsOccupied(isOccupied);

					if(this.rasterizacao) // Preenchido
					{
						grid[i][j].setMark(str); 
					}
					else // Contorno
					{
						boolean isContour = false;
                
						// Verifica vizinhos nas direções norte, sul, leste e oeste
						int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
						
						for (int[] dir : directions) {
							int ni = i + dir[0];
							int nj = j + dir[1];
							
							// Verifica se o vizinho do tile atual está dentro dos limites
							if (ni < 0 || ni >= range[0][1] || nj < 0 || nj >= range[1][1]) {
								isContour = true; // Se o vizinho está fora dos limites, então este tile é contorno
								break;
							}
							
							// Verifica se o vizinho não faz parte do polígono
							if (!grid[ni][nj].getTile().arePolygonsIntercepting(obj) && !obj.isInside(grid[ni][nj].getTile()) && !grid[ni][nj].getTile().isInside(obj)) {
								isContour = true; // Se o vizinho não é parte do polígono, então é contorno
								break;
							}
						}
						
						if (isContour) {
							grid[i][j].setMark(str);
						} else {
							grid[i][j].setMark(". ");
						}
					}
				}
			}
		}
	}

	/**
	 * Unmark the area of the arena occupied by the given polygon
	 * @param obj			Represents the polygon that will be removed from the arena
	 * @param isOccupied	Represents weather the area will stay occupied after the object is removed or not
	 */
	public void unmark(Poligono obj, boolean isOccupied)
	{
		mark(obj, ". ", isOccupied);
	}

	/**
	 * Calculates the available areas based on the size of the snake
	 * @param size Represents the size of the snake (n tiles)*tileSize
	 * @return an array with points that represents the available areas on the arena
	 */
	public Ponto<Integer>[] availableArea(int size)
	{
		List<Ponto<Integer>> areaList = new ArrayList<>();
		int units = size / getTileSize();
		int x = getXUnits() / units;
		int y = getYUnits() / units;

		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				if(isAreaAvailable(i*units, j*units, units)) 
					areaList.add(new Ponto<>(i*units, j*units));
			}
		}

		@SuppressWarnings("unchecked")
		Ponto<Integer>[] areaArray = areaList.toArray((Ponto<Integer>[]) new Ponto<?>[areaList.size()]);

		return areaArray;
	}

	/**
	 * Check if an area is available in the arena
	 * @param x		Represents x coordinate of the bottom left corner of this area 
	 * @param y		Represents y coordinate of the bottom left corner of this area
	 * @param size	Represents the size of the area
	 * @return true if the area is available, false otherwise
	 */
	private boolean isAreaAvailable(int x, int y, int size)
	{
		Tile[][] grid = getGrid();
		for(int i = x; i < x + size; i++)
		{
			for(int j = y; j < y + size; j++)
			{
				if(grid[i][j].isOccupied())
					return false;
			}
		}
		return true;
	}

	
	/** 
	 * @param g
	 * @param fill
	 */
	public void draw(Graphics g)
	{
		Tile[][] grid = getGrid();

		if(showHitboxs) {
			for(int i = 0; i < getXUnits(); i++)
			{
				for(int j = 0; j < getYUnits(); j++)
				{
					g.setColor(Color.pink);
					if(grid[i][j].getMark().equals(". ")) 
						g.setColor(Color.gray);
					
					if(grid[i][j].isOccupied())
						grid[i][j].draw(g, false);
				}
			}
		}
		
		g.setColor(Color.GRAY);
		g.drawRect(0, 0, getArenaSize() - 1, getArenaSize() - 1);
	}	

	/**
	 * Prints the state of the arena to the console
	 */
	public void print()
	{
		for(int i = 0; i < getYUnits(); i++)
		{
			for(int j = 0; j < getXUnits(); j++)
			{
				System.out.print(grid[j][i].getMark());
			}
			System.out.println("");
		}
	}

	public int getArenaSize() { return this.arenaSize; }
	public int getXUnits() { return this.xUnits; }
	public int getYUnits() { return this.yUnits; }
	public int getTileSize() { return this.tileSize; }
	public Tile[][] getGrid() { return this.grid; }
	public boolean getRasterizacao() { return this.rasterizacao; }
	public void setShowHitboxs(boolean showHitboxs) { this.showHitboxs = showHitboxs; }

	public class Tile 
	{
		private final Quadrado tile;
		private String mark;
		private boolean isOccupied;
	
		Tile(int x, int y) {
			String str = ""+ x + " " + y + " " + (x + tileSize) + " " + y  + " " + (x + tileSize) + " " + (y + tileSize) + " " + x + " " + (y + tileSize);
			this.tile = new Quadrado(str);
			this.mark = ". ";
			this.isOccupied = false;
		}

		public void draw(Graphics g, boolean fill)
		{
			if(mark.equals(". "))
				g.setColor(Color.gray);
			else if(mark.equals("F "))
				g.setColor(Color.cyan);
			else 
				g.setColor(Color.orange);
			
			tile.draw(g, fill);
		}

		public Quadrado getTile() { return this.tile; }
		public String getMark() { return this.mark; }
		public void setMark(String mark) { this.mark = mark; }
		public boolean isOccupied() { return this.isOccupied; }
		public void setIsOccupied(boolean isOccupied) { this.isOccupied = isOccupied; }
	}
}