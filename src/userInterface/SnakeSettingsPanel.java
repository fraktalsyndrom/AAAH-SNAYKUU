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
	private Map<String, Brain> snakes = new HashMap<String, Brain>();
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
				
				Brain brain = null;
				try
				{
					brain = classLoader.getBrain(url, name);
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
	
	
	
	
	public Map<String, Brain> getSnakes()
	{
		return snakes;
	}
	
}
