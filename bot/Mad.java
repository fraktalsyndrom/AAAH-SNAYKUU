package bot;

import gameLogic.*;

public class Mad implements Brain
{
	public Direction getNextMove(GameState gamestate)
	{
		return Direction.NORTH;
	}
}
