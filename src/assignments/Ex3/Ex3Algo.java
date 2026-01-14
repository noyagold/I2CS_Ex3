package assignments.Ex3;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;
import java.awt.Color;
import java.awt.*;

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
    @Override
    /**
     * This ia the main method - that you should design, implement and test.
     */
    public int move(PacmanGame game) {
        if(_count==0 || _count==300) {
            int code = 0;
            int[][] board = game.getGame(0);
            printBoard(board);
            int blue = Game.getIntColor(Color.BLUE, code);
            int pink = Game.getIntColor(Color.PINK, code);
            int black = Game.getIntColor(Color.BLACK, code);
            int green = Game.getIntColor(Color.GREEN, code);
            System.out.println("Blue=" + blue + ", Pink=" + pink + ", Black=" + black + ", Green=" + green);
            String pos = game.getPos(code).toString();
            System.out.println("Pacman coordinate: "+pos);
            GhostCL[] ghosts = game.getGhosts(code);
            printGhosts(ghosts);
            int up = Game.UP, left = Game.LEFT, down = Game.DOWN, right = Game.RIGHT;
        }
        _count++;
        int dir = randomDir();
        return dir;
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
}