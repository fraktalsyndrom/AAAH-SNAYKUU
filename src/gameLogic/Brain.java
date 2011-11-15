package gameLogic;

/**
 * This is the interface a participant needs to implement into his or her code.
 * A single method, <code>getNextMove</code>, is required, and this is the method
 * that will be called by the game engine each turn in order to
 * ask the participant's snake which direction it wishes to move in.
 *
 * @author	Sixten Hilborn
 * @author	Arian Jafari
 */

public interface Brain
{
	/**
	 * Returns which direction this brain wishes its snake to move in. This function is called
	 * by the game engine every turn. Given a {@link GameState}
	 * and a reference to the brain's snake, the brain should somehow calculate which direction
	 * it finds is optimal to move towards. If the Direction returned by this method is a 180 degree
	 * turn in comparison to the snake's current direction, its direction will simply remain unchanged.
	 * Similarly, if the brain takes too long in calculating its move, it will continue moving in its
	 * previous direction.
	 * 
	 * @param	yourSnake	The snake belonging to this brain.
	 * @param	gameState	The current state of the game.
	 * @return	The direction in which the brain wants its snake to move next.
	 */
	public Direction getNextMove(Snake yourSnake, GameState gameState);
}
