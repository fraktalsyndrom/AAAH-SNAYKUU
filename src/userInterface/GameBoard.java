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
	private Game game;
	private int pixelsPerXUnit, pixelsPerYUnit;
	private int graphicsWidth, graphicsHeight;
	private int boardWidth, boardHeight;
	private Color background = Color.WHITE;
	private Color wall = Color.BLACK;
	private Color grid = Color.GRAY;
	
	public GameBoard(Game game, int pixelsPerUnit)
	{
		this.game = game;
		this.pixelsPerXUnit = pixelsPerUnit;
		this.pixelsPerYUnit = pixelsPerUnit;
		this.addComponentListener(new CompLis());
		
		boardWidth  = game.getMetadata().getBoardWidth();
		boardHeight = game.getMetadata().getBoardHeight();
		
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
		
		GameState gs = game.getCurrentState();
		Board board = gs.getBoard();
		
		//Basic preparation.
		g.setColor(background);
		g.fillRect(0, 0, graphicsWidth, graphicsHeight);
		
		g.setColor(grid);
		
		int lineXpos = 0;
		for(int x = 0; x < graphicsWidth; ++x) //Vertical lines
		{
			g.drawLine(lineXpos, 0, lineXpos, graphicsHeight-1);
			lineXpos += (pixelsPerXUnit+1);
		}
		
		int lineYpos = 0;
		for(int y = 0; y < graphicsHeight; ++y) //Horizontal lines
		{
			g.drawLine(0, lineYpos, graphicsWidth-1, lineYpos);
			lineYpos += (pixelsPerYUnit+1);
		}
		
		//Image drawing.
		Graphics2D g2d = (Graphics2D)g;
		
		for(Position wall : gs.getWalls())
		{
			
			GraphicsTile icon = GraphicsTile.WALL;
			
			g2d.drawImage(icon.getImage(), icon.getTransformation(null, wall, pixelsPerXUnit, pixelsPerYUnit), null);
		}
		
		for(Snake s : gs.getSnakes())
		{
			Direction prevDir = null;
			for(Iterator<SnakeSegment> iter = s.getDrawData().iterator(); iter.hasNext(); ) 
			{
				SnakeSegment ss = iter.next();
				Position pos = ss.getPos();
				Direction dir = ss.getDir();
				Direction useDir = dir;
				
				GraphicsTile segment;
				
				if(prevDir == null && !iter.hasNext()) // only element
				{
					segment = GraphicsTile.SNAKEMONAD;
				}
				else if(prevDir == null) //first element
				{
					segment = GraphicsTile.SNAKEHEAD;

					if (s.isDead())
						segment = GraphicsTile.SNAKEDEAD;
				}
				else if(!iter.hasNext()) // last element
				{
					segment = GraphicsTile.SNAKETAIL;
					useDir = prevDir;
				} 
				else if(prevDir != dir)
				{
					if(prevDir.turnLeft() == dir) 
					{
						segment = GraphicsTile.SNAKERIGHT; // Unclear why this works, but it does
					}
					else
					{
						segment = GraphicsTile.SNAKELEFT;
					}
				}
				else
				{
					segment = GraphicsTile.SNAKEBODY;
				}
				
				AffineTransform transform = segment.getTransformation(useDir, pos, pixelsPerXUnit, pixelsPerYUnit);
				
				g2d.drawImage(segment.getImage(s.getColor()), transform, null);
				
				prevDir = dir;
			}
		}
		
		
		//BEGIN DEBUG SNAKE
		// GraphicsTile t = GraphicsTile.SNAKEHEAD;
		// g2d.drawImage(t.getImage(), t.getTransformation(Direction.WEST, new Position(0,0), pixelsPerXUnit, pixelsPerYUnit), null);
		
		// t = GraphicsTile.SNAKEBODY;
		// g2d.drawImage(t.getImage(), t.getTransformation(Direction.NORTH, new Position(1,0), pixelsPerXUnit, pixelsPerYUnit), null);
		// g2d.drawImage(t.getImage(), t.getTransformation(Direction.EAST, new Position(2,0), pixelsPerXUnit, pixelsPerYUnit), null);
		// g2d.drawImage(t.getImage(), t.getTransformation(Direction.EAST, new Position(3,0), pixelsPerXUnit, pixelsPerYUnit), null);
		
		// t = GraphicsTile.SNAKETAIL;
		// g2d.drawImage(t.getImage(), t.getTransformation(Direction.SOUTH, new Position(4,0), pixelsPerXUnit, pixelsPerYUnit), null);
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
			int size = Math.min(getWidth(), getHeight());
			pixelsPerXUnit = (size-1-boardWidth)/boardWidth;
			graphicsWidth = 1+boardWidth+boardWidth*pixelsPerXUnit;
			
			pixelsPerYUnit = (size-1-boardHeight)/boardHeight;
			graphicsHeight = 1+boardHeight+boardHeight*pixelsPerYUnit;
			
			setSize(new Dimension(graphicsWidth, graphicsHeight));
		}
	}
}
