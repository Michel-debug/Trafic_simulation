package engine.process;


import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import config.GameConfiguration;
import engine.map.*;


/**
 *  Allows you to divide cities, and initialize the map, airports and mountains
 * 
 * @author aérien2
 * Date:   2023
 * File : MapBuilder.java
 */
public class MapBuilder {   // 1000*750
	/**
	 * Builds a map with the given cities and dimensions.
	 * 
	 * @return a Map object representing the map
	 */
	public static Map buildMap() {
		List<City> cities = new ArrayList<>();
		cities.add(new City("Shanghai", new Rectangle(0, 0, 500, 300), Color.GREEN));
        cities.add(new City("Paris", new Rectangle(500, 0, 200, 200), Color.YELLOW));
        cities.add(new City("London", new Rectangle(0, 320, 600, 320), Color.RED));
        cities.add(new City("Pekin", new Rectangle(800, 500, 200, 240), Color.BLUE));
        cities.add(new City("Washington", new Rectangle(900, 20, 400, 220), Color.GRAY));
        cities.add(new City("Tokyo", new Rectangle(600, 300, 400, 200), Color.CYAN));
        cities.add(new City("Mexique", new Rectangle(1000, 250, 300, 250), Color.PINK));
		return new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT,cities);
	}
	/**
	 * Builds a mobile element manager with the given map.
	 * The manager initializes the airports and mountains on the map.
	 * 
	 * @param map a Map object representing the map
	 * @return a MobileElementManager object representing the mobile element manager
	 */
	public static MobileElementManager buildInitMobile(Map map) {
		MobileElementManager manager = new MobileElementManager(map);	
		intializeAirports(map);
		intializeMountain(map);
		return manager;
	}
	/**
	 * Initializes the airports on the map using the configuration data.
	 * 
	 * @param map a Map object representing the map
	 */
	private static void intializeAirports(Map map) {
		int AirportNumber = 1;
		for(Block airportPosition : GameConfiguration.Airport_POSITION) {
			  map.addAirport(GameConfiguration.Airport_NAME[AirportNumber-1], airportPosition);
			  AirportNumber++;
		}
	}
	/**
	 * Initializes the mountains on the map using the configuration data.
	 * 
	 * @param map a Map object representing the map
	 */
	private static void intializeMountain(Map map) {
		int MountainNumber = 0;
		for(Block MountainPosition : GameConfiguration.MOUNT_POSITION) {
			  map.addMountain(SimulationUtility.getRandom(GameConfiguration.MOUNTAIN_MIN, GameConfiguration.MOUNTAIN_MAX), MountainPosition,GameConfiguration.MOUNTAIN_NAME_STRINGS[MountainNumber]);
			  MountainNumber++;
		}
	}
	
	

}