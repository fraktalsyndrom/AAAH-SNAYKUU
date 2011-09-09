package gameLogic;

public class Fruit extends GameObject
{
	private int value;
	
	public Fruit(Board board, Position position)
	{
		super(board, position);
		value = 1;
	}
	
	public Fruit(Board board, Position position, int value)
	{
		super(board, position);
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public boolean isLethal() 
	{
		return false;
	}
}