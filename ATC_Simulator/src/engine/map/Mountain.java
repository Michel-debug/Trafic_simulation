package engine.map;

import engine.dynamic.MobileElement;
/**
 * This class represents a mountain on a map.
 * 
 * @author aérien2
 * Date:   2023
 * File : Mountain.java
 */
public class Mountain extends MobileElement {
	
	private String name;
	private double Altitude;

	/**
	 * Constructor mountain.
	 * 
	 * @param position the block where the mountain is located.
	 * @param Altitude the altitude of the mountain.
	 * @param name the name of the mountain.
	 */
	public Mountain(Block position,double Altitude,String name) {
		super(position);
		this.Altitude= Altitude;
		this.name = name;
	}
	public double getAltitude() {
		return Altitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAltitude(double altitude) {
		Altitude = altitude;
	}
	

}
