import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WaterSimulation 
{
	private int rows;
	private int cols;
	private int size;
	private int[] buffer1;
	private int[] buffer2;
	private int[] currentBuffer;
	private int baseForce = 18000000;
	private boolean buffer1Current = true;
	private  byte[] byteArray;
	private int dampingMult = 99;
	private int dampingMag = 100;
	private float clamp = 0.1f;
	private Obstacle[] obstacles;
	int smoothingLevel = 1;
	int blurRadius = 3;
	
	private GUI gui;
	
	BufferedImage image;
	
	public WaterSimulation (int r, int c, BufferedImage img, GUI g,Obstacle[] o)
	{
		rows = r;
		cols = c;
		image = img;
		obstacles = o;
		
		size = r*c;
		buffer1 = new int[size];
		buffer2 = new int[size];
		currentBuffer = buffer1;
		byteArray = new byte[size];	
		gui = g;
		gui.setSimRef(this);
		
		//disturbBuffer(132,126,2,baseForce);
		
		//Run main loop 30 times a second
		Runnable helloRunnable = new Runnable() {
			    public void run() {
			    	mainLoop();
			    }
			};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.MILLISECONDS);
					
	}
	
	public int getWaveHeight(int row, int col)
	{
		return currentBuffer[(row * (cols )) + col];
	}
	
	private void mainLoop()
	{
		//System.out.println("mian loop.");
		
		//Check Which buffer is current then pass the buffers to the Hugo Elias Filter
		if (buffer1Current)
		{
			//setObs();
	          simulationCore(buffer2, buffer1);
	          //setObs();
	          currentBuffer = buffer1; //Copy the current buffer to bufferCurrent
		}
		else
		{
			//setObs();
			simulationCore(buffer1, buffer2);
			//setObs();
			currentBuffer = buffer2; //Copy the current buffer to bufferCurrent
		}
      
		//clamp the values
		//for(int i = 0; i < size; i++)
			//clamp(currentBuffer[i],baseForce,-baseForce);
		
		
		setImagePixels();
		//Switch the current buffer ready for the next simulation step
		buffer1Current = !buffer1Current;
	}
	
	private void setObs()
	{
		for(Obstacle o: obstacles)
		{
			int obsPositions[] = o.pixelPath;
			
			for(int p: obsPositions)
			{
				//System.out.println("Pos= " + p);
				currentBuffer[p] = 0;
			}
		}
	}
	
	private void simulationCore(int[] previousBuffer, int[] currentBuffer)
    {
		//System.out.println("hugo.");

        int bufferPosition;

        // Exclude the top and bottom row from the simulation to avoid going out of array bounds
        for (int i = cols + 1; i < size - (cols + 1); i++)
        {
            currentBuffer[i] = (((previousBuffer[i - 1] +
                                  previousBuffer[i + 1] +
                                  previousBuffer[i - (cols)] +
                                  previousBuffer[i + (cols)]) / 2) -
                                  currentBuffer[i]);

            currentBuffer[i] = (currentBuffer[i] /dampingMag)*dampingMult; //Apply Damping so waves dissipate over time
            
            
        }

        //Set the 1st column to zero to stop ripple passing from one side to another
       for (int y = 0; y < rows; y++)
        {
            bufferPosition = (y * (cols )) + 0;  
            currentBuffer[bufferPosition] = 0;
       }
    }
	
	private void setImagePixels()
	{
		//maniuplate byte array then use to set image pixels.
		
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				int bufferPosition = (r * (cols )) + c;
		
				
				//float val;
				float val =  ((((float)currentBuffer[bufferPosition]/(float)baseForce) * 127f) + 127f);
				
			
	
				 int red = (int)(val * 0.3);
				 int green = (int)(val * 0.4);
				 int blue = (int)val;
				 int rgb = (red << 16) | (green << 8) | blue;
				 
				 
				 image.setRGB(r, c, rgb);
				
				
			}		
		}
		gui.refresh();
	}
	
	public void disturbBuffer(int row, int col, int radius, int force)
	{
		
		int inputSize = (int) Math.pow(((2 * radius) + 1), 2);
		int bufferPosition[] = new int[inputSize];
		
		
		for(int i = 1; i<= radius; i++)
		{	
			
			bufferPosition[0] = (row * (cols )) + col;
			bufferPosition[1] = ((row-i) * (cols )) + (col-i);
			bufferPosition[2] = ((row-i) * (cols )) + col;
			bufferPosition[3] = ((row-i) * (cols )) + (col+i);
			bufferPosition[4] = (row * (cols )) + (col-i);
			bufferPosition[5] = (row * (cols )) + (col+i);
			bufferPosition[6] = ((row+i) * (cols )) + (col-i);
			bufferPosition[7] = ((row+i) * (cols )) + col;
			bufferPosition[8] = ((row+i) * (cols )) + (col+i);
		}
		
		
		for(int i =0; i<inputSize; i++)
			currentBuffer[bufferPosition[i]] = force;
			
		
		/*
		//Run a Gaussian blur algorithm with a 5 by 5 kernel and sigma =1, InputSmoothingSettings.SmoothingLevel number of times.
        for (int i = 0; i < smoothingLevel; i++)
        {
            for (int y = row - blurRadius; y <= row + blurRadius; y++)
            {
                for (int x = col - blurRadius; x <= col + blurRadius; x++)
                {                	
                    currentBuffer[((y-1) * (cols)) + (x) -1] = ((1 * currentBuffer[((y - 3) * (cols)) + (x - 2) -1] +
								                            4 * currentBuffer[((y - 3) * (cols)) + (x - 1) - 1] +
								                            7 * currentBuffer[((y - 3) * (cols)) + (x) - 1] +
								                            4 * currentBuffer[((y - 3) * (cols)) + (x + 1) - 1] +
								                            1 * currentBuffer[((y - 3) * (cols)) + (x + 2) - 1] +
								                            4 * currentBuffer[((y - 2) * (cols)) + (x - 2) - 1] +
								                            16 * currentBuffer[((y - 2) * (cols)) + (x - 1)-1] +
								                            26 * currentBuffer[((y - 2) * (cols)) + (x) - 1] +
								                            16 * currentBuffer[((y - 2) * (cols)) + (x + 1) - 1] +
								                            4 * currentBuffer[((y - 2) * (cols)) + (x + 2) - 1] +
								                            7 * currentBuffer[((y-1) * (cols)) + (x - 2) - 1] +
								                            26 * currentBuffer[((y-1) * (cols)) + (x - 1) - 1] +
								                            41 * currentBuffer[((y-1) * (cols)) + (x) - 1] +
								                            26 * currentBuffer[((y-1) * (cols)) + (x + 1) - 1] +
								                            7 * currentBuffer[((y-1) * (cols)) + (x + 2) - 1] +
								                            4 * currentBuffer[((y ) * (cols)) + (x - 2) - 1] +
								                            16 * currentBuffer[((y) * (cols)) + (x - 1) - 1] +
								                            26 * currentBuffer[((y) * (cols)) + (x) - 1] +
								                            16 * currentBuffer[((y) * (cols)) + (x + 1) - 1] +
								                            4 * currentBuffer[((y) * (cols)) + (x + 2) - 1] +
								                            1 * currentBuffer[((y + 1) * (cols)) + (x - 2) - 1] +
								                            4 * currentBuffer[((y + 1) * (cols)) + (x - 1) - 1] +
								                            7 * currentBuffer[((y + 1) * (cols)) + (x) - 1] +
								                            4 * currentBuffer[((y + 1) * (cols)) + (x + 1) - 1] +
								                            1 * currentBuffer[((y + 1) * (cols)) + (x + 2) - 1]) / 273);
                }
            }
        }  
		*/
		
		
	}
	
	private int clamp (int val, int max, int min)
	{
		if(val < min)
			val = min;
		else if (val > max)
			val = max;
		return val;
	}
	
	
}
