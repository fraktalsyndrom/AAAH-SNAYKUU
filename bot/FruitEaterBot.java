package bot;

import gameLogic.*;
import java.util.ArrayList;
import java.util.Set;

public class FruitEaterBot implements Brain
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
		Set<Snake> otherSnakes = gamestate.getSnakes();
		otherSnakes.remove(yourSnake);
		
		
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
				if(!gamestate.willCollide(self, d) && !otherSnakeHeadIsClose(otherSnakes, yourPos, d)){
					return d;
				}
			}
			
		}
		
		
		if (gamestate.willCollide(self, previousDirection) || otherSnakeHeadIsClose(otherSnakes, yourPos, previousDirection)){
			if(!gamestate.willCollide(self, previousDirection.turnLeft()))
					return previousDirection.turnLeft();
			else
				return previousDirection.turnRight();
		}
		return previousDirection;
	}
	
	private Direction getBackwardsDirection(Direction previousDirection){
		
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
	
	private boolean otherSnakeHeadIsClose(Set<Snake> otherSnakes, Position yourPos, Direction d){
		for(Snake s : otherSnakes){
			if(gamestate.distanceBetween(s.getHeadPosition(), gamestate.calculateNextPosition(d, yourPos)) <= 1)
				return true;
		}
		return false;
	}
	
	
}
