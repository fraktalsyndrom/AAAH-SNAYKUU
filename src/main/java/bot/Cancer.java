package bot;

import gameLogic.*;
import java.util.List;
import java.util.Random;

public class Cancer implements Brain
{
	
	public Direction getNextMove(Snake snake, GameState gameState)
	{
		Board board = gameState.getBoard();
		
		Direction curDir = snake.getCurrentDirection();
		Position curPos = snake.getHeadPosition();
		Direction nextDir = curDir;
		
		List<Position> fruits = gameState.getFruits();
		Position nearestFruit = null;
		int currentDistance = Integer.MAX_VALUE;
		
		for(Position fruit : fruits)
		{
			int test = curPos.getDistanceTo(fruit);
			if(test < currentDistance)
			{
				nearestFruit = fruit;
				currentDistance = test;
			}
		}
		
		if(nearestFruit != null)
		{
			int xDist = curPos.getX()-nearestFruit.getX();
			int yDist = curPos.getY()-nearestFruit.getY();
			
			if(Math.abs(xDist) < Math.abs(yDist))
			{
				if(yDist < 0)
				{
					nextDir = Direction.SOUTH;
				}
				else
				{
					nextDir = Direction.NORTH;
				}
			}
			else
			{
				if(xDist < 0)
				{
					nextDir = Direction.EAST;
				}
				else
				{
					nextDir = Direction.WEST;
				}
			}
		}
		
		Position nextPos = GameState.calculateNextPosition(nextDir, curPos);
		
		int recursionDepth = snake.getSegments().size();
		recursionDepth += recursionDepth/gameState.getMetadata().getGrowthFrequency();
		
		Direction tempDir = null;
		while(nextDir != tempDir)
		{
			tempDir = nextDir;
			
			if(isLethal(nextPos, board))
			{
				nextDir = nextDir.turnRight();
				nextPos = nextDir.calculateNextPosition(curPos);
			}
			else if(isTrap(snake, nextPos, gameState))
			{
				nextDir = nextDir.turnRight();
				nextPos = nextDir.calculateNextPosition(curPos);
			}
			else if(isDeadEnd(recursionDepth, nextDir, nextPos, board))
			{
				nextDir = nextDir.turnRight();
				nextPos = nextDir.calculateNextPosition(curPos);
			}
		}
		
		return nextDir;
	}
	
	private boolean isLethal(Position next, Board board)
	{
		return board.hasGameObject(next) && !board.hasFruit(next);
	}
	
	private boolean isTrap(Snake me, Position pos, GameState gameState)
	{
		for(Snake s : gameState.getSnakes())
		{
			if
			(
				s != me 
				&& !s.isDead()
				&& 
				(
					GameState.calculateNextPosition(s.getCurrentDirection(), s.getHeadPosition()).equals(pos)
					|| GameState.calculateNextPosition(s.getCurrentDirection().turnLeft(), s.getHeadPosition()).equals(pos)
					|| GameState.calculateNextPosition(s.getCurrentDirection().turnRight(), s.getHeadPosition()).equals(pos)
				)
			)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isDeadEnd(int recursion, Direction nextD, Position nextP, Board board)
	{
		if(board.hasSnake(nextP) || board.hasWall(nextP))
		{
			return true;
		}
		
		if
		(
			recursion > 0
			&& isDeadEnd
			(
				recursion-1, 
				nextD.turnLeft(), 
				nextD.turnLeft().calculateNextPosition(nextP), 
				board
			) 
			&& isDeadEnd
			(
				recursion-1, 
				nextD.turnRight(), 
				nextD.turnRight().calculateNextPosition(nextP), 
				board
			)
			&& isDeadEnd
			(
				recursion-1, 
				nextD, 
				nextD.calculateNextPosition(nextP), 
				board
			)
		) 
		{
			return true;
		}
		return false;
	}
}
