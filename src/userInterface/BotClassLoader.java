package userInterface;

import java.io.*;
import java.net.*;
import java.util.*;
import gameLogic.Brain;

class BotClassLoader extends ClassLoader
{
	public BotClassLoader(ClassLoader parent)
	{
		super(parent);
	}
	
	private byte[] readBufferFromStream(InputStream input) throws IOException
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			
		int data = input.read();
		while (data != -1)
		{
			buffer.write(data);
			data = input.read();
		}
		input.close();
		
		return buffer.toByteArray();
	}

	private Class loadBotClass(URL url, String name) throws ClassNotFoundException
	{
		try
		{				
			//URL url = new URL("file:" + path);
			URLConnection urlConnection = url.openConnection();
			InputStream input = urlConnection.getInputStream();
			
			byte[] classData = readBufferFromStream(input);
			
			return defineClass("bot."+name, classData, 0, classData.length);
		}
		catch (IOException e)
		{
			System.out.println(e);
		}

		return null;
	}
	
	public Brain loadBrain(URL url, String name)
	{
		Class<?> brainClass;
		try
		{
			brainClass = loadBotClass(url, name);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Couldn't find class " + name + ": " + e);
		}
		
		System.out.println("Brain class: " + brainClass + ", HC: " + brainClass.hashCode());
		
		Object object;
		try
		{
			object = brainClass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("Couldn't instantiate class " + name + ": " + e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Couldn't access class " + name + ": " + e);
		}
		
		Brain brain = (Brain)object;
		
		return brain;
	}
}
