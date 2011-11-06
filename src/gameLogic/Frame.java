package gameLogic;

import java.util.Set;
import java.util.HashSet;

public class Frame
{
	private Board board;
	private Set<Snake> snakes;
	
	public Frame(Board board, Set<Snake> snakes)
	{
		this.board = new Board(board);
		this.snakes = new HashSet<Snake>();
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
