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
	
	public Position() 
	{
		x = 0;
		y = 0;
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
	
	public static List<Position> getNeighbours(Position position)
	{
		ArrayList<Position> neighbours = new ArrayList<Position>();
		int x = position.getX();
		int y = position.getY();

		neighbours.add(new Position(x, y+1));
		neighbours.add(new Position(x+1, y));
		if (y-1 > -1) neighbours.add(new Position(x, y-1));
		if (x-1 > -1) neighbours.add(new Position(x-1, y));
		return neighbours;
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
