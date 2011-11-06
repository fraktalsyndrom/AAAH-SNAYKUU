package userInterface;

import java.awt.event.*;
import javax.swing.*;

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
			// new ReplayWindow();
		}
	}
}
