import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Ghost implements GhostShape{

		private Image ghost;
		
	    public int x,y,width;
	    private int dy=1;

		private short[][] screenData;
		private Board board;

	    	public Ghost (short screenData[][], int brd_x, int brd_y, int width) 
	    	{
	    		 this.screenData = screenData;
	    		 this.x = brd_x * width;
	    		 this.y = brd_y * width;
	    		 this.width = width;

	    	}
	    	
	    	public void move ()
	    	{
	    		int px = x / width;
	    		int py;
	    		short ch1,ch2;
	    		int speed = 6;
	    		   
	    		if (y % width == 0)  //if ghost against wall change direction. never stop
	    		{
	    		    py = y / width;
	    	    	  	ch1 = screenData[py][px];
	    	    	  	ch2 = screenData[py+1][px];

	    	    	  	if(ch1==2 || ch1==3) 
	    	    	  		dy=1;
	    	    	  	if(ch2==2 || ch2==3) 
	    	    	  		dy=-1; 
	    	    	}
	    		y = y + (dy * speed);
	    	}
	    	
	    	
	    	public boolean contain (int boyx, int boyy)  //check whether boy meet ghost
	    	{
	    		if (boyx > (this.x - 12) && boyx < (this.x + 12) && boyy > (this.y - 12) && boyy < this.y + 12) 
	    			return true;
	    		return false;
	 	 }
	    

		public void draw(Graphics2D g2, int i)  
		{ 
			ghost = new ImageIcon("image/Ghost1.gif").getImage(); 
			g2.drawImage (ghost,this.x+1, this.y + 1, board);   
		}

}
