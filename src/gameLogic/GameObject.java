package gameLogic;


public abstract class GameObject
{
	private Position position;
	
	public GameObject(Position position)
	{
		this.position = position;
	}
	
	public Position getPosition()
	{
		return position;
	}
	
	public abstract boolean isLethal();
}
