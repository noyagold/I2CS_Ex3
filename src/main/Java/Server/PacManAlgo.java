package Server;

import exe.ex3.game.Game;

/**
 * Interface for defining the logic of a Pacman player.
 * Any class implementing this interface must provide a strategy for movement.
 */
public interface PacManAlgo {

    /**
     * Gets the description of the algorithm.
     * @return A string representing the logic/strategy name.
     */
    String getInfo();

    /**
     * Calculates the next move based on the current game state.
     * @param game The current state of the game (board, ghosts, player).
     * @return The direction to move (0-4) as defined in the Game constants.
     */
    int move(Game game);
}
