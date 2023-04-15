package engine.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import engine.process.SimulationUtility;

/**
 * This class represents a map with airports, mountains, cities and blocks.
 * 
 * @author aérien2
 * Date:   2023
 * File : Map.java
 */
public class Map {
	private ArrayList<Airport> airports = new ArrayList<>();
	private ArrayList<Mountain> mountains = new ArrayList<>();
	//HashMap pour list
	private HashMap<Block,Airport> airportMap = new HashMap<>();
	private HashMap<Block,Mountain> MountainMap = new HashMap<>();
	private List<City> cities;
	
	private Block[][] blocks;
	private int lineCount;
	private int columnCount;
	private String[][] blockImages;
	public Map(int lineCount, int columnCount,List<City> cities) {
		this.lineCount = lineCount;
		this.columnCount = columnCount;
		this.cities = cities;
		blocks = new Block[lineCount][columnCount];
		this.blockImages = new String[lineCount][columnCount];
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				blocks[lineIndex][columnIndex] = new Block(lineIndex, columnIndex);
			}
		}
	}
	/**
	 * Adds an airport to the map at the given position.
	 * The airport is randomly generated with the given name.
	 * 
	 * @param name the name of the airport.
	 * @param position the block where the airport is located.
	 */ 
	
	public synchronized void addAirport(String name, Block position) {
		Airport airport = SimulationUtility.generateRandomAirport(name, position);
		airports.add(airport);
		
		airportMap.put(position, airport);
	}
	/**
	 * Adds a mountain on the map at the given position.
	 * 
	 * @param Altitude the altitude of the mountain.
	 * @param position the block where the mountain is located.
	 * @param name the name of the mountain.
	 */
	public void addMountain(double Altitude,Block position,String name) {
		
		Mountain mountain = SimulationUtility.generateMountain(Altitude, position, name);
		mountains.add(mountain);
		MountainMap.put(position, mountain);
	}
	/**
	 * Returns the list of airports on the map.
	 * 
	 * @return the list of airports on the map.
	 */
	public ArrayList<Airport> getAirports(){
		return airports;
	}
	/**
	 * Returns the list of mountains on the map.
	 * 
	 * @return the list of mountains on the map.
	 */
	public ArrayList<Mountain> getMountains(){
		return mountains;
	}
	/**
	 * Returns the airport at the given position.
	 * 
	 * @param position the block where the airport is located.
	 * @return the airport at the given position.
	 */
	public synchronized Airport getAirport(Block position) {
		return airportMap.get(position);
	}
	/**
	 * Returns the mountain at the given position.
	 * 
	 * @param position the block where the mountain is located.
	 * @return the mountain at the given position.
	 */
	public Mountain getMountain(Block position) {
		return MountainMap.get(position);
	}
	/**
	 * Returns the two-dimensional array of blocks that make up the map.
	 * 
	 * @return the two-dimensional array of blocks that make up the map.
	 */
	
	public Block[][] getBlocks() {
		return blocks;
	}
	/**
	 * Returns the number of lines of the map.
	 * 
	 * @return the number of lines of the map.
	 */
	public int getLineCount() {
		return lineCount;
	}
	/**
	 * Returns the list of cities on the map.
	 * 
	 * @return the list of cities on the map.
	 */
	public List<City> getCities(){
		return cities;
	}

	/**
	 * Returns the number of columns of the map.
	 * 
	 * @return the number of columns of the map.
	 */
	public int getColumnCount() {
		return columnCount;
	}
	/**
	 * Returns the block at the given line and column.
	 * 
	 * @param line the line of the block.
	 * @param column the column of the block.
	 * @return the block at the given line and column.
	 */
	
	public Block getBlock(int line, int column) {
		return blocks[line][column];
	}

	/**
	 * Checks if a block is on the top border of the map.
	 * 
	 * @param block the block to check.
	 * @return true if the block is on the top border, false otherwise.
	 */
	public boolean isOnTop(Block block) {
		int line = block.getLine();
		return line == 0;
	}
	/**
	 * Checks if a block is on the bottom border of the map.
	 * 
	 * @param block the block to check.
	 * @return true if the block is on the bottom border, false otherwise.
	 */
	public boolean isOnBottom(Block block) {
		int line = block.getLine();
		return line == lineCount - 1;
	}
	/**
	 * Checks if a block is on the left border of the map.
	 * 
	 * @param block the block to check.
	 * @return true if the block is on the left border, false otherwise.
	 */
	public boolean isOnLeftBorder(Block block) {
		int column = block.getColumn();
		return column == 0;
	}
	/**
	 * Checks if a block is on the right border of the map.
	 * 
	 * @param block the block to check.
	 * @return true if the block is on the right border, false otherwise.
	 */
	public boolean isOnRightBorder(Block block) {
		int column = block.getColumn();
		return column == columnCount - 1;
	}
	/**
	 * Checks if a block is on any border of the map.
	 * 
	 * @param block the block to check.
	 * @return true if the block is on any border, false otherwise.
	 */
	public boolean isOnBorder(Block block) {
		return isOnTop(block) || isOnBottom(block) || isOnLeftBorder(block) || isOnRightBorder(block);
	}

}

