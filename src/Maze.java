
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.net.URL;

import javax.swing.*;

public class Maze extends JFrame {
	
    public Maze() {    initUI();   }
    
    public void initUI() {
        
        add (new Board());
        
        setTitle ("MAZE");
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo (null);
        setVisible (true);
    }

    public static void main (String[] args) throws Exception
    {

    		EventQueue.invokeLater (new Runnable() {
    			public void run() {
    				Maze ex = new Maze();
    				ex.setVisible (true);
    			}
        });

    		URL url= new URL("http://www.brainybetty.com/March2008/Music/DesiJourney.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.loop();
        Thread.sleep(1000*60*10);
        clip.stop();
 
    }
}

