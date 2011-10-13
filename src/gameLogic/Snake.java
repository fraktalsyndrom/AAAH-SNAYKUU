package gameLogic;

import java.util.LinkedList;

/**
 * @author 	Sixten Hilborn
 * @author	Arian Jafari
 *
 * The Snake class is a representation of each snake currently in the game,
 * including its name, its current direction, its position on the game board,
 * and its current statistics.
 *
 * It is a subclass of the GameObject class, in order for snakes to be able to
 * be inserted into Square objects.
 */

public class Snake extends GameObject
{
	private String name;
	private Brain brain;
	private Direction direction;
	private boolean grow = false;
	private LinkedList<Position> segments;
	private int score = 0;
	private int lifespan = 0;
	private boolean isDead = false;
	
	public Snake(GameObjectType type, String name, Brain brain)
	{
		super(type);
		this.name = name;
		this.brain = brain;
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
		return direction;
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
		direction = originalDirection;
	}
	
	void setCurrentDirection(Direction direction)
	{
		this.direction = direction;
	}
	
	void moveHead(Position pos)
	{
		segments.addFirst(pos);
	}	
	
	void removeTail()
	{
		segments.removeLast();
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
}
