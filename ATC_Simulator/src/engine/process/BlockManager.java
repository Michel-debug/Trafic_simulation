
package engine.process;

import config.GameConfiguration;
/**
 *  Determines if the aircraft can enter the shared flight area
 * 
 * @author aérien2
 * Date:   2023
 * File : BlockManager.java
 */
public class BlockManager {
//	private FlightZone flightZone;
	private AvionManager occupyingAvion = null;

	public BlockManager() {
	}

	/**
	 * This method is called when an airplane enters the block.
	 * If the block is already occupied by another airplane with the same altitude, the entering airplane waits until the block is free.
	 * Otherwise, the entering airplane updates its block manager and notifies the previous block manager that it has exited.
	 * 
	 * @param avionManager an AvionManager object representing the entering airplane
	 */
	public synchronized void entered(AvionManager avionManager) {

		if (occupyingAvion != null ) {
			if(occupyingAvion.getAltitude()== avionManager.getAltitude()) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			}
		}
		
		 BlockManager previousBlockManager = avionManager.getBlockManager();
		 avionManager.setBlockManager(this);
		 occupyingAvion = avionManager;
		 previousBlockManager.exit(avionManager);
		 
	
	}
	/**
	 * This method is called when an airplane exits the block.
	 * The occupying airplane is set to null and all waiting airplanes are notified.
	 * 
	 * @param avionManager an AvionManager object representing the exiting airplane
	 */
	public synchronized void exit(AvionManager avionManager) {
		occupyingAvion = null;
		notifyAll();

	}
	/**
	 * Returns true if the block is free, false otherwise.
	 * 
	 * @return a boolean indicating if the block is free
	 */
	public boolean isFree() {
		return occupyingAvion == null;
	}
	/**
	 * Returns the airplane that occupies the block, or null if the block is free.
	 * 
	 * @return an AvionManager object representing the occupying airplane
	 */
	public synchronized AvionManager getOccupyingAvion() {
		return occupyingAvion;
	}

}
