package engine.process;


import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.sampled.Line;


import config.GameConfiguration;
import engine.dynamic.Avion;
import engine.map.*;
/**
 * This MobileElementManager class manages mobile elements on a map. 
 * 
 * @author aérien2
 * Date:   2023
 * File : MobileElementManager.java
 */

public class MobileElementManager {
	private Map map;
	private ArrayList<AvionManager> avionManagers = new ArrayList<AvionManager>();
	// mettre la zone partage qui servir à diviser la carte
	private volatile BlockManager[][] blockManagers;
	private volatile HashMap<Block, BlockManager> blockManagerByposition = new HashMap<>();
	/**
	 * Constructs a mobile element manager with the given map.
	 * The manager initializes the block managers and the airplane managers using the configuration data.
	 * 
	 * @param map a Map object representing the map 
	 */
	public MobileElementManager(Map map) {
		this.map = map;
		 int rows = map.getLineCount() / GameConfiguration.Canton_Size;
	     int cols = map.getColumnCount() / GameConfiguration.Canton_Size;
	     blockManagers = new BlockManager[rows][cols];
	     for (int row = 0; row < rows; row++) {
	            for (int col = 0; col < cols; col++) {
	                BlockManager blockManager = new BlockManager();
	                blockManagers[row][col] = blockManager;
	                blockManagerByposition.put(new Block(row * GameConfiguration.BLOCK_SIZE, col * GameConfiguration.BLOCK_SIZE), blockManager);
	            }
	        }
		
		int AvionCount = GameConfiguration.JEUX_DURATION/GameConfiguration.AVION_INTERVAL;
		for(int i=1;i<=AvionCount;i++) {
			Avion avion = SimulationUtility.generateRandomAvion("U00"+i);
			AvionManager avionmanager = new AvionManager(this,avion,map,avion.getDepartureTime(),blockManagers);
			avionManagers.add(avionmanager);
		}
	}
	/**
	 * Stops all the airplanes by setting their flying flag to false.
	 */
	public void stopAllAvions() {
		for(AvionManager avionmanager:avionManagers) {
			avionmanager.setFlying(false);
		}
	}
	/**
	 * Returns the list of airplane managers.
	 * 
	 * @return an ArrayList of AvionManager objects
	 */
	public ArrayList<AvionManager> getAvionManager(){
		return avionManagers;
	}
	/**
	 * Returns the next airplane manager that is not flying, or null if there is none.
	 * 
	 * @return an AvionManager object representing the next airplane manager
	 */
	public AvionManager getNextAvion() {
		for(AvionManager avionManager : avionManagers) {
			if(!avionManager.isFlying()) {
				return avionManager;
			}
		}
		return null;
	}
	/**
	 * Returns the list of airplane managers that are flying.
	 * 
	 * @return a List of AvionManager objects 
	 */
	public List<AvionManager> getFlyingAvionManagers(){
		return avionManagers.stream().filter(AvionManager::isFlying).collect(Collectors.toList());
	}
	/**
	 * Returns the map object.
	 * 
	 * @return a Map object representing the map
	 */
	public Map getMap() {
		return map;
	}
	/**
	 * Returns the matrix of block managers.
	 * 
	 * @return a two-dimensional array of BlockManager objects
	 */
	public BlockManager[][] getBlockManagers() {
		return blockManagers;
	}
	/**
	 * Returns the block manager corresponding to a given position.
	 * @param position a Block object representing the position
	 * 
	 * @return a BlockManager object representing the block manager
	 */
	public BlockManager getBlockManager(Block position) {
		return blockManagerByposition.get(position);
	}
	
}


