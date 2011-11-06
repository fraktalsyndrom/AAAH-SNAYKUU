package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;
import java.util.Map;

public class SettingsWindow extends JFrame
{
	private boolean done = false;
	private JTabbedPane tabbedPane;	
	private SnakeSettingsPanel snakeSettingsPanel;
	private GameSettingsPanel gameSettingsPanel;
	private DeveloperPanel developerPanel;
	
	
	private JButton startButton;
	
	public SettingsWindow()
	{
		setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
		
		snakeSettingsPanel = new SnakeSettingsPanel();
		gameSettingsPanel = new GameSettingsPanel();
		developerPanel = new DeveloperPanel(this);
		
		tabbedPane.addTab("Snayks", snakeSettingsPanel);
		tabbedPane.addTab("Game settings", gameSettingsPanel);
		tabbedPane.addTab("Developer", developerPanel);
		
		startButton = new JButton("Start");
		startButton.addActionListener(new StartButtonListener());
		
		add(tabbedPane, BorderLayout.CENTER);
		add(startButton, BorderLayout.SOUTH);
		
		setSize(600, 400);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
	}
	
	public void putThisDamnWindowInMyFace()
	{
		done = false;
		setVisible(true);
	}
	
	
	private class StartButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			synchronized(SettingsWindow.this)
			{
				done = true;
			}
		}
	}
	
	public synchronized boolean isDone()
	{
		return done;
	}
	
	public Session generateSession()
	{
		Metadata metadata = gameSettingsPanel.generateMetadata();
		
		Session session = new Session(metadata);
		
		GameObjectType objectType = new GameObjectType("Snake", true);
		
		for (Map.Entry<String, Brain> snakeEntry : snakeSettingsPanel.getSnakes().entrySet())
		{
			Snake snake = new Snake(objectType, snakeEntry.getKey(), snakeEntry.getValue()); //Här behöver brain klonas.
			session.addSnake(snake);
		}
		
		session.prepareForStart();
		return session;
	}
	
	public int getGameSpeed()
	{
		return gameSettingsPanel.getGameSpeed();
	}
	
	public int getPixelsPerUnit()
	{
		return gameSettingsPanel.getPixelsPerUnit();
	}
	
}
