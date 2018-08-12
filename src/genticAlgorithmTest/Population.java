package genticAlgorithmTest;

import java.util.ArrayList;

public class Population {
	
	private ArrayList<Subject> subjects;
	
	private final int numberOfSubjectsInPop = 777;	//for performance: less than 1000
	
	private int screenWidth = 0;
	private int screenHeight = 0;
	private int edgeDistance = 0;
	private Target target;
	private int generation = 1;
	
	private int indexOfBestSubject = -1;
	private int bestFitness = -1;
	
	private int smallestFitness = Integer.MAX_VALUE;
	
	private String infoMessage = "";
	
	//temp, TODO for random selection
	private Subject bestSubject;
	
	public Population(int screen_width, int screen_height, int edge_distance, Target theTarget) {	//TODO: Merge this function into addNewGenerationToPopulation()
		subjects = new ArrayList<Subject>();
		screenWidth = screen_width;
		screenHeight = screen_height;
		edgeDistance = edge_distance;
		target = theTarget;
		
		for (int i=0; i<numberOfSubjectsInPop; i++) {
			subjects.add(new Subject(screenWidth, screenHeight, edgeDistance, target));
			if (subjects.get(i).fitness() > bestFitness) {
				indexOfBestSubject = i;
				bestFitness = subjects.get(i).fitness();
			}
			if (subjects.get(i).fitness()<smallestFitness)
				smallestFitness = subjects.get(i).fitness();
		}
		bestSubject = subjects.get(indexOfBestSubject);
		swapBestSubjectIntoIndex0();
	}
	
	public void doNewGeneration(int theGeneration) {	
		generation = theGeneration;
		addNewGenerationToPopulation();
	}
	
	private void addNewGenerationToPopulation() {	//very similar to construct 2 above - watch differences and info gathering
		ArrayList<Subject> newSubjects = new ArrayList<Subject>();
		indexOfBestSubject = -1;
		bestFitness = -1;
		smallestFitness = Integer.MAX_VALUE;

		for (int i=0; i<numberOfSubjectsInPop; i++) {
			if (i == 0)	//&&generation>1 (if merging functions with above construct
				newSubjects.add(subjects.get(0));	//keep the best from the past generation
			else {
				newSubjects.add(new Subject(screenWidth, screenHeight, edgeDistance, target, bestSubject));	//randomSubject(); TODO!!!!!!!!!!!!
			}
			if (newSubjects.get(i).fitness() > bestFitness) {
				indexOfBestSubject = i;
				bestFitness = newSubjects.get(i).fitness();
			}
			if (newSubjects.get(i).fitness()<smallestFitness)
				smallestFitness = newSubjects.get(i).fitness();
		}
		subjects = newSubjects;
		bestSubject = subjects.get(indexOfBestSubject);
		swapBestSubjectIntoIndex0();
	}
	
	private void swapBestSubjectIntoIndex0() {
		//not 100% sure this transfers all data within object
		subjects.add(0, subjects.get(indexOfBestSubject));
		subjects.remove(indexOfBestSubject+1);
		infoMessage = "Population: " + numberOfSubjectsInPop +"   Top Fitness: " + subjects.get(0).fitness() + " -> # Steps: " + subjects.get(0).steps() + ";   Smallest Fitness: " + smallestFitness;
	}
	
	public ArrayList<Subject> subjects() {
		return subjects;
	}
	
	public String infoMessage() {
		return infoMessage;
	}
	
}
