import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class OvalImageLabel extends JLabel 
{
	private static final long serialVersionUID = 1L;

	private int row = 135;
	private int col = 150;
	private int waveCheckOffset = 75;
	
	private int rows;
	private int cols;
	
	private int baseForce = 18000000;
	
	private WaterSimulation sim;
	private Obstacle[] obstacles;
	

    public OvalImageLabel(ImageIcon img, int r, int c, WaterSimulation s, Obstacle[] o)  
    {
    	super(img);
    	rows = r;
    	cols = c;
    	sim = s;
    	obstacles = o;
    	
    	Runnable helloRunnable = new Runnable() {
		    public void run() {
		    	waveLogic();
		    }
		};

	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	executor.scheduleAtFixedRate(helloRunnable, 0, 30, TimeUnit.MILLISECONDS);
	
    }
    int clickR,clickC;
    boolean done = false;
    private void waveLogic()
    {
    	int speed = 1;
    	
    	
    	//if (row > waveCheckOffset && row < rows - waveCheckOffset && col > waveCheckOffset && col < cols - waveCheckOffset) //seperate
        //{
            //We first find the wave heights of the water plane in all directions around the object.//
    		
    		int upTot = 0,downTot = 0,leftTot =0,rightTot = 0;
    		
    		for(int i = 0; i < waveCheckOffset; i++)
    		{
    			upTot += sim.getWaveHeight(row-i, col);
    			downTot +=  sim.getWaveHeight(row+i, col);
    			leftTot += sim.getWaveHeight(row, col-i);
    			rightTot += sim.getWaveHeight(row, col+i);
    		}
    	
    		
    	 int WaveHeightUP = upTot/waveCheckOffset;
          //Below the object.
          int WaveHeightDOWN =  downTot/waveCheckOffset;
          //To the left of the object.
          int WaveHeightLEFT =  leftTot/waveCheckOffset;
          //To the right of the object
          int WaveHeightRIGHT = rightTot/waveCheckOffset;
    		
            
            
            
           double cut = 10;
            

            // We now compare these values to determine the direction of any waves [Waveheight will be lower ahead of a wavefront and higher behind it].//

            if (WaveHeightUP < WaveHeightDOWN) //Wave Moving Up
            {
            	if(!done)
            	{
            		System.out.println("WaveUP = " + WaveHeightUP + "WaveDown = " + WaveHeightDOWN + "Wave Left = " + WaveHeightLEFT + "Wave Right = " + WaveHeightRIGHT);
            		done = true;
            	}
              
//            	for( Rectangle2D.Double r: rect)
//            	{
//            		if(!rect2.intersects(r))
           				row-=speed;
//            		else
//            			System.out.println("COLLIDED!!!");
//            	}
                	
               // }
                
               
                
            }
             if (WaveHeightUP > WaveHeightDOWN ) //Wave Moving Down
            {
            	 if(!done)
             	{
             		System.out.println("WaveUP = " + WaveHeightUP + "WaveDown = " + WaveHeightDOWN + "Wave Left = " + WaveHeightLEFT + "Wave Right = " + WaveHeightRIGHT);
             		done = true;
             	}
//            	 for( Rectangle2D.Double r: rect)
//             	{
//            		 if(!rect2.intersects(r))
             				row+=speed;
//            		 else
//             			System.out.println("COLLIDED!!!");
//             	}
             
            }


            if (WaveHeightLEFT < WaveHeightRIGHT ) //Wave Moving Left
            {
            	if(!done)
            	{
            		System.out.println("WaveUP = " + WaveHeightUP + "WaveDown = " + WaveHeightDOWN + "Wave Left = " + WaveHeightLEFT + "Wave Right = " + WaveHeightRIGHT);
            		done = true;
            	}

//            	for( Rectangle2D.Double r: rect)
//            	{
//            		if(!rect2.intersects(r))
            	col-=speed;
//            		else
//            			System.out.println("COLLIDED!!!");
//            	}
                
               
            }
            else if (WaveHeightLEFT > WaveHeightRIGHT) //Wave Moving Right
            {
            	if(!done)
            	{
            		System.out.println("WaveUP = " + WaveHeightUP + "WaveDown = " + WaveHeightDOWN + "Wave Left = " + WaveHeightLEFT + "Wave Right = " + WaveHeightRIGHT);
            		done = true;
            	}

//            	for( Rectangle2D.Double r: rect)
//            	{
//            		if(!rect2.intersects(r))
          				col+=speed;
//            		else
//            			System.out.println("COLLIDED!!!");
//            	}
               
            }
        }
    //}
    
  
    Rectangle2D.Double[] rect = new  Rectangle2D.Double[10];
    Rectangle2D.Double rect2;
    public void paintComponent(Graphics g) {
    	

        super.paintComponent(g);
        
    	
    	int i=0;

//        for(Obstacle o: obstacles)
//        {
//        	
//        	
//        	int w = o.getObWidth();
//        	int h = o.getObHeight();
//        	
//        	int topX = o.getObPos()[0].x;
//        	int topY = o.getObPos()[0].y;
//        	rect[i] =  new Rectangle2D.Double(topX,topY,w,h);
//        	g.setColor(Color.WHITE  );
//        	((Graphics2D) g).setStroke(new BasicStroke(2));
//    		g.drawRect(topX, topY, w,  h);
//    		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//    		g.fillRect(topX, topY, w,  h);
//    		i++;
//        	
//        }
        
    	
    	
        rect2 = new Rectangle2D.Double(col-10, row-10, 20,  20);
       
        g.setColor(Color.BLACK);
    	((Graphics2D) g).setStroke(new BasicStroke(4));
		g.drawOval(col-10, row-10, 20,  20);
        g.setColor(Color.RED);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval(col-10, row-10, 20,  20);


    }
}