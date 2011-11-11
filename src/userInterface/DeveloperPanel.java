package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import gameLogic.*;

class DeveloperPanel extends JPanel
{
	private SettingsWindow settingsWindow;
	private JButton statsButton;
	private JTextField numberOfRuns;
	
	DeveloperPanel(SettingsWindow settingsWindow)
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.settingsWindow = settingsWindow;
		
		add(new JLabel("Number of runs:"));
		
		numberOfRuns = new JTextField("2");
		numberOfRuns.setPreferredSize(new Dimension(200, 50));
		JPanel jp = new JPanel();
		jp.add(numberOfRuns);
		add(jp);
		
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
		
		add(statsButton);
	}
	
	private void playOverNineThousandGames()
	{
		try
		{
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
				System.out.println("Starting game #" + currentGame);
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
					System.out.println("Error: " + e);
				}
			}
			
			for(Map.Entry<String, Results> me : scores.entrySet())
			{
				System.out.println(me.getKey()+" (place: frequency)");
				Results r = me.getValue();
				
				for(int i = 0; i < numSnakes; ++i)
				{
					System.out.println("\t"+(i+1)+": "+r.getFreq(i)+" times");
				}
			}
			
			System.out.println("DONE");
		}
		catch(Exception e)
		{
			System.out.println("Error: " + e);
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


