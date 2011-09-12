package gameLogic;

public class GameObjectType
{
	private String name;
	private boolean isLethal;
	private int value;
	
	public GameObjectType(String name, boolean isLethal, int value)
	{
		this.name = name;
		this.isLethal = isLethal;
		this.value = value;
	}
	
	public GameObjectType(String name, boolean isLethal)
	{
		this.name = name;
		this.isLethal = isLethal;
		value = 0;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public boolean isLethal()
	{
		return isLethal;
	}
}