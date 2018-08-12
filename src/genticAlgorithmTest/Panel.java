package genticAlgorithmTest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Panel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final boolean speedMode = false;	//!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	private final int screenWidth = 800;
	private final int screenHeight = 600;
	private final int edgeDistance = 12;
	private final int subjectRadius = 12;
	
	private final int framesToDraw = 15; //redraw every x instuctions given (15,27)
	private final int millisecondsToSleepBetweenDrawings = 27;
	private boolean panelInitialized = false;
	
	private int pointsPerGeneration = 5000; //position changes in one cycle; checks with a Subject for official number
	private ArrayList<Point> pointsToDraw;
	private ArrayList<Point> oldPointsToDraw;
	private Point startingPoint;
	
	private JFrame topFrame;
	
	private int generation = 1;
	
	private Population population;
	private Target target;
	
	public Panel() {
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.white);
		
		pointsToDraw = new ArrayList<Point>();
		oldPointsToDraw = new ArrayList<Point>();
		
	}
	
	public void initPopulation() {
		target = new Target(screenWidth,screenHeight, edgeDistance, subjectRadius);
		population = new Population(screenWidth,screenHeight, edgeDistance, target);
		pointsPerGeneration = population.subjects().get(0).points().size();
		topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		
		panelInitialized = true;
		generationCycle();
	}
	
	@SuppressWarnings("unused")
	public void generationCycle() {
    	if (speedMode) {
    		aNewGenerationBegins();
    		return;
    	}
	    for (int f=0; f<pointsPerGeneration; f++) {	//move the Population once per frame
			boolean everyoneIsDead = true;
			if (f==0) {	//everyoneIsDead is trying to be disproved starting on frame 1
				everyoneIsDead = false;
				startingPoint = population.subjects().get(0).points().get(0);
			}
	    	pointsToDraw.clear();
	    	for (int i=0; i<population.subjects().size(); i++) {	//for each Subject, add its point for the circle to be drawn
	    		pointsToDraw.add(population.subjects().get(i).points().get(f));
		    	if (f!=0 && (oldPointsToDraw.get(i).getX() != pointsToDraw.get(i).getX() || oldPointsToDraw.get(i).getY() != pointsToDraw.get(i).getY())) {	//it is assumed everyone is dead; if somebody did move, then they are not dead, meaning someone is alive, you cant move if you are on first frame
		    		everyoneIsDead = false;
		    	}
	    	}
	    	if (f == pointsPerGeneration-1) {//last frame
	    		everyoneIsDead = true;
	    	}
	    	if (!everyoneIsDead) {
	    		if (f % framesToDraw == 0) {	//to draw every x frames
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
		generation++;
		topFrame.setTitle("Genetic Algorithm       - Gen " + generation + "  -       " + population.infoMessage());
		//(space to skip generation, q to toggle seeing only the best) 
		population.doNewGeneration(generation);
		generationCycle();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (panelInitialized) {
		    Graphics2D g2d = (Graphics2D) g;
		    drawOutline(g2d);
		    drawStartingPoint(g2d);
		    drawTarget(g2d);
		    drawPopulation(g2d);
		}
	}
	
	private void drawOutline(Graphics2D g2d) {
	    g2d.setColor(Color.black);
	    g2d.drawRect(edgeDistance, edgeDistance, screenWidth-2*edgeDistance, screenHeight-2*edgeDistance);
	}
	private void drawPopulation(Graphics2D g2d) {
    	for (int i=pointsToDraw.size()-1; i>=0; i--) {
    		Point currentPoint = pointsToDraw.get(i);
    		g2d.setColor(Color.gray);
    		if (i == 0)
    			g2d.setColor(Color.blue);
    		g2d.fillOval((int)(currentPoint.getX())-subjectRadius/2, (int)(currentPoint.getY())-subjectRadius/2, subjectRadius, subjectRadius);
    		g2d.setColor(Color.black);	//dot is drawn at corner, not center; compensated
    		g2d.drawOval((int)(currentPoint.getX())-subjectRadius/2, (int)(currentPoint.getY())-subjectRadius/2, subjectRadius, subjectRadius);
    	}
	}
	private void drawStartingPoint(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.drawOval((int)(startingPoint.getX())-subjectRadius/2, (int)(startingPoint.getY())-subjectRadius/2, subjectRadius, subjectRadius);
	}
	private void drawTarget(Graphics2D g2d) {
		g2d.setColor(Color.green);
		g2d.fillOval(target.x()-target.radius()/2-2, target.y()-target.radius()/2-2, target.radius()+4, target.radius()+4);
		g2d.setColor(Color.red);	//dot is drawn at corner, not center; compensated
		g2d.drawOval(target.x()-target.radius()/2-2, target.y()-target.radius()/2-2, target.radius()+4, target.radius()+4);
	}
	
}
