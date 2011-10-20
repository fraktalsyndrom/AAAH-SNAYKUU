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
	private JTextField fruitFrequency;
	private JTextField growthFrequency;
	private JTextField fruitGoal;
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
		fruitGoal = new JTextField("5");
		fruitFrequency = new JTextField("10");
		growthFrequency = new JTextField("3");
		
		boardWidth.setColumns(4);
		boardHeight.setColumns(4);
		fruitGoal.setColumns(4);
		fruitFrequency.setColumns(4);
		growthFrequency.setColumns(4);
		
		JPanel widthPanel = new JPanel();
		JPanel heightPanel = new JPanel();
		
		JPanel goalPanel = new JPanel();
		JPanel freqPanel = new JPanel();
		JPanel growthPanel = new JPanel();
		
		widthPanel.add(new JLabel("Board width:"));
		widthPanel.add(boardWidth);
		
		goalPanel.add(new JLabel("Fruit goal"));
		goalPanel.add(fruitGoal);
		
		freqPanel.add(new JLabel("Fruit freq."));
		freqPanel.add(fruitFrequency);
		
		growthPanel.add(new JLabel("Growth freq."));
		growthPanel.add(growthFrequency);
		
		heightPanel.add(new JLabel("Board height:"));
		heightPanel.add(boardHeight);
				
		add(widthPanel);
		add(heightPanel);
		add(goalPanel);
		add(freqPanel);
		add(growthPanel);
	}
	
	
	public Metadata generateMetadata()
	{
		int width = Integer.parseInt(boardWidth.getText()) + 2;
		int height = Integer.parseInt(boardHeight.getText()) + 2;
		int growth = Integer.parseInt(growthFrequency.getText());
		int fruitFreq = Integer.parseInt(fruitFrequency.getText());
		int thinkingTime = 100;
		int fruitG = Integer.parseInt(fruitGoal.getText());
		
		Metadata metadata = new Metadata(width, height, growth, fruitFreq, thinkingTime, fruitG);
		return metadata;
	}
	
	public int getGameSpeed()
	{
		return Integer.parseInt(gameSpeed.getText());
	}
	
}
