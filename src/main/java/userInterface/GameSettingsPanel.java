package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;

import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.*;

class GameSettingsPanel extends JPanel
{
	private JTextField boardWidth;
	private JTextField boardHeight;
	private JTextField pixelsPerUnit;
	private JTextField fruitFrequency;
	private JTextField growthFrequency;
	private JTextField fruitGoal;
	private JTextField thinkingTime;
	private JTextField gameSpeed;
	
	private GridBagLayout gridbag;
	private GridBagConstraints fieldC;
	private GridBagConstraints labelC;
	
	public GameSettingsPanel()
	{
		gridbag = new GridBagLayout();
		setLayout(gridbag);
		
		
		labelC = new GridBagConstraints();
		
		labelC.fill = HORIZONTAL;
		labelC.insets = new Insets(4, 4, 4, 4);
		labelC.gridwidth = 1;
		labelC.gridheight = 1;
		labelC.gridx = 0;
		labelC.gridy = 0;
		labelC.weightx = 0.0;
		labelC.weighty = 0.0;
		
		
		fieldC = new GridBagConstraints();
		
		fieldC.fill = NONE;
		fieldC.insets = new Insets(4, 4, 4, 4);
		fieldC.gridwidth = 1;
		fieldC.gridheight = 1;
		fieldC.gridx = 1;
		fieldC.gridy = 0;
		fieldC.weightx = 0.0;
		fieldC.weighty = 0.0;
		
		
		boardWidth = addRow("Board width", "15");
		boardHeight = addRow("Board height", "15");
		pixelsPerUnit = addRow("Pixels per square", "25");
		fruitGoal = addRow("Fruits to win", "5");
		fruitFrequency = addRow("Ticks between fruits", "10");
		growthFrequency = addRow("Ticks per unit of snayk growth", "5");
		thinkingTime = addRow("Thinking time (ms/frame)", "100");
		gameSpeed = addRow("Game speed (ms/frame)", "300");
	}
	
	private JTextField addRow(String text, String init) 
	{
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		
		gridbag.setConstraints(label, labelC);
		add(label);
		
		JTextField field = new JTextField(init);
		field.setColumns(4);
		
		gridbag.setConstraints(field, fieldC);
		add(field);
		
		labelC.gridy++;
		fieldC.gridy++;
		
		return field;
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
