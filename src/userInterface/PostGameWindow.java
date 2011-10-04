package userInterface;

import gameLogic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.ArrayList;

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
		
		setSize(200, 300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		printStandings();
		
		repaint();
	}
	
	private void printStandings()
	{
		Map<Snake, Integer> results = finalResult.calculateFinalScore();
		ArrayList<Snake> winners = finalResult.getWinnersFrom(results);
		headerLabel.setText("SNAYKUU RESULTS");
		centerArea.setText("Winner(s):\n");
		for (Snake winnerSnake : winners)
		{
			centerArea.append("\t" + winnerSnake + "\n");
		}
		centerArea.append("\nResults:\n");
		for (Map.Entry<Snake, Integer> resultEntry : results.entrySet())
		{
			Snake snake = resultEntry.getKey();
			int score = resultEntry.getValue();
			centerArea.append("\t" + snake + "\t" + score + "\n");
		}
	}
	
	private class CloseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
}