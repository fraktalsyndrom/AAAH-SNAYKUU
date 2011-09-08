import java.util.Map;
import java.util.TreeMap;

public class GameState
{
	private Board board;
	private Map<Integer, Snake> snakes;
	
	public GameState(Board currentBoard, Map<Integer, Snake> listOfSnakes) 
	{
		board = currentBoard;
		snakes = listOfSnakes;
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
}

