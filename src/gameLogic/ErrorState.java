package gameLogic;


/**
 * ErrorState is used to check if a Brain has been doing something bad
 * the last turn. NO_ERROR means that there have not been any error. If
 * an error occurs, the bot will continue to move in the same direction
 * as it did in the turn before.
 *
 * @author 	Sixten Hilborn
 * @author	Arian Jafari
 * @see GameState
 */

public enum ErrorState
{
	/**
	 * NO_ERROR means that all is well.
	 */
	NO_ERROR,
	
	/**
	 * Means that the Brain is taking too long time to think. If you get
	 * this error, you should try to change your algorithms so they run
	 * in less time.
	 */
	TOO_SLOW,
	
	/**
	 * Means that the Brain tries to make an invalid move. There are
	 * three possible directions your snake can move in, and backwards
	 * is not one of them. Actually, a 180 degree turn is the only
	 * invalid move your snake can do.
	 */
	INVALID_MOVE,
	
	/**
	 * Means that the Brain threw an exception to the game engine. The
	 * engine will not receive your movement decisions if you throw,
	 * so make sure to keep your exceptions for yourself.
	 */
	EXCEPTION;
	
	/**
	 * Converts the enum label into a human readable string, describing
	 * the error.
	 * 
	 * @return A string describing the error.
	 */
	public String toString()
	{
		switch (this)
		{
			case NO_ERROR:
				return "All is well";
			
			case TOO_SLOW:
				return "Your bot is too slow";
				
			case INVALID_MOVE:
				return "Your bot was trying to turn 180 degrees!";
			
			case EXCEPTION:
				return "Your bot threw an exception";
		}
		
		throw new IllegalStateException("This ErrorState is not defined");
	}
}
