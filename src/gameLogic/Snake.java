package gameLogic;

import java.util.*;

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
	
	void placeOnBoard(LinkedList<Position> segments, Direction originalDirection)
	{
		this.segments = segments;
		direction = originalDirection;
	}
	
	public Position getHeadPosition()
	{
		return segments.getFirst();
	}
	
	public Position getTailPosition()
	{
		return segments.getLast();
	}
	
	public boolean isDead()
	{
		return isDead;
	}
	
	public Direction getCurrentDirection()
	{
		return direction;
	}
	
	public int getScore()
	{
		return score;
	}
	
	/**
	 * Get the number of turns this snake have lived.
	 * 
	 * @return Age of the snake, in turns
	 */
	public int getLifespan()
	{
		return lifespan;
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
	
	public String toString()
	{
		return name;
	}
	
	public boolean equals(Object other)
	{
		if (other instanceof Snake)
		{
			Snake otherSnake = (Snake)other;
			return (name.equals(otherSnake.name));
		}
		return false;
	}
}
