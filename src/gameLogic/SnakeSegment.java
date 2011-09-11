package gameLogic;


public class SnakeSegment extends GameObject
{
	private SnakeSegment next;
	
	public SnakeSegment(Position position, SnakeSegment next)
	{
		super(position);
		
		this.next = next;
	}
	
	public SnakeSegment getNext()
	{
		return next;
	}
	
	public boolean isLethal()
	{
		return true;
	}
}
