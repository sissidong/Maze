import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Keys {
	
	private Image key;
	private Board board;

	public int x,y,width;
	public Keys (short screenData[][], int brd_x, int brd_y, int width) 
    	{
    		 this.x = brd_x * width;
    		 this.y = brd_y * width;
    		 this.width = width;
    	}
    		
    	public boolean findkey (int boyx, int boyy)  //check whether boy find key
    	{
    		if (boyx > (x - 12) && boyx < (x + 12) && boyy > (y - 12) && boyy < (y + 12)) 
    			return true;
    		return false;
    	}
   
    	
    	
	public void draw(Graphics2D g2)  
	{  
		key = new ImageIcon("image/if_Key_132286.png").getImage();
		g2.drawImage (key,this.x, this.y, board);   
	}
	
	
	public void remove(Graphics2D g2) {   g2.clearRect(this.x, this.y, 0, 0 );  }

}
