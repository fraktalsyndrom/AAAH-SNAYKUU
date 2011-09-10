package gameLogic;


import java.util.Map;
import java.util.TreeMap;

public class GameState
{
	private Board board;
	private Map<Integer, Snake> snakes;
	private int turn;
	private int turnsUntilGrowth;
	
	public GameState(Board currentBoard, Map<Integer, Snake> listOfSnakes, int turn, int turnsUntilGrowth) 
	{
		board = currentBoard;
		snakes = listOfSnakes;
		this.turn = turn;
		this.turnsUntilGrowth = turnsUntilGrowth;
	}
	
	public Snake getSnake(int id)
	{
		return snakes.get(id);
	}
	
	public Board getBoard()
	{
		return board;
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
		
		if (fromX > toX)
			distance += fromX - toX;
		else
			distance += toX - fromX;
		if (fromY > toY)
			distance += fromY - toY;
		else
			distance += toY - fromY;
		return distance;
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
}

