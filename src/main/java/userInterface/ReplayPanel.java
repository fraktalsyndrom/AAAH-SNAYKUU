package userInterface;

import gameLogic.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;

class ReplayPanel extends JPanel
{
	private SettingsWindow settingsWindow;
	private JButton loadAndPlay = new JButton("Load an old game and play it!");
	
	public ReplayPanel(SettingsWindow settingsWindow)
	{
		this.settingsWindow = settingsWindow;
		loadAndPlay.addActionListener(new ReplayListener());
		add(loadAndPlay);
	}
	
	private void startReplay(RecordedGame recordedGame)
	{
		try
		{
			new ReplayWindow(settingsWindow, recordedGame);
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(getParent(), "You must enter a valid amount of pixels per square");
		}
	}
	
	private class ReplayListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			JFileChooser fileChooser = new JFileChooser("./replays");
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Snaykuu Replay (.srp)", "srp");
			fileChooser.setFileFilter(filter);
			
			int returnValue = fileChooser.showOpenDialog(getParent());
			if (returnValue != JFileChooser.APPROVE_OPTION)
				return;
			
			File file = fileChooser.getSelectedFile();
			try
			{
				RecordedGame recordedGame = RecordedGame.loadFromFile(file);
				startReplay(recordedGame);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(getParent(), e);
			}
		}
	}
}
