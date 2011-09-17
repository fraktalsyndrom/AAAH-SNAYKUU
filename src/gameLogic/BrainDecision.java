package gameLogic;

class BrainDecision extends Thread
{
	private Brain brain;
	private GameState currentState;
	private Direction nextMove;
	
	public BrainDecision(Brain brain, GameState currentState) 
	{
		this.brain = brain;
		this.currentState = currentState;
	}
	
	public void run() 
	{ 
		nextMove = brain.getNextMove(currentState);
	}
	
	public Direction demandNextMove()
	{
		//~ This snake has taken too long to decide, and will automatically move forward.
		if (isAlive())
		{
			brain.tooSlowFault();
			return new Direction(Direction.FORWARD);
		}
		
		return nextMove;
	}
}
