package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;


class GameSettingsPanel extends JPanel
{
	private JLabel lolText;
	
	public GameSettingsPanel()
	{
		lolText = new JLabel("GAME SETTINGS");
		
		add(lolText);
	}
}
