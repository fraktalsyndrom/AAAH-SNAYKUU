package userInterface;

import java.io.*;
import java.net.*;
import java.util.*;
import gameLogic.Brain;

class BotClassLoader extends ClassLoader
{
	private Map<String, Class<?>> loadedBrainClasses = new HashMap<String, Class<?>>();
	
	public BotClassLoader(ClassLoader parent)
	{		
		super(parent);
	}
	
	public void reloadBrain(String name)
	{
		Class<?> brainClass = loadedBrainClasses.get(name);
		if (brainClass == null)
			throw new IllegalArgumentException("THE NAME " + name.toUpperCase() + " DOESN'T EVEN EXIST");
		
		// INSURT RELOAD COAD HERE
	}
	
	public void reloadAllBrains()
	{
		for (String name : loadedBrainClasses.keySet())
			reloadBrain(name);
	}	
	
	public Brain getBrain(String name)
	{
		return newBrain(getBrainClass(name));
	}
	
	
	private Brain newBrain(Class<?> brainClass)
	{
		Object object;
		try
		{
			object = brainClass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("Couldn't instantiate class " + brainClass.getName() + ": " + e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Couldn't access class " + brainClass.getName() + ": " + e);
		}
		
		Brain brain = (Brain)object;
		
		return brain;
	}
	
	public Class<?> getClass(String name)
	{
		return getBrainClass(name);
	}
	
	private Class<?> getBrainClass(String name)
	{
		Class<?> brainClass = loadedBrainClasses.get(name);
		
		if (brainClass == null)
		{
			try
			{
				brainClass = loadClass("bot."+name, true);
				loadedBrainClasses.put(name, brainClass);
			}
			catch (ClassNotFoundException e)
			{
				throw new RuntimeException("Couldn't find class " + name + ": " + e);
			}
		}
		
		return brainClass;
	}
	
	
	protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		Class c = findLoadedClass (name);
		if (c == null)
		{
			try
			{
				c = findSystemClass(name);
			}
			catch (Exception e)
			{
				// Ignore these
			}
		}
		
		if (c == null)
		{
			// Convert class name argument to filename
			// Convert package names into subdirectories
			String filename = name.replace ('.', File.separatorChar) + ".class";
			
			try
			{
				byte[] data = loadClassData(filename);

				c = defineClass (name, data, 0, data.length);
				if (c == null)
					throw new ClassNotFoundException(name);
			}
			catch (IOException e)
			{
				throw new ClassNotFoundException ("Error reading file: " + filename);
			}
		}
		
		if (resolve)
			resolveClass(c);
		
		return c;
	}
	
	private byte[] loadClassData(String filename) throws IOException
	{
		// Create a file object relative to directory provided
		File f = new File(".", filename);

		// Get size of class file
		int size = (int)f.length();

		// Reserve space to read
		byte buff[] = new byte[size];

		// Get stream to read from
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);

		// Read in data
		dis.readFully(buff);

		// close stream
		dis.close();

		// return data
		return buff;
	}
	
}
