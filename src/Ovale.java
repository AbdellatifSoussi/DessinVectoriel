
import java.awt.Color;

public class Ovale extends Forme{
	private Point p1;
	
	public Ovale (Point x, Point y, Color c) {
		this.couleur = c;
		this.p = x;
		this.setP1(y);
	}

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}
	
	public String toString () {
		return "Ovale "+p.getX()+", "+p.getY()+", "+getP1().getX()+", "+getP1().getY();
	}

}
