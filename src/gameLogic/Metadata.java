package gameLogic;

public class Metadata
{
	private int boardWidth;
	private int boardHeight;
	
	private int thinkingTime;
	private int growthFrequency;
	private int fruitFrequency;
	
	private int fruitGoal;
	
	private int turnsUntilGrowth;
	private int turnsUntilFruit;
	private int turn = 0;
	
	public Metadata(int boardWidth, int boardHeight, int growthFrequency, int fruitFrequency, int thinkingTime, int fruitGoal)
	{
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.growthFrequency = growthFrequency;
		this.fruitFrequency = fruitFrequency;
		this.thinkingTime = thinkingTime;
		this.fruitGoal = fruitGoal;
		
		turnsUntilGrowth = growthFrequency;
		turnsUntilFruit = fruitFrequency;
	}
	
	void tick()
	{
		if (--turnsUntilGrowth < 0)
			turnsUntilGrowth = growthFrequency;
		
		if (--turnsUntilFruit < 0)
			turnsUntilFruit = fruitFrequency;
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
	
	public int getTurnsUntilGrowth()
	{
		return turnsUntilGrowth;
	}
	
	public int getTurnsUntilFruitSpawn()
	{
		return turnsUntilFruit;
	}
	
	public int getFruitGoal()
	{
		return fruitGoal;
	}
}