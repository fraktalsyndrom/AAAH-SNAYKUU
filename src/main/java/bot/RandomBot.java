package bot;

import gameLogic.*;
import java.util.Random;

public class RandomBot implements Brain
{
	private Random random = new Random();
	
	public Direction getNextMove(Snake yourSnake, GameState gamestate)
	{
		switch (random.nextInt(4))
		{
		case 0:
			return Direction.NORTH;
		
		case 1:
			return Direction.WEST;
		
		case 2:
			return Direction.SOUTH;
		
		case 3:
			return Direction.EAST;
		}
		
		return Direction.NORTH;
	}
}
