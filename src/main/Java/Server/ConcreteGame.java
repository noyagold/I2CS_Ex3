package Server;

import exe.ex3.game.GhostCL;
import exe.ex3.game.PacmanGame;
import java.awt.Color;
import java.util.ArrayList;


public class ConcreteGame implements PacmanGame {

    public static final int STATUS_INIT = 0;
    public static final int STATUS_PLAY = 1;
    public static final int STATUS_DONE = 2;

    private int status;
    private int[][] board;
    private ArrayList<SmartGhost> ghosts; 
    private int playerX, playerY;
    private int score;
    private boolean isCyclicMap;

    // Colors
    private final int WALL = Color.BLUE.getRGB();
    private final int FOOD = Color.PINK.getRGB();
    private final int EMPTY = Color.BLACK.getRGB();

    //  20x20 Level
    private final String[] DEFAULT_MAP = {
            "####################",
            "#P........#........#",
            "#.###.###.#.###.##.#",
            "#.#...#.......#....#",
            "#.#.###.#####.###.##",
            "#..................#",
            "#####.###.#.###.####",
            "#.........#........#",
            "#.###.###.G.###.##.#",
            "#...#...#...#......#",
            "###.###.#####.###.##",
            "#.........G........#",
            "#.###.###.#.###.##.#",
            "#.#...#.......#....#",
            "#...#...#...#...G..#",
            "#####.###.#.###.####",
            "#.........#........#",
            "#.###.###.#.###.##.#",
            "#..................#",
            "####################"
    };

    public ConcreteGame() {
        this.status = STATUS_INIT;
        this.ghosts = new ArrayList<>();
        this.board = new int[20][20];
        this.score = 0;
    }

    @Override
    public String init(int level, String boardFile, boolean cyclic, long seed, double speed, int rows, int cols) {
        this.isCyclicMap = cyclic;
        this.status = STATUS_INIT;
        this.score = 0;
        this.ghosts.clear();

        int h = DEFAULT_MAP.length;
        int w = DEFAULT_MAP[0].length();
        this.board = new int[w][h];

        // Parse the map
        for (int y = 0; y < h; y++) {
            String line = DEFAULT_MAP[y];
            for (int x = 0; x < w; x++) {
                char c = line.charAt(x);
        

                int targetY = y;

                if (c == '#') {
                    this.board[x][targetY] = WALL;
                } else if (c == '.') {
                    this.board[x][targetY] = FOOD;
                } else if (c == 'P') {
                    this.board[x][targetY] = EMPTY;
                    this.playerX = x;
                    this.playerY = targetY;
                } else if (c == 'G') {
                    this.board[x][targetY] = FOOD; // Ghost sits on food
                    // Create a ghost
                    SmartGhost g = new SmartGhost(1, x + "," + targetY);
                    this.ghosts.add(g);
                } else {
                    this.board[x][targetY] = EMPTY;
                }
            }
        }

        return "Game Initialized: Level " + level;
    }

    @Override
    public void play() {
        this.status = STATUS_PLAY;
    }

    @Override
    public String move(int direction) {
        if (this.status != STATUS_PLAY) return "Game Over";

        // 1. Move Pacman
        movePacman(direction);

        // 2. Check Collision 
        if (checkCollision()) {
            this.status = STATUS_DONE;
            return "You Died";
        }

        // 3. Move Ghosts
        moveGhosts();

        // 4. Check Collision 
        if (checkCollision()) {
            this.status = STATUS_DONE;
            return "You Died";
        }

        return "OK";
    }

    private void movePacman(int direction) {
        int nextX = playerX;
        int nextY = playerY;

        switch (direction) {
            case 1: nextX++; break; // Right
            case 2: nextX--; break; // Left
            case 3: nextY--; break; // Up 
            case 4: nextY++; break; // Down
        }

        if (isCyclicMap) {
            nextX = (nextX + board.length) % board.length;
            nextY = (nextY + board[0].length) % board[0].length;
        }

        if (isValid(nextX, nextY)) {
            playerX = nextX;
            playerY = nextY;
            // Eat Food
            if (board[playerX][playerY] == FOOD) {
                board[playerX][playerY] = EMPTY;
                score++;
            }
        }
    }

    private void moveGhosts() {
        for (SmartGhost g : ghosts) {
            String[] pos = g.getPos(0).split(",");
            int gx = Integer.parseInt(pos[0]);
            int gy = Integer.parseInt(pos[1]);

            // Move towards player
            int bestDist = Integer.MAX_VALUE;
            int bestX = gx;
            int bestY = gy;

            // Try all 4 directions
            int[][] moves = {{0,1}, {0,-1}, {1,0}, {-1,0}};

            // Randomness factor so they don't get stuck
            if (Math.random() < 0.2) {
                int[] rnd = moves[(int)(Math.random()*4)];
                int nx = gx + rnd[0];
                int ny = gy + rnd[1];
                if (isValid(nx, ny)) {
                    g.setPos(nx + "," + ny);
                    continue;
                }
            }

            for (int[] m : moves) {
                int nx = gx + m[0];
                int ny = gy + m[1];

                if (isValid(nx, ny)) {
                    int d = Math.abs(nx - playerX) + Math.abs(ny - playerY); // Manhattan dist
                    if (d < bestDist) {
                        bestDist = d;
                        bestX = nx;
                        bestY = ny;
                    }
                }
            }
            g.setPos(bestX + "," + bestY);
        }
    }

    private boolean isValid(int x, int y) {
        // Bounds check
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) return false;
        // Wall check
        return board[x][y] != WALL;
    }

    private boolean checkCollision() {
        for (SmartGhost g : ghosts) {
            String[] pos = g.getPos(0).split(",");
            int gx = Integer.parseInt(pos[0]);
            int gy = Integer.parseInt(pos[1]);
            if (gx == playerX && gy == playerY) return true;
        }
        return false;
    }

    @Override
    public int[][] getGame(int tick) { return this.board; }

    @Override
    public GhostCL[] getGhosts(int tick) { return ghosts.toArray(new GhostCL[0]); }

    @Override
    public String getPos(int tick) { return playerX + "," + playerY; }

    @Override
    public boolean isCyclic() { return this.isCyclicMap; }

    @Override
    public int getStatus() { return this.status; }

    @Override
    public String end(int tick) {
        this.status = STATUS_DONE;
        return "Game Over. Score: " + score;
    }

    @Override
    public String getData(int tick) { return "{\"Score\":" + score + "}"; }

    public Character getKeyChar() { return null; }
}
