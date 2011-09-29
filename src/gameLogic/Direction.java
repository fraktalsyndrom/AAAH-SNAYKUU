package gameLogic;

public enum Direction
{
	NORTH, WEST, SOUTH, EAST;
	
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

	static Direction getDirectionFromPositionToPosition(Position from, Position to)
	{
		if (from.equals(to))
			throw new IllegalArgumentException("Don't force us to divide by zero please");
			//~ TODO: Wouldn't this be dividing by one and not zero? Investigate.
		
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
		
		throw new IllegalArgumentException("The two points must be.");
	}
}