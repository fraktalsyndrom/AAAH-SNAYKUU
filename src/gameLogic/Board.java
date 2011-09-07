public class Board 
{
	private Square[][] board;
	
	public Board(int width, int height)
	{
		if (width < 1 || height < 1)
			throw new IllegalArgumentException("Board size must be greater than 0");
		board = new Square[width][height];
	}
	
	public int getWidth()
	{
		return board.length;
	}
	
	public int getHeight()
	{
		return board[0].length;
	}
	
	public Square getSquare(int x, int y)
	{
		return board[x][y];
	}
}
