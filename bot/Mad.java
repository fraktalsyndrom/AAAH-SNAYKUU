package bot;

import gameLogic.*;

public class Mad implements Brain
{
	private GameState gameState;
	
	public void init(GameState initialState)
	{
		gameState = initialState;
	}
	
	public Direction getNextMove(GameState gamestate)
	{
		return Direction.NORTH;
	}
}
