package Server;

import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;
import java.awt.Color;

public class Ex3Algo implements PacManAlgo {


    public static final int STAY = 0;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;


    private final int WALL = Color.BLUE.getRGB();
    private final int FOOD = Color.PINK.getRGB();

    public Ex3Algo() {}

    @Override
    public String getInfo() {
        return "Ex3Algo running with Interface";
    }

    @Override
    public int move(PacmanGame game) {
        // 1.parse Board
        Map board = new Map(game.getGame(0));

        // 2.parse Position
        String posString = game.getPos(0);
        Pixel2D pacPos = parsePos(posString);

        // 3. get Ghosts
        GhostCL[] ghosts = game.getGhosts(0);

        // --- Strategy Logic ---

        //  Avoid Ghosts
        Map safeBoard = createSafeBoard(board, ghosts, pacPos, 2);

        // target Food
        Pixel2D foodTarget = getSmartFoodTarget(pacPos, safeBoard, board);

        if (foodTarget != null) {
            // Calculate Path to Food
            Pixel2D[] path = safeBoard.shortestPath(pacPos, foodTarget, WALL);
            if (path != null && path.length > 1) {
                return fixDirection(pacPos, path[1], board);
            }
        }

        return runToSafety(pacPos, ghosts, board);
    }

    // --- Helpers ---

    private Pixel2D parsePos(String s) {
        if (s == null || s.isEmpty()) return new Index2D(0, 0);
        String[] p = s.split(",");
        if (p.length < 2) return new Index2D(0, 0);
        try {
            return new Index2D(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
        } catch (NumberFormatException e) {
            return new Index2D(0,0);
        }
    }

    private int fixDirection(Pixel2D c, Pixel2D n, Map b) {
        int w = b.getWidth();
        int h = b.getHeight();
        if (n.getX() == (c.getX() + 1) % w) return RIGHT;
        if (c.getX() == (n.getX() + 1) % w) return LEFT;
        if (n.getY() == (c.getY() + 1) % h) return UP;
        if (c.getY() == (n.getY() + 1) % h) return DOWN;
        return STAY;
    }

    private Map createSafeBoard(Map original, GhostCL[] ghosts, Pixel2D pacPos, int radius) {
        Map safeMap = new Map(original.getMap());
        if (ghosts == null) return safeMap;

        for (GhostCL g : ghosts) {
            if (g.getStatus() == 1) {
                Pixel2D gPos = parsePos(g.getPos(0));
                // Mark ghost area as walls
                safeMap.setPixel(gPos, WALL);
            }
        }
        return safeMap;
    }

    private Pixel2D getSmartFoodTarget(Pixel2D pacPos, Map safeBoard, Map realBoard) {
        // Use BFS to find nearest food
        Map2D dists = safeBoard.allDistance(pacPos, WALL);
        int minDist = Integer.MAX_VALUE;
        Pixel2D bestTarget = null;

        for (int x = 0; x < realBoard.getWidth(); x++) {
            for (int y = 0; y < realBoard.getHeight(); y++) {
                if (realBoard.getPixel(x, y) == FOOD) {
                    int d = dists.getPixel(x, y);
                    if (d > 0 && d < minDist) {
                        minDist = d;
                        bestTarget = new Index2D(x, y);
                    }
                }
            }
        }
        return bestTarget;
    }

    private int runToSafety(Pixel2D pacPos, GhostCL[] ghosts, Map board) {

        Index2D[] neighbors = board.getNeighbours(pacPos);
        int[] dirs = {RIGHT, LEFT, UP, DOWN};

        for(int i=0; i<neighbors.length; i++) {
            if(board.getPixel(neighbors[i]) != WALL) {
                return dirs[i];
            }
        }
        return STAY;
    }
}