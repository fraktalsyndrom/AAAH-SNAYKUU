package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;


class SnakeSettingsPanel extends JPanel
{
	private JList snakeList;
	private SnakeManagementPanel snakeManagementPanel;
	private SnakeInfoPanel snakeInfoPanel;
	
	public SnakeSettingsPanel()
	{
		setLayout(new BorderLayout());
		
		String[] t = { "snayk1", "snayk2" };
		
		snakeList = new JList(t);
		snakeList.addListSelectionListener(new SnakeListSelectionListener());
		
		snakeManagementPanel = new SnakeManagementPanel();
		
		snakeInfoPanel = new SnakeInfoPanel();
		
		
		
		
		add(snakeList, BorderLayout.CENTER);
		add(snakeManagementPanel, BorderLayout.NORTH);
		add(snakeInfoPanel, BorderLayout.EAST);
		
	}
	
	
	private class SnakeListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
		}
	}
	
	
	private class SnakeManagementPanel extends JPanel
	{
		private JButton addSnakeButton;
		private JButton removeSnakeButton;
		
		public SnakeManagementPanel()
		{
			addSnakeButton = new JButton("Add snake");
			removeSnakeButton = new JButton("Remove snake");
			
			add(addSnakeButton);
			add(removeSnakeButton);
		}
	}
	
	
	private class SnakeInfoPanel extends JPanel
	{
		private JLabel snakeName;
		
		public SnakeInfoPanel()
		{
			snakeName = new JLabel("SNEjK");
			add(snakeName);
		}
	}
	
}
