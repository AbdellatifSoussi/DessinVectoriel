
import java.awt.Color;

public class Ligne extends Forme {
    private Point p1;
	
	public Ligne (Point x, Point y, Color c) {
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Ligne: "+p.getX()+", "+p.getY()+", "+getP1().getX()+", "+getP1().getY();
	}

}
