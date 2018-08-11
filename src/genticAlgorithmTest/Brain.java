package genticAlgorithmTest;

import java.util.ArrayList;

public class Brain {
	
	public int directions[] = new int[20000];	//0 is up y--; 1 is down y++; 2 is left x--; 3 is right x++
	
	public Brain() {
		randomizeDirections();
	}
	
	public void randomizeDirections() {
		for (int i=0; i<directions.length; i++) {
			if (i==0) {	//any direction on first move
				directions[i] = (int)((Math.random()*4));
			}
			else {	//if not first move, the next move can't go back
				int nonBacktrackNumber = -1;
				if (directions[i-1] == 0)
					nonBacktrackNumber=1;
				if (directions[i-1] == 1)
					nonBacktrackNumber=0;
				if (directions[i-1] == 2)
					nonBacktrackNumber=3;
				if (directions[i-1] == 3)
					nonBacktrackNumber=2;
				ArrayList<Integer> posibleDirections = new ArrayList<Integer>();
				for (int j=0; j<4; j++) {
					if (j != nonBacktrackNumber)
						posibleDirections.add(j);
				}
				directions[i] = posibleDirections.get((int)((Math.random()*3)));
				
				
			}
		}
	}
	
	public int[] directions() {
		return directions;
	}
	
}
