package gameLogic;

public class Board 
{
	private GameObject[][] board;
	
	public Board(int width, int height)
	{
		if (width < 1 || height < 1)
			throw new IllegalArgumentException("Board size must be greater than 0");
		board = new GameObject[width][height];
	}
	
	public int getWidth()
	{
		return board.length;
	}
	
	public int getHeight()
	{
		return board[0].length;
	}
	
	public GameObject getGameObject(int x, int y)
	{
		return board[x][y];
	}
	
	public GameObject getGameObject(Position p)
	{
		return board[p.getX()][p.getY()];
	}
}
