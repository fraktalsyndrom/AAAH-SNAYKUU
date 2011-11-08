package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;


class DeveloperPanel extends JPanel
{
	private SettingsWindow settingsWindow;
	private JButton statsButton;
	
	DeveloperPanel(SettingsWindow settingsWindow)
	{
		this.settingsWindow = settingsWindow;
		
		statsButton = new JButton("Fuck everyone in the face, here comes batman");
		statsButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { playOverNineThousandGames(); } } );
		add(statsButton);
	}
	
	private void playOverNineThousandGames()
	{
		final int numberOfGames = 20;
		for (int currentGame = 0; currentGame < numberOfGames; ++currentGame)
		{
			System.out.println("Starting game #" + currentGame);
			try
			{
				Session session = settingsWindow.generateSession();
				while (!session.hasEnded())
					session.tick();
				
				System.out.println(session.getGameResult());
			}
			catch (Exception e)
			{
				System.out.println("Error: " + e);
			}
		}
		
		System.out.println("DONE");
	}
}


