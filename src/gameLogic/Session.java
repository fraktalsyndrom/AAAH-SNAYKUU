package gameLogic;

import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

public class Session
{
	private Board board;
	private HashMap<Integer, Snake> snakes;
	private HashMap<Snake, Integer> score;
	private GameState currentGameState;
	
	private long thinkingTime;
	private int growthFrequency;
	
	private int turnsUntilGrowth;
	private int turn = 0;
	private int counter = 0;
	
	public Session(int boardWidth, int boardHeight, int growthFrequency, long thinkingTime) 
	{
		board = new Board(boardWidth, boardHeight);
		snakes = new HashMap<Integer, Snake>();
		score = new HashMap<Snake, Integer>();
		
		this.growthFrequency = growthFrequency;
		this.thinkingTime = thinkingTime;
		turnsUntilGrowth = growthFrequency;
	}
	
	public void addSnake(Snake newSnake)
	{
		snakes.put(++counter, newSnake);
		score.put(newSnake, 0);
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
	public void tick()
	{
		for (Snake snake : snakes.values())
		{
			if (--turnsUntilGrowth < 1)
			{
				snake.growOneUnitOfLengthNextTimeThisSnakeMoves();
				turnsUntilGrowth = growthFrequency;
			}
			snake.move(currentGameState);
		}
		/**
		 * Check for collision.
		 */
		for (Snake snake : snakes.values()) 
		{
			Position head = snake.getHead().getPosition();
			GameObject object = board.getGameObject(head);
			if (object != null) 
			{
				if (object.isLethal()) 
				{
					System.out.println("TERMINATE SNAKE.");
				}
				else if (object instanceof Fruit)
				{
					Fruit fruit = (Fruit)object;
					score.put(snake, score.get(snake) + fruit.getValue());
				}
			}
		}
		turn++;
		currentGameState = new GameState(board, snakes);
	}
}
