package gameLogic;


import java.util.Set;
import java.util.HashSet;

public class GameState
{
	private Board board;
	private Set<Snake> snakes;
	private int turn;
	private int turnsUntilGrowth;
	
	public GameState(Board currentBoard, Set<Snake> snakes, int turn, int turnsUntilGrowth) 
	{
		board = currentBoard;
		this.snakes = snakes;
		this.turn = turn;
		this.turnsUntilGrowth = turnsUntilGrowth;
	}
	
	public Set<Snake> getSnakes()
	{
		return new HashSet<Snake>(snakes);
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	
	/*
	 * Method stub, intended to eventually calculate whether or not a
	 * given snake will collide next turn if it moves in a particular direction.
	 */
	public boolean willCollide(Snake snake, Direction dir)
	{
		return false;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	public int getTurnsUntilGrowth()
	{
		return turnsUntilGrowth;
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

