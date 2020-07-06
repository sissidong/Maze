import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Girl {
	
	private Image girl;
	private Board board;
	public int x,y,width;
	public Girl (short screenData[][], int brd_x, int brd_y, int width) 
	{
		 this.x = brd_x * width;
		 this.y = brd_y * width;
		 this.width = width;
	}
	
    	public boolean save (int boyx, int boyy)  //check whether boy meet girl
    	{
    		if (boyx > (x - 12) && boyx < (x + 12) && boyy > (y - 12) && boyy < y + 12) 
    			return true;
    		else
    			return false;
    	}
    

    	
	public void draw(Graphics2D g2)  
	{  
		girl = new ImageIcon("image/if_Girl_132288.png").getImage();
		g2.drawImage (girl,this.x+1, this.y+1, board);   
	}
	
	
	public void remove(Graphics2D g2) {  g2.clearRect(this.x+1, this.y+1, 0, 0 );}

}
