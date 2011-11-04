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
	private int imgHeight;
	private int imgWidth;
	
	SnakeSegment(String s)
	{
		try
		{
			URL temp = getClass().getResource("/img/"+s);
			image = ImageIO.read(temp);
			imgHeight = image.getHeight(null);
			imgWidth = image.getWidth(null);
			
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
		
		//See the java6 API spec for AffineTransform for details on syntax.
		
		float[] flatmatrix = {0, 0, 0, 0, pos.getX(), pos.getY()};
		float[] translationCorrector = {0, 0};
		
		switch(dir)
		{
			case NORTH:
				flatmatrix[1] += -1;
				flatmatrix[2] += 1;
				flatmatrix[5] += 1;
				
				translationCorrector[0] = 1;
				break;
				
			case WEST:
				flatmatrix[0] += -1;
				flatmatrix[3] += -1;
				flatmatrix[4] += 1;
				flatmatrix[5] += 1;
				
				translationCorrector[0] = -1;
				translationCorrector[1] = -1;
				break;
				
			case SOUTH:
				flatmatrix[1] += 1;
				flatmatrix[2] += -1;
				flatmatrix[4] += 1;
				
				translationCorrector[1] = 1;
				break;
				
			default:
				flatmatrix[0] += 1;
				flatmatrix[3] += 1;
				
				translationCorrector[0] = 1;
				translationCorrector[1] = 1;
				break;
		}
		
		flatmatrix[4] *= pixelsPerUnit;
		flatmatrix[5] *= pixelsPerUnit;
		
		flatmatrix[4] += translationCorrector[0];
		flatmatrix[5] += translationCorrector[1];
		
		return new AffineTransform(flatmatrix);
		
	}
}
