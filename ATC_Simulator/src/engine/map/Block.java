package engine.map;
/**
 *  This class represents a block on a map with a row and a column.
 * 
 * @author aérien2
 * Date:   2023
 * File : Block.java
 */
public class Block {
	private int line;
	private int column;

	public Block(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	/**
	 * Calculates the distance between this block and another block using the Pythagorean theorem.
	 * 
	 * @param autreBlock the other block to calculate the distance to
	 * @return the distance between this block and the other block
	 */
	public double CalculateDistance(Block autreBlock) {
		int line_autre = autreBlock.getLine();
		int column_autre = autreBlock.getColumn();
		int ecart_line = Math.abs(this.getLine()-line_autre);
		int ecart_column = Math.abs(this.getColumn()-column_autre);
		double distance = Math.sqrt(Math.pow(ecart_line, 2) + Math.pow(ecart_column, 2));
		return distance;
	}
	/**
	 * Returns a string representation of the block with its line and column.
	 * 
	 * @return a string representing the block with its line and column
	 */
	@Override
	public String toString() {
		return "Block [line=" + line + ", column=" + column + "]";
	}
	/**
	 * Returns the hash code of the block based on its line and column.
	 * 
	 * @return an integer representing the hash code of the block
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + line;
		return result;
	}

	/**
	 * Compares the block to another object to check if they represent the same block.
	 * 
	 * @param obj an object to compare with the block
	 * @return true if the object represents the same block, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (column != other.column)
			return false;
		if (line != other.line)
			return false;
		return true;
	}
	

}
