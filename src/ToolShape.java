import java.awt.Graphics2D;

public interface ToolShape
{
	void draw(Graphics2D g2, int i); 
	void remove(Graphics2D g2);
	int getx();
	int gety();

}




