package Server;

import java.awt.Color;
import java.awt.Font;
import exe.ex3.game.GhostCL;

public class Ex3Main {

    public static void main(String[] args) {
        play1();
    }

    public static void play1() {
        ConcreteGame ex3 = new ConcreteGame();
        ex3.init(0, "", false, 1234, 1.0, 20, 20); // Not cyclic for this map

        Ex3Algo man = new Ex3Algo();
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(20, 0);
        StdDraw.enableDoubleBuffering();

        System.out.println("--- Starting Pacman ---");
        ex3.play();

        while(ex3.getStatus() != ConcreteGame.STATUS_DONE) {

            // logic
            int dir = man.move(ex3);
            ex3.move(dir);

            // render
            drawFrame(ex3);

            // Speed (adjust to make it playable/watchable)
            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }

        System.out.println(ex3.end(0));

        // Draw Game Over screen
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 40));
        StdDraw.text(10, 10, "GAME OVER");
        StdDraw.show();
    }

    private static void drawFrame(ConcreteGame game) {
        StdDraw.clear(StdDraw.BLACK);

        int[][] board = game.getGame(0);
        int w = board.length;
        int h = board[0].length;

        // Draw Map
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int tile = board[x][y];
                if (tile == Color.BLUE.getRGB()) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
                } else if (tile == Color.PINK.getRGB()) {
                    StdDraw.setPenColor(StdDraw.PINK);
                    StdDraw.filledCircle(x + 0.5, y + 0.5, 0.15);
                }
            }
        }

        // Draw Pacman
        String[] pPos = game.getPos(0).split(",");
        double px = Double.parseDouble(pPos[0]);
        double py = Double.parseDouble(pPos[1]);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.filledCircle(px + 0.5, py + 0.5, 0.4);

        // Draw Ghosts
        GhostCL[] ghosts = game.getGhosts(0);
        if (ghosts != null) {
            StdDraw.setPenColor(StdDraw.RED);
            for (GhostCL g : ghosts) {
                String[] gPos = g.getPos(0).split(",");
                double gx = Double.parseDouble(gPos[0]);
                double gy = Double.parseDouble(gPos[1]);
                StdDraw.filledCircle(gx + 0.5, gy + 0.5, 0.4);
            }
        }

        // Draw Score
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.textLeft(0.5, 0.5, game.getData(0)); // Score

        StdDraw.show();
    }

    public static Character getCMD() { return ' '; }
}