package bot;

import gameLogic.*;

public class MultipleClassBotHelpCollection
{
	int currentIndex;
	Direction[] directions = new Direction[4];
	
	public MultipleClassBotHelpCollection()
	{
		currentIndex = 0;
		directions[0] = Direction.NORTH;
		directions[1] = Direction.WEST;
		directions[2] = Direction.SOUTH;
		directions[3] = Direction.EAST;
	}
	
	public void setStartDirection(Direction direction)
	{
		switch (direction)
		{
			case NORTH:
				currentIndex = 1;
				break;
			case WEST:
				currentIndex = 2;
				break;
			case SOUTH:
				currentIndex = 3;
				break;
			case EAST:
				currentIndex = 4;
				break;
			default:
				throw new IllegalArgumentException("lol");
		}
	}
	
	public Direction getNextDirection()
	{
		Direction outputDirection = directions[currentIndex++];
		if (currentIndex > 3)
			currentIndex = 0;
		return outputDirection;
	}
}