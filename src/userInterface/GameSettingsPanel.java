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
	private JTextField gameSpeed;
	
	public GameSettingsPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		lolText = new JLabel("GAME SETTINGS");
				
		add(lolText);
		
		initializeBoardSizeSettings();
		
		JPanel gameSpeedPanel = new JPanel();
		gameSpeedPanel.add(new JLabel("Game speed:"));
		gameSpeed = new JTextField("300");
		gameSpeedPanel.add(gameSpeed);
		add(gameSpeedPanel);
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
		int width = Integer.parseInt(boardWidth.getText()) + 2;
		int height = Integer.parseInt(boardHeight.getText()) + 2;
		int growthFrequency = 3;
		int fruitFrequency = 10;
		int thinkingTime = 100;
		int fruitGoal = 5;
		
		Metadata metadata = new Metadata(width, height, growthFrequency, fruitFrequency, thinkingTime, fruitGoal);
		return metadata;
	}
	
	public int getGameSpeed()
	{
		return Integer.parseInt(gameSpeed.getText());
	}
	
}
