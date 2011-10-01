package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;

public class SettingsWindow extends JFrame
{
	private boolean done = false;
	private JTabbedPane tabbedPane;	
	private SnakeSettingsPanel snakeSettingsPanel;
	private GameSettingsPanel gameSettingsPanel;
	
	
	private JButton startButton;
	
	public SettingsWindow()
	{
		setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
		
		snakeSettingsPanel = new SnakeSettingsPanel();
		gameSettingsPanel = new GameSettingsPanel();
		
		tabbedPane.addTab("Snayks", snakeSettingsPanel);
		tabbedPane.addTab("Game settings", gameSettingsPanel);
		
		startButton = new JButton("Start");
		startButton.addActionListener(new StartButtonListener());
		
		add(tabbedPane, BorderLayout.CENTER);
		add(startButton, BorderLayout.SOUTH);
		
		setSize(600, 400);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		setVisible(true);
		
	}
	
	public void putThisDamnWindowInMyFace()
	{
		done = false;
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
		
		for (Snake snake : snakeSettingsPanel.getSnakeList())
		{
			session.addSnake(snake);
		}
		
		session.prepareForStart();
		return session;
	}
	
	
}
