
public class Direction
{
	public static final int NORTH = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 3;
	public static final int EAST = 4;
	public static final int FORWARD = 5;
	public static final int LEFT = 6;
	public static final int RIGHT = 7;

	private int direction;

	public Direction(int direction)
	{
		if (direction < NORTH || direction > EAST)
			throw new IllegalArgumentException("We need an absolute direction!");
		
		this.direction = direction;
	}
	
	public Direction(int originalDirection, int relativeDirection)
	{
		if (originalDirection < NORTH || originalDirection > EAST)
			throw new IllegalArgumentException("We need an valid original direction!");
		
		direction = originalDirection;
		
		switch (relativeDirection)
		{
			case LEFT:
				--direction;
				break;
			
			case RIGHT:
				++direction;
				break;
			
			case FORWARD:
				break;
			
			default:
				throw new IllegalArgumentException("wat, gief relative direction plz");
		}
		
		if (direction < NORTH)
			direction = EAST;
		
		if (direction > EAST)
			direction = NORTH;
			
		
	}
	
	public Position calculateNextPosition(Position oldPosition)
	{
		int x = oldPosition.getX(), y = oldPosition.getY();
		
		switch (direction)
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
	
	public boolean equals(Object other)
	{
		if (!(other instanceof Direction))
			return false;
		
		Direction otherDirection = (Direction)other;
		
		return direction == otherDirection.direction;
	}
	
	static Direction getDirectionFromPositionToPosition(Position from, Position to)
	{
		if (from.equals(to))
			throw new IllegalArgumentException("Don't force us to divide by zero please");
		
		if (from.getX() == to.getX())
		{
			if (from.getY() < to.getY())
				return new Direction(SOUTH);
			else if (from.getY() > to.getY())
				return new Direction(NORTH);
		}
		else if (from.getY() == to.getY())
		{
			if (from.getX() < to.getX())
				return new Direction(EAST);
			else if (from.getX() > to.getX())
				return new Direction(WEST);
		}
		
		throw new IllegalArgumentException("The two points must be.");
	}
}
