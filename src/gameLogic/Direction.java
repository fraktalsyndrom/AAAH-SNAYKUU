package gameLogic;

/**
 * This is an enum representing a direction in which a snake can move. Directions are
 * what getNextMove in the Brain interface are supposed to return.
 * Note that these are absolute directions, e.g. NORTH, and not relative
 * directions, e.g. FORWARD.
 * 
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 *
 */

public enum Direction
{
	NORTH, WEST, SOUTH, EAST;
	
	/**
	 * Gets the next position a snake would end up in if it continues in this direction.
	 * 
	 * @return	The next position if movement continues in this direction.
	 */
	public Position calculateNextPosition(Position oldPosition)
	{
		int x = oldPosition.getX(), y = oldPosition.getY();
		
		switch (this)
		{
			case WEST:
				--x;
				break;
			
			case NORTH:
				--y;
				break;
			
			case EAST:
				++x;
				break;
			
			case SOUTH:
				++y;
				break;
		}
		
		return new Position(x, y);
	}
	
	/**
	 * Returns a new direction that would be the same as turning left.
	 * 
	 * @return	The direction towards which one would move if one turned left.
	 */
	public Direction turnLeft()
	{
		switch (this)
		{
			case WEST:
				return SOUTH;
			
			case SOUTH:
				return EAST;
			
			case EAST:
				return NORTH;
			
			case NORTH:
				return WEST;
		}
		
		throw new IllegalStateException("This direction is invalid");
	}
	
	/**
	 * Returns a new direction that would be the same as turning right.
	 * 
	 * @return	The direction towards which one would move if one turned right.
	 */
	public Direction turnRight()
	{
		switch (this)
		{
			case WEST:
				return NORTH;
			
			case NORTH:
				return EAST;

			case EAST:
				return SOUTH;
			
			case SOUTH:
				return WEST;
		}
		
		throw new IllegalStateException("This direction is invalid");
	}

	/**
	 * Returns the direction between two positions, as long as they are on a straight line.
	 * 
	 * @param	from		The starting position
	 * @param	to		The destination position.
	 *
	 * @return	The direction towards which one needs to move from the starting position
	 *			in order to reach the destination.
	 * 
	 * @throws	java.lang.IllegalArgumentException either if the two positions are the same
	 *			or if they are not on a straight line.
	 */
	public static Direction getDirectionFromPositionToPosition(Position from, Position to)
	{
		if (from.equals(to))
			throw new IllegalArgumentException("Don't force us to divide by zero please");
		
		if (from.getX() == to.getX())
		{
			if (from.getY() < to.getY())
				return Direction.SOUTH;
			else if (from.getY() > to.getY())
				return Direction.NORTH;
		}
		else if (from.getY() == to.getY())
		{
			if (from.getX() < to.getX())
				return Direction.EAST;
			else if (from.getX() > to.getX())
				return Direction.WEST;
		}
		
		throw new IllegalArgumentException("The two points must be on a line.");
	}
}
