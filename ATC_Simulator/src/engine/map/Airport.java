package engine.map;

//import java.util.ArrayList;
//import java.util.Queue;

import engine.dynamic.MobileElement;
/**
 *  This class allows to create an airport
 * 
 * @author aérien2
 * Date:   2023
 * File : Airport.java
 */
public class Airport extends MobileElement {
	

	private String name;
//	private Block position;
	public Airport(String name, Block position) {
		super(position);
		this.name=name;
	}
	public String getName() {
		return name;
	}
	
	
}

	



