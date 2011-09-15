package gameLogic;

import java.util.*;

public class Snake extends GameObject
{
	private String name;
	private Brain brain;
	private Direction direction;
	private boolean grow = false;
	private LinkedList<Position> segments;
	
	public Snake(GameObjectType type, String name, Brain brain, Position position)
	{
		super(type);
		this.name = name;
		this.brain = brain;
		this.direction = new Direction(Direction.NORTH);
		segments = new LinkedList<Position>();
	}
	
	public Position getHead()
	{
		return segments.getFirst();
	}
	
	public Position getTail()
	{
		return segments.getLast();
	}
	
	void moveHead(Position pos)
	{
		segments.addFirst(pos);
	}	
	
	void removeTail()
	{
		segments.removeLast();
	}
	
	Direction getNextMove(GameState currentGameState)
	{
		return brain.getNextMove(currentGameState);
	}
	
	Brain getBrain()
	{
		return brain;
	}
	
	void tooSlowFault()
	{
		brain.tooSlowFault();
	}
	
	public String toString()
	{
		return name;
	}
	
	public boolean equals(Object other)
	{
		if (other instanceof Snake) {
			Snake otherSnake = (Snake)other;
			return (name.equals(otherSnake.name));
		}
		return false;
	}
}
