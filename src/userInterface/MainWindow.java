package userInterface;

import javax.swing.*;
import gameLogic.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MainWindow extends JFrame
{
	private Session session;
	private GameBoard gameBoard;
	private GameLoop gameLoop;
	
	public MainWindow()
	{
		session = new Session(20, 40, 3, 10, 100);
		
		gameBoard = new GameBoard(session, 16);
				
		add(gameBoard);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		Brain brain = loadBrain("Mad");

		
		if (brain == null)
			JOptionPane.showMessageDialog(this, "BRAINFUCK SHIT");
		
		GameObjectType got = new GameObjectType("Snake", true);
		LinkedList<Position> snakePosition = new LinkedList<Position>();
		snakePosition.addFirst(new Position(10, 29));
		snakePosition.addFirst(new Position(10, 30));
		Snake snake = new Snake(got, "Stefan", brain);
		
		session.addSnake(snake);
		session.prepareForStart();
		
		gameLoop = new GameLoop();
		gameLoop.start();
	}
	
	
	private class GameLoop extends Thread
	{
		public void run()
		{
			while(true)
			{
				session.tick();
				gameBoard.repaint();
				
				try
				{
					Thread.currentThread().sleep(500);
				}
				catch (InterruptedException e)
				{
					break;
				}
				
			}
		}
	}
	
	private Brain loadBrain(String name)
	{
		ClassLoader parentClassLoader = MainWindow.class.getClassLoader();
	
		BotClassLoader classLoader = new BotClassLoader(parentClassLoader);
		
		Class<?> brainClass;
		try
		{
			brainClass = classLoader.loadBotClass(name);
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(this, "Couldn't find class " + name + ": " + e);
			return null;
		}
		
		Object object;
		try
		{
			object = brainClass.newInstance();
		}
		catch (InstantiationException e)
		{
			JOptionPane.showMessageDialog(this, "Couldn't instantiate class " + name + ": " + e);
			return null;
		}
		catch (IllegalAccessException e)
		{
			JOptionPane.showMessageDialog(this, "Couldn't access class " + name + ": " + e);
			return null;
		}
		
		Brain brain = (Brain)object;
		
		return brain;
	}
	
	
	
	private class BotClassLoader extends ClassLoader
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

		public Class loadBotClass(String name) throws ClassNotFoundException
		{
			try
			{				
				URL url = new URL("file:./bot/" + name + ".class");
				URLConnection urlConnection = url.openConnection();
				InputStream input = urlConnection.getInputStream();
				
				byte[] classData = readBufferFromStream(input);
				
				return defineClass("bot."+name, classData, 0, classData.length);
			}
			catch (MalformedURLException e)
			{
				JOptionPane.showMessageDialog(MainWindow.this, e);
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(MainWindow.this, e);
			}

			return null;
		}

	}
	
}
