package gameLogic;


public class Position 
{
	private int x, y;
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position() 
	{
		x = 0;
		y = 0;
	}
	
	public Position(Position position)
	{
		x = position.x;
		y = position.y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean equals(Object other)
	{
		if (!(other instanceof Position))
			return false;
		
		Position otherPosition = (Position)other;

		return (x == otherPosition.x && y == otherPosition.y);
	}
}
