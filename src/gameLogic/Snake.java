package gameLogic;

import java.util.*;

public class Snake
{
	private String name;
	private Brain brain;
	private Direction direction;
	private boolean grow = false;
	
	public Snake(String name, Brain brain, Position position)
	{
		this.name = name;
		this.brain = brain;
		this.direction = new Direction(Direction.NORTH);
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
