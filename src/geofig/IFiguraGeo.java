package geofig;
import java.awt.*;

public interface IFiguraGeo {
	boolean equals(Object obj);
	String toString();
	Retangulo calculateInvolvingRectangle();
	void draw(Graphics g, boolean fill);
}
