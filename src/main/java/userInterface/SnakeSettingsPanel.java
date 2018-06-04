package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import gameLogic.Brain;
import org.reflections.Reflections;
import static java.awt.GridBagConstraints.*;

class SnakeSettingsPanel extends JPanel
{
	private JList snakeJList;
	private JList brainJList;
	private JButton addSnakeButton;
	private JButton removeSnakeButton;
	private JButton reloadAllBrainsButton;
	private Map<String, String> snakes = new TreeMap<String, String>();
	private Map<String, Class<? extends Brain>> brains = new TreeMap<String, Class<? extends Brain>>();
	
	public SnakeSettingsPanel()
	{
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		
		GridBagConstraints constraint = new GridBagConstraints();
		
		constraint.fill = HORIZONTAL;
		constraint.insets = new Insets(4, 4, 4, 4);
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		
			JLabel selected = new JLabel("Available snakes:");
			selected.setHorizontalAlignment(JLabel.CENTER);
			gridbag.setConstraints(selected, constraint);
			add(selected);
		
		constraint.fill = HORIZONTAL;
		constraint.insets = new Insets(4, 4, 4, 4);
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		constraint.gridx = 2;
		constraint.gridy = 0;
		
			JLabel available = new JLabel("Snakes in game:");
			available.setHorizontalAlignment(JLabel.CENTER);
			gridbag.setConstraints(available, constraint);
			add(available);
		
		constraint.fill = BOTH;
		constraint.gridwidth = 1;
		constraint.gridheight = 8;
		constraint.weightx = 0.5;
		constraint.weighty = 0.5;
		constraint.gridx = 0;
		constraint.gridy = 1;
		
			brainJList = new JList();
			brainJList.addMouseListener(new BrainMouseListener());
			
			JScrollPane jsp2 = new JScrollPane(brainJList);
			jsp2.setPreferredSize(jsp2.getPreferredSize());
			gridbag.setConstraints(jsp2, constraint);
			add(jsp2);
		
		constraint.fill = BOTH;
		constraint.gridwidth = 1;
		constraint.gridheight = 8;
		constraint.weightx = 0.5;
		constraint.weighty = 0.5;
		constraint.gridx = 2;
		constraint.gridy = 1;
		
			snakeJList = new JList();
			snakeJList.addMouseListener(new SnakeMouseListener());
			
			JScrollPane jsp1 = new JScrollPane(snakeJList);
			jsp1.setPreferredSize(jsp1.getPreferredSize());
			gridbag.setConstraints(jsp1, constraint);
			add(jsp1);
		
		constraint.fill = NONE;
		constraint.weightx = 0.1;
		constraint.weighty = 0.1;
		constraint.insets = new Insets(4, 4, 4, 4);
		constraint.gridheight = 4;
		constraint.gridx = 1;
		constraint.anchor = SOUTH;
		constraint.gridy = 0;
		
			addSnakeButton = new JButton("=>");
			addSnakeButton.addActionListener(new AddSnakeListener());
			gridbag.setConstraints(addSnakeButton, constraint);
			add(addSnakeButton);
		
		constraint.fill = NONE;
		constraint.weightx = 0.1;
		constraint.weighty = 0.1;
		constraint.insets = new Insets(4, 4, 4, 4);
		constraint.gridheight = 4;
		constraint.gridx = 1;
		constraint.anchor = NORTH;
		constraint.gridy = 4;
		
			removeSnakeButton = new JButton("<=");
			removeSnakeButton.addActionListener(new RemoveSnakeListener());
			gridbag.setConstraints(removeSnakeButton, constraint);
			add(removeSnakeButton);
		
		constraint.fill = NONE;
		constraint.weightx = 0.1;
		constraint.weighty = 0.1;
		constraint.insets = new Insets(4, 4, 4, 4);
		constraint.anchor = SOUTH;
		constraint.gridx = 0;
		constraint.gridy = 9;
		constraint.gridwidth = 3;
		constraint.gridheight = 1;
		constraint.weighty = 0.0;
		
			reloadAllBrainsButton = new JButton("Reload all brains");
			reloadAllBrainsButton.addActionListener(new ReloadBrainsListener());
			gridbag.setConstraints(reloadAllBrainsButton, constraint);
			add(reloadAllBrainsButton);
		
		loadBrains();
	}
	
	private String loadBrains()
	{
		Reflections reflections = new Reflections("bot");
		Set<Class<? extends Brain>> classes = reflections.getSubTypesOf(Brain.class);
		String loadedBrains = classes.stream()
				.map(Class::getSimpleName)
				.collect(Collectors.joining(", "));
		brains.putAll(classes.stream().collect(Collectors.toMap(Class::getSimpleName, it -> it)));
		brainJList.setListData(brains.keySet().toArray());
		return loadedBrains;
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
