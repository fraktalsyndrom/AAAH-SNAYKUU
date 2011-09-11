package userInterface;

import javax.swing.*;
import java.awt.*;
import gameLogic.*;

class GameBoard extends JComponent
{
	private Session session;
	private int pixelsPerUnit;
	
	public GameBoard(Session session, int pixelsPerUnit)
	{
		this.session = session;
		this.pixelsPerUnit = pixelsPerUnit;
		
		int width = session.getBoard().getWidth() * pixelsPerUnit;
		int height = session.getBoard().getHeight() * pixelsPerUnit;
		
		setSize(width, height);
		
		Dimension d = new Dimension(width, height);
		setMaximumSize(d);
		setMinimumSize(d);
		setPreferredSize(d);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Board board = session.getBoard();
		int width = pixelsPerUnit * board.getWidth();
		int height = pixelsPerUnit * board.getWidth();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		
		for (int x = 1; x < board.getWidth(); ++x)
		{
			g.drawLine(x * pixelsPerUnit, 0, x * pixelsPerUnit, height);
		}
		
		for (int y = 1; y < board.getHeight(); ++y)
		{
			g.drawLine(0, y * pixelsPerUnit, width, y * pixelsPerUnit);
		}
		
		Color[] colorList = { Color.GREEN, Color.RED, Color.BLUE };
		
		int colorIndex = 0;
		for (Snake snake : session.getSnakes())
		{
			g.setColor(colorList[colorIndex++]);
			
			for (SnakeSegment segment : snake.getSegments())
			{
				int x = pixelsPerUnit * segment.getPosition().getX();
				int y = pixelsPerUnit * segment.getPosition().getY();
				g.fillRect(x, y, pixelsPerUnit, pixelsPerUnit);
			}
		}
	}
}
