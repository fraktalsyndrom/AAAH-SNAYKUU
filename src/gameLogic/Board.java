package gameLogic;

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
	
	public boolean hasGameObject(Position p)
	{
		return hasGameObject(p.getX(), p.getY());
	}
	
	public boolean hasGameObject(int x, int y)
	{
		return (!board[x][y].isEmpty());
	}
	
	public Square getSquare(Position p)
	{
		return board[p.getX()][p.getY()];
	}
	
	void addGameObject(GameObjectType obj, Position p)
	{
		board[p.getX()][p.getY()].addGameObject(new GameObject(obj));
	}
	
	void addGameObject(GameObject obj, Position p)
	{
		board[p.getX()][p.getY()].addGameObject(obj);
	}
	
	void clearSquare(Position p)
	{
		board[p.getX()][p.getY()].clear();
	}
	
	void removeGameObject(GameObject obj, Position p)
	{
		board[p.getX()][p.getY()].removeGameObject(obj);
	}
	
	void removeFruit(Position p)
	{
		board[p.getX()][p.getY()].removeFruit();
	}
}
