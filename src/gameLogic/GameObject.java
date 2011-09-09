public abstract class GameObject
{
	private Position position;
	private Board board;
	
	public GameObject(Board board, Position position)
	{
		this.board = board;
		this.position = position;
	}
	
	public Position getPosition()
	{
		return position;
	}
}
