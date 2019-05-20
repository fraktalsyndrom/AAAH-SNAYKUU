package bot;

import gameLogic.Brain;
import gameLogic.Direction;
import gameLogic.GameState;
import gameLogic.Position;
import gameLogic.Snake;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Höjvall
 *
 * Winner of DVK snaykuu competition 2019
 *
 * Bot is using breadth first search and evaluating every position it finds by adding to the points variable.
 * The points add added based on if it contains a fruit, how far from the middle it is, and how far from all other
 * fruit it is. There is also a small attempt to avoid situations where this snake would crash into the head of an
 * other snake when they are both trying to go to the same square at the same time, threw it in at the last time.
 * Looking at that last thing afterwards it shouldn't work at all.
 */
public class LegitBot implements Brain {

    private GameState gamestate;
    private Snake self;
    private Position middle;
    private int x;
    private int y;

    public Direction getNextMove(Snake yourSnake, GameState gamestate) {
        self = yourSnake;
        this.gamestate = gamestate;

        x = gamestate.getBoard().getWidth() / 2;
        y = gamestate.getBoard().getHeight() / 2;
        middle = new Position(x, y);

        Direction currentDirection = self.getCurrentDirection();

        int i = calculatePoint(gamestate, currentDirection.turnLeft().calculateNextPosition(self.getHeadPosition()), currentDirection.turnLeft(), 0, 1, new ArrayList<Position>());
        int i2 = calculatePoint(gamestate, currentDirection.turnRight().calculateNextPosition(self.getHeadPosition()), currentDirection.turnRight(), 0, 1, new ArrayList<Position>());
        int i3 = calculatePoint(gamestate, currentDirection.calculateNextPosition(self.getHeadPosition()), currentDirection, 0, 1, new ArrayList<Position>());

        if (i3 >= i && i3 >= i2) {
            return currentDirection;
        } else if (i2 > i) {
            return currentDirection.turnRight();
        } else {
            return currentDirection.turnLeft();
        }
    }

    private int calculatePoint(GameState gameState, Position position, Direction direction, int points, int depth, List<Position> visited) {
        int riskDivider = 1;
        if (gameState.getBoard().isLethal(position) || depth == 10) {
            return points;
        }

        if (visited.contains(position)) {
            return points;
        }

        if (depth == 2) {
            for (Snake snake : gameState.getSnakes()) {
                Position headPosition = snake.getHeadPosition();
                if (headPosition.equals(position)) {
                    riskDivider = 8;
                }
            }
        }

        visited.add(position);

        depth++;

        // Point for just being alive!
        if (depth <= 2) {
            points += 5;
        } else if (depth < 5) {
            points += 1;
        }

        if (gameState.getBoard().hasFruit(position)) {
            points += 1000;
        }

        if (depth < 5) {
            for (Position fruit : gameState.getFruits()) {
                int distanceTo = fruit.getDistanceTo(position);
                if (distanceTo < 10) {
                    points += 12 - distanceTo / 2;
                }
            }
        }

        int middleDistanceTo = middle.getDistanceTo(position);
        if (middleDistanceTo < 5) {
            if (middleDistanceTo < 3) {
                points++;
            }

            points++;
        }

        int i = calculatePoint(gamestate, direction.turnLeft().calculateNextPosition(position), direction.turnLeft(), points, depth, new ArrayList<>(visited));
        int i2 = calculatePoint(gamestate, direction.turnRight().calculateNextPosition(position), direction.turnRight(), points, depth, new ArrayList<>(visited));
        int i3 = calculatePoint(gamestate, direction.calculateNextPosition(position), direction, points, depth, new ArrayList<>(visited));

        return (i + i2 + i3) / riskDivider;
    }


}
