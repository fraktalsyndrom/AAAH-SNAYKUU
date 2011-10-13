package bot;

import gameLogic.*;

public class CarefulBot implements Brain
{
	private GameState gamestate;
	private Snake self;
	
	public Direction getNextMove(Snake yourSnake, GameState gamestate)
	{
		self = yourSnake;
		this.gamestate = gamestate;
		
		Direction previousDirection = self.getCurrentDirection();
		if (gamestate.willCollide(self, previousDirection))
		{
			switch (previousDirection)
			{
				case NORTH:
					return Direction.WEST;
				case WEST:
					return Direction.SOUTH;
				case SOUTH:
					return Direction.EAST;
				case EAST:
					return Direction.NORTH;
				default:
					return previousDirection;
			}
		}
		return previousDirection;
	}
}