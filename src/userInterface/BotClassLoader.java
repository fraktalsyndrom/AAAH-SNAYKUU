package userInterface;

import java.io.*;
import java.net.*;
import java.util.*;

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

	public Class loadBotClass(URL url, String name) throws ClassNotFoundException
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
}
