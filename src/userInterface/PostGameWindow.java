package userInterface;

import gameLogic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.List;

public class PostGameWindow extends JFrame
{
	private GameResult finalResult;
	private JPanel standingsPanel;
	private JLabel headerLabel, standingsLabel;
	private JTextArea centerArea;
	private JButton closeButton;
	
	public PostGameWindow(GameResult finalResult)
	{
		this.finalResult = finalResult;
		
		standingsPanel = new JPanel();
		standingsPanel.setLayout(new BorderLayout());
		add(standingsPanel);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(new CloseButtonListener());
		headerLabel = new JLabel();
		centerArea = new JTextArea();
		centerArea.setEditable(false);
		
		standingsPanel.add(headerLabel, BorderLayout.NORTH);
		standingsPanel.add(new JScrollPane(centerArea), BorderLayout.CENTER);
		standingsPanel.add(closeButton, BorderLayout.SOUTH);
		
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		printStandings();
		
		repaint();
	}
	
	
	private void printStandings()
	{
		headerLabel.setText("SNAYKUU RESULTS");
		
		centerArea.setText("SCORES:\n" + finalResult);
	}
	
	private class CloseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
}
