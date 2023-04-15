package engine.dynamic;

import engine.map.Block;
/**
 * This class represents a mobile element on a map.
 * @author aérien2
 * Date:   2023
 * File : MobileElement.java
 */
public abstract class MobileElement {
	private Block position;

	/**
	 * Constructs a new mobile element with the given position.
	 * 
	 * @param position the block where the mobile element is located.
	 */
	public MobileElement(Block position) {
		this.position = position;
	}
	/**
	 * Returns the position of the mobile element.
	 * 
	 * @return the block where the mobile element is located.
	 */
	public Block getPosition() {
		return position;
	}
	/**
	 * Sets the position of the mobile element.
	 * 
	 * @param position the new block where the mobile element is located.
	 */
	public void setPosition(Block position) {
		this.position = position;
	}

	
}
