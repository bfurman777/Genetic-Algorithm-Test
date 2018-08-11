package genticAlgorithmTest;

import javax.swing.JFrame;

public class Frame extends JFrame{

	private static final long serialVersionUID = 1L;
	public Panel panel;

	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Genetic Algorithm - " + "Gen 1");
		setResizable(false);

		panel = new Panel();
		
		add(panel);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
		
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		panel.initPopulation();
	}
	
}
