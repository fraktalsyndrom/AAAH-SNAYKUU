package userInterface;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.net.URL;
import gameLogic.Direction;
import gameLogic.Position;

enum SnakeSegment
{
	HEAD("snake_head.bmp"),
	TAIL("snake_tail.bmp"),
	MIDDLE("snake_body.bmp");
	
	private Image image;
	
	SnakeSegment(String s)
	{
		try
		{
			URL temp = getClass().getResource("/img/"+s);
			image = ImageIO.read(temp);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
	
	Image getImage()
	{
		return image;
		
	}
	
	AffineTransform getTransformation(Direction dir, Position pos, int pixelsPerUnit)
	{
		AffineTransform at = new AffineTransform();
		
		System.out.println(at);
		
		at.scale(pixelsPerUnit, pixelsPerUnit);
		at.translate(pos.getX()*pixelsPerUnit, pos.getY()*pixelsPerUnit);
		
		
		Position rotationVector = dir.getDirectionVector();
		at.rotate(rotationVector.getX(), rotationVector.getY());
		/*
		switch(dir)
		{
			case SOUTH:
				at.translate(1, 0);
				break;
				
			case WEST:
				at.translate(1, 1);
				break;
				
			case NORTH:
				at.translate(0, 1);
				
			default:
				break;
		}
		*/
		
		return at;
		
	}
}
