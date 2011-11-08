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
	private Map<String, String> snakes = new TreeMap<String, String>();
	private Map<String, Class<? extends Brain>> brains = new TreeMap<String, Class<? extends Brain>>();
	
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
		
		
		loadBrains();
	}
	
	private String loadBrains()
	{
		ClassLoader parentClassLoader = MainWindow.class.getClassLoader();
		BotClassLoader classLoader = new BotClassLoader(parentClassLoader);
		
		FilenameFilter filter = new ClassfileFilter();
		File botFolder = new File("./bot");
		File[] listOfFiles = botFolder.listFiles(filter);
		
		String loadedBrains = "";
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
			catch (Throwable e)
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
			
			loadedBrains += name + '\n';
			brains.put(name, brainClass);
		}
		
		brainJList.setListData(brains.keySet().toArray());
		
		return loadedBrains;
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
		private JButton reloadAllBrainsButton;
		
		public SnakeManagementPanel()
		{
			addSnakeButton = new JButton("Add snake");
			addSnakeButton.addActionListener(new AddSnakeListener());
			
			removeSnakeButton = new JButton("Remove snake");
			removeSnakeButton.addActionListener(new RemoveSnakeListener());
			
			reloadAllBrainsButton = new JButton("Reload all brains");
			reloadAllBrainsButton.addActionListener(new ReloadBrainsListener());
			
			snakeJList.addMouseListener(new SnakeMouseListener());
			brainJList.addMouseListener(new BrainMouseListener());
			
			add(addSnakeButton);
			add(removeSnakeButton);
			add(reloadAllBrainsButton);
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
				
				snakes.put(generateSnakeName(name), name);
					
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
		
		private class ReloadBrainsListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				String reloadedBrains = loadBrains();
				JOptionPane.showMessageDialog(SnakeSettingsPanel.this, "Successfully reloaded:\n" + reloadedBrains);
			}
		}
		
		private class SnakeMouseListener extends MouseAdapter
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() % 2 == 0)
				{
					new RemoveSnakeListener().actionPerformed(null);
				}
			}
		}
		
		private class BrainMouseListener extends MouseAdapter
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() % 2 == 0)
				{
					new AddSnakeListener().actionPerformed(null);
				}
			}
		}

	}
	
	
	
	
	public Map<String, Brain> getSnakes() throws Exception
	{
		Map<String, Brain> snakeMap = new TreeMap<String, Brain>();
		for (Map.Entry<String, String> snake : snakes.entrySet())
		{
			Brain brain = brains.get(snake.getValue()).newInstance();
			snakeMap.put(snake.getKey(), brain);
		}
		return snakeMap;
	}
	
}
