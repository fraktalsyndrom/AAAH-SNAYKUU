package gameLogic;

public interface Brain
{
	public Direction getNextMove(GameState gamestate);
	public void init(GameState initialState);
	public void tooSlowFault();
}