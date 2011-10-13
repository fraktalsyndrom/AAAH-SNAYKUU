package userInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import gameLogic.*;
import javax.swing.filechooser.*;
import java.util.Map;
import java.util.TreeMap;
import java.net.*;

class SnakeSettingsPanel extends JPanel
{
	private JList snakeJList;
	private SnakeManagementPanel snakeManagementPanel;
	private SnakeInfoPanel snakeInfoPanel;
	private Map<String, Brain> brains = new TreeMap<String, Brain>();
	
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
				
				Brain brain = loadBrain(url, name);
				if (brain == null)
					return;
				
				brains.put(name, brain);
				
				snakeJList.setListData(brains.keySet().toArray());
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
	
	
	
	private Brain loadBrain(URL url, String name)
	{
		ClassLoader parentClassLoader = MainWindow.class.getClassLoader();
	
		BotClassLoader classLoader = new BotClassLoader(parentClassLoader);
		
		Class<?> brainClass;
		try
		{
			brainClass = classLoader.loadBotClass(url, name);
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(this, "Couldn't find class " + name + ": " + e);
			return null;
		}
		
		Object object;
		try
		{
			object = brainClass.newInstance();
		}
		catch (InstantiationException e)
		{
			JOptionPane.showMessageDialog(this, "Couldn't instantiate class " + name + ": " + e);
			return null;
		}
		catch (IllegalAccessException e)
		{
			JOptionPane.showMessageDialog(this, "Couldn't access class " + name + ": " + e);
			return null;
		}
		
		Brain brain = (Brain)object;
		
		return brain;
	}
	
	
	public Map<String, Brain> getBrains()
	{
		return brains;
	}
}
