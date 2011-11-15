package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.*;

import gameLogic.*;

class DeveloperPanel extends JPanel
{
	private SettingsWindow settingsWindow;
	private JButton statsButton;
	private JTextField numberOfRuns;
	private JTextArea output;
	
	DeveloperPanel(SettingsWindow settingsWindow)
	{
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		
		GridBagConstraints constraint = new GridBagConstraints();
		
		this.settingsWindow = settingsWindow;
		
		constraint.fill = NONE;
		constraint.insets = new Insets(4, 4, 4, 4);
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.weightx = 0.0;
		constraint.weighty = 0.0;
		
			JLabel runLabel = new JLabel("Number of runs:");
			gridbag.setConstraints(runLabel, constraint);
			add(runLabel);
		
		constraint.fill = HORIZONTAL;
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		constraint.gridx = 1;
		constraint.gridy = 0;
		constraint.weightx = 1.0;
		constraint.weighty = 0.0;
		
			numberOfRuns = new JTextField("50");
			numberOfRuns.setPreferredSize(numberOfRuns.getPreferredSize());
			gridbag.setConstraints(numberOfRuns, constraint);
			add(numberOfRuns);
		
		constraint.fill = NONE;
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		constraint.gridx = 2;
		constraint.gridy = 0;
		constraint.weightx = 0.0;
		constraint.weighty = 0.0;
		
			statsButton = new JButton("Run test games");
			statsButton.addActionListener
			(
				new ActionListener()
				{ 
					public void actionPerformed(ActionEvent ae) 
					{ 
						playOverNineThousandGames(); 
					} 
				} 
			);
			
			statsButton.setPreferredSize(statsButton.getPreferredSize());
			gridbag.setConstraints(statsButton, constraint);
			add(statsButton);
		
		constraint.fill = NONE;
		constraint.insets = new Insets(4, 4, 4, 4);
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		constraint.gridx = 0;
		constraint.gridy = 1;
		constraint.weightx = 0.0;
		constraint.weighty = 0.0;
		
			JLabel outputLabel = new JLabel("Output:");
			outputLabel.setHorizontalAlignment(JLabel.LEFT);
			gridbag.setConstraints(outputLabel, constraint);
			add(outputLabel);
		
		constraint.fill = BOTH;
		constraint.gridwidth = 3;
		constraint.gridheight = 1;
		constraint.gridx = 0;
		constraint.gridy = 2;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		
			output = new JTextArea();
			output.setEditable(false);
			JScrollPane jsp1 = new JScrollPane(output);
			jsp1.setPreferredSize(jsp1.getPreferredSize());
			gridbag.setConstraints(jsp1, constraint);
			add(jsp1);
	}
	
	private void println(String s)
	{
		output.append(s+"\n");
		output.setCaretPosition(output.getText().length() - 1);
	}
	
	private void print(String s)
	{
		output.append(s);
		output.setCaretPosition(output.getText().length() - 1);
	}
	
	private void clear()
	{
		output.setText("");
		output.setCaretPosition(0);
	}
	
	private void playOverNineThousandGames()
	{
		statsButton.setEnabled(false);
		GameRunner gr = new GameRunner();
		gr.start();
		statsButton.setEnabled(true);
	}
	
	private class GameRunner extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				clear();
				Session session = settingsWindow.generateSession();
				
				HashMap<String, Results> scores = new HashMap<String, Results>();
				int numSnakes = session.getSnakes().size();
				
				for(Snake s : session.getSnakes())
				{
					scores.put(s.getName(), new Results(numSnakes));
				}
				
				final int numberOfGames = Integer.parseInt(numberOfRuns.getText());
				for (int currentGame = 0; currentGame < numberOfGames; ++currentGame)
				{
					println("Starting game #" + currentGame);
					repaint();
					try
					{
						session = settingsWindow.generateSession();
						while (!session.hasEnded())
							session.tick();
						
						List<List<Snake>> result = session.getGameResult().getWinners();
						
						for(int i = 0; i < result.size(); ++i)
						{
							for(Snake s : result.get(i))
							{
								scores.get(s.getName()).addResult(i);
							}
						}
					}
					catch (Exception e)
					{
						println("Error: " + e);
					}
				}
				
				for(Map.Entry<String, Results> me : scores.entrySet())
				{
					println(me.getKey()+" (place: frequency)");
					Results r = me.getValue();
					
					for(int i = 0; i < numSnakes; ++i)
					{
						println("\t"+(i+1)+": "+r.getFreq(i)+" times");
					}
				}
				
				println("DONE");
			}
			catch(Exception e)
			{
				println("Error: " + e);
			}
		}
	}
	
	private class Results
	{
		private int[] placements;
		
		public Results(int numSnakes)
		{
			placements = new int[numSnakes];
			
			for(int i = 0; i < placements.length; ++i)
			{
				placements[i] = 0;
			}
		}
		
		void addResult(int i)
		{
			placements[i] += 1;
		}
		
		int getFreq(int place)
		{
			return placements[place];
		}
	}
}


