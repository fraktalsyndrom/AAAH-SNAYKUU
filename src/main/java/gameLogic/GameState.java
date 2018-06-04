package gameLogic;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * The GameState is a representation of the game at a given moment in time. It contains references
 * to the game board (containing a matrix of Squares, which in turn contain all game objects),
 * the current Metadata (turns until growth/spawning of fruit, among other things), all snakes
 * participating in this game session, and an ErrorState enum.
 *
 * @author 	Sixten Hilborn
 * @author	Arian Jafari
 */

public class GameState
{
	private ErrorState errorState;
	private Board board;
	private Set<Snake> snakes;
	private Metadata metadata;
	
	GameState(Board currentBoard, Set<Snake> snakes, Metadata metadata, ErrorState errorState)
	{
		board = new Board(currentBoard);
		this.snakes = new HashSet<Snake>(snakes);
		this.metadata = metadata;
		this.errorState = errorState;
	}
	
	/**
	 * Returns a Set containing all snakes in the game, both dead ones and alive ones.
	 *
	 * @return 	A Set<Snake> containing all snakes.
	 * @see		Snake
	 */
	public Set<Snake> getSnakes()
	{
		return snakes;
	}
	
	/**
	 * Returns a Board object, which constists of a 2D-array of Square objects.
	 *
	 * @return 	A representation of the current game board.
	 * @see		Board
	 */
	public Board getBoard()
	{
		return board;
	}
	
	/**
	 * Method for getting the current game metadata, containing (among other things) 
	 * time until the next fruit spawns and time until snake growth.
	 *
	 * @return 	The current Metadata object.
	 * @see		Metadata
	 */
	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * Returns the ErrorState for the previous turn, for example telling a brain it
	 * took too long to decide last turn.
	 *
	 * @return	The ErrorState object for last turn.
	 * @see 		ErrorState
	 */
	public ErrorState getErrorState()
	{
		return errorState;
	}
	
	
	/**
	 * This method can be used to help calculate whether or not a given snake will collide next
	 * turn if it continues in a given direction. It looks at the square the snake will end up in,
	 * and then checks if that square contains a lethal object. Note that this method returning false 
	 * does NOT guarantee that the snake will survive. For example, it is possible that a two
	 * snakes will move into an empty square during the same turn, causing the death of
	 * them both.
	 * 
	 * @param	snake	The snake you wish you perform the check for.
	 * @param	dir		The hypothetical direction in which the snake moves.
	 * @return 	True if the next position contains a lethal object, false if not.
	 */
	public boolean willCollide(Snake snake, Direction dir)
	{
		Position currentHeadPosition = snake.getHeadPosition();
		Position nextHeadPosition = calculateNextPosition(dir, currentHeadPosition);
		return (board.getSquare(nextHeadPosition).isLethal());
	}
	
	/**
	 * Gets the next position a snake would end up in if it continues in this direction.
	 * 
	 * @param 	direction		The current direction of the snake.
	 * @param	oldPosition	The current position of the snake.
	 * @return	The next position if movement continues in this direction.
	 */
	public static Position calculateNextPosition(Direction direction, Position oldPosition)
	{
		int x = oldPosition.getX(), y = oldPosition.getY();
		
		switch (direction)
		{
			case WEST:
				--x;
				break;
			
			case NORTH:
				--y;
				break;
			
			case EAST:
				++x;
				break;
			
			case SOUTH:
				++y;
				break;
		}
		
		return new Position(x, y);
	}
	
	/**
	 * Gets a list containing the positions of all the fruits currently on the board. Note that 
	 * the list will be empty if the number of fruits on the board is 0.
	 * 
	 * @return	The positions of the fruits currently on the board.
	 */
	public ArrayList<Position> getFruits()
	{
		ArrayList<Position> positionsContainingFruit = new ArrayList<Position>();
		for (int x = 0; x < metadata.getBoardWidth(); ++x)
		{
			for (int y = 0; y < metadata.getBoardHeight(); ++y)
			{
				Position currentPosition = new Position(x, y);
				if (board.hasFruit(currentPosition))
					positionsContainingFruit.add(currentPosition);
			}
		}
		return positionsContainingFruit;
	}
	
	/**
	 * Gets a list containing the positions of all the walls currently on the board. Note that 
	 * the list will be empty if the number of walls on the board is 0.
	 * 
	 * @return	The positions of the walls currently on the board.
	 */
	public ArrayList<Position> getWalls()
	{
		ArrayList<Position> positionsContainingWall = new ArrayList<Position>();
		for (int x = 0; x < metadata.getBoardWidth(); ++x)
		{
			for (int y = 0; y < metadata.getBoardHeight(); ++y)
			{
				Position currentPosition = new Position(x, y);
				if (board.hasWall(currentPosition))
					positionsContainingWall.add(currentPosition);
			}
		}
		return positionsContainingWall;
	}
	
	/**
	 * Returns in which direction one has to move in order to reach one
	 * position from another one. Returns an ArrayList containing either one
	 * or two elements. For example, it might contain either only <code>WEST</code>
	 * if the destination postion is directly west of the starting position, or it
	 * may contain both <code>WEST</code> and <code>NORTH</code> if 
	 * the destination is towards the northwest.
	 * 
	 * @param	from		The starting position.
	 * @param	to		The destination position.
	 * @return	containing either one or two Directions, pointing towards
	 *			the destination.
	 */
	public static ArrayList<Direction> getRelativeDirections(Position from, Position to)
	{
		ArrayList<Direction> directions = new ArrayList<Direction>();
		if(from.getX() < to.getX())
			directions.add(Direction.EAST);
		else if(from.getX() > to.getX())
			directions.add(Direction.WEST);
		
		if(from.getY() < to.getY())
			directions.add(Direction.SOUTH);
		else if(from.getY() > to.getY())
			directions.add(Direction.NORTH);
		
		return directions;
	}
	
	/**
	 * This method can be used to calculate the distance between two positions.
	 * 
	 * @param 	from		The position from which you wish to calculate the distance.
	 * @param	to		The position to which you wish to calculate the distance.
	 * @return	An integer representing the distance between two positions.
	 */
	public static int distanceBetween(Position from, Position to)
	{
		int distance = 0;
		
		// Calculate distance in the x-axis
		distance += Math.abs(from.getX() - to.getX());
		
		// Calculate distance in the y-axis
		distance += Math.abs(from.getY() - to.getY());

		return distance;
	}
}
