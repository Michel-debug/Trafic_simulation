package engine.process;

import java.awt.desktop.PrintFilesEvent;
import java.util.List;

import config.GameConfiguration;
import engine.dynamic.Avion;
import engine.map.Airport;
import engine.map.Block;
import engine.map.Map;
/**
 *  AvionManager method which allows to control the aircraft movement
 *  It's a avion thread class.
 * @author aérien2
 * Date:   2023
 * File : AvionManager.java
 */
public class AvionManager extends Thread {
	//
	private Avion avion;
	private Map map;
	private boolean atterminus = false;
	private boolean Flying = false;
	private MobileElementManager manager;
	private int departureTime;
	private BlockManager[][] blockManagers;
	private BlockManager blockManager;
	private boolean emergencyLanding;
	public boolean waitingToEnter = false;
	public int Altitude_Temporaire;
	private int slowDownDuration = 0;
	private boolean changeAltitude = false;

	/**
	 * Constructor of the AvionManager class.
	 * 
	 * @param manager MobileElementManager
	 * @param avion Avion
	 * @param map Map
	 * @param departureTime int
	 * @param blockManagers BlockManager[][]
	 */
	public AvionManager(MobileElementManager manager, Avion avion, Map map, int departureTime,
			BlockManager[][] blockManagers) {
		super();
		this.avion = avion;
		this.map = map;
		this.manager = manager;
		this.departureTime = departureTime;
		this.emergencyLanding = false;
		int line_initial = avion.getPosition().getLine();
		int column_initial = avion.getPosition().getColumn();
		this.blockManagers = blockManagers;
		this.blockManager = blockManagers[line_initial / GameConfiguration.Canton_Size][column_initial
				/ GameConfiguration.Canton_Size];
	}

	/**
	 * Returns if the plane is flying 
	 * 
	 * @return boolean
	 */

	public boolean isFlying() {
		return Flying;
	}
	/**
	 * This method allows to modify the Flying attribute of the current object
	 * @param Flying
	 */
	public void setFlying(boolean Flying) {
		this.Flying = Flying;
		if (Flying) {
			synchronized (this) {
				notify();
			}
		}
	}
	/**
	 * This method allows to define the destination airport of the current object
	 * @param airport
	 */
	public void setDestinationAirport(Airport airport) {
		Block airport_arriveBlock = airport.getPosition();
		avion.setArrivalPosition(airport_arriveBlock);
	}
	/**
	 * This method allow to get the next BlockManager
	 * 
	 * @param Currentposition Block
	 * @param Destination Block
	 * @return BlockManager
	 */
	private BlockManager getNextBlockManager(Block Currentposition, Block Destination) { // 当前这是一个方向
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int rows = map.getLineCount() / GameConfiguration.Canton_Size;
		int cols = map.getColumnCount() / GameConfiguration.Canton_Size;
		int nextLine = line_avion + Integer.signum(line_airport - line_avion) * 25;
		int nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 25;
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}

		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn / GameConfiguration.Canton_Size];
	}
	/**
	 * Returns the BlockManager corresponding to the path from the current position to the destination.
	 * 
	 * @param Currentposition The current position of the Block.
	 * @param Destination The destination position of the Block.
	 * @return The BlockManager corresponding to the path from the current position to the given destination.
	 */
	private BlockManager getPerpenduleBlockManager(Block Currentposition, Block Destination) {

		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();

		int nextColumn = 0;
		int nextLine = 0;
		nextLine = line_avion + Integer.signum(line_airport - line_avion) * 25;
		nextColumn = column_avion;
		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn / GameConfiguration.Canton_Size];
	}
	/**
	 * This method returns the BlockManager for the next block that is horizonable to the current position of the plane.
	 * @param Currentposition the current position of the plane.
	 * @param Destination the destination block of the plane.
	 * @return the BlockManager for the next horizonable block of the plane.
	 */
	private BlockManager getHorizonableBlockManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextColumn = 0;
		int nextLine = 0;
		nextLine = line_avion;
		nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 25;

		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn / GameConfiguration.Canton_Size];
	}
	/**
	 * Returns the BlockManager for the next block in the plus direction (diagonally)
	 * 
	 * @param Currentposition the current block
	 * @param Destination the destination block
	 * @return the BlockManager for the next block in the plus direction (diagonally)
	 */
	private BlockManager getNextPlusBlockManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextLine = line_avion + Integer.signum(line_airport - line_avion) * 45;
		int nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 45;
		// int rows = map.getLineCount() / GameConfiguration.Canton_Size;
		// int cols = map.getColumnCount() / GameConfiguration.Canton_Size;
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()-GameConfiguration.Canton_Size) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}

		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn
		                                               				/ GameConfiguration.Canton_Size];

	}
	/**
	 * Returns the BlockManager for the next block in the plus direction (diagonally)
	 * 
	 * @param Currentposition the current block
	 * @param Destination the destination block
	 * @return the BlockManager for the nextDroite block in the plus direction (diagonally)
	 */
	private BlockManager getNextDroitBlockManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextLine = line_avion + Integer.signum(column_airport - column_avion) * 25;
		int nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 25;

		if (column_airport != column_avion && line_airport == line_avion) {
			nextLine = line_avion + Integer.signum(column_airport - column_avion) * 45;
		} else if (column_airport == column_avion && line_airport != line_avion) {
			nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 45;
		}
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()-GameConfiguration.Canton_Size) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}
		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn
		                                               				/ GameConfiguration.Canton_Size];

	}
	/**
	 * Returns the BlockManager for the next block in the plus direction (diagonally)
	 * 
	 * @param Currentposition the current block
	 * @param Destination the destination block
	 * @return the BlockManager for the next Gauche block in the plus direction (diagonally)
	 */
	private BlockManager getNextGaucheBlockManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextLine = line_avion + Integer.signum(column_airport - column_avion) * 25;
		int nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 25;

		if (column_airport != column_avion && line_airport == line_avion) {
			nextLine = line_avion - Integer.signum(column_airport - column_avion) * 45;
		} else if (column_airport == column_avion && line_airport != line_avion) {
			nextColumn = column_avion - Integer.signum(column_airport - column_avion) * 45;
		}
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()-GameConfiguration.Canton_Size) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}
		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn
		                                               				/ GameConfiguration.Canton_Size];
	}
	/*
	 *  Voici quelques blockManager qui représentent des zones partagées détectées lors du vol d'un avion dans différentes directions 
	 */
	private BlockManager getNextPlusGaucheBlockManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextLine = line_avion - Integer.signum(line_airport - line_avion) * 25;
		int nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 45;
		// int rows = map.getLineCount() / GameConfiguration.Canton_Size;
		// int cols = map.getColumnCount() / GameConfiguration.Canton_Size;
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()-GameConfiguration.Canton_Size) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}
		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn
		                                               				/ GameConfiguration.Canton_Size];

	}

	private BlockManager getNextPlusDroiteManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextLine = line_avion + Integer.signum(line_airport - line_avion) * 45;
		int nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 25;
		// int rows = map.getLineCount() / GameConfiguration.Canton_Size;
		// int cols = map.getColumnCount() / GameConfiguration.Canton_Size;
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()-GameConfiguration.Canton_Size) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}

		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn
		                                               				/ GameConfiguration.Canton_Size];

	}

	private BlockManager getNextPlusGaucheGaucheeManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextLine = line_avion + Integer.signum(line_airport - line_avion) * 45;
		int nextColumn = column_avion;
		// int rows = map.getLineCount() / GameConfiguration.Canton_Size;
		// int cols = map.getColumnCount() / GameConfiguration.Canton_Size;
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()-GameConfiguration.Canton_Size) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}
		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn
		                                               				/ GameConfiguration.Canton_Size];

	}

	private BlockManager getNextPlusDroiteDroiteManager(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int nextLine = line_avion;
		int nextColumn = column_avion + Integer.signum(column_airport - column_avion) * 45;
		// int rows = map.getLineCount() / GameConfiguration.Canton_Size;
		// int cols = map.getColumnCount() / GameConfiguration.Canton_Size;
		if (nextColumn < 0 ) {
			nextColumn = 0;
			
		}
		if (nextColumn >= map.getColumnCount()) {
			nextColumn = map.getColumnCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][(nextColumn / GameConfiguration.Canton_Size)-1];
		}
		if (nextLine >= map.getLineCount()-GameConfiguration.Canton_Size) {
			nextLine = map.getLineCount();
			return blockManagers[(nextLine / GameConfiguration.Canton_Size)-1][nextColumn / GameConfiguration.Canton_Size];
		}
		if (nextLine < 0) {
			nextLine = 0;
		}
		return blockManagers[nextLine / GameConfiguration.Canton_Size][nextColumn
		                                               				/ GameConfiguration.Canton_Size];

	}

	/**
	 * Sets a new random destination for the current object.
	 * The destination is chosen among the airport positions defined in the game configuration.
	 * The destination is different from the current arrival position of the airplane object.
	*/

	public synchronized void setNewDestination() {
		Block newDestinationBlock = GameConfiguration.Airport_POSITION[SimulationUtility.getRandom(0,
				GameConfiguration.Airport_POSITION.length - 1)];
		while (newDestinationBlock.equals(avion.getArrivalPosition())) {
			newDestinationBlock = GameConfiguration.Airport_POSITION[SimulationUtility.getRandom(0,
					GameConfiguration.Airport_POSITION.length - 1)];
		}
		avion.setArrivalPosition(newDestinationBlock);
	}
	/**
	 * Returns the current position of the airplane object.
	 * @return a Block object representing the current position 
	 */
	public synchronized Block getPosition() {
		return avion.getCurrentPosition();
	}
	/**
	 * Returns the departure position of the airplane object.
	 * @return a Block object representing the departure position
	 */
	public synchronized Block getdepartPosition() {
		return avion.getDepartPosition();
	}
	/**
	 * Sets the departure position of the airplane object.
	 * @param position a Block object representing the new departure position
	 */
	
	public void setDepartPosition(Block position) {
		avion.setDepartPosition(position);
	}
	/**
	 * Returns the arrival position of the airplane object.
	 * @return a Block object representing the arrival position 
	 */
	public synchronized Block getArrivale_airport() {
		return avion.getArrivalPosition();
	}
	/**
	 * Returns the identifier of the airplane object.
	 * @return a String representing the identifier
	 */
	public String getAvionId() {
		return avion.getId();
	}
	/**
	 * Returns the speed stock of the airplane object.
	 * @return an int representing the speed stock 
	 */
	
	public int speed_stock() {
		return avion.getSpeed_stock();
	}
	/**
	 * Updates the current position of the airplane object.
	 * @param position a Block object representing the new current position
	 */
	public void updatePosition(Block position) {
		avion.setCurrentPosition(position);
	}
	/**
	 * Returns the departure time of the current object.
	 * @return an int representing the departure time
	 */
	public int getDepartureTime() {
		return departureTime;
	}
	/**
	 * Sets the departure time of the current object.
	 * @param departureTime an int representing the new departure time 
	 */
	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}
	/**
	 * Returns true if the current object is at terminus, false otherwise.
	 * @return a boolean indicating if the current object is at terminus 
	 */
	public boolean isAtTerminus() {
		return atterminus;
	}
	/**
	 * Returns the altitude of the airplane object.
	 * @return a double representing the altitude
	 */
	public double getAltitude() {
		return avion.getAltitude();
	}
	/**
	 * Sets the flying altitude of the airplane object.
	 * If the altitude is greater than 5000, it decreases it by 2000. 
	 */
	public void setFlying_Altitude() {
		if (getAltitude() > 5000) {
			avion.setAltitude(getAltitude() - 2000);
		}
	}
	/**
	 * Resets the altitude of the airplane object to zero.
	 */
	public void reset_Altitude() {
		avion.setAltitude(0);
	}
	/**
	 * Sets the speed of the airplane object.
	 * @param speed an int representing the new speed
	 */
	public void setSpeed(int speed) {
		avion.setSpeed(speed);
	}
	/**
	 * Returns the speed of the airplane object.
	 * @return an int representing the speed
	 */
	
	public int getSpeed() {
		return avion.getSpeed();
	}
	/**
	 * Sets the emergency landing flag of the current object.
	 * @param emergencyLanding a boolean indicating if there is an emergency landing 
	 */
	public void setEmergencyLanding(boolean emergencyLanding) {
		this.emergencyLanding = emergencyLanding;
	}
	/**
	 * Returns true if there is an emergency landing for the current object, false otherwise.
	 * @return a boolean indicating if there is an emergency landing 
	 */
	public boolean isEmergencyLanding() {
		return emergencyLanding;
	}
	/**
	 * Returns true if the current object is too close to another airplane object, false otherwise.
	 * The distance between the two airplanes is calculated using their current positions.
	 * 
	 * @param otherAvion an AvionManager object representing the other airplane
	 * @param minDistance a double representing the minimum distance allowed
	 * @return a boolean indicating if the current object is too close to the other airplane
	 */
	public boolean isTooCloseAnotherAvion(AvionManager otherAvion, double minDistance) {
		Block currentPosition = this.getPosition();
		Block otherPosition = otherAvion.getPosition();
		double distance = currentPosition.CalculateDistance(otherPosition);
		return distance < minDistance;
	}
	/**
	 *Moves the current object towards its destination.
	 *The movement depends on the difference between the current position and the destination position.
	 *The speed of the airplane object is adjusted according to the distance to the destination.
	 *The state of the airplane object is changed to LANDING if the distance is less than 120.
	 *The altitude of the airplane object is updated after each movement.
	 *
	 *@param Currentposition a Block object representing the current position
	 *@param Destination a Block object representing the destination position
	 */
	public synchronized void deplacement_avion(Block Currentposition, Block Destination) {
		int column_airport = Destination.getColumn();
		int line_airport = Destination.getLine();
		int column_avion = Currentposition.getColumn();
		int line_avion = Currentposition.getLine();
		int ecart_column = column_avion - column_airport;
		int ecart_line = line_avion - line_airport;
		if (ecart_column > 0) { // mettre a jour column
			if (ecart_column > 20) {
				int speed = avion.getSpeed(); // voler suivant la speed normal
				column_avion -= speed;
			} else {

				column_avion -= 1; // atterrir, diminution de vitesse
			}
		} else if (ecart_column < 0) {
			if (ecart_column < -20) {
				int speed = avion.getSpeed(); // voler suivant la speed normal
				column_avion += speed;
			} else {

				column_avion += 1; // atterrir, diminution de vitesse
			}

		}
		if (ecart_line > 0) {
			if (ecart_line > 20) {
				int speed = avion.getSpeed(); // voler suivant la speed normal
				line_avion -= speed;
			} else {
				line_avion -= 1; // atterrir, diminution de vitesse
			}
		} else if (ecart_line < 0) {
			if (ecart_line < -20) {
				int speed = avion.getSpeed(); // voler suivant la speed normal
				line_avion += speed;
			} else {
				line_avion += 1; // atterrir, diminution de vitesse
			}
		}
		ecart_column = column_avion - column_airport;
		ecart_line = line_avion - line_airport;
		double distance = Math.sqrt(Math.pow(ecart_line, 2) + Math.pow(ecart_column, 2));
		if (distance < 120) {
			avion.setState(engine.dynamic.Avion.State.LANDING);
		}
		updatePosition(new Block(line_avion, column_avion));
		avion.updateAltitude();
	}
	/**
	 * Sets the flying state of the airplane object to LANDING
	 */
	public void setFlyingState() {
		avion.setState(engine.dynamic.Avion.State.LANDING);
	}
	/**
	 * Returns the block manager of the current object.
	 * @return a BlockManager object representing the block manager 
	 */
	public BlockManager getBlockManager() {
		return blockManager;
	}
	/**
	 * Sets the block manager of the current object.
	 * @param blockManager a BlockManager object representing the new block manager
	 */
	public synchronized void setBlockManager(BlockManager blockManager) {
		this.blockManager = blockManager;
	}
	/**
	 * Increases the altitude of the airplane object. 
	 */
	public void Remonte_altitude() {
		avion.Remonter_Altitude();
	}
	/**
	 * Sets the temporary altitude of the airplane object to match another airplane object’s altitude.
	 * @param autreAvionManager an AvionManager object representing the other airplane 
	 */
	public void set_Altitude_temporaire(AvionManager autreAvionManager) {
		avion.setAltitude_temporaire(autreAvionManager.getAltitude());
	}
	/**
	 * Method that represents the running of an airplane thread.
	 * The airplane waits until its departure time, then takes off and flies towards its destination.
	 * While flying, the airplane checks for obstacles and adjusts its speed and altitude accordingly.
	 * If it reaches its destination, it lands and sets a new destination for its next flight.
	 */
	@Override
	public void run() {

		int heure_decollage = 0;
		while (heure_decollage < avion.getDepartureTime()) {
			synchronized (this) {
				try {
					wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			heure_decollage++;
		}
		setFlying(true);

		while (Flying) {
			SimulationUtility.unitTime();
			Block Currentposition = avion.getCurrentPosition();
			int column_avion = Currentposition.getColumn();
			int line_avion = Currentposition.getLine();
			Block Destination = avion.getArrivalPosition(); // obtenir la destination de chaque avion

			int column_airport = Destination.getColumn();
			int line_airport = Destination.getLine();
			BlockManager free_BlockManager = blockManagers[line_airport / GameConfiguration.Canton_Size][column_airport
					/ GameConfiguration.Canton_Size];
			free_BlockManager.exit(this);
			int ligne = Integer.signum(line_airport - line_avion);
			int Column = Integer.signum(column_airport - column_avion);
			if (ligne != 0 && Column != 0) {
				BlockManager Perpendiculaire = getPerpenduleBlockManager(Currentposition, Destination);
				BlockManager nextPlusBlockManager = getNextPlusBlockManager(Currentposition, Destination);
				BlockManager horizonManager = getHorizonableBlockManager(Currentposition, Destination);

				BlockManager nextplusGauchBlockManager = getNextPlusGaucheBlockManager(Currentposition, Destination);
				BlockManager nextplusDroiteBlockManager = getNextPlusDroiteManager(Currentposition, Destination);
				BlockManager nextPlusGGBlockManager = getNextPlusGaucheGaucheeManager(Currentposition, Destination);
				BlockManager nextPlusDDBlockManager = getNextPlusDroiteDroiteManager(Currentposition, Destination);
				BlockManager nextBlockManager = getNextBlockManager(Currentposition, Destination);

				AvionManager occupyingPAvionManager = Perpendiculaire.getOccupyingAvion();
				AvionManager occupyingHAvionManager = horizonManager.getOccupyingAvion();
				AvionManager occupyingNAvionManager = nextPlusBlockManager.getOccupyingAvion();

				AvionManager occ4 = nextplusGauchBlockManager.getOccupyingAvion();
				AvionManager occ5 = nextplusDroiteBlockManager.getOccupyingAvion();
				AvionManager occ6 = nextPlusGGBlockManager.getOccupyingAvion();
				AvionManager occ7 = nextPlusDDBlockManager.getOccupyingAvion();
				AvionManager occ8 = nextBlockManager.getOccupyingAvion();
				if (occupyingPAvionManager != null || occupyingHAvionManager != null || occupyingNAvionManager != null
						|| occ4 != null || occ5 != null || occ6 != null || occ7 != null || occ8 != null) {
					slowDownDuration = 50;
					changeAltitude = true;
					avion.setSpeed(2);
					avion.Landing_Altitude();
				}
			}

			else {
				BlockManager nextBlockManager = getNextBlockManager(Currentposition, Destination);
				BlockManager nextBlockDroitManager = getNextDroitBlockManager(Currentposition, Destination);
				BlockManager nextBlockGaucheManager = getNextGaucheBlockManager(Currentposition, Destination);
				BlockManager nextPlusBlockManager = getNextPlusBlockManager(Currentposition, Destination);

				AvionManager occupyingNAvionManager = nextBlockManager.getOccupyingAvion();
				AvionManager occupyingNDAvionManager = nextBlockDroitManager.getOccupyingAvion();
				AvionManager occupyingNGAvionManager = nextBlockGaucheManager.getOccupyingAvion();
				AvionManager occupyingNPAvionManager = nextPlusBlockManager.getOccupyingAvion();
				if (occupyingNAvionManager != null) {
					avion.setAltitude_temporaire(occupyingNAvionManager.getAltitude());
					slowDownDuration = 100;
					avion.setSpeed(2);
				} else if (occupyingNDAvionManager != null) {
					avion.setAltitude_temporaire(occupyingNDAvionManager.getAltitude());
					slowDownDuration = 100;
					avion.setSpeed(2);
				} else if (occupyingNGAvionManager != null) {
					avion.setAltitude_temporaire(occupyingNGAvionManager.getAltitude());
					slowDownDuration = 100;
					avion.setSpeed(2);
				} else if (occupyingNPAvionManager != null) {
					avion.setAltitude_temporaire(occupyingNPAvionManager.getAltitude());
					slowDownDuration = 100;
					avion.setSpeed(2);
				}
			}
			if (slowDownDuration > 0) {
				slowDownDuration--;
				if (changeAltitude == true)
					avion.Landing_Altitude();
				if (slowDownDuration == 0) {
					avion.setSpeed(avion.getSpeed_stock()); // 恢复原速度
					changeAltitude = false;
				}
			} else {
				double distance = Math
						.sqrt(Math.pow(line_airport - line_avion, 2) + Math.pow(column_airport - column_avion, 2));
				if (distance > 120) {
					avion.Remonter_Altitude();
				}
				avion.setSpeed(avion.getSpeed_stock());
			}

			// 飞机飞行代码 之后要封装到函数中
			if (Currentposition.equals(Destination)) { // 该函数让飞机到达目的地后重新刷新数据,刷新新的飞机场
				atterminus = true;
				this.reset_Altitude();
				BlockManager currentManager = this.getBlockManager();
				currentManager.exit(this);
				setNewDestination();
				avion.setSpeed(avion.getSpeed_stock());
				avion.setState(engine.dynamic.Avion.State.TAKEOFF);
				avion.setDepartPosition(getPosition());
				// mettre à jour l'heure de départ，
				avion.setDepartureTime(SimulationUtility.getRandom(0, GameConfiguration.Temps_depart));
				int time = avion.getDepartureTime();
				setDepartureTime(time);
				System.out.println("ID:" + avion.getId() + " heure" + time);
				this.emergencyLanding = false;
				try {
					sleep(time * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				BlockManager newBlockManager = blockManagers[line_avion / GameConfiguration.Canton_Size][column_avion
						/ GameConfiguration.Canton_Size];
				if (newBlockManager != blockManager) {
					blockManager.exit(this);
					newBlockManager.entered(this);
					blockManager = newBlockManager;
				}
				deplacement_avion(Currentposition, Destination);
			}
		}
	}

}
