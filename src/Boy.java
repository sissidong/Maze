
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Boy 
{
	private Board board;
	private Image boy,both;
    public int view_dx, view_dy, req_dx, req_dy;
    public int BLOCK_SIZE = Board.BLOCK_SIZE;
    public int x,y,dx,dy;
	private short[][] screenData;
	

    	public Boy (short screenData[][], int brd_x, int brd_y, int view_dx, int view_dy, 
    			int req_dx, int req_dy, int dx, int dy,int width) 
    	{
    		this.screenData=screenData;
    		this.x= brd_x*width;
    		this.y= brd_y*width;
    		this.view_dx=view_dx;
    		this.view_dy=view_dy;
    		this.req_dx = req_dx;
    		this.req_dy = req_dy;
    		this.dx = dx;
    		this.dy = dy;
    	}
 
    	public void move()
    	{
        	int px, py;
        short ch1,ch2,ch3;
  
        if (req_dx != 0 || req_dy != 0) 
        {
                px = x / BLOCK_SIZE;
                py = y / BLOCK_SIZE;
                
                ch1 = screenData[py][px];
                ch2 = screenData[py][px+1];
                ch3 = screenData[py+1][px];

                if(req_dx==-1 && req_dy==0) 		// stop when against left wall
                {
                		if(ch1==1 || ch1==3)
                			req_dx=0;
                }
                if(req_dx==1 && req_dy==0) 		// stop when against right wall
                {
                		if(ch2==1 || ch2==3)
                			req_dx=0;
                }
                if(req_dx==0 && req_dy==1)		// stop when against down wall
                {
            			if(ch3==2 || ch3==3) 
            				req_dy=0;
                }
                if(req_dx==0 && req_dy==-1) 		// stop when against up wall
                {
            			if(ch1==2 || ch1==3) 
            				req_dy=0;
                }
        		}
        		dx=req_dx;
          	dy=req_dy;
          	req_dx=0;
          	req_dy=0;

          	x=x+dx*24;
          	y=y+dy*24;

    	}
	


	 public void draw(Graphics2D g2)
	 {
		 boy = new ImageIcon("image/if_Boy_132328.png").getImage();
		 g2.drawImage (boy,this.x + 1, this.y + 1, board);

	 }
	 
	 public void removeboy(Graphics2D g2) {  g2.clearRect(this.x+1, this.y+1, 0, 0 );}
	 
	 public void drawboth(Graphics2D g2)  
	 {  
		both = new ImageIcon("image/if_Users_132253.png").getImage();
		g2.drawImage (both,this.x+1, this.y+1, board);   
	 }
	
	 
	 public boolean findexit()  					//check whether boy and girl find the exit
	 {
		if ( x/BLOCK_SIZE==29 && y/BLOCK_SIZE==24 ) 
	    		return true; 
		return false;	 
	 }
	 
	 
	 public boolean collect (int toolx, int tooly)  //check whether boy collect tools
	 {
    		if (toolx > (x - 12) && toolx < (x + 12) && tooly > (y - 12) && tooly < y + 12) 
    			return true;
    		return false;
	 }
	 	
}