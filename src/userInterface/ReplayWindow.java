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
	
	public ReplayWindow(RecordedGame recordedGame, int pixelsPerSquare)
	{
		this.recordedGame = recordedGame;
		
		setLayout(new BorderLayout());
		
		gameBoard = new GameBoard(recordedGame, pixelsPerSquare);
		controlPanel = new ControlPanel();
				
		add(gameBoard, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
		pack();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
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
	
	
}
