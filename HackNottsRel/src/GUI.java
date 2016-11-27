import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GUI 
{
	BufferedImage image;
	JFrame frame;
	OvalImageLabel picLabel;
	WaterSimulation simulationRef;
	private int baseForce = 18000000;
	private int rows,cols;
	private Obstacle[] obstacles;
	
	public GUI(BufferedImage img, int r, int c, Obstacle[] o)
	{
		rows = r;
		cols = c;
		image = img;
		obstacles = o;
	}
	
	public void setSimRef(WaterSimulation r)
	{
		simulationRef = r;
		createAndShowGUI();
	}
	
	public void refresh()
	{
		frame.revalidate();
		frame.repaint();
		picLabel.revalidate();
		picLabel.repaint();
	}

	
	private  void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Aqua-Mazing!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //Add the ubiquitous "Hello World" label.
        picLabel = new OvalImageLabel(new ImageIcon(image), rows,cols,simulationRef, obstacles);
        
        picLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
            	int x=me.getX();
                int y=me.getY();
                simulationRef.disturbBuffer(x, y, 3,baseForce);
                
                
                
                //System.out.println(x+","+y);//these co-ords are relative to the component
              }
            });
       
        frame.getContentPane().add(picLabel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
}
