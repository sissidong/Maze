
import java.awt.Graphics2D;

public interface GhostShape
{	
	boolean contain (int boyx, int boyy);
	void move ();
	void draw(Graphics2D g2, int i);
}