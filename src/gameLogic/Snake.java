import java.util.*;

public class Snake
{
	private String name;
	private LinkedList<SnakeSegment> segments = new LinkedList<SnakeSegment>();
	private Brain brain;
	private Direction direction;
	private boolean grow = false;
	
	public Snake(String name, Brain brain)
	{
		this.name = name;
		this.brain = brain;
		this.direction = new Direction(Direction.NORTH);
	}
	
	public SnakeSegment getHead()
	{
		return segments.get(0);
	}
	
	public SnakeSegment getTail()
	{
		return segments.getLast();
	}
	
	/**
	 * Move the snake one unit in the current direction
	 */
	void move(GameState currentGameState)
	{
		direction = brain.getNextMove(currentGameState);
		SnakeSegment head = getHead();
		
		//Perhaps move this functionality, or fix it to not need session information.
		//Position newHeadPosition = direction.calculateNextPosition(head.getPosition());
		//segments.addFirst(new SnakeSegment(session.getBoard(), newHeadPosition, head));
		
		if (grow)
		{
			grow = false;
			return;
		}
		
		//SnakeSegment tail = getTail();
		//segments.removeLast();		
	}
	
	void growOneUnitOfLengthNextTimeThisSnakeMoves()
	{
		grow = true;
	}
	
	public String toString()
	{
		return name;
	}
}
