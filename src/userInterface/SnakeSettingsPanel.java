package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;
import javax.swing.filechooser.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.net.*;

class SnakeSettingsPanel extends JPanel
{
	private JList snakeJList;
	private SnakeManagementPanel snakeManagementPanel;
	private SnakeInfoPanel snakeInfoPanel;
	private Map<String, Brain> brains = new HashMap<String, Brain>();
	private List<String> snakes = new ArrayList<String>();
	private BotClassLoader classLoader;
	
	public SnakeSettingsPanel()
	{
		setLayout(new BorderLayout());
				
		snakeJList = new JList();
		snakeJList.addListSelectionListener(new SnakeListSelectionListener());
		
		snakeManagementPanel = new SnakeManagementPanel();
		
		snakeInfoPanel = new SnakeInfoPanel();
		
		
		add(new JScrollPane(snakeJList), BorderLayout.CENTER);
		add(snakeManagementPanel, BorderLayout.NORTH);
		add(snakeInfoPanel, BorderLayout.EAST);
		
		
		ClassLoader parentClassLoader = MainWindow.class.getClassLoader();
		classLoader = new BotClassLoader(parentClassLoader);
	}
	
	
	private class SnakeListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
		}
	}
	
	
	private class SnakeManagementPanel extends JPanel
	{
		private JFileChooser fileChooser;
		
		private JButton addSnakeButton;
		private JButton removeSnakeButton;
		
		public SnakeManagementPanel()
		{
			fileChooser = new JFileChooser("./bot");
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Java class files", "class");
			fileChooser.setFileFilter(filter);
			
			addSnakeButton = new JButton("Add snake");
			addSnakeButton.addActionListener(new AddSnakeListener());
			
			removeSnakeButton = new JButton("Remove snake");
			
			add(addSnakeButton);
			add(removeSnakeButton);
		}
		
		private class AddSnakeListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				int returnValue = fileChooser.showOpenDialog(SnakeSettingsPanel.this);
				
				if (returnValue != JFileChooser.APPROVE_OPTION)
					return;
				
				URL url;
				try
				{
					url = fileChooser.getSelectedFile().toURI().toURL();
				}
				catch (MalformedURLException e)
				{
					System.out.println(e);
					return;
				}
				
				String name = fileChooser.getSelectedFile().getName();
					
				name = name.substring(0, name.lastIndexOf("."));
				
				Brain brain = brains.get(name);
				
				if (brain == null)
				{
					try
					{
						brain = classLoader.loadBrain(url, name);
						
						brains.put(name, brain);
					}
					catch (RuntimeException e)
					{
						JOptionPane.showMessageDialog(SnakeSettingsPanel.this, e.toString());
						return;
					}
				}
				
				snakes.add(name);
				snakeJList.setListData(snakes.toArray());
			}
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
	
	
	
	
	public Map<String, Brain> getBrains()
	{
		return brains;
	}
	
	public List<String> getSnakes()
	{
		return snakes;
	}
}
