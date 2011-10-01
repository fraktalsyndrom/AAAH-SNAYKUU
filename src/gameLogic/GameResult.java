package gameLogic;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Comparator;

public class GameResult
{
	private Map<Snake, Integer> scores;
	private Map<Snake, Integer> lifespans;
	private TreeMap<Snake, Integer> finalStandings;
	private int fruitGoal;
	
	public GameResult()
	{
		scores = new HashMap<Snake, Integer>();
		lifespans = new HashMap<Snake, Integer>();
	}
	
	public void setSnakeScores(Snake snake, int score, int lifespan)
	{
		scores.put(snake, score);
		lifespans.put(snake, lifespan);
	}
	
	public Map<Snake, Integer> getScores()
	{
		return scores;
	}
	
	public Map<Snake, Integer> getLifespans()
	{
		return lifespans;
	}
	
	public Map<Snake, Integer> calculateFinalStandings(boolean fruitFinish)
	{
		finalStandings = new TreeMap<Snake, Integer>();
		
		if (fruitFinish)
			finalStandings = calculateFinalFruitStandings();
		else
			finalStandings = calculateFinalLifespanStandings();
		
		return finalStandings;
	}
	
	private TreeMap<Snake, Integer> calculateFinalFruitStandings()
	{
		TreeMap<Snake, Integer> finalStandings = new TreeMap<Snake, Integer>();
		TreeMap<Snake, Integer> tempScores = new TreeMap<Snake, Integer>(new SnakeScoreComparator());
		tempScores.putAll(scores);

		int currentPlacement = 1;
		int snakesAtCurrentPlacement = 0;
		int currentScore = 0;
		
		while (tempScores.size() > 0)
		{
			System.out.println("In calculateFinalFruitStandings()");
			for (Map.Entry<Snake, Integer> snakeEntry : tempScores.entrySet())
			{
				Snake snake = snakeEntry.getKey();
				int snakeScore = snakeEntry.getValue();
				if (snakeScore < currentScore)
					break;
				finalStandings.put(snake, currentPlacement);
				++snakesAtCurrentPlacement;
				tempScores.remove(snake);
			}
			currentPlacement += snakesAtCurrentPlacement;
			snakesAtCurrentPlacement = 0;
		}
		
		return finalStandings;
	}
	
	private TreeMap<Snake, Integer> calculateFinalLifespanStandings()
	{
		TreeMap finalStandings = new TreeMap<Snake, Integer>();
		TreeMap<Snake, Integer> tempLifespans = new TreeMap<Snake, Integer>(new SnakeLifespanComparator());
		tempLifespans.putAll(lifespans);
		
		int currentPlacement = 1;
		int snakesAtCurrentPlacement = 0;
		int currentLifespan = 0;
		
		while (tempLifespans.size() > 0)
		{
			System.out.println("In calculateFinalLifespanStandings()");
			for (Map.Entry<Snake, Integer> snakeEntry : tempLifespans.entrySet())
			{
				Snake snake = snakeEntry.getKey();
				int snakeLifespan = snakeEntry.getValue();
				if (snakeLifespan < currentLifespan)
					break;
				finalStandings.put(snake, ++snakesAtCurrentPlacement);
				tempLifespans.remove(snake);
			}
			currentPlacement += snakesAtCurrentPlacement;
			snakesAtCurrentPlacement = 0;
		}
		
		return finalStandings;
	}
	
	private class SnakeScoreComparator implements Comparator<Snake>
	{
		public int compare(Snake first, Snake second)
		{
			return (scores.get(second) - scores.get(first));
		}
	}
	
	private class SnakeLifespanComparator implements Comparator<Snake>
	{
		public int compare(Snake first, Snake second)
		{
			return (lifespans.get(second) - lifespans.get(first));
		}
	}
}