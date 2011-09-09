package userInterface;

import javax.swing.*;
import java.awt.*;

class GameBoard extends JComponent
{
	public GameBoard(int width, int height)
	{		
		setSize(width, height);
		
		Dimension d = new Dimension(width, height);
		setMaximumSize(d);
		setMinimumSize(d);
		setPreferredSize(d);
	}
}
