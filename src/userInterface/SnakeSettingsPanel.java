package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;
import javax.swing.filechooser.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

class SnakeSettingsPanel extends JPanel
{
	private JList snakeJList;
	private JList brainJList;
	private SnakeManagementPanel snakeManagementPanel;
	private Map<String, Brain> snakes = new TreeMap<String, Brain>();
	private Map<String, Class<? extends Brain>> brains = new TreeMap<String, Class<? extends Brain>>();
	private BotClassLoader classLoader;
	
	public SnakeSettingsPanel()
	{
		setLayout(new BorderLayout());
				
		snakeJList = new JList();		
		brainJList = new JList();
		
		snakeManagementPanel = new SnakeManagementPanel();		
		
		JPanel centerPanel = new JPanel();
		centerPanel.add(new JScrollPane(snakeJList));
		centerPanel.add(new JScrollPane(brainJList));
		
		add(centerPanel, BorderLayout.CENTER);
		add(snakeManagementPanel, BorderLayout.NORTH);
		
		
		ClassLoader parentClassLoader = MainWindow.class.getClassLoader();
		classLoader = new BotClassLoader(parentClassLoader);
		
		loadBrains();
	}
	
	private void loadBrains()
	{
		FilenameFilter filter = new ClassfileFilter();
		File botFolder = new File("./bot");
		File[] listOfFiles = botFolder.listFiles(filter);
		
		for (File file : listOfFiles)
		{
			if (file.isDirectory())
				continue;
			
			String name = file.getName();
			name = name.substring(0, name.lastIndexOf("."));
			
			Class<?> c;
			try
			{
				c = classLoader.getClass(name);
			}
			catch (RuntimeException e)
			{
				JOptionPane.showMessageDialog(this, e.toString());
				continue;
			}
			
			Class<? extends Brain> brainClass;
			try
			{
				brainClass = c.asSubclass(Brain.class);
			}
			catch (Exception e)
			{
				continue;
			}
			
			brains.put(name, brainClass);
		}
		
		brainJList.setListData(brains.keySet().toArray());
	}
	
	static private class ClassfileFilter implements FilenameFilter
	{
		public boolean accept(File dir, String name)
		{
			name = name.substring(name.lastIndexOf("."), name.length());
			return name.equalsIgnoreCase(".class");
		}
	}

	
	private class SnakeManagementPanel extends JPanel
	{
		private JButton addSnakeButton;
		private JButton removeSnakeButton;
		
		public SnakeManagementPanel()
		{
			addSnakeButton = new JButton("Add snake");
			addSnakeButton.addActionListener(new AddSnakeListener());
			
			removeSnakeButton = new JButton("Remove snake");
			removeSnakeButton.addActionListener(new RemoveSnakeListener());
			
			add(addSnakeButton);
			add(removeSnakeButton);
		}
		
		private class AddSnakeListener implements ActionListener
		{
			private String generateSnakeName(String name)
			{
				String snakeName = name;
				int numberOfSnakesWithTheSameBrain = 1;
				
				while (snakes.containsKey(snakeName))
				{
					++numberOfSnakesWithTheSameBrain;
					snakeName = name + "#" + numberOfSnakesWithTheSameBrain;
				}
				
				return snakeName;
			}
			
			public void actionPerformed(ActionEvent event)
			{
				Object selectedObject = brainJList.getSelectedValue();
				if (selectedObject == null)
					return;
				
				
				String name = selectedObject.toString();
				
				Brain brain = null;
				try
				{
					brain = classLoader.getBrain(name);
				}
				catch (RuntimeException e)
				{
					JOptionPane.showMessageDialog(SnakeSettingsPanel.this, e.toString());
					return;
				}
				
				snakes.put(generateSnakeName(name), brain);
					
				snakeJList.setListData(snakes.keySet().toArray());
			}
		}
		
		private class RemoveSnakeListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				Object selectedObject = snakeJList.getSelectedValue();
				if (selectedObject == null)
					return;
				
				snakes.remove(selectedObject.toString());
				
				snakeJList.setListData(snakes.keySet().toArray());
			}
		}

	}
	
	
	
	
	public Map<String, Brain> getSnakes()
	{
		return snakes;
	}
	
}
