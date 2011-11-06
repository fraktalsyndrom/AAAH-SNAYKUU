package gameLogic;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

public class Frame implements Serializable
{
	private Board board;
	private Set<Snake> snakes = new HashSet<Snake>();
	
	public Frame(Board board, Set<Snake> snakes)
	{
		this.board = new Board(board);

		for (Snake snake : snakes)
			this.snakes.add(new Snake(snake));
	}
	
	public Board getBoard()
	{
		return board;
	} 
	
	public Set<Snake> getSnakes()
	{
		return snakes;
	}
}
