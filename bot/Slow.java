package bot;

import gameLogic.*;

public class Slow implements Brain
{
	private GameState gameState;
	
	public void init(GameState initialState)
	{
		gameState = initialState;
	}
	
	public Direction getNextMove(GameState gameState)
	{
		//~ Sleep for ten seconds before making the decision to continue onward.
		try { Thread.sleep(10000); }
		catch (InterruptedException e) { System.out.println(e); }
		return Direction.WEST;
	}
	
	public void tooSlowFault()
	{
		System.out.println("WHADDAYAMEEEAAAN I'M TOO SLOW!?");
	}
}