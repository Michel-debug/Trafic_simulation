package engine.dynamic;

import java.util.List;

import engine.map.Block;
import engine.process.AvionManager;
/**
 *  This class represents an aircraft
 * 
 * @author aérien2
 * Date:   2023
 * File : Avion.java
 */
public class Avion extends MobileElement {
	public enum State {
		TAKEOFF, CRUISE, LANDING
	};

	private State state;
	private String id;
	private int speed;
	private int speed_stock;
	private Block airport_depart;
	private Block airport_depart_vrai;
	private Block airport_arrive;
	private volatile int departureTime;
	private double Altitude = 0;
	private double Altitude_temporaire = 0;
	private int targetAltitude = 10000;
	
	public Avion(String id, int speed, Block airport_depart, int departureTime, Block airport_arrive) {
		super(airport_depart);
		this.airport_depart = airport_depart;
		this.airport_depart_vrai = airport_depart;
		this.id = id;
		this.speed = speed;
		this.departureTime = departureTime;
		this.state = State.TAKEOFF;
		this.airport_arrive = airport_arrive;
		this.speed_stock = speed;
	}
	/**
	 * Returns the stock speed of the airplane.
	 * 
	 * @return the stock speed of the airplane.
	 */
	public int getSpeed_stock() {
		return speed_stock;
	}
	/**
	 * Updates the altitude of the airplane according to its state.
	 */
	public void updateAltitude() {
		switch (state) {
		case TAKEOFF:
			Altitude += 300;
			if (Altitude >= 6000) {
				Altitude += 100;
				if (Altitude >= targetAltitude) {
					Altitude = targetAltitude;
					state = State.CRUISE;
				}
			}
			break;
		case CRUISE:
			// No altitude change during cruising
			break;
		case LANDING:
			if (Altitude > 0) {
				if (speed > 3 && Altitude > 1000)
					Altitude -= 300;
				else {
					Altitude -= 200;
				
				if (Altitude <= 400 && Altitude > 0) {
					Altitude -= 20;
					if (Altitude <= 100 && Altitude > 0) {
						Altitude -= 5;
					}
				}
				// 限制 Altitude 不小于 0
				Altitude = Math.max(0, Altitude);
				}
			}
			break;

		}
	}
	/**
	 * Updates the altitude of the airplane when landing.
	 * The altitude decreases gradually until it reaches zero.
	 */
	public void Landing_Altitude() {
		
		if (Altitude == Altitude_temporaire && Altitude_temporaire > 5000) {
			 Altitude = Altitude_temporaire - 400;
		}else if(Math.abs(Altitude-Altitude_temporaire)<1000 && Altitude_temporaire > 3000) {
			 Altitude-=30;
		}
	}

	/**
	 * Updates the altitude of the airplane when climbing.
	 * The altitude increases gradually until it reaches 10000.
	 */
	
	public void Remonter_Altitude() {
		if (Altitude <= 10000) {
			Altitude += 200;
			if (Altitude > 10000) {
				Altitude = 10000;
			}
		}
	}

	/**
	 * Sets the state of the airplane.
	 * 
	 * @param state the new state of the airplane.
	 */
	public void setState(State state) {
		this.state = state;
	}
	/**
	 * Returns the id of the airplane.
	 * 
	 * @return the id of the airplane.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the speed of the airplane.
	 * 
	 * @return the speed of the airplane
	 */
	public int getSpeed() {
		return speed;
	}
	/**
	 * Sets the speed of the airplane.
	 * 
	 * @param speed the new speed of the airplane.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	/**
	 * Returns the departure position of this flight.
	 * 
	 * @return a Block object representing the departure position.
	 */
	public Block getDepartPosition() {
		return airport_depart_vrai;
	}
	/**
	 * Sets the departure position of this flight.
	 * 
	 * @param departBlock a Block object representing the departure position.
	 */
	public void setDepartPosition(Block departBlock) {
		this.airport_depart_vrai = departBlock;
	}
	/**
	 * Returns the current position of this flight.
	 * This method is synchronized to avoid concurrency issues.
	 * 
	 * @return a new Block object representing the current position.
	 */
	public synchronized Block getCurrentPosition() {
		return new Block(airport_depart.getLine(), airport_depart.getColumn());
	}
	/**
	 * Sets the current position of this flight.
	 * This method is synchronized to avoid concurrency issues.
	 * 
	 * @param currentPosition a Block object representing the current position. 
	 */
	public synchronized void setCurrentPosition(Block currentPosition) {
		airport_depart = currentPosition;
	}
	/**
	 * Returns the arrival position of this flight.
	 * This method is synchronized to avoid concurrency issues.
	 * 
	 * @return a Block object representing the arrival position. 
	 */
	public synchronized Block getArrivalPosition() {
		return airport_arrive;
	}
	/**
	 * Sets the arrival position of this flight.
	 * This method is synchronized to avoid concurrency issues.
	 * 
	 * @param arrivalPosition a Block object representing the arrival position.
	 */
	public synchronized void setArrivalPosition(Block arrivalPosition) {
		airport_arrive = arrivalPosition;
	}
	/**
	 * Returns the altitude of this flight.
	 * 
	 * @return a double value representing the altitude in meters. 
	 */
	public double getAltitude() {
		return Altitude;
	}
	/**
	 * Sets the altitude of this flight.
	 * 
	 * @param altitude a double value representing the altitude in meters. 
	 */
	public void setAltitude(double altitude) {
		Altitude = altitude;
	}
	/** 
	 * Returns the departure time of this flight.
	 * 
	 * @return an int value representing the departure time in minutes.
	*/
	public int getDepartureTime() {
		return departureTime;
	}
	/**
	 * Sets the departure time of this flight.
	 * This method is synchronized to avoid concurrency issues.
	 * 
	 * @param departureTime an int value representing the departure time in minutes.
	 */
	public synchronized void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}
	/**
	 * Returns the temporary altitude of this flight.
	 * This method is synchronized to avoid concurrency issues.
	 * 
	 * @return a double value representing the temporary altitude in meters.
	 */
	public synchronized double getAltitude_temporaire() {
		return Altitude_temporaire;
	}
	/**
	 * Sets the temporary altitude of this flight.
	 * 
	 * @param altitude_temporaire a double value representing the temporary altitude in meters. 
	 */
	public void setAltitude_temporaire(double altitude_temporaire) {
		Altitude_temporaire = altitude_temporaire;
	}

}
