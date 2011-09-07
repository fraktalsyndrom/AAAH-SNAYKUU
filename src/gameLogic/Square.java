
public class Square
{
	private Position position;
	
	public Square(Position position)
	{
		this.position = position;
	}
	
	public Position getPosition()
	{
		return position;
	}
	
	public boolean containsWall()
	{
		return false;	//temp
	}
	
	public boolean containsFruit()
	{
		return false;	//temp
	}
	
	public boolean containsSnake()
	{
		return false;	//temp
	}
	
	public boolean containsHead()
	{
		return false;
	}
}
