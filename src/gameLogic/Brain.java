package gameLogic;

public interface Brain
{
	public Direction getNextMove(GameState gamestate);
	public void tooSlowFault();
}
