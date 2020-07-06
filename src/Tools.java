import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Tools implements ToolShape
{
	private Image clock;
	private Image coins;
    public int x,y;

	private Board board;
    	public Tools (short screenData[][],int brd_x, int brd_y, int width) 
    	{
    		this.x= brd_x*width;
    		this.y= brd_y*width;	
    	}
    	
	public void draw(Graphics2D g2, int i)  
	{  
		clock = new ImageIcon("image/if_Clock_132299.png").getImage();
		coins = new ImageIcon("image/if_Coins_132337.png").getImage();
		 if(i<=3)	   
			 g2.drawImage (clock,this.x + 1, this.y + 1, board);
		 else 
			 g2.drawImage (coins,this.x + 1, this.y + 1, board);   	   	
	}
	
	
	public void remove(Graphics2D g2) {  g2.clearRect(x, y, 0, 0 );} 

	public int getx() {   return this.x;    }
	public int gety() {   return this.y;    }

}
