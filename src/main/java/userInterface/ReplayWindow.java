package userInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gameLogic.*;

public class ReplayWindow extends JFrame
{
	private SettingsWindow settingsWindow;
	private RecordedGame recordedGame;
	private GameBoard gameBoard;
	private ScoreBoardPanel scoreBoardPanel;
	private ControlPanel controlPanel;
	private ReplayThread replayThread = new ReplayThread();
	
	public ReplayWindow(SettingsWindow settingsWindow, RecordedGame recordedGame)
	{
		this.settingsWindow = settingsWindow;
		this.recordedGame = recordedGame;
		
		setLayout(new BorderLayout());
		
		gameBoard = new GameBoard(recordedGame, settingsWindow.getPixelsPerUnit());
		controlPanel = new ControlPanel();
		scoreBoardPanel = new ScoreBoardPanel(recordedGame);
				
		add(gameBoard, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		add(scoreBoardPanel, BorderLayout.EAST);
		pack();
		
		addWindowListener(new WindowListener());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		replayThread.start();
	}
	
	private void updateGame()
	{
		scoreBoardPanel.updateScore(recordedGame.getGameResult());
		repaint();
	}
	
	
	private class WindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			replayThread.stopRunning();
			dispose();
		}
	}
	
	
	private class ControlPanel extends JPanel
	{
		private JButton beginButton = new JButton("<<");
		private JButton backOneFrame = new JButton("<");
		private JButton play = new JButton("P");
		private JButton forwardOneFrame = new JButton(">");
		private JButton endButton = new JButton(">>");
		
		public ControlPanel()
		{
			beginButton.addActionListener(new BeginListener());
			backOneFrame.addActionListener(new PreviousFrameListener());
			play.addActionListener(new PlayListener());
			forwardOneFrame.addActionListener(new NextFrameListener());
			endButton.addActionListener(new EndListener());
			
			add(beginButton);
			add(backOneFrame);
			add(play);
			add(forwardOneFrame);
			add(endButton);
		}
		
		private class BeginListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				recordedGame.setCurrentReplayFrame(0);
				updateGame();
			}
		}
		
		private class PreviousFrameListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				recordedGame.setCurrentReplayFrame(recordedGame.getCurrentReplayFrame() - 1);
				updateGame();
			}
		}
		
		private class PlayListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				replayThread.togglePause();
			}
		}
		
		private class NextFrameListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				recordedGame.setCurrentReplayFrame(recordedGame.getCurrentReplayFrame() + 1);
				updateGame();
			}
		}
		
		private class EndListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				recordedGame.setCurrentReplayFrame(recordedGame.getTurnCount());
				updateGame();
			}
		}
	}
	
	
	private class ReplayThread extends Thread
	{
		private boolean running = true;
		private boolean paused = true;
		
		
		public void run()
		{
			while (isRunning())
			{
				int currentFrame = recordedGame.getCurrentReplayFrame() + 1;
				recordedGame.setCurrentReplayFrame(currentFrame);
				updateGame();
				
				if (currentFrame >= recordedGame.getTurnCount() && !isPaused())
					togglePause();
				
				while (isPaused())
					ReplayWindow.sleep(10);
				
				try
				{
					ReplayWindow.sleep(settingsWindow.getGameSpeed());
				}
				catch (Exception e)
				{
					ReplayWindow.sleep(300);
				}
			}
		}
		
		public synchronized void togglePause()
		{
			paused = !paused;
		}
		
		public synchronized boolean isPaused()
		{
			return paused;
		}
		
		public synchronized void stopRunning()
		{
			running = false;
		}
		
		public synchronized boolean isRunning()
		{
			return running;
		}
		
	}
	
	private static void sleep(long ms)
	{
		try
		{
			Thread.currentThread().sleep(ms);
		}
		catch (InterruptedException e)
		{
		}
	}
}
