package gameLogic;


import java.util.*;

public class Snake
{
	private LinkedList<SnakeSegment> segments = new LinkedList<SnakeSegment>();
	private Session session;
	private Brain brain;
	private Direction direction;
	private boolean grow = false;
	
	public Snake(Session session, Brain brain)
	{
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
	void move(Direction direction)
	{
		SnakeSegment head = getHead();
		
		Position newHeadPosition = direction.calculateNextPosition(head.getPosition());
		segments.addFirst(new SnakeSegment(session.getBoard(), newHeadPosition, head));
		
		if (grow)
		{
			grow = false;
			return;
		}
		
		SnakeSegment tail = getTail();
		segments.removeLast();		
	}
	
	void growOneUnitOfLengthNextTimeThisSnakeMoves()
	{
		grow = true;
	}
	
	
}
