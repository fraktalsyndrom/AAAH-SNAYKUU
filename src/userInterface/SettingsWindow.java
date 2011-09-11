package userInterface;

import javax.swing.*;
import java.awt.event.*;
import gameLogic.*;

public class SettingsWindow extends JFrame
{
	private JButton startButton;
	
	public SettingsWindow()
	{		
		startButton = new JButton("Start");
		startButton.addActionListener(new StartButtonListener());
						
		add(startButton);
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		
		
	}
	
	
	private class StartButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			SettingsWindow.this.dispose();
			new MainWindow();
		}
	}
	
}
