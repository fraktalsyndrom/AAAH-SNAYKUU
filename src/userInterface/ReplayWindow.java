package userInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gameLogic.*;

public class ReplayWindow extends JFrame
{
	private RecordedGame recordedGame;
	private GameBoard gameBoard;
	private ControlPanel controlPanel;
	private ReplayThread replayThread = new ReplayThread();
	
	public ReplayWindow(RecordedGame recordedGame, int pixelsPerSquare)
	{
		this.recordedGame = recordedGame;
		
		setLayout(new BorderLayout());
		
		gameBoard = new GameBoard(recordedGame, pixelsPerSquare);
		controlPanel = new ControlPanel();
				
		add(gameBoard, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		pack();
		
		addWindowListener(new WindowListener());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
		replayThread.start();
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
				ReplayWindow.this.repaint();
			}
		}
		
		private class PreviousFrameListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				recordedGame.setCurrentReplayFrame(recordedGame.getCurrentReplayFrame() - 1);
				ReplayWindow.this.repaint();
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
				ReplayWindow.this.repaint();
			}
		}
		
		private class EndListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				recordedGame.setCurrentReplayFrame(recordedGame.getTurnCount());
				ReplayWindow.this.repaint();
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
				repaint();
				
				if (currentFrame >= recordedGame.getTurnCount() && !isPaused())
					togglePause();
				
				while (isPaused())
					ReplayWindow.sleep(10);
				
				ReplayWindow.sleep(300);
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
