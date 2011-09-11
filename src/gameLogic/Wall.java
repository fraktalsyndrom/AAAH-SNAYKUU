package gameLogic;

public class Wall extends GameObject 
{
	public Wall(Position position) 
	{
		super(position);
	}
	
	public boolean isLethal()
	{
		return true;
	}
}
