package gameLogic;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class GameResult
{
	private Set<Snake> snakes;
	private Metadata metadata;
	private RecordedGame recordedGame;
	
	public GameResult(Set<Snake> snakes, Metadata metadata, RecordedGame recordedGame)
	{
		this.metadata = metadata;		
		this.snakes = snakes;
		this.recordedGame = recordedGame;
	}
	
	public RecordedGame getRecordedGame()
	{
		return recordedGame;
	}
	
	public List<List<Snake>> getWinners()
	{
		ArrayList<List<Snake>> results = new ArrayList<List<Snake>>();
		SnakeComparator snakeComparator = new SnakeComparator();
		for (Snake snake : snakes)
		{
			int index;
			boolean addedToExistingList = false;
			for (index = 0; index < results.size(); ++index)
			{
				Snake examinedSnake = results.get(index).get(0);
				int comparisonResult = snakeComparator.compare(snake, examinedSnake);
				
				if (comparisonResult > 0)
				{
					break;
				}
				else if (comparisonResult == 0)
				{
					results.get(index).add(snake);
					addedToExistingList = true;
				}
			}
			if (!addedToExistingList)
			{
				ArrayList<Snake> newPlacement = new ArrayList<Snake>();
				newPlacement.add(snake);
				results.add(index, newPlacement);
			}
		}
		return results;
	}
	
	public String toString()
	{
		List<List<Snake>> winners = getWinners();
		
		int placement = 1;
		String retVal = "";
		
		for (List<Snake> snakeList : winners)
		{
			retVal += (placement + ":");
			for (Snake snake : snakeList)
			{
				retVal += ("\t" + snake + " (" + snake.getScore() + ", " + snake.getLifespan() + ")\n");
				++placement;
			}
		}
		
		return retVal;
	}
	
	private static class SnakeComparator implements Comparator<Snake>
	{
		public int compare(Snake first, Snake second)
		{
			int comparedLifespan = first.getLifespan() - second.getLifespan();
			if (comparedLifespan != 0)
				return comparedLifespan;
			return first.getScore() - second.getScore();
		}
	}
}
