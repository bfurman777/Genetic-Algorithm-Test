package genticAlgorithmTest;

public class Target {

	private int x;
	private int y;

	private int radius;
	
	private int screenWidth;
	private int screenHeight;
	private int edgeDistance;
	private int subjectRadius;
	
	public Target(int screen_width, int screen_height, int edge_distance, int subject_Radius) {
		screenWidth = screen_width;
		screenHeight = screen_height;
		edgeDistance = edge_distance;
		subjectRadius = subject_Radius;
		
		radius = (int)(subjectRadius*1.5);
		
		x = screenWidth-edgeDistance-subjectRadius*5;
		y = screenHeight/2;
	}
	
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
	public int radius() {
		return radius;
	}
	
}
