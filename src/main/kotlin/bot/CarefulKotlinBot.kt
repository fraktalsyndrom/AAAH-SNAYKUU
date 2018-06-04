package bot

import gameLogic.Brain
import gameLogic.Direction
import gameLogic.GameState
import gameLogic.Snake

class CarefulKotlinBot : Brain {
    private var gamestate: GameState? = null
    private var self: Snake? = null

    override fun getNextMove(yourSnake: Snake, gamestate: GameState): Direction {
        self = yourSnake
        this.gamestate = gamestate

        val previousDirection = self!!.currentDirection
        return if (gamestate.willCollide(self, previousDirection)) {
            previousDirection.turnLeft()
        } else previousDirection
    }
}
