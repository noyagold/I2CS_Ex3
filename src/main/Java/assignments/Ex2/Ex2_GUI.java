package assignments.Ex2;
import assignments.Ex1.StdDraw;

import java.awt.*;
import java.io.*;




/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */

    public class Ex2_GUI {
    public static void drawMap(Map2D map) {
        int c = map.getWidth();
        int r = map.getHeight();
        StdDraw.clear();
        StdDraw.setScale(0.0, 1.0);

        double cellSize = 1.0 / Math.max(c, r);
        double half = cellSize / 2.0; //for fill square

        for (int i = 0; i < r; i++) { //x-rows
            for (int j = 0; j < c; j++) { //y-columns
                int v = map.getPixel(j, i);
                StdDraw.setPenColor(colorByValue(v));
                double x = (j + 0.5) * cellSize;
                double y = 1 - (i + 0.5) * cellSize; //upsidedown
                StdDraw.filledSquare(x, y, half);
            }
        }
        StdDraw.show();
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapFileName));//br always wraps another reader

            String line = br.readLine(); //reading starts , first line
            String[] parts = line.trim().split("\\s+");//handles whitespaces and trims space ends

            int height = Integer.parseInt(parts[0]);//transfer height and width (strings) to int
            int width = Integer.parseInt(parts[1]);

            ans = new Map(width, height, 0); //create object map with taken size(height,width)

            for (int row = 0; row < height; row++) {
                line = br.readLine(); //read next raw
                parts = line.trim().split("\\s+"); //clean it

                for (int col = 0; col < width; col++) {
                    int par = Integer.parseInt(parts[col]); //parse every cell in that raw
                    ans.setPixel(col, row, par);//put par in the map in designated place
                }
            }

            br.close();//close file
            return ans;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(mapFileName)); //prepare converting int to string txt and writing in file

            out.println(map.getHeight() + " " + map.getWidth());//first line is height & width

            for (int row = 0; row < map.getHeight(); row++) { //x
                for (int col = 0; col < map.getWidth(); col++) { //y
                    out.print(map.getPixel(col, row) + " ");
                }
                out.println();//every finished row start lower row
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] a) {
        fillExample();
        shortestPathExample();
        rescaleExample();
        allDistanceExample();
        drawRectAndLine();
        simpleMazeExample();
    }


    /// ///////////// Private functions ///////////////

    private static Color colorByValue(int v) {

        if (v == -1) return Color.BLACK;                  // obstacle
        if (v == 0) return new Color(230, 230, 230);       // background

        if (v == 1) return Color.white;
        if (v == 2) return new Color(70, 120, 255);        // start
        if (v == 3) return new Color(255, 200, 0);         // target
        if (v == 4) return new Color(0, 180, 0);           // fill
        if (v == 5) return new Color(255, 80, 80);
        if (v == 6) return new Color(160, 90, 255);
        if (v == 7) return new Color(0, 200, 200);
        if (v == 8) return new Color(255, 140, 0);
        if (v == 9) return new Color(255, 120, 180);
        if (v == 10) return new Color(120, 210, 255);
        if (v == 11) return new Color(190, 160, 255);
        if (v == 12) return new Color(150, 100, 60);
        if (v == 13) return new Color(255, 210, 170);


        return Color.DARK_GRAY;
    }

    /**
     * fill example:
     * Builds an inner box of obstacles and fills from a point outside the box.
     */
    private static void fillExample() {
        int size = 30;
        Map2D map = new Map(size, size, 0);
        // inner box obstacle
        for (int x = 8; x <= 20; x++) {
            map.setPixel(x, 8, -1);
            map.setPixel(x, 20, -1);
        }
        for (int y = 8; y <= 20; y++) {
            map.setPixel(8, y, -1);
            map.setPixel(20, y, -1);
        }
        Pixel2D p = new Index2D(2, 2);
        drawMap(map);//before
        StdDraw.pause(1000);
        map.fill(p, 7, false);   // fill
        drawMap(map);
        StdDraw.pause(1000);
    }

    /**
     * shortest path example:
     * Creates a simple maze and shows the shortest path from start to target.
     */
    private static void shortestPathExample() {
        int size = 30;
        Map2D map = new Map(size, size, 0);
        for (int i = 0; i < size; i++) {//borders
            map.setPixel(i, 0, -1);
            map.setPixel(i, size-1, -1);
            map.setPixel(0, i, -1);
            map.setPixel(size-1, i, -1);
        }
        for (int y = 2; y < size-2; y++) map.setPixel(10, y, -1);//maze
        for (int x = 10; x < size-2; x++) map.setPixel(x, 18, -1);
        map.setPixel(10, 8, 0);  // gaps
        map.setPixel(22, 18, 0);
        Pixel2D start = new Index2D(2, 2); //start and target
        Pixel2D target = new Index2D(27, 27);
        map.setPixel(start, 2);
        map.setPixel(target, 3);
        drawMap(map); //the before
        Pixel2D[] path = map.shortestPath(start, target, -1, false);
        for (Pixel2D p : path) {//each p in path
            if (!p.equals(start) && !p.equals(target)) {
                map.setPixel(p, 4); // path color
            }
        }
        drawMap(map);
        StdDraw.pause(1200);
    }

    /**
     * rescale example:
     * Draws several circles (grapes) and rescales the map.
     */
    private static void rescaleExample() {
        Map2D map = new Map(80);
        Pixel2D c = new Index2D(40, 30);
        map.drawCircle(c, 6, 4);
        map.drawCircle(new Index2D(34, 36), 6, 6);
        map.drawCircle(new Index2D(46, 36), 6, 6);
        map.drawCircle(new Index2D(28, 42), 6, 6);
        map.drawCircle(new Index2D(40, 42), 6, 6);
        map.drawCircle(new Index2D(52, 42), 6, 6);
        map.drawCircle(new Index2D(34, 48), 6, 6);
        map.drawCircle(new Index2D(46, 48), 6, 6);
        map.drawCircle(new Index2D(40, 54), 6, 6);
        drawMap(map);

        map.rescale(1.5, 0.7);
        drawMap(map);
    }

    /**
     * allDistance example:
     * Computes distance from the center point. colors are translated from distance.
     */
    private static void allDistanceExample() {
        int size = 10;
        Map2D map1 = new Map(size);
        Pixel2D start = new Index2D(size/2, size/2);
        map1.setPixel(start, 2);
        drawMap(map1);
        StdDraw.pause(1500);
        map1 = map1.allDistance(start, -1, true);
        drawMap(map1);
        StdDraw.pause(1500);
    }

    /**
     * Draws rectangles and diagonal lines.
     */
    public static void drawRectAndLine() {
        int size = 60;
        Map2D map = new Map(size, size, 9);
        map.drawRect(new Index2D(4, 8), new Index2D(16, 28), 11);
        map.drawRect(new Index2D(34, 14), new Index2D(52, 36), 3);
        Pixel2D top    = new Index2D(size/2, 6);
        Pixel2D right  = new Index2D(size-6, size/2);
        Pixel2D bottom = new Index2D(size/2, size-6);
        Pixel2D left   = new Index2D(6, size/2);
        map.drawLine(top, right, 10);
        map.drawLine(right, bottom, 10);
        map.drawLine(bottom, left, 10);
        map.drawLine(left, top, 10);
        drawMap(map);
        StdDraw.pause(1000);
    }

    /**
     * A maze with shortest path.
     */
    private static void simpleMazeExample() {
        int size = 15;
        Map2D map = new Map(size, size, 0);
        for (int i = 0; i < size; i++) {//borders
            map.setPixel(i, 0, -1);
            map.setPixel(i, size - 1, -1);
            map.setPixel(0, i, -1);
            map.setPixel(size - 1, i, -1);
        }
        for (int x = 2; x < size - 2; x++) map.setPixel(x, 3, -1);//maze
        for (int y = 3; y < size - 2; y++) map.setPixel(4, y, -1);
        for (int x = 4; x < size - 3; x++) map.setPixel(x, 7, -1);
        for (int y = 7; y < size - 2; y++) map.setPixel(9, y, -1);
        Pixel2D start  = new Index2D(1, 1);
        Pixel2D target = new Index2D(size - 2, size - 2);
        map.setPixel(start, 2);
        map.setPixel(target, 3);
        drawMap(map);
        StdDraw.pause(1500);
        Pixel2D[] path = map.shortestPath(start, target, -1, false);
        for (Pixel2D p : path) { //for each p from path
            if (!p.equals(start) && !p.equals(target)) { //color path
                map.setPixel(p, 4);
            }
        }
        drawMap(map);
        StdDraw.pause(1500);
    }
}