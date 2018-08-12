package genticAlgorithmTest;

import java.awt.Point;
import java.util.ArrayList;

public class Subject {
	
	private int x = 10;
	private int y = 10;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private int edgeDistance = 0;
	private boolean isAlive = true;
	private int fitness = 0;
	private Target target;
	private int steps = 0;
	
	//what makes subjects different from each other (update when swapping index 0) -> loseIdentity() function here
	private ArrayList<Point> points;
	private Brain brain;
	
	public Subject(int screen_width, int screen_height, int edge_distance, Target theTarget) {
		screenWidth = screen_width;
		screenHeight = screen_height;
		edgeDistance = edge_distance;
		target = theTarget;
		
		x = edgeDistance*4;//screenWidth/2;
		y = screenHeight/2;
		
		points = new ArrayList<Point>();
		brain = new Brain();
		turnDirectionsIntoPoints();
	}
	public Subject(int screen_width, int screen_height, int edge_distance, Target theTarget, Subject parent) {	//similar constructor to above
		screenWidth = screen_width;
		screenHeight = screen_height;
		edgeDistance = edge_distance;
		target = theTarget;
		
		x = edgeDistance*4;//screenWidth/2;
		y = screenHeight/2;
		
		points = new ArrayList<Point>();
		brain = new Brain(parent);
		turnDirectionsIntoPoints();
	}
	
	public void turnDirectionsIntoPoints() {
		//points.add(new Point(x,y));	//starting point
		for (int i=0; i<brain.directions().length; i++) {
			points.add(move(brain.directions()[i]));
			if (i == brain.directions().length-1 && isAlive)
				die();
		}
	}
	
	public Point move(int direction) {	//0 is up y--; 1 is down y++; 2 is left x--; 3 is right x++
		if (isAlive) {	//dead people don't walk
			steps++;
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
			//dies if hits outer bounds
			if (x <= edgeDistance || y <= edgeDistance || x >= screenWidth-edgeDistance || y >= screenHeight-edgeDistance) {
				die();
			}
			//dies from joy if hits target
			if (distanceToTarget() < target.radius()) {
				die();
			}
		}
		return new Point(x,y);
	}
	
	private void die() {
		isAlive = false;
		calculateFitness();
	}
	
	private void calculateFitness() {
		fitness = 0;
		fitness += Math.pow((screenWidth/distanceToTarget())*100, 2);	//less distance = more fitness, squared for more oomph
		fitness += (screenWidth/steps)*1000;	//less steps = more fitness
	}
	
	public double distanceToTarget() {
		return Math.sqrt(Math.pow((target.x()-x),2)+Math.pow((target.y()-y),2));
	}
	
	public int x() {
		return x;
	}
	public int y() {
		return y;
	}
	public int steps() {
		return steps;
	}
	public ArrayList<Point> points() {
		return points;
	}
	public int fitness() {
		return fitness;
	}
	public Brain brain() {
		return brain;
	}
}
