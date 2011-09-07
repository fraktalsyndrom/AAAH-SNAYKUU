import java.util.*;

public class Snake
{
	private LinkedList<Position> segments = new LinkedList<Position>();
	private Brain brain;
	private Direction direction;
	private boolean grow = false;
	
	public Snake(Brain brain)
	{
		this.brain = brain;
		this.direction = new Direction(Direction.NORTH);
	}
	
	
	public Position getHeadPosition()
	{
		return segments.get(0);
	}
	
	public Position getTailPosition()
	{
		return segments.getLast();
	}
	
	/**
	 * Move the snake one unit in the current direction
	 */
	void move()
	{
		Position head = getHeadPosition();
		
		head = direction.calculateNextPosition(head);
		segments.set(0, head);
		
		if (grow)
		{
			grow = false;
			return;
		}
		
		Position tailPosition = getTailPosition();
		Position tailbourPosition = segments.get(segments.size() - 2);
		
		Direction tailDirection = Direction.getDirectionFromPositionToPosition(tailPosition, tailbourPosition);
		tailPosition = tailDirection.calculateNextPosition(tailPosition);
		
		if (tailPosition.equals(tailbourPosition))
			segments.removeLast();
		else
		{
			segments.set(segments.size() - 1, tailPosition);
		}
		
	}
	
	void growOneUnitOfLengthNextTimeThisSnakeMoves()
	{
		grow = true;
	}
	
	
	/**
	 * Changes direction without moving the snake
	 * @param[in] direction New direction of the snake
	 */
	void changeDirection(Direction direction)
	{
		if (direction.equals(this.direction))
			return;
		
		segments.add(0, new Position(getHeadPosition()));
		
		this.direction = direction;
	}
	
}
