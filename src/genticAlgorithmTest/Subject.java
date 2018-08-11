package genticAlgorithmTest;

import java.awt.Point;
import java.util.ArrayList;

public class Subject {
	
	private Brain brain;
	
	private int x = 10;
	private int y = 10;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private int edgeDistance = 0;
	private boolean isAlive = true;	//THIS IS BROKEN!!!!!!!!!!!!!!!!!
	
	private ArrayList<Point> points;
	
	public Subject(int screen_width, int screen_height, int edge_distance) {
		screenWidth = screen_width;
		screenHeight = screen_height;
		edgeDistance = edge_distance;
		
		x = edgeDistance*4;
		y = screenHeight/2;
		
		points = new ArrayList<Point>();
		brain = new Brain();
		turnDirectionsIntoPoints();
	}
	
	public void turnDirectionsIntoPoints() {
		points.add(new Point(x,y));	//starting point
		for (int i=0; i<brain.directions().length; i++) {
			points.add(move(brain.directions()[i]));
		}
	}
	
	public Point move(int direction) {	//0 is up y--; 1 is down y++; 2 is left x--; 3 is right x++
		if (isAlive) {	//dead people don't walk
			if (direction == 0) {	//up
				y--;
			}
			else if (direction == 1) {	//down
				y++;
			}
			else if (direction == 2) {	//left
				x--;
			}
			else if (direction == 3) {	//right
				x++;
			}
		}
		//if hits outer bounds
		if (x <= edgeDistance || y <= edgeDistance || x >= screenWidth-edgeDistance || y >= screenHeight-edgeDistance) {
			isAlive = false;
		}
		
		return new Point(x,y);
	}
	
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
	public ArrayList<Point> points() {
		return points;
	}
}
