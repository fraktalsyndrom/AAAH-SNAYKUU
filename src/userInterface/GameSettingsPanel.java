package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;


class GameSettingsPanel extends JPanel
{
	private JLabel lolText;
	private JTextField boardWidth;
	private JTextField boardHeight;
	
	public GameSettingsPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		lolText = new JLabel("GAME SETTINGS");
				
		add(lolText);
		
		initializeBoardSizeSettings();
	}
	
	private void initializeBoardSizeSettings()
	{
		boardWidth = new JTextField("15");
		boardHeight = new JTextField("15");
		
		boardWidth.setColumns(4);
		boardHeight.setColumns(4);
		
		JPanel widthPanel = new JPanel();
		JPanel heightPanel = new JPanel();
		
		widthPanel.add(new JLabel("Board width:"));
		widthPanel.add(boardWidth);
		heightPanel.add(new JLabel("Board height:"));
		heightPanel.add(boardHeight);
				
		add(widthPanel);
		add(heightPanel);
	}
	
	
	public Metadata generateMetadata()
	{
		int width = Integer.parseInt(boardWidth.getText());
		int height = Integer.parseInt(boardHeight.getText());
		int growthFrequency = 3;
		int fruitFrequency = 10;
		int thinkingTime = 100;
		
		Metadata metadata = new Metadata(width, height, growthFrequency, fruitFrequency, thinkingTime);
		return metadata;
	}
	
}
