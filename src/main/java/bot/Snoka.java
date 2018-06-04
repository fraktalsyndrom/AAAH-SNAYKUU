package bot;

import gameLogic.Brain;
import gameLogic.Direction;
import gameLogic.GameState;
import gameLogic.Position;
import gameLogic.Snake;

/**
 *
 * @author rloqvist
 */
public class Snoka implements Brain {
    
    private Snake yourSnake;
    private GameState gameState;

    public Direction getNextMove(Snake yourSnake, GameState gameState) {
        this.yourSnake = yourSnake;
        this.gameState = gameState;
        
        Direction currentDirection = ( gameState.getFruits().isEmpty() )
                ? getMiddleDirection(yourSnake.getCurrentDirection())
                : getFruitDirection(yourSnake.getCurrentDirection());
        
        return getSafeDirection(currentDirection);
    }
    
    private Direction getMiddleDirection(Direction direction) {
        if ( isMovingToMiddle(direction) ) {
            return direction;
        } else if ( isMovingToMiddle(direction.turnLeft()) ) {
            return direction.turnLeft();
        } else {
            return direction.turnRight();
        }
    }
    
    private boolean isMovingToMiddle(Direction direction){        
        Position currPos = yourSnake.getHeadPosition();
        Position nextPos = direction.calculateNextPosition(currPos);
        
        return getMiddleDist(nextPos) < getMiddleDist(currPos);
    }
    
    private int getMiddleDist(Position position) {
        float middleX = gameState.getBoard().getWidth()/ 2;
        float middleY = gameState.getBoard().getHeight() / 2;
        
        float distX = (middleX - position.getX()) * (middleX - position.getX());
        float distY = (middleY - position.getY()) * (middleY - position.getY());
        
        return Float.floatToIntBits(distX + distY);
    }
    
    private Direction getFruitDirection(Direction direction) {
        if ( this.isMovingToFruit(direction) ) {
            return direction;
        } else if ( isMovingToFruit(direction.turnLeft()) ) {
            return direction.turnLeft();
        } else {
            return direction.turnRight();
        }
    }
    
    private boolean isMovingToFruit(Direction direction){
        Position currPos = yourSnake.getHeadPosition();
        Position nextPos = direction.calculateNextPosition(currPos);
        
        return getFruitDist(nextPos) < getFruitDist(currPos);
    }
    
    private int getFruitDist(Position position) {
        return gameState.getFruits().stream()
                .map(fruit -> position.getDistanceTo(fruit)).mapToInt(v -> v)
                .min()
                .orElse(2 * (gameState.getBoard().getHeight()));
    }

    private Direction getSafeDirection(Direction direction) {
        Position position = yourSnake.getHeadPosition();
        if ( canContinue(position, direction) ) {
            return direction;
        } else if ( canContinue(position, direction.turnLeft()) ) {
            return direction.turnLeft();
        } else if ( canContinue(position, direction.turnRight()) ){
            return direction.turnRight();
        } else {
            return direction.turnLeft().turnLeft();
        }
    }

    private boolean canContinue(Position position, Direction direction) {
        Position nextPos = direction.calculateNextPosition(position);
        
        return !gameState.getBoard().isLethal(nextPos);
    }
}
