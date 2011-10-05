package gameLogic;

import java.util.Map;
import java.util.HashMap;

public class Frame
{
	private Board board;
	
	public Frame(Board board)
	{
		this.board = new Board(board);
	}
	
	public Board getBoard()
	{
		return board;
	} 
}
