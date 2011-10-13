package gameLogic;

public interface Brain
{
	public Direction getNextMove(Snake yourSnake, GameState gamestate);
}
