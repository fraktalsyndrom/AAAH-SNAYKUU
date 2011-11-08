package userInterface;

import gameLogic.*;
import javax.swing.*;
import java.awt.*;

public class ScoreBoardPanel extends JPanel
{
	private JTextArea centerArea;
	
	public ScoreBoardPanel()
	{
		setLayout(new BorderLayout());
		centerArea = new JTextArea();
		centerArea.setEditable(false);
		
		add(new JScrollPane(centerArea));
	}
	
	public void updateScore(GameResult gameResult)
	{
		centerArea.setText("SCORES:\n" + gameResult);
	}
}
