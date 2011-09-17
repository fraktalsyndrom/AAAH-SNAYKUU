package gameLogic;

import java.util.*;

public class Session
{
	private Board board;
	private HashMap<Integer, Snake> snakes;
	private HashMap<Snake, Integer> score;
	
	private HashMap<String, GameObjectType> objects;
	private GameState currentGameState;
	
	private long thinkingTime;
	private int growthFrequency;
	
	private int turnsUntilGrowth;
	private int turn = 0;
	private int numberOfSnakes = 0;
	
	public Session(int boardWidth, int boardHeight, int growthFrequency, long thinkingTime) 
	{
		snakes = new HashMap<Integer, Snake>();
		score = new HashMap<Snake, Integer>();
		objects = new HashMap<String, GameObjectType>();
		initGameObjects();
		board = createStandardBoard(boardWidth, boardHeight);
		
		this.growthFrequency = growthFrequency;
		this.thinkingTime = thinkingTime;
		turnsUntilGrowth = growthFrequency;
	}
	
	public void addSnake(Snake newSnake)
	{
		if (newSnake == null)
			throw new IllegalArgumentException("Trying to add a null Snake.");
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
	
	public Set<Snake> getSnakes()
	{
		return new HashSet<Snake>(snakes.values());
	}
	
	/**
	 * Move all the snakes simultaneously. In addition to movement, it also checks for collision,
	 * kills colliding snakes, adds point when fruit is eaten, and updates the gamestate.
	 */
	public void tick()
	{
		boolean growth = checkForGrowth();
		HashMap<Snake, Direction> moves = getDecisionsFromSnakes();	
		moveAllSnakes(moves, growth);
		
		ArrayList<Snake> deadSnakes = checkForCollision();
		removeDeadSnakes(deadSnakes);
		
		updateGameState();
	}
	
	private boolean checkForGrowth()
	{
		boolean grow = false;
		if (--turnsUntilGrowth < 1)
		{
			grow = true;
			turnsUntilGrowth = growthFrequency;
		}
		return grow;
	}
	
	/**
	 * Returns a HashMap, with each position containing a Snake object and
	 * the Direction towards which the given snake wishes to move next turn. 
	 * Spawns a single thread for each participating snake, then waits until
	 * their allotted time is up. If a snake hasn't responed yet, it's direction
	 * is defaulted to Direction.FORWARD.
	 * @return 	The HashMap containing snakes and their next moves.
	 */
	private HashMap<Snake, Direction> getDecisionsFromSnakes()
	{
		BrainDecision[] decisionThreads = new BrainDecision[numberOfSnakes];
		int arrpos = 0;
		HashMap<Snake, Direction> moves = new HashMap<Snake, Direction>();
		//~ Using a HashMap here since I'm unsure of the sorting order of snakes.values() below.
		
		//~ Prepare some decision threads.
		for (Snake snake : snakes.values())
		{
			BrainDecision bd = new BrainDecision(snake, currentGameState);
			decisionThreads[arrpos++] = bd;
		}
		
		//~ Start all the decision threads.
		for (int i = 0; i < decisionThreads.length; i++)
			decisionThreads[i].start();
		
		//~ Chill out while the snakes are thinking.
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
			//~ This snake has taken too long to decide, and will automatically move forward.
			{
				nextMove = new Direction(Direction.FORWARD);
				Snake slowSnake = decision.getSnake();
				slowSnake.tooSlowFault();
			}
			moves.put(currentSnake, nextMove);
		}
		return moves;
	}

	
	private void moveAllSnakes(HashMap<Snake, Direction> moves, boolean growSnakes)
	{
		for (Map.Entry<Snake, Direction> snakeMove : moves.entrySet())
		{
			moveSnake(snakeMove.getKey(), snakeMove.getValue(), growSnakes);
		}
	}
	
	private ArrayList<Snake> checkForCollision()
	{
		ArrayList<Snake> deadSnakes = new ArrayList<Snake>();
		for (Snake snake : snakes.values()) 
		{
			Position head = snake.getHead();
			Square square = board.getSquare(head);
			if (square.hasWall() || (square.hasSnake() && (square.getSnakes().size() > 1)))
			{
				deadSnakes.add(snake);
				System.out.println("TERMINATE SNAKE.");
			}
			if (square.hasFruit()) 
			{
				int fruitValue = square.eatFruit();
				int oldScore = score.get(snake);
				int newScore = oldScore + fruitValue;
				score.put(snake, newScore);
			}
		}
		return deadSnakes;
	}
	
	private void removeDeadSnakes(ArrayList<Snake> dead)
	{
		Iterator<Snake> deadSnakeIter = dead.iterator();
		while (deadSnakeIter.hasNext())
		{
			removeSnake(deadSnakeIter.next());
		}
	}
		
	private void moveSnake(Snake snake, Direction dir, boolean grow)
	{
		Position currentHeadPosition = snake.getHead();
		Position currentTailPosition = snake.getTail();
		Position newHeadPosition = dir.calculateNextPosition(currentHeadPosition);
		board.addGameObject(snake, newHeadPosition);
		snake.moveHead(newHeadPosition);
		if (!grow)
		{
			board.removeGameObject(snake, currentTailPosition);
			snake.removeTail();
		}
	}
	
	private void updateGameState()
	{
		turn++;
		currentGameState = new GameState(board, snakes, turn, turnsUntilGrowth);
	}
	
	/**
	 * Generates a standard snake board, sized width x height, with lethal walls around the edges.
	 * @param width		Desired board height.
	 * @param height	Desired board width.
	 * @return			The newly generated board.
	 */
	private Board createStandardBoard(int width, int height)
	{
		board = new Board(width, height);
		GameObjectType wall = objects.get("Wall");
		for (int x = 0; x < width; x++)
		{
			Position bottomRowPos = new Position(x, 0);
			Position topRowPos = new Position(x, height-1);
			board.addGameObject(wall, bottomRowPos);
			board.addGameObject(wall, topRowPos);
		}
		for (int y = 0; y < height; y++)
		{
			Position leftmostColumnPos = new Position(0, y);
			Position rightmostColumnPos = new Position(width-1, y);
			board.addGameObject(wall, leftmostColumnPos);
			board.addGameObject(wall, rightmostColumnPos);
		}
		return board;
	}
	
	private void initGameObjects()
	{
		objects.put("Wall", new GameObjectType("Wall", true));
		objects.put("Fruit", new GameObjectType("Fruit", false, 1));
	}
}
