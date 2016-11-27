import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Main 
{
	
	public static void main(String[] args) 
	{
		int rows;
		int cols;
		BufferedImage image = null;
		ImageScanner imgScan = new ImageScanner();
		int baseForce = 18000000;

		
		image = imgScan.getImage();
		rows = image.getWidth();
		cols = image.getHeight();
		
		
		
		Obstacle obs[] = new Obstacle[10];
		for (int i = 0; i < 10; ++i)
			obs[i] = new Obstacle(i, obs, rows,cols);
		
		//int w = obs[0].getObWidth();
		
		GUI g = new GUI(image, rows, cols,obs);
		
		WaterSimulation water = new WaterSimulation(rows,cols,image,g,obs);
		
		//Create wave generators
		int amountGenerators = 1;
		
		
		
		
		
		WaveGenerator[] generators = new WaveGenerator[amountGenerators];
		
//		for(int i = 0; i < amountGenerators; i++)
//		{
//			int r = 300;
//			int c = 126;
//			int rad = 10;
//			int force = (int)(baseForce*0.3);
//			int freq = 50;
//			generators[i] = new WaveGenerator(r, c, force, rad, freq, water);
//		}
		
		
		

		
		
	}

}
