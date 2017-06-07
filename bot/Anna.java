package bot;

import java.text.DecimalFormat;
import java.util.*;

import gameLogic.Brain;
import gameLogic.Direction;
import gameLogic.GameState;
import gameLogic.Position;
import gameLogic.Snake;
import gameLogic.Square;

/**
 * An awesome bot for the engine/API Snaykuu called Anna.
 * 
 * @author	Marcus Hansson
 * @version 1.0
 */
public class Anna implements Brain {
	private GameState gameState;
	private Snake snake;
	private double smallestFruitRisk;
	private List<Snake> otherLivingSnakes = new ArrayList<Snake>();
	private Direction chosenDir;
	private int turnsUntilTailGrowth;
	private int stepsInTrail = 5;
	final double maximumFruitRisk = 0.98;
	
	public Direction getNextMove(Snake snake, GameState gameState) {
		this.gameState = gameState;
		this.snake = snake;
		
		int maxThinkingTime = gameState.getMetadata().getMaximumThinkingTime();
		double minPercentage = 0.2;
		double maxPercentage = 0.7;
		int maxStepsInTrail = 10;
		
		long startTime = System.currentTimeMillis();
		
		Direction d = main();
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		if (totalTime > maxThinkingTime * maxPercentage) {
			stepsInTrail--;
		} else if (totalTime < maxThinkingTime * minPercentage) {
			stepsInTrail++;
		}
		
		if (stepsInTrail > maxStepsInTrail) {
			stepsInTrail = maxStepsInTrail;
		}
		
		return d;

		
	}
	
	public Direction main() {
		int closestCompetitorDistance = Integer.MAX_VALUE;
		int distanceToFruit = Integer.MAX_VALUE;
		final int growthFrequency = gameState.getMetadata().getGrowthFrequency();
		int numberOfRounds = snake.getLifespan();

		double leftRisk = 0;
		double forwardRisk = 0;
		double rightRisk = 0;

		Direction leftDir = snake.getCurrentDirection().turnLeft();
		Direction forwardDir = snake.getCurrentDirection();
		Direction rightDir = snake.getCurrentDirection().turnRight();

		Position headPos = snake.getHeadPosition();
		Position leftOfHead = GameState.calculateNextPosition(leftDir, headPos);
		Position forwardOfHead = GameState.calculateNextPosition(forwardDir, headPos);
		Position rightOfHead = GameState.calculateNextPosition(rightDir, headPos);

		Trail leftTrail;
		Trail forwardTrail;
		Trail rightTrail;

		final List<Direction> fruitDirs = new ArrayList<Direction>();
		final List<Position> fruits = new ArrayList<Position>();
		final List<Trail> leftDirSquares = new ArrayList<Trail>();
		final List<Trail> forwardDirSquares = new ArrayList<Trail>();
		final List<Trail> rightDirSquares = new ArrayList<Trail>();
		final List<Position> leftStartPos = new ArrayList<Position>();
		final List<Position> forwardStartPos = new ArrayList<Position>();
		final List<Position> rightStartPos = new ArrayList<Position>();

		leftStartPos.add(leftOfHead);
		forwardStartPos.add(forwardOfHead);
		rightStartPos.add(rightOfHead);

		leftTrail = new Trail(leftStartPos, leftDir, leftRisk);
		forwardTrail = new Trail(forwardStartPos, forwardDir, forwardRisk);
		rightTrail = new Trail(rightStartPos, rightDir, rightRisk);

		turnsUntilTailGrowth = calculateTurnsUntilTailGrowth(numberOfRounds, growthFrequency);

		leftDirSquares.add(leftTrail);
		forwardDirSquares.add(forwardTrail);
		rightDirSquares.add(rightTrail);

		leftTrail.setRisk(calculateTotalRisk(leftDirSquares));
		forwardTrail.setRisk(calculateTotalRisk(forwardDirSquares));
		rightTrail.setRisk(calculateTotalRisk(rightDirSquares));

		setOtherLivingSnakes();

		fruits.addAll(gameState.getFruits());

		sortFruitsByDistance(fruits, headPos);

		chosenDir = null;

		for (Position fruit : fruits) {
			distanceToFruit = headPos.getDistanceTo(fruit);

			fruitDirs.clear();

			for (Snake otherSnake : otherLivingSnakes) {
				Position competitorHeadPos = otherSnake.getHeadPosition();
				int competitorDistanceToFruit = competitorHeadPos.getDistanceTo(fruit);

				if (competitorDistanceToFruit < closestCompetitorDistance) {
					closestCompetitorDistance = competitorDistanceToFruit;
				}
			}

			if (distanceToFruit <= closestCompetitorDistance) {
				smallestFruitRisk = maximumFruitRisk;
				
				fruitDirs.addAll(GameState.getRelativeDirections(headPos, fruit));

				for (Direction fruitDir : fruitDirs) {
					if (fruitDir.equals(leftTrail.getEndOfTrailDir())) {
						chosenDir = checkIfSafeEnough(leftTrail);
					} else if (fruitDir.equals(forwardTrail.getEndOfTrailDir())) {
						chosenDir = checkIfSafeEnough(forwardTrail);
					} else if (fruitDir.equals(rightTrail.getEndOfTrailDir())) {
						chosenDir = checkIfSafeEnough(rightTrail);
					}
				}
				break;
			}
		}

		if (chosenDir == null) {
			chosenDir = smallestRiskDir(leftTrail, forwardTrail, rightTrail);
		}

		printInfo(headPos, leftTrail, forwardTrail, rightTrail);
		
		return chosenDir;
	}
	
	/**
	 * Prints information about the trail risks and the chosen direction to the
	 * console.
	 * 
	 * @param headPos 		this head position
	 * @param leftTrail 	trail when turning left
	 * @param forwardTrail	trail when not turning
	 * @param rightTrail	trail when turning right
	 */
	public void printInfo(Position headPos, Trail leftTrail, Trail forwardTrail,
						  Trail rightTrail) {
		final DecimalFormat df = new DecimalFormat("#%");
		
		System.out.println("Where should I turn??");
		System.out.println("Current position: " + headPos);
		System.out.println("Left risk: " + df.format(leftTrail.getRisk()));
		System.out.println("Forward risk: " + df.format(forwardTrail.getRisk()));
		System.out.println("Right risk: " + df.format(rightTrail.getRisk()));
		System.out.println("I am going " + chosenDir + " to " 
				+ GameState.calculateNextPosition(chosenDir, headPos));
		System.out.println();
	}

	
	/**
	 * Sorts all the visible fruits on the game board by the distance to this 
	 * bot's head, with the closest fruit first.
	 * 
	 * @param fruits 	all visible fruits on the game board
	 * @param headPos 	the bot's head position
	 */
	public void sortFruitsByDistance(List<Position> fruits, Position headPos) {
		Collections.sort(fruits, new Comparator<Position>() {
			
			@Override
			public int compare(Position o1, Position o2) {
				return o1.getDistanceTo(headPos) - o2.getDistanceTo(headPos);
			}
		});
	}

	/**
	 * Add all living snakes, which are not this bot, to the list 
	 * {@link #otherLivingSnakes} class. 
	 */
	public void setOtherLivingSnakes() {
		for (Snake otherSnake : gameState.getSnakes()) {
			if (!otherSnake.isDead()) {
				if (!otherSnake.equals(snake)) {
					otherLivingSnakes.add(otherSnake);
				}
			}
		}
	}

	/**
	 * Calculates the number of turns that must pass until all the tails of the
	 * snakes will grow one length unit.
	 * 
	 * @param numberOfRounds 	the number of rounds that has lasted i.e. the 
	 * 							amount of steps that this bot has taken
	 * @param growthFrequency 	the number of turns it takes for snakes to grow
	 * @return					the number of turns until tail growth
	 */
	public int calculateTurnsUntilTailGrowth(int numberOfRounds, int growthFrequency) {
		return growthFrequency - (numberOfRounds % growthFrequency + 1);
	}

	/**
	 * Checks if a trail is safe enough to take.
	 *  
	 * @param trail the trail to be examined
	 * @return 		the current safest direction, that has been examined
	 */
	public Direction checkIfSafeEnough(Trail trail) {
		if (trail.getRisk() < smallestFruitRisk) {
			smallestFruitRisk = trail.getRisk();
			chosenDir = trail.getEndOfTrailDir();
		}

		return chosenDir;
	}
 
	/**
	 * Finds the direction with the smallest risk for this bot to take.
	 * 
	 * @param leftTrail 	the trail taken when this bot go left next move
	 * @param forwardTrail 	the trail taken when this bot go forward next move 
	 * @param rightTrail	the trail taken when this bot go right next move
	 * @return				the direction with the smallest risk
	 */
	public Direction smallestRiskDir(Trail leftTrail, Trail forwardTrail, Trail rightTrail) {

		if (rightTrail.getRisk() < leftTrail.getRisk() && rightTrail.getRisk() < forwardTrail.getRisk()) {
			return rightTrail.getEndOfTrailDir();

		} else if (leftTrail.getRisk() < forwardTrail.getRisk()) {
			return leftTrail.getEndOfTrailDir();
		}

		return forwardTrail.getEndOfTrailDir();

	}

	/**
	 * Calculates the total risk of taking a specific direction for this bot.
	 * 
	 * @param trails 	a list initially only containing one of the possible trails
	 * 					that this bot can take, with the length of one square 
	 * @return			the risk of taking a specific direction (0.0 to ~1.0)
	 */
	public double calculateTotalRisk(List<Trail> trails) {
		double risk = 0;

		for (int stepNum = 0; stepNum < stepsInTrail; stepNum++) {
			risk += calculateRisk(trails, stepNum);
			if (risk >= 1) {
				risk = 1 + 1.0 / (100000 * (stepNum + 1));
				break;
			}
		}
		return risk;
	}
	
	/**
	 * Calculates the total risk of taking all possible trails with a set amount
	 * of trail steps.
	 * 
	 * @param trails		
	 * @param trailSteps
	 * @return
	 */
	public double calculateRisk(List<Trail> trails, int trailSteps) {
		double totalRiskOfDeath = 0;

		final List<Trail> tempList = new ArrayList<Trail>(trails);
		final List<Position> neighbourPositions = new ArrayList<Position>();
		final List<Trail> newTrails = new ArrayList<Trail>();

		trails.clear();

		for (Trail trail : tempList) {
			int riskOfDeath = 0;
			int turnsUntilSquareNotLeathal = Integer.MAX_VALUE;

			Square square;
			
			final Position squarePos = trail.getEndOfTrail();
			final List<Position> squarePositions = trail.getTrailPositions();

			neighbourPositions.clear();
			newTrails.clear();

			try {
				square = gameState.getBoard().getSquare(squarePos);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("I found a ArrayIndexOutOfBoundsException");
				totalRiskOfDeath += Math.pow((1 / 3.0), trailSteps) * 1;
				continue;
			}

			for (Snake otherSnake : square.getSnakes()) {
				if (otherLivingSnakes.contains(otherSnake)) {
					final List<Position> segments = otherSnake.getSegments();

					for (int i = segments.size(); i > 0; i--) {
						final Position segmentPos = segments.get(i - 1);

						if (segmentPos.equals(squarePos)) {
							turnsUntilSquareNotLeathal = i;
							
							if (turnsUntilSquareNotLeathal == segments.size()) {
								if (trailSteps == 0) {
									totalRiskOfDeath = 1;
									break;
								} else if (trailSteps == 1) {
									totalRiskOfDeath = 0.999;
									break;
								}
							}
							break;
						}
					}
				}
			}

			if (!square.isLethal()) {
				// Snake is able to collide with itself.
				if (squarePositions.size() >= 5) { 
					for (int steps = 0; steps < squarePositions.size() - 4; steps++) {
						if (squarePositions.get(steps).equals(squarePos)
								&& snake.getSegments().size() >= squarePositions.size() - steps) {
							riskOfDeath = 1;
							break;
						}
					}
				}

			} else {
				if (turnsUntilSquareNotLeathal > trailSteps) {
					riskOfDeath = 1;
				}

			}

			if (riskOfDeath != 1) {
				neighbourPositions.addAll(squarePos.getNeighbours());
			}

			for (int k = 0; k < neighbourPositions.size(); k++) {
				final Position neighbour = neighbourPositions.get(k);
				final Direction neighbourDir = findNeighbourDir(k);
				final List<Position> trailPositions = trail.getTrailPositions();
				final List<Position> trailPositionsCopy = new ArrayList<Position>();

				trailPositionsCopy.addAll(trailPositions);

				if (!neighbourDir.equals(trail.getOppositeEndOfTrailDir())) {
					trailPositionsCopy.add(neighbour);
					Trail newTrail = new Trail(trailPositionsCopy, neighbourDir, 0.0);
					newTrails.add(newTrail);
				} 
			}

			trails.addAll(newTrails);

			totalRiskOfDeath += Math.pow((1 / 3.0), trailSteps) * riskOfDeath;
		}

		return totalRiskOfDeath;

	}

	public Direction findNeighbourDir(int k) {
		Direction neighbourDir = null;
		
		switch (k) {
		case 0:
			neighbourDir = Direction.SOUTH;
			break;
		case 1:
			neighbourDir = Direction.EAST;
			break;
		case 2:
			neighbourDir = Direction.NORTH;
			break;
		case 3:
			neighbourDir = Direction.WEST;
			break;
		}
		return neighbourDir;
	}

	class Trail {
		private List<Position> trailPositions;
		private Position endOfTrail;
		private Direction endOfTrailDir;
		private double risk;

		Trail(List<Position> trailPositions, Direction endOfTrailDir, 
			  double risk) {
			this.trailPositions = trailPositions;
			this.endOfTrail = trailPositions.get(trailPositions.size() - 1);
			this.endOfTrailDir = endOfTrailDir;
			this.risk = risk;
		}

		public List<Position> getTrailPositions() {
			return trailPositions;
		}

		public void setRisk(double newRisk) {
			risk = newRisk;
		}

		public double getRisk() {
			return risk;
		}

		public Direction getEndOfTrailDir() {
			return endOfTrailDir;
		}

		public Direction getOppositeEndOfTrailDir() {
			return endOfTrailDir.turnLeft().turnLeft();
		}

		public Position getEndOfTrail() {
			return endOfTrail;
		}
	}
}
