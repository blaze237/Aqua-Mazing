import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WaveGenerator 
{
	private int row;
	private int col;
	private int freq;
	private int force;
	private int radius;
	private WaterSimulation sim;
	
	
	public WaveGenerator(int r, int c, int f, int rad, int frequencyMS, WaterSimulation w)
	{
		row = r;
		col = c;
		freq = frequencyMS;
		force = f;
		radius = rad;
		sim = w;
		
		//Disturb water every freq ms 
		Runnable helloRunnable = new Runnable() {
			    public void run() {
			    	generateWave();
			    }
			};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, freq, TimeUnit.MILLISECONDS);
	}
	
	
	private void generateWave()
	{
		sim.disturbBuffer(row, col, radius, force);
		
	}
	

}
