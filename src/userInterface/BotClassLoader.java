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
				Class<?> brainClass = loadClass("bot."+name, true); //(url, name);
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
			URLConnection urlConnection = url.openConnection();
			InputStream input = urlConnection.getInputStream();
			
			byte[] classData = readBufferFromStream(input);
						
			//return defineClass("bot."+name, classData, 0, classData.length);
			Class newClass = defineClass(null, classData, 0, classData.length);
			if (newClass != null)
				return newClass;
				
		}
		catch (IOException e)
		{
			System.out.println(e);
		}

		throw new ClassNotFoundException(name);
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
