package engine.process;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import config.GameConfiguration;
import engine.dynamic.Avion;
import engine.map.Airport;
import engine.map.Block;
import engine.map.Mountain;
/**
 *  This class can read images, generate random planes, airports and mountains, as well as simulate time units and window refresh times.
 * 
 * @author aérien2
 * Date:   2023
 * File : SimulationUtility.java
 */
public class SimulationUtility {
	/**
	 * read a image from an image file
	 * @param filepath the path(from 'src') of the image file
	 * @return the read file
	 *
	 */
	public static Image readImage(String filePath) {
		try {
			return ImageIO.read(new File(filePath));
			
		}catch(IOException e) {
			System.out.println("Can not read the image file");
			return null;
		}
	}
	/**
	 * Returns a random integer between a minimum and a maximum value.
	 * 
	 * @param min an int representing the minimum value
	 * @param max an int representing the maximum value
	 * @return an int representing the random value 
	 */
	public static int getRandom(int min,int max) {
		return (int)(Math.random()*(max+1-min))+min;
		
	}
	/**
	 * Generates a random airplane with a given identifier.
	 * The airplane has a random speed, departure position, arrival position and departure time.
	 * The departure and arrival positions are chosen among the airport positions defined in the configuration data.
	 * 
	 * @param id a String representing the identifier of the airplane
	 * @return an Avion object representing the random airplane 
	 */
	public static Avion generateRandomAvion(String id) {
		int Speed = getRandom(GameConfiguration.GAME_MIN_SPEED,GameConfiguration.GAME_MAX_SPEED); //generer la vitesse de vol
	    Block airport_depart = GameConfiguration.Airport_POSITION[getRandom(0, GameConfiguration.Airport_POSITION.length-1)];
	    Block airport_arrive = GameConfiguration.Airport_POSITION[getRandom(0, GameConfiguration.Airport_POSITION.length-1)];
	    while(airport_arrive.equals(airport_depart)) {
	    	airport_arrive = GameConfiguration.Airport_POSITION[getRandom(0, GameConfiguration.Airport_POSITION.length-1)];
	    }
	    int departureTime = getRandom(0,GameConfiguration.Temps_depart);
		// générer du temps de vol
		return new Avion(id, Speed, airport_depart, departureTime, airport_arrive);
	}
	
	/**
	 * Generates a random airport with a given name and position.
	 * 
	 * @param name a String representing the name of the airport
	 * @param position a Block object representing the position of the airport
	 * @return an Airport object representing the random airport
	 */
	public static Airport generateRandomAirport(String name,Block position) {
		return new Airport(name,position);
	}
	/**
	 * Generates a mountain with a given height, position and name.
	 * 
	 * @param H a Double representing the height of the mountain
	 * @param position a Block object representing the position of the mountain
	 * @param name a String representing the name of the mountain
	 * @return a Mountain object representing the mountain
	 */
	public static Mountain generateMountain(Double H,Block position,String name) {
		return new Mountain(position,H,name);
	}
	/**
	 * Simulates a unit of time by making the current thread sleep for 90 milliseconds.
	 */
	public static void unitTime() {
		try {
			Thread.sleep(90);
		}catch(InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
	/**
	 * Simulates a window refresh time by making the current thread sleep for 200 milliseconds.
	 */
	public static void windowRefreshTime() {
		try {
			Thread.sleep(200);
		}catch(InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
}
