package gameLogic;

import java.util.concurrent.TimeoutException;

class BrainDecision extends Thread
{
	private Brain brain;
	private GameState currentState;
	private Direction nextMove;
	private Throwable exception = null;
	
	public BrainDecision(Brain brain, GameState currentState) 
	{
		this.brain = brain;
		this.currentState = currentState;
	}
	
	public void run() 
	{
		try
		{
			nextMove = brain.getNextMove(currentState);
		}
		catch (Throwable t)
		{
			exception = t;
		}
	}
	
	public Direction demandNextMove() throws Throwable
	{
		//~ This snake has taken too long to decide, and will automatically move forward.
		if (isAlive())
		{
			stop();
			throw new TimeoutException("The brain has taken too long to decide. Summon the minions.");
		}
		
		if (exception != null)
			throw exception;
		
		return nextMove;
	}
}
