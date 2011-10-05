package gameLogic;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Comparator;

public class GameResult
{
	private Map<Snake, Integer> scores;
	private Map<Snake, Integer> lifespans;
	private TreeMap<Snake, Integer> finalStandings;
	private int endGameCondition;
	
	public static final int FRUIT_FINISH = 1;
	public static final int DEATH_FINISH = 2;
	
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
	
	void setEndGameCondition(int endGameCondition)
	{
		if (endGameCondition < 1 || endGameCondition > 2)
			throw new IllegalArgumentException("endGameCondition must be either FRUIT_FINISH (= 1) or DEATH_FINISH (= 2).");
		this.endGameCondition = endGameCondition;
	}
	
	public Map<Snake, Integer> calculateFinalScore()
	{
		TreeMap<Snake, Integer> finalScore;
		switch (endGameCondition)
		{
			case FRUIT_FINISH:
				finalScore = new TreeMap<Snake, Integer>(new SnakeScoreComparator());
				finalScore.putAll(scores);
				break;
			case DEATH_FINISH:
				finalScore = new TreeMap<Snake, Integer>(new SnakeLifespanComparator());
				finalScore.putAll(lifespans);
				break;
			default:
				throw new IllegalStateException("endGameCondition is set to an invalid value/is null");
		}
		return finalScore;
	}
	
	public ArrayList<Snake> getWinnersFrom(Map<Snake, Integer> results)
	{
		ArrayList<Snake> winners = new ArrayList<Snake>();
		int highestScore = -1;
		for (Map.Entry<Snake, Integer> entry : results.entrySet())
		{
			Snake currentSnake = entry.getKey();
			int currentScore = entry.getValue();
			if (currentScore > highestScore)
			{
				winners.clear();
				highestScore = currentScore;
				winners.add(currentSnake);
			}
			else if (currentScore == highestScore)
			{
				winners.add(currentSnake);
			}
		}
		return winners;
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