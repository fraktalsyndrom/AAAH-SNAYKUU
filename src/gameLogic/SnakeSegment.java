package gameLogic;

import java.io.Serializable;

public class SnakeSegment implements Serializable
{
	private Position pos;
	private Direction dir;
	
	SnakeSegment(Position p, Direction d)
	{
		pos = p;
		dir = d;
	}
	
	SnakeSegment(SnakeSegment other)
	{
		pos = other.pos;
		dir = other.dir;
	}
	
	public Position getPos()
	{
		return pos;
	}
	
	public Direction getDir()
	{
		return dir;
	}
}
