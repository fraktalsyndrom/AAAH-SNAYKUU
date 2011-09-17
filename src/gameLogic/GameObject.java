package gameLogic;


public class GameObject
{	
	GameObjectType type;
	public GameObject(GameObjectType type)
	{
		this.type = type;
	}
	
	public GameObjectType getType()
	{
		return type;
	}
}