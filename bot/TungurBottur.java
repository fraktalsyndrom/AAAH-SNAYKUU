package bot;

import gameLogic.*;

public class TungurBottur implements Brain {
	@Override
	public Direction getNextMove(Snake yourSnake, GameState gameState) {
		Position prevPos = yourSnake.getHeadPosition();
		Direction prevDir = yourSnake.getCurrentDirection();

		double leftScore = getScore(prevDir.turnLeft().calculateNextPosition(prevPos), gameState);
		double straightScore = getScore(prevDir.calculateNextPosition(prevPos), gameState);
		double rightScore = getScore(prevDir.turnRight().calculateNextPosition(prevPos), gameState);

		System.out.println("left: " + leftScore + " right: " + rightScore + " straight: " + straightScore);

		if(leftScore >= straightScore && leftScore >= rightScore){
			System.out.println("Going Left");
			return prevDir.turnLeft();
		}
		if(rightScore >= straightScore && rightScore >= leftScore){
			System.out.println("Going Right");
			return prevDir.turnRight();
		}
		System.out.println("Going Straight");
		return prevDir;
	}

	private double getScore(Position position, GameState state){
		if(isLethal(position, state.getBoard())){
			return Double.NEGATIVE_INFINITY;
		}
		return getFruitScore(position, state);
	}

	private double getFruitScore(Position pos, GameState state){
		double score = 1.0;

		for (Position position : state.getFruits()) {
			System.out.println(pos.getDistanceTo(position));
			score = score * (1.0 / pos.getDistanceTo(position));
		}

		return score;
	}

	private boolean isLethal(Position next, Board board)
	{
		return board.hasGameObject(next) && !board.hasFruit(next);
	}
}
