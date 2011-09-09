public class SnakeSegment extends GameObject
{
	private SnakeSegment next;
	
	public SnakeSegment(Board board, Position position, SnakeSegment next)
	{
		super(board, position);
		
		this.next = next;
	}
	
	public SnakeSegment getNext()
	{
		return next;
	}
}
