package userInterface;

import javax.swing.*;
import gameLogic.*;

public class MainWindow extends JFrame
{
	private Session session;
	private GameBoard gameBoard;
	
	public MainWindow(Session session)
	{
		this.session = session;
		
		gameBoard = new GameBoard(session, 16);
				
		add(gameBoard);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		/*Brain brain = loadBrain("Mad");

		
		if (brain == null)
			JOptionPane.showMessageDialog(this, "BRAINFUCK SHIT");
		
		GameObjectType got = new GameObjectType("Snake", true);
		LinkedList<Position> snakePosition = new LinkedList<Position>();
		snakePosition.addFirst(new Position(10, 29));
		snakePosition.addFirst(new Position(10, 30));
		Snake snake = new Snake(got, "Stefan", brain);
		
		session.addSnake(snake);
		session.prepareForStart();*/
		
	}
	
	
}
