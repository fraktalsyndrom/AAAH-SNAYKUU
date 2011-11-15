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
	private JTextField pixelsPerUnit;
	private JTextField fruitFrequency;
	private JTextField growthFrequency;
	private JTextField fruitGoal;
	private JTextField thinkingTime;
	private JTextField gameSpeed;
	
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
		pixelsPerUnit = new JTextField("25");
		fruitGoal = new JTextField("5");
		fruitFrequency = new JTextField("10");
		growthFrequency = new JTextField("5");
		thinkingTime = new JTextField("100");
		gameSpeed = new JTextField("300");
		
		
		boardWidth.setColumns(4);
		boardHeight.setColumns(4);
		pixelsPerUnit.setColumns(4);
		fruitGoal.setColumns(4);
		fruitFrequency.setColumns(4);
		growthFrequency.setColumns(4);
		thinkingTime.setColumns(4);
		gameSpeed.setColumns(4);
		
		JPanel widthPanel = new JPanel();
		JPanel heightPanel = new JPanel();
		JPanel pixelsPerUnitPanel = new JPanel();
		
		JPanel goalPanel = new JPanel();
		JPanel freqPanel = new JPanel();
		JPanel growthPanel = new JPanel();
		JPanel thinkingPanel = new JPanel();
		JPanel gameSpeedPanel = new JPanel();
		
		widthPanel.add(new JLabel("Board width:"));
		widthPanel.add(boardWidth);
		
		heightPanel.add(new JLabel("Board height:"));
		heightPanel.add(boardHeight);
		
		pixelsPerUnitPanel.add(new JLabel("Pixels per square:"));
		pixelsPerUnitPanel.add(pixelsPerUnit);
		
		goalPanel.add(new JLabel("Fruit goal"));
		goalPanel.add(fruitGoal);
		
		freqPanel.add(new JLabel("Fruit freq."));
		freqPanel.add(fruitFrequency);
		
		growthPanel.add(new JLabel("Growth freq."));
		growthPanel.add(growthFrequency);
		
		thinkingPanel.add(new JLabel("Thinking time (ms/frame):"));
		thinkingPanel.add(thinkingTime);
		
		gameSpeedPanel.add(new JLabel("Game speed (ms/frame):"));
		gameSpeedPanel.add(gameSpeed);
		
		
		add(widthPanel);
		add(heightPanel);
		add(pixelsPerUnitPanel);
		add(goalPanel);
		add(freqPanel);
		add(growthPanel);
		add(thinkingPanel);
		add(gameSpeedPanel);
	}
	
	
	public Metadata generateMetadata()
	{
		int width = Integer.parseInt(boardWidth.getText()) + 2;
		int height = Integer.parseInt(boardHeight.getText()) + 2;
		int growth = Integer.parseInt(growthFrequency.getText());
		int fruitFreq = Integer.parseInt(fruitFrequency.getText());
		int thinkingT = Integer.parseInt(thinkingTime.getText());
		int fruitG = Integer.parseInt(fruitGoal.getText());
		
		Metadata metadata = new Metadata(width, height, growth, fruitFreq, thinkingT, fruitG);
		return metadata;
	}
	
	public int getGameSpeed()
	{
		return Integer.parseInt(gameSpeed.getText());
	}
	
	public int getPixelsPerUnit()
	{
		return Integer.parseInt(pixelsPerUnit.getText());
	}
	
}
