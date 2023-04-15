package engine.map;

import java.awt.Color;
import java.awt.Rectangle;
/**
 * This class represents a city on a map.
 * A city has a name, an airspace and a color.
 * 
 * @author aérien2
 * Date:   2023
 * File : City.java
 */
public class City {
	private String nameString;
	private Rectangle airspace;
	private Color color;
	/**
	 * Builds a new city with the given parameters.
	 * 
	 * @param nameString the name of the city
	 * @param airspace the rectangle defining the airspace of the city
	 * @param color the color of the city on the map
	 */
	public City(String nameString, Rectangle airspace, Color color) {
		super();
		this.nameString = nameString;
		this.airspace = airspace;
		this.color = color;
	}
	/**
	 * Returns the name of the city.
	 * 
	 * @return the name of the city
	 */
	public String getNameString() {
		return nameString;
	}

	/**
	 * Returns the rectangle defining the city's airspace.
	 * 
	 * @return the rectangle defining the city's airspace
	 */
	public Rectangle getAirspace() {
		return airspace;
	}
	/**
	 * Returns the color of the city on the map.
	 *
	 * @return the color of the city on the map
	 */
	public Color getColor() {
		return color;
	}
	
}
