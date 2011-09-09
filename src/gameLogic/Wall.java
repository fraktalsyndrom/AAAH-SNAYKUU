public class Wall extends GameObject 
{
	public Wall(Board board, Position position) 
	{
		super(board, position);
	}
	
	public boolean isLethal()
	{
		return true;
	}
}