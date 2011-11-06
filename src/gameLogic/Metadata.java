package gameLogic;

import java.io.Serializable;

/**
 * This class contains all the metadata concerning the current game session.
 * such as the size of the map, the thinking time for the snakes, the frequency
 * at which snakes grow, the frequency at which fruit spawns and the number 
 * of fruits snakes need to eat to win.
 *
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 */

public class Metadata implements Serializable
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
	
	/**
	 * Gets the width of the game board.
	 * 
	 * @return	The width of the board.
	 */
	public int getBoardWidth()
	{
		return boardWidth;
	}
	
	/**
	 * Gets the height of the game board.
	 * 
	 * @return	The height of the board.
	 */
	public int getBoardHeight()
	{
		return boardHeight;
	}
	
	/**
	 * Gets the thinking time each snake has each turn, in milliseconds.
	 * 
	 * @return	The thinking time in milliseconds.
	 */
	public int getMaximumThinkingTime()
	{
		return thinkingTime;
	}
	
	/**
	 * Gets the number of turns it takes for snakes to grow. Note that this is
	 * not the same thing as the number of turns that remain until the next
	 * time snakes grow.
	 * 
	 * @return	The frequency (in turns) with which snakes grow.
	 */
	public int getGrowthFrequency()
	{
		return growthFrequency;
	}
	
	/**
	 * Gets the number of turns it takes for fruit to spawn. Note that this is
	 * not the same thing as the number of turns that remain until the next
	 * time snakes grow.
	 * 
	 * @return	The frequency (in turns) with which snakes grow.
	 */
	public int getFruitFrequency()
	{
		return fruitFrequency;
	}
	
	/**
	 * Gets the number of total fruit required to win the game. 
	 * 
	 * @return	The number of fruit required to win the game.
	 */
	public int getFruitGoal()
	{
		return fruitGoal;
	}
}
