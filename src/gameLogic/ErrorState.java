package gameLogic;

public enum ErrorState
{
	NO_ERROR, TOO_SLOW;
	
	public String toString()
	{
		switch (this)
		{
			case NO_ERROR:
				return "All is well";
			
			case TOO_SLOW:
				return "Your bot is too slow";
		}
		
		throw new IllegalStateException("This ErrorState is not defined");
	}
}
