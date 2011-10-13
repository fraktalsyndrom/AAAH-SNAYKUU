package bot;

import gameLogic.*;

public class Slow implements Brain
{
	public Direction getNextMove(Snake yourSnake, GameState gameState)
	{
		System.out.println(gameState.getErrorState());
		//~ Sleep for ten seconds before making the decision to continue onward.
		try { Thread.sleep(10000); }
		catch (InterruptedException e) { System.out.println(e); }
		return Direction.WEST;
	}
}
