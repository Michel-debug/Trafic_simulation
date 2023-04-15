package config;

import engine.map.Block;

/**
 *  A class that contains constants for the game configuration. 
 * 
 * @author aérien2
 * Date:   2023
 * File : GameConfiguration.java
 * 
 */
public class GameConfiguration {
	 // Window dimensions
	public static final int WINDOW_WIDTH =1500;
	public static final int WINDOW_HEIGHT = 750;
	 // Block size for grid
	public static final int BLOCK_SIZE = 1;
	 // Grid dimensions
	public static final int LINE_COUNT = WINDOW_HEIGHT / BLOCK_SIZE; //750
	public static final int COLUMN_COUNT = WINDOW_WIDTH / BLOCK_SIZE; //1500
	 // Avion speed limits
	public static final int GAME_MAX_SPEED = 5;  
	public static final int GAME_MIN_SPEED = 3;
	 // Game duration
	public static final int JEUX_DURATION = 400;
	 // Mountain positions
	public static final Block[] MOUNT_POSITION = new Block[] {new Block(200,300),new Block(50,650),new Block(400,1000),new Block(500, 200)};
	// Airport positions
	public static final Block[] Airport_POSITION = new Block[] {new Block(100,300),new Block(20,600),new Block(700,950),new Block(600,200),new Block(150, 1000),new Block(400,700),new Block(300, 1200)};
	// Airport names
	public static final String[] Airport_NAME = new String[] {new String("Shanghai"),new String("Paris"),new String("Pekin"),new String("London"),new String("Washington"),new String("Tokyo"),new String("Mexique")};
	// Mountain names
	public static final String[] MOUNTAIN_NAME_STRINGS = new String[] {new String("A"),new String("B"),new String("C"),new String("D")};
	// Station stay duration limits
	public static final int STATION_MIN_STAY= 10;
	public static final int STATION_MAX_STAY= 20;
	  // Plane interval
	public static final int AVION_INTERVAL = 20;
	// Departure time
	public static final int Temps_depart = 10;
	// Mountain height limits
	public static final int MOUNTAIN_MIN= 1000;
	public static final int MOUNTAIN_MAX = 8000;
	// Canton size
	public static final int Canton_Size = 20;
	// Waiting interval
	public static final int WATTING_INTERVAL = 500;
	
	
	
	
}
