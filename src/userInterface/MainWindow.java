package userInterface;

import javax.swing.*;
import gameLogic.*;

public class MainWindow extends JFrame
{
	private Session session;
	private GameBoard gameBoard;
	
	public MainWindow(Session session, int pixelsPerSquare)
	{
		this.session = session;
		
		gameBoard = new GameBoard(session, pixelsPerSquare);
				
		add(gameBoard);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	
}
