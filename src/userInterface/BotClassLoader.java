package userInterface;

import java.io.*;
import java.net.*;
import java.util.*;
import gameLogic.Brain;

class BotClassLoader extends ClassLoader
{
	private Map<String, BrainClassInfo> loadedBrainClasses = new HashMap<String, BrainClassInfo>();
	
	public BotClassLoader(ClassLoader parent)
	{		
		super(parent);
	}
	
	public void reloadBrain(String name)
	{
		BrainClassInfo brainClassInfo = loadedBrainClasses.get(name);
		if (brainClassInfo == null)
			throw new IllegalArgumentException("THE NAME " + name.toUpperCase() + " DOESN'T EVEN FUCKING EXIST");
		
		// INSURT RELOAD COAD HERE
	}
	
	public void reloadAllBrains()
	{
		for (String name : loadedBrainClasses.keySet())
			reloadBrain(name);
	}	
	
	public Brain getBrain(URL url, String name)
	{
		return newBrain(getBrainClass(url, name));
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
	
	
	private Class<?> getBrainClass(URL url, String name)
	{
		BrainClassInfo brainClassInfo = loadedBrainClasses.get(name);
		
		if (brainClassInfo == null)
		{
			try
			{
				Class<?> brainClass = loadBotClass(url, name);
				brainClassInfo = new BrainClassInfo(brainClass, url);
				loadedBrainClasses.put(name, brainClassInfo);
			}
			catch (ClassNotFoundException e)
			{
				throw new RuntimeException("Couldn't find class " + name + ": " + e);
			}
		}
		
		return brainClassInfo.getBrainClass();
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

	
	
	private class BrainClassInfo
	{
		private Class<?> brainClass;
		private URL url;
		
		public BrainClassInfo(Class<?> brainClass, URL url)
		{
			this.brainClass = brainClass;
			this.url = url;
		}
		
		public Class<?> getBrainClass()
		{
			return brainClass;
		}
		
		public URL getUrl()
		{
			return url;
		}
	}
}
