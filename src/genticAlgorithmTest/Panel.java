package genticAlgorithmTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Panel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final int screenWidth = 800;
	private final int screenHeight = 600;
	private final int edgeDistance = 12;
	private final int subjectRadius = 12;
	private final int framesToDraw = 10; //redraw every x instuctions given
	private final int millisecondsToSleepBetweenDrawings = 27;
	
	private int pointsPerGeneration = 5000; //check with Subject for real number
	private ArrayList<Point> pointsToDraw;
	private ArrayList<Point> oldPointsToDraw;
	
	private Subject subject;
	
	public Panel() {
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.white);
		
		pointsToDraw = new ArrayList<Point>();
		oldPointsToDraw = new ArrayList<Point>();
		
	}
	
	public void initPopulation() {
		subject = new Subject(screenWidth,screenHeight, edgeDistance);
		pointsPerGeneration = subject.points().size();
		
		drawGenerationCycle();
	}
	
	public void drawGenerationCycle() {
	    for (int f=0; f<pointsPerGeneration; f++) {	//move the Population once per frame
			boolean everyoneIsDead = true;
			if (f==0) {	//everyoneIsDead is trying to be disproved starting on frame 1
				everyoneIsDead = false;
			}
	    	pointsToDraw.clear();
	    	for (int i=0; i<1; i++) {	//for each Subject, add its point for the circle to be drawn
	    		pointsToDraw.add(subject.points().get(f));
		    	if (f!=0 && (oldPointsToDraw.get(i).getX() != pointsToDraw.get(i).getX() || oldPointsToDraw.get(i).getY() != pointsToDraw.get(i).getY())) {	//it is assumed everyone is dead; if somebody did move, then they are not dead, meaning someone is alive, you cant move if you are on first frame
		    		everyoneIsDead = false;
		    	}
	    	}
	    	System.out.println(oldPointsToDraw + "\n" + pointsToDraw + "\n");
	    	if (!everyoneIsDead) {
	    		if (f % framesToDraw == 0) {	//actually draw every x frames
	    			repaint();
	    	    	try {Thread.sleep(millisecondsToSleepBetweenDrawings);} catch (InterruptedException e) {e.printStackTrace();}
	    		}
	    		oldPointsToDraw = new ArrayList<>(pointsToDraw);
	    	}
	    	else {
	    		repaint();
	    		aNewGenerationBegins();
	    		return;
	    	}
	    }
	}
	
	public void aNewGenerationBegins() {
		//TODO
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("A New Generation Begins!");
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	    Graphics2D g2d = (Graphics2D) g;
	    
	    //draw the outline
	    g2d.setColor(Color.black);
	    g2d.drawLine(edgeDistance, edgeDistance,   edgeDistance, screenHeight-edgeDistance);
	    g2d.drawLine(edgeDistance, edgeDistance,   screenWidth-edgeDistance, edgeDistance);
	    g2d.drawLine(edgeDistance, screenHeight-edgeDistance,   screenWidth-edgeDistance, screenHeight-edgeDistance);
	    g2d.drawLine(screenWidth-edgeDistance, edgeDistance,   screenWidth-edgeDistance, screenHeight-edgeDistance);
	    
	    //draw the Subjects
    	
    	for (int i=0; i<pointsToDraw.size(); i++) {
    		Point currentPoint = pointsToDraw.get(i);
    		g2d.setColor(Color.gray);
    		g2d.fillOval((int)(currentPoint.getX())-subjectRadius/2, (int)(currentPoint.getY())-subjectRadius/2, subjectRadius, subjectRadius);
    		g2d.setColor(Color.black);	//dot is drawn at corner, not center; compensated
    		g2d.drawOval((int)(currentPoint.getX())-subjectRadius/2, (int)(currentPoint.getY())-subjectRadius/2, subjectRadius, subjectRadius);
    	}

	}
	
}
