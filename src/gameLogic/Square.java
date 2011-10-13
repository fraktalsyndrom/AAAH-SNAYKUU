package gameLogic;

import java.util.*;

/**
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 * 
 * Objects of this class represent a single square of the game board. Each square has a List<GameObject>
 * of all the GameObjects it contains, but it will usually only contain 0 or 1 GameObjects. The only time
 * it can contain more than one is in between turns; a special case that doesn't need any attention from a
 * brain programmer.
 */

public class Square
{
	private ArrayList<GameObject> objects;
	
	Square()
	{
		objects = new ArrayList<GameObject>();
	}
	
	Square(Square other)
	{
		this.objects = new ArrayList<GameObject>(other.objects);
	}
	
	/**
	 * Returns whether or not this Square is empty, e.g. has no game objects inside it.
	 * 
	 * @return	True if the square is empty, false if not.
	 */
	public boolean isEmpty()
	{
		return objects.isEmpty();
	}
	
	
	public boolean hasObjectType(String typeName)
	{
		for (GameObject object : objects)
			if (object.getType().getName().equals(typeName))
				return true;
		return false;
	}
	
	/**
	 * Returns whether or not this Square contains a fruit. Checks by using
	 * hasObjectType.
	 * 
	 * @return	True if the square has a fruit, false if not.
	 */
	public boolean hasFruit()
	{
		return hasObjectType("Fruit");
	}
	
	/**
	 * Returns whether or not this Square contains a snake.
	 * 
	 * @return	True if the square contains a snake, false if not.
	 */
	public boolean hasSnake()
	{
		for (GameObject object : objects)
			if (object instanceof Snake)
				return true;
		return false;
	}
	
	/**
	 * Returns whether or not this Square contains a wall. Checks by using
	 * hasObjectType.
	 * 
	 * @return	True if the square contains a wall, false if not.
	 */
	public boolean hasWall()
	{
		return hasObjectType("Wall");
	}
	
	/**
	 * Checks if this square is lethal, e.g. if it contains a lethal GameObject. 
	 * 
	 * @return	True if the square contains a wall, false if not.
	 */
	public boolean isLethal()
	{
		for (GameObject object : objects)
			if (object.getType().isLethal())
				return true;
		return false;
	}
	
	/**
	 * Returns the game object this square contains. Technically returns the first object in the 
	 * objects ArrayList. Could theoretically throw an IllegalStateException, but that will never 
	 * be encountered by a brain programmer.
	 * Returns null if the square is empty.
	 * 
	 * @return	The GameObject in this square. null if the square is empty.
	 */
	public GameObject getGameObject()
	{
		if (objects.size() > 1)
			throw new IllegalStateException("Trying to getGameObject from a Square with more than one object.");
		if (objects.isEmpty())
			return null;
		return objects.get(0);
	}
	
	// Remove a fruit from the square, returning its value.
	int eatFruit()
	{
		for (GameObject object : objects)
		{
			if (object.getType().getName().equalsIgnoreCase("Fruit"))
			{
				int value = object.getType().getValue();
				removeGameObject(object);
				return value;
			}
		}
		return 0;
	}
	
	void addGameObject(GameObject newObject)
	{
		objects.add(newObject);
	}
	
	void removeGameObject(GameObject object)
	{
		objects.remove(object);
	}
	
	ArrayList<Snake> getSnakes()
	{
		ArrayList<Snake> snakes = new ArrayList<Snake>();
		for (GameObject object : objects)
			if (object instanceof Snake)
				snakes.add((Snake)object);
		return snakes;
	}
	
	void removeFruit()
	{
		Iterator objectIterator = objects.iterator();
		while (objectIterator.hasNext())
		{
			GameObject currentGameObject = (GameObject)objectIterator.next();
			if (currentGameObject.getType().equals("Fruit"))
				objects.remove(currentGameObject);
		}
	}
	
	void clear()
	{
		objects.clear();
	}
}
