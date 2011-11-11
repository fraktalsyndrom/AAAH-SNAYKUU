package userInterface;

import javax.swing.*;
import java.awt.*;
import gameLogic.*;

public class MainWindow extends JFrame
{
	private Session session;
	private GameBoard gameBoard;
	private ScoreBoardPanel scoreBoardPanel;
	
	public MainWindow(Session session, int pixelsPerSquare)
	{
		super("SNAYKUU - game");
		this.session = session;
		
		gameBoard = new GameBoard(session, pixelsPerSquare);
		scoreBoardPanel = new ScoreBoardPanel(session);
		scoreBoardPanel.setPreferredSize(scoreBoardPanel.getPreferredSize());
		
		setLayout(new BorderLayout());
		
		add(gameBoard, BorderLayout.CENTER);
		add(scoreBoardPanel, BorderLayout.EAST);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void update()
	{
		scoreBoardPanel.updateScore(session.getGameResult());
		repaint();
	}
	
	
}
