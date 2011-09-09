import java.util.Map;
import java.util.TreeMap;

public class Session
{
	private Board board;
	private Map<Integer, Snake> snakes;
	private GameState currentGameState;
	
	private long thinkingTime;
	private int growthFrequency;
	
	private int turnsUntilGrowth;
	private int turn = 0;
	private int counter = 0;
	
	public Session(int boardWidth, int boardHeight, int growthFrequency, long thinkingTime) 
	{
		board = new Board(boardWidth, boardHeight);
		snakes = new TreeMap<Integer, Snake>();
		this.growthFrequency = growthFrequency;
		this.thinkingTime = thinkingTime;
		turnsUntilGrowth = growthFrequency;
	}
	
	public void addSnake(Snake newSnake)
	{
		snakes.put(++counter, newSnake);
	}
	
	public void removeSnake(int id)
	{
		snakes.remove(id);
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	/**
	 * Move all the snakes simultaneously.
	 */
	void tick() {
		// Do stuff.
		currentGameState = new GameState(board, snakes);
	}
}
