package gameLogic;

import java.io.Serializable;
import java.util.LinkedList;
import java.awt.Color;

/**
 * The Snake class is a representation of each snake currently in the game,
 * including its name, its current direction, its position on the game board,
 * and its current statistics.
 *
 * It is a subclass of the GameObject class, in order for snakes to be able to
 * be inserted into Square objects.
 *
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 */

public class Snake extends GameObject implements Serializable
{
	private String name;
	private Brain brain;
	private LinkedList<Position> segments;
	private LinkedList<SnakeSegment> directionLog = new LinkedList<SnakeSegment>();
	private int score = 0;
	private int lifespan = 0;
	private boolean isDead = false;
	private Color color;
	
	public Snake(GameObjectType type, String name, Brain brain, Color color)
	{
		super(type);
		this.name = name;
		this.brain = brain;
		this.color = color;
	}
	
	public Snake(Snake other)
	{
		super(other.getType());
		this.name = other.name;
		this.brain = null;
		this.segments = new LinkedList<Position>(other.segments);
		this.directionLog = new LinkedList<SnakeSegment>(other.directionLog);
		this.score = other.score;
		this.lifespan = other.lifespan;
		this.isDead = other.isDead;
		this.color = other.color;
	}
	
	/**
	 * Get a list of all the squares this snake has occupied.
	 * 
	 * @return A list of the positions of the occupied squares.
	 */
	public LinkedList<Position> getSegments()
	{
		return new LinkedList<Position>(segments);
	}
	
	/**
	 * Get a list of SnakeSegments, which represent each square this snake is 
	 * occupying together with its direction at each point.
	 * 
	 * This method is mainly useful for drawing purposes, getSegments() is 
	 * recommended for most other uses.
	 * 
	 * @return A list of this snake's positions and directions.
	 */
	public LinkedList<SnakeSegment> getDrawData()
	{
		return new LinkedList<SnakeSegment>(directionLog);
	}
	
	/**
	 * Get the direction of a specific segment in this snake.
	 * 
	 * @param position The position (in x-y coordinates) of the segment.
	 * @return The direction of that segment, or null if this snake has no segment on the specified position.
	 */
	
	@Deprecated //also no longer used anywhere
	public Direction getDirection(Position position)
	{
		for(SnakeSegment ss : directionLog)
		{
			if(ss.getPos().equals(position))
			{
				return ss.getDir();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the current position of this snake's head.
	 *
	 * @return	The Position of the snake's head.
	 * @see		Position
	 */
	public Position getHeadPosition()
	{
		return segments.getFirst();
	}
	
	/**
	 * Gets the current position of the last segment of the snake's tail.
	 *
	 * @return	The Position of the snake's last tail segment.
	 */
	public Position getTailPosition()
	{
		return segments.getLast();
	}
	
	/**
	 * Returns whether or not this snake is dead. Note that a dead snake won't be 
	 * removed from the board; it will only be unable to move and unable to win the
	 * game.
	 *
	 * @return	A boolean; true if the snake is dead, false if not.
	 */
	public boolean isDead()
	{
		return isDead;
	}
	
	/**
	 * Gets the direction this snake moved in last turn. Note that it does not actually
	 * get the direction the snake will move in next turn.
	 *
	 * @return	The Direction in which the snake moved last turn.
	 */
	public Direction getCurrentDirection()
	{
		return directionLog.getFirst().getDir();
	}
	
	/**
	 * Gets the number of fruits this snake has eaten. 
	 *
	 * @return	The number of fruits eaten by this snake.
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Gets the number of turns this snake has lived. If the snake is currently dead,
	 * this method returns the number of turns it was alive before its death.
	 * 
	 * @return 	Age of the snake, in turns.
	 */
	public int getLifespan()
	{
		return lifespan;
	}
	
	/**
	 * Returns a string representation of this snake, consisting of a String of its name.
	 * 
	 * @return	The snake's name as a string.
	 */
	public String toString()
	{
		return name;
	}
	
	void placeOnBoard(LinkedList<Position> segments, Direction originalDirection)
	{
		this.segments = segments;
		
		for(Position p : segments)
		{
			directionLog.add(new SnakeSegment(p, originalDirection));
		}
	}
	
	Position moveHead(Direction dir)
	{
		Position pos = dir.calculateNextPosition(getHeadPosition());
		segments.addFirst(pos);
		directionLog.addFirst(new SnakeSegment(pos, dir));
		
		return pos;
	}
	
	Position removeTail()
	{
		directionLog.removeLast();
		return segments.removeLast();
	}
	
	void kill()
	{
		isDead = true;
	}
	
	Brain getBrain()
	{
		return brain;
	}
		
	void addScore(int points)
	{
		score += points;
	}
	
	void increaseLifespan()
	{
		++lifespan;
	}
	
	void removeBrain()
	{
		brain = null;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public String getName()
	{
		return name;
	}
}
