package gameLogic;

import java.util.Set;
import java.util.HashSet;

public class GameState
{
	private ErrorState errorState = ErrorState.NO_ERROR;
	private Board board;
	private Set<Snake> snakes;
	private Metadata metadata;
	
	GameState(Board currentBoard, Set<Snake> snakes, Metadata metadata) 
	{
		board = new Board(currentBoard);
		this.snakes = new HashSet<Snake>(snakes);
		this.metadata = metadata;
	}
	
	/**
	 * 
	 */
	public Set<Snake> getSnakes()
	{
		return snakes;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public Metadata getMetadata()
	{
		return metadata;
	}
	
	public ErrorState getErrorState()
	{
		return errorState;
	}
	
	
	/*
	 * Method stub, intended to eventually calculate whether or not a
	 * given snake will collide next turn if it moves in a particular direction.
	 */
	public boolean willCollide(Snake snake, Direction dir)
	{
		Position currentHeadPosition = snake.getHeadPosition();
		Position nextHeadPosition = dir.calculateNextPosition(currentHeadPosition);
		return (board.getSquare(nextHeadPosition).isLethal());
	}
	
	
	
	/**
	 * Method stubs intended to eventually be of assistance during the planning phase.
	 * Do we want the methods to be here?
	 */
	public static int distanceBetween(Position from, Position to)
	{
		int distance = 0;
		int fromX = from.getX();
		int fromY = from.getY();
		int toX = to.getX();
		int toY = to.getY();
		
		// Calculate distance in the x-axis
		distance += Math.abs(fromX - toX);
		
		// Calculate distance in the y-axis
		distance += Math.abs(fromY - toY);

		return distance;
	}
}
