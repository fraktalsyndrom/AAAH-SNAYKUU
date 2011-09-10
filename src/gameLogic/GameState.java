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
	public int distanceBetween(Position from, Position to)
	{
		return Integer.MAX_VALUE;
	}
	
	public boolean willCollide(Direction dir)
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

