package gameLogic;

public interface Game
{
	public GameState getCurrentState();
	public Metadata getMetadata();
	public GameResult getGameResult();
}
