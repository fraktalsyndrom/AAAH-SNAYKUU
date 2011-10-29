package userInterface;

import gameLogic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.List;

public class PostGameWindow extends JFrame
{
	private GameResult finalResult;
	private JPanel standingsPanel;
	private JLabel headerLabel, standingsLabel;
	private JTextArea centerArea;
	private JButton newGameButton;
	private JButton rematchButton;
	private JButton exitButton;
	private GameEndType gameEndType = null;
	
	public PostGameWindow(GameResult finalResult)
	{
		this.finalResult = finalResult;
		
		standingsPanel = new JPanel();
		standingsPanel.setLayout(new BorderLayout());
		add(standingsPanel);
		
		newGameButton = new JButton("New game");
		newGameButton.addActionListener(new NewGameButtonListener());
		
		rematchButton = new JButton("Rematch");
		rematchButton.addActionListener(new RematchButtonListener());
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new CloseButtonListener());
		
		headerLabel = new JLabel();
		centerArea = new JTextArea();
		centerArea.setEditable(false);
		
		standingsPanel.add(headerLabel, BorderLayout.NORTH);
		standingsPanel.add(new JScrollPane(centerArea), BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newGameButton);
		buttonPanel.add(rematchButton);
		buttonPanel.add(exitButton);
		standingsPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		printStandings();
		
		repaint();
	}
	
	
	private void printStandings()
	{
		headerLabel.setText("SNAYKUU RESULTS");
		
		centerArea.setText("SCORES:\n" + finalResult);
	}
	
	static private void sleep(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			System.out.println(e);
		}
	}
	
	public GameEndType getGameEndType()
	{
		while (true)
		{
			synchronized (this)
			{
				if (gameEndType != null)
					break;
			}
			sleep(1);
		}
		
		dispose();
		
		return gameEndType;
	}
	
	private class NewGameButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			synchronized (PostGameWindow.this)
			{
				gameEndType = GameEndType.NEW_GAME;
			}
		}
	}
	
	private class RematchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			synchronized (PostGameWindow.this)
			{
				gameEndType = GameEndType.REMATCH;
			}
		}
	}
	
	private class CloseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			synchronized (PostGameWindow.this)
			{
				gameEndType = GameEndType.EXIT;
			}
		}
	}
}
