package userInterface;

import javax.swing.*;
import gameLogic.*;

public class MainWindow extends JFrame
{
	private Session session;
	private GameBoard gameBoard;
	private GameLoop gameLoop;
	
	public MainWindow()
	{
		session = new Session(20, 15, 1, 33);
		
		gameBoard = new GameBoard(session, 16);
				
		add(gameBoard);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
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
					Thread.currentThread().sleep(200);
				}
				catch (InterruptedException e)
				{
					break;
				}
				
			}
		}
	}
	
}
