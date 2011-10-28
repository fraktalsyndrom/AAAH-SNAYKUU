package userInterface;

import java.io.*;
import java.net.*;
import java.util.*;
import gameLogic.Brain;

class BotClassLoader extends ClassLoader
{
	private Map<String, BrainInfo> loadedBrains = new HashMap<String, BrainInfo>();
	
	public BotClassLoader(ClassLoader parent)
	{		
		super(parent);
	}
	
	public void reloadBrain(String name)
	{
		BrainInfo brainInfo = loadedBrains.get(name);
		if (brainInfo == null)
			throw new IllegalArgumentException("THE NAME " + name.toUpperCase() + " DOESN'T EVEN FUCKING EXIST");
		
		// INSURT RELOAD COAD HERE
	}
	
	public void reloadAllBrains()
	{
		for (String name : loadedBrains.keySet())
			reloadBrain(name);
	}	
	
	public Brain getBrain(URL url, String name)
	{
		BrainInfo brainInfo = loadedBrains.get(name);
		
		if (brainInfo == null)
		{
			Brain brain = loadBrain(url, name);
			brainInfo = new BrainInfo(brain, url);
			loadedBrains.put(name, brainInfo);
		}
		
		return brainInfo.getBrain();
	}
	
	
	private Brain loadBrain(URL url, String name)
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

	
	
	private class BrainInfo
	{
		private Brain brain;
		private URL url;
		
		public BrainInfo(Brain brain, URL url)
		{
			this.brain = brain;
			this.url = url;
		}
		
		public Brain getBrain()
		{
			return brain;
		}
		
		public URL getUrl()
		{
			return url;
		}
	}
}
