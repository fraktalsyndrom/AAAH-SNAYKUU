package userInterface;

import java.awt.*;
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
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private class ControlPanel extends JPanel
	{
		private JButton backOneFrame = new JButton("<");
		private JButton play = new JButton("P");
		private JButton forwardOneFrame = new JButton(">");
		
		public ControlPanel()
		{
			add(backOneFrame);
			add(play);
			add(forwardOneFrame);
		}
	}
	
	
}
