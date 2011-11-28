package gameLogic;

import java.util.*;

/**
 * Represents the internal structure of a game session.  Takes care of things such as 
 * snake movement, collision detection and victory conditions. Participants don't need
 * to care about <code>Session</code>; it is documented for satisfying curiosity only.
 * 
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 */

public class Session implements Game
{
	private Board board;
	private Set<Snake> snakes = new HashSet<Snake>();
	private Map<Snake, ErrorState> snakeErrors = new HashMap<Snake, ErrorState>();
	
	private Random random = new Random();
	
	private HashMap<String, GameObjectType> objects = new HashMap<String, GameObjectType>();

	private Metadata metadata;
	
	private RecordedGame recordedGame = null;
	
	public Session(Metadata metadata)
	{
		this.metadata = metadata;
		
		initGameObjects();
		
		board = createStandardBoard(metadata.getBoardWidth(), metadata.getBoardHeight());
	}
	
	public GameState getCurrentState()
	{
		return new GameState(board, snakes, metadata, ErrorState.NO_ERROR);
	}
	
	public Metadata getMetadata()
	{
		return metadata;
	}
	
	public void addSnake(Snake newSnake)
	{
		if (newSnake == null)
			throw new IllegalArgumentException("Trying to add a null Snake.");
		
		snakes.add(newSnake);
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
		
		recordedGame = new RecordedGame(metadata, board, snakes);
	}
	
	/**
	 * Checks whether the game has ended or not. If only one snake remains alive (and
	 * the game was started using more than one snake), or if a snake has achieved the
	 * minimum requireds score in order to win the game, this method returns true.
	 * 
	 * @return	<code>true</code> if the game has ended, <code>false</code> if not.
	 */
	public boolean hasEnded()
	{
		int numberOfLivingSnakes = snakes.size();
		
		for (Snake snake : snakes)
			if (snake.isDead())
				--numberOfLivingSnakes;

		if (numberOfLivingSnakes == 0 || (numberOfLivingSnakes < 2 && snakes.size() >= 2))
			return true;
		
		
		for (Snake snake : snakes)
		{
			if (snake.getScore() >= metadata.getFruitGoal())
				return true;
		}

		return false;
	}
	
	/**
	 * Returns a GameResult object constructed using the current real-time results.
	 * Note that this method does not guarantee that the game has ended.
	 * Check using hasEnded() first before assuming that this will return a final gameResult.
	 *
	 * @return	The current result of this session.
	 */
	public GameResult getGameResult()
	{
		return new GameResult(snakes, metadata, recordedGame);
	}
	
	/**
	 * Moves all the snakes simultaneously, checks for collision, kills colliding snakes, 
	 * adds point when fruit is eaten, and updates the gamestate.
	 */
	public void tick()
	{
		boolean growth = checkForGrowth();
		Map<Snake, Direction> moves = getDecisionsFromSnakes();	
		moveAllSnakes(moves, growth);
		checkForCollision();
		perhapsSpawnFruit();
		
		Frame frame = new Frame(board, snakes);
		recordedGame.addFrame(frame);
	}
	
	public void cleanup()
	{
		for (Snake snake : snakes)
			snake.removeBrain();
	}
	
	private boolean checkForGrowth()
	{
		int timeTillGrowth = recordedGame.getTurnCount() % metadata.getGrowthFrequency();
		return timeTillGrowth == 0;
	}
	
	/**
	 * Returns a HashMap, with each position containing a Snake object and
	 * the Direction towards which the given snake wishes to move next turn. 
	 * Spawns a single thread for each participating snake, then waits until
	 * their allotted time is up. If a snake hasn't responed yet, it's direction
	 * is defaulted to Direction.FORWARD.
	 * 
	 * @see		BrainDecision
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
				ErrorState errorState = ErrorState.NO_ERROR;
				if (snakeErrors.containsKey(snake))
				{
					errorState = snakeErrors.get(snake);
					snakeErrors.remove(snake);
				}
				GameState currentGameState = new GameState(board, snakes, metadata, errorState);
				BrainDecision bd = new BrainDecision(snake, currentGameState);
				decisionThreads.put(snake, bd);
			}
		}
		
		//~ Start all the decision threads.
		for (BrainDecision brainDecision : decisionThreads.values())
			brainDecision.start();
		
		//~ Chill out while the snakes are thinking.
		for (long time = System.currentTimeMillis(); System.currentTimeMillis() < time + metadata.getMaximumThinkingTime(); )
		{
			boolean everyoneIsDone = true;
			for (Map.Entry<Snake, BrainDecision> decisionThread : decisionThreads.entrySet())
			{
				if (decisionThread.getValue().isAlive())
				{
					everyoneIsDone = false;
					break;
				}
			}
			
			if (everyoneIsDone)
				break;
			
			sleep(1);
		}
		
		
		for (Map.Entry<Snake, BrainDecision> decisionThread : decisionThreads.entrySet())
		{
			Snake currentSnake = decisionThread.getKey();
			BrainDecision decision = decisionThread.getValue();
			Direction actualMove = currentSnake.getCurrentDirection();
			try 
			{
				Direction nextMove = decision.demandNextMove();
				if (isValidMove(currentSnake, nextMove))
					actualMove = nextMove;
				else
					snakeErrors.put(currentSnake, ErrorState.INVALID_MOVE);
			}
			catch (java.util.concurrent.TimeoutException t)
			{
				snakeErrors.put(currentSnake, ErrorState.TOO_SLOW);
			}
			catch (Throwable t)
			{
				System.out.println(currentSnake + " is tossing an exception in our face: " + t);
				snakeErrors.put(currentSnake, ErrorState.EXCEPTION);
			}
			
			moves.put(currentSnake, actualMove);
			
		}
		return moves;
	}
	
	static private void sleep(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Checks that moving in a given direction is valid, e g that the snake
	 * doesn't attempt to turn 180 degrees.
	 * 
	 * @param	snake	The snake we want to check.
	 * @param	direction	The direction in which the snake is attempting to move.
	 * @return	<code>true</code> if the attempted move is valid, <code>false</code> if not.
	 */
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
	
	/**
	 * Moves all the snakes by calling the <code>moveSnake</code> for each snake.
	 * 
	 * @param	moves		Map of each snake to its desired movement.
	 * @param	growSnakes	Whether or not snakes are supposed to grow this turn.
	 */
	private void moveAllSnakes(Map<Snake, Direction> moves, boolean growSnakes)
	{
		for (Map.Entry<Snake, Direction> snakeMove : moves.entrySet())
		{
			moveSnake(snakeMove.getKey(), snakeMove.getValue(), growSnakes);
		}
	}
	
	/**
	 * Moves a single snake in the specified direction and grows the snake if necessary.
	 * Works by moving the position of the snake's head, and then also moving its tail
	 * (unless growth is specified).
	 * 
	 * @param	snake	The snake that is going to be moved.
	 * @param	dir		The direction in which the snake is to be moved.
	 * @param	grow	Whether or not the snake is supposed to grow this turn.
	 */
	private void moveSnake(Snake snake, Direction dir, boolean grow)
	{
		Position newHeadPosition = snake.moveHead(dir);
		board.addGameObject(snake, newHeadPosition);
		if (!grow)
		{
			board.removeGameObject(snake, snake.removeTail());
		}
	}

	/**
	 * Checks if any collision has occured, and performs necessary actions. 
	 * If the head of a snake has collided with a lethal object, that snake is 
	 * killed (e g marked as dead). If it collided with a fruit, the appropriate amount of points is
	 * added to that snake's score.
	 */
	private void checkForCollision()
	{
		for (Snake snake : snakes) 
		{
			if (snake.isDead())
				continue;
			
			Position head = snake.getHeadPosition();
			Square square = board.getSquare(head);
			if (square.hasWall() || (square.hasSnake() && (square.getSnakes().size() > 1)))
			{
				snake.kill();
				continue;
			}
			else
				snake.increaseLifespan();
			
			if (square.hasFruit()) 
			{
				int fruitValue = square.eatFruit();
				snake.addScore(fruitValue);
			}
		}
	}
	
	/**
	 * Checks if it is time to spawn a new fruit on the map, and does so if necessary.
	 *
	 * @return	<code>true</code> if a fruit was spawned, <code>false</code> if not.
	 */
	private boolean perhapsSpawnFruit()
	{
		int timeTillFruitSpawn = recordedGame.getTurnCount() % metadata.getFruitFrequency();
		
		if (timeTillFruitSpawn != 0)
			return false;
		
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
		return true;
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
		Position[] startingPositions = getStartingHeadPositions(snakes.size(), board.getWidth(), board.getHeight());
		Collections.shuffle(Arrays.asList(startingPositions));
		int positionIndex = 0;
		for (Snake snake : snakes)
		{
			Position start = startingPositions[positionIndex++];
			
			// Determines the quadrant the snake is in an points it outward
			Direction snakeDirection;
			Position p = new Position(start.getX() - board.getWidth()/2, start.getY() - board.getHeight()/2);
			
			if(p.getX() < 0)
			{
				
				if(p.getY() < 0)
				{
					snakeDirection = Direction.NORTH;
					
				} else {
					snakeDirection = Direction.WEST;
				}
				
			} else {
				
				if(p.getY() < 0)
				{
					snakeDirection = Direction.EAST;
					
				} else {
					snakeDirection = Direction.SOUTH;
				}
			}
			
			// End new code
			
			LinkedList<Position> snakePositions = new LinkedList<Position>();
			snakePositions.add(start);
			snake.placeOnBoard(snakePositions, snakeDirection); //snakeDirection was previously Direction.NORTH
		}
		
	}
	
	
	//~ Metod som tar det antal ormar som ska placeras ut och bradets 
	//~ dimensioner, och returnerar en array av Positions, jamnt fordelade 
	//~ i en cirkel runt bradet. Varje orm ar minst 1 ruta fran kanten men 
	//~ i ovrigt gors INGA kontroller av att antalet ormar ar vettigt.
	
	//~ Jag har lyckats klamma in 101 ormar pa ett 40x40-brade, sen orkade 
	//~ jag inte testa langre.
	
	/**
	 * Gets appropriate starting positions for snake heads.
	 *
	 * @param	snakes	The number of snakes in the game.
	 * @param	xSize	The width of the board.
	 * @param	ySize	The height of the board.
	 * @return	An array of starting positions with as many elements as the number of snakes in the game.
	 */
	private static Position[] getStartingHeadPositions(int snakes, int xSize, int ySize)
	{
		Random random = new Random();
		
		int xCenter = xSize/2;
		int yCenter = ySize/2;
		
		int edgeOffset = 2;
		
		double angleStep = 2*Math.PI/snakes;
		double nextStep = random.nextDouble() * 2 * Math.PI;
		
		Position[] output = new Position[snakes];
		
		for(int i = 0; i < snakes; ++i)
		{
			int xRadi = xCenter - edgeOffset;
			int yRadi = yCenter - edgeOffset;
			
			int x = (int)(xRadi * Math.cos(nextStep)+0.5);
			int y = (int)(yRadi * Math.sin(nextStep)-0.5);
			
			output[i] = new Position(xCenter + x, yCenter + y);
			
			nextStep += angleStep;
		}
		
		return output;
	}
	
	private void placeSnake(Snake snake, LinkedList<Position> segments)
	{
		for (Position pos : segments)
		{
			board.addGameObject(snake, pos);
		}
	}
	
	private void initGameObjects()
	{
		objects.put("Wall", new GameObjectType("Wall", true));
		objects.put("Fruit", new GameObjectType("Fruit", false, 1));
	}
}
