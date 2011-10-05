package gameLogic;

public class Metadata
{
	private int boardWidth;
	private int boardHeight;
	
	private int thinkingTime;
	private int growthFrequency;
	private int fruitFrequency;
	private int fruitGoal;
	
	public Metadata(int boardWidth, int boardHeight, int growthFrequency, int fruitFrequency, int thinkingTime, int fruitGoal)
	{
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.growthFrequency = growthFrequency;
		this.fruitFrequency = fruitFrequency;
		this.thinkingTime = thinkingTime;
		this.fruitGoal = fruitGoal;
	}
	
	public int getBoardWidth()
	{
		return boardWidth;
	}
	
	public int getBoardHeight()
	{
		return boardHeight;
	}
	
	public int getMaximumThinkingTime()
	{
		return thinkingTime;
	}
	
	public int getGrowthFrequency()
	{
		return growthFrequency;
	}
	
	public int getFruitFrequency()
	{
		return fruitFrequency;
	}
	
	public int getFruitGoal()
	{
		return fruitGoal;
	}
}
