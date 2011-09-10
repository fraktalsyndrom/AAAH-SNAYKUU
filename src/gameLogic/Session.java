package gameLogic;

import java.util.*;

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
	
	private void removeSnake(int id)
	{
		snakes.remove(id);
	}
	
	private void removeSnake(Snake snake)
	{
		for (Map.Entry<Integer, Snake> snakeEntry : snakes.entrySet())
		{
			if (snakeEntry.getValue().equals(snake))
			{
				snakes.remove(snake);
				return;
			}
		}
		throw new IllegalArgumentException("No such snake exists.");
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
		/**
		 * Check for growth.
		 */
		boolean growAllSnakes = false;
		if (--turnsUntilGrowth < 1)
		{
			growAllSnakes = true;
			turnsUntilGrowth = growthFrequency;
		}
		
		for (Snake snake : snakes.values())
		{
			//~ if (growAllSnakes)
			//~ {
				//~ snake.growOneUnitOfLengthNextTimeThisSnakeMoves();
			//~ }
			//~ snake.move();
			Direction direction = snake.getNextMove(currentGameState);
			moveSnake(snake, direction, growAllSnakes);
		}
		
		/**
		 * Check for collision.
		 */
		ArrayList<Snake> dead = new ArrayList<Snake>();
		for (Snake snake : snakes.values()) 
		{
			Position head = snake.getHead().getPosition();
			GameObject object = board.getGameObject(head);
			if (object != null) 
			{
				if (object.isLethal()) 
				{
					dead.add(snake);
					System.out.println("TERMINATE SNAKE.");
				}
				else if (object instanceof Fruit)
				{
					Fruit fruit = (Fruit)object;
					score.put(snake, score.get(snake) + fruit.getValue());
				}
			}
		}
		
		/*
		 * Remove all dead snakes.
		 */
		Iterator<Snake> deadSnakeIter = dead.iterator();
		while (deadSnakeIter.hasNext())
		{
			removeSnake(deadSnakeIter.next());
		}
		
		turn++;
		currentGameState = new GameState(board, snakes, turn, turnsUntilGrowth);
	}
	
	private void moveSnake(Snake snake, Direction dir, boolean grow)
	{
		LinkedList<SnakeSegment> segments = snake.getSegments();
		SnakeSegment currentHead = segments.get(0);
		Position currentHeadPosition = currentHead.getPosition();
		Position newHeadPosition = dir.calculateNextPosition(currentHeadPosition);
		SnakeSegment newHeadSegment = new SnakeSegment(board, newHeadPosition, currentHead);
		segments.addFirst(newHeadSegment);
		if (!grow) 
		{
			segments.removeLast();
		}
		snake.updatePosition(segments);
	}
}
