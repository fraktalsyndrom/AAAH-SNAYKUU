package gameLogic;

import java.io.Serializable;
import java.util.*;

/** 
 * Objects of this class represent a single square of the game board. Each square has a List<GameObject>
 * of all the GameObjects it contains, but it will usually only contain 0 or 1 GameObjects. The only time
 * it can contain more than one game object is when a snake collides, with either a wall or another
 * snake.
 *
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 */

public class Square implements Serializable
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
	 * Generates a list of all snakes in this square. Usually there is zero or one
	 * snake in the same square, but there can be more than one snake in the
	 * same square if one snake collides with another. It is therefore guaranteed
	 * that there cannot be more than one living snake in the same square.
	 * 
	 * @return	An ArrayList containing the snakes in this square, might be empty.
	 */
	public ArrayList<Snake> getSnakes()
	{
		ArrayList<Snake> snakes = new ArrayList<Snake>();
		for (GameObject object : objects)
			if (object instanceof Snake)
				snakes.add((Snake)object);
		return snakes;
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
	
	void removeFruit()
	{
		Iterator<GameObject> objectIterator = objects.iterator();
		while (objectIterator.hasNext())
		{
			GameObject currentGameObject = objectIterator.next();
			if (currentGameObject.getType().equals("Fruit"))
				objects.remove(currentGameObject);
		}
	}
	
	void clear()
	{
		objects.clear();
	}
}
