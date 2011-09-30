package gameLogic;

import java.util.List;
import java.util.ArrayList;

public class Position 
{
	private int x, y;
	
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
	
	public int getX()
	{
		return x;
	}
	
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
