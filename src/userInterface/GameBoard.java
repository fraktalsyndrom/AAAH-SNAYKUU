package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Iterator;
import java.awt.geom.AffineTransform;
import gameLogic.*;

class GameBoard extends JComponent
{
	private Session session;
	private int pixelsPerXUnit, pixelsPerYUnit;
	private int graphicsWidth, graphicsHeight;
	private int boardWidth, boardHeight;
	private Color background = Color.WHITE;
	private Color wall = Color.BLACK;
	private Color grid = Color.GRAY;
	
	public GameBoard(Session session, int pixelsPerUnit)
	{
		this.session = session;
		this.pixelsPerXUnit = pixelsPerUnit;
		this.pixelsPerYUnit = pixelsPerUnit;
		this.addComponentListener(new CompLis());
		
		boardWidth = session.getBoard().getWidth();
		boardHeight = session.getBoard().getHeight();
		
		/*
		 * The grid needs space to be in that is not inside a game square.
		 * Hence we add 1 pixel (for the first line), plus the number of squares 
		 * (for each subsequent line), and multiply the number of squares 
		 * by the size of each square.
		 * The result is the total drawing area.
		 */
		graphicsWidth = 1+boardWidth+boardWidth*pixelsPerXUnit;
		graphicsHeight = 1+boardHeight+boardHeight*pixelsPerYUnit;
		
		setSize(graphicsWidth, graphicsHeight);
		
		Dimension d = new Dimension(graphicsWidth, graphicsHeight);
		// setMaximumSize(d);
		// setMinimumSize(d);
		setPreferredSize(d);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		/*
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
		*/
		
		Graphics2D g2d = (Graphics2D)g;
		GameState gs = session.getCurrentState();
		Board board = gs.getBoard();
		
		g.setColor(background);
		g.fillRect(0, 0, graphicsWidth, graphicsHeight);
		
		//Den här utritningen borde inte behöva hårdkodas.
		g.setColor(wall);
		g.fillRect(1, 1, graphicsWidth-2, pixelsPerYUnit);
		g.fillRect(1, 1, pixelsPerXUnit, graphicsHeight-2);
		g.fillRect(graphicsWidth-1, 1, -pixelsPerXUnit, graphicsHeight-2);
		g.fillRect(1, graphicsHeight-1, graphicsWidth-2, -pixelsPerYUnit);
		
		g.setColor(grid);
		
		int lineXpos = 0;
		for(int x = 0; x < graphicsWidth; ++x) //Vertical lines
		{
			g.drawLine(lineXpos, 0, lineXpos, graphicsHeight);
			lineXpos += (pixelsPerXUnit+1);
		}
		
		int lineYpos = 0;
		for(int y = 0; y < graphicsHeight; ++y) //Horizontal lines
		{
			g.drawLine(0, lineYpos, graphicsWidth, lineYpos);
			lineYpos += (pixelsPerYUnit+1);
		}
		
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
				
				AffineTransform transform = segment.getTransformation(useDir, pos, pixelsPerXUnit, pixelsPerYUnit);
				
				g2d.drawImage(segment.getImage(), transform, null);
				
				prevDir = dir;
			}
		}
		
		
		//BEGIN DEBUG SNAKE
		GraphicsTile t = GraphicsTile.SNAKEHEAD;
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.WEST, new Position(0,0), pixelsPerXUnit, pixelsPerYUnit), null);
		
		t = GraphicsTile.SNAKEBODY;
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.NORTH, new Position(1,0), pixelsPerXUnit, pixelsPerYUnit), null);
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.EAST, new Position(2,0), pixelsPerXUnit, pixelsPerYUnit), null);
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.EAST, new Position(3,0), pixelsPerXUnit, pixelsPerYUnit), null);
		
		t = GraphicsTile.SNAKETAIL;
		g2d.drawImage(t.getImage(), t.getTransformation(Direction.SOUTH, new Position(4,0), pixelsPerXUnit, pixelsPerYUnit), null);
		//END DEBUG SNAKE
		
		for(Position fruit : gs.getFruits())
		{
			
			GraphicsTile icon = GraphicsTile.FRUIT;
			
			g2d.drawImage(icon.getImage(), icon.getTransformation(null, fruit, pixelsPerXUnit, pixelsPerYUnit), null);
		}
		
	}
	
	class CompLis extends ComponentAdapter
	{
		@Override
		public void componentResized(ComponentEvent ce)
		{
			pixelsPerXUnit = (getWidth()-1-boardWidth)/boardWidth;
			graphicsWidth = 1+boardWidth+boardWidth*pixelsPerXUnit;
			
			pixelsPerYUnit = (getHeight()-1-boardHeight)/boardHeight;
			graphicsHeight = 1+boardHeight+boardHeight*pixelsPerYUnit;
		}
	}
}
