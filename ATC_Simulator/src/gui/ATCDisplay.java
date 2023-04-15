package gui;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import engine.map.Airport;
import engine.process.AvionManager;
import engine.process.MobileElementManager;
import engine.dynamic.Avion;
import engine.map.*;
/**
 * Class that allows to call the method in the class PaintStrategy class and display all components
 * 
 * @author aérien2
 * Date:   2023
 * File : ATCDisplay.java
 */


public class ATCDisplay extends JPanel {

	private static final long serialVersionUID = 1L;
	private MobileElementManager manager;
	private Map map;
	private PaintStrategy paintStrategy = new PaintStrategy();
	/**
	 * Constructor of the ATCDisplay class.
	 * 
	 * @param map the map to display
	 * @param manager the manager of mobile elements
	 */
	public ATCDisplay (Map map,MobileElementManager manager) {
		this.map = map;
		this.manager = manager;
	}
	/**
	 * This method allows to redefine the paintComponent method to draw the elements of the map.
	 * 
	 * @param g the graphic context
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintStrategy.paint(map, g);
		
		ArrayList<Airport> airports = map.getAirports();
		ArrayList<Mountain> mountains = map.getMountains();
		for (Airport airport : airports) {
			paintStrategy.paint(airport, g);
		}
		for (Mountain mountain : mountains) {
			paintStrategy.paint(mountain, g);
		}
		for (AvionManager avionManager : manager.getAvionManager()) {
			paintStrategy.paint(avionManager, g);
		}
		
		
	}
	
	
}
