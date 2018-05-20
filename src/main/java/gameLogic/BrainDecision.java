package gameLogic;

import java.util.concurrent.TimeoutException;

/**
 * @author 	Sixten Hilborn
 * @author	Arian Jafari
 */
class BrainDecision extends Thread
{
	private Snake snake;
	private GameState currentState;
	private Direction nextMove;
	private Throwable exception = null;
	
	public BrainDecision(Snake snake, GameState currentState) 
	{
		this.snake = snake;
		this.currentState = currentState;
	}
	
	public void run() 
	{
		try
		{
			nextMove = snake.getBrain().getNextMove(snake, currentState);
		}
		catch (Throwable t)
		{
			exception = t;
		}
	}
	
	@SuppressWarnings("deprecation")
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