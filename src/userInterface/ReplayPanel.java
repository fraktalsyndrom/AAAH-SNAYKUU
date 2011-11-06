package userInterface;

import gameLogic.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;

class ReplayPanel extends JPanel
{
	private JButton loadAndPlay = new JButton("Load an old game and play it!");
	public ReplayPanel()
	{
		loadAndPlay.addActionListener(new ReplayListener());
		add(loadAndPlay);
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
				new ReplayWindow(recordedGame, 12);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(getParent(), e);
			}
		}
	}
}
