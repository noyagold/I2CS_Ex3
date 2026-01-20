package Server;

/**
 * Interface representing the core Pacman game logic and state.
 */
public interface PacmanGame {

    // --- Game States ---
    int STATUS_INIT = 0;
    int STATUS_PLAY = 1;
    int STATUS_PAUSE = 2;
    int STATUS_DONE = 3;
    int STATUS_ERR = -1;

    // --- Movement Directions ---
    int STAY = 0;
    int UP = 1;
    int LEFT = 2;
    int DOWN = 3;
    int RIGHT = 4;

    /**
     * @return The current status of the game (INIT, PLAY, etc.)
     */
    int getStatus();

    /**
     * Initializes the game with the given parameters.
     * @return A string representation of the initial state.
     */
    String init(int level, String boardFile, boolean cyclic, long seed, double speed, int rows, int cols);

    /**
     * Starts or resumes the game.
     */
    void play();

    /**
     * Executes a move in the given direction.
     * @param direction (0=STAY, 1=UP, 2=LEFT, 3=DOWN, 4=RIGHT)
     * @return Information about the game after the move.
     */
    String move(int direction);

    /**
     * @return The grid representing the game board (walls, pellets, etc.)
     */
    int[][] getGame(int tick);

    /**
     * @return An array of all ghosts currently in the game.
     */
    GhostCL[] getGhosts(int tick);

    /**
     * @return The current position of the player/entities as a String.
     */
    String getPos(int tick);

    /**
     * @return True if the map is cyclic (wrap-around edges).
     */
    boolean isCyclic();

    /**
     * Ends the game and returns the final score/log.
     */
    String end(int tick);

    /**
     * @return General game data/metadata.
     */
    String getData(int tick);

    /**
     * @return The character representing the last key pressed.
     */
    Character getKeyChar();
}