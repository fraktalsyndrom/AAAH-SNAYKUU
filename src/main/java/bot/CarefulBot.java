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
			return previousDirection.turnLeft();
		}
		return previousDirection;
	}
}
