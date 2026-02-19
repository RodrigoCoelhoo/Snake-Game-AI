package geofig;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.awt.*;

public class Poligono implements IFiguraGeo{	
	protected final Ponto<?>[] pontos;

	/**
	 * @author Rodrigo Coelho 79786
	 * @param pontos representing the points of the polygon
	 * @inv A polygon must contain at least 3 points
	 * @inv 3 consecutive points can't be collinear
	 * @inv There can't be intersections between edges
	 */
	public Poligono(Ponto<?>[] pontos) 
	{
		if(!validatePolygon(pontos)) throw new IllegalArgumentException("Poligono:vi");

		this.pontos = pontos;
	}

	/**
	 * @author Rodrigo Coelho 79786
	 * @param s representing the points of the polygon
	 * @inv A polygon must contain at least 3 points
	 * @inv 3 consecutive points can't be collinear
	 * @inv There can't be intersections between edges
	 * @pre The given string must have the format "n p1.x p1.y p2.x p2.y ..." or "p1.x p1.y p2.x p2.y ..."
	 */
	public Poligono(String s) 
	{
		String[] temp;
		temp = s.split(" ");
		int nPoints = temp.length;
		if(nPoints % 2 != 0) nPoints--;
		nPoints /= 2;
		@SuppressWarnings("unchecked")
		Ponto<Double>[] pontos = (Ponto<Double>[]) new Ponto<?>[nPoints];
		int count = 0;
		int index = temp.length % 2 != 0 ? 1 : 0;
		while(count != nPoints)
		{
			Ponto<Double> p = new Ponto<>(Double.parseDouble(temp[index]), Double.parseDouble(temp[index + 1]));
			pontos[count++] = p;
			index += 2;
		}

		if(!validatePolygon(pontos)) throw new IllegalArgumentException("Poligono:vi");

		this.pontos = pontos;
	}

	
	/** 
	 * @return Ponto<?>[]
	 */
	public Ponto<?>[] getPoints()
	{
		return this.pontos;
	}

	/**
	 * Check if all invariants of class Poligno are satisfied
	 * @param pontos	Array of objects of class Ponto
	 * @return true if pass all conditions, false otherwise
	 */
	private static boolean validatePolygon(Ponto<?>[] pontos)
	{
		if(pontos.length <= 2)
			return false;
		
		if(Ponto.areThreeConsecutivePointsCollinear(pontos))
		{
			return false;
		}

		if(!validatePolygonIntersections(pontos))
			return false;

		return true;
	}


	/**
	 * Checks if the edges of the polygon intersect with each other
	 * @param p		Array of objects of class Ponto
	 * @return true if there is no intersection, false otherwise
	 */
	private static boolean validatePolygonIntersections(Ponto<?>[] p) 
	{
		SegmentoReta[] sr = new SegmentoReta[p.length];
	
		sr[0] = new SegmentoReta(p[0], p[1]);
		sr[1] = new SegmentoReta(p[1], p[2]);
	
		boolean condition = false;
		for(int i = 2; i < p.length - 1; i++) 
		{
			SegmentoReta reta = new SegmentoReta(p[i], p[(i + 1) % p.length]);

			for(int j = 0; j < (i - 1); j++) 
			{
				condition = SegmentoReta.intersect(sr[j], reta) || SegmentoReta.isSegmentOnSegment(sr[j], reta);

				if(condition) {
					return false;
				}
			}
			sr[i] = reta;
		}
	
		SegmentoReta reta = new SegmentoReta(p[p.length - 1], p[0]);
		for(int i = 1; i < sr.length - 2; i++) {
			condition = SegmentoReta.intersect(reta, sr[i]);
			if(condition) return false;
		}

		return true;
	}

	/**
	 * @return an array of objects from class SegmentoReta containing the edges of the Polygon
	 */
	public SegmentoReta[] polygonEdges()
	{
		SegmentoReta[] result = new SegmentoReta[pontos.length];
		for(int i = 0; i < pontos.length; i++)
		{
			result[i] = new SegmentoReta(pontos[i], pontos[(i + 1) % pontos.length]);
		}
		return result;
	}

	/**Check if a edge intersect the polygon
	 * @param edge		Object from class SegmentoReta
	 * @return true if the edge intersects the polygon, false otherwise
	 */
	public boolean edgeIntersectsPolygon(SegmentoReta edge)
	{
		boolean result = false;
		SegmentoReta[] polygonEdges = polygonEdges();

		for(int j = 0; j < polygonEdges.length; j++)
		{
			result = SegmentoReta.intersect(edge, polygonEdges[j]) && !polygonEdges[j].isPointOnSegment(edge.getP2());
			if(result && !SegmentoReta.isSegmentOnSegment(polygonEdges[j], edge))
			{
				return result;
			}
			
			result = false;
		}
		return result;
	}

	/**
	 * Checks if the given point is on the perimter of the polygon
	 * @param p		Object from class Ponto
	 * @return true if the point is on the perimeter, false otherwise
	 */
	public boolean isPointOnPolygon(Ponto<?> p)
	{
		SegmentoReta[] polygonEdges = polygonEdges();
		for(int i = 0; i < polygonEdges.length; i++)
		{
			boolean result = polygonEdges[i].isPointOnSegment(p);
			if(result) return result;
		}
		return false;
	}

	/**
	 * Calculates the involving rectangle of a polygon
	 * @param p		Array of type Ponto
	 * @return an array of SegmentoReta representing the segments of the rectangle
	 */
	public Retangulo calculateInvolvingRectangle() 
	{
		Ponto<?>[] p = getPoints();

        int minX = Integer.MAX_VALUE;
        int maxX = -1;
        int minY = Integer.MAX_VALUE;
        int maxY = -1;

        for (Ponto<?> ponto : p) 
		{
			minX = Math.min(minX, ponto.getX().intValue());
			maxX = Math.max(maxX, ponto.getX().intValue());
			minY = Math.min(minY, ponto.getY().intValue());
			maxY = Math.max(maxY, ponto.getY().intValue());
        }
		Ponto<?>[] points = new Ponto[4];

		points[0] = new Ponto<>(minX, minY);
		points[1] = new Ponto<>(maxX, minY);
		points[2] = new Ponto<>(maxX, maxY);
		points[3] = new Ponto<>(minX, maxY);

        return new Retangulo(points);
    }

	/**
	 * Checks if a point is inside the polygon
	 * @param p		Object from class Ponto
	 * @return true if the point is inside the polygon, false if the point is outside or on the perimeter of the polygon
	 * @see https://www.youtube.com/watch?v=RSXM9bgqxJM&ab_channel=Insidecode
	 */
	public boolean isPointInsidePolygon(Ponto<?> p)
	{
		int count = 0;
		SegmentoReta[] polygonEdges = polygonEdges();
		double x1,y1,x2,y2;
		
		for(int i = 0; i < polygonEdges.length; i++)
		{
			if(polygonEdges[i].getP1().getY().doubleValue() < polygonEdges[i].getP2().getY().doubleValue())
			{
				x1 = polygonEdges[i].getP1().getX().doubleValue();
				y1 = polygonEdges[i].getP1().getY().doubleValue();
				x2 = polygonEdges[i].getP2().getX().doubleValue();
				y2 = polygonEdges[i].getP2().getY().doubleValue();
			}
			else
			{
				x1 = polygonEdges[i].getP2().getX().doubleValue();
				y1 = polygonEdges[i].getP2().getY().doubleValue();
				x2 = polygonEdges[i].getP1().getX().doubleValue();
				y2 = polygonEdges[i].getP1().getY().doubleValue();
			}

			if((p.getY().doubleValue() < y1) != (p.getY().doubleValue() < y2) && (p.getX().doubleValue() < x1 + ((p.getY().doubleValue()-y1)/(y2-y1))*(x2-x1)))
				count++;
		}
		
		return count % 2 == 1 || isPointOnPolygon(p);
	}

	/**
	 * Checks if two polygons A and B intersect with each other
	 * @param p1		Array of type Ponto representing the points of the polygon A
	 * @param p2		Array of type Ponto representing the points of the polygon B
	 * @return true if the polygons A and B intersect, false otherwise
	 */
	public boolean arePolygonsIntercepting(Poligono that) 
	{
		SegmentoReta[] p2Edges = that.polygonEdges();

		for(int i = 0; i < p2Edges.length; i++)
		{
			if(this.edgeIntersectsPolygon(p2Edges[i])) return true;
		}

		return false;
	}

	/**
	 * Checks if a polygon is entirely inside this polygon.
	 * 
	 * @param that		Represents a polygon
	 * @return true if that is inside the current {this} polygon, false otherwise
	 */
	public boolean isInside(Poligono that)
	{
		Ponto<?>[] thatPoints = that.getPoints();
		for(Ponto<?> p : thatPoints)
		{
			if(!this.isPointInsidePolygon(p)) return false;
		}

		return true;
	}

	/**
	 * @return Perimeter of a polygon
	 */
	public double perimeter()
	{
		double result = 0;
		for(int i=0; i< pontos.length; i++)
		{
			result += pontos[i].dist(pontos[(i+1) % pontos.length]);
		}
		return result;
	}

	@Override
	public String toString()
	{
		return "Poligono de " + pontos.length + " vertices: " + Arrays.toString(getPoints());
	}

	@Override
	public boolean equals(Object that)
	{
		Poligono thatP = (Poligono) that;
		Ponto<?>[] thisPoints = getPoints();
		Ponto<?>[] thatPoints = thatP.getPoints();

		if(thisPoints.length != thatPoints.length) return false;

		Queue<Ponto<?>> queueThisPoints = new LinkedList<>();
		Stack<Ponto<?>> stackThisPoints = new Stack<>();

		for(Ponto<?> p : thisPoints)
		{
			stackThisPoints.add(p);
			queueThisPoints.add(p);
		}

		Ponto<?> firstStackElement = thisPoints[thisPoints.length - 1];
		Ponto<?> firstQueueElement = thisPoints[0];

		int indexStack = -1;
		int indexQueue = -1;
		for(int i = 0; i < thatPoints.length; i++)
		{
			if(indexStack != -1 && indexQueue != -1) break;

			if(thatPoints[i].equals(firstQueueElement))
				indexQueue = i;
			
			if(thatPoints[i].equals(firstStackElement))
				indexStack = i;
		}

		if(indexQueue != -1)
		{
			while(!queueThisPoints.isEmpty())
			{
				if(!(queueThisPoints.remove()).equals(thatPoints[indexQueue % thatPoints.length])) 
					break;

				indexQueue++;
			}
			if(queueThisPoints.isEmpty()) return true;
		}

		if(indexStack != -1)
		{
			while(!stackThisPoints.isEmpty())
			{
				if(!(stackThisPoints.pop()).equals(thatPoints[indexStack % thatPoints.length])) 
					break;

				indexStack++;
			}
			if(stackThisPoints.isEmpty()) return true;
		}

		return false;
	}

	/**
	 * Created by chatGPT in order to be able to override equals
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		Ponto<?>[] points = this.getPoints();

		for (Ponto<?> point : points) {
			result = prime * result + ((point == null) ? 0 : point.hashCode());
		}

		return result;
	}

	/** Calculate the centroide of the polygon
	 * @return an array of double that represents the centroide of the polygon 0:x 1:y
	 */
	public double[] centroide() 
	{
		Ponto<?>[] points = this.getPoints();
		int sumX = 0, sumY = 0;
	
		for (int i = 0; i < points.length; i++) {
			sumX += points[i].getX().doubleValue();
			sumY += points[i].getY().doubleValue();
		}
	
		double[] result = new double[2];
		result[0] = ((double) sumX / points.length);
		result[1] = ((double) sumY / points.length);
	
		return result;
	}

	/**
	 * Calculate the rotation of the polygon based on the centroide of the polygon
	 * @param degrees representing the angle of rotation
	 * @return an object from class Poligono representing the rotated polygon
	 */
	public Poligono rotate(double degrees)
	{
		double[] center = centroide();
		Ponto<?>[] points = this.getPoints();
		Ponto<?>[] newPoints = new Ponto[points.length];

		for(int i = 0; i < points.length; i++)
		{
			newPoints[i] = points[i].rotate(degrees, center[0], center[1]);
		}

		return createInstance(newPoints);
	}

	/**
	 * Calculate the rotation of the polygon based on the given center
	 * @param degrees 	representing the angle of rotation
	 * @param centerX	representing x coordinate of the center of rotation
	 * @param centerY	representing y coordinate of the center of rotation
	 * @return an object from class Poligono representing the rotated polygon
	 */
	public Poligono rotate(double degrees, double centerX, double centerY)
	{
		Ponto<?>[] points = this.getPoints();
		Ponto<?>[] newPoints = new Ponto[points.length];

		for(int i = 0; i < points.length; i++)
		{
			newPoints[i] = points[i].rotate(degrees, centerX, centerY);
		}
		
		return createInstance(newPoints);
	}

	protected Poligono createInstance(Ponto<?>[] points) {
        return new Poligono(points);
    }

	/**
	 * Translate the polygon by the specified displacements in the x and y directions
	 * @param dx	representing the displacement in the x direction
	 * @param dy	representing the displacement in the y direction
	 * @return a new polygon representing the result of the translation
	 */
	public Poligono translate(int dx, int dy)
	{
		Ponto<?>[] points = this.getPoints();
		Ponto<?>[] newPoints = new Ponto[points.length];

		for(int i = 0; i < points.length; i++)
		{
			newPoints[i] = new Ponto<>(points[i].getX().doubleValue() + dx, points[i].getY().doubleValue() + dy);
		}

		return createInstance(newPoints);
	}

	/**
	 * Translate the polygon to the given centroide
	 * @param p	representing the new centroide
	 * @return a new polygon representing the result of the translation
	 */
	public Poligono translate(Ponto<?> p)
	{
		double[] centroide = this.centroide();
		int dx = (int) Math.round(p.getX().doubleValue() - centroide[0]);
		int dy = (int) Math.round(p.getY().doubleValue() - centroide[1]);
		return translate(dx, dy);
	}

	/**
	 * Calculate the left bottom corner and the right top corner of the polygon
	 * @return an array of Ponto<?> with bottom left corner point as a[0] and top right corner as a[1]
	 */
	public Ponto<?>[] minMax()
	{
		Ponto<?>[] result = new Ponto[2];

		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;

		for(Ponto<?> p : this.getPoints())
		{
			double x = p.getX().doubleValue();
			double y = p.getY().doubleValue();

			if(x < minX) minX = x;
			if(x > maxX) maxX = x;
			if(y < minY) minY = y;
			if(y > maxY) maxY = y;
		}

		result[0] = new Ponto<>(minX, minY);
		result[1] = new Ponto<>(maxX, maxY);
		return result;
	}

	public void draw(Graphics g, boolean fill)
	{
		Ponto<?>[] points = getPoints();

		if(fill)
		{
			int[] x = new int[points.length];
			int[] y = new int[points.length];

			for(int i = 0; i < points.length; i++)
			{
				x[i] = points[i].getX().intValue();
				y[i] = points[i].getY().intValue();
			}

			Color c = g.getColor();
			g.fillPolygon(x, y, points.length);
			g.setColor(Color.BLACK);
			g.drawPolygon(x, y, points.length);
			g.setColor(c);
		}
		else
		{
			for(int i = 0; i < points.length; i++)
			{
				g.drawLine(points[i].getX().intValue(),points[i].getY().intValue(),points[(i+1) % points.length].getX().intValue(),points[(i+1) % points.length].getY().intValue());
			}
		}
	}
}