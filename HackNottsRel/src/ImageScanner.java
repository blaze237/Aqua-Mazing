import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageScanner 
{
	
	
	public BufferedImage getImage()
	{
		File f = null;
		 BufferedImage image = null;
		
		try{
			 f = new File("water.bmp"); //image file path
			 //image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
			 image = ImageIO.read(f);
			
		 }catch(IOException e){
			 System.out.println("YIKES: "+e);
			}
		
		return image;
	}
	
	
}
