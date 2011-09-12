package gameLogic;

import java.util.*;

public class Square
{
	private ArrayList<GameObject> objects;
	
	public Square()
	{
		objects = new ArrayList<GameObject>();
	}
	
	public Square(ArrayList<GameObject> objects)
	{
		this.objects = objects;
	}
	
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
	
	public boolean hasFruit()
	{
		return hasObjectType("Fruit");
	}
	
	public boolean hasSnake()
	{
		return hasObjectType("SnakeSegment");
	}
	
	public boolean hasWall()
	{
		return hasObjectType("Wall");
	}
	
	public boolean isLethal()
	{
		for (GameObject object : objects)
			if (object.getType().isLethal())
				return true;
		return false;
	}
	
	// Remove a fruit from the square, returning its value.
	public int eatFruit()
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
