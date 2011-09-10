package gameLogic;

public class BrainDecision extends Thread
{
	private Snake snake;
	private GameState currentState;
	private Direction nextMove;
	
	public BrainDecision(Snake snake, GameState currentState) 
	{
		this.snake = snake;
		this.currentState = currentState;
	}
	
	public void run() 
	{ 
		nextMove = snake.getNextMove(currentState);
	}
	
	public Direction getNextMove()
	{
		return nextMove;
	}
	
	public Snake getSnake()
	{
		return snake;
	}
}