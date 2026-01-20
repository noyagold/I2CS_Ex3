package Server;


import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D {
    private int[][] _map;


    /**
     * Constructs a w*h 2D raster map with an init value v.
     * @param w
     * @param h
     * @param v
     */
    public Map(int w, int h, int v) {init(w,h, v);}
    /**
     * Constructs a square map (size*size).
     * @param size
     */


    /**
     * Constructs a map from a given 2D array.
     * @param data
     */
    public Map(int[][] data) {
        init(data);
    }
    @Override
    public void init(int w, int h, int v) {
        /////// add your code below ///////
        _map = new int[w][h];
        for (int x = 0; x < w; x++) for (int y = 0; y < h; y++) _map[x][y] = v;
        ///////////////////////////////////
    }
    @Override
    public void init(int[][] arr) {
        /////// add your code below ///////
        _map = new int[arr.length][arr[0].length];
        for (int x = 0; x < arr.length; x++) System.arraycopy(arr[x], 0, _map[x], 0, arr[0].length);

        ///////////////////////////////////
    }
    @Override
    public int[][] getMap() {

        /////// add your code below ///////
        return _map;
        ///////////////////////////////////

    }
    @Override
    /////// add your code below ///////
    public int getWidth() {return _map.length; }
    @Override
    /////// add your code below ///////
    public int getHeight() {return _map[0].length;}
    @Override
    /////// add your code below ///////
    public int getPixel(int x, int y) { return _map[x][y]; }
    @Override
    /////// add your code below ///////
    public int getPixel(Pixel2D p) {
        return getPixel(p.getX(), p.getY());
    }
    @Override
    /////// add your code below ///////
    public void setPixel(int x, int y, int v) {_map[x][y] = v; }
    @Override
    /////// add your code below ///////
    public void setPixel(Pixel2D p, int v) {
        setPixel(p.getX(), p.getY(), v);
    }
    @Override
    /**
     * Fills this map with the new color (new_v) starting from p.
     * https://en.wikipedia.org/wiki/Flood_fill
     */
    public int fill(Pixel2D xy, int new_v) {

        /////// add your code below ///////
        return 0;
        ///////////////////////////////////

    }

    @Override
    /**
     * BFS like shortest the computation based on iterative raster implementation of BFS, see:
     * https://en.wikipedia.org/wiki/Breadth-first_search
     */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {

        /////// add your code below ///////
        Map2D dists = allDistance(p1, obsColor);
        int d = dists.getPixel(p2);
        if (d == -1) return null; // No path
        Pixel2D[] path = new Pixel2D[d + 1];
        Pixel2D curr = p2; path[d] = p2;
        while (d > 0) {
            for (Index2D n : getNeighbours(curr)) {
                if (dists.getPixel(n) == d - 1) {
                    curr = n;
                    path[--d] = n;
                    break;
                }
            }
        }
        path[0] = p1;
        return path;
        ///////////////////////////////////
    }
    @Override
    /////// add your code below ///////
    public boolean isInside(Pixel2D p) {
        return p!=null && p.getX()>=0 && p.getY()>=0 && p.getX()<getWidth() && p.getY()<getHeight();
    }

    @Override
    /////// add your code below ///////
    public boolean isCyclic() {
        return true;
    }
    @Override
    /////// add your code below ///////
    public void setCyclic(boolean cy) {;}
    @Override
    /////// add your code below ///////
    public Map2D allDistance(Pixel2D start, int obsColor) {

        /////// add your code below ///////
        Map res = new Map(getWidth(), getHeight(), -1);
        Queue<Pixel2D> q = new LinkedList<>();
        if(isInside(start)) {
            q.add(start);
            res.setPixel(start, 0);
        }
        while (!q.isEmpty()) {
            Pixel2D curr = q.poll();
            int d = res.getPixel(curr);
            for (Index2D next : getNeighbours(curr)) {
                if (getPixel(next) != obsColor && res.getPixel(next) == -1) {
                    res.setPixel(next, d + 1);
                    q.add(next);
                }
            }
        }
        return res;
        ///////////////////////////////////

    }

    public Index2D[] getNeighbours(Pixel2D p) {
        int x = p.getX(), y = p.getY();
        int w = getWidth(), h = getHeight();
        // Uses double modulo to ensure positive indices for cyclic map
        return new Index2D[]{
                new Index2D((x + 1) % w, y),
                new Index2D((x - 1 + w) % w, y),
                new Index2D(x, (y + 1) % h),
                new Index2D(x, (y - 1 + h) % h)
        };
    }


}