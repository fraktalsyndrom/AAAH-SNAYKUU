package gameLogic;

import java.util.*;

public class Session
{
	private Board board;
	private Set<Snake> snakes = new HashSet<Snake>();
	
	private HashMap<String, GameObjectType> objects = new HashMap<String, GameObjectType>();
	private HashMap<Snake, Direction> lastMoves = new HashMap<Snake, Direction>();

	private GameState currentGameState;
	private GameResult gameResult = new GameResult();
	
	private long thinkingTime;
	private int growthFrequency;
	private int fruitFrequency;
	private int fruitGoal;
	
	private int turnsUntilGrowth;
	private int turnsUntilFruit;
	private int turn = 0;
	private int numberOfSnakes = 0;
	
	private Snake winner;
	
	public Session(int boardWidth, int boardHeight, int growthFrequency, int fruitFrequency, long thinkingTime) 
	{
		initGameObjects();
		board = createStandardBoard(boardWidth, boardHeight);
		
		this.growthFrequency = growthFrequency;
		this.fruitFrequency = fruitFrequency;
		this.thinkingTime = thinkingTime;
		
		turnsUntilGrowth = growthFrequency;
		turnsUntilFruit = fruitFrequency;
	}
	
	public void addSnake(Snake newSnake)
	{
		if (newSnake == null)
			throw new IllegalArgumentException("Trying to add a null Snake.");
		
		snakes.add(newSnake);
	}
	
	private void removeSnake(Snake snake)
	{
		if (!snakes.contains(snake))
			throw new IllegalArgumentException("No such snake exists.");
		
		snakes.remove(snake);
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public Set<Snake> getSnakes()
	{
		return new HashSet<Snake>(snakes);
	}
	
	public void prepareForStart()
	{
		placeSnakesOnBoard();
	}
	
	public boolean hasEnded()
	{
		int numberOfLivingSnakes = snakes.size();
		for (Snake snake : snakes)
			if (snake.isDead())
				--numberOfLivingSnakes;
		if (numberOfLivingSnakes < 2)
		{
			gameResult.setEndGameCondition(GameResult.DEATH_FINISH);
			return true;
		}
		
		for (Map.Entry<Snake, Integer> snakeScore : gameResult.getScores().entrySet())
		{
			if (snakeScore.getValue() >= fruitGoal)
			{
				gameResult.setEndGameCondition(GameResult.FRUIT_FINISH);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Note that this method does not guarantee that the game has ended.
	 * Check using hasEnded() first before assuming that this will return a final gameResult.
	 */
	public GameResult getGameResult()
	{
		return gameResult;
	}
	
	/**
	 * Move all the snakes simultaneously. In addition to movement, it also checks for collision,
	 * kills colliding snakes, adds point when fruit is eaten, and updates the gamestate.
	 */
	public void tick()
	{
		boolean growth = checkForGrowth();
		Map<Snake, Direction> moves = getDecisionsFromSnakes();	
		moveAllSnakes(moves, growth);
		checkForCollision();
		if (perhapsSpawnFruit())
			System.out.println("FRUIT SPAWNED");
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
	private Map<Snake, Direction> getDecisionsFromSnakes()
	{
		Map<Snake, BrainDecision> decisionThreads = new HashMap<Snake, BrainDecision>();
		Map<Snake, Direction> moves = new HashMap<Snake, Direction>();
		//~ Using a HashMap here since I'm unsure of the sorting order of snakes.values() below.
		
		//~ Prepare some decision threads.
		for (Snake snake : snakes)
		{
			if (!snake.isDead())
			{
				BrainDecision bd = new BrainDecision(snake.getBrain(), currentGameState);
				decisionThreads.put(snake, bd);
			}
		}
		
		//~ Start all the decision threads.
		for (BrainDecision brainDecision : decisionThreads.values())
			brainDecision.start();
		
		//~ Chill out while the snakes are thinking.
		try { Thread.sleep(thinkingTime); }
		catch (InterruptedException e) { System.out.println(e); }
		
		for (Map.Entry<Snake, BrainDecision> decisionThread : decisionThreads.entrySet())
		{
			Snake currentSnake = decisionThread.getKey();
			BrainDecision decision = decisionThread.getValue();
			try 
			{
				Direction nextMove = decision.demandNextMove();
				if (isValidMove(currentSnake, nextMove))
				{
					moves.put(currentSnake, nextMove);
					currentSnake.setCurrentDirection(nextMove);
				}
				else
					moves.put(currentSnake, currentSnake.getCurrentDirection());
			}
			catch (Throwable t)
			{
				System.out.println(t);
				moves.put(currentSnake, currentSnake.getCurrentDirection());
			}
		}
		return moves;
	}
	
	private boolean isValidMove(Snake snake, Direction direction)
	{
		switch (snake.getCurrentDirection())
		{
			case NORTH:
				return (direction != Direction.SOUTH);

			case WEST:
				return (direction != Direction.EAST);

			case SOUTH:
				return (direction != Direction.NORTH);

			case EAST:
				return (direction != Direction.WEST);
			
			default:
				throw new IllegalArgumentException("No such Direction exists.");
		}
	}
	
	private void moveAllSnakes(Map<Snake, Direction> moves, boolean growSnakes)
	{
		for (Map.Entry<Snake, Direction> snakeMove : moves.entrySet())
		{
			moveSnake(snakeMove.getKey(), snakeMove.getValue(), growSnakes);
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

	
	private void checkForCollision()
	{
		ArrayList<Snake> deadSnakes = new ArrayList<Snake>();
		for (Snake snake : snakes) 
		{
			if (snake.isDead())
				continue;
			
			Position head = snake.getHead();
			Square square = board.getSquare(head);
			if (square.hasWall() || (square.hasSnake() && (square.getSnakes().size() > 1)))
			{
				killAndSetResults(snake);
				System.out.println(snake + " HAS BEEN TERMINATED.");
			}
			if (square.hasFruit()) 
			{
				int fruitValue = square.eatFruit();
				snake.addScore(fruitValue);
			}
		}
	}
	
	private void killAndSetResults(Snake snake)
	{
		int score = snake.getScore();
		int lifespan = turn;
		gameResult.setSnakeScores(snake, score, lifespan);
		snake.kill();
	}
	
	private boolean perhapsSpawnFruit()
	{
		if (--turnsUntilFruit < 1)
		{
			Random random = new Random();
			boolean spawned = false;
			while (!spawned)
			{
				int x = 1 + random.nextInt(board.getWidth() - 2);
				int y = 1 + random.nextInt(board.getHeight() - 2);
				Position potentialFruitPosition = new Position(x, y);
				if (!board.hasGameObject(potentialFruitPosition))
				{
					board.addGameObject(objects.get("Fruit"), potentialFruitPosition);
					spawned = true;
				}
			}
			turnsUntilFruit = fruitFrequency;
			return true;
		}
		return false;
	}
	
	private void updateGameState()
	{
		turn++;
		currentGameState = new GameState(board, snakes, turn, turnsUntilGrowth);
	}
	
	/**
	 * Generates a standard snake board, sized width x height, with lethal walls around the edges.
	 * @param width		Desired board height.
	 * @param height		Desired board width.
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
	
	private void placeSnakesOnBoard()
	{
		LinkedList<Snake> remainingSnakes = new LinkedList<Snake>(snakes);
		Random random = new Random();
		
		while (remainingSnakes.size() > 0)
		{
			Snake snake = remainingSnakes.getLast();
			int x = 1 + random.nextInt(board.getWidth() - 2);
			int y = 1 + random.nextInt(board.getHeight() - 2);
			Position randomPos = new Position(x, y);
			
			if (isAcceptedStartingPosition(randomPos)) {
				LinkedList<Position> startingPositions = new LinkedList<Position>();
				startingPositions.add(randomPos);
				
				placeSnake(snake, startingPositions);
				snake.placeOnBoard(startingPositions, Direction.NORTH); //~ TODO: Look at the starting direction.
				remainingSnakes.removeLast();
			}
		}
		
		updateGameState();
		
		for (Snake snake : snakes)
			snake.getBrain().init(currentGameState);
	}
	
	private void placeSnake(Snake snake, LinkedList<Position> segments)
	{
		for (Position pos : segments)
		{
			board.addGameObject(snake, pos);
		}
	}
	
	private boolean isAcceptedStartingPosition(Position position)
	{
		return (!board.hasLethalObjectWithinRange(position, 2));
	}
	
	private void initGameObjects()
	{
		objects.put("Wall", new GameObjectType("Wall", true));
		objects.put("Fruit", new GameObjectType("Fruit", false, 1));
	}
}