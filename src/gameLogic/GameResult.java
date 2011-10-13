package gameLogic;

import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.LinkedList;
import java.util.Comparator;

public class GameResult
{
	private TreeSet<Snake> sortedSnakes;
	
	private Metadata metadata;
	
	public static final int FRUIT_FINISH = 1;
	public static final int DEATH_FINISH = 2;
	
	public GameResult(Set<Snake> snakes, Metadata metadata)
	{
		this.metadata = metadata;
		
		sortedSnakes = new TreeSet<Snake>(getSnakeComparator(snakes));
		sortedSnakes.addAll(snakes);
	}
	
	private Comparator<Snake> getSnakeComparator(Set<Snake> snakes)
	{
		switch (getEndGameCondition(snakes))
		{
			case FRUIT_FINISH:
				return new SnakeScoreComparator();
			case DEATH_FINISH:
				return new SnakeLifespanComparator();
		}
		
		throw new IllegalStateException("endGameCondition is set to an invalid value/is null");
	}
	
	private int getEndGameCondition(Set<Snake> snakes)
	{
		for (Snake snake : snakes)
		{
			if (snake.getScore() == metadata.getFruitGoal())
				return FRUIT_FINISH;
		}
		
		return DEATH_FINISH;
	}
	
	public int getEndGameCondition()
	{
		return getEndGameCondition(sortedSnakes);
	}
	
	public TreeSet<Snake> getFinalStandings()
	{
		return sortedSnakes;
	}
	
	public List<List<Snake>> getWinners()
	{
		LinkedList<List<Snake>> winners = new LinkedList<List<Snake>>();
		int highestScore = Integer.MAX_VALUE;
		for (Snake snake : sortedSnakes)
		{
			int currentScore;
			switch (getEndGameCondition())
			{
				case FRUIT_FINISH:
					currentScore = snake.getScore();
					break;
				case DEATH_FINISH:
					currentScore = snake.getLifespan();
					break;
				default:
					throw new IllegalStateException("ITS IMPOSSIBLE");
			}
			if (currentScore < highestScore)
			{
				winners.addLast(new LinkedList<Snake>());

				highestScore = currentScore;
			}
			
			winners.getLast().add(snake);
		}
		return winners;
	}
	
	private static class SnakeScoreComparator implements Comparator<Snake>
	{
		public int compare(Snake first, Snake second)
		{
			return compareTwoSnakes(second.getScore() - first.getScore(), first, second);
		}
	}
	
	private static class SnakeLifespanComparator implements Comparator<Snake>
	{
		public int compare(Snake first, Snake second)
		{
			return compareTwoSnakes(second.getLifespan() - first.getLifespan(), first, second);
		}
	}
	
	private static int compareTwoSnakes(int cmp, Snake first, Snake second)
	{
		if (cmp != 0)
			return cmp;
		
		int strcmp = first.toString().compareTo(second.toString());
		if (strcmp != 0)
			return strcmp;
		
		return second.hashCode() - first.hashCode();
	}
}
