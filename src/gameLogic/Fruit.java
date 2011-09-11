package gameLogic;

public class Fruit extends GameObject
{
	private int value;
	
	public Fruit(Position position)
	{
		super(position);
		value = 1;
	}
	
	public Fruit(Position position, int value)
	{
		super(position);
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
