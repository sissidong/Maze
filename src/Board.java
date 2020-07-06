import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel
{
    private final static int N_BLOCKS = 30;
    public final static int BLOCK_SIZE = 24; 
    private final static int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final static int BORDER_SIZE = 25;
  
    public int score ;
    public int timeCtr; 
    private int verticalwall=1;
    private int horizontalwall=2;
    private int leftupwall=3;
	
    private Image coin =new ImageIcon("image/if_Coins_132337.png").getImage();
    private Image alarm =new ImageIcon("image/if_Alarm_132336.png").getImage();
    private Image exit =new ImageIcon("image/if_Exit_132316.png").getImage();

    private boolean inGame=false;
    private boolean dying ;
    private boolean playagain;
    private boolean finished;

    private boolean findkey[]= {false,false};
    private boolean findtool[]= {false,false,false,false,false,false,false,false};
    private boolean showgirl;
    public boolean both;

    private short[][] screenData;
    private Timer timer;
    private Boy boy;
    private Girl girl;
    private Keys key1,key2;
    private ArrayList<GhostShape> ghost;
    private ArrayList<ToolShape> tools;
    
 
    private final short map[][] =     // game map without exit
	    	{	
	    			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	    			{0,3,2,2,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,2,3,1},		
	    			{0,1,3,0,3,2,2,2,1,3,2,0,3,2,2,2,2,2,1,1,3,2,2,1,3,2,2,1,1,1},	
	    			{0,1,2,2,0,3,2,2,0,3,1,1,1,3,2,2,2,1,1,1,1,2,1,1,1,2,2,0,1,1},
	    			{0,1,3,2,2,1,3,2,2,0,1,1,1,2,2,2,0,1,1,0,1,1,2,0,1,3,2,2,0,1},
	    			{0,0,1,3,2,0,1,3,2,3,0,1,2,2,2,2,1,1,2,2,0,2,2,2,1,1,3,1,1,1},
	    			{0,1,1,1,3,1,1,2,1,1,2,2,2,2,2,2,0,2,2,2,2,2,2,1,1,1,1,2,0,1},
	    			{0,3,1,1,1,1,1,1,1,2,2,2,2,3,2,2,2,2,2,2,2,0,3,1,1,1,2,2,1,1},
	    			{0,1,1,1,1,0,2,0,1,1,2,2,2,2,2,2,2,2,2,3,2,1,1,2,0,2,1,1,1,1},
	    			{0,1,1,1,2,2,2,2,0,1,3,2,2,2,2,2,2,2,2,0,1,1,1,3,2,2,0,1,1,1},
	    			{0,1,1,2,2,2,1,3,2,0,1,2,2,2,2,2,2,2,2,1,1,1,1,1,3,2,0,1,1,1},
	    			{0,1,2,2,2,1,1,1,3,2,2,2,2,2,2,2,2,2,1,1,1,2,0,1,2,2,2,0,1,1},
	    			{0,1,3,2,1,0,1,1,1,3,2,2,2,2,2,2,2,1,1,0,1,3,0,2,2,2,1,1,1,1},
	    			{0,3,0,1,1,1,1,1,1,1,2,2,2,2,2,2,2,0,1,1,1,1,3,3,2,2,1,1,1,1},
	    			{0,1,2,0,1,1,1,1,1,2,2,2,2,1,3,3,2,2,0,1,1,1,1,1,2,1,1,1,1,1},
	    			{0,1,3,1,1,1,1,1,0,3,3,2,1,1,1,3,2,2,2,0,1,1,1,3,1,1,1,0,1,1},
	    			{0,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,3,0,2,2,0,1,1,1,1,1,2,2,0,1},
	    			{0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,3,2,2,2,0,0,1,1,2,1,2,1,1},
	    			{0,1,1,1,1,2,0,1,1,1,1,1,2,2,0,1,1,1,3,1,1,3,1,1,1,1,1,3,0,1},
	    			{0,1,1,1,0,3,1,1,1,1,1,2,2,2,2,0,1,1,1,1,1,1,1,1,1,1,1,2,1,1},
	    			{0,1,1,2,2,0,1,1,1,1,2,2,2,2,2,2,0,1,1,1,1,0,1,1,2,0,2,1,1,1},
	    			{0,1,0,3,2,2,0,1,1,1,3,2,2,1,3,2,2,0,1,0,2,2,0,2,2,2,1,1,1,1},
	    			{0,3,2,0,3,2,0,1,1,1,1,2,1,1,1,3,2,2,0,1,3,2,2,2,2,1,1,1,1,1},
	    			{0,1,3,1,1,2,2,0,1,1,2,2,0,1,0,1,3,2,2,2,0,2,2,2,1,1,1,1,3,1},
	    			{0,1,2,0,2,2,2,1,1,2,2,2,0,1,2,0,1,3,1,3,2,2,3,2,0,1,1,1,1,1},
	    			{0,1,3,2,2,2,0,1,2,2,2,2,1,0,2,2,0,1,0,1,3,0,2,2,2,0,1,1,1,1},
	    			{0,1,1,3,0,2,2,2,2,1,3,1,2,2,3,2,2,0,3,1,2,2,2,2,2,2,0,1,1,1},
	    			{0,1,1,2,2,2,2,2,1,1,1,2,2,2,0,3,2,2,0,3,2,2,2,2,3,2,2,0,1,1},
	    			{0,1,2,2,2,0,2,2,0,1,0,2,2,2,2,0,2,2,2,0,2,2,2,1,1,2,2,2,0,1},
	    			{0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0}	
	    	};

    public Board()   
    {
        setMinimumSize (new Dimension (SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));
        setPreferredSize (new Dimension (SCREEN_SIZE + BORDER_SIZE, SCREEN_SIZE + BORDER_SIZE));
        initVariables();
        initBoard();
        initLevel();   	

        timer = new Timer (100, new ActionListener() {      
			public void actionPerformed(ActionEvent e) {
				repaint();
				timeCtr=timeCtr-100;
			}
        });
    }
    
    private void initVariables()
    {
        screenData = new short[N_BLOCKS][N_BLOCKS];
        if(inGame==true) 
        		timer.start();
    }

    public void initLevel()   // set map,roles,time and coins.
    {
        for (int r = 0; r < N_BLOCKS; r++)
            for (int c = 0; c < N_BLOCKS; c++)
            		screenData[r][c] = map[r][c];

//	    boy = new Boy(screenData, 5, 5, 0,0,0,0,0,0,24);
//	    key1 = new Keys(screenData, 5, 7, 24);
//	    key2 = new Keys(screenData, 6, 10, 24);
//	    girl= new Girl(screenData,6, 7,24);

        	ghost = new ArrayList<GhostShape>();
		ghost.add (new Ghost (screenData, 20,8, BLOCK_SIZE));
		ghost.add (new Ghost (screenData, 28,1, BLOCK_SIZE));
		ghost.add (new Ghost (screenData, 6, 9, BLOCK_SIZE));
		ghost.add (new Ghost (screenData, 13, 20, BLOCK_SIZE));
        tools = new ArrayList<ToolShape>();
		for (int i = 0; i < 8; i++) 
    			tools.add (new Tools (screenData, (int) (Math.random() * 27+1), (int) (Math.random() * 27+1), BLOCK_SIZE));

      	boy = new Boy(screenData, 0, 5, 0,0,0,0,0,0,BLOCK_SIZE);
        key1 = new Keys(screenData, 5, 19, BLOCK_SIZE);
        key2 = new Keys(screenData, 19, 8, BLOCK_SIZE);
        girl= new Girl(screenData,16, 12, BLOCK_SIZE);
		
		dying=false;
		timeCtr=1000*60*3;
		score=300;

        playagain=false;

        findkey[0] = false;
        findkey[1] = false;
        for(int i = 0; i<8; i++)
             findtool[i] = false;
        showgirl=false;
        both=false;   
    }
    
    private void initBoard()
    {
        addKeyListener (new PacKeyAdapter());
        setFocusable (true);
        setBackground (Color.black);
        setDoubleBuffered (true); 
    }


    private void playGame (Graphics2D g2d)
    {
    		if(playagain==true && inGame == true)
    		{
    			inGame = false;
    			playagain = false;
//    			if (timer.isRunning())
//                    timer.stop();
    			
    			if(finished==true)
    				JOptionPane.showMessageDialog(null, "   Good job!   ");
    			
    	        int i = JOptionPane.showConfirmDialog(null, "Would you like to play again?");
    	  		if(i==1)
    	  			System.exit(0);
    	  		else if(i==0) 
    	  			dying=true;
    		}
    		
    		if(dying==true) 
    		{
    			initLevel();
    			repaint();
    		}
    		else {
          	moveboy(g2d);
          	moveghost(g2d);
            checkMaze ();
        }
    }
    
 
    public void moveboy(Graphics2D g2d) 
    {
    	  	boy.move();
    	  	/* collect clock or coin */
    	  	for (int i = 0; i < 8; i++)
    	  	{	
    	  		if (boy.collect(tools.get(i).getx(), tools.get(i).gety())) 
    	  		{
    	  			if (findtool[i] == false)
    	  			{
    	  				if(i<3)
    	  					timeCtr += 1000*20;
    	  				else
    	  					score += 30;
    	  			}
    	  			findtool[i]=true;
    	  		}
    	  		if(findtool[i]==false)
    	  			tools.get(i).draw(g2d, i);
    	  		else	
    	  			tools.get(i).remove(g2d);

    	  	}
  
    	  	/* find key1, show girl*/
    	  	if (key1.findkey(boy.x, boy.y) ) 
    	  	{
    			showgirl=true;
    			findkey[0]=true;
    	  	}	
    	  	if(findkey[0]==false)
    			key1.draw(g2d);
    	  	else 
    	  	{
    			key1.remove(g2d);
    			if(showgirl==true)
    				girl.draw(g2d);
    			else       					
    				girl.remove(g2d);
         }
    	  	
    	  	/* find key2, show exit and change map */   
    	  	if (key2.findkey(boy.x, boy.y)) 
    	  		findkey[1]=true;
    	  	if(findkey[1]==false) 
    	  		key2.draw(g2d);
        else               		
        {
        		key2.remove(g2d);
        		g2d.drawImage (exit, 29*24, 24*24, this);
        		map[24][29] = 0;
        		map[9][19] = 2;
        		for (int r = 0; r < N_BLOCKS; r++)
        			for (int c = 0; c < N_BLOCKS; c++) 
        				screenData[r][c] = map[r][c];
        				
        	}
            
         /* save girl*/   
    	  	if (girl.save(boy.x, boy.y)) 
    	  	{
    	  		showgirl=false;
    	  		both=true;
    	  		System.out.println(both);
    	  	}
    	  	if(both==false)
    	  		boy.draw(g2d);
    	  	else
    	  	{
    	  		girl.remove(g2d);
    	  		boy.removeboy(g2d);
    	  		boy.drawboth(g2d);
    	  	}
    }

    public void moveghost(Graphics2D g2d) 
    {
    		for(int i=0; i<4;i++)
    		{
    			ghost.get(i).draw(g2d,i);
    			ghost.get(i).move();
    		}
    }
 
    private void checkMaze()
    {
        if(boy.findexit()) {
        		finished = true;
        		playagain=true;
        }
    		for(int i=0;i<4;i++) {
	        if(inGame && (score ==0 || timeCtr <= 0 || ghost.get(i).contain(boy.x, boy.y)) ) 
	        		playagain=true;
    		}
    }
    
    
    
    public void paintComponent (Graphics g)
    {
    		super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor (Color.black);
        g2d.fillRect (0, 0, getWidth(), getHeight());
        
        drawMaze (g2d);
        drawScore (g2d);
        drawtime(g2d);

        if (inGame) 
            playGame (g2d);
        else
            showIntroScreen (g2d);  
    }
   

    private void showIntroScreen (Graphics2D g2d)  
    {
        g2d.setColor (new Color (0, 32, 48));
        g2d.fillRect (90, 240, 570, 260);
        g2d.setColor (Color.white);
        g2d.drawRect (90, 240 , 570, 260);
        String s = "    As a knight, you need to save the princess from the maze.  	\n"
        			+"       Two keys will give you hints about the princess and exit.   \n" 
        			+ "   You have limit time and coins. Every step will cost one coin.	\n"
        			+ "         Along the way you could collect Coins and Time,         	\n"
        			+ "   1 Coin = 30 coins; 1 Timer = 20s. They are randomly assigned.	\n"
        			+ "When there is no time and coins or you meet ghost, Game is over.  \n"
        			+ " \n"
        			+ "                   GOOD LUCK !        Press  s  to start.";
        Font small = new Font ("Helvetica", Font.BOLD, 16);
        g2d.setColor (Color.white);
        g2d.setFont (small);
        int x=130;
        int y=270;
        for (String line : s.split("\n"))
            g2d.drawString(line, x, y+= g2d.getFontMetrics().getHeight()+8);
    }
    

    
    
    private void drawScore (Graphics2D g)
    {
        String s = ": " + score;
        Font smallFont = new Font ("Helvetica", Font.BOLD, 14);
        g.setFont (smallFont);
        g.setColor (new Color (96, 128, 255));
        g.drawString (s, SCREEN_SIZE / 2 + 95, SCREEN_SIZE + 16);
        g.drawImage (coin, SCREEN_SIZE / 2 + 70, SCREEN_SIZE , this);
    }
    
    private void drawtime (Graphics2D g) 
    {
      	String s = ": "+ String.format("%02d min, %02d sec", 
    		    TimeUnit.MILLISECONDS.toMinutes(timeCtr),
    		    TimeUnit.MILLISECONDS.toSeconds(timeCtr) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeCtr))    );
        Font smallFont = new Font ("Helvetica", Font.BOLD, 14);
        g.setFont (smallFont);
        g.setColor (new Color (96, 128, 255));
        g.drawString (s, SCREEN_SIZE / 2-95, SCREEN_SIZE + 16);
        g.drawImage (alarm, SCREEN_SIZE / 2-120, SCREEN_SIZE , this);
    }
    
    
    private void drawMaze (Graphics2D g2d)
    {
        int r, c;
        Color mazeColor = new Color (5, 100, 5);

        for (r = 0; r < SCREEN_SIZE; r += BLOCK_SIZE)
        {
            for (c = 0; c < SCREEN_SIZE; c += BLOCK_SIZE)
            {
            		int gr = r / BLOCK_SIZE;
            		int gc = c / BLOCK_SIZE;

                g2d.setColor (mazeColor);
                g2d.setStroke (new BasicStroke(2));
                	/* set wall, draw maze */
                if (screenData[gr][gc] == verticalwall)                
                    g2d.drawLine (c, r, c, r + BLOCK_SIZE - 1);
                if (screenData[gr][gc] == horizontalwall)
                    g2d.drawLine (c, r, c + BLOCK_SIZE - 1, r);      
                if (screenData[gr][gc] == leftupwall) {
                    g2d.drawLine (c, r, c, r + BLOCK_SIZE - 1);      
                    g2d.drawLine (c, r, c + BLOCK_SIZE - 1, r);		
                }
            }
        }
    }
 

    protected class PacKeyAdapter extends KeyAdapter
    {
        public void keyPressed (KeyEvent e)
        {
            int key = e.getKeyCode();

            if (inGame)
            {
                if (key == KeyEvent.VK_LEFT)
                {
                    boy.req_dx = -1;
                    boy.req_dy = 0;
                    score--;
                }
                else if (key == KeyEvent.VK_RIGHT)
                {
	                	boy.req_dx = 1;
	                	boy.req_dy = 0;
	                	score--;
                }
                else if (key == KeyEvent.VK_UP)
                {
	                	boy.req_dx = 0;
	                	boy.req_dy = -1;
	                	score--;
                }
                else if (key == KeyEvent.VK_DOWN)
                {
	                	boy.req_dx = 0;
	                	boy.req_dy = 1;
	                	score--;
                }
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) 
                {
                    inGame = false;
                }
                else if (key == KeyEvent.VK_PAUSE)
                {
                    if (timer.isRunning())
                        timer.stop();
                    else
                        timer.start();
                }
            }
            else if (key == 's' || key == 'S') 		// start the game.
            {
            		inGame = true;
            		initVariables();
            		initLevel();       
            }
            
        }

        public void keyReleased (KeyEvent e)
        {
            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN)
            {
            		boy.req_dx = 0;  
            		boy.req_dy = 0;  
            }
        }
    }

}
