package assignments.Ex3;

import exe.ex3.game.*;
import java.awt.Color;



/**
 * This is the major algorithmic class for Ex3 - the PacMan game:
 *
 * This code is a very simple example (random-walk algorithm).
 * Your task is to implement (here) your PacMan algorithm.
 */
public class Ex3Algo implements PacManAlgo{
    private final int WALL = Game.getIntColor(Color.BLUE, 0);
    private final int FOOD = Game.getIntColor(Color.PINK, 0);
    private int _count;
    public Ex3Algo() {_count=0;}
    @Override
    /**
     *  Add a short description for the algorithm as a String.
     */
    public String getInfo() {
        return null;
    }

    /**
     * This ia the main method - that you should design, implement and test.
     */
        @Override
        public int move(PacmanGame game) {
            //  get the current board state and positions
            Map board = new Map(game.getGame(0));
            Pixel2D pacPos = parsePos(game.getPos(0));
            GhostCL[] ghosts = game.getGhosts(0);

            // step 1: check how much food (pellets) is left to set bravery level
            int foodLeft = countFood(board);
            int dynamicRadius = (foodLeft > 10) ? 3 : (foodLeft > 2 ? 2 : 1);

            // step 2: a 'safe map' where ghosts are treated like walls
            Map safeBoard = createSafeBoard(board, ghosts, pacPos, dynamicRadius);

            // step 3: if ghosts are eatable try to eat them
            Pixel2D ghostTarget = getBestEdibleGhost(pacPos, ghosts, safeBoard);
            if (ghostTarget != null) {
                Pixel2D[] path = safeBoard.shortestPath(pacPos, ghostTarget, WALL);
                if (path != null && path.length > 1) {
                    return fixDirection(pacPos, path[1], board);
                }
            }

            // Step 4:find the best area to eat food
            // This looks for high density spots so we finish the game faster
            Pixel2D foodTarget = getSmartFoodTarget(pacPos, safeBoard, board);
            if (foodTarget != null) {
                Pixel2D[] path = safeBoard.shortestPath(pacPos, foodTarget, WALL);
                if (path != null && path.length > 1) {
                    return fixDirection(pacPos, path[1], board);
                }
            }

            // Step 5: if no safe path to food exists,
            // just move to the square that is furthest from danger.
            return runToSafety(pacPos, ghosts, board);
        }


    /**
     * Converts a string "x,y" to a Pixel2D object.
     * @param s The string from the game API
     * @return A Pixel2D coordinate
     */
    private Pixel2D parsePos(String s) {
        String[] p = s.split(",");
        return new Index2D(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }

    /**
     * Calculates Manhattan distance with wrap-around support.
     * @param p1 Point A
     * @param p2 Point B
     * @param b The game board
     * @return The shortest distance between points
     */
    private int getDist(Pixel2D p1, Pixel2D p2, Map b) {
        int dx = Math.abs(p1.getX() - p2.getX());
        int dy = Math.abs(p1.getY() - p2.getY());
        dx = Math.min(dx, b.getWidth() - dx);//check if it's faster to go through the screen edge
        dy = Math.min(dy, b.getHeight() - dy);
        return dx + dy;
    }

    /**
     * Finds the move command for a target tile.
     * @param c Current tile
     * @param n Next tile
     * @param b The board
     * @return Game direction
     */
    private int fixDirection(Pixel2D c, Pixel2D n, Map b) {
        if (n.getX() == (c.getX() + 1) % b.getWidth()) return Game.RIGHT;
        if (c.getX() == (n.getX() + 1) % b.getWidth()) return Game.LEFT;
        if (n.getY() == (c.getY() + 1) % b.getHeight()) return Game.UP;
        if (c.getY() == (n.getY() + 1) % b.getHeight()) return Game.DOWN;
        return Game.STAY;
    }

    /**
     * Counts how much food is left on board.
     * @param board The current map
     * @return Total food count
     */
    private int countFood(Map board) {
        int c = 0;
        for (int x = 0; x < board.getWidth(); x++)
            for (int y = 0; y < board.getHeight(); y++)
                if (board.getPixel(x, y) == FOOD) c++;
        return c;
    }

    /**
     * Checks how much food is in a small area around a point.
     * @param x Center x
     * @param y Center y
     * @param board The board
     * @return Number of nearby food
     */
    private int countNearbyFood(int x, int y, Map board) {
        int count = 0;
        // looking at a 5x5 area to find "hot spots"
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                int nx = (x + i + board.getWidth()) % board.getWidth();
                int ny = (y + j + board.getHeight()) % board.getHeight();
                if (board.getPixel(nx, ny) == FOOD) count++;
            }
        }
        return count;
    }

    /**
     * Creates a map where dangerous ghost zones are blocked.
     * @param original The real board
     * @param ghosts List of ghosts
     * @param pacPos My position
     * @param radius Buffer distance
     * @return A "Safe" map with extra walls
     */
    private Map createSafeBoard(Map original, GhostCL[] ghosts, Pixel2D pacPos, int radius) {
        Map safeMap = new Map(original.getMap());
        for (GhostCL g : ghosts) {
            // Only avoid if the ghost is dangerous and not eatable
            if (g.getStatus() == 1 && g.remainTimeAsEatable(0) < 1.0) {
                Pixel2D gPos = parsePos(g.getPos(0));
                for (int x = 0; x < safeMap.getWidth(); x++) {
                    for (int y = 0; y < safeMap.getHeight(); y++) {
                        Pixel2D p = new Index2D(x, y);
                        if (getDist(p, gPos, original) <= radius && !p.equals(pacPos)) {
                            safeMap.setPixel(p, WALL);
                        }
                    }
                }
            }
        }
        return safeMap;
    }

    /**
     * Move away from ghosts if trapped.
     * @param pacPos Current pos
     * @param ghosts List of ghosts
     * @param board The board
     * @return Safest direction to move
     */
    private int runToSafety(Pixel2D pacPos, GhostCL[] ghosts, Map board) {
        Index2D[] neighbors = board.getNeighbours(pacPos);
        int[] dirs = {Game.RIGHT, Game.LEFT, Game.UP, Game.DOWN};
        int bestDir = Game.STAY;
        double maxSafetyScore = -1;

        for (int i = 0; i < neighbors.length; i++) {
            Pixel2D n = neighbors[i];
            if (board.getPixel(n) == WALL) continue;
            double currentScore = 0;
            for (GhostCL g : ghosts) {
                if (g.getStatus() == 1 && g.remainTimeAsEatable(0) < 1.0) {
                    int d = getDist(n, parsePos(g.getPos(0)), board);
                    if (d <= 1) currentScore -= 1000;
                    else currentScore += d;
                }
            }
            if (currentScore > maxSafetyScore) {
                maxSafetyScore = currentScore;
                bestDir = dirs[i];
            }
        }
        if (bestDir == Game.STAY) {
            for (int i = 0; i < neighbors.length; i++)
                if (board.getPixel(neighbors[i]) != WALL) return dirs[i];
        }
        return bestDir;
    }

    /**
     * Chooses the best food area to go to.
     * @param pacPos My position
     * @param safeBoard The safe map
     * @param realBoard The real map
     * @return Target pixel
     */
    private Pixel2D getSmartFoodTarget(Pixel2D pacPos, Map safeBoard, Map realBoard) {
        Map2D dists = safeBoard.allDistance(pacPos, WALL);
        Pixel2D best = null;
        double maxScore = -1;
        for (int x = 0; x < realBoard.getWidth(); x++) {
            for (int y = 0; y < realBoard.getHeight(); y++) {
                if (realBoard.getPixel(x, y) == FOOD) {
                    int d = dists.getPixel(x, y);
                    if (d <= 0) continue;
                    double score = (double) countNearbyFood(x, y, realBoard) / (d * 0.8);
                    if (score > maxScore) { maxScore = score; best = new Index2D(x, y); }
                }
            }
        }
        return best;
    }

    /**
     * Finds the nearest edible ghost.
     * @param pacPos My position
     * @param ghosts All ghosts
     * @param safeBoard Safe map
     * @return Target ghost position or null
     */
    private Pixel2D getBestEdibleGhost(Pixel2D pacPos, GhostCL[] ghosts, Map safeBoard) {
        Pixel2D best = null; int minD = Integer.MAX_VALUE;
        for (GhostCL g : ghosts) {
            if (g.getStatus() == 1 && g.remainTimeAsEatable(0) > 2.0) {
                Pixel2D gPos = parsePos(g.getPos(0));
                Pixel2D[] path = safeBoard.shortestPath(pacPos, gPos, WALL);
                if (path != null && path.length < minD) { minD = path.length; best = gPos; }
            }
        }
        return best;
    }


}