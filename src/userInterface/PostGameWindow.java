package userInterface;

import gameLogic.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.List;
import java.io.File;

public class PostGameWindow extends JFrame
{
	private GameResult finalResult;
	private JLabel headerLabel, standingsLabel;
	private ScoreBoardPanel scoreBoardPanel;
	private JButton newGameButton;
	private JButton rematchButton;
	private JButton saveReplayButton;
	private JButton exitButton;
	private GameEndType gameEndType = null;
	
	public PostGameWindow(Session session)
	{
		super("SNAYKUU - results");
		this.finalResult = session.getGameResult();
		
		newGameButton = new JButton("New game");
		newGameButton.addActionListener(new NewGameButtonListener());
		
		rematchButton = new JButton("Rematch");
		rematchButton.addActionListener(new RematchButtonListener());
		
		saveReplayButton = new JButton("Save replay");
		saveReplayButton.addActionListener(new SaveReplayButtonListener());
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new CloseButtonListener());
		
		scoreBoardPanel = new ScoreBoardPanel(session);
		scoreBoardPanel.setPreferredSize(scoreBoardPanel.getPreferredSize());
		add(scoreBoardPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newGameButton);
		buttonPanel.add(rematchButton);
		buttonPanel.add(saveReplayButton);
		buttonPanel.add(exitButton);
		buttonPanel.setPreferredSize(buttonPanel.getPreferredSize());
		add(buttonPanel, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		printStandings();
		
		repaint();
	}
	
	
	private void printStandings()
	{
		scoreBoardPanel.updateScore(finalResult);
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
	
	private class SaveReplayButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser fileChooser = new JFileChooser("./replays");
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Snaykuu Replay (.srp)", "srp");
			fileChooser.setFileFilter(filter);
			
			int returnValue = fileChooser.showSaveDialog(PostGameWindow.this);
				
			if (returnValue != JFileChooser.APPROVE_OPTION)
				return;
			
			File file = fileChooser.getSelectedFile();
			
			if(!file.getName().endsWith(".srp"))
			{
				file = new File(file.getParent(), file.getName()+".srp");
			}
			
			try
			{
				finalResult.getRecordedGame().saveToFile(file);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(PostGameWindow.this, e);
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
