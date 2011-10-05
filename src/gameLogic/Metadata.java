package gameLogic;

public class Metadata
{
	private int boardWidth;
	private int boardHeight;
	
	private int thinkingTime;
	private int growthFrequency;
	private int fruitFrequency;
	
	public Metadata(int boardWidth, int boardHeight, int growthFrequency, int fruitFrequency, int thinkingTime)
	{
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		this.growthFrequency = growthFrequency;
		this.fruitFrequency = fruitFrequency;
		this.thinkingTime = thinkingTime;
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
}
