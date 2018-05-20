package gameLogic;

import java.io.Serializable;

/**
 * Represents a type of object that may be present on the board. Examples include
 * Fruit and Wall. Instances of some GameObjectTypes are lethal to collide with,
 * while others might give points if "eaten".
 *
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 */
public class GameObjectType implements Serializable
{
	private String name;
	private boolean isLethal;
	private int value;
	
	/**
	 * Constructs a GameObjectType object with the given name, lethality and
	 * numeric point value.
	 */
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
	
	/**
	 * Returns the name of this GameObjectType, for instance "Fruit" or "Wall".
	 * 
	 * @return	the name of this GameObjectType.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the number of points objects of this type are worth. The default value
	 * for fruit is 1.
	 * 
	 * @return	the number of points that are granted by "eating" objects of
	*			this GameObjectType.
	 */
	public int getValue()
	{
		return value;
	}
	
	public boolean isLethal()
	{
		return isLethal;
	}
}
