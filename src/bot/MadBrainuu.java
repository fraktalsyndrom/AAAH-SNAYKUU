package bot;

import gameLogic.*;

public class MadBrainuu implements Brain
{
	private GameState gameState;
	
	public void init(GameState initialState)
	{
		gameState = initialState;
	}
	
	public Direction getNextMove(GameState gamestate)
	{
		return new Direction(Direction.NORTH);
	}
	
	public void tooSlowFault()
	{
		System.out.println("FUCK YOU DOLPHIN I AM WHALE");
	}

}
