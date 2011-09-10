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
	private int numberOfSnakes = 0;
	
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
		snakes.put(++numberOfSnakes, newSnake);
		score.put(newSnake, 0);
	}
	
	private void removeSnake(int id)
	{
		snakes.remove(id);
		numberOfSnakes--;
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
		
		/**
		 * Move each snake.
		 */
		int arrpos = 0;
		BrainDecision[] decisionThreads = new BrainDecision[numberOfSnakes];
		HashMap<Snake, Direction> moves = new HashMap<Snake, Direction>();
		//~ Using a HashMap here since I'm unsure of the sorting order of snakes.values() below.
		
		for (Snake snake : snakes.values())
		{
			BrainDecision bd = new BrainDecision(snake, currentGameState);
			decisionThreads[arrpos++] = bd;
		}
		for (int i = 0; i < decisionThreads.length; i++)
			decisionThreads[i].start();
		
		//Chill out while the snakes are thinking.
		try { Thread.sleep(thinkingTime); }
		catch (InterruptedException e) { System.out.println(e); }
		
		for (int i = 0; i < decisionThreads.length; i++)
		{
			BrainDecision decision = decisionThreads[i];
			Snake currentSnake = decision.getSnake();
			Direction nextMove;
			if (!decision.isAlive())
			{
				nextMove = decision.getNextMove();
			}
			else
			{
				//~ This snake has taken too long to decide, and will automatically move forward.
				nextMove = new Direction(Direction.FORWARD);
				Snake slowSnake = decision.getSnake();
				slowSnake.tooSlowFault();
			}
			moves.put(currentSnake, nextMove);
		}
		for (Map.Entry<Snake, Direction> snakeMove : moves.entrySet())
		{
			moveSnake(snakeMove.getKey(), snakeMove.getValue(), growAllSnakes);
		}
		
		/**
		 * Check for collision. Kill snake if collision is lethal. Add points if the snake eats a fruit.
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
