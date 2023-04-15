package testATC;

import engine.map.Block;
import gui.MainGUI;

public class TestATC {
	/**
	 * The main method creates a new thread and initializes the ATC Simulator's Interface.
	 * 
	 * * @author aérien2
	 * Date:   2023
	 * File : ATCDisplay.java
	 * 
	 * @param args the command line arguments (not used)
	 * 
	 */
	public static void main(String[] args) {
		MainGUI gameMainGUI = new MainGUI("ATC_Simulator");

		Thread gameThread = new Thread(gameMainGUI);
		gameThread.start();
	
	}

}
