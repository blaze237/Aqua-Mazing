import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Obstacle {
	private int obWidth;
	private int obHeight;
	public int obIndex;
	
	private Random rand;
	private Point[] obPos;
	public ArrayList<Point> pixelPathList;
	public int[] pixelPath;
	private int arraySize;
	private int rows,cols;
	
	public  Point[] getObPos()
	{
		return obPos;
	}
	
	public Obstacle(int index, Obstacle[] obs,int c, int r) {
		obIndex = index;
		rand = new Random();
		obPos = new Point[3];
		cols = c;
		rows = r;
		pixelPathList = new ArrayList<Point>();
		
		generateValues();
		//System.out.println("p0 x: " + obPos[0].x + ", y: " + obPos[0].y);
		//System.out.println("p1 x: " + obPos[1].x + ", y: " + obPos[1].y);
		//System.out.println("p2 x: " + obPos[2].x + ", y: " + obPos[2].y);
		generatePixelPath();
		sortArray();
		
	}
	
	private void generateValues() {
		obPos[0] = generatePosition();
		System.out.println("POSITION IS GENERATED");
		obWidth = generateWidth();
		obPos[1] = new Point(obPos[0].x + obWidth, obPos[0].y);
		System.out.println("WIDTH IS GENERATED");
		obHeight = generateHeight();
		obPos[2] = new Point(obPos[0].x, obPos[0].y + obHeight);
	}
	
	private Point generatePosition() {
		Point pos = new Point(0, 0);
	
		pos.x = rand.nextInt(cols);
		pos.y = rand.nextInt(rows);
		
		return pos;
	}
//	private boolean validatePosition(Point position) {
//		int i;
//		boolean canGenerate = true;
//		
//		for (i = 0; i < obIndex + 1 && obIndex != 0; ++i) {
//			if (i != obIndex) {
//				//System.out.println(obIndex + " values: \nposx: " + position.x + "\nposy: " + position.y
//				//		+ "\notherleftbound: " + (otherObstacles[i].obPos[0].x - Variables.PLAYER_WIDTH)
//				//		+ "\notherrightbound: " + (otherObstacles[i].obPos[0].x + Variables.PLAYER_WIDTH)
//				//		+ "\nothertopbound: " + (otherObstacles[i].obPos[0].y - Variables.PLAYER_WIDTH)
//				//		+ "\notherbottombound: " + (otherObstacles[i].obPos[0].y + Variables.PLAYER_WIDTH));
//				if (position.x >= otherObstacles[i].obPos[0].x - Variables.PLAYER_WIDTH
//						&& position.x <= otherObstacles[i].obPos[1].x  + Variables.PLAYER_WIDTH
//						&& position.y >= otherObstacles[i].obPos[0].y - Variables.PLAYER_WIDTH
//						&& position.y <= otherObstacles[i].obPos[2].y + Variables.PLAYER_WIDTH) {
//					canGenerate = false;
//					System.out.println("CANGENERATE HAS BEEN SET TO FALSE");
//				}
//			}
//		}
//		
//		return canGenerate;
//	}
	
	private int generateWidth() {
		int width = 0;
		int widthRange = cols - obPos[0].x;
		
			
		if (widthRange > 1) {
			width = rand.nextInt(widthRange - 1) + 1;
			if (width > cols/4)
				width = cols/4;
			if (width < 100)
				width = 100;
		}
		else 
			width = 1;
		return width;
	}
//	private boolean validateWidth(int w) {
//		int i;
//		boolean canGenerate = true;
//		
//		for (i = 0; i < obIndex + 1 && obIndex != 0; ++i) {
//			if (i != obIndex) {
//				//System.out.println(obIndex + " values: \nwidth: " + w + "\nposx: " + obPos[0]
//				//		+ "\notherleftbound: " + (otherObstacles[i].obPos[0].x - Variables.PLAYER_WIDTH)
//				//		+ "\notherrightbound: " + (otherObstacles[i].obPos[0].x + Variables.PLAYER_WIDTH)
//				//		+ "\nothertopbound: " + (otherObstacles[i].obPos[0].y - Variables.PLAYER_WIDTH)
//				//		+ "\notherbottombound: " + (otherObstacles[i].obPos[0].y + Variables.PLAYER_WIDTH));
//				if (obPos[0].x + w >= otherObstacles[i].obPos[0].x - Variables.PLAYER_WIDTH 
//						&& obPos[0].x + w <= otherObstacles[i].obPos[1].x + Variables.PLAYER_WIDTH) {
//					canGenerate = false;
//					System.out.println("WIDTH INVALID");
//				}
//			}
//		}
//		return canGenerate;
//	}
	
	private int generateHeight() {
		int height = 0;
		int heightRange = cols - obPos[0].y;// - 20;
	
		if (heightRange > 1) {
			height = rand.nextInt(heightRange - 1) + 1;
			if (height > rows/4)
				height = rows/4;
			if (height < 100)
				height = 100;
		}
		else
			height = 100;
		return height;
	}
//	private boolean validateHeight(int h) {
//		int i;
//		boolean canGenerate = true;
//		
//		for (i = 0; i < obIndex; ++i) {
//			if (i != obIndex) {
//					if (obPos[0].y + h >= otherObstacles[i].obPos[0].y - Variables.PLAYER_WIDTH
//							&& obPos[0].y + h <= otherObstacles[i].obPos[1].y + Variables.PLAYER_WIDTH) {
//						canGenerate = false;
//						System.out.println("HEIGHT INVALID");
//					}
//			}
//		}
//		return canGenerate;
//	}
	
	private void generatePixelPath() {
		int i, j;
		
		for (i = obPos[0].x, j = 0; i < obPos[1].x; ++i, ++j) {
			pixelPathList.add(new Point(i, obPos[0].y));
			//System.out.println("loop 1 " + pixelPathList.get(j));
		}
		for (i = obPos[1].y; i < obPos[2].y; ++i, ++j) {
			pixelPathList.add(new Point(obPos[1].x, i));
			//System.out.println("loop 2 " + pixelPathList.get(j));
		}
		for (i = obPos[1].x; i > obPos[2].x; --i, ++j) {
			pixelPathList.add(new Point(i, obPos[2].y));
			//System.out.println("loop 3 " + pixelPathList.get(j));
		}
		for (i = obPos[2].y; i > obPos[0].y; --i, ++j) {
			pixelPathList.add(new Point(obPos[2].x, i));
			//System.out.println("loop 4 " + pixelPathList.get(j));
		}
		convertArrayList();
	}
	
	private void convertArrayList() {
		int i;
		arraySize = pixelPathList.size();
		pixelPath = new int[arraySize];
		for (i = 0; i < arraySize; ++i) 
			pixelPath[i] = pixelPathList.get(i).x*cols + pixelPathList.get(i).y;
	}
	
	private void sortArray() {
		int i, temp;
		boolean done = false;
		
		while (done == false) {
			done = true;
			for (i = 0; i < arraySize - 1; ++i) {
				if (pixelPath[i] > pixelPath[i + 1]) {
					temp = pixelPath[i];
					pixelPath[i] = pixelPath[i + 1];
					pixelPath[i + 1] = temp;
					done = false;
				}
			}
		}
	
	}
	
	public int getObWidth() {return obWidth;}
	public int getObHeight() {return obHeight;}
	public Point getCornerPosition(int index) {return obPos[index];}
}
