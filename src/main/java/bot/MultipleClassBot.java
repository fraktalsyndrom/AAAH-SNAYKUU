package bot;

import gameLogic.*;

public class MultipleClassBot implements Brain
{
	private MultipleClassBotHelpCollection mcb_hc;
	private int turn = 0;
	
	public MultipleClassBot()
	{
		mcb_hc = new MultipleClassBotHelpCollection();
	}
	
	public Direction getNextMove(Snake yourSnake, GameState gamestate)
	{
		if (turn++ == 0)
		{
			mcb_hc.setStartDirection(yourSnake.getCurrentDirection());
		}
		return mcb_hc.getNextDirection();
	}
}