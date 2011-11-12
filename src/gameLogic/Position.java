package gameLogic;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a coordinate (x and y position) on the game board. All methods requiring
 * coordinates as a parameter should be called using a Position object.
 * 
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 */

public class Position implements Serializable
{
	private int x, y;
	
	/**
	 * Constructs a Position object with the given coordinates.
	 * 
	 * @param	x	The x coordinate.
	 * @param	y	The y coordinate.
	 */
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position(Position position)
	{
		x = position.x;
		y = position.y;
	}
	
	/**
	 * Gets the X coordinate of this position object.
	 * 
	 * @return	The x coordinate of this position.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Gets the Y coordinate of this position object.
	 * 
	 * @return	The y coordinate of this position.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Get a list of all neighbours to this position. One position may
	 * have up to four neighbours (up, down, left and right).
	 * 
	 * @return A list of neighbour positions.
	 */
	public List<Position> getNeighbours()
	{	
		ArrayList<Position> neighbours = new ArrayList<Position>();
		
		neighbours.add(new Position(x, y+1));
		neighbours.add(new Position(x+1, y));
		if (y-1 > -1) neighbours.add(new Position(x, y-1));
		if (x-1 > -1) neighbours.add(new Position(x-1, y));
		return neighbours;
	}
	
	/**
	 * Calculates the manhattan distance (the number of squares that must be traversed) between this Position and another Position.
	 * 
	 * @param	other The Position to measure distance to.
	 * 
	 * @return The manhattan distance between this and other.
	 */
	public int getDistanceTo(Position other)
	{
		return Math.abs(this.x-other.x) + Math.abs(this.y-other.y);
	}
	
	public int hashCode()
	{
		return new Integer(x * 13337).hashCode() + new Integer(y * 13337).hashCode();
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof Position))
			return false;
		
		Position otherPosition = (Position)other;

		return (x == otherPosition.x && y == otherPosition.y);
	}
	
	public String toString()
	{
		String output = "";
		output += "{x: " + x + " y: " + y + "}";
		return output;
	}
}
