package userInterface;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.awt.geom.AffineTransform;
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
		int height = pixelsPerUnit * board.getHeight();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		
		for (int x = 1; x < board.getWidth(); ++x)
		{
			g.drawLine(x * pixelsPerUnit, 0, x * pixelsPerUnit, height);
		}
		
		for (int y = 1; y < board.getHeight(); ++y)
		{
			g.drawLine(0, y * pixelsPerUnit, width, y * pixelsPerUnit);
		}
		
		g.setColor(Color.GREEN);
		
		for (int x = 0; x < board.getWidth(); ++x)
		{
			for (int y = 0; y < board.getHeight(); ++y)
			{
				Position pos = new Position(x, y);
				if (board.hasGameObject(pos))
				{
					if (board.hasWall(pos))
						g.setColor(Color.BLACK);
					else if (board.hasSnake(pos)) {
						//~ Snake snake = (Snake)board.getSquare(pos).getGameObject();
						//~ if (snake.isDead())
							//~ g.setColor(Color.GRAY);
						//~ else
							g.setColor(Color.WHITE);
					}
					else if (board.hasFruit(pos))
						g.setColor(Color.RED);
					int xPosition = x * pixelsPerUnit;
					int yPosition = y * pixelsPerUnit;
					g.fillRect(xPosition, yPosition, pixelsPerUnit, pixelsPerUnit);
				}
			}
		}
		
		
		Graphics2D g2d = (Graphics2D)g;
		
		GameState gs = session.getCurrentState();
		
		for(Snake s : gs.getSnakes())
		{
			Direction prevDir = null;
			for(Iterator<Position> iter = s.getSegments().iterator(); iter.hasNext(); ) 
			{
				Position pos = iter.next();
				Direction dir = s.getDirection(pos);
				Direction useDir = dir;
				
				GraphicsTile segment;
				
				if(prevDir == null) //första elementet
				{
					segment = GraphicsTile.SNAKEHEAD;
				}
				else if(!iter.hasNext()) //sista elementet
				{
					segment = GraphicsTile.SNAKETAIL;
					useDir = prevDir;
				} 
				else
				{
					//TODO: turns, player colors
					segment = GraphicsTile.SNAKEBODY;
				}
				
				AffineTransform transform = segment.getTransformation(useDir, pos, pixelsPerUnit);
				
				g2d.drawImage(segment.getImage(), transform, null);
				
				prevDir = dir;
			}
		}
		
		GraphicsTile t = GraphicsTile.SNAKEHEAD;
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.WEST, new Position(1,1), pixelsPerUnit), null);
		
		t = GraphicsTile.SNAKEBODY;
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.WEST, new Position(2,1), pixelsPerUnit), null);
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.WEST, new Position(3,1), pixelsPerUnit), null);
		
		t = GraphicsTile.SNAKETAIL;
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.WEST, new Position(4,1), pixelsPerUnit), null);
		
		/*
		for(Position fruit : gs.getFruits())
		{
			~GraphicsTile.FRUIT är ännu ej implementerad i väntan på grafik.
			
			GraphicsTile icon = GraphicsTile.FRUIT;
			
			g2d.drawImage(icon.getImage(), icon.getTransformation(Direction.EAST, fruit, pixelsPerUnit), null);
			
		}
		
		// Här vill man kanske rita ut väggar med egen grafik.
		*/
	}
}
