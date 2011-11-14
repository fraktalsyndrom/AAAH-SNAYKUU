package gameLogic;


/**
 * ErrorState is used to check if a Brain has been doing something bad
 * the last turn. NO_ERROR means that there have not been any error.
 *
 * @author 	Sixten Hilborn
 * @author	Arian Jafari
 * @see GameState
 */

public enum ErrorState
{
	NO_ERROR, TOO_SLOW, EXCEPTION;
	
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
			
			case EXCEPTION:
				return "Your bot threw an exception";
		}
		
		throw new IllegalStateException("This ErrorState is not defined");
	}
}
