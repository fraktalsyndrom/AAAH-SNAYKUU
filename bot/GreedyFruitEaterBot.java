package bot;

import gameLogic.*;
import java.util.ArrayList;

public class GreedyFruitEaterBot implements Brain
{
	private GameState gamestate;
	private Snake self;
	
	public Direction getNextMove(Snake yourSnake, GameState gamestate)
	{
		self = yourSnake;
		this.gamestate = gamestate;
		Position yourPos = yourSnake.getHeadPosition();
		Position closestFruitPos = null;
		Direction previousDirection = self.getCurrentDirection();
		Direction backwards = getBackwardsDirection(previousDirection);
		
		
		if(!gamestate.getFruits().isEmpty()){
			
			ArrayList<Position> fruits = gamestate.getFruits();
			int minDistance = Integer.MAX_VALUE;
			for(Position p : fruits){
				if(gamestate.distanceBetween(p, yourPos) < minDistance){
					minDistance = gamestate.distanceBetween(p, yourPos);
					closestFruitPos = p;
				}
			}
			
			ArrayList<Direction> fruitDirections = GameState.getRelativeDirections(yourPos, closestFruitPos);
			
			for(Direction d : fruitDirections){
				if(!gamestate.willCollide(self, d)){
					return d;
				}
			}
			
		}
		
		if (gamestate.willCollide(self, previousDirection))
		{
			return previousDirection.turnLeft();
		}
		return previousDirection;
	}
	
	public Direction getBackwardsDirection(Direction previousDirection){
		
		if(previousDirection == Direction.SOUTH)
			return Direction.NORTH;
		else if(previousDirection == Direction.NORTH)
			return Direction.SOUTH;
		else if(previousDirection == Direction.EAST)
			return Direction.WEST;
		else if(previousDirection == Direction.WEST)
			return Direction.EAST;
		return null;
	}
	
}
